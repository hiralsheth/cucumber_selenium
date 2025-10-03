package com.example.steps;

import com.example.pages.LoginPage;
import com.example.utils.WebDriverFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import com.example.pages.CartPage;

public class LoginSteps {
    private final WebDriver driver = WebDriverFactory.getDriver();
    private final LoginPage loginPage = new LoginPage(driver);
    private final CartPage cartPage = new CartPage(driver);

    @Given("Launch browser")
    public void launch_browser() {
        // driver initialized in factory
    }

    @When("I navigate to url {string}")
    public void i_navigate_to_url(String url) {
        driver.get(url);
    }

    @Then("Verify that home page is visible successfully")
    public void verify_that_home_page_is_visible_successfully() {
        boolean visible = loginPage.isHomeVisible();
        Assert.assertTrue("Home page not visible", visible);
    }

    @When("I click {string} button")
    public void i_click_button(String buttonText) {
        // route click based on the button text used in features
        if (buttonText == null) return;
        String t = buttonText.trim();
        if (t.equalsIgnoreCase("Signup / Login") || t.toLowerCase().contains("signup") || t.toLowerCase().contains("login")) {
            loginPage.clickSignupLogin();
        } else if (t.equalsIgnoreCase("Cart") || t.toLowerCase().contains("cart")) {
            cartPage.clickCartButton();
        } else {
            // fallback: click any link/button matching the visible text
            try {
                driver.findElement(org.openqa.selenium.By.xpath("//a[normalize-space()='" + t + "']|//button[normalize-space()='" + t + "']")).click();
            } catch (Exception e) {
                // ignore - test should fail later if element truly missing
            }
        }
    }

    @When("I fill email {string} and password {string} and click {string} button")
    public void i_fill_email_and_password_and_click_button(String email, String password, String buttonText) {
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();
    }

    @Then("I should see {string}")
    public void i_should_see(String expected) {
        String actual = loginPage.getLoggedInText();
        Assert.assertTrue("Expected message not found. actual=[" + actual + "]", actual.contains(expected));
    }
}