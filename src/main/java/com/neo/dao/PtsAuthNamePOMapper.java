package com.neo.dao;

import java.util.List;

import com.neo.model.po.PtsAuthNamePO;
import com.neo.model.qo.PtsAuthNameQO;

public interface PtsAuthNamePOMapper {

	List<PtsAuthNamePO> selectPtsAuthNamePO(PtsAuthNameQO PtsAuthNameQO);
	
	int insertPtsAuthNamePO(PtsAuthNamePO ptsAuthNamePO);
	
}
