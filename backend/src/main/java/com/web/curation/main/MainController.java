package com.web.curation.main;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
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
@RequestMapping("/main")
public class MainController {

    private MainService mainService;
    
    private SurveyService surveyService;
    
    @GetMapping("/count/{uid}")
    public ResponseEntity<Map<String, Object>> countSurvey(
    		@PathVariable String uid
    		){
    	Map<String, Object> resultmap=new HashMap<String, Object>();
    	List<Survey> result=surveyService.getSurvey("PROCEEDING",uid);
    	int cnt=result.size();
    	
    	resultmap.put("status", HttpStatus.OK);
    	resultmap.put("data", cnt);
    	return new ResponseEntity<>(resultmap,HttpStatus.OK); 
    }
    
    @GetMapping("/{uid}")
    public ResponseEntity<Map<String,Object>> mainSurvey(
    		@PathVariable String uid
    		){
    	Map<String, Object> resultmap=new HashMap<String, Object>();
    	List<Survey> EX_Survey=new ArrayList<Survey>();
    	List<Survey> PRO_Survey=new ArrayList<Survey>();
    	List<Survey> COM_Survey=new ArrayList<Survey>();
    	EX_Survey=surveyService.getSurvey("EXPECTED",uid);
    	PRO_Survey=surveyService.getSurvey("PROCEEDING",uid);
    	COM_Survey=surveyService.getSurvey("COMPLETED",uid);
    	
    	resultmap.put("status", HttpStatus.OK);
    	resultmap.put("EXPECTED", EX_Survey);
    	resultmap.put("PROCEEDING", PRO_Survey);
    	resultmap.put("COMPLETED", COM_Survey);
    	return new ResponseEntity<>(resultmap,HttpStatus.OK); 
 
    }
}
