package com.neo.service.vote.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neo.commons.cons.DefaultResult;
import com.neo.commons.cons.EnumResultCode;
import com.neo.commons.cons.EnumVoteCode;
import com.neo.commons.cons.IResult;
import com.neo.commons.util.HttpUtils;
import com.neo.dao.PtsVotePOMapper;
import com.neo.model.po.PtsVotePO;
import com.neo.service.vote.IVoteService;

/**
 * pdf工具集投票业务类
 * @author xujun
 * @description
 * @create 2019年10月28日
 */
@Service
public class VoteService implements IVoteService{
	
	@Autowired
	private PtsVotePOMapper ptsVotePOMapper;
	
	@Autowired
	private VoteManager voteManager;
	

	/**
	 * pdf工具集投票处理
	 */
	@Override
	public IResult<String> votePdfTools(Long userid,String ipAddress,String vote,String otherContent) {
		
		//投票内容不能为空
		if(StringUtils.isBlank(vote)) {
			return DefaultResult.failResult(EnumResultCode.E_VOTE_NULL_ERROR.getInfo());
		}
		//一次最多5票
		String[] votes = vote.split(",");
		if(votes.length > 5) {
			return DefaultResult.failResult(EnumResultCode.E_VOTE_NUM_ERROR.getInfo());
		}
		
		//如果选择其他选项
		if(vote.contains(String.valueOf(EnumVoteCode.OTHER.getValue()))) {
			//必须输入内容
			if(StringUtils.isBlank(otherContent)) {
				return DefaultResult.failResult(EnumResultCode.E_VOTE_CONTENT_NULL_ERROR.getInfo());
			}
			
			//不能超过50个字符
			if(StringUtils.length(otherContent) > 50) {
				return DefaultResult.failResult(EnumResultCode.E_VOTE_CONTENT_OVER_ERROR.getInfo());
			}
		}else {
			//选择其他选项后，才能输入内容
			if(StringUtils.isNoneBlank(otherContent)) {
				return DefaultResult.failResult(EnumResultCode.E_VOTE_OTHER_ERROR.getInfo());
			}
		}
		
		PtsVotePO ptsVotePO = voteManager.buildPtsVotePO(userid, ipAddress, vote, otherContent);

		boolean insertPtsVotePO = insertPtsVotePO(ptsVotePO)>0;
		if(insertPtsVotePO) {
			return DefaultResult.successResult();
		}else {
			return DefaultResult.failResult(EnumResultCode.E_SERVER_BUSY.getInfo());
		}
	}

	
	
	/**
	 * 插入PtsVotePO用户投票对象
	 */
	@Override
	public Integer insertPtsVotePO(PtsVotePO ptsVotePO) {
		return ptsVotePOMapper.insertPtsVotePO(ptsVotePO);
	}


	
}
