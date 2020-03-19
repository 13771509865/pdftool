package com.neo.service.yzcloud.impl;

import com.neo.commons.cons.DefaultResult;
import com.neo.commons.cons.EnumConvertType;
import com.neo.commons.cons.EnumResultCode;
import com.neo.commons.cons.IResult;
import com.neo.commons.cons.constants.SysConstant;
import com.neo.commons.cons.constants.UaaConsts;
import com.neo.commons.cons.constants.YzcloudConsts;
import com.neo.commons.cons.entity.HttpResultEntity;
import com.neo.commons.properties.PtsProperty;
import com.neo.commons.util.HttpUtils;
import com.neo.commons.util.JsonUtils;
import com.neo.commons.util.ZipUtils;
import com.neo.dao.FcsFileInfoPOMapper;
import com.neo.dao.PtsYcUploadPOMapper;
import com.neo.model.bo.FcsFileInfoBO;
import com.neo.model.po.FcsFileInfoPO;
import com.neo.model.po.PtsYcUploadPO;
import com.neo.model.qo.PtsYcUploadQO;
import com.neo.service.convert.PtsYcUploadService;
import com.neo.service.httpclient.HttpAPIService;
import com.neo.service.yzcloud.IYzcloudService;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhoufeng
 * @description
 * @create 2019-11-26 09:07
 **/
@Service("yzcloudService") 
public class YzcloudService implements IYzcloudService {

    @Autowired
    private HttpAPIService httpAPIService;

    @Autowired
    private PtsProperty ptsProperty;

    @Autowired
    private FcsFileInfoPOMapper fcsFileInfoPOMapper;

    @Autowired
    private PtsYcUploadPOMapper ptsYcUploadPOMapper;

    @Async("uploadYcFileExecutor")
    @Override
    public IResult<String> uploadFileToYc(FcsFileInfoBO fcsFileInfoBO, Long userId, String cookie) {
        File targetFile = new File(ptsProperty.getFcs_targetfile_dir(), fcsFileInfoBO.getDestStoragePath());
        System.out.println(targetFile.getAbsolutePath());
        String finalFileName = targetFile.getName();
        if (targetFile.exists()) {
            if (EnumConvertType.isNeedPack(fcsFileInfoBO.getConvertType())) {
                // 需要打包
                String zipFilePath = getZipFilePath(fcsFileInfoBO);
                if(StringUtils.isBlank(zipFilePath)){
                    return DefaultResult.failResult(EnumResultCode.E_ZIP_PACKAGE_ERROR.getInfo());
                }
                targetFile = new File(ptsProperty.getFcs_targetfile_dir(), zipFilePath);
                finalFileName = FilenameUtils.getBaseName(fcsFileInfoBO.getSrcFileName())+".zip";
            }
            String url = ptsProperty.getYzcloud_domain() + YzcloudConsts.UPLOAD_INTERFACE;
            Map<String, Object> params = new HashMap<>();
            params.put("fileName", finalFileName);
            params.put("typeOfSource", "application.pdf");
            Map<String, Object> headers = new HashMap<>();
            headers.put(UaaConsts.COOKIE, cookie);
            System.out.println("开始发送信息给优云。。。。");
            IResult<HttpResultEntity> httpResult = httpAPIService.uploadResouse(targetFile, url, params, headers);
           System.out.println(httpResult.getData().getBody());
            if (HttpUtils.isHttpSuccess(httpResult)) {
                try {
                    Map<String, Object> resultMap = JsonUtils.parseJSON2Map(httpResult.getData().getBody());
                    if (!resultMap.isEmpty() && "0".equals(resultMap.get(YzcloudConsts.ERRORCODE).toString())) {
                        Map<String, Object> result = (Map<String, Object>) resultMap.get(YzcloudConsts.RESULT);
                        String fileId = result.get("fileId").toString();
                        if (StringUtils.isNotBlank(fileId)) {
                            FcsFileInfoPO fcsFileInfoPo = new FcsFileInfoPO();
                            fcsFileInfoPo.setUserID(userId);
                            fcsFileInfoPo.setFileHash(fcsFileInfoBO.getFileHash());
                            fcsFileInfoPo.setUCloudFileId(fileId);
                            int updateResult = fcsFileInfoPOMapper.updatePtsConvert(fcsFileInfoPo);
                            return DefaultResult.successResult(fileId);
                        }
                    }
                } catch (Exception e) {
                    return DefaultResult.failResult(EnumResultCode.E_SAVE_YCFILE_ERROR.getInfo());
                }
            }
            PtsYcUploadPO ptsYcUploadPO = new PtsYcUploadPO();
            ptsYcUploadPO.setConvertType(fcsFileInfoBO.getConvertType());
            ptsYcUploadPO.setDestStoragePath(fcsFileInfoBO.getDestStoragePath());
            ptsYcUploadPO.setFileHash(fcsFileInfoBO.getFileHash());
            ptsYcUploadPO.setCookie(cookie);
            ptsYcUploadPO.setUserId(userId);
            ptsYcUploadPO.setSrcFileName(fcsFileInfoBO.getSrcFileName());
            int num = ptsYcUploadPOMapper.insertPtsYcUpload(ptsYcUploadPO);
            if(num < 1){
                return DefaultResult.failResult(EnumResultCode.E_YCUPLOAD_SAVE_FAIL.getInfo());
            }
        }
        return DefaultResult.failResult(EnumResultCode.E_SAVE_YCFILE_ERROR.getInfo());
    }

    @Override
    public String getZipFilePath(FcsFileInfoBO fcsFileInfoBO) {
        String finalPath = null;
        String zipRelativePath = SysConstant.ZIP_TEMP + File.separator + fcsFileInfoBO.getFileHash() + File.separator
                + SysConstant.ZIP_NAME;
        File zipFile = new File(ptsProperty.getFcs_targetfile_dir(), zipRelativePath);
        // zip存在
        if (zipFile.isFile()) {
            finalPath = zipRelativePath;
        } else { // 不存在打包
            File parentFile = getParentFile(fcsFileInfoBO.getDestStoragePath());
            IResult<String> zipResult = ZipUtils.zipFile(zipFile, parentFile);
            if (zipResult.isSuccess()) {
                finalPath = zipRelativePath;
            }
        }
        return finalPath;
    }

    private File getParentFile(String childPath) {
        File destFile = new File(ptsProperty.getFcs_targetfile_dir(), childPath);
        if (destFile.isFile()) {
            // html文件
            return destFile.getParentFile();
        } else {
            return destFile;
        }
    }
}
