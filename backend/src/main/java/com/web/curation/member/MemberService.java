package com.web.curation.member;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.web.curation.error.CustomException;
import com.web.curation.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class MemberService {

    private UserDao userDao;
    private final String TOKEN = "uieydcqsspf87n6d7xo3kugp7r";

    //회원가입한 사용자인지 판별
    public String getUidByEmail(String email){
        Optional<User> user = userDao.getUserByEmail(email);
        if(user.isPresent()){
            return user.get().getUid();
        }
        return null;
    }
    public void joinUser(Map<String, String> map){
        String uid = map.get("uid");
        String email = map.get("email");
        String token = map.get("token");

        User user = User.builder()
                .uid(uid)
                .email(email)
                .token(token)
                .build();

        userDao.save(user);
    }

    public User findUser(String uid){
        User user  = userDao.findById(uid)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        return user;
    }

    public void updateUser(User user){
        User dbUser = userDao.findById(user.getUid())
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        user.setToken(dbUser.getToken());
        userDao.save(user);
    }

    public List<User> searchUser(String name, String position, int generation, String area,
                                 String group, String team, String team_roll){
        List<User> list = userDao.searchUser(name, position, generation, area, group, team, team_roll);

        return list;
    }

    public void getUserImage(String uid) {
        HttpURLConnection conn = null;

        MultipartFile a;
        a.

        try {
            URL url = new URL("https://meeting.ssafy.com/api/v4/users/" + uid + "/image");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + TOKEN);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            bw.flush();

            BufferedInputStream bufferedInputStream = new BufferedInputStream(conn.getInputStream());
            OutputStream outputStream = new FileOutputStream(file);

            int size;
            int len = 0;
            byte[] buf = new byte[1024];
            while ((size = bufferedInputStream.read(buf)) != -1) {
                len += size;
                outputStream.write(buf, 0, size);
            }

            outputStream.close();
            bufferedInputStream.close();

            // Return file object
            return file;



            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";

            StringBuilder result = new StringBuilder();

            while ((line = br.readLine()) != null) {
                result.append(line);
            }
//            JsonParser parser = new JsonParser();
//            JsonElement element = parser.parse(result.toString());
//
//            channelId = element.getAsJsonObject().get("id").getAsString();
            System.out.println(result.toString());

            br.close();
            bw.close();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
