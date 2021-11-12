package com.web.curation.mySurvey;

import com.web.curation.error.CustomException;
import com.web.curation.error.ErrorCode;
import com.web.curation.member.User;
import com.web.curation.member.UserDao;
import com.web.curation.survey.State;
import com.web.curation.survey.Survey;
import com.web.curation.survey.SurveyDao;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class MySurveyService {

    private UserDao userDao;
    private SurveyDao surveyDao;

    public List<MySurveyDto> getMySurveysByState (String uid, State state){
        User user = userDao.findById(uid)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        List<MySurveyDto> result = new ArrayList<>();

        List<String> sidList = user.getMySurvey();
        if(sidList == null){
            return result;
        }
        for(int i=0; i<sidList.size(); i++){
            String sid = sidList.get(i);
            Survey survey = surveyDao.findById(sid)
                    .orElseThrow(() -> new CustomException(ErrorCode.SURVEY_NOT_FOUND));
            if(survey.getState().equals(state)){
                MySurveyDto dto = new MySurveyDto();
                dto.setSid(sid);
                dto.setTitle(survey.getTitle());
                dto.setExplain(survey.getExplain());
                dto.setWriter(survey.getWriter());
                dto.setAnony(survey.isAnony());
                dto.setStart_date(survey.getStart_date());
                dto.setEnd_date(survey.getEnd_date());
                dto.setState(survey.getState());

                result.add(dto);
            }
        }
        return result;
    }
}
