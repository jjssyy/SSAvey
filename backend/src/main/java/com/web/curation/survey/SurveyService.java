package com.web.curation.survey;

import com.web.curation.alarm.AlarmService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class SurveyService {
	
	private SurveyDao surveyDao;
	private AlarmService alarmService;
	
	public void createSurvey(Survey survey) {
		survey = surveyDao.save(survey);

		alarmService.setAlarmSchdule(survey.getSid(), survey.getStart_date());
	}
	
}
