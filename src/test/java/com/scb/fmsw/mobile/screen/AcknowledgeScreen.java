package com.scb.fmsw.mobile.screen;

import com.scb.fmsw.mobile.WorkflowConstants;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.scb.fmsw.mobile.base.BaseScreen;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

public class AcknowledgeScreen extends BaseScreen implements WorkflowConstants {

    private PageObjects acknowledgeScreen;
    private String lateCode = "//XCUIElementTypeStaticText[@name='$1']";
    private String alertTitle = "//XCUIElementTypeAlert//XCUIElementTypeStaticText[1]";

    public AcknowledgeScreen(IOSDriver<IOSElement> testDriver) {
        iosDriver = testDriver;
        acknowledgeScreen = new PageObjects();
        PageFactory.initElements(iosDriver, acknowledgeScreen);
    }

    /**
     * This method will acknowledge workflow
     *
     * @param lateCode     null if the workflow is not overdue
     * @param workflowType
     * @param count
     * @return InboxScreen
     */
    public InboxScreen acknowledgeWorkflow(String lateCode, String workflowType, int count) {
        hasLoadingCompleted();
        if (lateCode != null) {
            if (hasFormContainerLoaded()) {
                selectLateCode(lateCode);
                tapOnFormDoneButton();
            } else {
                throw new RuntimeException(ERROR_MSG_CONTAINER_NOT_LOADED);
            }
        }
        verifyAcknowledgeStatus(workflowType, count);
        return new InboxScreen(iosDriver);
    }

    /**
     * This method will acknowledge OMR workflow
     *
     * @param lateCode        null if workflow is not late
     * @param acknowledgeCode
     * @param workflowType
     * @param count
     * @return InboxScreen
     */
    public InboxScreen acknowledgeOMRWorkflow(String lateCode, String acknowledgeCode, String workflowType, int count) {
        hasLoadingCompleted();
        if (hasFormContainerLoaded()) {
            selectLateCode(lateCode);
            selectPickerValue(FORM_LABEL_ACKNOWLEDGE_CODE, acknowledgeCode);
            enterComments(FORM_LABEL_ACKNOWLEDGEMENT_COMMENTS, MSG_ENTER_COMMENT);
            tapOnFormDoneButton();
            verifyAcknowledgeStatus(workflowType, count);
        } else {
            throw new RuntimeException(ERROR_MSG_CONTAINER_NOT_LOADED);
        }
        return new InboxScreen(iosDriver);
    }

    /**
     * This method will Review and Assess CE Workflow
     *
     * @param lateResponseCode
     * @param severityValue
     * @param potentialLossValue
     * @param workflowType
     * @param workflowCount
     * @return InboxScreen
     */
    public InboxScreen reviewAndAssessWorkflow(String lateResponseCode, String severityValue,
                                               String potentialLossValue, String workflowType,
                                               int workflowCount) {
        hasLoadingCompleted();
        if (hasFormContainerLoaded()) {
            selectPickerValue(FORM_LABEL_CE_SEVERITY, severityValue);
            enterComments(FORM_LABEL_CE_ISSUE_FLAGGED_BY_MTCR, MSG_ENTER_ISSUE_FLAG_COMMENT);
            enterComments(FORM_LABEL_CE_RISK_ASSESSMENT_AND_ACTIONS_TAKEN_TO_MITIGATE_RISK, MSG_ENTER_RISK_ASSESSMENT_COMMENT);
            enterComments(FORM_LABEL_CE_EXPLANATION_OR_DETAILS_CONTROL_BREAK_DOWN, MSG_ENTER_EXPLANATION_COMMENT);
            enterComments(FORM_LABEL_CE_TYPE_OF_CONTROL_BREAKDOWN, MSG_ENTER_CONTROL_BREAKDOWN_COMMENT);
            selectPickerValue(FORM_LABEL_CE_ANY_POTENTIAL_LOSS, potentialLossValue);
            enterComments(FORM_LABEL_CE_OUTCOME_OF_THE_EXCESS, MSG_ENTER_OUTCOME_COMMENT);
            enterComments(FORM_LABEL_CE_TCRM_GROUP_REMARKS, MSG_ENTER_GROUP_REMARK);
            selectLateResponseCode(lateResponseCode, workflowType);

            tapOnFormDoneButton();
            verifyAcknowledgeStatus(workflowType, workflowCount);
        }
        return new InboxScreen(iosDriver);
    }

