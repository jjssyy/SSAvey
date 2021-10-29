package com.web.curation.question;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.curation.answer.AnswerController;
import com.web.curation.answer.AnswerService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/answer")
public class QuestionController {

	private QuestionService questionService;
}
