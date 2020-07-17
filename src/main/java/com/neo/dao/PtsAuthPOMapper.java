package com.neo.dao;

import com.neo.model.po.PtsAuthPO;
import com.neo.model.qo.PtsAuthQO;

import java.util.List;

public interface PtsAuthPOMapper {

	Integer insertPtsAuthPO(List<PtsAuthPO> list);
	
	List<PtsAuthPO> selectPtsAuthPO(PtsAuthQO ptsAuthQO);
	
	Integer deletePtsAuth(Long userid);

	List<PtsAuthPO> selectAuth(PtsAuthQO ptsAuthQO);


	//更新完就可以删了
//	List<PtsAuthPO> selectOldPtsAuthPO(PtsAuthQO ptsAuthQO);
	
}
