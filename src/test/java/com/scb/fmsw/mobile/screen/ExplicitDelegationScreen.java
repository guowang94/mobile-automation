package com.scb.fmsw.mobile.screen;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.scb.fmsw.mobile.base.BaseScreen;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

public class ExplicitDelegationScreen extends BaseScreen {

    private PageObjects delegateScreen;
    private String container = "(//XCUIElementTypeImage[@name='Rectangle 4'])[2]";
    private String alertTitle = "//XCUIElementTypeAlert//XCUIElementTypeStaticText[1]";

    public ExplicitDelegationScreen(IOSDriver<IOSElement> testDriver) {
        iosDriver = testDriver;
        delegateScreen = new PageObjects();
        PageFactory.initElements(iosDriver, delegateScreen);
    }

    /**
     * This method will check if Container has loaded
     *
     * @return boolean
     */
    private boolean hasContainerLoaded() {
        return waitForElementByXpath(container).isEnabled();
    }

    /**
     * This method will delegate the workflow
     *
     * @param userID
     * @param workflowType
     * @return InboxScreen
     */
    public InboxScreen delegateWorkflow(String userID, String workflowType) {
        hasLoadingCompleted();
        if (hasContainerLoaded()) {
            searchForUser(userID);
            enterComment();
            delegateScreen.delegateButton.click();
            verifyDelegateStatus(workflowType);
        } else {
            throw new RuntimeException(ERROR_MSG_CONTAINER_NOT_LOADED);
        }
        return new InboxScreen(iosDriver);
    }

    /**
     * This method will search for user based on the userID
     *
     * @param userID
     */
    private void searchForUser(String userID) {
        delegateScreen.psidSearchTextbox.sendKeys(userID);
        hasLoadingCompleted();
        waitForElementById(userID).click();
    }

    /**
     * This method will key in comment
     */
    private void enterComment() {
        delegateScreen.commentTextbox.sendKeys(MSG_ENTER_COMMENT);
    }

    /**
     * This method will verify the Workflow level Delegation status
     *
     * @param workflowType
     */
    private void verifyDelegateStatus(String workflowType) {
        hasLoadingCompleted();
        try {
            waitForElementByXpath(alertTitle);
            if (ALERT_TITLE_SUCCESS.equalsIgnoreCase(delegateScreen.alertTitle.getText())) {
                screenshot(SCREENSHOT_MSG_SUCCESSFULLY_DELEGATE_WORKFLOW.replace("$1", workflowType));
                System.out.println(SUCCESS_MSG_SUCCESSFULLY_DELEGATE_WORKFLOW);
                delegateScreen.alertOkButton.click();
            } else if (ALERT_TITLE_FAILED.equalsIgnoreCase(delegateScreen.alertTitle.getText())) {
                screenshot(SCREENSHOT_MSG_FAILED_TO_DELEGATE_WORKFLOW.replace("$1", workflowType));
                throw new RuntimeException(FAILED_MSG_FAILED_TO_DELEGATE_WORKFLOW.replace("$1 ", ""));
            }
        } catch (Exception e) {
            switch (delegateScreen.alertMessage.getText()) {
                case ALERT_MSG_WORKFLOW_STATUS_HAS_BEEN_UPDATED:
                    throw new RuntimeException(ALERT_MSG_WORKFLOW_STATUS_HAS_BEEN_UPDATED);
                case ALERT_MSG_STAFF_NOT_AVAILABLE:
                    throw new RuntimeException(ALERT_MSG_STAFF_NOT_AVAILABLE);
                case ALERT_MSG_WORKFLOW_CANNOT_SELF_DELEGATE:
                    throw new RuntimeException(ALERT_MSG_WORKFLOW_CANNOT_SELF_DELEGATE);
            }
        }
    }

    class PageObjects {
        @FindBy(xpath = "//XCUIElementTypeAlert//XCUIElementTypeButton[1]")
        WebElement alertOkButton;

        @FindBy(xpath = "//XCUIElementTypeAlert//XCUIElementTypeStaticText[1]")
        WebElement alertTitle;

        @FindBy(xpath = "//XCUIElementTypeAlert//XCUIElementTypeStaticText[2]")
        WebElement alertMessage;

        @FindBy(id = "SearchText")
        WebElement psidSearchTextbox;

        @FindBy(id = "comment0")
        WebElement commentTextbox;

        @FindBy(xpath = "//XCUIElementTypeNavigationBar/XCUIElementTypeButton[2]")
        WebElement delegateButton;
    }
}
