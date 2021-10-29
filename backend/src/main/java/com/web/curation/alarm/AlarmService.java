package com.web.curation.alarm;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

@Slf4j
@AllArgsConstructor
@Service
public class AlarmService{
    private ArrayList<ThreadPoolTaskScheduler> schedulerArr = new ArrayList<>();

    public void setAlarm(String sid, LocalDateTime localDateTime){
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.initialize();
        threadPoolTaskScheduler.setThreadNamePrefix(sid);

        Date date = Timestamp.valueOf(localDateTime);
        threadPoolTaskScheduler.schedule(getRunnable(), date);

        schedulerArr.add(threadPoolTaskScheduler);
    }

    public void editAlarm(String sid, LocalDateTime localDateTime){
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
            System.out.println(Thread.currentThread().getName());
            // TODO MM으로 알림 전송

            for(int i = 0; i < schedulerArr.size(); i++){
                if(Thread.currentThread().getName().contains(schedulerArr.get(i).getThreadNamePrefix())){
                    schedulerArr.get(i).shutdown();
                    schedulerArr.remove(i);
                    break;
                }
            }
        };
    }
}
