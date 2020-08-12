package com.neo.service.convertRecord;

import com.neo.model.po.PtsTotalConvertRecordPO;
import com.neo.model.qo.PtsTotalConvertRecordQO;

import java.util.List;

/**
 * @Author: xujun
 * @Date: 2020/7/29 4:08 下午
 */
public interface ITotalConvertRecordService {


    int insertOrUpdatePtsTotalConvertRecord(PtsTotalConvertRecordPO ptsTotalConvertRecordPO,PtsTotalConvertRecordQO ptsTotalConvertRecordQO);

    List<PtsTotalConvertRecordPO> selectPtsTotalConvertRecord(PtsTotalConvertRecordPO ptsTotalConvertRecordPO);

    int updateConvertNum(PtsTotalConvertRecordPO ptsTotalConvertRecordPO);

}

