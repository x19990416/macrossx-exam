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
import java.util.UUID;
import javax.transaction.Transactional;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.x19990416.macrossx.exam.common.Constants;
import com.github.x19990416.macrossx.exam.common.ExamException;
import com.github.x19990416.macrossx.exam.data.domain.Role;
import com.github.x19990416.macrossx.exam.data.domain.User;
import com.github.x19990416.macrossx.exam.data.persistence.MeUser;
import com.github.x19990416.macrossx.exam.data.repository.MeUserRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService implements IUserService {

  @Autowired
  MeUserRepository meUserRep;
  
  @Autowired
  IRoleService roleService;
  
  @Override public Optional<User> login(String username, String password) {
    return meUserRep.findByuserNameAndPassword(username, password).flatMap(e->{
      User user = new User(e);
      user.setRoles(roleService.findUserRole(e.getUserId()));
      return Optional.of(user);
    });}

  /**
   * 微信登陆，若用户不存在则创建用户后登陆，默認用戶為學生
   * 
   * @throws Exception
   */
  
  @Transactional
  @Override public User login(String wechatOpenId) throws ExamException {
  return meUserRep.findBywxOpenid(wechatOpenId).flatMap(e->{
     User user =  new User(e);
     user.setRoles(roleService.findUserRole(user.getUserId()));
     return Optional.of(user);
          
   }).orElseGet(()->{
     MeUser meUser = new MeUser();
     meUser.setUserId(UUID.randomUUID().toString());
     meUser.setNickName(wechatOpenId);
     meUser.setWxOpenid(wechatOpenId);
     meUser.setEnabled(1);
     MeUser newMeUser = this.addUser(meUser);
     Optional<Role> role = roleService.find(Constants.Role.ROLE_STUDENT.name());
     if(!role.isPresent()) {
       new ExamException(Constants.EXCEPTION_SERVICE_REPOSITORY_RETRIVE_ERROR,"无相关数据: "+Constants.Role.ROLE_STUDENT.name());
     }
     
     User user = new User(newMeUser);
     user.setRoles(Lists.newArrayList(role.get()));
     return user;
   });
  }

  @Transactional
  private MeUser addUser(MeUser meUser) {
    return meUserRep.save(meUser);
  }

  @Override
  public Optional<User> loadUserByUsername(String username) {
    return meUserRep.findByuserName(username).flatMap(e->{
      User user = new User(e);
      user.setRoles(roleService.findUserRole(user.getUserId()));
      return Optional.of(user);
    });
  }

  


}
