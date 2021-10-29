package com.web.curation.member;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class MemberService {

    private UserDao userDao;

    //회원가입한 사용자인지 판별
    public String getUidByEmail(String email){
        Optional<User> user = userDao.getUserByEmail(email);
        if(user.isPresent()){
            return user.get().getUid();
        }
        return null;
    }
    public void joinUser(String email){
        long randomNum = (long)(Math.random() * (long)Math.pow(10, 12));
        String uid = "U"+randomNum;

        User user = User.builder()
                .uid(uid)
                .email(email)
                .build();

        userDao.insert(user);
    }
}
