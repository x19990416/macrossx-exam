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

public class Constants {
  public static enum Role {
    ROLE_ADMIN, ROLE_TEACHER, ROLE_STUDENT;
  }
  public static String SUCCESS_CODE="000000";
  
  /**
   * 数据库添加数据失败
   */
  public static String EXCEPTION_SERVICE_REPOSITORY_CREATE_ERROR ="1000001";
  /**
   * 数据库读取数据失败
   */
  public static String EXCEPTION_SERVICE_REPOSITORY_RETRIVE_ERROR ="1000002";
  /**
   * 数据库读取数据失败
   */
  public static String EXCEPTION_SERVICE_REPOSITORY_UPDATE_ERROR ="1000003";
  /**
   * 数据库读删除据失败
   */
  public static String EXCEPTION_SERVICE_REPOSITORY_DELETE_ERROR ="1000004";
}
