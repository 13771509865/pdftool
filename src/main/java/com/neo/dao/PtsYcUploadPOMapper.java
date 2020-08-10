package com.neo.dao;

import com.neo.model.po.PtsYcUploadPO;
import com.neo.model.qo.PtsYcUploadQO;

import java.util.List;


public interface PtsYcUploadPOMapper {
    int insertPtsYcUpload(PtsYcUploadPO ptsYcUploadPO);

    int updatePtsYcUpload(PtsYcUploadQO ptsYcUploadQO);

    List<PtsYcUploadPO> selectPtsYcUploadPOByStatus(PtsYcUploadQO ptsYcUploadQO);

    int updatePtsYcUploadByIds(List<Integer> list);
}