    /**
     * This method will Review and Approve CE Workflow
     *
     * @param lateResponseCode
     * @param disciplinaryAction
     * @param workflowType
     * @param workflowCount
     * @return InboxScreen
     */
    public InboxScreen reviewAndApproveWorkflow(String lateResponseCode, String disciplinaryAction,
                                                String workflowType, int workflowCount) {
        hasLoadingCompleted();
        if (hasFormContainerLoaded()) {

            selectPickerValue(FORM_LABEL_CE_DISCIPLINARY_ACTION_TAKEN, disciplinaryAction);
            enterComments(FORM_LABEL_CE_FRONT_OFFICE_JUSTIFICATION_FOR_THE_DISCIPLINARY, MSG_ENTER_FO_JUSTIFICATION_COMMENT);
            enterComments(FORM_LABEL_CE_HOW_WILL_YOU_PREVENT_RECURRENCE, MSG_ENTER_PREVENT_RECURRENCE_COMMENT);
            enterComments(FORM_LABEL_CE_SUPERVISOR_REMARKS, MSG_ENTER_SUPERVISOR_REMARK);
            selectLateResponseCode(lateResponseCode, workflowType);

            tapOnFormDoneButton();
            verifyAcknowledgeStatus(workflowType, workflowCount);
        }
        return new InboxScreen(iosDriver);
    }

    /**
     * This method will Review and Action VE Workflow
     *
     * @param lateResponseCode
     * @param disciplinaryAction
     * @param workflowType
     * @param workflowCount
     * @return InboxScreen
     */
    public InboxScreen reviewAndActionWorkflow(String lateResponseCode, String disciplinaryAction,
                                               String workflowType, int workflowCount) {
        hasLoadingCompleted();
        if (hasFormContainerLoaded()) {
            selectPickerValue(FORM_LABEL_DISCIPLINARY_ACTION_TAKEN, disciplinaryAction);
            enterComments(FORM_LABEL_JUSTIFICATION_FOR_DISCIPLINARY_ACTION, MSG_ENTER_FO_JUSTIFICATION_COMMENT);
            enterComments(FORM_LABEL_HOW_TO_PREVENT_RECURRENCE, MSG_ENTER_PREVENT_RECURRENCE_COMMENT);
            enterComments(FORM_LABEL_REMEDIATION_ACTION, MSG_ENTER_REMEDIATION_ACTION_COMMENT);
            selectRemediationDate("13", "August", "2020");
            enterComments(FORM_LABEL_SUPERVISOR_COMMENTS, MSG_ENTER_VDO_COMMENT);
            selectLateResponseCode(lateResponseCode, workflowType);

            tapOnFormDoneButton();
            verifyAcknowledgeStatus(workflowType, workflowCount);
        }
        return new InboxScreen(iosDriver);
    }

