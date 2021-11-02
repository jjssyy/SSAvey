package com.web.curation.alarm;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
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
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class AlarmService{
    private final String TOKEN = "uieydcqsspf87n6d7xo3kugp7r";

    private SurveyDao surveyDao;
    private ArrayList<ThreadPoolTaskScheduler> schedulerArr = new ArrayList<>();

    public void setAlarmSchdule(String sid, LocalDateTime localDateTime){
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.initialize();
        threadPoolTaskScheduler.setThreadNamePrefix(sid + "-");

        Date date = Timestamp.valueOf(localDateTime);
        threadPoolTaskScheduler.schedule(getRunnable(), date);

        schedulerArr.add(threadPoolTaskScheduler);
    }

    public void editAlarmSchedule(String sid, LocalDateTime localDateTime){
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.initialize();
        threadPoolTaskScheduler.setThreadNamePrefix(sid);

        Date date = Timestamp.valueOf(localDateTime);
        threadPoolTaskScheduler.schedule(getRunnable(), date);

        for(int i = 0; i < schedulerArr.size(); i++){
            if(sid.equals(schedulerArr.get(i).getThreadNamePrefix())){
                schedulerArr.get(i).shutdown();
                schedulerArr.set(i, threadPoolTaskScheduler);
                break;
            }
        }
    }

    private Runnable getRunnable() {
        return () -> {
            String fullName = Thread.currentThread().getName();
            String prefixName = fullName.substring(0, fullName.indexOf("-"));
            System.out.println(prefixName);

            Optional<Survey> survey = surveyDao.findById(prefixName);
            survey.get().setState(State.PROCEEDING);
            mattermostAlarm(survey.get().getTitle(), survey.get().getEnd_date(), survey.get().getTarget());

            surveyDao.save(survey.get());

            for(int i = 0; i < schedulerArr.size(); i++){
                if(Thread.currentThread().getName().contains(schedulerArr.get(i).getThreadNamePrefix())){
                    schedulerArr.get(i).shutdown();
                    schedulerArr.remove(i);
                    break;
                }
            }
        };
    }

    private void mattermostAlarm(String title, LocalDateTime endDate, List<String> targetMember){
        for(String uid : targetMember){
            String channelId = getDirectChannelId(uid);
            sendPosts(channelId, title, endDate);
        }
    }

    private String getDirectChannelId(String uid) {
        String channelId = null;
        String serverUid="o1ojzjw173d17c3itj67o5k5do";
        HttpURLConnection conn = null;

        try {
            URL url = new URL("https://meeting.ssafy.com/api/v4/channels/direct");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-type", "application/json");
            conn.setRequestProperty("Authorization", "Bearer " + TOKEN);

            JsonArray data = new JsonArray();
            data.add(serverUid);
            data.add(uid);

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

    private void sendPosts(String channelId, String title, LocalDateTime endDate) {
        HttpURLConnection conn = null;

        try {
            URL url = new URL("https://meeting.ssafy.com/api/v4/posts");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-type", "application/json");
            conn.setRequestProperty("Authorization", "Bearer " + TOKEN);

            Duration remainDuration = Duration.between(LocalDateTime.now(), endDate);

            JSONObject data = new JSONObject();
            data.put("message",title + " 설문이 " + remainDuration.toDays() + "일 " + (remainDuration.toHours()  - remainDuration.toDays() * 24) + "시 " + (remainDuration.toMinutes() - remainDuration.toHours() * 60) + "분 남았습니다." );
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
