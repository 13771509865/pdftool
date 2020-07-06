package com.neo.service.authName;

import com.neo.model.po.PtsAuthNamePO;
import com.neo.model.qo.PtsAuthNameQO;

import java.util.List;

public interface IAuthNameService {
	
	List<PtsAuthNamePO> selectPtsAuthNamePO(PtsAuthNameQO ptsAuthNameQO);

	int insertPtsAuthNamePO(PtsAuthNamePO ptsAuthNamePO);
}
