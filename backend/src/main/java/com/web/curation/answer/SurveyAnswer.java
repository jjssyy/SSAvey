package com.web.curation.answer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SurveyAnswer {
	private String aid;
	
	private String title;
	
	private String explain;
	
	private LocalDateTime start_date;
	
	private LocalDateTime end_date;
	
	private List<AnswerQuestion> answer_question;
	
}
