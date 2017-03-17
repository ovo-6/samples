package com.ovo6.expenses.repo;

import com.ovo6.expenses.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Used to validate unique user name in admin UI only.
 */
@Component
public class UniqueUserValidator implements Validator {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        User user = (User)o;
        if (userRepository.exists(user.getName())) {
            errors.rejectValue("name", "name.alreadyExists");
        }
    }
}
