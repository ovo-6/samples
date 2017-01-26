package com.ovo6.poll.controller;

import com.ovo6.poll.model.Answer;
import com.ovo6.poll.model.Question;
import com.ovo6.poll.repo.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class AnswerController {

    @Autowired
    AnswerRepository repo;

    @RequestMapping("/questions/{questionId}/answers")
    List<Answer> getAnswersForQuestion(@PathVariable Integer questionId) {

        Question question = new Question();
        question.setId(questionId);

        return repo.findByQuestion(question);
    }


}
