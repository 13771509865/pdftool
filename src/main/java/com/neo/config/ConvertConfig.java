package com.neo.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.neo.commons.cons.constants.ConvertConsts;
import com.neo.commons.util.SysLog4JUtils;

import java.io.File;

/**
 * @description csc配置类
 * @author: zhoufeng
 * @create: 2019-01-24 17:20
 **/

@Service
@PropertySource(value={"classpath:convertConfig.properties"},ignoreResourceNotFound=true)
/**
 * @author zhoufeng
 *
 */
public class ConvertConfig {
    //初始代码块
    {
        try{
            ClassPathResource resource = new ClassPathResource(ConvertConsts.PDF2WORDJARPATH+ConvertConsts.PDF2WORDJARNAME);
            File file = resource.getFile();
            p2wJarPath = file.getAbsolutePath();
        }catch (Exception e){
            System.out.println("pdf2word转换jar包不存在!");
            SysLog4JUtils.error("pdf2word转换jar包不存在",e);
        }
        try{
            ClassPathResource resource = new ClassPathResource(ConvertConsts.DCCJARPATH+ConvertConsts.DCCJARNAME);
            File file = resource.getFile();
            dccJarPath = file.getAbsolutePath();
            dccJarParentPath = file.getParentFile().getAbsolutePath();
        }catch (Exception e){
            System.out.println("dcc转换jar包不存在!");
            SysLog4JUtils.error("dcc转换jar包不存在",e);
        }
    }

    @Value(value="${dccConfig}")
    private String dccConfig;

    @Value(value="${pdf2WordExt}")
    private String pdf2WordExt;

    @Value(value="${pdf2WordConfig}")
    private String pdf2WordConfig;
    
    @Value(value="${javaPath}")
    private String javaPath;

    private String p2wJarPath;

    private String dccJarPath;

    private String dccJarParentPath;
    
    public String getDccConfig() {
        return dccConfig;
    }

    public void setDccConfig(String dccConfig) {
        this.dccConfig = dccConfig;
    }

    public String getPdf2WordExt() {
        return pdf2WordExt;
    }

    public void setPdf2WordExt(String pdf2WordExt) {
        this.pdf2WordExt = pdf2WordExt;
    }

    public String getPdf2WordConfig() {
        return pdf2WordConfig;
    }

    public void setPdf2WordConfig(String pdf2WordConfig) {
        this.pdf2WordConfig = pdf2WordConfig;
    }

    public String getP2wJarPath() {
        return p2wJarPath;
    }

    public void setP2wJarPath(String p2wJarPath) {
        this.p2wJarPath = p2wJarPath;
    }

    public String getDccJarPath() {
        return dccJarPath;
    }

    public void setDccJarPath(String dccJarPath) {
        this.dccJarPath = dccJarPath;
    }

    public String getDccJarParentPath() {
        return dccJarParentPath;
    }

    public void setDccJarParentPath(String dccJarParentPath) {
        this.dccJarParentPath = dccJarParentPath;
    }

    public String getJavaPath() {
        return javaPath;
    }

    public void setJavaPath(String javaPath) {
        this.javaPath = javaPath;
    }
	
	public boolean checkJar(String useWhichJar) {
        if (useWhichJar != null) {
            switch (useWhichJar) {
            case ConvertConsts.DCCJAR:
            	if(dccJarPath!=null) {
            		return true;
            		}
                case ConvertConsts.PDF2WORDJAR:
                	if(p2wJarPath!=null) {
                		return true;
                		}
                default:
                    return false;
            }
        }
		return false;		
	}
}
