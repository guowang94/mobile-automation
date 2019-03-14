package com.scb.fmsw.mobile.screen;

import com.scb.fmsw.mobile.base.BaseScreen;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginScreen extends BaseScreen {

    private PageObjects loginScreen;

    public LoginScreen(IOSDriver<IOSElement> testDriver) {
        iosDriver = testDriver;
        loginScreen = new PageObjects();
        PageFactory.initElements(iosDriver, loginScreen);
    }

    /**
     * This method will enter the username into the textbox
     *
     * @param username username
     */
    private void enterUsername(String username) {
        clearUsername();
        loginScreen.usernameTextbox.sendKeys(username);
    }

    /**
     * This method will enter the password into the textbox
     *
     * @param password password
     */
    private void enterPassword(String password) {
        loginScreen.passwordTextbox.sendKeys(password);
    }

    /**
     * This method will tap on the sign in button
     */
    private void tapOnSignIn() {
        loginScreen.signInButton.click();
    }

    /**
     * This method will login to the application
     *
     * @return LandingScreen
     */
    public LandingScreen login(String userID) {
        if ("Y".equals(prop.getProperty("isBusinessDashboardUser"))) {
            enterUsername(prop.getProperty("uat.BusinessDashboardUsername01"));
            enterPassword(prop.getProperty("uat.BusinessDashboardPwd01"));
        } else {
            enterUsername(userID);
            enterPassword(userID.concat("&scb123"));
        }
        tapOnSignIn();
        return new LandingScreen(iosDriver);
    }

    /**
     * This method will clear username textfield
     */
    private void clearUsername() {
        loginScreen.usernameTextbox.clear();
    }


    private class PageObjects {
        @FindBy(xpath = "//XCUIElementTypeTextField[1]")
        WebElement usernameTextbox;

        @FindBy(xpath = "//XCUIElementTypeSecureTextField[1]")
        WebElement passwordTextbox;

        @FindBy(id = "Sign In")
        WebElement signInButton;
    }
}