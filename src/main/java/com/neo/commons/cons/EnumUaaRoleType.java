package com.neo.commons.cons;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import com.yozosoft.auth.client.security.UaaUserRole;


/**
 * 单点账号类型
 *
 * @author xujun
 * @description
 * @create 2019年11月25日
 */
@Getter
public enum EnumUaaRoleType {

    /**
     *
     */
    USER(1, "User"),
    CORP_MEMBER(2, "CorpMember"),
    CORP_ADMIN(3, "CorpAdmin"),
    ADMIN(4, "Admin");


    private Integer value;
    private String info;

    private EnumUaaRoleType(Integer value, String info) {
        this.value = value;
        this.info = info;
    }

    public static EnumUaaRoleType getEnumUaaRoleType(String info) {
        for (EnumUaaRoleType role : values()) {
            if (info.equals(role.getInfo())) {
                return role;
            }
        }
        return null;
    }

    /**
     * 是否允许上传优云
     * @author zhoufeng
     * @date 2019/11/26
     */
    public static Boolean canUploadYc(UaaUserRole role){
		if(StringUtils.isNotBlank(role.name())){
			EnumUaaRoleType enumUaaRoleType = getEnumUaaRoleType(role.name());
			if(enumUaaRoleType!=null){
				switch (enumUaaRoleType){
					case USER:
					case ADMIN:
						return true;
					default:
						return false;
				}
			}
		}
		return false;
	}
}
