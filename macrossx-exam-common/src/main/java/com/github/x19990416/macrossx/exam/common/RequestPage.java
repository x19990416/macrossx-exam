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
import lombok.ToString;

@Data
@ToString(callSuper=true)
public abstract class RequestPage {
  private Integer pageSize;
  private Integer pageNo;
  
  public void checkDefault() {
    if(pageSize==null || pageNo ==null) {
      pageSize =10 ;
      pageNo =0;
    }
  }
  
  public void checkAll() {
    if(pageSize==null || pageNo ==null) {
      pageSize = Integer.MAX_VALUE;
      pageNo = 0;
    }
  }
  
}
