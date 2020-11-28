package com.neo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@ServletComponentScan
@EnableScheduling //允许schedul定时任务
@MapperScan("com.neo.dao")
//@EnableCaching
//@EnableTransactionManagement //开启事务注解支持
public class YozoPtsApplication extends SpringBootServletInitializer{
	
	 @Override
	    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
	        return builder.sources(YozoPtsApplication.class);
	    }

	    public static void main(String[] args) {
	        SpringApplication.run(YozoPtsApplication.class, args);
	    }

}
