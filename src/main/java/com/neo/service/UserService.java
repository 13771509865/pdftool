package com.neo.service;

import org.springframework.stereotype.Service;

@Service("userService")
public class UserService
{


    // 启动检查超级管理员初始化
    public void checkAdmin()
    {
        System.out.println("启动检查超级管理员初始化");
    }
}


