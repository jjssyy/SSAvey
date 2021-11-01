package com.web.curation.member;

import com.web.curation.error.CustomException;
import com.web.curation.error.ErrorCode;
import com.web.curation.error.UnauthorizedException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    public User findUser(String uid){
        User user  = userDao.findById(uid)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        return user;
    }

    public void updateUser(){

    }
}
