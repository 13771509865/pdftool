package com.neo.dao;

import java.util.List;

import com.neo.model.po.PtsAuthPO;

public interface PtsAuthPOMapper {

	Integer insertPtsAuthPO(PtsAuthPO ptsAuthPO);
	
	List<PtsAuthPO> selectAuthByUserid(Long userid);
	
	Integer updatePtsAuthPOByUserId(PtsAuthPO ptsAuthPO);
	
	Integer deletePtsAuth(PtsAuthPO ptsAuthPO);
	
	List<PtsAuthPO> selectAuth();//脚本
}
