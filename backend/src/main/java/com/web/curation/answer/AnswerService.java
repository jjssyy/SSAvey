package com.web.curation.answer;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.web.curation.error.CustomException;
import com.web.curation.error.ErrorCode;
import com.web.curation.member.User;
import com.web.curation.member.UserDao;
import com.web.curation.survey.Survey;
import com.web.curation.survey.SurveyDao;
import com.web.curation.survey.SurveyService;

@Slf4j
@AllArgsConstructor
@Service
public class AnswerService {
	
	private SurveyDao surveyDao;
	private SurveyService surveyService;
	private SurveyAnswerDao surveyAnswerDao;
	private UserDao userDao;
	
	
	public SurveyAnswer GetSurveyAnswer(String sid,String uid) {
		List<Survey>  tmp=surveyService.getSurvey("COMPLETED", uid);
		for(int i=0;i<tmp.size();i++) {
			Survey temp_Survey=tmp.get(i);
			if(temp_Survey.getSid().equals(sid)) {
				String temp_sid=temp_Survey.getSid();
				SurveyAnswer temp_SurveyAnswer=surveyAnswerDao.findById(temp_sid+uid)
						.orElseThrow(()-> new CustomException(ErrorCode.SURVEYANSWER_NOT_FOUND));
				
				return temp_SurveyAnswer;
			}
		}
		return null;
	}  
	public void CreateSurveyAnswer(SurveyAnswer surveyAnswer,String sid,String uid) {
		surveyAnswer.setAid(sid+uid);
		surveyAnswerDao.save(surveyAnswer);
		
	}
	
	public boolean CheckSurveyAnswer(String sid,String uid) {
		User user  = userDao.findById(uid)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
		for(int i=0;i<user.getSurvey().size();i++) {
			if(user.getSurvey().get(i).equals(sid))return true;
		}
		return false;
		
		
	}
	
}
