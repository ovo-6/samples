package com.ovo6.poll;

import com.ovo6.poll.model.Answer;
import com.ovo6.poll.model.Person;
import com.ovo6.poll.model.Question;
import com.ovo6.poll.repo.AnswerRepository;
import com.ovo6.poll.repo.PersonRepository;
import com.ovo6.poll.repo.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private PersonRepository personRepository;


    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {

        if (questionRepository.count() == 0) {

            Person p1 = new Person();
            p1.setName("JohnD");
            personRepository.save(p1);

            Person p2 = new Person();
            p2.setName("Elvis");
            personRepository.save(p2);

            Person p3 = new Person();
            p3.setName("Homer.S.");
            personRepository.save(p3);


            Question q1 = new Question();
            q1.setText("What is Java?");
            q1.setCreated(new Date());
            q1.setAuthor(p1);


            Answer q1a1 = new Answer();
            q1a1.setQuestion(q1);
            q1a1.setText("Programming language");
            q1a1.setCreated(new Date());
            q1a1.setAuthor(p2);

            Answer q1a2 = new Answer();
            q1a2.setQuestion(q1);
            q1a2.setText("Coffee");
            q1a2.setCreated(new Date());
            q1a2.setAuthor(p3);


            q1.getAnswers().add(q1a1);
            q1.getAnswers().add(q1a2);

            questionRepository.save(q1);


            Question q2 = new Question();
            q2.setText("What is Spring?");
            q2.setCreated(new Date());
            q2.setAuthor(p2);

            Answer q2a1 = new Answer();
            q2a1.setQuestion(q2);
            q2a1.setText("Part of the year");
            q2a1.setCreated(new Date());
            q2a1.setAuthor(p3);

            q2.getAnswers().add(q2a1);
            questionRepository.save(q2);
        }
    }
}