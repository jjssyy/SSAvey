package com.web.curation.survey;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SurveyDao extends MongoRepository<Survey, String>{

}
