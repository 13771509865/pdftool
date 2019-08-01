package com.neo.commons.cons.constants;

import java.util.Arrays;
import java.util.List;


/**
 * ConvertParameterPO对象中的String[]参数名
 * @author xujun
 * 2019-08-01
 */
public class FcsParmConsts {

	public static final String MERGE_INPUT = "mergeInput";

	public static final String BOOKMARK = "bookMark";
	
	public static final String SOURCE_REPLACE = "sourceReplace";
	
	public static final String SIGNATRUE_IMGPATH = "signatureImgPath";
	
	public static final String TARGET_REPALCE = "targetReplace";

	public static final List<String> FCS_PARMS = Arrays.asList(MERGE_INPUT, BOOKMARK, SOURCE_REPLACE,SIGNATRUE_IMGPATH,TARGET_REPALCE);


}
