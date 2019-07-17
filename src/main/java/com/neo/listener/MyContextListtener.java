package com.neo.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


public class MyContextListtener implements ServletContextListener{
	@Override
	public void contextInitialized(ServletContextEvent event){
		
	}
    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        System.out.println("感谢使用本系统！！^-^");
        
    }
}
