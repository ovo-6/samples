package com.ovo6.expenses.acceptance;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Main entry point for cucumber tests.
 * We specify .feature files location, but there are no tests implemented,
 * they are in StepDefs with cucumber annotations (@When, @Then).
 */

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/cucumber")
public class CucumberTest {
}