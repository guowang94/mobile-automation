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
    private String container = "(//XCUIElementTypeImage[@name='Rectangle 4'])[2]";
    private String alertTitle = "//XCUIElementTypeAlert//XCUIElementTypeStaticText[1]";

    public AcknowledgeScreen(IOSDriver<IOSElement> testDriver) {
        iosDriver = testDriver;
        acknowledgeScreen = new PageObjects();
        PageFactory.initElements(iosDriver, acknowledgeScreen);
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
            if (hasContainerLoaded()) {
                enterLateComment();
                selectAcknowledgeCode(lateCode);
                acknowledgeScreen.acknowledgeButton.click();
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
        if (hasContainerLoaded()) {
            selectOMRLateCode(lateCode);
            selectAcknowledgeCode(acknowledgeCode);
            enterFirstComment(MSG_ENTER_COMMENT);

            acknowledgeScreen.acknowledgeButton.click();
            verifyAcknowledgeStatus(workflowType, count);
        } else {
            throw new RuntimeException(ERROR_MSG_CONTAINER_NOT_LOADED);
        }
        return new InboxScreen(iosDriver);
    }

    /**
     * This method will Review and Assess CE Workflow
     *
     * @param lateCode
     * @param severityValue
     * @param potentialLossValue
     * @param workflowType
     * @param workflowCount
     * @return InboxScreen
     */
    public InboxScreen reviewAndAssessWorkflow(String lateCode, String severityValue,
                                               String potentialLossValue, String workflowType,
                                               int workflowCount) {
        //FIXME this method will have error as they change the ids again, now late code textbox and potential loss value is having the same ids

        hasLoadingCompleted();
        if (hasContainerLoaded()) {
            selectLateCode(lateCode);
            selectSeverityValue(severityValue);
            selectPotentialLossValue(potentialLossValue);
            enterFirstComment(MSG_ENTER_ISSUE_FLAG_COMMENT);
            enterSecondComment(MSG_ENTER_RISK_ASSESSMENT_COMMENT);
            enterThirdComment(MSG_ENTER_EXPLANATION_COMMENT);
            enterFourthComment(MSG_ENTER_CONTROL_BREAKDOWN_COMMENT);
            enterFifthComment(MSG_ENTER_OUTCOME_COMMENT);
            enterSixthComment(MSG_ENTER_GROUP_REMARK);

            acknowledgeScreen.acknowledgeButton.click();
            verifyAcknowledgeStatus(workflowType, workflowCount);
        }
        return new InboxScreen(iosDriver);
    }

    /**
     * This method will Review and Approve CE Workflow
     *
     * @param lateCode
     * @param disciplinaryAction
     * @param workflowType
     * @param workflowCount
     * @return InboxScreen
     */
    public InboxScreen reviewAndApproveWorkflow(String lateCode, String disciplinaryAction,
                                                String workflowType, int workflowCount) {
        hasLoadingCompleted();
        if (hasContainerLoaded()) {
            selectLateCode(lateCode);
            selectDisciplinaryActionPickerValue(disciplinaryAction);
            enterFirstComment(MSG_ENTER_FO_JUSTIFICATION_COMMENT);
            enterSecondComment(MSG_ENTER_PREVENT_RECURRENCE_COMMENT);
            enterThirdComment(MSG_ENTER_SUPERVISOR_REMARK);

            acknowledgeScreen.acknowledgeButton.click();
            verifyAcknowledgeStatus(workflowType, workflowCount);
        }
        return new InboxScreen(iosDriver);
    }

    /**
     * This method will Review and Action VE Workflow
     *
     * @param lateCode
     * @param disciplinaryAction
     * @param workflowType
     * @param workflowCount
     * @return InboxScreen
     */
    public InboxScreen reviewAndActionWorkflow(String lateCode, String disciplinaryAction,
                                               String workflowType, int workflowCount) {
        hasLoadingCompleted();
        if (hasContainerLoaded()) {
            selectVELateCode(lateCode, disciplinaryAction);
            selectDisciplinaryActionPickerValue(disciplinaryAction);
            enterFirstComment(MSG_ENTER_FO_JUSTIFICATION_COMMENT);
            enterSecondComment(MSG_ENTER_PREVENT_RECURRENCE_COMMENT);
            enterThirdComment(MSG_ENTER_REMEDIATION_ACTION_COMMENT);
            enterFourthComment(MSG_ENTER_VDO_COMMENT);

            acknowledgeScreen.acknowledgeButton.click();
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
                acknowledgeWorkflow(LATE_CODE_DEADLINE_MISSED, workflowType, workflowCount);
            } else {
                if (ALERT_MSG_WORKFLOW_STATUS_HAS_BEEN_UPDATED.equalsIgnoreCase(acknowledgeScreen.alertMessage.getText())) {
                    throw new RuntimeException(ALERT_MSG_WORKFLOW_STATUS_HAS_BEEN_UPDATED);
                } else {
                    if (ALERT_MSG_SELECT_LATE_CODE.equalsIgnoreCase(acknowledgeScreen.alertMessage.getText())) {
                        acknowledgeScreen.alertOkButton.click();
                        scrollToTop();
                        selectOMRLateCode(lateCode);
                    } else if (ALERT_MSG_SELECT_TYPE.equalsIgnoreCase(acknowledgeScreen.alertMessage.getText())) {
                        acknowledgeScreen.alertOkButton.click();
                        scrollToTop();
                        selectLateCode(lateCode);
                    } else if (ALERT_MSG_SELECT_DISCIPLINARY_ACTION_TAKEN.equalsIgnoreCase(acknowledgeScreen.alertMessage.getText())) {
                        acknowledgeScreen.alertOkButton.click();
                        scrollToTop();
                        selectVELateCode(VE_LATE_CODE_INVESTIGATION_WITH_HR, VE_DISCIPLINARY_ACTION_COACHING_OR_COUNSELING);
                    }
                    acknowledgeScreen.acknowledgeButton.click();
                    verifyAcknowledgeStatus(workflowType, workflowCount);
                }
            }
        }
    }

    /**
     * This method will select Late Code value and enter Late Comments
     *
     * @param lateCode
     */
    private void selectLateCode(String lateCode) {
        if (lateCode != null) {
            selectAcknowledgeCode(lateCode);
            enterLateComment();
        }
    }

    /**
     * This method will select Late Code value and enter Late Comments for OMR Workflow
     *
     * @param lateCode
     */
    private void selectOMRLateCode(String lateCode) {
        if (lateCode != null) {
            selectOMRLateCodeValue(lateCode);
            enterLateComment();
        }
    }

    /**
     * This method will select Late Code value and enter Late Comments for VE Workflow
     *
     * @param lateCode
     * @param disciplinaryAction
     */
    private void selectVELateCode(String lateCode, String disciplinaryAction) {
        if (lateCode != null) {
            selectDisciplinaryActionDropDownValue(disciplinaryAction);
            //acknowledgecode's element is the same as the late code for CE
            selectAcknowledgeCode(lateCode);
            enterLateComment();
        }
    }

    /**
     * This method will select late code from the picker
     *
     * @param lateCode
     */
    private void selectOMRLateCodeValue(String lateCode) {
        acknowledgeScreen.lateCodePicker.click();
        iosDriver.findElementByXPath(this.lateCode.replace("$1", lateCode)).click();
    }

    /**
     * This method will key in late comment
     */
    private void enterLateComment() {
        acknowledgeScreen.lateCommentTextbox.sendKeys(MSG_ENTER_LATE_COMMENT);
        iosDriver.hideKeyboard();
    }

    /**
     * This method will select acknowledgement code from the picker
     *
     * @param acknowledgementCode
     */
    private void selectAcknowledgeCode(String acknowledgementCode) {
        acknowledgeScreen.acknowledgeCodeTextBox.click();
        acknowledgeScreen.pickerWheel.sendKeys(acknowledgementCode);
        acknowledgeScreen.pickerDoneButton.click();
    }

    /**
     * This method will enter Comment for the First Textbox
     *
     * @param comment
     */
    private void enterFirstComment(String comment) {
        acknowledgeScreen.firstCommentTextbox.sendKeys(comment);
        iosDriver.hideKeyboard();
    }

    /**
     * This method will enter Comment for the Second Textbox
     *
     * @param comment
     */
    private void enterSecondComment(String comment) {
        acknowledgeScreen.secondCommentTextbox.sendKeys(comment);
        iosDriver.hideKeyboard();
    }

    /**
     * This method will enter Comment for the Third Textbox
     *
     * @param comment
     */
    private void enterThirdComment(String comment) {
        acknowledgeScreen.thirdCommentTextbox.sendKeys(comment);
        iosDriver.hideKeyboard();
    }

    /**
     * This method will enter Comment for the Fourth Textbox
     *
     * @param comment
     */
    private void enterFourthComment(String comment) {
        acknowledgeScreen.fourthCommentTextbox.sendKeys(comment);
        iosDriver.hideKeyboard();
    }

    /**
     * This method will enter Comment for the Fifth Textbox
     *
     * @param comment
     */
    private void enterFifthComment(String comment) {
        acknowledgeScreen.fifthCommentTextbox.sendKeys(comment);
        iosDriver.hideKeyboard();
    }

    /**
     * This method will enter Comment for the Sixth Textbox
     *
     * @param comment
     */
    private void enterSixthComment(String comment) {
        acknowledgeScreen.sixthCommentTextbox.sendKeys(comment);
        iosDriver.hideKeyboard();
    }

    /**
     * This method will select Severity Value
     *
     * @param severityValue
     */
    private void selectSeverityValue(String severityValue) {
        acknowledgeScreen.severityButton.click();
        waitForElementById(severityValue).click();
    }

    /**
     * This method will select Potential Loss Value
     *
     * @param potentialLostValue
     */
    private void selectPotentialLossValue(String potentialLostValue) {
        acknowledgeScreen.potentialLossTextBox.click();
        acknowledgeScreen.pickerWheel.sendKeys(potentialLostValue);
        acknowledgeScreen.pickerDoneButton.click();
    }

    /**
     * This method will select Disciplinary Action Value from Picker
     *
     * @param disciplinaryAction
     */
    private void selectDisciplinaryActionPickerValue(String disciplinaryAction) {
        acknowledgeScreen.disciplinaryActionTextbox.click();
        acknowledgeScreen.pickerWheel.sendKeys(disciplinaryAction);
        acknowledgeScreen.pickerDoneButton.click();
    }

    /**
     * This method will select Disciplinary Action Value from Drop Down List
     *
     * @param disciplinaryAction
     */
    private void selectDisciplinaryActionDropDownValue(String disciplinaryAction) {
        acknowledgeScreen.disciplinaryActionButton.click();
        waitForElementById(disciplinaryAction).click();
    }

    class PageObjects {
        @FindBy(id = "Late Code")
        WebElement lateCodePicker;

        @FindBy(id = "Severity*")
        WebElement severityButton;

        @FindBy(id = "Disciplinary Action taken*")
        WebElement disciplinaryActionButton;

        @FindBy(id = "SelectPicker")
        WebElement disciplinaryActionTextbox;

        @FindBy(id = "SelectPicker")
        WebElement potentialLossTextBox;

        @FindBy(id = "SelectPicker")
        WebElement acknowledgeCodeTextBox;

        @FindBy(xpath = "//XCUIElementTypePickerWheel")
        WebElement pickerWheel;

        @FindBy(id = "Done")
        WebElement pickerDoneButton;

        @FindBy(id = "lateComment")
        WebElement lateCommentTextbox;

        @FindBy(id = "comment0")
        WebElement firstCommentTextbox;

        @FindBy(id = "comment1")
        WebElement secondCommentTextbox;

        @FindBy(id = "comment2")
        WebElement thirdCommentTextbox;

        @FindBy(id = "comment3")
        WebElement fourthCommentTextbox;

        @FindBy(id = "comment4")
        WebElement fifthCommentTextbox;

        @FindBy(id = "comment5")
        WebElement sixthCommentTextbox;

        @FindBy(xpath = "//XCUIElementTypeNavigationBar[1]/XCUIElementTypeButton[2]")
        WebElement acknowledgeButton;

        @FindBy(xpath = "//XCUIElementTypeAlert//XCUIElementTypeStaticText[1]")
        WebElement alertTitle;

        @FindBy(xpath = "//XCUIElementTypeAlert//XCUIElementTypeStaticText[2]")
        WebElement alertMessage;

        @FindBy(xpath = "//XCUIElementTypeAlert//XCUIElementTypeButton[1]")
        WebElement alertOkButton;
    }

}
