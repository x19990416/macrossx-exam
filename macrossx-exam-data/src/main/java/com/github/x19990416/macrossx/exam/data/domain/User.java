package com.github.x19990416.macrossx.exam.data.domain;

import java.util.List;

import com.github.x19990416.macrossx.exam.data.persistence.MeUser;

import lombok.Data;

@Data
public class User {
	private String userId;
	private String nickName;
	private String realName;
	private String wxOpenid;
	private String userName;
	private String mobile;
	private Integer enabled;
	private List<Role> roles;
	private String token;
	
	public User() {
	  
	}
	
	public User(MeUser meUser) {
		this.userId = meUser.getUserId();
		this.nickName = meUser.getNickName();
		this.realName = meUser.getRealName();
		this.wxOpenid = meUser.getWxOpenid();
		this.mobile = meUser.getMobile();
		this.enabled = meUser.getEnabled();	
	}
}
