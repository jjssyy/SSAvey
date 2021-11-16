package com.web.curation.compare;

import com.web.curation.error.CustomException;
import com.web.curation.error.ErrorCode;
import com.web.curation.survey.Survey;
import com.web.curation.survey.SurveyDao;
import com.web.curation.template.Template;
import com.web.curation.template.TemplateDao;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class CompareService {
    private TemplateDao templateDao;
    private SurveyDao surveyDao;

    public List<CompareSurvey> getCertainPeriodSurveys(String tid, LocalDate startDate, LocalDate endDate){
        log.info("특정 기간 " + tid + " 템플릿을 이용한 설문 리스트");
        log.info(startDate + " - " + endDate);

        List<CompareSurvey> list = new ArrayList<>();
        Template template = templateDao.findById(tid)
                .orElseThrow(() -> new CustomException(ErrorCode.TEMPLATE_NOT_FOUND));

        for(String sid : template.getSurvey()){
            Survey survey = surveyDao.findById(sid)
                    .orElseThrow(() -> new CustomException(ErrorCode.SURVEY_NOT_FOUND));

            LocalDate surveyStartDate = survey.getStart_date().toLocalDate();

            if(startDate.compareTo(surveyStartDate) <= 0 && endDate.compareTo(surveyStartDate) >= 0){
                CompareSurvey compareSurvey = CompareSurvey.builder()
                        .sid(survey.getSid())
                        .title(survey.getTitle())
                        .explain(survey.getExplain())
                        .start_date(survey.getStart_date())
                        .end_date(survey.getEnd_date())
                        .build();

                list.add(compareSurvey);
            }
        }

        return list;
    }
}
