package com.neo.service.vote.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.neo.commons.cons.EnumStatus;
import com.neo.commons.util.DateViewUtils;
import com.neo.commons.util.HttpUtils;
import com.neo.model.po.PtsVotePO;

@Service("voteManager")
public class VoteManager {
	
	
	/**
	 * 构建PtsVotePO对象
	 * @param userid
	 * @param vote
	 * @param otherContent
	 * @return
	 */
	public PtsVotePO buildPtsVotePO(Long userid,String ipAddress,String vote,String otherContent) {
		PtsVotePO ptsVotePO = new PtsVotePO();
		ptsVotePO.setGmtCreate(DateViewUtils.getNowDate());
		ptsVotePO.setGmtModified(DateViewUtils.getNowDate());
		ptsVotePO.setStatus(EnumStatus.ENABLE.getValue());
		ptsVotePO.setUserid(userid);
		ptsVotePO.setIpAddress(ipAddress);
		ptsVotePO.setVote(vote);
		ptsVotePO.setOtherContent(otherContent);
		return ptsVotePO;
	}

}
