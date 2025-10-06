package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private final WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isHomeVisible() {
        try {
            wait.until(ExpectedConditions
                    .presenceOfElementLocated(By.xpath("//a[contains(text(),'Home') or contains(text(),'HOME')]")));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void clickSignupLogin() {
        // Intentionally use an incorrect XPath
      // WebElement link = wait.until(ExpectedConditions.elementToBeClickable(
//            By.xpath("//a[contains(text(),'ThisWillNotMatchAnything')]")));
        // Place a breakpoint on the line below to show debugging skills

       //  Correct XPath for actual usage
       WebElement link = wait.until(ExpectedConditions.elementToBeClickable(
             By.xpath("//a[contains(text(),'Signup / Login') or contains(text(),'Sign Up / Login')]")));
        link.click();
    }
    

    public void enterEmail(String email) {
        // try common locators
        try {
            WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
            el.clear();
            el.sendKeys(email);
            return;
        } catch (Exception ignored) {
        }
        try {
            WebElement el = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[data-qa='login-email']")));
            el.clear();
            el.sendKeys(email);
            return;
        } catch (Exception ignored) {
        }
        WebElement el = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='email']")));
        el.clear();
        el.sendKeys(email);
    }

    public void enterPassword(String password) {
        try {
            WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
            el.clear();
            el.sendKeys(password);
            return;
        } catch (Exception ignored) {
        }
        try {
            WebElement el = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[data-qa='login-password']")));
            el.clear();
            el.sendKeys(password);
            return;
        } catch (Exception ignored) {
        }
        WebElement el = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[type='password']")));
        el.clear();
        el.sendKeys(password);
    }

    public void clickLoginButton() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(.,'Login') or contains(.,'Log In') or @type='submit']")));
        btn.click();
    }

    public String getLoggedInText() {
        try {
            WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//*[contains(normalize-space(.),'Logged in as')]")));
            return el.getText();
        } catch (Exception e) {
            return "";
        }
    }
}