package com.neo.service.yzcloud;

import com.neo.commons.cons.IResult;
import com.neo.commons.cons.entity.ConvertEntity;
import com.neo.model.bo.FcsFileInfoBO;
import com.neo.model.po.PtsYcUploadPO;
import com.neo.model.qo.PtsYcUploadQO;

import java.util.List;

/**
 * @author zhoufeng
 * @description 优云相关service
 * @create 2019-11-26 09:04
 **/
public interface IYzcloudService {

    IResult<String> uploadFileToYc(FcsFileInfoBO fcsFileInfoBO, Long userId, String cookie);

    String getZipFilePath(FcsFileInfoBO fcsFileInfoBO);

    IResult<String> recordFailYCUpload(IResult<String> uploadFileToYc, FcsFileInfoBO fcsFileInfoBO, Long userId, ConvertEntity convertEntity);

    int insertPtsYcUpload(PtsYcUploadPO ptsYcUploadPO);

    int updatePtsYcUpload(PtsYcUploadQO ptsYcUploadQO);

    List<PtsYcUploadPO> selectPtsYcUploadPOByStatus(PtsYcUploadQO ptsYcUploadQO);

    int updatePtsYcUploadByIds(List<Integer> ids);
}
