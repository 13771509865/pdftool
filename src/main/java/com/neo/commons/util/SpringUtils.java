package com.neo.commons.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author zhoufeng
 * @description spring工具类,通过上下文获取bean
 * @create 2019-03-12 14:37
 **/
@Component
public class SpringUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(SpringUtils.applicationContext == null){
            SpringUtils.applicationContext = applicationContext;
        }
        System.out.println("ApplicationContext配置成功");
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
    * @description 获取运行时contextpath
    * @author zhoufeng
    * @date 2019/4/28
    */
    public static String getApplicationName(){
        return getApplicationContext().getApplicationName();
    }

    public static Object getBean(String name) {
        try{
            return getApplicationContext().getBean(name);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T getBean(Class<T> clazz) {
        try{
            return getApplicationContext().getBean(clazz);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static Object getBeanByClassPath(String clazz){
        try{
            Class<?> tempClass = Class.forName(clazz);
            return getBean(tempClass);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T getBean(String name,Class<T> clazz) {
        try{
            return getApplicationContext().getBean(name,clazz);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
