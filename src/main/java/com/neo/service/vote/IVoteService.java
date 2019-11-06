package com.neo.service.vote;

import com.neo.commons.cons.IResult;
import com.neo.model.po.PtsVotePO;

public interface IVoteService {

	IResult<String> votePdfTools(Long userid,String ipAddress,String vote,String otherContent);
	
	Integer insertPtsVotePO(PtsVotePO ptsVotePO);
}
