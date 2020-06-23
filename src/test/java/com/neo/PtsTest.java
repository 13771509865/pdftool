package com.neo;

import com.neo.dao.PtsAuthPOMapper;
import com.neo.model.po.PtsAuthPO;
import com.neo.service.auth.IAuthService;
import com.neo.service.cache.impl.RedisCacheManager;
import com.neo.service.clear.IClearService;
import com.neo.service.order.impl.OrderManager;
import com.neo.service.update.impl.UpdateManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

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
	private UpdateManager updateManager;

//	@Test
//	public void test() {
//		ServiceAppUserRightDto serviceAppUserRightDto = new ServiceAppUserRightDto();
//		serviceAppUserRightDto.setApp(YozoServiceApp.PdfTools);
//		List<UserRightItem> rights = new ArrayList<>();
//		String[] specs = {"-1"};
//		String[] specs2 = {"1024"};
//		String[] specs3 = {"true"};
//		Long userid = 353109833127297025L;
//
//		UserRightItem userRightItem = new UserRightItem();
//		userRightItem.setId(41172L);
//		userRightItem.setPriority(10);
//		userRightItem.setUserId(userid);
//		userRightItem.setFeature("convert001Num");
//		userRightItem.setSpecs(specs);
//		userRightItem.setBegin(DateViewUtils.getNowDate());
//		userRightItem.setEnd(DateViewUtils.getNowDate());
//
//		UserRightItem u2 = new UserRightItem();
//		u2.setId(41173L);
//		u2.setPriority(10);
//		u2.setUserId(userid);
//		u2.setFeature("convert001Size");
//		u2.setSpecs(specs2);
//		u2.setBegin(DateViewUtils.getNowDate());
//		u2.setEnd(DateViewUtils.getNowDate());
//
//		UserRightItem u3 = new UserRightItem();
//		u3.setId(41173L);
//		u3.setPriority(10);
//		u3.setUserId(userid);
//		u3.setFeature("convert002");
//		u3.setSpecs(specs3);
//		u3.setBegin(DateViewUtils.getNowDate());
//		u3.setEnd(DateViewUtils.getNowDate());
//
//		UserRightItem u4 = new UserRightItem();
//		u4.setId(41174L);
//		u4.setPriority(10);
//		u4.setUserId(userid);
//		u4.setFeature("uploadSize");
//		u4.setSpecs(specs2);
//		u4.setBegin(DateViewUtils.getNowDate());
//		u4.setEnd(DateViewUtils.getNowDate());
//
//		rights.add(userRightItem);
//		rights.add(u2);
//		rights.add(u3);
//		rights.add(u4);
//
//		serviceAppUserRightDto.setRights(rights);
//
//		updateManager.updatePermissions(serviceAppUserRightDto,userid);
//
//
//	}


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



//		@Test
//	    public void contextLoads() {
//			String[] a = {"true"};
//			String[] c = {"100"};
//			String[] b = {"1","Month"};
//			Map<String, String[]> specs = new HashMap<>();
//			specs.put(EnumAuthCode.PTS_CONVERT_NUM.getAuthCode(), c);
//			specs.put(EnumAuthCode.PTS_UPLOAD_SIZE.getAuthCode(), c);
//			specs.put(EnumAuthCode.PTS_VALIDITY_TIME.getAuthCode(), b);
//			specs.put(EnumAuthCode.PDF_WORD.getAuthCode(), a);
//			specs.put(EnumAuthCode.EXCEL_PDF.getAuthCode(), a);
//			specs.put(EnumAuthCode.PDF_EXCEL.getAuthCode(), a);
//			specs.put(EnumAuthCode.WORD_PDF.getAuthCode(), a);
//			
//			OrderServiceAppSpec orderServiceAppSpec = new OrderServiceAppSpec();
//			orderServiceAppSpec.setApp(YozoServiceApp.PdfTools);
//			orderServiceAppSpec.setSpecs(specs);
//			
//			List<OrderServiceAppSpec> list = new  ArrayList<>();
//			list.add(orderServiceAppSpec);
//			
//			OrderRequestDto ord = new OrderRequestDto();
//			ord.setSpecs(list);
//			ord.setProductId("413985833427673089");
//			ord.setPriority(3);
//			ord.setUpgrade(false);
//			RedisOrderDto dto = new RedisOrderDto();
//			dto.setUserId(8506L);
//			dto.setOrderRequestDto(ord);
//			
//			
//			IResult<String> result = orderManager.modifyOrderEffective(dto);
//			System.out.println(result.isSuccess());
//			
//		}

	
//	@Test
//    public void test() {
//		List<PtsAuthPO> authList = new ArrayList<>();
//		for(int i = 0;i<3;i++) {
//			PtsAuthPO p = new PtsAuthPO();
//			p.setAuthCode("convert002");
//			p.setAuthValue("true");
//			p.setGmtCreate(DateViewUtils.getNowDate());
//			p.setGmtExpire(DateViewUtils.getNowDate());
//			p.setGmtModified(DateViewUtils.getNowDate());
//			p.setOrderId(null);
//			p.setPriority(i);
//			p.setStatus(1);
//			p.setUserid(121L);
//			authList.add(p);
//		}
//		ptsAuthPOMapper.insertPtsAuthPO(authList);
//	}



		@Test
	    public void updatePtsAuthPOByPriority() {
			List<PtsAuthPO> authList = new ArrayList<>();
//			for(int i = 0;i<5;i++){
//				PtsAuthPO ptsAuthPO = new PtsAuthPO();
//				ptsAuthPO.setAuthCode("convert001Num");
//				ptsAuthPO.setAuthValue("-1");
//				ptsAuthPO.setGmtCreate(DateViewUtils.getNowDate());
//				ptsAuthPO.setGmtModified(DateViewUtils.getNowDate());
//				ptsAuthPO.setGmtExpire(DateViewUtils.getNowDate());
//				ptsAuthPO.setOrderId(4589467894L);
//				ptsAuthPO.setPriority(null);
//				ptsAuthPO.setStatus(EnumStatus.ENABLE.getValue());
//				ptsAuthPO.setUserid(12345L);
//				authList.add(ptsAuthPO);
//			}

			Boolean insertPtsAuthPO = iAuthService.insertPtsAuthPO(authList)>0;
		}


}
