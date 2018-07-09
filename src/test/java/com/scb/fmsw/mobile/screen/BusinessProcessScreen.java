package com.scb.fmsw.mobile.screen;

import com.scb.fmsw.mobile.WorkflowConstants;
import com.scb.fmsw.mobile.base.BaseScreen;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BusinessProcessScreen extends BaseScreen implements WorkflowConstants {

    private PageObjects businessProcessScreen;
    private String userTable = "//XCUIElementTypeTable[@visible='true']";

    public BusinessProcessScreen(IOSDriver testDriver) {
        iosDriver = testDriver;
        businessProcessScreen = new PageObjects();
        PageFactory.initElements(iosDriver, businessProcessScreen);
    }

    /**
     * This method will check if user table is loaded
     *
     * @return boolean
     */
    private boolean hasTableLoaded() {
        return waitForElementByXpath(userTable).isDisplayed();
    }

    /**
     * This method will search for user and select the user with Front Office Role
     *
     * @param userID
     * @return
     */
    public LandingScreen searchForUser(String userID) {
        hasLoadingCompleted();
        if (hasTableLoaded()) {
            businessProcessScreen.searchButton.click();
            businessProcessScreen.searchField.sendKeys(userID);
            iosDriver.hideKeyboard();
            try {
                businessProcessScreen.firstResultCell.click();
                businessProcessScreen.alertOkButton.click();
            } catch (NoSuchElementException e) {
                screenshot(SCREENSHOT_MSG_NO_USER_FOUND);
                throw new RuntimeException(ERROR_MSG_NO_USER_FOUND);
            }
        } else {
            throw new RuntimeException(ERROR_MSG_CONTAINER_NOT_LOADED);
        }
        return new LandingScreen(iosDriver);
    }

    class PageObjects {
        @FindBy(id = "search icon")
        WebElement searchButton;

        @FindBy(className = "XCUIElementTypeSearchField")
        WebElement searchField;

        @FindBy(xpath = "//XCUIElementTypeTable[@visible='true']/XCUIElementTypeCell[1]")
        WebElement firstResultCell;

        @FindBy(id = "OK")
        WebElement alertOkButton;
    }
}
