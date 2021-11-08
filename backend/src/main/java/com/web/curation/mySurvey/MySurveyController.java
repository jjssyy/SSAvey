package com.web.curation.mySurvey;

import com.web.curation.survey.State;
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
	
	@GetMapping("/{uid}/state/{state}")
	public ResponseEntity<Map<String, Object>> getMySurvey( @PathVariable String uid, @PathVariable State state){

		Map<String, Object> resultMap=new HashMap<>();
		List<MySurveyDto> mySurveyList = mySurveyService.getMySurveysByState(uid, state);

		resultMap.put("data", mySurveyList);
		if(mySurveyList.size()==0){
			resultMap.put("message","설문 목록이 없습니다.");
		}
		return new ResponseEntity<>(resultMap,HttpStatus.OK);
	}
}
