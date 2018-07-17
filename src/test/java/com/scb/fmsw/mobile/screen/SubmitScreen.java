package com.scb.fmsw.mobile.screen;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.scb.fmsw.mobile.base.BaseScreen;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

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
     * @return InboxScreen
     */
    public InboxScreen submitWorkflow(String lateCode, String workflowType, int workflowCount) {
        hasLoadingCompleted();
        if (hasFormContainerLoaded()) {
            if (WORKFLOW_VE.equals(workflowType)) {
                enterComments(FORM_LABEL_VDO_COMMENTS, MSG_ENTER_COMMENT);
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
            //fixme there is a production issue for this as the user cannot use For Clarification tab therefore i am unable to get the label

            //todo to be deleted
            /*selectVELateCode(lateCode, disciplinaryAction);
            selectDisciplinaryActionPickerValue(disciplinaryAction);
            enterFirstComment(MSG_ENTER_FO_JUSTIFICATION_COMMENT_EDIT);
            enterSecondComment(MSG_ENTER_PREVENT_RECURRENCE_COMMENT_EDIT);
            enterThirdComment(MSG_ENTER_REMEDIATION_ACTION_COMMENT_EDIT);
            enterFourthComment(MSG_ENTER_VDO_COMMENT_EDIT);

            submitScreen.submitButton.click();*/

            //todo need to add in the label
            /*selectPickerValue("", disciplinaryAction);
            enterComments("", MSG_ENTER_FO_JUSTIFICATION_COMMENT_EDIT);
            enterComments("", MSG_ENTER_PREVENT_RECURRENCE_COMMENT_EDIT);
            enterComments("", MSG_ENTER_REMEDIATION_ACTION_COMMENT_EDIT);
            enterComments("", MSG_ENTER_VDO_COMMENT_EDIT);
            selectLateResponseCode(lateCode, WORKFLOW_VE);

            tapOnFormDoneButton();*/
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
            waitForElementByXpath(alertTitle, duration);
            if (ALERT_TITLE_SUCCESS.equalsIgnoreCase(submitScreen.alertTitle.getText())) {
                screenshot(SCREENSHOT_MSG_SUCCESSFULLY_SUBMIT_WORKFLOW.replace("$1", workflowType));
                System.out.println(SUCCESS_MSG_SUCCESSFULLY_SUBMIT_WORKFLOW);
                submitScreen.alertOkButton.click();
            } else if (ALERT_TITLE_FAILED.equalsIgnoreCase(submitScreen.alertTitle.getText())) {
                screenshot(SCREENSHOT_MSG_FAILED_TO_SUBMIT_WORKFLOW.replace("$1 ", workflowType));
                throw new RuntimeException(FAILED_MSG_FAILED_TO_SUBMIT_WORKFLOW.replace("$1 ", workflowType));
            }
        } catch (Exception e) {
            if (ALERT_MSG_SELECT_LATE_CODE.equalsIgnoreCase(submitScreen.alertMessage.getText())) {
                submitScreen.alertOkButton.click();
                selectLateCode(LATE_CODE_DEADLINE_MISSED);
            } else if (ALERT_MSG_SELECT_LATE_RESPONSE_CODE.equalsIgnoreCase(submitScreen.alertMessage.getText())) {
                submitScreen.alertOkButton.click();
                selectLateResponseCode(CE_LATE_CODE_OTHERS, workflowType);
            } else if (ALERT_MSG_UNEXPECTED_ERROR_OCCURRED.equalsIgnoreCase(submitScreen.alertMessage.getText())) {
                screenshot(ALERT_MSG_UNEXPECTED_ERROR_OCCURRED);
                throw new RuntimeException(ALERT_MSG_UNEXPECTED_ERROR_OCCURRED);
            } else if (ALERT_MSG_WORKFLOW_STATUS_HAS_BEEN_UPDATED.equalsIgnoreCase(submitScreen.alertMessage.getText())) {
                throw new RuntimeException(ALERT_MSG_WORKFLOW_STATUS_HAS_BEEN_UPDATED);
            } else {
                screenshot("None of Alert Message are matched");
                throw new RuntimeException("None of Alert Message are matched");
            }
            tapOnFormDoneButton();
            verifySubmitStatus(workflowType, workflowCount);
        }
    }

    class PageObjects {
        @FindBy(xpath = "//XCUIElementTypeAlert//XCUIElementTypeButton[1]")
        WebElement alertOkButton;

        @FindBy(xpath = "//XCUIElementTypeAlert//XCUIElementTypeStaticText[2]")
        WebElement alertMessage;

        @FindBy(xpath = "//XCUIElementTypeAlert//XCUIElementTypeStaticText[1]")
        WebElement alertTitle;
    }
}
