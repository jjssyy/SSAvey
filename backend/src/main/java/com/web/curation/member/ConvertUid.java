package com.web.curation.member;

import java.util.List;

import org.springframework.data.annotation.Id;

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
public class ConvertUid {

	    private String uid;

	    private String email;

	    private String name;

	    private String position;

	    private int generation;

	    private String area;

	    private String group;

	    private String team;
	    
	    private String alias;
}
