package com.web.curation.survey;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.web.curation.question.Question;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection ="Survey")
public class Survey {
	@Id
	private String sid;

	private String title;

	private String explain;

	private boolean anony;

	private String writer;

	private LocalDateTime start_date;

	private LocalDateTime end_date;

	private List<Question> question;

	private List<String> share;

	private List<String> target;

	private List<String> incomplete;

	private List<String> complete;

	private State state;

	private boolean use_template;

	private String template;



}