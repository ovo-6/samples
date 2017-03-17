package com.ovo6.expenses.integration;

import com.ovo6.expenses.controller.SignupUser;
import com.ovo6.expenses.security.AccountCredentials;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class RestLoginTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testLoginSuccess() {
        testLogin("admin", "admin", HttpStatus.OK);
        testLogin("manager", "manager", HttpStatus.OK);
        testLogin("user1", "user1", HttpStatus.OK);
        testLogin("user2", "user2", HttpStatus.OK);
        testLogin("user3", "user3", HttpStatus.OK);
    }

    @Test
    public void testNewUser() {
        testLogin("a", "a", HttpStatus.UNAUTHORIZED);

        SignupUser user = new SignupUser();
        user.setName("a");
        user.setPassword("a");
        user.setEmail("a@a.com");

        assertPost(HttpStatus.OK, "/signup", null, user);

        testLogin("a", "a", HttpStatus.OK);
    }

    @Test
    public void testWrongPassword() {
        testLogin("admin", "wrong", HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void testWrongUser() {
        testLogin("wrong", "wrong", HttpStatus.UNAUTHORIZED);
    }

    private void testLogin(String username, String password, HttpStatus expectedStatus) {
        AccountCredentials admin = new AccountCredentials();
        admin.setUsername(username);
        admin.setPassword(password);

        ResponseEntity<String> response = this.restTemplate.postForEntity("/login", admin, String.class);
        assertThat(response.getStatusCode()).isEqualTo(expectedStatus);

        if (HttpStatus.OK == expectedStatus) {
            assertThat(response.getHeaders().get("Authorization")).isNotEmpty();
        }
    }

    private void assertPost(HttpStatus status, String url, String token, SignupUser user) {
        assertHttp(status, HttpMethod.POST, url, token, user);
    }

    private void assertHttp(HttpStatus status, HttpMethod method, String url, String token, SignupUser user) {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        if (token != null) {
            headers.set("Authorization", token);
        }

        HttpEntity<SignupUser> entity = new HttpEntity<SignupUser>(user, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, method, entity, String.class);

        assertThat(response.getStatusCode()).isEqualTo(status);
    }

}


