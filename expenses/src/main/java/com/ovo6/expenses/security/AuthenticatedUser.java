package com.ovo6.expenses.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.HashSet;


public class AuthenticatedUser implements Authentication {

    private final String name;
    private boolean authenticated = true;
    private final Collection<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();

    public AuthenticatedUser(String name, GrantedAuthority authority){
        this.name = name;
        this.authorities.add(authority);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return this.authenticated;
    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {
        this.authenticated = b;
    }

    @Override
    public String getName() {
        return this.name;
    }
}