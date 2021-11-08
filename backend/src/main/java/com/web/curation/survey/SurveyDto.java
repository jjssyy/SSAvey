package com.web.curation.survey;

import java.time.LocalDateTime;
import java.util.List;

import com.web.curation.member.ConvertUid;
import com.web.curation.question.Question;

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
public class SurveyDto {

	private String sid;

	private String title;

	private String explain;

	private boolean is_anony;

	private String writer;

	private LocalDateTime start_date;

	private LocalDateTime end_date;

	private List<Question> question;

	private List<String> share;

	private List<String> target;

	private List<ConvertUid> incomplete;

	private List<ConvertUid> complete;

	private State state;

	private boolean use_template;

	private String template;
}
