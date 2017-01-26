package com.ovo6.poll.model;



import com.fasterxml.jackson.annotation.JsonView;
import com.ovo6.poll.view.QuestionListView;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Person {


    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @JsonView(QuestionListView.class)
    private Integer id;

    @JsonView(QuestionListView.class)
    private String name;

    @JsonView(QuestionListView.class)
    private String photo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
