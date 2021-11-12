package com.web.curation.template;

import com.web.curation.question.Question;
import lombok.*;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.web.curation.survey.Survey;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection ="Template")
public class Template {
	@Id
	private String tid;
	
	private boolean deleted;
	
	private String t_title;
	
	private String t_explain;

	private List<Question> question;
	
	private List<String> survey;

	private String writer;
}
