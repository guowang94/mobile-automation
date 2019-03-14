package com.scb.fmsw.mobile.screen.delegation;

import com.scb.fmsw.mobile.base.BaseScreen;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DelegationDetailScreen extends BaseScreen {

    private PageObjects delegationDetailScreen;

    public DelegationDetailScreen(IOSDriver<IOSElement> testDriver) {
        iosDriver = testDriver;
        delegationDetailScreen = new PageObjects();
        PageFactory.initElements(iosDriver, delegationDetailScreen);
    }


    /**
     * This method will check if Table Container is loaded
     *
     * @return boolean
     */
    private boolean hasTableContainerLoaded() {
        return delegationDetailScreen.tableContainer.isDisplayed();
    }

    /**
     * This method will verify Delegator Comment
     *
     * @param comment
     * @return boolean
     */
    public boolean verifyDelegatorComment(String comment) {
        if (hasTableContainerLoaded()) {
            scrollDownUntilElementIsDisplayed(delegationDetailScreen.delegatorComment);
            return delegationDetailScreen.delegatorComment.getText().equals(comment);
        } else {
            throw new RuntimeException(ERROR_MSG_TABLE_CONTAINER_NOT_LOADED);
        }
    }

    /**
     * This method will verify Workflow Type
     *
     * @param workflowType
     * @return
     */
    public boolean verifyWorkflowType(String workflowType) {
        if (hasTableContainerLoaded()) {
            scrollDownUntilElementIsDisplayed(delegationDetailScreen.workflowType);
            return delegationDetailScreen.workflowType.getText().equals(workflowType);
        } else {
            throw new RuntimeException(ERROR_MSG_TABLE_CONTAINER_NOT_LOADED);
        }
    }

    /**
     * This method will verify Status of Delegation
     *
     * @param status
     * @return
     */
    public boolean verifyStatus(String status) {
        if (hasTableContainerLoaded()) {
            scrollDownUntilElementIsDisplayed(delegationDetailScreen.status);
            return delegationDetailScreen.status.getText().equals(status);
        } else {
            throw new RuntimeException(ERROR_MSG_TABLE_CONTAINER_NOT_LOADED);
        }
    }

    /**
     * This method will tap on back button
     */
    public void back() {
        delegationDetailScreen.backButton.click();
    }

    private class PageObjects {
        @FindBy(xpath = "//XCUIElementTypeTable[@visible='true']")
        WebElement tableContainer;

        @FindBy(id = "textBox12")
        WebElement delegatorComment;

        @FindBy(id = "icon left arrow")
        WebElement backButton;

        @FindBy(id = "textBox5")
        WebElement workflowType;

        @FindBy(id = "textBox11")
        WebElement status;
    }
}
