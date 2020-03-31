package com.neo.commons.cons.entity;

import com.neo.commons.cons.UnitType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderSpecsEntity {

	
	private String auth;
	
	private Integer validityTime;
	
	private UnitType unitType;
	
	
	
}
