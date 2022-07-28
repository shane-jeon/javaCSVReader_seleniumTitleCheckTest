package com.csvreader.withseleniumtest;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class SeleniumTestForStaticContentPages {

    //    main driver method
    public static void main(String[] args) {

//     declare new object variable from CSVReader class to access listOfContents
        CSVReader content = new CSVReader();

//      instantiate new ArrayList with return value of CSVReader class
        List<ArrayList<String>> pages = CSVReader.getContentPages("/Users/jjun/Documents/csv_files/US Content Pages - DocumentPage.csv");

//        Getting ListIterator
        ListIterator<ArrayList<String>> pagesIterator = pages.listIterator();
//        Assign chromedriver value path to variable String chromeDriver
        String chromeDriver = "/Users/jjun/Downloads/chromedriver";

//        ***********************************************
//        CHROME 103 DOES NOT WORK, USING CHROME 104 BETA
//        ***********************************************
//      Selenium Test will return in error with Chrome Version, chromedriver 103.

//      Ensures Chrome Beta is launched (version 104) to match chromedriver 104
        ChromeOptions optionsBeta = new ChromeOptions();
        optionsBeta.setBinary("/Applications/Google Chrome Beta.app/Contents/MacOS/Google Chrome Beta");
        System.setProperty("webdriver.chrome.driver", chromeDriver);
        WebDriver driver = new ChromeDriver(optionsBeta);

//      Will iterate through list as long as the next item in array is not null
        while (pagesIterator.hasNext()) {
            ArrayList<String> page = pagesIterator.next();

//            Prints baseURL and Actual Title obtained from Array List
            System.out.println("line: " + page);

//            Gets baseURL from index 0 in CSV File
//            *improvements for future, find way to avoid hardcoding index value
            String baseURL = page.get(0);
            System.out.println("baseURL: " + baseURL);

            driver.get(baseURL);
            String expectedURL = driver.getCurrentUrl();
            System.out.println(expectedURL);

//            *improvements for future, avoid hardcoding index value
            String actualTitle = page.get(1);
            System.out.println("actualTitle: " + actualTitle);

            String expectedTitle = driver.getTitle();
            System.out.println("expectedTitle: " + expectedTitle);

//            If title returned from static content page contains Actual Title from CSV file, return true (PASS). Else, return false (FAIL)
            if (expectedTitle.contains(actualTitle)) {
                System.out.println("PASS: Expected Title " + expectedTitle + "matches Actual Title " + actualTitle);
                System.out.println("");
            }
            else {
                System.out.println("ERROR: Expected Title " + expectedTitle + "DOES NOT match " + actualTitle);
                System.out.println("");
            }
        }
//        Close driver
        driver.quit();
    }
}
