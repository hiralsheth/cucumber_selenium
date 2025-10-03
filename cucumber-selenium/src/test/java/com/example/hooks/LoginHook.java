package com.example.hooks;

import com.example.pages.LoginPage;
import com.example.utils.WebDriverFactory;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;

public class LoginHook {
    @Before("@login")
    public void ensureLoggedIn() {
        WebDriver driver = WebDriverFactory.getDriver();
        LoginPage login = new LoginPage(driver);
        driver.get("http://automationexercise.com");
        login.clickSignupLogin();
        login.enterEmail("tesths@gmail.com");   // or read from config
        login.enterPassword("tesths");
        login.clickLoginButton();
    }
}