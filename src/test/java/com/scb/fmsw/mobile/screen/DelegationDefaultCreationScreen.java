package com.scb.fmsw.mobile.screen;

import com.scb.fmsw.mobile.base.BaseScreen;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DelegationDefaultCreationScreen extends BaseScreen {

    private PageObjects delegationDefaultCreationScreen;
    private String alertMessage = "//XCUIElementTypeAlert//XCUIElementTypeStaticText[2]";

    public DelegationDefaultCreationScreen(IOSDriver<IOSElement> testDriver) {
        iosDriver = testDriver;
        delegationDefaultCreationScreen = new PageObjects();
        PageFactory.initElements(iosDriver, delegationDefaultCreationScreen);
    }

    /**
     * This method will check if Table Container is loaded
     *
     * @return boolean
     */
    private boolean hasTableContainerLoaded() {
        return delegationDefaultCreationScreen.tableContainer.isDisplayed();
    }

    /**
     * This method will create Default Delegation
     *
     * @param priority
     * @param delegateeID
     * @return DelegationScreen
     */
    public DelegationScreen createDefaultDelegation(String priority, String delegateeID, String comment) {
        hasLoadingCompleted();
        if (hasTableContainerLoaded()) {
            selectPriority(priority);
            searchDelegateTo(delegateeID);
            enterComment(comment);
            delegationDefaultCreationScreen.doneButton.click();
            verifyDefaultDelegationStatus();
        } else {
            throw new RuntimeException(ERROR_MSG_TABLE_CONTAINER_NOT_LOADED);
        }
        return new DelegationScreen(iosDriver);
    }

    /**
     * This method will select priority
     *
     * @param priority
     */
    private void selectPriority(String priority) {
        delegationDefaultCreationScreen.priorityField.click();
        delegationDefaultCreationScreen.picker.sendKeys(priority);
        delegationDefaultCreationScreen.doneButton.click();
    }

    /**
     * This method will search for delegatee based on the id
     *
     * @param delegateeID
     */
    private void searchDelegateTo(String delegateeID) {
        delegationDefaultCreationScreen.delegationToField.sendKeys(delegateeID.substring(0,4));
        hasLoadingCompleted();
        try {
            waitForElementById(delegateeID, true).click();
        } catch (Exception e) {
            screenshot(SCREENSHOT_MSG_NO_RESULT_FOUND);
            throw new RuntimeException(ERROR_MSG_NO_RESULT_FOUND.replace("$1", delegateeID));
        }
    }

    /**
     * This method will enter comment
     */
    private void enterComment(String comment) {
        delegationDefaultCreationScreen.commentField.sendKeys(comment);
    }

    /**
     * This method will verify Default Delegation Status
     */
    private void verifyDefaultDelegationStatus() {
        hasLoadingCompleted();
        waitForElementByXpath(alertMessage, true);

        if (delegationDefaultCreationScreen.alertMessage.getText().contains(ALERT_MSG_SUCCESSFULLY_CREATED_DELEGATION)) {

            screenshot(SCREENSHOT_MSG_SUCCESSFULLY_CREATE_DEFAULT_DELEGATION);
            System.out.println(SUCCESS_MSG_SUCCESSFULLY_CREATE_DEFAULT_DELEGATION);
            delegationDefaultCreationScreen.alertOkButton.click();

        } else if (ALERT_MSG_DELEGATION_CANNOT_SELF_DELEGATE.equalsIgnoreCase(delegationDefaultCreationScreen.alertMessage.getText())) {

            screenshot(SCREENSHOT_MSG_FAILED_TO_CREATE_DEFAULT_DELEGATION);
            throw new RuntimeException(FAILED_MSG_FAILED_TO_CREATE_DEFAULT_DELEGATION);

        } else if (delegationDefaultCreationScreen.alertMessage.getText().contains(ALERT_MSG_ALREADY_HAS_DEFAULT_DELEGATION)) {

            screenshot(SCREENSHOT_MSG_FAILED_TO_CREATE_DEFAULT_DELEGATION);
            throw new RuntimeException(FAILED_MSG_FAILED_TO_CREATE_DEFAULT_DELEGATION);

        }
    }

    class PageObjects {
        @FindBy(xpath = "//XCUIElementTypeTable[@visible='true']")
        WebElement tableContainer;

        @FindBy(id = "Done")
        WebElement doneButton;

        @FindBy(id = "priorityLbl")
        WebElement priorityField;

        @FindBy(id = "delegatee_empl_idLbl")
        WebElement delegationToField;

        @FindBy(id = "commentLbl")
        WebElement commentField;

        @FindBy(className = "XCUIElementTypePickerWheel")
        WebElement picker;

        @FindBy(xpath = "//XCUIElementTypeAlert//XCUIElementTypeStaticText[2]")
        WebElement alertMessage;

        @FindBy(xpath = "//XCUIElementTypeAlert//XCUIElementTypeButton[1]")
        WebElement alertOkButton;
    }
}
