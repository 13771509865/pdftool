package com.neo.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.neo.service.UserService;

public class MyContextListtener implements ServletContextListener{
	@Override
	public void contextInitialized(ServletContextEvent event)
	{
		ServletContext application = event.getServletContext();  
        WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(application);  
        UserService userService = (UserService) wac.getBean("userService");
        //检查数据库中是否存在 超级管理员，不存在则添加，存在则修改 为初始密码
        userService.checkAdmin();
        //启动的时候查出所有用户，转换量，然后存入application，
	}
    @Override
    public void contextDestroyed(ServletContextEvent arg0)
    {
        System.out.println("感谢使用本系统！！^-^");
        
    }
}
