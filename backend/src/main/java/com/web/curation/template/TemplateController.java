package com.web.curation.template;

import com.web.curation.survey.Survey;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
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

    @GetMapping()
    public ResponseEntity<Map<String, Object>> getTemplateList(@RequestParam String uid){
        Map<String, Object> resultmap=new HashMap<String, Object>();

        List<Template> data = templateService.getUserTemplates(uid);

        resultmap.put("status", HttpStatus.OK);
        resultmap.put("data", data);
        resultmap.put("message", "success");

        return new ResponseEntity<>(resultmap,HttpStatus.OK);
    }

    @GetMapping("/{tid}")
    public ResponseEntity<Map<String, Object>> getTemplate(@PathVariable String tid){
        Map<String, Object> resultmap=new HashMap<String, Object>();

        Template data = templateService.getTemplate(tid);

        resultmap.put("status", HttpStatus.OK);
        resultmap.put("data", data);
        resultmap.put("message", "success");

        return new ResponseEntity<>(resultmap,HttpStatus.OK);
    }

    @DeleteMapping("/{tid}")
    public ResponseEntity<Map<String, Object>> deleteTemplate(@PathVariable String tid, @RequestParam String uid){
        Map<String, Object> resultmap=new HashMap<String, Object>();

        log.info("tid : " + tid);
        log.info("uid : " + uid);
        templateService.deleteTemplate(tid, uid);

        resultmap.put("status", HttpStatus.OK);
        resultmap.put("message", "success");

        return new ResponseEntity<>(resultmap,HttpStatus.OK);
    }
}
