package com.neo.dao;

import java.util.List;

import com.neo.model.po.FcsFileInfoPO;

public interface FcsFileInfoPOMapper {
	
	int insertPtsConvert(FcsFileInfoPO fcsFileInfoPO);
	
	List<FcsFileInfoPO> selectFcsFileInfoPOByUserID(Long userID);
	
	int updatePtsConvert(FcsFileInfoPO fcsFileInfoPO);
	
	int deletePtsConvert(FcsFileInfoPO fcsFileInfoPO);

}
