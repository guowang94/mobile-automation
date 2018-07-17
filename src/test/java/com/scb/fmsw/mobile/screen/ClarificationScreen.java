package com.scb.fmsw.mobile.screen;

import com.scb.fmsw.mobile.WorkflowConstants;
import com.scb.fmsw.mobile.base.BaseScreen;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ClarificationScreen extends BaseScreen implements WorkflowConstants {

    private PageObjects clarificationScreen;
    private String container = "(//XCUIElementTypeImage[@name='Rectangle 4'])[2]";
    private String alertTitle = "//XCUIElementTypeAlert//XCUIElementTypeStaticText[1]";

    public ClarificationScreen(IOSDriver<IOSElement> testDriver) {
        iosDriver = testDriver;
        clarificationScreen = new PageObjects();
        PageFactory.initElements(iosDriver, clarificationScreen);
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
     * this method send workflow for clarification
     *
     * @param userID
     * @param isToggle  always false for Middle Office, Line Manager and Performer
     * @param workflowType
     * @param workflowCount
     * @return InboxScreen
     */
    public InboxScreen clarifyWorkflow(String lateCode, String userID, boolean isToggle, String workflowType, int workflowCount) {
        hasLoadingCompleted();
        if (hasFormContainerLoaded()) {

            if (isToggle) {
                toggleSwitch(FORM_LABEL_DISPUTE, isToggle);
                toggleSwitch(FORM_LABEL_ESCALATE, isToggle);
            }

            if (userID != null) {
                searchForUser(FORM_LABEL_PSID_OR_NAME, userID);
            }

            if (WORKFLOW_VE.equals(workflowType)) {
                enterComments(FORM_LABEL_VDO_COMMENTS, MSG_ENTER_COMMENT);
            } else {
                enterComments(FORM_LABEL_COMMENTS, MSG_ENTER_COMMENT);
            }
            selectLateCode(lateCode);

            tapOnFormDoneButton();
            verifyClarificationStatus(workflowType, workflowCount);
        } else {
            throw new RuntimeException(ERROR_MSG_CONTAINER_NOT_LOADED);
        }
        return new InboxScreen(iosDriver);
    }

    /**
     * This method will send CE workflow for clarification
     *
     * @param lateCode
     * @param workflowType
     * @param workflowCount
     * @return InboxScreen
     */
    public InboxScreen clarifyCEWorkflow(String lateCode, String workflowType, int workflowCount) {
        hasLoadingCompleted();
        if (hasFormContainerLoaded()) {
            enterComments(FORM_LABEL_COMMENTS, MSG_ENTER_COMMENT);
            selectLateCode(lateCode);

            tapOnFormDoneButton();
            verifyClarificationStatus(workflowType, workflowCount);
        } else {
            System.out.println(hasContainerLoaded());
            throw new RuntimeException(ERROR_MSG_CONTAINER_NOT_LOADED);
        }
        return new InboxScreen(iosDriver);
    }

    /**
     * this method will verify the Clarification status
     *
     * @param workflowType
     * @param workflowCount
     */
    private void verifyClarificationStatus(String workflowType, int workflowCount) {
        hasLoadingCompleted();
        int duration = 20;

        if (workflowCount == 1) {
            duration = 30;
        } else if (workflowCount > 1) {
            duration = duration * workflowCount;
        }
        try {
            waitForElementByXpath(alertTitle, duration);
            if (ALERT_TITLE_SUCCESS.equalsIgnoreCase(clarificationScreen.alertTitle.getText())) {
                screenshot(SCREENSHOT_MSG_SUCCESSFULLY_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowType));
                System.out.println(SUCCESS_MSG_SUCCESSFULLY_SENT_WORKFLOW_FOR_CLARIFICATION);
                clarificationScreen.alertOkButton.click();
            } else if (ALERT_TITLE_FAILED.equalsIgnoreCase(clarificationScreen.alertTitle.getText())) {
                screenshot(SCREENSHOT_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowType));
                throw new RuntimeException(FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1 ", ""));
            }
        } catch (Exception e) {
            if (ALERT_MSG_SELECT_LATE_CODE.equalsIgnoreCase(clarificationScreen.alertMessage.getText())) {
                clarificationScreen.alertOkButton.click();
                selectLateCode(LATE_CODE_DEADLINE_MISSED);
            } else if (ALERT_MSG_SELECT_LATE_RESPONSE_CODE.equalsIgnoreCase(clarificationScreen.alertMessage.getText())) {
                clarificationScreen.alertOkButton.click();
                selectLateResponseCode(CE_LATE_CODE_OTHERS, workflowType);
            } else if (ALERT_MSG_UNEXPECTED_ERROR_OCCURRED.equalsIgnoreCase(clarificationScreen.alertMessage.getText())) {
                screenshot(ALERT_MSG_UNEXPECTED_ERROR_OCCURRED);
                throw new RuntimeException(ALERT_MSG_UNEXPECTED_ERROR_OCCURRED);
            } else if (ALERT_MSG_WORKFLOW_STATUS_HAS_BEEN_UPDATED.equalsIgnoreCase(clarificationScreen.alertMessage.getText())) {
                throw new RuntimeException(ALERT_MSG_WORKFLOW_STATUS_HAS_BEEN_UPDATED);
            } else {
                screenshot("None of Alert Message are matched");
                throw new RuntimeException("None of Alert Message are matched");
            }
            tapOnFormDoneButton();
            verifyClarificationStatus(workflowType, workflowCount);
        }
    }

    class PageObjects {
        @FindBy(xpath = "//XCUIElementTypeAlert//XCUIElementTypeStaticText[1]")
        WebElement alertTitle;

        @FindBy(xpath = "//XCUIElementTypeAlert//XCUIElementTypeStaticText[2]")
        WebElement alertMessage;

        @FindBy(xpath = "//XCUIElementTypeAlert//XCUIElementTypeButton[1]")
        WebElement alertOkButton;
    }

}
