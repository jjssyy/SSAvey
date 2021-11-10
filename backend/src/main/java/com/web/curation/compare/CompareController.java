package com.web.curation.compare;

import com.web.curation.member.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/compare")
public class CompareController {

    private CompareService compareService;

    @GetMapping("/{tid}")
    public ResponseEntity<Map<String, Object>> getCertainPeriodSurveys(@PathVariable String tid, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start_date, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end_date){
        Map<String, Object> resultMap = new HashMap<>();
        List<CompareSurvey> list = compareService.getCertainPeriodSurveys(tid, start_date, end_date);

        resultMap.put("message","특정 기간 설문 리스트");
        resultMap.put("data", list);

        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }
}
