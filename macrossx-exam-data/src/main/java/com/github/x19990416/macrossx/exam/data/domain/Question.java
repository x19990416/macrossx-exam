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

import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.x19990416.macrossx.exam.common.RequestPage;
import com.github.x19990416.macrossx.exam.data.persistence.MeQuestion;
import com.github.x19990416.macrossx.exam.data.persistence.MeQuestionView;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Question extends RequestPage{
  @JsonProperty(value="key")
  private Long questionId;
  private String name;
  private Integer questionType;
  private String questionTypeName;
  private QuestionContent content;
  private List<String> answer;
  private List<Long> points;
  private List<String> pointNames;
  private String analysis;

  public Question() {}

  public Question(MeQuestionView meQuestionView) {
    this.questionId = meQuestionView.getQuesitonId();
    this.name = meQuestionView.getName();
    this.analysis = meQuestionView.getAnalysis();
    this.questionType = meQuestionView.getQuestionType();
    this.questionTypeName = meQuestionView.getQuestionTypeName();
    this.content = new Gson().fromJson(meQuestionView.getContent(), QuestionContent.class);
    if(meQuestionView.getPoints()!=null &&!meQuestionView.getPoints().isEmpty()) {
      this.points =Lists.newArrayList(new Gson().fromJson(meQuestionView.getPoints(), Long[].class));          
    }
    if(meQuestionView.getAnswer()!=null &&!meQuestionView.getAnswer().isEmpty()) {
      this.answer =Lists.newArrayList(new Gson().fromJson(meQuestionView.getAnswer(), String[].class));          
    }
  }
  
  public Question(MeQuestion meQuestion) {
    this.questionId = meQuestion.getQuesitonId();
    this.name = meQuestion.getName();
    this.questionType = meQuestion.getQuestionType();
    
  }
  
  @Data
  public static class QuestionContent{
    private String title;
    private Map<String,String> choiceList;
  }
}
