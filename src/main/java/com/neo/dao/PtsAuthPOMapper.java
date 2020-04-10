package com.neo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.neo.model.po.PtsAuthPO;
import com.neo.model.qo.PtsAuthQO;

public interface PtsAuthPOMapper {

	Integer insertPtsAuthPO(PtsAuthPO ptsAuthPO);
	
	List<PtsAuthPO> selectPtsAuthPO(PtsAuthQO ptsAuthQO);
	
	Integer deletePtsAuth(Long userid);
	
}
