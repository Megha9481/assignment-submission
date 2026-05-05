package com.heroku.tests;

import com.heroku.pages.LoginPage;
import com.heroku.pages.SecureAreaPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test suite for Heroku login (POM). Waits use WebDriverWait in page objects — no Thread.sleep.
 */
class LoginTest extends BaseTest {

    @Test
    @DisplayName("Navigate, login, Secure Area heading visible")
    void validLoginShowsSecureAreaHeading() {
        var loginPage = new LoginPage(driver);
        loginPage.open().login("tomsmith", "SuperSecretPassword!");

        var secure = new SecureAreaPage(driver);
        assertTrue(secure.isHeadingVisible(), "Secure Area heading should be visible");
        assertTrue(secure.getHeadingText().contains("Secure Area"));
        assertTrue(secure.getFlashMessage().contains("You logged into a secure area"));
    }

    @Test
    @DisplayName("Logout redirects back to login page")
    void logoutReturnsToLoginPage() {
        var loginPage = new LoginPage(driver);
        loginPage.open().login("tomsmith", "SuperSecretPassword!");

        var secure = new SecureAreaPage(driver);
        assertTrue(secure.isHeadingVisible());

        LoginPage afterLogout = secure.logoutReturningToLoginPage();

        assertTrue(driver.getCurrentUrl().contains("/login"), "URL should be the login page after logout");
        assertTrue(afterLogout.isUsernameFieldVisible(), "Login form should be visible after logout");
        assertTrue(afterLogout.getFlashMessage().contains("You logged out of the secure area"));
    }

    @Test
    @DisplayName("Failed login shows expected error message text")
    void failedLoginShowsErrorMessage() {
        var loginPage = new LoginPage(driver);
        loginPage.open().login("tomsmith", "wrong_password");

        String flash = loginPage.getFlashMessage().replaceAll("\\s+", " ").trim();
        assertTrue(
                flash.contains("Your password is invalid!"),
                "Flash should contain invalid password message, was: " + flash);
    }
}
