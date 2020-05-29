package com.neo.commons.util;

import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.druid.pool.vendor.SybaseExceptionSorter;
import com.neo.commons.cons.EnumResultCode;
import com.neo.commons.cons.constants.PtsConsts;
import com.neo.commons.cons.entity.ModuleEntity;

/**
 * 功能描述 加密常用类
 */
public class EncryptUtils {
	// 密钥是16位长度的byte[]进行Base64转换后得到的字符串
	public static String key = "eW96b2Rjc2lzdGhlYmVzdA==";

	//前后端规定隔离符号
	public static String separator = "###";


	/**
	 * DES加密操作
	 * @param source 要加密的源
	 * @return
	 */
	public static String encryptDES(String source){
		if(StringUtils.isNotBlank(source)) {

			//强加密随机数生成器
			SecureRandom random = new SecureRandom();
			try {
				//创建密钥规则
				DESKeySpec keySpec = new DESKeySpec(key.getBytes());
				//创建密钥工厂
				SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
				//按照密钥规则生成密钥
				SecretKey secretKey =  keyFactory.generateSecret(keySpec);
				//加密对象
				Cipher cipher = Cipher.getInstance("DES");
				//初始化加密对象需要的属性
				cipher.init(Cipher.ENCRYPT_MODE, secretKey, random);
				//开始加密
				byte[] result = cipher.doFinal(source.getBytes());
				//Base64加密
				return  Base64.getEncoder().encodeToString(result) ;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}



	/**
	 * 解密
	 * @param cryptograph 密文
	 * @return
	 */
	public static String decryptDES(String cryptograph){
		if(StringUtils.isNotBlank(cryptograph)) {
			//强加密随机生成器
			SecureRandom random  =  new SecureRandom();
			try {
				//定义私钥规则
				DESKeySpec keySpec = new DESKeySpec(key.getBytes());
				//定义密钥工厂
				SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
				//按照密钥规则生成密钥
				SecretKey secretkey = factory.generateSecret(keySpec);
				//创建加密对象
				Cipher cipher = Cipher.getInstance("DES");
				cipher.init(Cipher.DECRYPT_MODE, secretkey, random);
				//Base64对
				byte[] result = Base64.getDecoder().decode(cryptograph);
				return new String(cipher.doFinal(result));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}


	/**
	 * 解密前端module参数
	 * @param parameter
	 * @return
	 */
	public static ModuleEntity decryptModule(String parameter) {
		try {
			ModuleEntity moduleEntity = new ModuleEntity();
			String moduleStr = decryptDES(parameter);
			if(StringUtils.isBlank(moduleStr)) {
				return null;
			}
			Integer module = Integer.valueOf(StringUtils.substringBefore(moduleStr, separator));
			String timeStamp =StringUtils.substringAfter(moduleStr, separator);
			moduleEntity.setModule(module);
			moduleEntity.setTimeStamp(timeStamp);
			return moduleEntity;
		} catch (Exception e) {
			SysLogUtils.error("解密前端module失败，内容："+parameter);
			e.printStackTrace();
			return null;
		}
	}



	public static void main(String[] args) throws Exception {
		
		String c = "PdCq16BIhjc=";
		String cc = "FhqV6o9j+cqC3rKo5zaAgMwPMMcj+c4M";
		System.out.println(EncryptUtils.decryptDES(c));	
		System.out.println(EncryptUtils.decryptDES(cc));

//		String b = "14###fr8r5"+System.currentTimeMillis();
//		String a = EncryptUtils.encryptDES(b);
//		System.out.println(a);
//		System.out.println(EncryptUtils.decryptDES(a));	
	}
}