package com.example.utils;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GenerateCucumberReport {
    public static void main(String[] args) {
        try {
            File reportOutputDirectory = new File("target/cucumber-report");
            List<String> jsonFiles = new ArrayList<>();
            jsonFiles.add("target/cucumber-report.json");

            Configuration configuration = new Configuration(reportOutputDirectory, "cucumber-selenium");
            ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
            reportBuilder.generateReports();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
