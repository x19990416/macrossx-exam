/*
 * Copyright (C) 2018 The macrossx-exam-backend Authors
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
package com.github.x19990416.macrossx.exam.backend.controller;

import java.util.List;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.github.x19990416.macrossx.exam.common.ExamException;
import com.github.x19990416.macrossx.exam.common.Message;
import com.github.x19990416.macrossx.exam.common.Page;
import com.github.x19990416.macrossx.exam.data.domain.Field;
import com.github.x19990416.macrossx.exam.data.domain.Point;
import com.github.x19990416.macrossx.exam.data.domain.Question;
import com.github.x19990416.macrossx.exam.data.domain.QuestionType;
import com.github.x19990416.macrossx.exam.service.IQuestionService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/backend/question")
@Slf4j
public class QuestionController {
  @Autowired
  IQuestionService questionService;

  @RequestMapping(value = "/field/list", method = RequestMethod.POST,
      produces = {"application/json"})
  public Message<?> listField(@RequestBody Field field) {
    field.checkDefault();
    Page<List<Field>> fields =
        questionService.findFields(field, PageRequest.of(field.getPageNo(), field.getPageSize()));
    fields.setPageNumber(fields.getPageNumber() + 1);

    return Message.success(fields);
  }

  @RequestMapping(value = "/field/name", method = RequestMethod.POST,
      produces = {"application/json"})
  public Message<?> listFieldName(@RequestBody Field field) {
    field.checkDefault();
    return Message.success(questionService.findFields(field));
  }

  @RequestMapping(value = "/field/delete", method = RequestMethod.POST,
      produces = {"application/json"})
  public Message<?> removeField(@RequestBody Field field) {
    if (field == null || field.getFieldId() == null) {
      log.info("题库id不存在{[]}", field);
      return Message.BAD_REQUEST("id不存在");
    }

    questionService.deleteField(field.getFieldId());

    return this.listField(new Field());
  }

  @RequestMapping(value = "/field/add", method = RequestMethod.POST,
      produces = {"application/json"})
  public Message<?> addField(@RequestBody Field field) {
    try {
      return Message.success(questionService.addField(field).get());
    } catch (ExamException e) {
      return Message.error(e.getCode(), e.getMessage());
    }
  }

  @RequestMapping(value = "/field/update", method = RequestMethod.POST,
      produces = {"application/json"})
  public Message<?> updateField(@RequestBody Field field) {
    try {
      questionService.updateField(field);
      return this.listField(new Field());
    } catch (ExamException e) {
      return Message.error(e.getCode(), e.getMessage());
    }
  }

  @RequestMapping(value = "/point/list", method = RequestMethod.POST,
      produces = {"application/json"})
  public Message<?> listPoint(@RequestBody Point point) {
    point.checkDefault();
    Page<List<Point>> points =
        questionService.findPoints(point, PageRequest.of(point.getPageNo(), point.getPageSize()));
    points.setPageNumber(points.getPageNumber() + 1);
    return Message.success(points);
  }
  
  @RequestMapping(value = "/point/add", method = RequestMethod.POST,
      produces = {"application/json"})
  public Message<?> addPoint(@RequestBody Point point) {
    try {
      return Message.success(questionService.addPoint(point).get());
    } catch (ExamException e) {
      return Message.error(e.getCode(), e.getMessage());
    }
  }

  @RequestMapping(value = "/point/update", method = RequestMethod.POST,
      produces = {"application/json"})
  public Message<?> updatePoint(@RequestBody Point point) {
    try {
      return Message.success(questionService.updatePoint(point).get());
    } catch (ExamException e) {
      return Message.error(e.getCode(), e.getMessage());
    }
  }

  @RequestMapping(value = "/point/delete", method = RequestMethod.POST,
      produces = {"application/json"})
  public Message<?> deletePoint(@RequestBody Point point) {
    if (point == null || point.getPointId() == null) {
      log.info("知识点id不存在{[]}", point);
      return Message.BAD_REQUEST("id不存在");
    }
    return Message.success(questionService.deletePoint(point.getPointId()).get());
  }
  @RequestMapping(value = "/question/types", method = RequestMethod.POST,
      produces = {"application/json"})
  public Message<?> listQuestionType(@RequestBody QuestionType questionType) {
    return Message.success(questionService.findQuestionType(questionType));
  }

  @RequestMapping(value = "/question/list", method = RequestMethod.POST,
      produces = {"application/json"})
  public Message<?> listField(@RequestBody Question quesiton) {
    quesiton.checkDefault();
    Page<List<Question>> fields = questionService.findQuestions(quesiton,
        PageRequest.of(quesiton.getPageNo(), quesiton.getPageSize()));
    fields.setPageNumber(fields.getPageNumber() + 1);

    return Message.success(fields);
  }
  
  @RequestMapping(value = "/question/add", method = RequestMethod.POST,
      produces = {"application/json"})
  public Message<?> addQuestion(@RequestBody Question quesiton) {
    try {
      return Message.success(questionService.addQuestion(quesiton).get());
    } catch (ExamException e) {
      return Message.error(e.getCode(), e.getMessage());
    }
  }
  
  
  @RequestMapping(value = "/question/update", method = RequestMethod.POST,
      produces = {"application/json"})
  public Message<?> updateQuestion(@RequestBody Question quesiton) {
    try {
      return Message.success(questionService.updateQuestion(quesiton).get());
    } catch (ExamException e) {
      return Message.error(e.getCode(), e.getMessage());
    }
  }
}
