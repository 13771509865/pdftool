package com.neo.service.convertRecord.impl;

import com.neo.commons.cons.DefaultResult;
import com.neo.commons.cons.EnumStatus;
import com.neo.commons.cons.IResult;
import com.neo.commons.cons.entity.ConvertEntity;
import com.neo.commons.util.DateViewUtils;
import com.neo.dao.PtsFailRecordPOMapper;
import com.neo.model.bo.ConvertParameterBO;
import com.neo.model.bo.FcsFileInfoBO;
import com.neo.model.po.PtsFailRecordPO;
import com.neo.service.convertRecord.IFailRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: xujun
 * @Date: 2020/7/2 10:00 上午
 */
@Service("failRecordService")
public class FailRecordService implements IFailRecordService {

    @Autowired
    private PtsFailRecordPOMapper ptsFailRecordPOMapper;


    /**
     * 插入转换失败的文件信息
     * @param result
     * @param convertBO
     * @param convertEntity
     * @return
     */
    public IResult<String> insertPtsFailRecord(IResult<FcsFileInfoBO> result , ConvertParameterBO convertBO, ConvertEntity convertEntity){
        PtsFailRecordPO ptsFailRecordPO =new PtsFailRecordPO();
        ptsFailRecordPO.setUserId(convertEntity.getUserId());
        ptsFailRecordPO.setStatus(EnumStatus.ENABLE.getValue());
        ptsFailRecordPO.setSrcFileSize(convertBO.getSrcFileSize());
        ptsFailRecordPO.setSrcFileName(convertBO.getSrcFileName());
        ptsFailRecordPO.setResultMessage(result.getMessage());
        ptsFailRecordPO.setResultCode(result.getData().getCode());
        ptsFailRecordPO.setModule(convertEntity.getModule());
        ptsFailRecordPO.setConvertType(convertBO.getConvertType());
        ptsFailRecordPO.setGmtCreate(DateViewUtils.getNowDate());
        ptsFailRecordPO.setGmtModified(DateViewUtils.getNowDate());

        Boolean insertPtsFailRecord = insertPtsFailRecord(ptsFailRecordPO)>0;
        return insertPtsFailRecord?DefaultResult.successResult():DefaultResult.failResult();
    }





    /**
     * 插入转换失败的文件信息
     * @param ptsFailRecordPO
     * @return
     */
    public int insertPtsFailRecord(PtsFailRecordPO ptsFailRecordPO){
        return ptsFailRecordPOMapper.insertPtsFailRecord(ptsFailRecordPO);
    }



}
