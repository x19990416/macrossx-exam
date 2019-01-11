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

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.assertj.core.util.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import com.github.x19990416.macrossx.exam.common.Constants;
import com.github.x19990416.macrossx.exam.common.ExamException;
import com.github.x19990416.macrossx.exam.common.Page;
import com.github.x19990416.macrossx.exam.data.domain.Field;
import com.github.x19990416.macrossx.exam.data.domain.Point;
import com.github.x19990416.macrossx.exam.data.domain.Question;
import com.github.x19990416.macrossx.exam.data.domain.QuestionType;
import com.github.x19990416.macrossx.exam.data.persistence.MeField;
import com.github.x19990416.macrossx.exam.data.persistence.MePoint;
import com.github.x19990416.macrossx.exam.data.persistence.MePointView;
import com.github.x19990416.macrossx.exam.data.persistence.MeQuestion;
import com.github.x19990416.macrossx.exam.data.persistence.MeQuestionPoint;
import com.github.x19990416.macrossx.exam.data.persistence.MeQuestionType;
import com.github.x19990416.macrossx.exam.data.persistence.MeQuestionView;
import com.github.x19990416.macrossx.exam.data.repository.MeFieldRepository;
import com.github.x19990416.macrossx.exam.data.repository.MePointRepository;
import com.github.x19990416.macrossx.exam.data.repository.MePointViewRepository;
import com.github.x19990416.macrossx.exam.data.repository.MeQuestionPointRepository;
import com.github.x19990416.macrossx.exam.data.repository.MeQuestionRepository;
import com.github.x19990416.macrossx.exam.data.repository.MeQuestionTypeRepository;
import com.github.x19990416.macrossx.exam.data.repository.MeQuestionViewRepository;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class QuestionService implements IQuestionService {

  @Autowired
  MeFieldRepository meFieldRep;

  @Autowired  MePointRepository mePointRep;
  
  @Autowired  MeQuestionTypeRepository meQuesitonTypeRep;

  @Autowired
  MePointViewRepository meFieldPointViewRep;

  @Autowired
  MePointViewRepository mePointViewRep;
  
  @Autowired
  MeQuestionViewRepository meQuestionViewRep;
  
  @Autowired
  MeQuestionRepository meQuestionRep;
  
  @Autowired
  MeQuestionPointRepository meQuestionPointRep;
  
  @Override
  @Transactional
  public Optional<Field> addField(Field field) throws ExamException {
    MeField meField = new MeField();
    meField.setFieldName(field.getName());
    meField.setComment(field.getComment());
    meField.setState(1);
    try {
      meField = meFieldRep.save(meField);
    } catch (DataIntegrityViolationException e) {
      log.info("save field [{}] error, for [{}]", field, e.getMessage());
      throw new ExamException(Constants.EXCEPTION_SERVICE_REPOSITORY_CREATE_ERROR, e);
    }

    return Optional.of(new Field(meField));
  }

  public Page<List<Field>> findFields(Field field, PageRequest pageable) {
    org.springframework.data.domain.Page<MeField> page =
        meFieldRep.findAll(example(field), pageable);
    return Page.of(page.getTotalPages(), page.getTotalElements(), page.getPageable().getPageSize(),
        page.getPageable().getPageNumber(), page.getContent().stream().map(meField -> {
          return new Field(meField);
        }).collect(Collectors.toList()));

  }

  private Example<MeField> example(Field field) {
    MeField meField = new MeField();
    BeanUtils.copyProperties(field, meField);
    meField.setFieldName(field.getName());
    ExampleMatcher matcher =
        ExampleMatcher.matching().withMatcher("comment", GenericPropertyMatchers.contains())
            .withMatcher("fieldId", GenericPropertyMatchers.exact())
            .withMatcher("fieldName", GenericPropertyMatchers.contains())
            .withMatcher("state", GenericPropertyMatchers.exact());

    return Example.of(meField, matcher);
  }
  
  private Example<MeQuestionType> example(QuestionType quesitonType) {
    MeQuestionType meQuestionType = new MeQuestionType ();
    BeanUtils.copyProperties(quesitonType, quesitonType);
    meQuestionType.setSubjective(Boolean.TRUE.equals(quesitonType.getSubjective())?1:0);
    ExampleMatcher matcher =
        ExampleMatcher.matching().withMatcher("comment", GenericPropertyMatchers.contains())
            .withMatcher("id", GenericPropertyMatchers.exact())
            .withMatcher("name", GenericPropertyMatchers.contains())
            .withMatcher("state", GenericPropertyMatchers.exact());

    return Example.of(meQuestionType, matcher);
  }
  
  private Example<MeQuestionView> example(Question question) {
    MeQuestionView meQuestionView = new MeQuestionView();
    BeanUtils.copyProperties(question, meQuestionView);    
    ExampleMatcher matcher =
        ExampleMatcher.matching().withMatcher("name", GenericPropertyMatchers.contains())
            .withMatcher("questionId", GenericPropertyMatchers.exact())
            .withMatcher("questionType", GenericPropertyMatchers.exact());

    return Example.of(meQuestionView, matcher);
  }

  private Example<MePointView> example(Point point) {
    MePointView mePointView = new MePointView();
    BeanUtils.copyProperties(point, mePointView);
    mePointView.setPointName(point.getName());
    ExampleMatcher matcher =
        ExampleMatcher.matching().withMatcher("comment", GenericPropertyMatchers.contains())
            .withMatcher("fieldId", GenericPropertyMatchers.exact())
            .withMatcher("pointId", GenericPropertyMatchers.exact())
            .withMatcher("fieldName", GenericPropertyMatchers.contains())
            .withMatcher("pointName", GenericPropertyMatchers.contains())
            .withMatcher("state", GenericPropertyMatchers.exact());

    return Example.of(mePointView, matcher);
  }

  @Override
  @Transactional
  public Optional<Boolean> deleteField(Long fieldId) {
    meFieldRep.deleteById(fieldId);
    return Optional.of(Boolean.TRUE);
  }

  @Override
  @Transactional
  public Optional<Boolean> updateField(Field field) throws ExamException {
    Optional<MeField> meField = meFieldRep.findById(field.getFieldId());
    if (meField.isPresent()) {
      MeField e = meField.get();
      boolean isChanged = false;
      if (field.getComment() != null) {
        e.setComment(field.getComment());
        isChanged = true;
      }
      if (field.getName() != null) {
        e.setFieldName(field.getName());
        isChanged = true;
      }
      if (isChanged) {
        try {
          meFieldRep.save(e);
        } catch (DataIntegrityViolationException ex) {
          log.info("update field [{}] error, for [{}]", e, ex.getMessage());
          throw new ExamException(Constants.EXCEPTION_SERVICE_REPOSITORY_UPDATE_ERROR, "题库名冲突");
        }
      }
      return Optional.of(Boolean.TRUE);
    } else
      return Optional.of(Boolean.FALSE);

  }

  @Override
  public Page<List<Point>> findPoints(Point point, PageRequest pageable) {
    org.springframework.data.domain.Page<MePointView> page =
        mePointViewRep.findAll(example(point), pageable);
    return Page.of(page.getTotalPages(), page.getTotalElements(), page.getPageable().getPageSize(),
        page.getPageable().getPageNumber(), page.getContent().stream().map(mePointView -> {
          return new Point(mePointView);
        }).collect(Collectors.toList()));
  }

  public List<Field> findFields(Field field) {
    return meFieldRep.findAll(example(field)).stream().map(e -> {
      Field ret = new Field();
      ret.setFieldId(e.getFieldId());
      ret.setName(e.getFieldName());
      return ret;
    }).collect(Collectors.toList());
  }

  @Override
  @Transactional
  public Optional<Point> addPoint(Point point) throws ExamException {

    try {
      MePoint mePoint = new MePoint();
      mePoint.setPointName(point.getName());
      mePoint.setState(1);
      mePoint.setWeight(BigDecimal.valueOf(1.0));
      mePoint.setComment(point.getComment());
      mePoint.setState(1);
      mePoint.setFieldId(point.getFieldId());
      // 添加point表
      mePoint = mePointRep.save(mePoint);

      return Optional.of(new Point(mePoint));
    } catch (DataIntegrityViolationException e) {
      log.info("save field [{}] error, for [{}]", point, e.getMessage());
      throw new ExamException(Constants.EXCEPTION_SERVICE_REPOSITORY_CREATE_ERROR, e);
    }

  }

  @Override
  public Optional<Boolean> updatePoint(Point point) throws ExamException {
    Optional<MePoint> mePoint = mePointRep.findById(point.getPointId());
    if (mePoint.isPresent()) {
      
      //检查并更新Me_point表
      MePoint e = mePoint.get();
      boolean isChanged = false;
      if (point.getComment() != null) {
        e.setComment(point.getComment());
        isChanged = true;
      }
      if (point.getName() != null ) {
        e.setPointName(point.getName());
        isChanged = true;
      }
      if (point.getFieldId() != null ) {
        e.setFieldId(point.getFieldId());
        isChanged = true;
      }
      
      if (isChanged) {
        try {
          mePointRep.save(e);
        } catch (DataIntegrityViolationException ex) {
          log.info("update field [{}] error, for [{}]", e, ex.getMessage());
          throw new ExamException(Constants.EXCEPTION_SERVICE_REPOSITORY_UPDATE_ERROR, "题库名冲突");
        }
      }
      if(point.getFieldId()!=null) {
        
      }
      
      return Optional.of(Boolean.TRUE);
    } else
      return Optional.of(Boolean.FALSE);
  }

  @Override
  public Optional<Boolean> deletePoint(Long pointId) {
    mePointRep.deleteById(pointId);
    return Optional.of(Boolean.TRUE);
  }
  
  public Page<List<Question>> findQuestions(Question question, PageRequest pageable) {
    org.springframework.data.domain.Page<MeQuestionView> page =
        meQuestionViewRep.findAll(example(question), pageable);
    return Page.of(page.getTotalPages(), page.getTotalElements(), page.getPageable().getPageSize(),
        page.getPageable().getPageNumber(), page.getContent().stream().map(meQuestionView -> {
          return new Question(meQuestionView);
        }).collect(Collectors.toList()));

  }

  @Override
  public List<QuestionType> findQuestionType(QuestionType questionType) {
        List<MeQuestionType> listQuesitonType = meQuesitonTypeRep.findAll(example(questionType));
    return listQuesitonType.stream().map(type -> {
          return new QuestionType(type);
        }).collect(Collectors.toList());
  }

  @Override
  @Transactional
  public Optional<Question> addQuestion(Question question) throws ExamException {
    MeQuestion meQuestion = new MeQuestion();
    meQuestion.setName(question.getName());
    meQuestion.setAnswer(new Gson().toJson(question.getAnswer()));
    meQuestion.setQuestionType(question.getQuestionType());
    meQuestion.setExposeTimes(0l);
    meQuestion.setRightTimes(0l);
    meQuestion.setWrongTimes(0l);
    meQuestion.setContent(new Gson().toJson(question.getContent()));
    meQuestion.setStatus(1);
    try {
      final MeQuestion saveed = meQuestionRep.save(meQuestion);
      if(question.getPoints()!=null) {
        meQuestionPointRep.saveAll(question.getPoints().stream().map(e->{
        MeQuestionPoint mqp = new MeQuestionPoint();
        mqp.setPointId(e);
        mqp.setQuestionId(saveed.getQuesitonId());
        return mqp;
        }).collect(Collectors.toList()));}
      return Optional.of(new Question(meQuestion));
    } catch (Exception e) {
      log.info("save field [{}] error, for [{}]", question, e.getMessage());
      throw new ExamException(Constants.EXCEPTION_SERVICE_REPOSITORY_CREATE_ERROR, e);
    }
  }

  @Override
  @Transactional
  public Optional<Boolean> updateQuestion(Question question) throws ExamException {
    Optional<MeQuestion> meQuestion = meQuestionRep.findById(question.getQuestionId());
    if (meQuestion.isPresent()) {
      MeQuestion e = meQuestion.get();
      boolean isChanged = false;
      if (question.getName() != null) {
        e.setName(question.getName());
        isChanged = true;
      }
      if (question.getAnalysis()!= null) {
        e.setAnalysis(question.getAnalysis());
        isChanged = true;
      }
      if(question.getAnswer()!=null &&!question.getAnswer().isEmpty()) {
        e.setAnswer(new Gson().toJson(question.getAnswer()));
      }
      if(question.getContent()!=null ) {
        e.setContent(new Gson().toJson(question.getContent()));
      }      
      if(question.getPoints()!=null) {
        isChanged =true;
      }
      if (isChanged) {
        try {
          meQuestionRep.save(e);
          if(question.getPoints()!=null) {            
            meQuestionPointRep.deleteByQuestionId(e.getQuesitonId());
            meQuestionPointRep.flush();
            List<MeQuestionPoint> toSavePoint = Lists.newArrayList();
            for(Long pointId : question.getPoints()) {
              MeQuestionPoint tmp = new MeQuestionPoint();
              tmp.setQuestionId(e.getQuesitonId());
              tmp.setPointId(pointId);
              toSavePoint.add(tmp);
            }
            meQuestionPointRep.saveAll(toSavePoint);
          }
        } catch (Exception ex) {
          log.info("update field [{}] error, for [{}], input data is [{}]", e, ex.getMessage(),question);
          throw new ExamException(Constants.EXCEPTION_SERVICE_REPOSITORY_UPDATE_ERROR, "数据冲突");
        }
      }
      return Optional.of(Boolean.TRUE);
    } else
      return Optional.of(Boolean.FALSE);

  }

}
