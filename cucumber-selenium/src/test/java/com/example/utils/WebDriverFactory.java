package com.example.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions; 
import io.github.bonigarcia.wdm.WebDriverManager;

public class WebDriverFactory {
    private static WebDriver driver;

    
    public static WebDriver getDriver() {
        if (driver == null) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            
            // Add headless mode options
            // options.addArguments("--headless=new");
            // options.addArguments("--no-sandbox");
            // options.addArguments("--disable-dev-shm-usage");
            // options.addArguments("--remote-allow-origins=*");
            
            driver = new ChromeDriver(options);
        }
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}