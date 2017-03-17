package com.ovo6.expenses;

import com.ovo6.expenses.security.SecurityEvaluationContextExtension;
import com.ovo6.expenses.security.JWTAuthenticationFilter;
import com.ovo6.expenses.security.JWTLoginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.query.spi.EvaluationContextExtension;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // disable caching
        http.headers().cacheControl();

        http.csrf().disable() // disable csrf for our requests.
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //for REST API we don't need sessions
                    .and()

                .authorizeRequests()
                    .antMatchers("/", "/js/*.js", "/js/lib/*.js", "/js/lib/*.css", "/favicon.ico").permitAll()
                    .antMatchers(HttpMethod.POST, "/signup").permitAll()
                    .antMatchers(HttpMethod.POST, "/login").permitAll()
                    .anyRequest().authenticated()
                    .and()
                // We filter the api/login requests
                .addFilterBefore(new JWTLoginFilter("/login", authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                // And filter other requests to check the presence of JWT in header
                .addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // authentication source when creating JWT tokens
        auth.jdbcAuthentication().dataSource(dataSource)
                .passwordEncoder(getPasswordEncoder())
                .usersByUsernameQuery("select name,password,true from users where name=?")
                .authoritiesByUsernameQuery("select user_name,roles from user_roles where user_name=?"); // must return some role to work
    }

    @Bean(name = "passwordEncoder")
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    EvaluationContextExtension securityExtension() {
        return new SecurityEvaluationContextExtension();
    }

}