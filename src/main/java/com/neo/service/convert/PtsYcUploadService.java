package com.neo.service.convert;

import com.neo.commons.cons.DefaultResult;
import com.neo.commons.cons.EnumResultCode;
import com.neo.commons.cons.IResult;
import com.neo.commons.cons.EnumYcUploadSType;
import com.neo.commons.util.SysLogUtils;
import com.neo.model.bo.FcsFileInfoBO;
import com.neo.model.po.PtsYcUploadPO;
import com.neo.model.qo.PtsYcUploadQO;
import com.neo.service.yzcloud.IYzcloudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author miaowei
 * @description
 * @create 2019-12-03 16:33
 **/
@Service
public class PtsYcUploadService {

    @Autowired
    private IYzcloudService iYzcloudService;


    /**
     * 重试上传优云失败的文件
     *
     * @return
     */
    public IResult<String> retryYCUpload() {
        PtsYcUploadQO ptsYcUploadQO = new PtsYcUploadQO();
        ptsYcUploadQO.setStatus(EnumYcUploadSType.E_FALE.getValue());
        List<PtsYcUploadPO> list = iYzcloudService.selectPtsYcUploadPOByStatus(ptsYcUploadQO);
        if (list.size() < 0) {
            return DefaultResult.failResult(EnumResultCode.E_YCUPLOAD_NULL.getInfo());
        }
        List<Integer> idsList = new ArrayList<>();

        //批量重试上传失败的文件
        for (PtsYcUploadPO pts : list) {
            String cookie = pts.getCookie();
            Long userId = pts.getUserId();
            FcsFileInfoBO fcsFileInfoBO = FcsFileInfoBO.builder()
                    .convertType(pts.getConvertType())
                    .fileHash(pts.getFileHash())
                    .destStoragePath(pts.getDestStoragePath())
                    .srcFileName(pts.getSrcFileName()).build();
            IResult<String> result = iYzcloudService.uploadFileToYc(fcsFileInfoBO, userId, cookie);
            if (result.isSuccess()) {
                idsList.add(pts.getId());
            }
        }
        //重试成功后批量修改状态
        if(!idsList.isEmpty() && idsList.size() > 0){
            Integer updatePtsYcUploadByIds = iYzcloudService.updatePtsYcUploadByIds(idsList);
            SysLogUtils.info("==================重试成功，数量："+updatePtsYcUploadByIds+"=====================");
        }

        //修改超过24小时失败的上传记录，不再重试
        PtsYcUploadQO p = PtsYcUploadQO.builder().status(EnumYcUploadSType.E_NO_RETRY.getValue()).build()  ;
        Integer updatePtsYcUpload = iYzcloudService.updatePtsYcUpload(p);
        SysLogUtils.info("==================超过24小时，数量："+updatePtsYcUpload+"=====================");
        return DefaultResult.successResult();
    }
}
