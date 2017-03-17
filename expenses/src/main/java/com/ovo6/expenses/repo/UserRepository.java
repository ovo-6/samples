package com.ovo6.expenses.repo;

import com.ovo6.expenses.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Repository for users.
 * Only accessible for admin and user manager.
 */
@PreAuthorize("isFullyAuthenticated() && hasAnyRole('ROLE_ADMIN', 'ROLE_USER_MANAGER')")
public interface UserRepository extends PagingAndSortingRepository<User, String> {

}