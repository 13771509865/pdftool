package com.neo.dao;

import com.neo.model.po.PtsTotalConvertRecordPO;
import com.neo.model.qo.PtsTotalConvertRecordQO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: xujun
 * @Date: 2020/7/29 3:38 下午
 */
public interface PtsTotalConvertRecordPOMapper {

    int insertOrUpdatePtsTotalConvertRecord(@Param("rp") PtsTotalConvertRecordPO ptsTotalConvertRecordPO, @Param("rq") PtsTotalConvertRecordQO ptsTotalConvertRecordQO);

    List<PtsTotalConvertRecordPO> selectPtsTotalConvertRecord(PtsTotalConvertRecordPO ptsTotalConvertRecordPO);

    int updateConvertNum(PtsTotalConvertRecordPO ptsTotalConvertRecordPO);

}
