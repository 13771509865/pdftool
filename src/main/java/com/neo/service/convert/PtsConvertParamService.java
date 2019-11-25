package com.neo.service.convert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neo.commons.cons.constants.FcsParmConsts;
import com.neo.commons.cons.constants.PtsConsts;
import com.neo.commons.cons.constants.SizeConsts;
import com.neo.commons.properties.ConfigProperty;
import com.neo.commons.properties.PtsProperty;
import com.neo.commons.util.CheckMobileUtils;
import com.neo.commons.util.DateViewUtils;
import com.neo.commons.util.HttpUtils;
import com.neo.commons.util.JsonUtils;
import com.neo.model.bo.ConvertParameterBO;
import com.neo.model.bo.FcsFileInfoBO;
import com.neo.model.po.ConvertParameterPO;
import com.neo.model.po.FcsFileInfoPO;
import com.neo.model.po.PtsSummaryPO;


@Service("ptsConvertParamService")
public class PtsConvertParamService {

	@Autowired
	private ConfigProperty configProperty;

	@Autowired
	private PtsProperty ptsProperty;


	/**
	 * 构建传递给fcs的参数条件
	 * @param convertPO
	 * @return
	 */
	public Map<String, Object> buildFcsMapParamPO(ConvertParameterPO convertPO){
		Map<String, Object> convertParamMap = JsonUtils.parseJSON2Map(convertPO.toString());
		Map<String, Object> fcsParamMap = new HashMap<>();
		fcsParamMap.putAll(convertParamMap);
		return fcsParamMap;
	}


	/**
	 * 构建ConvertParameterPO对象
	 * @param convertBO
	 */
	public ConvertParameterPO buildConvertParameterPO(ConvertParameterBO convertBO) {
		if(convertBO.getConvertTimeOut() == null) {
			convertBO.setConvertTimeOut(configProperty.getConvertTimeout());
		} 
		Map<String,Object> map = JsonUtils.parseJSON2Map(convertBO.toString());
		for(String param : FcsParmConsts.FCS_PARMS) {
			if(map.containsKey(param)) {
				List<String> list = (List<String>)map.get(param);
				if(!list.isEmpty() && list !=null) {
					map.put(param, StringUtils.join(list, ","));
				}
			}
		}
		return JsonUtils.map2obj(map, ConvertParameterPO.class);
	}



	/**
	 * 构建FcsFileInfoPO对象，用于insert
	 * @param fcsFileInfoBO
	 * @param request
	 * @return
	 */
	public FcsFileInfoPO buildFcsFileInfoParameter(ConvertParameterBO convertBO,FcsFileInfoBO fcsFileInfoBO,Long userId,String ipAddress) {
		FcsFileInfoPO fcsFileInfoPO = new FcsFileInfoPO();
		fcsFileInfoPO.setIpAddress(ipAddress);
		fcsFileInfoPO.setUserID(userId);
		fcsFileInfoPO.setFileHash(fcsFileInfoBO.getFileHash());
		fcsFileInfoPO.setResultCode(fcsFileInfoBO.getCode());
		fcsFileInfoPO.setSrcFileName(fcsFileInfoBO.getSrcFileName());
		fcsFileInfoPO.setDestFileSize(fcsFileInfoBO.getDestFileSize());
		fcsFileInfoPO.setSrcFileSize(fcsFileInfoBO.getSrcFileSize());
		fcsFileInfoPO.setConvertType(fcsFileInfoBO.getConvertType());
		fcsFileInfoPO.setSrcStoragePath(fcsFileInfoBO.getSrcStoragePath());
		fcsFileInfoPO.setDestStoragePath(fcsFileInfoBO.getDestStoragePath());

		//手写签批，做特殊处理DestFileName，需要保存上传的源文件
		//viewUrl需要修改成download
		if(convertBO.getConvertType() == 14 && convertBO.getIsSignature() ==1) {
			fcsFileInfoPO.setDestFileName(fcsFileInfoBO.getSrcFileName());
			String token = StringUtils.substringAfter(fcsFileInfoBO.getViewUrl(),PtsConsts.PREVIEW);
			fcsFileInfoPO.setViewUrl(ptsProperty.getFcs_downLoad_url()+token);
		}else {
			fcsFileInfoPO.setDestFileName(fcsFileInfoBO.getDestFileName());
			fcsFileInfoPO.setViewUrl(fcsFileInfoBO.getViewUrl());
		}

		return fcsFileInfoPO;
	}


