package com.ovo6.expenses.integration;

import com.jayway.jsonpath.JsonPath;
import com.ovo6.expenses.model.Expense;
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

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.junit.Assert.assertNotNull;

/**
 * Test for create, update and delete for Expense.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ExpensesCrudTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testCrud() {
        String token = login("user1", "user1");

        Expense newExpense = new Expense();
        newExpense.setDescription("expense 1");
        newExpense.setAmount(new BigDecimal(15));
        newExpense.setComment("comment");
        newExpense.setDatetime(new Date());

        // CREATE
        
        String response = http(HttpMethod.POST, "/api/expenses", token, newExpense).getBody();
        String uri = JsonPath.read(response, "$._links.self.href");
        assertThat(uri, not(isEmptyOrNullString()));
        assertExpense(newExpense, response);

        response = http(HttpMethod.GET, uri, token, newExpense).getBody();
        assertExpense(newExpense, response);

        
        // UPDATE
        newExpense.setDescription("updated expense 1");
        newExpense.setAmount(new BigDecimal(16));

        response = http(HttpMethod.PUT, uri, token, newExpense).getBody();
        assertExpense(newExpense, response);

        response = http(HttpMethod.GET, uri, token, newExpense).getBody();
        assertExpense(newExpense, response);

        // DELETE
        http(HttpMethod.DELETE, uri, token, null);
        HttpStatus status = http(HttpMethod.GET, uri, token, newExpense).getStatusCode();
        assertThat(status, equalTo(HttpStatus.NOT_FOUND));
    }

    @Test
    public void testInvalidId() {
        String token = login("user1", "user1");
        String uri = "/api/expenses/12345678";

        HttpStatus status = http(HttpMethod.GET, uri, token, null).getStatusCode();
        assertThat(status, equalTo(HttpStatus.NOT_FOUND));

        status = http(HttpMethod.PUT, uri, token, new Expense()).getStatusCode();
        assertThat(status, equalTo(HttpStatus.NOT_FOUND));

        status = http(HttpMethod.DELETE, uri, token, null).getStatusCode();
        assertThat(status, equalTo(HttpStatus.NOT_FOUND));
    }
    
    private static void assertExpense(Expense expense, String response) {
        assertNotNull(response);
        String description = JsonPath.read(response, "$.description");
        Number amount = JsonPath.read(response, "$.amount");
        String comment = JsonPath.read(response, "$.comment");
        String datetime = JsonPath.read(response, "$.datetime");
        String owner = JsonPath.read(response, "$.owner");

        assertThat(description, equalTo(expense.getDescription()));
        assertThat(amount.intValue(), equalTo(expense.getAmount().intValue()));
        assertThat(comment, equalTo(expense.getComment()));
        assertThat(datetime, not(isEmptyOrNullString()));
        assertThat(owner, equalTo("user1"));
    }

    private ResponseEntity<String> http(HttpMethod method, String url, String token, Expense expense) {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        if (token != null) {
            headers.set("Authorization", token);
        }

        HttpEntity<Expense> entity = new HttpEntity<Expense>(expense, headers);
        return restTemplate.exchange(url, method, entity, String.class);
    }

    private String login(String username, String password) {
        AccountCredentials admin = new AccountCredentials();
        admin.setUsername(username);
        admin.setPassword(password);

        ResponseEntity<String> response = this.restTemplate.postForEntity("/login", admin, String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

        return response.getHeaders().get("Authorization").get(0).substring(7);
    }

}
