package com.neo.service.convertRecord.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neo.dao.PtsConvertRecordPOMapper;
import com.neo.model.po.PtsConvertRecordPO;
import com.neo.model.qo.PtsConvertRecordQO;
import com.neo.service.convertRecord.IConvertRecordService;


@Service("convertRecordService")
public class ConvertRecordService implements IConvertRecordService{
	
	@Autowired
	private PtsConvertRecordPOMapper ptsConvertRecordPOMapper;
	
	
	/**
	 * 进来转换就加一次
	 */
	public int insertOrUpdatePtsConvertRecord(PtsConvertRecordPO ptsConvertRecordPO,PtsConvertRecordQO ptsConvertRecordQO) {
		return ptsConvertRecordPOMapper.insertOrUpdatePtsConvertRecord(ptsConvertRecordPO, ptsConvertRecordQO);
	}

	
	/**
	 * 转换失败还次数
	 * @param ptsConvertRecordPO
	 * @return
	 */
	public int updateConvertNum(PtsConvertRecordPO ptsConvertRecordPO) {
		return ptsConvertRecordPOMapper.updateConvertNum(ptsConvertRecordPO);
	}
	
	
	
	public List<PtsConvertRecordPO> selectPtsConvertRecord(PtsConvertRecordPO ptsConvertRecordPO){
		return ptsConvertRecordPOMapper.selectPtsConvertRecord(ptsConvertRecordPO);
	}
	
	
	
}
