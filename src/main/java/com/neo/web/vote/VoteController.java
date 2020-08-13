package com.neo.web.vote;

import com.neo.commons.cons.EnumResultCode;
import com.neo.commons.cons.IResult;
import com.neo.commons.util.HttpUtils;
import com.neo.commons.util.JsonResultUtils;
import com.neo.dao.PtsAuthPOMapper;
import com.neo.service.vote.IVoteService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Api(value = "投票相关Controller", tags = {"投票相关Controller"})
@Controller
@RequestMapping(value = "/vote")
public class VoteController {

	
	
	@Autowired
	private IVoteService iVoteService;
	
	@Autowired
	private PtsAuthPOMapper ptsAuthPOMapper;
	
	
	
	@ApiOperation(value = "pdf工具集投票")
	@ApiImplicitParams({
			@ApiImplicitParam(name="vote" ,value="投票内容" ,required=false ,dataType="String",paramType="query"),
			@ApiImplicitParam(name="otherContent" ,value="其他投票内容" ,required=false ,dataType="string",paramType="query")})
	@ApiResponses({
			@ApiResponse(code=200 ,response= Map.class, message="固定返回模型，json字符串表现形式,data:主要字段内容，code：返回结果码，message：返回结果信息")})
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
