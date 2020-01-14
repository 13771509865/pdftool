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
@RequestMapping(value = "/vote")
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
	
	
	
	@ApiOperation(value = "pdf工具集投票")
	@GetMapping(value = "/change/rong")
	@ResponseBody
	public ResponseEntity<String> changeRong(){
		List<PtsAuthPO> listWrong = ptsAuthPOMapper.selectAuthWrong();
		int a = 0;
		for(PtsAuthPO ptsAuth : listWrong) {
			if(ptsAuth.getId() < 206) {
				List<String> infoList = ptsAuthPOMapper.selectInfoByUserId(ptsAuth.getUserid());
				if(infoList.size() == 1) {
					int date = Integer.valueOf(StringUtils.substring(infoList.get(0), 4, 5));
					Date expireDate = DateViewUtils.stepMonth(ptsAuth.getGmtCreate(),date,5);
					PtsAuthPO po = new PtsAuthPO();
					po.setUserid(ptsAuth.getUserid());
					po.setGmtExpire(expireDate);
					boolean flag = ptsAuthPOMapper.updatePtsAuthPOByUserId(po) > 0 ;
					if(flag) {
						a++;
					}
				}
			}
		}
		return ResponseEntity.ok("成功："+a);
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
