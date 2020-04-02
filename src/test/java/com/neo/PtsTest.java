package com.neo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.neo.dao.PtsAuthPOMapper;
import com.neo.dao.PtsConvertRecordPOMapper;
import com.neo.model.po.PtsAuthPO;
import com.neo.service.auth.IAuthService;
import com.neo.service.cache.impl.RedisCacheManager;
import com.neo.service.clear.IClearService;
import com.neo.service.order.impl.OrderManager;

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
	
	@Autowired
	private IClearService iClearService;
	
	@Autowired
	private PtsConvertRecordPOMapper ptsConvertRecordPOMapper;
	
	@Test
	public void test() {
		PtsAuthPO ptsAuthPO =new PtsAuthPO();
		ptsAuthPO.setUserid(3678L);
		System.out.println(ptsAuthPOMapper.updatePtsAuthPOByUserId(ptsAuthPO));
	}
	
	
//	@Test
//	public void clearTest() throws InterruptedException {
//		String nowDate = DateViewUtils.getNow();
//		String nowTime = DateViewUtils.getNowTime();
//		 
//		PtsConvertRecordPO ptsConvertRecordPO = new PtsConvertRecordPO();
//		ptsConvertRecordPO.setConvertNum(1);
//		ptsConvertRecordPO.setCreateDate(DateViewUtils.getNowDate());//时间搞一搞
//		ptsConvertRecordPO.setCreateTime(DateViewUtils.getNowDate());
//		ptsConvertRecordPO.setModifiedDate(DateViewUtils.getNowDate());
//		ptsConvertRecordPO.setModifiedTime(DateViewUtils.getNowDate());
//		ptsConvertRecordPO.setModule(2);
//		ptsConvertRecordPO.setStatus(1);
//		ptsConvertRecordPO.setUserID(13L);
//		
//		PtsConvertRecordQO ptsConvertRecordQO = new PtsConvertRecordQO();
//		ptsConvertRecordQO.setConvertNum(5);
//		
//		int num = ptsConvertRecordPOMapper.insertOrUpdatePtsConvertRecord(ptsConvertRecordPO, ptsConvertRecordQO);
//		System.out.println(num);
//	}
	
	
//	@Test
//	public void clearTest() throws InterruptedException {
//		UserClearRequestDto userClearRequestDto = new UserClearRequestDto();
//		UserClearDto[] userClearDto = new UserClearDto[2];
//		userClearRequestDto.setId("423844151126130689");
//		UserClearDto userClearDto1 = new UserClearDto();
//		UserClearDto userClearDto2 = new UserClearDto();
//		userClearDto1.setId("423837533986619393");
//		userClearDto2.setId("423813009295540225");
//		userClearDto[0] = userClearDto1;
//		userClearDto[1] = userClearDto2;
//		
//		userClearRequestDto.setMembers(userClearDto);
//		iClearService.clearUserData(userClearRequestDto);
//	}
//	

//	@Test
//	public void test() throws InterruptedException {
//		while(true) {
//		A a = new A();
//		Thread t1 = new Thread(a);
//		Thread t2 = new Thread(a);
//			t1.start();
//			t2.start();
//			Thread.currentThread().sleep(1000);
//		}
//		
//	}

	class A implements Runnable{
		@Override
		public void run() {
			float a = 1;
			System.out.println(redisCacheManager.htbRateLimiter("test",5,a,1,System.currentTimeMillis()));
		}
	}
	
	




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
