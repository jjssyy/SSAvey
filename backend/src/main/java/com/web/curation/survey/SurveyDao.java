package com.web.curation.survey;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SurveyDao extends MongoRepository<Survey, String>{
	// List<Survey> findSurveyByTemplateAndend_dateBetween(String Template,LocalDate start_date,LocalDate end_date);
}
