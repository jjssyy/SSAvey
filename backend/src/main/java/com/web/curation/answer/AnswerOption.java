package com.web.curation.answer;

import java.time.LocalDate;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Document(collection ="AnswerOption")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnswerOption {

	private int o_number;
	
	private String o_explanation;
	
	private boolean is_short;
}
