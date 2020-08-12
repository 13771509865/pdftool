package com.neo.model.qo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FcsFileInfoQO extends PageQO{
	
	private Integer id;
	private String ipAddress;
	private String fileHash;
	private Long userID;
	private Date showDateStart;
    private Date showDateEnd;
    private Integer status;
    private Integer isRPT;
	


}
