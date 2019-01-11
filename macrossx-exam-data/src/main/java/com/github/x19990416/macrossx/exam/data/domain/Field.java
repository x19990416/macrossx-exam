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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.x19990416.macrossx.exam.common.RequestPage;
import com.github.x19990416.macrossx.exam.data.persistence.MeField;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Field extends RequestPage{
  @JsonProperty(value="key")
  private Long fieldId;
  private String name;
  private String comment;
  private Integer state;

  public Field() {}

  public Field(MeField meField) {
    this.fieldId = meField.getFieldId();
    this.name = meField.getFieldName();
    this.comment = meField.getComment();
    this.state = meField.getState();
  }
}
