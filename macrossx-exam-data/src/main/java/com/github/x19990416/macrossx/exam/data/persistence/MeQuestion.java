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
@Data
@Table(name="me_question")
@Entity
public class MeQuestion{
  @Id
  @Column(name = "question_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long quesitonId;

  private String name;
  
  private String content;
  
  private String answer;
  
  private Integer questionType;
  
  private Integer duration;
  
  private BigDecimal score;
  
  private Long groupId;
  
  private Long exposeTimes;
  
  private Long rightTimes;
  
  private Long wrongTimes;
  
  private String analysis;
  
  private String reference;
  
  private String keywords;
  
  private Timestamp createTime  = new Timestamp(System.currentTimeMillis());
 
  private Integer status;  
}
