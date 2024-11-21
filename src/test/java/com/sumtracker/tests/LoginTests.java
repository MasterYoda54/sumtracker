package com.sumtracker.tests;

import com.sumtracker.pages.LoginPage;
import com.sumtracker.pages.InventoryPage;
import com.sumtracker.utils.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class LoginTests {
    private WebDriver driver;
    private LoginPage loginPage;

    @BeforeMethod
    public void setUp() {
        driver = WebDriverFactory.getDriver();
        driver.get("https://qa-challenge.codesubmit.io");
        loginPage = new LoginPage(driver);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test(description = "Validate Standard User Login")
    public void testStandardUserLogin() {
        loginPage.login("standard_user", "secret_sauce");
        Assert.assertTrue(loginPage.isLoginSuccessful(), "Login failed for standard user");
    }

    @Test(description = "Validate Locked Out User Login")
    public void testLockedOutUserLogin() {
        loginPage.login("locked_out_user", "secret_sauce");
        Assert.assertTrue(loginPage.getErrorMessage().contains("Sorry, this user has been locked out"));
    }

    @Test(description = "Validate Performance Glitch User Login")
    public void testPerformanceGlitchUserLogin() {
        long startTime = System.currentTimeMillis();
        loginPage.login("performance_glitch_user", "secret_sauce");
        long loginTime = System.currentTimeMillis() - startTime;

        Assert.assertTrue(loginPage.isLoginSuccessful(), "Login failed for performance glitch user");
        Assert.assertTrue(loginTime < 5000, "Login took too long");
    }
}