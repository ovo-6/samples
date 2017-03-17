package com.ovo6.expenses;

import com.ovo6.expenses.model.Expense;
import com.ovo6.expenses.model.Role;
import com.ovo6.expenses.model.User;
import com.ovo6.expenses.repo.ExpenseRepository;
import com.ovo6.expenses.repo.UserRepository;
import com.ovo6.expenses.security.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

/**
 * Loads initial sample data (if tables are empty).
 */
@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {

        // authenticate as admin to be able to call protected userRepository
        SecurityContextHolder.getContext().setAuthentication(
                new AuthenticatedUser("admin", new SimpleGrantedAuthority(Role.ROLE_ADMIN.name())));

        if (userRepository.count() == 0) {

            User admin = createUser("admin", Role.ROLE_ADMIN);
            User manager = createUser("manager", Role.ROLE_USER_MANAGER);

            List<User> users = new LinkedList();

            for (int i=1; i<=5; i++) {
                User user1 = createUser("user" + i, Role.ROLE_USER);
                users.add(user1);
            }


            int c = 1;
            for (User user: users) {
                for (int i=0; i<100; i++) {
                    Expense expense = new Expense();
                    expense.setAmount(new BigDecimal(c*10 + 0.99));
                    expense.setDescription("description" + c);
                    expense.setComment("comment" + c);
                    expense.setDatetime(new Date());
                    expense.setUser(user);
                    expenseRepository.save(expense);
                    c++;
                }
            }


            Expense expense = new Expense();
            expense.setAmount(new BigDecimal(44.4));
            expense.setDescription("manager's expense");
            expense.setComment("only visible to manager and admin");
            expense.setDatetime(new Date());
            expense.setUser(manager);
            expenseRepository.save(expense);

            Expense expense2 = new Expense();
            expense2.setAmount(new BigDecimal(33.3));
            expense2.setDescription("admin's expense");
            expense2.setComment("only visible to admin");
            expense2.setDatetime(new Date());
            expense2.setUser(admin);
            expenseRepository.save(expense2);
        }
    }

    private User createUser(String name, Role... roles) {
        User user = new User();
        user.setName(name);
        user.setEmail(name + "@email.com");
        user.setRoles(new HashSet<Role>(Arrays.asList(roles)));
        user.setPassword(name);

        return userRepository.save(user);
    }
}