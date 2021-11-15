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
		log.info(uid + " 유저의 완료된 " + sid + " 설문 탐색");
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
		log.info(uid + " 유저의 " + sid + " 설문 답변 기록");
		surveyAnswer.setAid(sid+uid);
		surveyAnswerDao.save(surveyAnswer);
		Survey survey=surveyDao.findById(sid)
				.orElseThrow(()-> new CustomException(ErrorCode.SURVEY_NOT_FOUND));
		//설문완료하면  미완료자에서 완료자로 이동..
		List<String> complete=survey.getComplete();
		List<String> inComplete=survey.getIncomplete();
		complete.add(uid);
		inComplete.remove(uid);
		survey.setComplete(complete);
		survey.setIncomplete(inComplete);
		surveyDao.save(survey);
		
		//유저에게 할당된 설문에서 응답 설문으로 이동과정
		User user  = userDao.findById(uid)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
		List<String> user_survey=user.getSurvey();
		List<String> answer_survey=user.getAnswer_survey();
		user_survey.remove(sid);
		answer_survey.add(sid);
		user.setAnswer_survey(answer_survey);
		user.setSurvey(user_survey);
		userDao.save(user);
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
