package com.tcg.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Main {
    static WebDriver driver = null;
    public static void main(String[] args) {
        System.out.println("running....");
        enableEditInOffice();
    }

    public static void enableEditInOffice() {
         driver = new ChromeDriver();
        driver.get("http://localhost:8090/");

        loginIfNeeded();

        gotoAdminScreen("Further Configuration");

        // click the edit button
        String edit_xpath ="//a[@href='/admin/editspacesconfig.action#features']";
        WebElement edit_button = driver.findElement(By.xpath( edit_xpath ));
        edit_button.click();

        // set checkbox state to no threaded comments
        setCheckedState("allowThreadedComments", false);

        // save the form
        driver.findElement(By.xpath("//input[@value='Save']")).click();

        // Make sure to close the driver when you're done.
        driver.quit();
    }

    public static void pauseForDemo(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public static void setCheckedState(String elementID, boolean desiredState) {
        WebElement el = driver.findElement(By.id(elementID));
        if (el.isSelected() != desiredState) {
            driver.findElement(By.xpath("//label[@for='" + elementID + "']")).click();
        }
    }

    public static void gotoAdminScreen(String adminPageName) {
        driver.findElement(By.id("admin-menu-link")).click();
        driver.findElement(By.id("administration-link")).click();
        loginIfNeeded();
        pauseForDemo(1000);
        String xPath = "//a[contains(text(),'"++"')]";
        driver.findElement(By.xpath(xPath)).click();
    }

    public static void loginIfNeeded() {
        if (isPageTitle("Log In - Confluence")) {
            driver.findElement(By.id("os_username")).sendKeys(System.getenv("CONF_USER"));
            driver.findElement(By.id("os_password")).sendKeys(System.getenv("CONF_PASS"));
            pauseForDemo(1000);
            driver.findElement(By.id("loginButton")).click();
        }

        if (isPageTitle("Administrator Access - Confluence")) {
            driver.findElement(By.id("password")).sendKeys(System.getenv("CONF_PASS"));
            pauseForDemo(1000);
            driver.findElement(By.id("authenticateButton")).click();
        }

    }

    public static boolean isPageTitle(String title){
        String currentPageTitle = driver.getTitle().replace(" ", "");
        return title.replace(" ", "").equalsIgnoreCase(currentPageTitle);
    }
}

