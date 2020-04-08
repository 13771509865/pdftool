package com.neo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.neo.model.po.PtsAuthPO;
import com.neo.model.qo.PtsAuthQO;

public interface PtsAuthPOMapper {

	Integer insertPtsAuthPO(PtsAuthPO ptsAuthPO);
	
	List<PtsAuthPO> selectPtsAuthPO(PtsAuthQO ptsAuthQO);
	
	Integer updatePtsAuthPO(PtsAuthPO ptsAuthPO);
	
	Integer deletePtsAuth(PtsAuthPO ptsAuthPO);
	
	Integer updatePtsAuthPOByPriority(@Param("vt")Integer validityTime,@Param("ut")String unitType,@Param("uId")Long userid,@Param("pri")Integer priority);
	
	List<PtsAuthPO> selectPtsAuthPOByPriority(@Param("uId")Long userid,@Param("sta")Integer status);
	
}
