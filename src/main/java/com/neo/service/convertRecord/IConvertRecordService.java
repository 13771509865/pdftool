package com.neo.service.convertRecord;

import java.util.List;

import com.neo.model.po.PtsConvertRecordPO;
import com.neo.model.qo.PtsConvertRecordQO;

public interface IConvertRecordService {

	int insertOrUpdatePtsConvertRecord(PtsConvertRecordPO ptsConvertRecordPO,PtsConvertRecordQO ptsConvertRecordQO);
	
	int updateConvertNum(PtsConvertRecordPO ptsConvertRecordPO);
	
	List<PtsConvertRecordPO> selectPtsConvertRecord(PtsConvertRecordPO ptsConvertRecordPO);
	
	int deletePtsConvertRecord(PtsConvertRecordPO ptsConvertRecordPO);
}
