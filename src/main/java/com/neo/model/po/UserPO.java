package com.neo.model.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.neo.model.BasePO;

import java.util.Date;

public class UserPO extends BasePO
{

    private static final long serialVersionUID = -1723268717083414402L;

    public UserPO()
    {
        super();
    }


    private String username;

    @JsonIgnore
    private String passwords;

    private Integer type;

    @JsonIgnore
    private Integer roleId;

    private String phone;

    private String email;

    private Integer score;

    private String openId;

    private String nickName;

    private Integer sex;

    private String province;

    private String city;

    private String country;

    private String headImgurl;

    @JsonIgnore
    private String privilege;

    @JsonIgnore
    private String unionid;

    @JsonIgnore
    private String accessToken;

    @JsonIgnore
    private Date gmtAccess;

    @JsonIgnore
    private String refreshToken;

    @JsonIgnore
    private Date gmtRefresh;

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username == null ? null : username.trim();
    }

    public String getPasswords()
    {
        return passwords;
    }

    public void setPasswords(String passwords)
    {
        this.passwords = passwords == null ? null : passwords.trim();
    }

    public Integer getType()
    {
        return type;
    }

    public void setType(Integer type)
    {
        this.type = type;
    }

    public Integer getRoleId()
    {
        return roleId;
    }

    public void setRoleId(Integer roleId)
    {
        this.roleId = roleId;
    }

    public String getOpenId()
    {
        return openId;
    }

    public void setOpenId(String openId)
    {
        this.openId = openId == null ? null : openId.trim();
    }

    public String getNickName()
    {
        return nickName;
    }

    public void setNickName(String nickName)
    {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email == null ? null : email.trim();
    }

    public Integer getScore()
    {
        return score;
    }

    public void setScore(Integer score)
    {
        this.score = score;
    }

    public Integer getSex()
    {
        return sex;
    }

    public void setSex(Integer sex)
    {
        this.sex = sex;
    }

    public String getProvince()
    {
        return province;
    }

    public void setProvince(String province)
    {
        this.province = province;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public String getHeadImgurl()
    {
        return headImgurl;
    }

    public void setHeadImgurl(String headImgurl)
    {
        this.headImgurl = headImgurl;
    }

    public String getPrivilege()
    {
        return privilege;
    }

    public void setPeivilege(String privilege)
    {
        this.privilege = privilege;
    }

    public String getUnionid()
    {
        return unionid;
    }

    public void setUnionid(String unionid)
    {
        this.unionid = unionid;
    }

    public String getAccessToken()
    {
        return accessToken;
    }

    public void setAccessToken(String accessToken)
    {
        this.accessToken = accessToken;
    }

    public Date getGmtAccess()
    {
        return gmtAccess;
    }

    public void setGmtAccess(Date gmtAccess)
    {
        this.gmtAccess = gmtAccess;
    }

    public String getRefreshToken()
    {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken)
    {
        this.refreshToken = refreshToken;
    }

    public Date getGmtRefresh()
    {
        return gmtRefresh;
    }

    public void setGmtRefresh(Date gmtRefresh)
    {
        this.gmtRefresh = gmtRefresh;
    }

    @Override
    public String toString()
    {
        return "UserPO [username=" + username + ", passwords=" + passwords + ", type=" + type + ", roleId=" + roleId + ", openId=" + openId + ", nickName="
                + nickName + ", phone=" + phone + ", email=" + email + ", id=" + id + ", gmtCreate=" + gmtCreate + ", gmtModify=" + gmtModify + ", status="
                + status + "]";
    }

}