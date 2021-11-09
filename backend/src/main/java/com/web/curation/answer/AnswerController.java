package com.web.curation.answer;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/answer")
public class AnswerController {

    private AnswerService answerService;
    
    @GetMapping("/{uid}/{survey_id}")
    public ResponseEntity<Map<String, Object>> GetSurveyAnswer(
    		@PathVariable String uid,
    		@PathVariable String survey_id
    		){
    	Map<String, Object> resultmap=new HashMap<String, Object>();
    	
    	SurveyAnswer surveyAnswer=answerService.GetSurveyAnswer(survey_id, uid);
    	resultmap.put("status", HttpStatus.OK);
    	resultmap.put("data", surveyAnswer);
    	return new ResponseEntity<>(resultmap,HttpStatus.OK); 
    }
    
    @PostMapping("/{uid}/{survey_id}")
    public ResponseEntity<Map<String, Object>> CreateSurveyAnswer(
    		@PathVariable String uid,
    		@PathVariable String survey_id,
    		@RequestBody SurveyAnswer surveyAnswer
    		){
    	Map<String, Object> resultmap=new HashMap<String, Object>();
    	answerService.CreateSurveyAnswer(surveyAnswer, survey_id, uid);
    	resultmap.put("status", HttpStatus.OK);
    	resultmap.put("data", "success");
    	return new ResponseEntity<>(resultmap,HttpStatus.OK); 
    }
    
    @GetMapping("/check/{uid}/{survey_id}")
    public ResponseEntity<Map<String, Object>> checkSurveyAnswer(
       		@PathVariable String uid,
    		@PathVariable String survey_id
    		){
    	Map<String, Object> resultmap=new HashMap<String, Object>();
    	boolean result = answerService.CheckSurveyAnswer(survey_id, uid);
    	resultmap.put("status", HttpStatus.OK);
    	resultmap.put("data", result);
    	return new ResponseEntity<>(resultmap,HttpStatus.OK); 
    }
}
