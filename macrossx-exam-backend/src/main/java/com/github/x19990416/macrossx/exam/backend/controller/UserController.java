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
package com.github.x19990416.macrossx.exam.backend.controller;

import javax.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.x19990416.macrossx.exam.common.ExamException;
import com.github.x19990416.macrossx.exam.common.Message;
import com.github.x19990416.macrossx.exam.common.aop.Ignore;
import com.github.x19990416.macrossx.exam.data.domain.User;
import com.github.x19990416.macrossx.exam.service.IUserService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
@RestController
@RequestMapping("/backend/user")

@Slf4j
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserController {
  @Autowired
  IUserService userService;
  
  @Ignore
  @RequestMapping(value="/login", method={RequestMethod.GET})
  public Message<?> login(@Validated LoginVo loginVo) {
    
    User user= userService.login(loginVo.getUsername(), loginVo.getPassword()).orElse(null);
    
    return user ==null?Message.error("2000001", "用户名密码错误"):Message.success(user);    
    
  }
  
  @Ignore
  @RequestMapping(value="/login/wechat", method={RequestMethod.GET})
  public Message<?> login(@NotBlank @RequestParam String openid) {
    
    try {
      User user = userService.login(openid);
      return user ==null?Message.error("2000001", "用户名密码错误"):Message.success(user);    
    } catch (ExamException e) {
      // TODO Auto-generated catch block
      log.error("创建用户失败 [{}]-{}-{}",openid,e.getCode(),e.getMessage());
      return Message.error("2000001", "创建用户失败");
    }
    
   
    
  }
  
  @Data
  public static class LoginVo{
    @NotBlank
    private String username;
    @NotBlank
    private String password;
  }
}
