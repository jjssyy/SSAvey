package com.web.curation.survey;

import java.util.List;

import com.web.curation.member.ConvertUid;

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
public class SurveyAnswerDto {
	
	private String q_number;
	
	private List<UserAnswer> answers;

}
