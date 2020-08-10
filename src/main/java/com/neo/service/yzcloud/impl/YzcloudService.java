package com.neo.service.yzcloud.impl;

import com.neo.commons.cons.DefaultResult;
import com.neo.commons.cons.EnumConvertType;
import com.neo.commons.cons.EnumResultCode;
import com.neo.commons.cons.IResult;
import com.neo.commons.cons.constants.SysConstant;
import com.neo.commons.cons.constants.UaaConsts;
import com.neo.commons.cons.constants.YzcloudConsts;
import com.neo.commons.cons.entity.ConvertEntity;
import com.neo.commons.cons.entity.HttpResultEntity;
import com.neo.commons.cons.EnumYcUploadSType;
import com.neo.commons.properties.PtsProperty;
import com.neo.commons.util.HttpUtils;
import com.neo.commons.util.JsonUtils;
import com.neo.commons.util.SysLogUtils;
import com.neo.commons.util.ZipUtils;
import com.neo.dao.PtsYcUploadPOMapper;
import com.neo.model.bo.FcsFileInfoBO;
import com.neo.model.po.FcsFileInfoPO;
import com.neo.model.po.PtsYcUploadPO;
import com.neo.model.qo.PtsYcUploadQO;
import com.neo.service.convert.PtsConvertService;
import com.neo.service.httpclient.HttpAPIService;
import com.neo.service.yzcloud.IYzcloudService;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
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
    private PtsYcUploadPOMapper ptsYcUploadPOMapper;
    
    @Autowired
    private PtsConvertService ptsConvertService;
 
    @Override
    public IResult<String> uploadFileToYc(FcsFileInfoBO fcsFileInfoBO, Long userId, String cookie) {
        File targetFile = new File(ptsProperty.getFcs_targetfile_dir(), fcsFileInfoBO.getDestStoragePath());
        String finalFileName = targetFile.getName();
        SysLogUtils.info(System.currentTimeMillis()+"===转换完成进入上传优云方法，文件名==="+finalFileName);
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
            SysLogUtils.info(System.currentTimeMillis()+"==开始发送信息给优云。。。。文件名："+finalFileName);
            IResult<HttpResultEntity> httpResult = httpAPIService.uploadResouse(targetFile, url, params, headers);
            SysLogUtils.info(System.currentTimeMillis()+"====优云返回的结果。。。。"+httpResult.getData().getBody()+"==文件名==="+finalFileName);
            Map<String, Object> resultMap = JsonUtils.parseJSON2Map(httpResult.getData().getBody());
            if (HttpUtils.isHttpSuccess(httpResult)) {
                try {
                    if (!resultMap.isEmpty() && "0".equals(resultMap.get(YzcloudConsts.ERRORCODE).toString())) {
                        Map<String, Object> result = (Map<String, Object>) resultMap.get(YzcloudConsts.RESULT);
                        String fileId = result.get("fileId").toString();
                        if (StringUtils.isNotBlank(fileId)) {
                            FcsFileInfoPO fcsFileInfoPo = new FcsFileInfoPO();
                            fcsFileInfoPo.setUserID(userId);
                            fcsFileInfoPo.setFileHash(fcsFileInfoBO.getFileHash());
                            fcsFileInfoPo.setUCloudFileId(fileId);
                            int updateResult = ptsConvertService.updatePtsConvert(fcsFileInfoPo);
                            SysLogUtils.info(System.currentTimeMillis()+"=====优云返回结果数据库记录完毕=====文件名："+finalFileName);
                            return DefaultResult.successResult(resultMap.get(YzcloudConsts.ERROR_MESSAGE).toString(),fileId);
                        }
                        return DefaultResult.failResult(EnumResultCode.E_GET_UCLOUD_ID_ERROR.getInfo());
                    }
                    return DefaultResult.failResult(resultMap.get(YzcloudConsts.ERROR_MESSAGE).toString());
                } catch (Exception e) {
                    return DefaultResult.failResult(EnumResultCode.E_SAVE_YCFILE_ERROR.getInfo());
                }
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


    /**
     * 记录上传优云失败的文件记录
     * @param fcsFileInfoBO
     * @param userId
     * @return
     */
    @Override
    public IResult<String> recordFailYCUpload(IResult<String> uploadFileToYc, FcsFileInfoBO fcsFileInfoBO, Long userId, ConvertEntity convertEntity){
        Integer status = uploadFileToYc.isSuccess()?EnumYcUploadSType.E_SUCCESS.getValue():EnumYcUploadSType.E_FALE.getValue();
        Integer errorCode = uploadFileToYc.isSuccess()?EnumResultCode.E_SUCCES.getValue():EnumResultCode.E_FAIL.getValue();
        PtsYcUploadPO ptsYcUploadPO = PtsYcUploadPO.builder()
                        .errorCode(errorCode)
                        .errorMessage(uploadFileToYc.getMessage())
                        .convertType(fcsFileInfoBO.getConvertType())
                        .destStoragePath(fcsFileInfoBO.getDestStoragePath())
                        .fileHash(convertEntity.getFileHash())
                        .cookie(convertEntity.getCookie())
                        .userId(userId)
                        .srcFileName(fcsFileInfoBO.getSrcFileName())
                        .status(status).build();
        int num = insertPtsYcUpload(ptsYcUploadPO);
        if(num < 1){
            return DefaultResult.failResult(EnumResultCode.E_YCUPLOAD_SAVE_FAIL.getInfo());
        }
        return DefaultResult.successResult();
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



    public int insertPtsYcUpload(PtsYcUploadPO ptsYcUploadPO){
        return ptsYcUploadPOMapper.insertPtsYcUpload(ptsYcUploadPO);
    }

    public int updatePtsYcUpload(PtsYcUploadQO ptsYcUploadQO){
        return ptsYcUploadPOMapper.updatePtsYcUpload(ptsYcUploadQO);
    }


    public List<PtsYcUploadPO> selectPtsYcUploadPOByStatus(PtsYcUploadQO ptsYcUploadQO){
        return ptsYcUploadPOMapper.selectPtsYcUploadPOByStatus(ptsYcUploadQO);
    }

    public int updatePtsYcUploadByIds(List<Integer> ids){
        return ptsYcUploadPOMapper.updatePtsYcUploadByIds(ids);
    }

    public static void main(String[] args) {
        String a = "优云APP";
        String b = "优APaPggregegergr";
        System.out.println(StringUtils.containsOnly(a,b));
    }
}
