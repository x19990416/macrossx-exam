/*
 * Copyright (C) 2018 The macrossx-exam-service Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.github.x19990416.macrossx.exam.service;

import java.util.Optional;
import com.github.x19990416.macrossx.exam.common.ExamException;
import com.github.x19990416.macrossx.exam.data.domain.User;

/**
 * 用户管理
 * @author lmguo
 */
public interface IUserService {
	/**
	 * 用户名、密码登录
	 * @param username
	 * @param password
	 */
	public Optional<User> login(String username,String password);
	
	/**
	 * 根据用户获取用户信息
	 */
	public Optional<User> loadUserByUsername(String username);
	
	/**
	 * 微信登录
	 * @param wechatOpenId
	 */
	public User login(String wechatOpenId) throws ExamException;
	
}
