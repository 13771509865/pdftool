package com.neo.service;

import com.neo.model.bo.ConvertParameterBO;

public interface IMqConvertManager {
	public void Producer(ConvertParameterBO convertBO);
	
	public void Consumer();
}
