package com.web.curation.alarm;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RestController
@AllArgsConstructor
@RequestMapping("/alarm")
public class AlarmController {
    private AlarmService alarmService;

    @GetMapping("/make")
    public ResponseEntity<Map<String, Object>> kakaoLogin(@RequestParam String name) {
        Map<String, Object> resultMap = new HashMap<>();

        LocalDateTime time = LocalDateTime.of(2021, 10, 29, 14, 30);
        alarmService.setAlarm(name, time);

        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @GetMapping("/edit")
    public ResponseEntity<Map<String, Object>> kakaoLoin(@RequestParam String name) {
        Map<String, Object> resultMap = new HashMap<>();

        LocalDateTime time = LocalDateTime.of(2021, 10, 29, 14, 31);
        alarmService.editAlarm(name, time);

        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }
}
