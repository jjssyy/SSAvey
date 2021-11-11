package com.web.curation.main;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.web.curation.error.CustomException;
import com.web.curation.error.ErrorCode;
import com.web.curation.member.User;
import com.web.curation.member.UserDao;
import com.web.curation.survey.Survey;
import com.web.curation.survey.SurveyDao;

@Slf4j
@AllArgsConstructor
@Service
public class MainService {
	
	private UserDao userDao;
	private SurveyDao surveyDao;
	
	
	
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
}
