package com.web.curation.survey;

import com.web.curation.alarm.AlarmService;
import com.web.curation.error.CustomException;
import com.web.curation.error.ErrorCode;
import com.web.curation.member.User;
import com.web.curation.member.UserDao;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class SurveyService {
	
	private SurveyDao surveyDao;
	private AlarmService alarmService;
	private UserDao userDao;
	
	
	
	public void createSurvey(Survey survey) {
		survey = surveyDao.save(survey);
		alarmService.setAlarmSchdule(survey.getSid(), survey.getStart_date(), survey.getEnd_date());
		//할당된 설문,만든 설문 분배하는 작업
		List<String> target=survey.getTarget();
		List<String> share=survey.getShare();
		//할당된 설문 분배하는작업
		for(int i=0;i<target.size();i++) {
			String temp=target.get(i);
			User tmp= userDao.findById(temp)
             .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
			List<String> target_survey=tmp.getSurvey();
			target_survey.add(survey.getSid());
			tmp.setSurvey(target_survey);
			userDao.save(tmp);
		}
		for(int i=0;i<share.size();i++) {
			String temp=share.get(i);
			User tmp= userDao.findById(temp)
             .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
			List<String> my_survey=tmp.getMySurvey();
			my_survey.add(survey.getSid());
			tmp.setMySurvey(my_survey);
			userDao.save(tmp);
		}
	}
	
	public List<Survey> getSurvey(String state,String uid){
		List<Survey> result=new ArrayList<Survey>();
		User user=userDao.findById(uid)
	             .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
		//자신에게 할당된 설문조사중 state와 똑같은 설문지 찾기
		List<String> temp=user.getSurvey();
		List<String> answer_survey=user.getAnswer_survey();
		if(state.equals("COMPLETED")&&answer_survey!=null) {
			for(int i=0;i<answer_survey.size();i++) {
				Survey tmp_survey=surveyDao.findById(answer_survey.get(i))
						.orElseThrow(()-> new CustomException(ErrorCode.SURVEY_NOT_FOUND));
				result.add(tmp_survey);
			}
			Collections.sort(result,(a,b)-> b.getEnd_date().compareTo(a.getEnd_date()));
		}
		if(state.equals("COMPLETED")&&answer_survey!=null) {
			for(int i=0;i<temp.size();i++) {
				Survey tmp_survey=surveyDao.findById(answer_survey.get(i))
						.orElseThrow(()-> new CustomException(ErrorCode.SURVEY_NOT_FOUND));
				result.add(tmp_survey);
			}
			Collections.sort(result,(a,b)-> b.getEnd_date().compareTo(a.getEnd_date()));
		}
		if(temp!=null&&state.equals("PROCEEDING")) {
			for(int i=0;i<temp.size();i++) {
				Survey tmp_survey=surveyDao.findById(temp.get(i))
						.orElseThrow(()-> new CustomException(ErrorCode.SURVEY_NOT_FOUND));
				
				if(tmp_survey.getState().toString().equals(state)) {
					result.add(tmp_survey);
				}			
			}
			Collections.sort(result,(a,b)-> a.getEnd_date().compareTo(b.getEnd_date()));
		}
		if(temp!=null&&state.equals("EXPECTED")) {
			for(int i=0;i<temp.size();i++) {
				Survey tmp_survey=surveyDao.findById(temp.get(i))
						.orElseThrow(()-> new CustomException(ErrorCode.SURVEY_NOT_FOUND));
				
				if(tmp_survey.getState().toString().equals(state)) {
					result.add(tmp_survey);
				}			
			}
			Collections.sort(result,(a,b)-> a.getStart_date().compareTo(b.getStart_date()));
		}
		return result; 
	}
	
	public List<Survey> getMySurvey(String state, String uid){
		List<Survey> result=new ArrayList<Survey>();
		User user=userDao.findById(uid)
				.orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
		//자신이 만든 설문 가져오기
		List<String> temp=user.getMySurvey();
		if(temp==null)return result;
		for(int i=0;i<temp.size();i++) {
			Survey tmp_survey=surveyDao.findById(temp.get(i))
					.orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));;
			
			if(tmp_survey.getState().toString().equals(state)) {
				result.add(tmp_survey);
			}	
		}
		return result;
	}
	public Survey getSurveyInfo(String sid) {
		Survey tmp_survey=surveyDao.findById(sid)
				.orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));;
		
		
		return tmp_survey;
		
	}
	
	
}
