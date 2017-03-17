package com.ovo6.expenses.controller;

import com.ovo6.expenses.exception.ForbiddenException;
import com.ovo6.expenses.exception.InvalidDataException;
import com.ovo6.expenses.exception.NotFoundException;
import com.ovo6.expenses.model.Expense;
import com.ovo6.expenses.model.Role;
import com.ovo6.expenses.model.User;
import com.ovo6.expenses.repo.ExpenseRepository;
import com.ovo6.expenses.repo.ExpenseStats;
import com.ovo6.expenses.repo.UserRepository;
import com.ovo6.expenses.security.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.math.BigDecimal;
import java.util.Date;

import static com.ovo6.expenses.repo.ExpenseRepository.ISO_FORMAT;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Custom controller for expenses.
 * Overrides create method from repository to do pre-processing of user and date.
 */
@BasePathAwareController
@RequestMapping(value = "/expenses")
public class ExpensesController {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    ResponseEntity<Resource<Expense>> createExpense(@RequestBody Expense expense) {

        validateAndSanitize(expense);

        Expense saved = expenseRepository.save(expense);
        Resource<User> resource = new Resource(saved);
        Link selfLink = linkTo(ExpensesController.class).slash(saved.getId()).withSelfRel();
        resource.add(selfLink);
        return new ResponseEntity(resource , HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    ResponseEntity<Resource<Expense>> updateExpense(@PathVariable String id, @RequestBody Expense expense) {

        Expense orig = expenseRepository.findOne(Long.parseLong(id));
        if (orig == null) {
            throw new NotFoundException();
        }
        checkExpenseOwnedByCurrentUser(orig);

        validateAndSanitize(expense);

        orig.setDescription(expense.getDescription());
        orig.setAmount(expense.getAmount());
        orig.setDatetime(expense.getDatetime());
        orig.setComment(expense.getComment());

        Expense saved = expenseRepository.save(orig);
        Resource<Expense> resource = new Resource(saved);
        return new ResponseEntity(resource , HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    ResponseEntity<Resource<Expense>> getExpense(@PathVariable String id) {

        Expense expense = expenseRepository.findOne(Long.parseLong(id));
        if (expense == null) {
            throw new NotFoundException();
        }
        checkExpenseOwnedByCurrentUser(expense);

        Resource<Expense> resource = new Resource(expense);
        return new ResponseEntity(resource , HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    ResponseEntity<Resource<Expense>> deleteExpense(@PathVariable String id) {

        Expense expense = expenseRepository.findOne(Long.parseLong(id));
        if (expense == null) {
            throw new NotFoundException();
        }
        checkExpenseOwnedByCurrentUser(expense);

        expenseRepository.delete(expense);

        Resource<Expense> resource = new Resource(expense);
        return new ResponseEntity(resource , HttpStatus.NO_CONTENT);
    }

    private void checkExpenseOwnedByCurrentUser(Expense expense) throws ForbiddenException{
        if (!expense.getOwner().equals(getCurrentUserName()) && !"ROLE_ADMIN".equals(getCurrentUserRole())) {
            throw new ForbiddenException();
        }
    }

    private void validateAndSanitize(Expense expense) {

        if (StringUtils.isEmpty(expense.getDescription())) {
            throw new InvalidDataException("description cannot be empty");
        }

        if (expense.getAmount() == null) {
            throw new InvalidDataException("amount cannot be empty");
        }

        if (expense.getDatetime() == null) {
            expense.setDatetime(new Date());
        }

        expense.setUser(getCurrentUser());

        expense.setDescription(HtmlUtils.htmlEscape(expense.getDescription()));

        if (expense.getComment() != null) {
            expense.setComment(HtmlUtils.htmlEscape(expense.getComment()));
        }

    }

    @RequestMapping(value = "/stats", method = RequestMethod.GET)
    @ResponseBody
    ResponseEntity<Resource<ExpenseStats>> stats(@RequestParam(required = false) BigDecimal minAmount,
                                                 @RequestParam(required = false) BigDecimal maxAmount,
                                                 @RequestParam(required = false) String description,
                                                 @DateTimeFormat(pattern = ISO_FORMAT) @RequestParam(required = false) Date startDate,
                                                 @DateTimeFormat(pattern = ISO_FORMAT) @RequestParam(required = false) Date endDate) {

        if (minAmount == null) minAmount = new BigDecimal(0);
        if (maxAmount == null) maxAmount = new BigDecimal(1000000);
        if (description == null) description = "%";
        if (startDate == null) startDate = new Date(0);
        if (endDate == null) endDate = new Date(9489088231150L); //far future

        ExpenseStats stats = expenseRepository.stats(minAmount, maxAmount, description, startDate, endDate);

        Resource<ExpenseStats> resource = new Resource(stats);
        return new ResponseEntity(resource , HttpStatus.OK);
    }

    private User getCurrentUser() {

        // store curernt auth
        Authentication origAuth = SecurityContextHolder.getContext().getAuthentication();

        // set auth to admin
        SecurityContextHolder.getContext().setAuthentication(new AuthenticatedUser("admin", new SimpleGrantedAuthority(Role.ROLE_ADMIN.name())));

        User user = userRepository.findOne(origAuth.getName());

        // restore original auth
        SecurityContextHolder.getContext().setAuthentication(origAuth);

        return user;
    }

    private static String getCurrentUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    private static String getCurrentUserRole() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().iterator().next().toString();
    }

}
