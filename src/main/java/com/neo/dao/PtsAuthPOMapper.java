package com.neo.dao;

import java.util.List;

import com.neo.model.po.PtsAuthPO;

public interface PtsAuthPOMapper {

	Integer insertPtsAuthPO(PtsAuthPO ptsAuthPO);
	
	List<PtsAuthPO> selectAuthByUserid(Long userid);
	
}
