package com.neo.dao;

import java.util.List;

import com.neo.model.po.PtsSummaryPO;
import com.neo.model.qo.PtsSummaryQO;

public interface PtsSummaryPOMapper {
	
	int insertPtsSumm(PtsSummaryPO ptsSummaryPO);
	
	int updatePtsSumm(PtsSummaryPO ptsSummaryPO);
	
	PtsSummaryPO selectCountBySize();
	
	List<PtsSummaryPO> selectCountByIpAndDate(PtsSummaryQO ptsSummaryQO);

}
