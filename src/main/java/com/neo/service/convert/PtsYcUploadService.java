package com.neo.service.convert;

import com.neo.commons.cons.DefaultResult;
import com.neo.commons.cons.EnumConvertType;
import com.neo.commons.cons.EnumResultCode;
import com.neo.commons.cons.IResult;
import com.neo.commons.cons.constants.UaaConsts;
import com.neo.commons.cons.constants.YzcloudConsts;
import com.neo.commons.cons.entity.HttpResultEntity;
import com.neo.commons.properties.PtsProperty;
import com.neo.commons.util.HttpUtils;
import com.neo.commons.util.JsonUtils;
import com.neo.commons.util.SysLogUtils;
import com.neo.dao.FcsFileInfoPOMapper;
import com.neo.dao.PtsYcUploadPOMapper;
import com.neo.model.bo.FcsFileInfoBO;
import com.neo.model.po.FcsFileInfoPO;
import com.neo.model.po.PtsYcUploadPO;
import com.neo.model.qo.PtsYcUploadQO;
import com.neo.service.httpclient.HttpAPIService;
import com.neo.service.yzcloud.IYzcloudService;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author miaowei
 * @description
 * @create 2019-12-03 16:33
 **/
@Service
public class PtsYcUploadService {
    @Autowired
    private PtsYcUploadPOMapper ptsYcUploadPOMapper;


    @Autowired
    private HttpAPIService httpAPIService;

    @Autowired
    private PtsProperty ptsProperty;

    @Autowired
    private FcsFileInfoPOMapper fcsFileInfoPOMapper;

    @Autowired
    private IYzcloudService iYzcloudService;

    //保存上传优云文件失败记录
    public IResult<String> savePtsYcUpload(PtsYcUploadPO ptsYcUploadPO,String errorcode){
        int num = ptsYcUploadPOMapper.insertPtsYcUpload(ptsYcUploadPO);
        if(num>0){
            return DefaultResult.successResult();
        }
        return DefaultResult.failResult(EnumResultCode.E_YCUPLOAD_SAVE_FAIL.getInfo());
    }

    public IResult<String> updatePtsYcUpload(PtsYcUploadQO ptsYcUploadQO){
        int num = ptsYcUploadPOMapper.updatePtsYcUpload(ptsYcUploadQO);
        if(num>0){
            return DefaultResult.successResult();
        }
        return DefaultResult.failResult(EnumResultCode.E_YCUPLOAD_SAVE_FAIL.getInfo());
    }

    public IResult<String> deletePtsYcUpload(Map map){
        int num = ptsYcUploadPOMapper.deletePtsYcUpload(map);
        if(num>0){
            return DefaultResult.successResult();
        }
        return DefaultResult.failResult(EnumResultCode.E_FAIL.getInfo());
    }

    public IResult<String> retryYCUpload(){
        PtsYcUploadQO ptsYcUploadQO = new PtsYcUploadQO();
        ptsYcUploadQO.setStatus(0);
        List<PtsYcUploadPO> list = ptsYcUploadPOMapper.selectPtsYcUploadPOByStatus(ptsYcUploadQO);
        if(list.size()<0){
            return DefaultResult.failResult(EnumResultCode.E_YCUPLOAD_NULL.getInfo());
        }
        List ids = new ArrayList();
        for(PtsYcUploadPO pts : list){
            String cookie = pts.getCookie();
            Long userId = pts.getUserId();
            FcsFileInfoBO fcsFileInfoBO = new FcsFileInfoBO();
            fcsFileInfoBO.setConvertType(pts.getConvertType());
            fcsFileInfoBO.setFileHash(pts.getFileHash());
            fcsFileInfoBO.setDestStoragePath(pts.getDestStoragePath());
            fcsFileInfoBO.setSrcFileName(pts.getSrcFileName());
            File targetFile = new File(ptsProperty.getFcs_targetfile_dir(), fcsFileInfoBO.getDestStoragePath());
            String finalFileName = targetFile.getName();
            if (targetFile.exists()) {
                if (EnumConvertType.isNeedPack(fcsFileInfoBO.getConvertType())) {
                    // 需要打包
                    String zipFilePath = iYzcloudService.getZipFilePath(fcsFileInfoBO);
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
                IResult<HttpResultEntity> httpResult = httpAPIService.uploadResouse(targetFile, url, params, headers);

                if (HttpUtils.isHttpSuccess(httpResult)) {
                    ids.add(pts.getId());
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
                            }
                        }
                    } catch (Exception e) {
                        SysLogUtils.error(EnumResultCode.E_SAVE_YCFILE_ERROR.getInfo()+"-------"+pts.getFileHash());
                    }
                }
            }
            SysLogUtils.error(EnumResultCode.E_SAVE_YCFILE_ERROR.getInfo()+"----------"+pts.getFileHash());
        }
        if(ids.size()>0){
            Map<String,Object> map = new HashMap<>();
            map.put("ids",ids);
            IResult<String> dResult = deletePtsYcUpload(map);
            return DefaultResult.successResult();
        }
        return DefaultResult.failResult(EnumResultCode.E_YCUPLOAD_RETRY_NULL.getInfo());

    }
}
