package com.neo.commons.cons;

import org.apache.commons.lang3.StringUtils;

import com.neo.model.bo.MembershipsBO;

import lombok.Getter;

/**
 * 会员枚举类
 * @author xujun
 * @description
 * @create 2019年12月5日
 */
@Getter
public enum EnumMemberType {

	VISITOR(0,"visitor","游客"),
	MEMBER(1,"Member","注册用户"),
	MEMBER_YOZOCLOUD(2,"MemberYozocloud","永中会员"),
	MEMBER_YOMOER(3,"MemberYomoer","柚墨会员"),
	MEMBER_VIP(4,"MemberVip","超级会员");
	
	private Integer value;
    private String info;
    private String type;
    
	private EnumMemberType(Integer value, String info ,String type) {
		this.value = value;
		this.info = info;
		this.type = type;
	}
    
	
	public static String getTypeInfo(Integer velue) {
        for (EnumMemberType statu : values()) {
			if(statu.getValue().equals(velue)){
				return statu.getInfo();
			}
		}
		return null;
	}
	
	
    public static EnumMemberType getEnumMemberType(String info) {
        for (EnumMemberType role : values()) {
            if (info.equals(role.getInfo())) {
                return role;
            }
        }
        return null;
    }
	
    
    
    /**
     * 判断是否是会员
     * @param info
     * @return
     */
    public static Boolean isMember(MembershipsBO  memberships[]){
    	if(memberships!=null && memberships.length > 0) {
    		for(MembershipsBO  bo : memberships) {
    			if(StringUtils.isNotBlank(bo.getMembership())){
    				EnumMemberType enumMemberType = getEnumMemberType(bo.getMembership());
    				if(enumMemberType!=null){
    					switch (enumMemberType){
    						case MEMBER_YOZOCLOUD:
    						case MEMBER_YOMOER:
    						case MEMBER_VIP:
    							return true;
    						default:
    							return false;
    					}
    				}
    			}
    			return false;
    		}
    	}
    	return false; 
	}
	
	
	
	
	
	
	
	
}
