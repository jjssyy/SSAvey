package com.web.curation.mySurvey;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.survey.Survey;
import com.web.curation.survey.SurveyService;

@Slf4j
@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/my-survey")
public class MySurveyController {

    private MySurveyService mySurveyService;
    
    private SurveyService surveyService;
	
	 @GetMapping("/{uid}/state/{state}")
	    public ResponseEntity<Map<String, Object>> getMySurvey(
	    		@PathVariable String uid,
	    		@PathVariable String state
	    		){
	    	Map<String, Object> resultmap=new HashMap<String, Object>();
	    	List<Survey> result=surveyService.getMySurvey(state, uid);
	    	
	    	resultmap.put("status", HttpStatus.OK);
	    	resultmap.put("data", result);
	    	return new ResponseEntity<>(resultmap,HttpStatus.OK); 	
	    }
}
