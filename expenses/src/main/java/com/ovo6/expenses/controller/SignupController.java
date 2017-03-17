package com.ovo6.expenses.controller;

import com.ovo6.expenses.exception.InvalidDataException;
import com.ovo6.expenses.exception.UserConflictException;
import com.ovo6.expenses.model.Role;
import com.ovo6.expenses.model.User;
import com.ovo6.expenses.repo.UserRepository;
import com.ovo6.expenses.security.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Public API for signing up.
 */
@RestController
public class SignupController {

    private final Pattern EMAIL_PATTERN = Pattern.compile("(^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$)");

    private final Pattern NAME_PATTERN = Pattern.compile("(^[a-zA-Z0-9]+$)");

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    SignupUser signUp(@RequestBody SignupUser user) {


        Authentication orig = SecurityContextHolder.getContext().getAuthentication();

        try {
            // authenticate as admin to be able to call protected userRepository
            SecurityContextHolder.getContext().setAuthentication(
                    new AuthenticatedUser("admin", new SimpleGrantedAuthority(Role.ROLE_ADMIN.name())));


            if (StringUtils.isEmpty(user.getName())) {
                throw new InvalidDataException("user name cannot be empty");
            }

            Matcher matcher = NAME_PATTERN.matcher(user.getName());
            if (!matcher.matches()) {
                throw new InvalidDataException("user name invalid, only letters and numbers are allowed");
            }

            if (StringUtils.isEmpty(user.getPassword())) {
                throw new InvalidDataException("user password cannot be empty");
            }

            Matcher matcher2 = EMAIL_PATTERN.matcher(user.getEmail());
            if (!matcher2.matches()) {
                throw new InvalidDataException("user email invalid");
            }


            if (userRepository.exists(user.getName())) {
                throw new UserConflictException(user.getName() + " is already taken");
            }

            Set<Role> roles = new HashSet<Role>();
            roles.add(Role.ROLE_USER);

            User userToSave = new User();
            userToSave.setName(user.getName());

            userToSave.setPassword(user.getPassword());

            userToSave.setEmail(user.getEmail());

            userToSave.setRoles(roles);

            userRepository.save(userToSave);

            return user;
        }
        finally {
            SecurityContextHolder.getContext().setAuthentication(orig);
        }
    }

}
