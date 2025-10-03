package com.example.steps;

import com.example.pages.LoginPage;
import com.example.utils.ConfigReader;
import com.example.utils.WebDriverFactory;
import io.cucumber.java.en.Given;
import org.openqa.selenium.WebDriver;

public class AuthSteps {
    private final WebDriver driver = WebDriverFactory.getDriver();
    private final LoginPage loginPage = new LoginPage(driver);

    @Given("I am logged in")
    public void i_am_logged_in() {
        String baseUrl = ConfigReader.getOrDefault("base.url", "http://automationexercise.com");
        String email = ConfigReader.get("login.email");
        String password = ConfigReader.get("login.password");

        driver.get(baseUrl);
        loginPage.clickSignupLogin();
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();
        // optional: verify logged-in state here or in subsequent steps
    }
}