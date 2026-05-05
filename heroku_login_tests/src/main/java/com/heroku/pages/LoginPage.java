package com.heroku.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class LoginPage {

    public static final String URL = "https://the-internet.herokuapp.com/login";

     static final By USERNAME_FIELD = By.id("username");
     static final By PASSWORD_FIELD = By.id("password");
     static final By LOGIN_BUTTON = By.cssSelector("button[type=\"submit\"]");
     static final By FLASH_MESSAGE = By.id("flash");

    private final WebDriver driver;
    private final WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public LoginPage open() {
        driver.get(URL);
        return this;
    }

    public LoginPage enterUsername(String username) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(USERNAME_FIELD));
        var el = driver.findElement(USERNAME_FIELD);
        el.clear();
        el.sendKeys(username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(PASSWORD_FIELD));
        var el = driver.findElement(PASSWORD_FIELD);
        el.clear();
        el.sendKeys(password);
        return this;
    }

    public LoginPage clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(LOGIN_BUTTON));
        driver.findElement(LOGIN_BUTTON).click();
        return this;
    }

    public String getFlashMessage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(FLASH_MESSAGE));
        return driver.findElement(FLASH_MESSAGE).getText();
    }

    public LoginPage login(String username, String password) {
        return enterUsername(username).enterPassword(password).clickLogin();
    }

   
    public LoginPage waitUntilLoginPageLoaded() {
        wait.until(ExpectedConditions.urlContains("/login"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(USERNAME_FIELD));
        return this;
    }

    public boolean isUsernameFieldVisible() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(USERNAME_FIELD));
        return driver.findElement(USERNAME_FIELD).isDisplayed();
    }
}
