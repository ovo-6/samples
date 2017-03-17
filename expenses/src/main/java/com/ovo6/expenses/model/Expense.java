package com.ovo6.expenses.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.data.annotation.CreatedBy;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Expense entity.
 */
@Entity
@Table(name="EXPENSES")
public class Expense {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Date datetime;
    private String description;
    private BigDecimal amount;
    private String comment;

    @ManyToOne
    @JsonIgnore // do not expose whole object, only user name via #getOwner()
    private User user;

    public Long getId() {
        return id;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @JsonProperty("owner")
    public String getOwner() {
        if (user == null) return null;
        return user.getName();
    }
}
