package com.neo.service.authName;

import java.util.List;

import com.neo.model.po.PtsAuthNamePO;
import com.neo.model.qo.PtsAuthNameQO;

public interface IAuthNameService {
	
	List<PtsAuthNamePO> selectPtsAuthNamePO(PtsAuthNameQO ptsAuthNameQO);

}
