package com.web.curation.answer;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SurveyAnswerDao extends MongoRepository<SurveyAnswer, String>{

}
