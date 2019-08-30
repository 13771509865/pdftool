package com.neo.commons.util;

import java.util.regex.Pattern;

public class Validator {
    public static Validator USERNAME;
    public static Validator PASSWORD;
    public static Validator REGISTER_PASSWORD;
    public static Validator MOBILE;
    public static Validator EMAIL;
    public static Validator CHINESE;
    public static Validator ID_CARD;
    public static Validator URL;
    public static Validator IP_ADDR;
    public static Validator BIRTHDAY;
    public static Validator NICKNAME;
    public static Validator NUMBER;
    public static Validator CORP_ACCOUNT;
    public static Validator IMAGE_FORMAT;

    private Pattern regPattern;

    static {
        USERNAME = new Validator("^[a-zA-Z]\\w{5,17}$");
        PASSWORD = new Validator("^[a-zA-Z0-9]{8,16}$");
        REGISTER_PASSWORD =
            new Validator("^(?=.*\\d)(?=.*[a-zA-Z])[\\da-zA-Z!@`#\\$￥%,\\(\\)\\.><=……&*\\-_;:\\+/]{6,20}$");
        MOBILE = new Validator("^(1[3-9])\\d{9}$");
        EMAIL = new Validator("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
        CHINESE = new Validator("^[\u4e00-\u9fa5],{0,}$");
        ID_CARD = new Validator("(^\\d{18}$)|(^\\d{15}$)");
        URL = new Validator("http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?");
        IP_ADDR = new Validator("(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)");
        BIRTHDAY = new Validator("\\d{4}-\\d{1,2}-\\d{1,2}");
        NICKNAME = new Validator("^.{1,20}$");
        NUMBER = new Validator("[0-9]+");
        CORP_ACCOUNT = new Validator("^\\w{1,12}@[a-zA-Z]\\w{1,22}.yozocloud.cn$");
        IMAGE_FORMAT = new Validator("/\\w(\\.gif|\\.jpeg|\\.png|\\.jpg|\\.bmp|\\.tif)/i");
    }

    public Validator(String pattern) {
        this.regPattern = Pattern.compile(pattern);
    }

    public boolean match(String content) {
        return this.regPattern.matcher(content).matches();
    }
}
