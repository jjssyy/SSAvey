package com.web.curation.answer;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Document
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnswerQuestion {

	private int q_number;
	
	private String q_explanation;
	
	private String q_type;
	
	private String answer;
	
	private boolean is_required;
	
	private List<SurveyAnswer>  q_option;
}
