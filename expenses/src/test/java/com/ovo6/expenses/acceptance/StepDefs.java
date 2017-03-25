package com.ovo6.expenses.acceptance;

import com.ovo6.expenses.controller.SignupUser;
import com.ovo6.expenses.security.AccountCredentials;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Implementation of cucumber steps definitions.
 */

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ContextConfiguration
public class StepDefs {

    @Autowired
    private TestRestTemplate restTemplate;

    private ResponseEntity<String> response; //output

    @When("^(.*) logs in with password (.*)$")
    public void login(String username, String password) throws Throwable {
        AccountCredentials admin = new AccountCredentials();
        admin.setUsername(username);
        admin.setPassword(password);
        response = this.restTemplate.postForEntity("/login", admin, String.class);
    }

    @Then("^the server should handle it and return a (.*) status$")
    public void the_server_should_handle_it_and_return_a_status(String status) throws Throwable {
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.valueOf(status));
    }

    @Then("^auth token should be returned$")
    public void auth_token_should_be_returned() throws Throwable {
        assertThat(response.getHeaders().get("Authorization")).isNotEmpty();
    }

    @Then("^(.*) role should be returned$")
    public void role_should_be_returned(String role) throws Throwable {
        assertThat(response.getBody()).isEqualTo("{\"role\": \"" + role + "\"}");
    }

    @Given("^(.*) signed up with password (.*)$")
    public void signed_up_with_password(String username, String password) throws Throwable {
        SignupUser user = new SignupUser();
        user.setName(username);
        user.setPassword(password);
        user.setEmail("a@a.com");

        postUser("/signup", user);
    }

    private void postUser(String url, SignupUser user) {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<SignupUser> entity = new HttpEntity<SignupUser>(user, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }


}