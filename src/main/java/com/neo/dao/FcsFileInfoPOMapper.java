package com.neo.dao;

import com.neo.model.po.FcsFileInfoPO;
import com.neo.model.qo.FcsFileInfoQO;

import java.util.List;

public interface FcsFileInfoPOMapper {
	
	int insertPtsConvert(FcsFileInfoPO fcsFileInfoPO);
	
	List<FcsFileInfoPO> selectFcsFileInfoPOByUserID(FcsFileInfoQO fcsFileInfoQO);
	
	int selectCountNumFcsFileInfoPOByUserID(FcsFileInfoQO fcsFileInfoQO);
	
	int updatePtsConvert(FcsFileInfoPO fcsFileInfoPO);
	
	int deletePtsConvert(FcsFileInfoQO fcsFileInfoQO);

	List<FcsFileInfoPO> selectFcsFileInfoPO(FcsFileInfoQO fcsFileInfoQO);
	
}
