package com.scb.fmsw.mobile.screen;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.scb.fmsw.mobile.base.BaseScreen;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

public class ExplicitDelegationScreen extends BaseScreen {

    private PageObjects delegateScreen;
    private String alertTitle = "//XCUIElementTypeAlert//XCUIElementTypeStaticText[1]";

    public ExplicitDelegationScreen(IOSDriver<IOSElement> testDriver) {
        iosDriver = testDriver;
        delegateScreen = new PageObjects();
        PageFactory.initElements(iosDriver, delegateScreen);
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
        if (hasFormContainerLoaded()) {
            searchForUser(FORM_LABEL_PSID_OR_NAME, userID);
            enterComments(FORM_LABEL_COMMENTS, MSG_ENTER_COMMENT);
            tapOnFormDoneButton();
            verifyDelegateStatus(workflowType);
        } else {
            throw new RuntimeException(ERROR_MSG_CONTAINER_NOT_LOADED);
        }
        return new InboxScreen(iosDriver);
    }

    /**
     * This method will verify the Workflow level Delegation status
     *
     * @param workflowType
     */
    private void verifyDelegateStatus(String workflowType) {
        hasLoadingCompleted();
        try {
            waitForElementByXpath(alertTitle, true);
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
                    default:
                        screenshot(ALERT_MSG_NONE_OF_THE_MSG_ARE_MATCHED);
                        throw new RuntimeException(ALERT_MSG_NONE_OF_THE_MSG_ARE_MATCHED);
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
    }
}
