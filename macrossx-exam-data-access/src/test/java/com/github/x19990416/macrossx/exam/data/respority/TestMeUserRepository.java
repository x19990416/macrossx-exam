/*
 * Copyright (C) 2018 The macrossx-exam-backend Authors
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
package com.github.x19990416.macrossx.exam.data.respority;

import java.util.UUID;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.x19990416.macrossx.exam.data.persistence.MeUser;
import com.github.x19990416.macrossx.exam.data.repository.MeUserRepository;

import org.junit.Assert;
@ComponentScan(basePackages="com.github.x19990416.macrossx.exam")
@SpringBootApplication
@RunWith(SpringRunner.class)
@SpringBootTest(classes=TestMeUserRepository.class)
@EnableJpaRepositories("com.github.x19990416.macrossx.exam.data.repository")
@EntityScan("com.github.x19990416.macrossx.exam.data.persistence")
public class TestMeUserRepository{

	@Autowired
	MeUserRepository meUserRep;
	@Test
	public void testLoadUserByUsername() {
		MeUser user = new MeUser();
		user.setUserId(UUID.randomUUID().toString());
		user.setNickName("testNickName");
		user.setWxOpenid("testWxOpenid");
		MeUser newUser = meUserRep.save(user);
		Assert.assertEquals(newUser.getUserId(), user.getUserId());
		
		
	}

}
