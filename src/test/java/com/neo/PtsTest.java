package com.neo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.neo.commons.cons.EnumAuthCode;
import com.neo.commons.cons.IResult;
import com.neo.commons.util.DateViewUtils;
import com.neo.dao.PtsAuthPOMapper;
import com.neo.model.dto.RedisOrderDto;
import com.neo.model.po.PtsAuthPO;
import com.neo.service.auth.IAuthService;
import com.neo.service.cache.impl.RedisCacheManager;
import com.neo.service.order.impl.OrderManager;
import com.yozosoft.api.order.dto.OrderRequestDto;
import com.yozosoft.api.order.dto.OrderServiceAppSpec;
import com.yozosoft.saas.YozoServiceApp;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PtsTest {

	@Autowired
	private OrderManager orderManager;

	@Autowired
	private RedisCacheManager<String> redisCacheManager;

	@Autowired
	private IAuthService iAuthService;

	@Autowired
	private PtsAuthPOMapper ptsAuthPOMapper;



//	@Test
//	public void test() {
//		List<PtsAuthPO> listWrong = ptsAuthPOMapper.selectAuthWrong();
//		for(PtsAuthPO ptsAuth : listWrong) {
//			if(ptsAuth.getId() < 206) {
//				List<String> infoList = ptsAuthPOMapper.selectInfoByUserId(ptsAuth.getUserid());
//				if(infoList.size() == 1) {
//					int date = Integer.valueOf(StringUtils.substring(infoList.get(0), 4, 5));
//					Date expireDate = DateViewUtils.stepMonth(ptsAuth.getGmtCreate(),date,5);
//					PtsAuthPO po = new PtsAuthPO();
//					po.setUserid(ptsAuth.getUserid());
//					po.setGmtExpire(expireDate);
//					ptsAuthPOMapper.updatePtsAuthPOByUserId(po);
//				}
//			}
//		}
//	}



	//	@Test
	//    public void contextLoads() {
	//		String[] a = {"true"};
	//		String[] c = {"100"};
	//		Map<String, String[]> specs = new HashMap<>();
	//		specs.put(EnumAuthCode.PTS_CONVERT_NUM.getAuthCode(), c);
	//		specs.put(EnumAuthCode.PTS_UPLOAD_SIZE.getAuthCode(), c);
	//		specs.put(EnumAuthCode.PTS_VALIDITY_TIME.getAuthCode(), c);
	//		specs.put(EnumAuthCode.PDF_WORD.getAuthCode(), a);
	//		specs.put(EnumAuthCode.EXCEL_PDF.getAuthCode(), a);
	//		specs.put(EnumAuthCode.PDF_EXCEL.getAuthCode(), a);
	//		specs.put(EnumAuthCode.WORD_PDF.getAuthCode(), a);
	//		
	//		OrderServiceAppSpec orderServiceAppSpec = new OrderServiceAppSpec();
	//		orderServiceAppSpec.setApp(YozoServiceApp.PdfTools);
	//		orderServiceAppSpec.setSpecs(specs);
	//		
	//		List<OrderServiceAppSpec> list = new  ArrayList<>();
	//		list.add(orderServiceAppSpec);
	//		
	//		OrderRequestDto ord = new OrderRequestDto();
	//		ord.setSpecs(list);
	//		
	//		RedisOrderDto dto = new RedisOrderDto();
	//		dto.setUserId(502L);
	//		dto.setOrderRequestDto(ord);
	//		
	//		IResult<String> result = orderManager.modifyOrderEffective(dto);
	//		System.out.println(result.isSuccess());
	//		
	//	}






}
