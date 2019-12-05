package com.neo.dao;

import com.neo.model.po.PtsYcUploadPO;
import com.neo.model.qo.PtsYcUploadQO;

import java.util.List;
import java.util.Map;


public interface PtsYcUploadPOMapper {
    int insertPtsYcUpload(PtsYcUploadPO ptsYcUploadPO);

    int updatePtsYcUpload(PtsYcUploadQO ptsYcUploadQO);

    int deletePtsYcUpload(Map map);

    List<PtsYcUploadPO> selectPtsYcUploadPOByStatus(PtsYcUploadQO ptsYcUploadQO);

    int updatePtsYcUploadByIds(List ids);
}
