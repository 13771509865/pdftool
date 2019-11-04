package com.neo.commons.util;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class StrUtils {
    public static String getEncoding(String str) {
        //UTF-8包含绝大多数字符，所以放最后判断
        String[] encodes = {"ISO-8859-1", "GBK", "UTF-8", "GB2312"};
        for (String encode : encodes) {
            try {
                if (str.equals(new String(str.getBytes(encode), encode))) {
                    return encode;
                }
            } catch (UnsupportedEncodingException e) {
            }
        }
        return null;
    }

    public static String TranEncode2CN(String str) {
        String strEncode = getEncoding(str);
        String value = str;
        if (strEncode != null && "ISO-8859-1".equals(strEncode)) {
            try {
                value = new String(str.getBytes(strEncode), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return value;
    }

    //=?utf-8?B?开头utf-8表示编码,B表示base64,?=结尾
    public static String TranEmilHeader(String str) {
        if (str.startsWith("=?utf-8?B?")) {
            String temp = str.substring(10);
            temp = temp.lastIndexOf("?=") > -1 ? temp.substring(0, temp.lastIndexOf("?=")) : temp;
            String filename = new String(Base64.getDecoder().decode(temp));
            //厦门航空的获取文件名操作
            //String filename = MimeUtility.decodeText(str);
            return filename;
        } else {
            return str;
        }
    }
    
    
    
    /**
     * 将字符串拆分成map
     * @param data
     * @param splitStr
     * @param splitMap
     * @return
     */
	public static Map<String,Object> strToMap(String data ,String splitStr,String splitMap) {
        Map<String,Object> map = new HashMap<String,Object>();
        if (null != data) {
            String[] param = data.split(splitStr);
            for (int i = 0; i < param.length; i++) {
                int index = param[i].indexOf(splitMap);
                map.put(param[i].substring(0,index), param[i].substring((index + 1)));
            }
        }
        return map;
	}
    
    
    
    
    
    

    public static void main(String[] args) {
        String a = "convert001:true,convert002:true,convert003:true,convert004:true,convert005:true,";
        Map<String,Object> map = strToMap(a,",",":");
        System.out.println(map.toString());

    }
}
