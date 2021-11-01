package com.web.curation.template;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.web.curation.survey.Survey;

@Document
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Template {
	@Id
	private String tid;
	
	private boolean is_deleted;
	
	private String t_title;
	
	private String t_explain;
	
	private List<Survey> survey;
	
	
	
	
	
}
