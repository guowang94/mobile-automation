package com.scb.fmsw.mobile.screen.delegation;

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

    public DelegationScreen createUsersDelegation(String fromDate, String toDate, String delegateeID, String comment) {
        hasLoadingCompleted();
        if (hasTableContainerLoaded()) {
            selectFromDate(fromDate.split(",")[0], fromDate.split(",")[1], fromDate.split(",")[2]);
            selectToDate(toDate.split(",")[0], toDate.split(",")[1], toDate.split(",")[2]);
            searchDelegateTo(delegateeID);
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

    /**
     * This method will select the date value
     *
     * @param day
     * @param month
     * @param year
     */
    private void datePicker(String day, String month, String year) {
        delegationUsersCreationScreen.yearPicker.sendKeys(year);
        delegationUsersCreationScreen.monthPicker.sendKeys(month);
        delegationUsersCreationScreen.dayPicker.sendKeys(day);
        delegationUsersCreationScreen.doneButton.click();
    }

    private void selectFromDate(String day, String month, String year) {
        delegationUsersCreationScreen.fromDateField.click();
        datePicker(day, month, year);
    }

    private void selectToDate(String day, String month, String year) {
        try {
            delegationUsersCreationScreen.errorMsg.click();
        } catch (Exception e) {
            //Do nothing
        }
        delegationUsersCreationScreen.toDateField.click();
        datePicker(day, month, year);
    }

    /**
     * This method will search for delegatee based on the id
     *
     * @param delegateeID
     */
    private void searchDelegateTo(String delegateeID) {
        delegationUsersCreationScreen.delegateToTextField.sendKeys(delegateeID.substring(0, 3));
        hasLoadingCompleted();
        try {
            waitForElementById(delegateeID, true).click();
        } catch (Exception e) {
            screenshot(SCREENSHOT_MSG_NO_RESULT_FOUND);
            throw new RuntimeException(ERROR_MSG_NO_RESULT_FOUND.replace("$1", delegateeID));
        }
    }

    private class PageObjects {
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

        @FindBy(id = "delegatee_empl_idLbl")
        WebElement delegateToTextField;

        @FindBy(xpath = "//XCUIElementTypePickerWheel[1]")
        WebElement dayPicker;

        @FindBy(xpath = "//XCUIElementTypePickerWheel[2]")
        WebElement monthPicker;

        @FindBy(xpath = "//XCUIElementTypePickerWheel[3]")
        WebElement yearPicker;

        @FindBy(id = "ToDate")
        WebElement toDateField;

        @FindBy(id = "FromDate")
        WebElement fromDateField;

        @FindBy(id = "Please choose the valid date")
        WebElement errorMsg;
    }
}
