package com.neo.service.clear;

import com.neo.commons.cons.IResult;

public interface IClearService {
	
	IResult<Integer> checkClearSign(String nonce, String sign);

	IResult<Integer> clearUserData(String id);
}
