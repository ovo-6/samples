package com.ovo6.poll.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.ovo6.poll.model.Question;
import com.ovo6.poll.repo.QuestionRepository;
import com.ovo6.poll.view.QuestionListView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class QuestionController {

    @Autowired
    QuestionRepository repo;

    @RequestMapping("/questions")
    @JsonView(QuestionListView.class)
    List<Question> getAll() {
        return repo.findAll();
    }

    @RequestMapping("/questions/{id}")
    Question getQuestion(@PathVariable Integer id) {
        return repo.findOne(id);
    }


}
