package com.neo.service.statistics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neo.commons.cons.EnumAuthCode;
import com.neo.commons.cons.EnumMemberType;
import com.neo.commons.cons.constants.SysConstant;
import com.neo.commons.properties.ConfigProperty;
import com.neo.commons.util.StrUtils;

@Service("staticsManager")
public class StaticsManager {

	@Autowired
	private ConfigProperty configProperty;


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





	public static void main(String[] args) {
		//		String a = "1,2";
		//		List<String> list = new ArrayList<>();
		//		String[] convertCode = a.split(SysConstant.COMMA);
		//		for(String code : convertCode) {
		//			String module = EnumAuthCode.getModule(Integer.valueOf(code));
		//			list.add(module);
		//		}
		//		System.out.println(list.toString());


		Map<String, Object> map = new HashMap<String, Object>();
		map.put("aaa", 111);
		map.put("bbb", 222);
		map.put("ccc", 333);
		map.put("ddd", 444);
		Iterator<Entry<String, Object>> it = map.entrySet().iterator();
		while(it.hasNext()){
			Entry<String, Object> entry = it.next();
			System.out.println(entry.getKey()+","+entry.getValue());

		}

	}



}
