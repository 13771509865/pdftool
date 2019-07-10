package com.neo.commons.cons.mail;

public class EmailTempletBind {
    private static final String Subject = "永中云转换邮箱激活";
    
    private String nickName;
    private String code;
	public EmailTempletBind(String nickName, String code) {
		super();
		this.nickName = nickName;
		this.code = code;
	}
    
	public String getSubject(){
		return Subject;
	}
    public String getTempBind(){
    	return "<div style=\"background-color:#ECECEC; padding: 35px;\">"+
    				  "<table cellpadding=\"0\" align=\"center\" style=\"width: 600px; margin: 0px auto; text-align: left; position: relative; border-top-left-radius: 5px; border-top-right-radius: 5px; border-bottom-right-radius: 5px; border-bottom-left-radius: 5px; font-size: 14px; font-family:微软雅黑, 黑体; line-height: 1.5; box-shadow: rgb(153, 153, 153) 0px 0px 5px; border-collapse: collapse; background-position: initial initial; background-repeat: initial initial;background:#fff;\">"+
    				  "<tbody><tr><th valign=\"middle\" style=\"height: 25px; line-height: 25px; padding: 15px 35px; border-bottom-width: 1px; border-bottom-style: solid; border-bottom-color: #5A88C8; background-color: #5A88C8; border-top-left-radius: 5px; border-top-right-radius: 5px; border-bottom-right-radius: 0px; border-bottom-left-radius: 0px;\">"+
    				  "<font face=\"微软雅黑\" size=\"5\" style=\"color: rgb(255, 255, 255); \">永中云转换</font>"+
    				  "</th></tr><tr><td><div style=\"padding:25px 35px 40px; background-color:#fff;\">"+
    				  "<h2 style=\"margin: 5px 0px; \"><font color=\"#333333\" style=\"line-height: 20px; \"><font style=\"line-height: 22px; \" size=\"4\">亲爱的 "+
    				  nickName+"：</font></font></h2>"+
    				  "<p>您邮箱绑定的激活码是："+
    				  "<br><font style=\"line-height: 20px; \" size=\"3\">"+code+"</font>"+
    				  "<br><b>说明：</b><br>如果您是云转换的用户，在绑定邮箱时，我们需要对您的地址有效性进行验证确保可以正常使用。<br>请在30分钟内激活！<br>感谢您的访问，祝您使用愉快！"+
    				  "<p align=\"right\">永中云转换团队</p><p align=\"right\">http://www.dcsapi.com</p></div></td></tr></tbody></table></div>";	
    }
    public String getTempActive(){
    	return "<div style=\"background-color:#ECECEC; padding: 35px;\">"+
    				  "<table cellpadding=\"0\" align=\"center\" style=\"width: 600px; margin: 0px auto; text-align: left; position: relative; border-top-left-radius: 5px; border-top-right-radius: 5px; border-bottom-right-radius: 5px; border-bottom-left-radius: 5px; font-size: 14px; font-family:微软雅黑, 黑体; line-height: 1.5; box-shadow: rgb(153, 153, 153) 0px 0px 5px; border-collapse: collapse; background-position: initial initial; background-repeat: initial initial;background:#fff;\">"+
    				  "<tbody><tr><th valign=\"middle\" style=\"height: 25px; line-height: 25px; padding: 15px 35px; border-bottom-width: 1px; border-bottom-style: solid; border-bottom-color: #5A88C8; background-color: #5A88C8; border-top-left-radius: 5px; border-top-right-radius: 5px; border-bottom-right-radius: 0px; border-bottom-left-radius: 0px;\">"+
    				  "<font face=\"微软雅黑\" size=\"5\" style=\"color: rgb(255, 255, 255); \">永中云转换</font>"+
    				  "</th></tr><tr><td><div style=\"padding:25px 35px 40px; background-color:#fff;\">"+
    				  "<h2 style=\"margin: 5px 0px; \"><font color=\"#333333\" style=\"line-height: 20px; \"><font style=\"line-height: 22px; \" size=\"4\">亲爱的 "+
    				  nickName+"：</font></font></h2>"+
    				  "<p>您邮箱绑定的激活链接是："+
    				  "<br><a href='"+code+"' targer='_blank'>"+code+"</a>"+
    				  "<br><b>说明：</b><br>如果您是云转换的用户，在绑定邮箱时，我们需要对您的地址有效性进行验证确保可以正常使用。<br>请在24小时内激活！<br>感谢您的访问，祝您使用愉快！"+
    				  "<p align=\"right\">永中云转换团队</p><p align=\"right\">http://www.dcsapi.com</p></div></td></tr></tbody></table></div>";	
    }
}
