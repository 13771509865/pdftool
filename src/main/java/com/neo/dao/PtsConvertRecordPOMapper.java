package com.neo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.neo.model.po.PtsConvertRecordPO;
import com.neo.model.qo.PtsConvertRecordQO;

public interface PtsConvertRecordPOMapper {
	
	int insertOrUpdatePtsConvertRecord(@Param("rp")PtsConvertRecordPO ptsConvertRecordPO,@Param("rq")PtsConvertRecordQO ptsConvertRecordQO);

	int updateConvertNum(PtsConvertRecordPO ptsConvertRecordPO);
	
	List<PtsConvertRecordPO> selectPtsConvertRecord(PtsConvertRecordPO ptsConvertRecordPO);
	
	int deletePtsConvertRecord(PtsConvertRecordPO ptsConvertRecordPO);
	
}
