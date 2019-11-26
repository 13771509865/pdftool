package com.neo.dao;

import java.util.List;

import com.neo.model.po.FcsFileInfoPO;
import com.neo.model.qo.FcsFileInfoQO;

public interface FcsFileInfoPOMapper {
	
	int insertPtsConvert(FcsFileInfoPO fcsFileInfoPO);
	
	List<FcsFileInfoPO> selectFcsFileInfoPOByUserID(FcsFileInfoQO fcsFileInfoQO);
	
	int selectCountNumFcsFileInfoPOByUserID(FcsFileInfoQO fcsFileInfoQO);
	
	int updatePtsConvert(FcsFileInfoPO fcsFileInfoPO);
	
	int deletePtsConvert(FcsFileInfoQO fcsFileInfoQO);
	
}
