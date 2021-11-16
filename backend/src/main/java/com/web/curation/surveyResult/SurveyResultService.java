package com.web.curation.surveyResult;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import com.web.curation.answer.SurveyAnswer;
import com.web.curation.answer.SurveyAnswerDao;
import com.web.curation.error.CustomException;
import com.web.curation.error.ErrorCode;
import com.web.curation.member.ConvertUid;
import com.web.curation.member.User;
import com.web.curation.member.UserDao;
import com.web.curation.survey.Survey;
import com.web.curation.survey.SurveyAnswerDto;
import com.web.curation.survey.SurveyDao;
import com.web.curation.survey.SurveyDto;
import com.web.curation.survey.UserAnswer;

@Slf4j
@AllArgsConstructor
@Service
public class SurveyResultService {
	
	private SurveyDao surveyDao;
	
	private UserDao userDao;
	private SurveyAnswerDao surveyAnswerDao;
	
	public SurveyDto getSurveyResult(String survey_id) {
		SurveyDto result =new SurveyDto();
		Survey tmp_survey=surveyDao.findById(survey_id)
				.orElseThrow(() -> new CustomException(ErrorCode.SURVEY_NOT_FOUND));
		//SurveyDto 생성과정
		result.setSid(tmp_survey.getSid());
		result.setTitle(tmp_survey.getTitle());
		result.setExplain(tmp_survey.getExplain());
		result.setAnony(tmp_survey.isAnony());
		result.setWriter(tmp_survey.getWriter());
		result.setStart_date(tmp_survey.getStart_date());
		result.setEnd_date(tmp_survey.getEnd_date());
		result.setQuestion(tmp_survey.getQuestion());
		result.setShare(tmp_survey.getShare());
		result.setTarget(tmp_survey.getTarget());
		result.setState(tmp_survey.getState());
		result.setUse_template(tmp_survey.isUse_template());
		result.setTemplate(tmp_survey.getTemplate());
		
		//UID를 별명에 맞게 변경하는 과정.
		List<ConvertUid> inComplete=new ArrayList<ConvertUid>();
		List<ConvertUid> Complete = new ArrayList<ConvertUid>();
		if(tmp_survey.getIncomplete()!=null) {
			for(int i=0;i<tmp_survey.getIncomplete().size();i++) {
				String tmp_uid=tmp_survey.getIncomplete().get(i);
				ConvertUid tmp_ConvertUid=convertUid(tmp_uid);
				inComplete.add(tmp_ConvertUid);
			}			
		}
		if(tmp_survey.getComplete()!=null) {
			for(int i=0;i<tmp_survey.getComplete().size();i++) {
				String tmp_uid=tmp_survey.getComplete().get(i);
				ConvertUid tmp_ConvertUid=convertUid(tmp_uid);
				Complete.add(tmp_ConvertUid);
			}			
		}
		
		result.setIncomplete(inComplete);
		result.setComplete(Complete);
		//설문대답 가져오기..
		List<SurveyAnswerDto> tmp_SurveyAnswerDtos=new ArrayList<SurveyAnswerDto>();
		//설문문항마다 넘버 생성.
		for(int i=0;i<tmp_survey.getQuestion().size();i++) {
			//추가하기 위한 객체
			SurveyAnswerDto temp_SurveyAnswerDto=new SurveyAnswerDto();
			temp_SurveyAnswerDto.setQ_number(tmp_survey.getQuestion().get(i).getQ_number());
			temp_SurveyAnswerDto.setAnswers(new ArrayList<UserAnswer>());
			tmp_SurveyAnswerDtos.add(temp_SurveyAnswerDto);
		}
		
		
		for(int i=0;i<Complete.size();i++) {
			//레디스에서 값을 불러올 때 중복체크하는 과정 필요?.
			SurveyAnswer tmp_SurveyAnswer=surveyAnswerDao.findById(survey_id+Complete.get(i).getUid())
					.orElseThrow(() -> new CustomException(ErrorCode.SURVEYANSWER_NOT_FOUND));
			UserAnswer tmp_UserAnswer=new UserAnswer();
			tmp_UserAnswer.setName(Complete.get(i).getName());
			tmp_UserAnswer.setUid(Complete.get(i).getUid());
			tmp_UserAnswer.setAlias(Complete.get(i).getAlias());
			tmp_UserAnswer.setArea(Complete.get(i).getArea());
			tmp_UserAnswer.setGeneration(Complete.get(i).getGeneration());
			tmp_UserAnswer.setTeam(Complete.get(i).getTeam());
			tmp_UserAnswer.setPosition(Complete.get(i).getPosition());
			for(int j=0;j<tmp_SurveyAnswer.getAnswer_question().size();j++) {
				String Q_number=tmp_SurveyAnswer.getAnswer_question().get(j).getQ_number();
				for(int k=0;k<tmp_SurveyAnswerDtos.size();k++) {
					if(Q_number.equals(tmp_SurveyAnswerDtos.get(k).getQ_number())) {
						tmp_UserAnswer.setAnswer(tmp_SurveyAnswer.getAnswer_question().get(j).getAnswer());
						List<UserAnswer> SurveyAnswer=tmp_SurveyAnswerDtos.get(k).getAnswers();
						SurveyAnswer.add(tmp_UserAnswer);
						break;
					}									
				}
			}
			
		}
		result.setAnswers(tmp_SurveyAnswerDtos);


		for(SurveyAnswerDto surveyAnswer : result.getAnswers()){
//			StringBuilder totalAnswerEachQuestions = new StringBuilder();
			List<String> slicedWords = new ArrayList<>();

			for(UserAnswer answers : surveyAnswer.getAnswers()){
				for(String answer : answers.getAnswer()){
					if(!answer.matches("-?\\d+")){
//						totalAnswerEachQuestions.append(answer);
						slicedWords.add(answer);
					}
				}
			}

//			List<String> slicedWords = sliceWords(totalAnswerEachQuestions.toString());
			surveyAnswer.setSlicedWords(slicedWords);
		}
		
		return result;
	}

