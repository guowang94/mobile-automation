package com.scb.fmsw.mobile.screen;

import com.scb.fmsw.mobile.base.BaseScreen;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SubmitScreen extends BaseScreen {

    private PageObjects submitScreen;
    private String alertTitle = "//XCUIElementTypeAlert//XCUIElementTypeStaticText[1]";

    public SubmitScreen(IOSDriver<IOSElement> testDriver) {
        iosDriver = testDriver;
        submitScreen = new PageObjects();
        PageFactory.initElements(iosDriver, submitScreen);
    }

    /**
     * This method will submit overdue workflow
     *
     * @param lateCode
     * @param workflowType
     * @param workflowCount
     * @param ceToDealer
     * @return InboxScreen
     */
    public InboxScreen submitWorkflow(String lateCode, String workflowType, int workflowCount, boolean ceToDealer) {
        hasLoadingCompleted();
        if (hasFormContainerLoaded()) {
            if (WORKFLOW_VE.equals(workflowType)) {
                enterComments(FORM_LABEL_VDO_COMMENTS, MSG_ENTER_COMMENT);
            } else if (WORKFLOW_CE.equals(workflowType) && ceToDealer) {
                enterComments(FORM_LABEL_RESPONSE_COMMENTS_COMPULSORY, MSG_ENTER_COMMENT);
            } else if (WORKFLOW_CE.equals(workflowType) && !ceToDealer) {
                enterComments(FORM_LABEL_COMMENTS_COMPULSORY, MSG_ENTER_COMMENT);
            } else {
                enterComments(FORM_LABEL_COMMENTS, MSG_ENTER_COMMENT);
            }
            selectLateCode(lateCode);
            tapOnFormDoneButton();
            verifySubmitStatus(workflowType, workflowCount);
        } else {
            throw new RuntimeException(ERROR_MSG_CONTAINER_NOT_LOADED);
        }
        return new InboxScreen(iosDriver);
    }

    /**
     * This method will Edit and Respond the workflow
     *
     * @param lateCode
     * @param disciplinaryAction
     * @param workflowType
     * @param workflowCount
     * @return InboxScreen
     */
    public InboxScreen editAndRespondWorkflow(String lateCode, String disciplinaryAction,
                                              String workflowType, int workflowCount) {
        hasLoadingCompleted();
        if (hasFormContainerLoaded()) {
            if (WORKFLOW_VE.equals(workflowType)) {
                //todo need to add in the label
            /*selectPickerValue("", disciplinaryAction);
            enterComments("", MSG_ENTER_FO_JUSTIFICATION_COMMENT_EDIT);
            enterComments("", MSG_ENTER_PREVENT_RECURRENCE_COMMENT_EDIT);
            enterComments("", MSG_ENTER_REMEDIATION_ACTION_COMMENT_EDIT);
            enterComments("", MSG_ENTER_VDO_COMMENT_EDIT);
            selectLateResponseCode(lateCode, WORKFLOW_VE);
            */
            } else if (WORKFLOW_CE.equals(workflowType)) {

            }
            tapOnFormDoneButton();
            verifySubmitStatus(workflowType, workflowCount);
        }
        return new InboxScreen(iosDriver);
    }

    /**
     * This method will verify the Submit status
     *
     * @param workflowType
     * @param workflowCount
     */
    private void verifySubmitStatus(String workflowType, int workflowCount) {
        hasLoadingCompleted();
        int duration = 20;

        if (workflowCount == 1) {
            duration = 30;
        } else if (workflowCount > 1) {
            duration = duration * workflowCount;
        }
        try {
            waitForElementByXpath(alertTitle, duration, true);
            if (ALERT_TITLE_SUCCESS.equalsIgnoreCase(submitScreen.alertTitle.getText())) {
                screenshot(SCREENSHOT_MSG_SUCCESSFULLY_SUBMIT_WORKFLOW.replace("$1", workflowType));
                System.out.println(SUCCESS_MSG_SUCCESSFULLY_SUBMIT_WORKFLOW);
                submitScreen.alertOkButton.click();
            } else if (ALERT_TITLE_FAILED.equalsIgnoreCase(submitScreen.alertTitle.getText())) {
                screenshot(SCREENSHOT_MSG_FAILED_TO_SUBMIT_WORKFLOW.replace("$1 ", workflowType));
                throw new RuntimeException(FAILED_MSG_FAILED_TO_SUBMIT_WORKFLOW.replace("$1 ", workflowType));
            }
        } catch (Exception e) {
            switch (submitScreen.alertMessage.getText()) {
                case ALERT_MSG_SELECT_LATE_CODE_COMPULSORY:
                case ALERT_MSG_SELECT_LATE_CODE:
                    submitScreen.alertOkButton.click();
                    selectLateCode(LATE_CODE_DEADLINE_MISSED);
                    break;
                case ALERT_MSG_SELECT_LATE_RESPONSE_CODE:
                    submitScreen.alertOkButton.click();
                    selectLateResponseCode(CE_LATE_CODE_OTHERS, workflowType);
                    break;
                case ALERT_MSG_NETWORK_CONNECTION_WAS_LOST:
                    submitScreen.alertOkButton.click();
                    tapOnFormDoneButton();
                    break;
                case ALERT_MSG_UNEXPECTED_ERROR_OCCURRED:
                    screenshot(ALERT_MSG_UNEXPECTED_ERROR_OCCURRED);
                    throw new RuntimeException(ALERT_MSG_UNEXPECTED_ERROR_OCCURRED);
                case ALERT_MSG_WORKFLOW_STATUS_HAS_BEEN_UPDATED:
                    throw new RuntimeException(ALERT_MSG_WORKFLOW_STATUS_HAS_BEEN_UPDATED);
                default:
                    screenshot(ALERT_MSG_NONE_OF_THE_MSG_ARE_MATCHED);
                    throw new RuntimeException(ALERT_MSG_NONE_OF_THE_MSG_ARE_MATCHED);
            }
            tapOnFormDoneButton();
            verifySubmitStatus(workflowType, workflowCount);
        }
    }

    private class PageObjects {
        @FindBy(xpath = "//XCUIElementTypeAlert//XCUIElementTypeButton[1]")
        WebElement alertOkButton;

        @FindBy(xpath = "//XCUIElementTypeAlert//XCUIElementTypeStaticText[2]")
        WebElement alertMessage;

        @FindBy(xpath = "//XCUIElementTypeAlert//XCUIElementTypeStaticText[1]")
        WebElement alertTitle;
    }
}
