package com.web.curation.member;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.web.curation.error.CustomException;
import com.web.curation.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
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
        Map<String, Object> resultMap = new HashMap<>();
        //여기서 파악하고 변수명 email로 설정해서 받기
        String email = getMattermostEmail(loginDto);

        if(email.contains("400")){
            throw new CustomException(ErrorCode.MATTERMOST_BAD_REQUEST);
        }else if(email.contains("401")){
            throw new CustomException(ErrorCode.MATTERMOST_UNAUTHORIZED);
        }else if(email.contains("403")){
            throw new CustomException(ErrorCode.MATTERMOST_FORBIDDEN);
        }else if(email.contains("501")){
            throw new CustomException(ErrorCode.MATTERMOST_NOT_IMPLEMENTED);
        }

        String uid = memberService.getUidByEmail(email);

        if(uid == null){
            memberService.joinUser(email);
            String newUid = memberService.getUidByEmail(email);
            resultMap.put("Uid", newUid);
            resultMap.put("isSignUp", false);
        }else{
            resultMap.put("Uid", uid);
            resultMap.put("isSignUp", true);
        }
        return new ResponseEntity<>(resultMap,HttpStatus.OK);
    }

    private String getMattermostEmail(LoginDto loginDto){
        String email="";
        HttpURLConnection conn = null;

        try {
            URL url = new URL("https://meeting.ssafy.com/api/v4/users/login");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-type", "application/json");

            JSONObject loginData = new JSONObject();
            loginData.put("login_id",loginDto.getLogin_id());
            loginData.put("password",loginDto.getPassword());

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));

            bw.write(loginData.toString());
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

            email = element.getAsJsonObject().get("email").getAsString();

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
        return email;
    }

    @GetMapping("/{uid}")
    public ResponseEntity<Map<String, Object>> findUser(@PathVariable String uid){
        Map<String, Object> resultMap = new HashMap<>();
        User user = memberService.findUser(uid);

        resultMap.put("message","회원 정보 확인");
        resultMap.put("user", user);

        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<Map<String, Object>> updateUser(@RequestBody User user){
        Map<String, Object> resultMap = new HashMap<>();

        memberService.updateUser(user);

        resultMap.put("message", "회원 정보 수정 완료");

        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchUser(@RequestParam(required = false) String name,
                                                          @RequestParam(required = false) String position,
                                                          @RequestParam(required = false) Integer generation,
                                                          @RequestParam(required = false) String area,
                                                          @RequestParam(required = false) String group,
                                                          @RequestParam(required = false) String team,
                                                          @RequestParam(required = false) String team_roll){
        Map<String, Object> resultMap = new HashMap<>();

        if(generation == null){
            generation = 0;
        }

        List<User> list = memberService.searchUser(name, position, generation, area, group, team, team_roll);

        resultMap.put("검색 결과", list);
        if(list.size()==0){
            resultMap.put("message","검색 결과가 없습니다.");
        }
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }
}
