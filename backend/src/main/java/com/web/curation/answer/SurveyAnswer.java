package com.web.curation.answer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection ="Survey_answer")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SurveyAnswer {
	@Id
	private String aid;
	
	private String title;
	
	private String explain;
	
	private LocalDateTime start_date;
	
	private LocalDateTime end_date;
	
	private List<AnswerQuestion> answer_question;
	
}
