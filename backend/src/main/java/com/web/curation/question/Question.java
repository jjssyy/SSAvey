package com.web.curation.question;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.web.curation.answer.SurveyAnswer;
import com.web.curation.option.Option;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Document(collection ="Question")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Question {
	private int q_number;
	
	private String q_explanation;
	
	private Type q_type;
	
	private List<Option> q_option;
	
	private boolean is_required;
	
	

}
