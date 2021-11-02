package com.web.curation.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class UserDaoImpl implements UserCustomDao{

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<User> searchUser(String name, String position, int generation, String area, String group, String team, String team_roll) {
        Query query = new Query();

        if(name != null){
            Criteria nameCriteria = Criteria.where("name").regex(name);
            query.addCriteria(nameCriteria);
        }
        if(position != null){
            Criteria positionCriteria = Criteria.where("position").is(position);
            query.addCriteria(positionCriteria);
        }
        if(generation != 0){
            Criteria generationCriteria = Criteria.where("generation").is(generation);
            query.addCriteria(generationCriteria);
        }
        if(area != null){
            Criteria areaCriteria = Criteria.where("area").is(area);
            query.addCriteria(areaCriteria);
        }
        if(group != null){
            Criteria groupCriteria = Criteria.where("group").is(group);
            query.addCriteria(groupCriteria);
        }
        if(team != null){
            Criteria teamCriteria = Criteria.where("team").is(team);
            query.addCriteria(teamCriteria);
        }
        if(team_roll != null){
            Criteria team_rollCriteria = Criteria.where("team_roll").is(team_roll);
            query.addCriteria(team_rollCriteria);
        }

        List<User> list = mongoTemplate.find(query, User.class);

        return list;
    }
}
