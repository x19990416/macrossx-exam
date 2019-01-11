/*
 * Copyright (C) 2019 The macrossx-exam-data Authors
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
package com.github.x19990416.macrossx.exam.data.persistence;

import java.math.BigDecimal;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
/**
 * 想使用表继承{@extends MeQuesiton} 但是怎么写都觉得怪（主要是对spring 不熟悉），所以最后还是顶一个view的实例。
 * @author lmguo
 */

@Entity
@Table(name = "me_question_view")
@Data
public class MeQuestionView{
  @Id
  @Column(name = "question_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long quesitonId;

  private String name;
  
  private String content;
  
  private String answer;
  
  private Integer questionType;
  
  private String questionTypeName;
  
  private Integer duration;
  
  private BigDecimal score;
  
  private Long groupId;
  
  private Long exposeTimes;
  
  private Long rightTimes;
  
  private Long wrongTimes;
  
  private String analysis;
  
  private String reference;
  
  private String keywords;
  
  private Timestamp createTime;
 
  private Integer status;  
  
  private String points;

  

  
}
