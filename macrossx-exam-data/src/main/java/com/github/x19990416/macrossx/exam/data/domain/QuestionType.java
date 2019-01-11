/*
 *
 * Copyright (C) 2018 The macrossx-exam-data Authors Licensed under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.github.x19990416.macrossx.exam.data.domain;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.x19990416.macrossx.exam.data.persistence.MeQuestionType;
import lombok.Data;

@Data
public class QuestionType {
  @JsonProperty(value="key")
  private Integer id;
  private String name;
  private Boolean subjective;


  public QuestionType() {}
  public QuestionType(MeQuestionType meQuestionType) {
    this.id = meQuestionType.getId();
    this.name = meQuestionType.getName();
    this.subjective = meQuestionType.getSubjective()==1?Boolean.TRUE:Boolean.FALSE;
  }
  @Data
  public static class QuestionContent{
    private String title;
    private Map<String,String> choiceList;
  }
}
