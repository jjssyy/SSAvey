package com.web.curation.member;

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
@RequestMapping("/member")
public class MemberController {

    private MemberService memberService;

    @PostMapping()
    public ResponseEntity<Map<String, Object>> Login(@RequestBody LoginDto loginDto){

        String login_id = loginDto.getLogin_id();

        Map<String, Object> resultMap = new HashMap<>();
        String uid = memberService.getUidByEmail(login_id);

        if(uid == null){
            memberService.joinUser(login_id);
            String newUid = memberService.getUidByEmail(login_id);
            resultMap.put("Uid", newUid);
            resultMap.put("isSignUp", false);
        }else{
            resultMap.put("Uid", uid);
            resultMap.put("isSignUp", true);
        }
        return new ResponseEntity<>(resultMap,HttpStatus.OK);
    }
}
