package com.neo.web.vote;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.neo.commons.cons.EnumResultCode;
import com.neo.commons.cons.IResult;
import com.neo.commons.util.DateViewUtils;
import com.neo.commons.util.HttpUtils;
import com.neo.commons.util.JsonResultUtils;
import com.neo.dao.PtsAuthPOMapper;
import com.neo.model.po.PtsAuthPO;
import com.neo.service.vote.IVoteService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "投票相关Controller", tags = {"投票相关Controller"})
@Controller
@RequestMapping(value = "/api/vote")
public class VoteController {

	
	
	@Autowired
	private IVoteService iVoteService;
	
	@Autowired
	private PtsAuthPOMapper ptsAuthPOMapper;
	
	
	
	@ApiOperation(value = "pdf工具集投票")
	@PostMapping(value = "/pdfTools")
	@ResponseBody
	public ResponseEntity<String> votePdfTools(HttpServletRequest request,String vote,String otherContent){
		try {
			IResult<String> result =  iVoteService.votePdfTools(HttpUtils.getSessionUserID(request), HttpUtils.getIpAddr(request), vote, otherContent);
			return ResponseEntity.ok(result.isSuccess()?JsonResultUtils.success(null,result.getMessage()):JsonResultUtils.fail(result.getMessage()));
		} catch (Exception e) {
			return new ResponseEntity<String>(JsonResultUtils.fail(EnumResultCode.E_SERVER_BUSY.getInfo()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
}
