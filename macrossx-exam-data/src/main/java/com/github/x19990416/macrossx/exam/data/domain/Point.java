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
package com.github.x19990416.macrossx.exam.data.domain;

import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.x19990416.macrossx.exam.common.RequestPage;
import com.github.x19990416.macrossx.exam.data.persistence.MePoint;
import com.github.x19990416.macrossx.exam.data.persistence.MePointView;
import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper=false)
public class Point  extends RequestPage{
  
  @JsonProperty(value="key")
  private Long pointId;   
  
  private String name;
  
  private BigDecimal weight;
  
  private Long fieldId;
  
  private String fieldName;
  
  private String comment;
  
  private Integer state;
  
  public Point() {}

  public Point(MePoint mePoint) {
    this.name = mePoint.getPointName();
    this.comment = mePoint.getComment();
    this.state = mePoint.getState();
    this.pointId = mePoint.getPointId();
    this.weight = mePoint.getWeight();
  }  
  
  public Point(MePointView mePointView) {
    this.name = mePointView.getPointName();
    this.comment = mePointView.getComment();
    this.state = mePointView.getState();
    this.pointId = mePointView.getPointId();
    this.fieldId = mePointView.getFieldId();
    this.weight = mePointView.getWeight();
    this.fieldName = mePointView.getFieldName();
  }  
}
