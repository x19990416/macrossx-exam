/*
 * Copyright (C) 2018 The macrossx-exam-service Authors
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
package com.github.x19990416.macrossx.exam.service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import com.github.x19990416.macrossx.exam.common.ExamException;
import com.github.x19990416.macrossx.exam.common.Page;
import com.github.x19990416.macrossx.exam.data.domain.Field;
import com.github.x19990416.macrossx.exam.data.domain.Point;
import com.github.x19990416.macrossx.exam.data.domain.Question;
import com.github.x19990416.macrossx.exam.data.domain.QuestionType;

public interface IQuestionService {
  /**
   * 添加題庫
   */
  public Optional<Field> addField(Field field) throws ExamException;
  
  public Optional<Boolean> updateField(Field field) throws ExamException;
  
  public Optional<Boolean> deleteField(Long fieldId);
  
  /**
   * 根據題庫名查詢題庫
   */
  public Page<List<Field>> findFields(Field field, PageRequest pageable);
  
  public List<Field> findFields(Field field);
  
  public Page<List<Point>> findPoints(Point point,PageRequest pageable);
  
  public Optional<Point> addPoint(Point point) throws ExamException;
  
  public Optional<Boolean> updatePoint(Point point) throws ExamException;
  
  public Optional<Boolean> deletePoint(Long pointId);
  
  public Page<List<Question>> findQuestions(Question question, PageRequest pageable);
  
  public List<QuestionType> findQuestionType(QuestionType questionType);
  
  public Optional<Question> addQuestion(Question question) throws ExamException;
  
  public Optional<Boolean> updateQuestion(Question question) throws ExamException;
  
  
}
