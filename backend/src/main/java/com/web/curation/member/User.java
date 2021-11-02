package com.web.curation.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Document(collection = "User")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

//    @MongoId(targetType = FieldType.STRING)
    @Id
    private String uid;

    private String email;

    private String file_path;

    private String name;

    private String position;

    private int generation;

    private String area;

    private String group;

    private String team;

    private String team_roll;

    private List<String> template;

    private List<String> mySurvey;

    private List<String> survey;
}
