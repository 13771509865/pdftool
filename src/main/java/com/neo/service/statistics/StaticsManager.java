package com.neo.service.statistics;

import com.neo.commons.cons.DefaultResult;
import com.neo.commons.cons.EnumAuthCode;
import com.neo.commons.cons.EnumMemberType;
import com.neo.commons.cons.IResult;
import com.neo.commons.cons.constants.SysConstant;
import com.neo.commons.properties.ConfigProperty;
import com.neo.commons.util.DateViewUtils;
import com.neo.commons.util.StrUtils;
import com.neo.commons.util.SysLogUtils;
import com.neo.model.po.PtsConvertRecordPO;
import com.neo.service.auth.impl.AuthManager;
import com.neo.service.convertRecord.IConvertRecordService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Service("staticsManager")
public class StaticsManager {

	@Autowired
	private ConfigProperty configProperty;

	@Autowired
	private AuthManager authManager;

//	@Autowired
//	private OldAuthManager oldAuthManager;

	@Autowired
	private IConvertRecordService iConvertRecordService;



	/**
	 * 获取用户允许使用的模块
	 * @param memberValue
	 * @param authCode
	 * @return
	 */
	public List<String> getModules(Integer memberValue,String authCode) {
		List<String> list = new ArrayList<>();

		//会员允许使用的模块
		if(memberValue != EnumMemberType.VISITOR.getValue() && memberValue != EnumMemberType.MEMBER.getValue()) {
			Map<String,Object> map = StrUtils.strToMap(authCode, SysConstant.COMMA, SysConstant.COLON);
			Iterator<Entry<String, Object>> it = map.entrySet().iterator();
			while(it.hasNext()){
				Entry<String, Object> entry = it.next();
				//必须是true
				if(StringUtils.equals(String.valueOf(entry.getValue()), SysConstant.TRUE)) {
					String module  = EnumAuthCode.getModule(entry.getKey());
					list.add(module);
				}
			}
			return list;
		}

		//注册用户允许使用的模块
		if(memberValue == EnumMemberType.MEMBER.getValue()) {
			String[] convertCode = configProperty.getConvertModule().split(SysConstant.COMMA);
			for(String code : convertCode) {
				String module = EnumAuthCode.getModule(Integer.valueOf(code));
				list.add(module);
			}
			return list;
		}
		return list;
	}


	/**
	 * 获取用户剩余权限
	 * @param userID
	 * @return
	 */
	public IResult<Map<String,Object>> getAuth(Long userID){
		try {

			//获取所有的用户权限
			IResult<Map<String,Object>> getPermissionResult = authManager.getPermission(userID,null);
			Map<String,Object> map = getPermissionResult.getData();

			PtsConvertRecordPO ptsConvertRecordPO = new PtsConvertRecordPO();
			ptsConvertRecordPO.setUserID(userID);
			String nowDate = DateViewUtils.getNow();
			ptsConvertRecordPO.setModifiedDate(DateViewUtils.parseSimple(nowDate));

			//查询当天的转换记录
			List<PtsConvertRecordPO> recordList = iConvertRecordService.selectPtsConvertRecord(ptsConvertRecordPO);

			if(!recordList.isEmpty() && recordList.size()>0) {
				for(PtsConvertRecordPO po : recordList) {
					Integer convertNum = po.getConvertNum();//转了多少次

					//module==0,跳出
					if(po.getModule() == EnumAuthCode.PTS_CONVERT_NUM.getValue()){
						continue;
					}
					String moduleNum = EnumAuthCode.getModuleNum(po.getModule());

					Integer allowConvertNum = Integer.valueOf(map.get(moduleNum).toString());//允许转多少次
					if(allowConvertNum != -1) {
						Integer newNum = allowConvertNum - convertNum;//剩余多少次
						map.put(moduleNum, newNum);
					}
				}
			}
			return DefaultResult.successResult(map);
		} catch (Exception e) {
			e.printStackTrace();
			SysLogUtils.error("查询每天的转换记录和文件大小失败，原因：", e);
			return DefaultResult.failResult("查询每日转换记录和文件大小失败");
		}
	}


	public static void main(String[] args) {

	}



}
