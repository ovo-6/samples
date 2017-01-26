package com.ovo6.poll.repo;

import com.ovo6.poll.model.Answer;
import com.ovo6.poll.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {

    List<Answer> findByQuestion(Question question);
}
