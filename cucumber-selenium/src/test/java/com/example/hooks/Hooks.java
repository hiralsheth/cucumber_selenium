package com.example.hooks;

import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import com.example.utils.WebDriverFactory;
import com.example.utils.ScreenshotUtil;
import org.openqa.selenium.WebDriver;

import java.io.File;

public class Hooks {

    // no video recorder — screenshots and logs only

    @Before
    public void setUp(Scenario scenario) {
        // initialize the singleton driver so step classes can use it
    WebDriverFactory.getDriver();
        // nothing else needed here for screenshots-only workflow
    }

    @After
    public void tearDown(Scenario scenario) {
        WebDriver driver = WebDriverFactory.getDriver();
        try {
            // always take a final screenshot
            File shot = ScreenshotUtil.takeScreenshot(driver, scenario.getName());
            // if scenario failed, keep frames and record location
            // attach screenshot image to the scenario (so the HTML report can display it)
            try {
                byte[] bytes = java.nio.file.Files.readAllBytes(shot.toPath());
                scenario.attach(bytes, "image/png", shot.getName());
            } catch (Exception ignored) {}

            if (scenario.isFailed()) {
                scenario.log("Screenshot: " + shot.getAbsolutePath());
            }
        } catch (Exception ignored) {}

        WebDriverFactory.quitDriver();
    }
}