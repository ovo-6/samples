package com.ovo6.expenses.integration;

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

/**
 * Testing resource access restrictions for roles admin, manager and user.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class RestRoleAccessTest {

    private static final String NEW_USER_A = "{\"name\": \"a\", \"email\": \"a@a.com\", \"password\": \"a\", \"roles\": [\"ROLE_USER\"]}";
    private static final String NEW_USER_B = "{\"name\": \"b\", \"email\": \"a@a.com\", \"password\": \"a\", \"roles\": [\"ROLE_USER\"]}";
    private static final String NEW_USER_E = "{\"name\": \"e\", \"email\": \"a@a.com\", \"password\": \"a\", \"roles\": [\"ROLE_USER\"]}";
    private static final String  NEW_EXPENSE = "{\"description\": \"one\", \"amount\": 1}";

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testAdminAccess() {
        String token = login("admin", "admin");
        assertGet(HttpStatus.OK, "/api/users", token);
        assertGet(HttpStatus.OK, "/api/users/admin", token);
        assertGet(HttpStatus.OK, "/api/expenses", token);
        assertGet(HttpStatus.OK, "/api/expenses/stats", token);

        assertGet(HttpStatus.OK, "/api/expenses/1", token); //expense of user1

        assertDelete(HttpStatus.NO_CONTENT, "/api/users/user4", token);
        assertDelete(HttpStatus.NO_CONTENT, "/api/expenses/4", token); //expense of user1

        assertPost(HttpStatus.CREATED, "/api/users", token, NEW_USER_A);
        assertPost(HttpStatus.CREATED, "/api/expenses", token, NEW_EXPENSE);

        assertPut(HttpStatus.OK, "/api/users/a", token, NEW_USER_E);
        assertPut(HttpStatus.OK, "/api/expenses/1", token, NEW_EXPENSE);
    }

    @Test
    public void testManagerAccess() {
        String token = login("manager", "manager");
        assertGet(HttpStatus.OK, "/api/users", token);
        assertGet(HttpStatus.OK, "/api/users/user3", token);
        assertGet(HttpStatus.FORBIDDEN, "/api/expenses", token);
        assertGet(HttpStatus.FORBIDDEN, "/api/expenses/stats", token);

        assertGet(HttpStatus.FORBIDDEN, "/api/expenses/1", token); //expense of user1

        assertDelete(HttpStatus.NO_CONTENT, "/api/users/user5", token);
        assertDelete(HttpStatus.FORBIDDEN, "/api/expenses/5", token); //expense of user1

        assertPost(HttpStatus.CREATED, "/api/users", token, NEW_USER_B);
        assertPost(HttpStatus.FORBIDDEN, "/api/expenses", token, NEW_EXPENSE);

        assertPut(HttpStatus.OK, "/api/users/a", token, NEW_USER_E);
        assertPut(HttpStatus.FORBIDDEN, "/api/expenses/1", token, NEW_EXPENSE);
    }

    @Test
    public void testUserAccess() {
        String token = login("user1", "user1");
        assertGet(HttpStatus.FORBIDDEN, "/api/users", token);
        assertGet(HttpStatus.FORBIDDEN, "/api/users/admin", token);
        assertGet(HttpStatus.OK, "/api/expenses", token);
        assertGet(HttpStatus.OK, "/api/expenses/stats", token);

        assertGet(HttpStatus.OK, "/api/expenses/1", token); //expense of user1
        assertGet(HttpStatus.FORBIDDEN, "/api/expenses/112", token); //expense of user2

        assertDelete(HttpStatus.FORBIDDEN, "/api/users/user6", token);
        assertDelete(HttpStatus.NO_CONTENT, "/api/expenses/6", token); //expense of user1
        assertDelete(HttpStatus.FORBIDDEN, "/api/expenses/112", token); //expense of user2

        assertPost(HttpStatus.CREATED, "/api/expenses", token, NEW_EXPENSE);
        assertPost(HttpStatus.FORBIDDEN, "/api/users", token, NEW_USER_B);

        assertPut(HttpStatus.FORBIDDEN, "/api/users/a", token, NEW_USER_E);
        assertPut(HttpStatus.OK, "/api/expenses/1", token, NEW_EXPENSE);
        assertPut(HttpStatus.FORBIDDEN, "/api/expenses/112", token, NEW_EXPENSE);
    }

    @Test
    public void testAnonymous() {
        assertPost(HttpStatus.OK, "/signup", null, "{\"name\":\"c\", \"password\": \"c\", \"email\":\"c@me.com\"}");

        assertGet(HttpStatus.FORBIDDEN, "/api/users", null);
        assertGet(HttpStatus.FORBIDDEN, "/api/users/admin", null);
        assertGet(HttpStatus.FORBIDDEN, "/api/expenses", null);
        assertGet(HttpStatus.FORBIDDEN, "/api/expenses/stats", null);
        assertGet(HttpStatus.FORBIDDEN, "/api/expenses/1", null); //expense of user1

        assertDelete(HttpStatus.FORBIDDEN, "/api/users/user7", null);
        assertDelete(HttpStatus.FORBIDDEN, "/api/expenses/7", null); //expense of user1

        assertPost(HttpStatus.FORBIDDEN, "/api/expenses", null, NEW_EXPENSE);
        assertPost(HttpStatus.FORBIDDEN, "/api/users", null, NEW_USER_B);

        assertPut(HttpStatus.FORBIDDEN, "/api/users/a", null, NEW_USER_E);
        assertPut(HttpStatus.FORBIDDEN, "/api/expenses/1", null, NEW_EXPENSE);
    }


    private String login(String username, String password) {
        AccountCredentials admin = new AccountCredentials();
        admin.setUsername(username);
        admin.setPassword(password);

        ResponseEntity<String> response = this.restTemplate.postForEntity("/login", admin, String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeaders().get("Authorization")).isNotEmpty();

        return response.getHeaders().get("Authorization").get(0).substring(7);
    }

    private void assertGet(HttpStatus status, String url, String token) {
        assertHttp(status, HttpMethod.GET, url, token, "");
    }

    private void assertDelete(HttpStatus status, String url, String token) {
        assertHttp(status, HttpMethod.DELETE,  url, token, "");
    }

    private void assertPost(HttpStatus status, String url, String token, String body) {
        assertHttp(status, HttpMethod.POST, url, token, body);
    }

    private void assertPut(HttpStatus status, String url, String token, String body) {
        assertHttp(status, HttpMethod.PUT, url, token, body);
    }

    private void assertHttp(HttpStatus status, HttpMethod method, String url, String token, String body) {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        if (token != null) {
            headers.set("Authorization", token);
        }

        HttpEntity<String> entity = new HttpEntity<String>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, method, entity, String.class);

        assertThat(response.getStatusCode()).isEqualTo(status);
    }

}


