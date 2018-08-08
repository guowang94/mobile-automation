package com.scb.fmsw.mobile.screen;

import com.scb.fmsw.mobile.base.BaseScreen;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DelegationUsersCreationScreen extends BaseScreen {

    private PageObjects delegationUsersCreationScreen;
    private String alertMessage = "//XCUIElementTypeAlert//XCUIElementTypeStaticText[2]";

    public DelegationUsersCreationScreen(IOSDriver<IOSElement> testDriver) {
        iosDriver = testDriver;
        delegationUsersCreationScreen = new PageObjects();
        PageFactory.initElements(iosDriver, delegationUsersCreationScreen);
    }

    /**
     * This method will check if Table Container is loaded
     *
     * @return boolean
     */
    private boolean hasTableContainerLoaded() {
        return delegationUsersCreationScreen.tableContainer.isDisplayed();
    }

    public DelegationScreen createUsersDelegation(String comment) {
        hasLoadingCompleted();
        if (hasTableContainerLoaded()) {
            delegationUsersCreationScreen.commentTextField.sendKeys(comment);
            delegationUsersCreationScreen.doneButton.click();
            verifyUsersDelegationStatus();
        } else {
            throw new RuntimeException(ERROR_MSG_TABLE_CONTAINER_NOT_LOADED);
        }
        return new DelegationScreen(iosDriver);
    }

    /**
     * This method will verify Users Delegation Status
     */
    private void verifyUsersDelegationStatus() {
        hasLoadingCompleted();
        waitForElementByXpath(alertMessage, true);

        if (delegationUsersCreationScreen.alertMessage.getText().contains(ALERT_MSG_SUCCESSFULLY_CREATED_DELEGATION)) {

            screenshot(SCREENSHOT_MSG_SUCCESSFULLY_CREATE_USERS_DELEGATION);
            System.out.println(SUCCESS_MSG_SUCCESSFULLY_CREATE_USERS_DELEGATION);
            delegationUsersCreationScreen.alertOkButton.click();

        } else if (ALERT_MSG_DELEGATION_CANNOT_SELF_DELEGATE.equalsIgnoreCase(delegationUsersCreationScreen.alertMessage.getText())) {

            screenshot(SCREENSHOT_MSG_FAILED_TO_CREATE_USERS_DELEGATION);
            throw new RuntimeException(FAILED_MSG_FAILED_TO_CREATE_USERS_DELEGATION);

        } else if (delegationUsersCreationScreen.alertMessage.getText().contains(ALERT_MSG_EXISTING_ACTIVE_DELEGATION)) {

            screenshot(SCREENSHOT_MSG_FAILED_TO_CREATE_USERS_DELEGATION);
            throw new RuntimeException(FAILED_MSG_FAILED_TO_CREATE_USERS_DELEGATION);

        }
    }

    class PageObjects {
        @FindBy(xpath = "//XCUIElementTypeTable[@visible='true']")
        WebElement tableContainer;

        @FindBy(id = "commentLbl")
        WebElement commentTextField;

        @FindBy(id = "Done")
        WebElement doneButton;

        @FindBy(xpath = "//XCUIElementTypeAlert//XCUIElementTypeStaticText[2]")
        WebElement alertMessage;

        @FindBy(xpath = "//XCUIElementTypeAlert//XCUIElementTypeButton[1]")
        WebElement alertOkButton;
    }
}
