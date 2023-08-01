package com.tcg.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Main {
    static WebDriver driver = null;
    public static void main(String[] args) {
        System.out.println("running....");
        insertText();
    }

    public static void insertText() {
         driver = new ChromeDriver();

        driver.get("http://localhost:8090/");

        loginIfNeeded();


        // Make sure to close the driver when you're done.
        driver.quit();
    }

    public static void loginIfNeeded() {
        if (isPageTitle("Log In - Confluence")) {
            driver.findElement(By.id("os_username")).sendKeys(System.getenv("CONF_USER"));
            driver.findElement(By.id("os_password")).sendKeys(System.getenv("CONF_PASS"));
            driver.findElement(By.id("loginButton")).click();
        }

    }

    public static boolean isPageTitle(String title){
        String currentPageTitle = driver.getTitle();
        return title.equalsIgnoreCase(currentPageTitle);
    }
}

