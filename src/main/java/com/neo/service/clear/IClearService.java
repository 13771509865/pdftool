package com.neo.service.clear;

import com.neo.commons.cons.IResult;
import com.neo.model.dto.UserClearRequestDto;

public interface IClearService {
	
	IResult<Integer> checkClearSign(String nonce, String sign);

	IResult<Integer> clearUserData(UserClearRequestDto userClearRequestDto);
}
