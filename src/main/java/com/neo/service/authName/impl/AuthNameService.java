package com.neo.service.authName.impl;

import com.neo.dao.PtsAuthNamePOMapper;
import com.neo.model.po.PtsAuthNamePO;
import com.neo.model.qo.PtsAuthNameQO;
import com.neo.service.authName.IAuthNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthNameService implements IAuthNameService{
	
	
	@Autowired
	private PtsAuthNamePOMapper ptsAuthNamePOMapper;
	
	

	@Override
	public List<PtsAuthNamePO> selectPtsAuthNamePO(PtsAuthNameQO ptsAuthNameQO){
		return ptsAuthNamePOMapper.selectPtsAuthNamePO(ptsAuthNameQO);
	}


	@Override
	public int insertPtsAuthNamePO(PtsAuthNamePO ptsAuthNamePO){
		return ptsAuthNamePOMapper.insertPtsAuthNamePO(ptsAuthNamePO);
	}
	
	
}
