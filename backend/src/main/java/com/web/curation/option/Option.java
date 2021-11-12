package com.web.curation.option;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.web.curation.question.Question;
import com.web.curation.survey.Survey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection ="Option")
public class Option {

	private int o_number;
	 
	private String o_explanation;
	
	private boolean short_answer;
}
