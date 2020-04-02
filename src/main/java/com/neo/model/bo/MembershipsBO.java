package com.neo.model.bo;

import com.neo.model.dto.UserClearDto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 对应单点UserBO 中memberships参数
 * @author xujun
 * @description
 * @create 2020年3月25日
 */
@Data
@NoArgsConstructor
public class MembershipsBO {

	private String  membership;
	private String  duetime;
	private String  membershipLabel;
	
}
