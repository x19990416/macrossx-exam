/*
 * Copyright (C) 2018 The macrossx-exam-data Authors
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
public class Page<T> {
  private T content;    
  private int pages;
  private long count;
  private int pageSize;
  private int pageNumber;


  public static <T> Page<T> of(int pages, long count, int pageSize, int pageNumber, T content) {
    Page<T> page = new Page<T>();
    page.setPages(pages);
    page.setContent(content);
    page.setCount(count);
    page.setPageNumber(pageNumber);
    page.setPageSize(pageSize);
    return page;
  }

  
  public static <T> Page<T> empty() {
    Page<T> page = new Page<T>();
    page.setPages(0);
    page.setContent(null);
    page.setCount(0);
    page.setPageNumber(0);
    page.setPageSize(0);
    return page;
  }
}
