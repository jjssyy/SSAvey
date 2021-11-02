package com.web.curation.template;

import com.web.curation.survey.Survey;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/template")
public class TemplateController {

    private TemplateService templateService;

    @PostMapping()
    public ResponseEntity<Map<String, Object>> createTemplate(@RequestBody Template template){
        Map<String, Object> resultmap=new HashMap<String, Object>();

        templateService.createTemplate(template);

        resultmap.put("status", HttpStatus.OK);
        resultmap.put("message", "success");
        return new ResponseEntity<>(resultmap,HttpStatus.OK);
    }
}
