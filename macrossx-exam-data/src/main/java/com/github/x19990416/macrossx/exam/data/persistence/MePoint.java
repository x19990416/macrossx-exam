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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "me_point")
public class MePoint {
  @Id
  @Column(name = "point_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long pointId;
  
  
  private String pointName;
  
  private BigDecimal weight;
  
  private String comment;
  
  private Long fieldId;
  
  private Integer state;
}
