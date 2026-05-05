package com.heroku.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class SecureAreaPage {

    public static final String URL = "https://the-internet.herokuapp.com/secure";

    private static final By HEADING = By.cssSelector("h2");
    private static final By FLASH_MESSAGE = By.id("flash");
    private static final By LOGOUT_LINK = By.cssSelector("a[href='/logout']");

    private final WebDriver driver;
    private final WebDriverWait wait;

    public SecureAreaPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String getHeadingText() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(HEADING));
        return driver.findElement(HEADING).getText();
    }

    public String getFlashMessage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(FLASH_MESSAGE));
        return driver.findElement(FLASH_MESSAGE).getText();
    }

    public SecureAreaPage clickLogout() {
        wait.until(ExpectedConditions.elementToBeClickable(LOGOUT_LINK));
        driver.findElement(LOGOUT_LINK).click();
        return this;
    }

 
    public LoginPage logoutReturningToLoginPage() {
        clickLogout();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.waitUntilLoginPageLoaded();
        return loginPage;
    }

    public boolean isHeadingVisible() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(HEADING));
        return driver.findElement(HEADING).isDisplayed();
    }
}