	private List<String> sliceWords(String totalAnswerEachQuestions) {
		log.info("GCP 단어 slicing : " + totalAnswerEachQuestions);
		List<String> slicedWords = new ArrayList<>();

		JSONObject data=new JSONObject();
		JSONObject document=new JSONObject();
		document.put("type", "PLAIN_TEXT");
		document.put("language", "ko");
		document.put("content", totalAnswerEachQuestions);
		data.put("document", document);
		data.put("encodingType", "UTF8");

		HttpURLConnection conn=null;
		String reqURL= "https://language.googleapis.com/v1/documents:analyzeEntities?key=AIzaSyD1h3QKUFWEx1A2lQsVouHZ7cq44r8-gMU";
		try {
			URL url = new URL(reqURL);
			conn=(HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setDoOutput(true);
			conn.connect();

			//Post방식으로 스트링을통한 JSON전송
			BufferedWriter bw= new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			bw.write(data.toString());
			bw.flush();
			bw.close();

			//서버에서 보낸 응답 데이터 수신 받기
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String line = "";
			StringBuilder result = new StringBuilder();

			while ((line = br.readLine()) != null) {
				result.append(line);
			}

			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(result.toString());

			JsonArray entities = element.getAsJsonObject().get("entities").getAsJsonArray();

			for(JsonElement el : entities){
				JsonArray mentions = el.getAsJsonObject().get("mentions").getAsJsonArray();

				for(JsonElement mention : mentions){
					String content = mention.getAsJsonObject().get("text").getAsJsonObject().get("content").getAsString();
					slicedWords.add(content);
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}

		return slicedWords;
	}

	public ConvertUid convertUid(String uid) {
		ConvertUid result=new ConvertUid();
		User tmp_user=userDao.findById(uid)
				.orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
		if(tmp_user.getPosition().equals("교육생")) {
			result.setUid(tmp_user.getUid());
			result.setName(tmp_user.getName());
			result.setPosition(tmp_user.getPosition());
			result.setGeneration(tmp_user.getGeneration());
			result.setArea(tmp_user.getArea());
			result.setGroup(tmp_user.getGroup());
			result.setTeam(tmp_user.getTeam());
			result.setAlias(tmp_user.getGeneration()+"기/"+tmp_user.getArea()+"/"+tmp_user.getGroup());
		}else {
			result.setUid(tmp_user.getUid());
			result.setName(tmp_user.getName());
			result.setPosition(tmp_user.getPosition());
			result.setAlias(tmp_user.getPosition());
		}		
		return result;
	}
	
	public String createRestTimeMessage(String sid){
		Survey survey = surveyDao.findById(sid)
				.orElseThrow(() -> new CustomException(ErrorCode.SURVEY_NOT_FOUND));

		Duration remainDuration = Duration.between(LocalDateTime.now(), survey.getEnd_date());
		return "#### " + survey.getTitle() + " 설문\n남은 기간: " +
				remainDuration.toDays() + "일 " + (remainDuration.toHours()  - remainDuration.toDays() * 24) + "시 "
				+ (remainDuration.toMinutes() - remainDuration.toHours() * 60) + "분\n:running_man: 서둘러주세요";
	}
}
