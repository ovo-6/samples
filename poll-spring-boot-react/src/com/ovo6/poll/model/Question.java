package com.ovo6.poll.model;


import com.fasterxml.jackson.annotation.JsonView;
import com.ovo6.poll.view.QuestionListView;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


@Entity
public class Question {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @JsonView(QuestionListView.class)
    private Integer id;

    @ManyToOne
    @JsonView(QuestionListView.class)
    private Person author;

    @JsonView(QuestionListView.class)
    private Date created;

    @JsonView(QuestionListView.class)
    private String text;

    @JsonView(QuestionListView.class)
    private Integer votes = 0;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.EXTRA) // do not load whole collection when getting size
    private List<Answer> answers = new LinkedList();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Person getAuthor() {
        return author;
    }

    public void setAuthor(Person author) {
        this.author = author;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    @JsonView(QuestionListView.class)
    public int getAnswerCount() {
        return answers.size();
    }
}