    /**
     * This method will verify the status of the Acknowledgement.
     *
     * @param workflowType
     * @param workflowCount
     */
    private void verifyAcknowledgeStatus(String workflowType, int workflowCount) {
        boolean isAlertPopUpDisplayed = false;
        int duration = 20;
        hasLoadingCompleted();

        if (workflowCount == 1) {
            duration = 30;
        } else if (workflowCount > 1) {
            duration = duration * workflowCount;
        }
        try {
            waitForElementByXpath(alertTitle, duration);
            if (ALERT_TITLE_SUCCESS.equalsIgnoreCase(acknowledgeScreen.alertTitle.getText())) {
                screenshot(SCREENSHOT_MSG_SUCCESSFULLY_ACKNOWLEDGED_WORKFLOW.replace("$1", workflowType));
                System.out.println(SUCCESS_MSG_SUCCESSFULLY_ACKNOWLEDGE_WORKFLOW);
                acknowledgeScreen.alertOkButton.click();
            } else if (ALERT_TITLE_FAILED.equalsIgnoreCase(acknowledgeScreen.alertTitle.getText())) {
                isAlertPopUpDisplayed = true;
                screenshot(SCREENSHOT_MSG_FAILED_TO_ACKNOWLEDGE_WORKFLOW.replace("$1", workflowType));
                throw new RuntimeException(FAILED_MSG_FAILED_TO_ACKNOWLEDGE_WORKFLOW.replace("$1 ", ""));
            }
        } catch (Exception e) {
            if (!isAlertPopUpDisplayed) {
                selectLateCode(LATE_CODE_DEADLINE_MISSED);
            } else if (ALERT_MSG_SELECT_LATE_CODE.equalsIgnoreCase(acknowledgeScreen.alertMessage.getText()) &&
                    WORKFLOW_OMR.equals(workflowType)) {
                acknowledgeScreen.alertOkButton.click();
                selectLateCode(LATE_CODE_DEADLINE_MISSED);
            } else if (ALERT_MSG_SELECT_LATE_RESPONSE_CODE.equalsIgnoreCase(acknowledgeScreen.alertMessage.getText())) {
                acknowledgeScreen.alertOkButton.click();
                selectLateResponseCode(CE_LATE_CODE_OTHERS, workflowType);
            } else if (ALERT_MSG_UNEXPECTED_ERROR_OCCURRED.equalsIgnoreCase(acknowledgeScreen.alertMessage.getText())) {
                screenshot(ALERT_MSG_UNEXPECTED_ERROR_OCCURRED);
                throw new RuntimeException(ALERT_MSG_UNEXPECTED_ERROR_OCCURRED);
            } else if (ALERT_MSG_WORKFLOW_STATUS_HAS_BEEN_UPDATED.equalsIgnoreCase(acknowledgeScreen.alertMessage.getText())) {
                throw new RuntimeException(ALERT_MSG_WORKFLOW_STATUS_HAS_BEEN_UPDATED);
            } else {
                screenshot(ALERT_MSG_NONE_OF_THE_MSG_ARE_MATCHED);
                throw new RuntimeException(ALERT_MSG_NONE_OF_THE_MSG_ARE_MATCHED);
            }
            tapOnFormDoneButton();
            verifyAcknowledgeStatus(workflowType, workflowCount);
        }
    }

    /**
     * This method will select Remediation Date
     *
     * @param day
     * @param month
     * @param year
     */
    private void selectRemediationDate(String day, String month, String year) {
        acknowledgeScreen.remediationDateButton.click();
        acknowledgeScreen.dayPickerWheel.sendKeys(day);
        acknowledgeScreen.monthPickerWheel.sendKeys(month);
        acknowledgeScreen.yearPickerWheel.sendKeys(year);
        acknowledgeScreen.pickerDoneButton.click();
    }

    class PageObjects {
        @FindBy(xpath = "//XCUIElementTypeAlert//XCUIElementTypeStaticText[1]")
        WebElement alertTitle;

        @FindBy(xpath = "//XCUIElementTypeAlert//XCUIElementTypeStaticText[2]")
        WebElement alertMessage;

        @FindBy(xpath = "//XCUIElementTypeAlert//XCUIElementTypeButton[1]")
        WebElement alertOkButton;

        @FindBy(id = "FromDate")
        WebElement remediationDateButton;

        @FindBy(xpath = "//XCUIElementTypePickerWheel[1]")
        WebElement dayPickerWheel;

        @FindBy(xpath = "//XCUIElementTypePickerWheel[2]")
        WebElement monthPickerWheel;

        @FindBy(xpath = "//XCUIElementTypePickerWheel[3]")
        WebElement yearPickerWheel;

        @FindBy(id = "Done")
        WebElement pickerDoneButton;
    }

}
