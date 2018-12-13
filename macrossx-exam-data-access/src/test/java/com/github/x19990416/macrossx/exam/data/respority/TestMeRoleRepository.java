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

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringRunner;
import com.github.x19990416.macrossx.exam.data.repository.MeRoleRepository;
@ComponentScan(basePackages="com.github.x19990416.macrossx.exam")
@SpringBootApplication
@RunWith(SpringRunner.class)
@SpringBootTest(classes=TestMeRoleRepository.class)
@EnableJpaRepositories("com.github.x19990416.macrossx.exam.data.repository")
@EntityScan("com.github.x19990416.macrossx.exam.data.persistence")
public class TestMeRoleRepository{

	@Autowired
	MeRoleRepository meRoleRep;
	@Test
	public void testfindById() {
	 // Assert.assertTrue(meRoleRep.findById(1l).isPresent());
	}
	@Test
	public void testFindByAuthority() {
	      Assert.assertTrue(meRoleRep.findByAuthority("ROLE_ADMIN").isPresent());
	 }
	   @Test
	    public void testFindByAuthorityNull() {
	          Assert.assertFalse(meRoleRep.findByAuthority("test").isPresent());
	     }
}
