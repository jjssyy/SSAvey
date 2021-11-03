package com.web.curation.member;

import com.web.curation.error.CustomException;
import com.web.curation.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
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
    public void joinUser(Map<String, String> map){
        String uid = map.get("uid");
        String email = map.get("email");

        User user = User.builder()
                .uid(uid)
                .email(email)
                .build();

        userDao.save(user);
    }

    public User findUser(String uid){
        User user  = userDao.findById(uid)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        return user;
    }

    public void updateUser(User user){
        userDao.findById(user.getUid())
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        userDao.save(user);
    }

    public List<User> searchUser(String name, String position, int generation, String area,
                                 String group, String team, String team_roll){
        List<User> list = userDao.searchUser(name, position, generation, area, group, team, team_roll);

        return list;
    }
}
