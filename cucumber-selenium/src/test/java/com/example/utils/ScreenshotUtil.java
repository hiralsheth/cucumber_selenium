package com.example.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotUtil {
    public static File takeScreenshot(WebDriver driver, String name) throws IOException {
        File shot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
        String fileName = name.replaceAll("[^a-zA-Z0-9-_]","_") + "-" + LocalDateTime.now().format(f) + ".png";
        File outDir = new File(System.getProperty("user.dir"), "target/screenshots");
        FileUtils.forceMkdir(outDir);
        File out = new File(outDir, fileName);
        FileUtils.copyFile(shot, out);
        return out;
    }
}
