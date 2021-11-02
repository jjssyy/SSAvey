package com.web.curation.template;

import com.web.curation.error.CustomException;
import com.web.curation.error.ErrorCode;
import com.web.curation.member.User;
import com.web.curation.member.UserDao;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class TemplateService {
    private TemplateDao templateDao;
    private UserDao userDao;

    public void createTemplate(Template template){
        template = templateDao.save(template);

        User user  = userDao.findById(template.getWriter())
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        user.getTemplate().add(template.getTid());
        userDao.save(user);
    }
}
