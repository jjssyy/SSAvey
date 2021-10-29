package com.web.curation.survey;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/survey")
public class SurveyController {

    private SurveyService surveyService;
    
    @PostMapping()
    public ResponseEntity<Map<String, Object>> createSurvey(
    		@RequestBody Survey survey
    		){
    	Map<String, Object> resultmap=new HashMap<String, Object>();
 
    	surveyService.createSurvey(survey);
    	
    	resultmap.put("status", HttpStatus.OK);
    	resultmap.put("data", "success");
    	return new ResponseEntity<>(resultmap,HttpStatus.OK); 
    }
}
