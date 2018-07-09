package com.scb.fmsw.mobile.screen;

import com.scb.fmsw.mobile.base.BaseScreen;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class DelegationUsersScreen extends BaseScreen {

    private PageObjects delegationUsersScreen;

    public DelegationUsersScreen(IOSDriver<IOSElement> testDriver) {
        iosDriver = testDriver;
        delegationUsersScreen = new PageObjects();
        PageFactory.initElements(iosDriver, delegationUsersScreen);
    }

    /**
     * This method will check if Table Container is loaded
     *
     * @return boolean
     */
    private boolean hasTableContainerLoaded() {
        return delegationUsersScreen.tableContainer.isDisplayed();
    }

    /**
     * This method will fill all the value in the form
     *
     * @param workflowTypeList
     * @param fromDate
     * @param toDate
     * @param delegateeID
     * @return DelegationUsersCreationScreen
     */
    public DelegationUsersCreationScreen fillInUsersForm(List<String> workflowTypeList, String fromDate, String toDate, String delegateeID) {
        hasLoadingCompleted();
        if (hasTableContainerLoaded()) {
            selectWorkflowType(workflowTypeList);
            hasLoadingCompleted();
            selectFromDate(fromDate.split(",")[0], fromDate.split(",")[1], fromDate.split(",")[2]);
            selectToDate(toDate.split(",")[0], toDate.split(",")[1], toDate.split(",")[2]);
            searchDelegateTo(delegateeID);

            delegationUsersScreen.plusButton.click();
            System.out.println("Navigate to Users Delegation Creation Screen");
        } else {
            throw new RuntimeException(ERROR_MSG_TABLE_CONTAINER_NOT_LOADED);
        }
        return new DelegationUsersCreationScreen(iosDriver);
    }

    /**
     * This method will select the date value
     *
     * @param day
     * @param month
     * @param year
     */
    private void datePicker(String day, String month, String year) {
        delegationUsersScreen.yearPicker.sendKeys(year);
        delegationUsersScreen.monthPicker.sendKeys(month);
        delegationUsersScreen.dayPicker.sendKeys(day);
        delegationUsersScreen.doneButton.click();
    }

    private void selectFromDate(String day, String month, String year) {
        delegationUsersScreen.fromDateField.click();
        datePicker(day, month, year);
    }

    private void selectToDate(String day, String month, String year) {
        try {
            delegationUsersScreen.errorMsg.click();
        } catch (Exception e) {
            //Do nothing
        }
        delegationUsersScreen.toDateField.click();
        datePicker(day, month, year);
    }

    /**
     * This method will search for delegatee based on the id
     *
     * @param delegateeID
     */
    private void searchDelegateTo(String delegateeID) {
        delegationUsersScreen.delegateToTextField.sendKeys(delegateeID.substring(0, 4));
        hasLoadingCompleted();
        try {
            waitForElementById(delegateeID).click();
        } catch (Exception e) {
            screenshot(SCREENSHOT_MSG_NO_RESULT_FOUND);
            throw new RuntimeException(ERROR_MSG_NO_RESULT_FOUND.replace("$1", delegateeID));
        }
    }

    /**
     * This method will select Workflow Type
     *
     * @param workflowTypeList
     */
    private void selectWorkflowType(List<String> workflowTypeList) {
        delegationUsersScreen.workflowTypeField.click();
        System.out.println("Navigate to Delegation Workflow Type Screen");
        new DelegationWorkflowTypeScreen(iosDriver).tapOnWorkflowType(workflowTypeList);
    }


    class PageObjects {
        @FindBy(xpath = "//XCUIElementTypeTable[@visible='true']")
        WebElement tableContainer;

        @FindBy(xpath = "//XCUIElementTypeTextField[@name='Choose Workflow Type']")
        WebElement workflowTypeField;

        @FindBy(id = "greenplus")
        WebElement plusButton;

        @FindBy(xpath = "//XCUIElementTypeTextField[@name='Type to search PSID/Name']")
        WebElement delegateToTextField;

        @FindBy(xpath = "//XCUIElementTypePickerWheel[1]")
        WebElement dayPicker;

        @FindBy(xpath = "//XCUIElementTypePickerWheel[2]")
        WebElement monthPicker;

        @FindBy(xpath = "//XCUIElementTypePickerWheel[3]")
        WebElement yearPicker;

        @FindBy(id = "Done")
        WebElement doneButton;

        @FindBy(id = "ToDate")
        WebElement toDateField;

        @FindBy(id = "FromDate")
        WebElement fromDateField;

        @FindBy(id = "Please choose the valid date")
        WebElement errorMsg;
    }
}