	/**
	 * 构建PtsSummaryPO对象，用于数据统计
	 * @param fcsFileInfoBO
	 * @param request
	 * @return
	 */
	public PtsSummaryPO buildPtsSummaryPOParameter(FcsFileInfoBO fcsFileInfoBO,ConvertParameterBO convertBO,HttpServletRequest request) {
		PtsSummaryPO ptsSummaryPO = new PtsSummaryPO();
		Long srcFileSize = convertBO.getSrcFileSize();
		if(srcFileSize >= 0 && srcFileSize <=SizeConsts.PTS_TWO_SIZE) {
			ptsSummaryPO.setZeroToTwo(1);
		}
		if(srcFileSize >SizeConsts.PTS_TWO_SIZE  && srcFileSize <=SizeConsts.PTS_FIVE_SIZE) {
			ptsSummaryPO.setTwoToFive(1);
		}
		if(srcFileSize >SizeConsts.PTS_FIVE_SIZE  && srcFileSize <=SizeConsts.PTS_TEN_SIZE) {
			ptsSummaryPO.setFiveToTen(1);
		}
		if(srcFileSize >SizeConsts.PTS_TEN_SIZE  && srcFileSize <=SizeConsts.PTS_FIFTEEN_SIZE) {
			ptsSummaryPO.setTenToFifteen(1);
		}
		if(srcFileSize >SizeConsts.PTS_FIFTEEN_SIZE  && srcFileSize <=SizeConsts.PTS_TWENTY_SIZE) {
			ptsSummaryPO.setFifteenToTwenty(1);
		}
		if(srcFileSize >SizeConsts.PTS_TWENTY_SIZE  && srcFileSize <=SizeConsts.PTS_THIRTY_SIZE) {
			ptsSummaryPO.setTwentyToThirty(1);
		}
		if(srcFileSize >SizeConsts.PTS_THIRTY_SIZE  && srcFileSize <=SizeConsts.PTS_FOURTY_SIZE) {
			ptsSummaryPO.setThirtyToFourty(1);
		}
		if(srcFileSize >SizeConsts.PTS_FOURTY_SIZE  && srcFileSize <=SizeConsts.PTS_FIFTY_SIZE) {
			ptsSummaryPO.setFourtyToFifty(1);
		}
		if(srcFileSize >SizeConsts.PTS_FIFTY_SIZE  ) {
			ptsSummaryPO.setFiftyMore(1);
		}

		if(fcsFileInfoBO.getCode() == 0) {//判断转换是否成功
			ptsSummaryPO.setIsSuccess(0);
		}else {
			ptsSummaryPO.setIsSuccess(1);
		}

		if(CheckMobileUtils.checkIsMobile(request)) {//判断是手机端，还是PC端
			ptsSummaryPO.setAppType(0);
		}else {
			ptsSummaryPO.setAppType(1);
		}


		String nowDate = DateViewUtils.getNow();
		String nowTime = DateViewUtils.getNowTime();


		Long userId = HttpUtils.getSessionUserID(request);
		if(userId ==null) {//如果登录ip_address就记录userId，没有登录就记录ip地址
			ptsSummaryPO.setIpAddress(HttpUtils.getIpAddr(request));
		}else {
			ptsSummaryPO.setIpAddress(String.valueOf(userId));
		}

		ptsSummaryPO.setModule(Integer.valueOf(request.getParameter(PtsConsts.MODULE)));//区分模块

		ptsSummaryPO.setCreateDate(DateViewUtils.parseSimple(nowDate));//时间搞一搞
		ptsSummaryPO.setCreateTime(DateViewUtils.parseSimpleTime(nowTime));
		ptsSummaryPO.setModifiedDate(DateViewUtils.parseSimple(nowDate));
		ptsSummaryPO.setModifiedTime(DateViewUtils.parseSimpleTime(nowTime));

		return ptsSummaryPO;
	}



	public static void main(String[] args) {
		String viewUrl= "https://pdl.yozocloud.cn/view/preview/k1-hrvUr7bTpBr5jnICu7K_onAql9n_xFKkHnwKyCauJNGvsoax6iL7hM7ivxXubTYc72pa0P6-qIOZQXwGm344FakTjd93Lz4r77DI_H_2kHYa5qxmBJSoDp4Z0wLiZYQH0ms-ucI3mDwvSpwkBe0H-Izmjd4yYl2iov6wDdJk3pvrns0vOx5W1PcP6ZHVBs5Z6Od_RVp5l3q7oz09z9S7E07CIHBS5BYeGrfxsIpz_tJrYZxfeex5_N4Vrl5xlJKGaaevyvDIvzCchdWnD9zNPrjUt0jUfzKyrHr6QTMonLLtQaCzVnzYsdDiVTUMRzxuYQMhErfI=/";
		String a = "preview";
		String aa = StringUtils.substringAfter(viewUrl,a);
		System.out.println(aa);
	}
}
