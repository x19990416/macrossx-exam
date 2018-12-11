/*
 * Copyright (C) 2010 The macrossx-exam-data Authors
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

package com.github.x19990416.macrossx.exam.data.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * 用户表
 * @author lmguo
 */
@Data
@Entity
@Table(name="me_user")
public class MeUser {
	@Id
	@Column(name="user_id")
	private String userId;
	
	private String nickName;
	
	private String realName;
	
	private String wxOpenid;
	
	private String mobile;
	
	private Integer enabled;	
}
