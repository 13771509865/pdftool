package com.neo.service.convertRecord;

import com.neo.commons.cons.IResult;
import com.neo.commons.cons.entity.ConvertEntity;
import com.neo.model.bo.ConvertParameterBO;
import com.neo.model.bo.FcsFileInfoBO;
import com.neo.model.po.PtsFailRecordPO;

/**
 * @Author: xujun
 * @Date: 2020/7/2 10:00 上午
 */
public interface IFailRecordService {

    IResult<String> insertPtsFailRecord(IResult<FcsFileInfoBO> result , ConvertParameterBO convertBO, ConvertEntity convertEntity);

    int insertPtsFailRecord(PtsFailRecordPO ptsFailRecordPO);

}
