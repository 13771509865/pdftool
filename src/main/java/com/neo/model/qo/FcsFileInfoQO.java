package com.neo.model.qo;

import java.util.Date;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FcsFileInfoQO  extends PageQO{
	
	private Integer id;
	private String ipaddress;
	private Long userID;
	private String fileHash;
	private Integer resultCode;
	private String destFileName;
	private String srcFileName;
	private Long srcFileSize;
	private Long destFileSize;
	private Integer convertType;
	private String srcStoragePath;
	private String destStoragePath;
	private String viewUrl;
	private Integer isMobile;
	private Date gmtCreate;
    private Date gmtModified;
	


}
