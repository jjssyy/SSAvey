package com.web.curation.member;

import java.util.List;

public interface UserCustomDao {
    List<User> searchUser(String name, String position, int generation,
                          String area, String group, String team, String team_roll);
}
