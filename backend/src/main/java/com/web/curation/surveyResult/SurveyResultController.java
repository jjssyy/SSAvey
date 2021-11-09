package com.web.curation.surveyResult;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.survey.SurveyAnswerDto;
import com.web.curation.survey.SurveyDto;

@Slf4j
@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/survey-result")
public class SurveyResultController {

    private SurveyResultService surveyResultService;
    
    
    @GetMapping("/{survey_id}")
   public ResponseEntity<Map<String, Object>> getSurveyResult(
		   @PathVariable String survey_id
		   ){
    	
    	Map<String, Object> resultmap=new HashMap<String, Object>();
    	SurveyDto result= surveyResultService.getSurveyResult(survey_id);
    	
    	resultmap.put("status", HttpStatus.OK);
    	resultmap.put("data", result);
    	return new ResponseEntity<>(resultmap,HttpStatus.OK); 
    }
    
}
