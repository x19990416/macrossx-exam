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

import org.springframework.http.HttpStatus;
import lombok.Data;
/**
 *   200: '服务器成功返回请求的数据。',
 *   201: '新建或修改数据成功。',
 *   202: '一个请求已经进入后台排队（异步任务）。',
 *   204: '删除数据成功。',
 *   400: '发出的请求有错误，服务器没有进行新建或修改数据的操作。',
 *   401: '用户没有权限（令牌、用户名、密码错误）。',
 *   403: '用户得到授权，但是访问是被禁止的。',
 *   404: '发出的请求针对的是不存在的记录，服务器没有进行操作。',
 *   406: '请求的格式不可得。',
 *   410: '请求的资源被永久删除，且不会再得到的。',
 *   422: '当创建一个对象时，发生一个验证错误。',
 *   500: '服务器发生错误，请检查服务器。',
 *   502: '网关错误。',
 *   503: '服务不可用，服务器暂时过载或维护。',
 *   504: '网关超时。',
 */
@Data
public class Message<T> {
  private Long code;
  private T msg;
  
  public static <T>  Message<T> of(Long code,T t) {
    Message<T> message = new Message<T>();
    message.setCode(code);
    message.setMsg(t);
    return message;
  }
  
  public static <T> Message<T> success(T t){
    return Message.of( Constants.SUCCESS_CODE,t);
  }
  
  public static  Message<String> success(){
    return Message.of( Constants.SUCCESS_CODE,"success");
  }
  
  public static <T> Message<T> CREATED(T msg){
    return Message.of(Long.valueOf(HttpStatus.CREATED.value()),msg);
  }
  public static <T> Message<T> NO_CONTENT(T msg){
    return Message.of(Long.valueOf(HttpStatus.NO_CONTENT.value()),msg);
  }
  public static <T> Message<T> BAD_REQUEST(T msg){
    return Message.of(Long.valueOf(HttpStatus.BAD_REQUEST.value()),msg);
  }
  public static <T> Message<T> UNAUTHORIZED(T msg){
    return Message.of(Long.valueOf(HttpStatus.UNAUTHORIZED.value()),msg);
  }
  public static <T> Message<T> FORBIDDEN(T msg){
    return Message.of(Long.valueOf(HttpStatus.FORBIDDEN.value()),msg);
  }
  public static <T> Message<T> UNPROCESSABLE_ENTITY(T msg){
    return Message.of(Long.valueOf(HttpStatus.UNPROCESSABLE_ENTITY.value()),msg);
  }
  public static  Message<String> error(Long errorCode,String errorMsg){
    return Message.of( errorCode,errorMsg);
  }
}
