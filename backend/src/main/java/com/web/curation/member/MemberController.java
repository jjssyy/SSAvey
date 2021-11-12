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

        Map<String, String> map = getMattermostInfo(loginDto);
        String uid = map.get("uid");
        String email = map.get("email");

        String stored_uid = memberService.getUidByEmail(email);

        if(stored_uid == null){
            memberService.joinUser(map);
            resultMap.put("Uid", uid);
            resultMap.put("isSignUp", false);
        }else{
            resultMap.put("Uid", uid);
            resultMap.put("isSignUp", true);
        }
        return new ResponseEntity<>(resultMap,HttpStatus.OK);
    }

    private Map<String,String> getMattermostInfo(LoginDto loginDto){
        String email="";
        HttpURLConnection conn = null;
        Map<String, String> map = new HashMap();

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

            String uid = element.getAsJsonObject().get("id").getAsString();
            email = element.getAsJsonObject().get("email").getAsString();

            map.put("uid", uid);
            map.put("email", email);

            br.close();
            bw.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            try {
                int responseCode = ((HttpURLConnection)conn).getResponseCode();
                if(responseCode==400){
                    throw new CustomException(ErrorCode.MATTERMOST_BAD_REQUEST);
                }else if(responseCode==401){
                    throw new CustomException(ErrorCode.MATTERMOST_UNAUTHORIZED);
                }else if(responseCode==403){
                    throw new CustomException(ErrorCode.MATTERMOST_FORBIDDEN);
                }else if(responseCode==501){
                    throw new CustomException(ErrorCode.MATTERMOST_NOT_IMPLEMENTED);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return map;
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

    @GetMapping("/image")
    public ResponseEntity<Map<String, Object>> getUserImage(@RequestParam String uid){
        Map<String, Object> resultMap = new HashMap<>();

        memberService.getUserImage(uid);


        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }
}
