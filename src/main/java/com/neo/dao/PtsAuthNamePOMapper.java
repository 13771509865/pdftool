package com.neo.dao;

import java.util.List;

import com.neo.model.po.PtsAuthNamePO;

public interface PtsAuthNamePOMapper {

	List<PtsAuthNamePO> selectPtsAuthNamePO();
	
	int insertPtsAuthNamePO(PtsAuthNamePO ptsAuthNamePO);
	
}
