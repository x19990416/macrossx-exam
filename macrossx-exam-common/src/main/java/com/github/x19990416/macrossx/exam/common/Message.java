/*
 * Copyright (C) 2018 The macrossx-exam-common Authors
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
package com.github.x19990416.macrossx.exam.common;

import lombok.Data;

@Data
public class Message<T> {
  private String code;
  private T msg;
  
  public static <T>  Message<T> of(String code,T t) {
    Message<T> message = new Message<T>();
    message.setCode(code);
    message.setMsg(t);
    return message;
  }
  
  public static <T> Message<T> success(T t){
    return Message.of( Constants.SUCCESS_CODE,t);
  }
  
  public static  Message<String> error(String errorCode,String errorMsg){
    return Message.of( errorCode,errorMsg);
  }
}
