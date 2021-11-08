package com.web.curation.survey;

import com.web.curation.member.ConvertUid;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAnswer {
	
	private String uid;

    private String email;

    private String name;

    private String position;

    private int generation;

    private String area;

    private String group;

    private String team;
    
    private String alias;
    
    private String answer;
	

}
