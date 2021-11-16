package com.web.curation.surveyResult;

import com.web.curation.alarm.AlarmService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.web.curation.survey.SurveyDto;

@Slf4j
@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/survey-result")
public class SurveyResultController {

    private SurveyResultService surveyResultService;
    private AlarmService alarmService;

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

	@PostMapping("/{sid}/alarm/{uid}")
	public ResponseEntity<Map<String, Object>> postAlarm(@PathVariable String sid, @PathVariable String uid, @RequestBody List<String> target){
		Map<String, Object> resultmap=new HashMap<String, Object>();

		String message = surveyResultService.createRestTimeMessage(sid);
		alarmService.mattermostAlarm(uid, target, message);

		resultmap.put("status", HttpStatus.OK);
		resultmap.put("message", "알람 전송");
		return new ResponseEntity<>(resultmap,HttpStatus.OK);
	}

}
