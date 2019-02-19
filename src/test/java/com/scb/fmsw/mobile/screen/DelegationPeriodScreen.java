package com.scb.fmsw.mobile.screen;

import com.scb.fmsw.mobile.base.BaseScreen;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class DelegationPeriodScreen extends BaseScreen {

    private PageObjects delegationUsersScreen;

    String typeFieldID = "Choose TypeBtn";

    public DelegationPeriodScreen(IOSDriver<IOSElement> testDriver) {
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
     * @return DelegationUsersCreationScreen
     */
    public DelegationUsersCreationScreen fillInUsersForm(List<String> workflowTypeList) {
        hasLoadingCompleted();
        if (hasTableContainerLoaded()) {
            selectWorkflowType(workflowTypeList);
            //Need to wait for the screen to load after navigating back from DelegationWorkflowTypeScreen
            hasLoadingCompleted();
            selectType("User");
            //Need to wait for the screen to refresh after choosing type
            hasLoadingCompleted();
            delegationUsersScreen.plusButton.click();
            System.out.println("Navigate to Users Delegation Creation Screen");
        } else {
            throw new RuntimeException(ERROR_MSG_TABLE_CONTAINER_NOT_LOADED);
        }
        return new DelegationUsersCreationScreen(iosDriver);
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

    /**
     * This method will select Type value
     *
     * @param type
     */
    public void selectType(String type) {
        delegationUsersScreen.typeField.click();
        selectPickerValue(type);
    }

    /**
     * This method will select value from picker wheel
     *
     * @param value
     */
    private void selectPickerValue(String value) {
        if (!value.equalsIgnoreCase(delegationUsersScreen.picker.getText())) {
            delegationUsersScreen.picker.sendKeys(value);
        }
        waitForElementByIdUntilClickable("Done", true);
        delegationUsersScreen.doneButton.click();
    }

    class PageObjects {
        @FindBy(xpath = "//XCUIElementTypeTable[@visible='true']")
        WebElement tableContainer;

        @FindBy(xpath = "//XCUIElementTypeTextField[@name='Choose Workflow Type']")
        WebElement workflowTypeField;

        @FindBy(id = "Choose TypeBtn")
        WebElement typeField;

        @FindBy(id = "greenplus")
        WebElement plusButton;

        @FindBy(id = "Done")
        WebElement doneButton;

        @FindBy(className = "XCUIElementTypePickerWheel")
        WebElement picker;
    }
}
