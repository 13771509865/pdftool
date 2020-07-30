package com.neo.service.convertRecord.impl;

import com.neo.dao.PtsTotalConvertRecordPOMapper;
import com.neo.model.po.PtsTotalConvertRecordPO;
import com.neo.model.qo.PtsTotalConvertRecordQO;
import com.neo.service.convertRecord.ITotalConvertRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: xujun
 * @Date: 2020/7/29 4:09 下午
 */
@Service("totalConvertRecordService")
public class TotalConvertRecordService implements ITotalConvertRecordService {

    @Autowired
    private PtsTotalConvertRecordPOMapper ptsTotalConvertRecordPOMapper;




    @Override
    public int insertOrUpdatePtsTotalConvertRecord(PtsTotalConvertRecordPO ptsTotalConvertRecordPO,PtsTotalConvertRecordQO ptsTotalConvertRecordQO){
        return ptsTotalConvertRecordPOMapper.insertOrUpdatePtsTotalConvertRecord(ptsTotalConvertRecordPO,ptsTotalConvertRecordQO);
    }

    @Override
    public List<PtsTotalConvertRecordPO> selectPtsTotalConvertRecord(PtsTotalConvertRecordPO ptsTotalConvertRecordPO){
        return ptsTotalConvertRecordPOMapper.selectPtsTotalConvertRecord(ptsTotalConvertRecordPO);
    }

    @Override
    public int updateConvertNum(PtsTotalConvertRecordPO ptsTotalConvertRecordPO){
        return ptsTotalConvertRecordPOMapper.updateConvertNum(ptsTotalConvertRecordPO);
    }







}
