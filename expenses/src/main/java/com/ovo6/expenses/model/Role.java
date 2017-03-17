package com.ovo6.expenses.model;

/**
 * Enum with defined roles used through application.
 */
public enum Role {

    ROLE_ADMIN,         // can do everything
    ROLE_USER_MANAGER,  // can only edit users and NOT expenses
    ROLE_USER           // can only edit expenses and NOT users

}
