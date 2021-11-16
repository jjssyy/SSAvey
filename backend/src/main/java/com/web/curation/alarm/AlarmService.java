package com.web.curation.alarm;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.web.curation.error.CustomException;
import com.web.curation.error.ErrorCode;
import com.web.curation.member.User;
import com.web.curation.member.UserDao;
import com.web.curation.survey.State;
import com.web.curation.survey.Survey;
import com.web.curation.survey.SurveyDao;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@AllArgsConstructor
@Service
public class AlarmService{
    private SurveyDao surveyDao;
    private UserDao userDao;
    private HashMap<String, ThreadPoolTaskScheduler> schedulerHashMap;


    public void setAlarmSchdule(String sid, LocalDateTime startDateTime, LocalDateTime endDateTime){
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.initialize();
        threadPoolTaskScheduler.setThreadNamePrefix(sid + "-");


        Date startDate = Timestamp.valueOf(startDateTime);
        Date endDate = Timestamp.valueOf(endDateTime);
        log.info("startDate : " + startDate);
        log.info("endDate : " + endDate);

        threadPoolTaskScheduler.schedule(startSurvey(), startDate);
        threadPoolTaskScheduler.schedule(closeSurvey(), endDate);

        schedulerHashMap.put(sid, threadPoolTaskScheduler);
    }

    public void editAlarmSchedule(String sid, LocalDateTime startDateTime, LocalDateTime endDateTime){
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.initialize();
        threadPoolTaskScheduler.setThreadNamePrefix(sid + "-");

        Date startDate = Timestamp.valueOf(startDateTime);
        Date endDate = Timestamp.valueOf(endDateTime);
        log.info("startDate : " + startDate);
        log.info("endDate : " + endDate);

        threadPoolTaskScheduler.schedule(startSurvey(), startDate);
        threadPoolTaskScheduler.schedule(closeSurvey(), endDate);

        schedulerHashMap.get(sid).shutdown();
        schedulerHashMap.put(sid, threadPoolTaskScheduler);
    }

    private Runnable startSurvey() {
        return () -> {
            String fullName = Thread.currentThread().getName();
            String prefixName = fullName.substring(0, fullName.indexOf("-"));
            log.info("Survey Start, threadName : " + fullName);

            Survey survey = surveyDao.findById(prefixName).get();
            survey.setState(State.PROCEEDING);

            String message = "#### " + survey.getTitle() + " 설문 시작\n기간: "
                    + survey.getStart_date().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")) + " ~ "
                    + survey.getEnd_date().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));

            mattermostAlarm(survey.getWriter(), survey.getTarget(), message);
            surveyDao.save(survey);
        };
    }

    private Runnable closeSurvey() {
        return () -> {
            String fullName = Thread.currentThread().getName();
            String prefixName = fullName.substring(0, fullName.indexOf("-"));
            log.info("Survey End, threadName : " + fullName);

            Survey survey = surveyDao.findById(prefixName).get();
            survey.setState(State.COMPLETED);
            surveyDao.save(survey);

            schedulerHashMap.get(prefixName).shutdown();
            schedulerHashMap.remove(prefixName);
        };
    }

    public void mattermostAlarm(String sendMember, List<String> targetMembers, String message){
        User user = userDao.findById(sendMember)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        String token = user.getToken();

        log.info("postAlarm " + sendMember + " -> " + Arrays.toString(targetMembers.toArray()));
        log.info("postAlarm message : " + message);

        for(String targetMember : targetMembers){
            String channelId = getDirectChannelId(token, sendMember, targetMember);
            sendPosts(token, channelId, message);
        }
    }

    private String getDirectChannelId(String token, String sendMember, String targetMember) {
        String channelId = null;
        HttpURLConnection conn = null;

        try {
            URL url = new URL("https://meeting.ssafy.com/api/v4/channels/direct");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-type", "application/json");
            conn.setRequestProperty("Authorization", "Bearer " + token);

            JsonArray data = new JsonArray();
            data.add(sendMember);
            data.add(targetMember);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));

            bw.write(data.toString());
            bw.flush();

            //응답
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";

            StringBuilder result = new StringBuilder();

            while ((line = br.readLine()) != null) {
                result.append(line);
            }
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result.toString());

            channelId = element.getAsJsonObject().get("id").getAsString();

            br.close();
            bw.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            try {
                int responseCode = ((HttpURLConnection)conn).getResponseCode();
                return Integer.toString(responseCode);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return channelId;
    }

    private void sendPosts(String token, String channelId, String message) {
        HttpURLConnection conn = null;

        try {
            URL url = new URL("https://meeting.ssafy.com/api/v4/posts");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-type", "application/json");
            conn.setRequestProperty("Authorization", "Bearer " + token);

            JSONObject data = new JSONObject();
            data.put("message", message);
            data.put("channel_id", channelId);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));

            bw.write(data.toString());
            bw.flush();

            //응답
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";

            StringBuilder result = new StringBuilder();

            while ((line = br.readLine()) != null) {
                result.append(line);
            }
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result.toString());

            br.close();
            bw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
