package com.scb.fmsw.mobile.screen;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.scb.fmsw.mobile.base.BaseScreen;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

public class SubmitScreen extends BaseScreen {

    private PageObjects submitScreen;
    private String container = "(//XCUIElementTypeImage[@name='Rectangle 4'])[2]";
    private String alertTitle = "//XCUIElementTypeAlert//XCUIElementTypeStaticText[1]";

    public SubmitScreen(IOSDriver<IOSElement> testDriver) {
        iosDriver = testDriver;
        submitScreen = new PageObjects();
        PageFactory.initElements(iosDriver, submitScreen);
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
     * This method will submit overdue workflow
     *
     * @param lateCode
     * @param workflowType
     * @param workflowCount
     * @return InboxScreen
     */
    public InboxScreen submitWorkflow(String lateCode, String workflowType, int workflowCount) {
        hasLoadingCompleted();
        if (hasContainerLoaded()) {
            selectLateCode(lateCode);
            enterFirstComment(MSG_ENTER_COMMENT);
            submitScreen.submitButton.click();
            verifySubmitStatus(workflowType, workflowCount);
        } else {
            throw new RuntimeException(ERROR_MSG_CONTAINER_NOT_LOADED);
        }
        return new InboxScreen(iosDriver);
    }

    /**
     * This method will Edit and Respond CE Workflow
     *
     * @param lateCode
     * @param severityValue
     * @param potentialLossValue
     * @param workflowType
     * @param workflowCount
     * @return InboxScreen
     */
    public InboxScreen editAndRespondCEWorkflow(String lateCode, String severityValue,
                                                String potentialLossValue, String workflowType,
                                                int workflowCount) {
        //FIXME this method might have error as they change the ids again, now late code textbox and potential loss value is having the same ids

        hasLoadingCompleted();
        if (hasContainerLoaded()) {
            selectLateCode(lateCode);
            selectSeverityValue(severityValue);
            selectPotentialLossValue(potentialLossValue);
            enterFirstComment(MSG_ENTER_COMMENT);
            enterSecondComment(MSG_ENTER_ISSUE_FLAG_COMMENT);
            enterThirdComment(MSG_ENTER_RISK_ASSESSMENT_COMMENT);
            enterFourthComment(MSG_ENTER_EXPLANATION_COMMENT);
            enterFifthComment(MSG_ENTER_CONTROL_BREAKDOWN_COMMENT);
            enterSixthComment(MSG_ENTER_OUTCOME_COMMENT);
            enterSeventhComment(MSG_ENTER_GROUP_REMARK);

            submitScreen.submitButton.click();
            verifySubmitStatus(workflowType, workflowCount);
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
        if (hasContainerLoaded()) {
            selectVELateCode(lateCode, disciplinaryAction);
            selectDisciplinaryActionPickerValue(disciplinaryAction);
            enterFirstComment(MSG_ENTER_FO_JUSTIFICATION_COMMENT_EDIT);
            enterSecondComment(MSG_ENTER_PREVENT_RECURRENCE_COMMENT_EDIT);
            enterThirdComment(MSG_ENTER_REMEDIATION_ACTION_COMMENT_EDIT);
            enterFourthComment(MSG_ENTER_VDO_COMMENT_EDIT);

            submitScreen.submitButton.click();
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
            if (ALERT_MSG_WORKFLOW_STATUS_HAS_BEEN_UPDATED.equalsIgnoreCase(submitScreen.alertMessage.getText())) {
                throw new RuntimeException(ALERT_MSG_WORKFLOW_STATUS_HAS_BEEN_UPDATED);
            } else if (ALERT_MSG_ENTER_LATE_COMMENT.equalsIgnoreCase(submitScreen.alertMessage.getText())) {
                submitScreen.alertOkButton.click();
                iosDriver.hideKeyboard();
                if (workflowType.equals(WORKFLOW_CE)) {
                    selectCELateCode(CE_LATE_CODE_OTHERS);
                } else if (workflowType.equals(WORKFLOW_VE)) {
                    //TODO this might not work as the alert message might be different
                    selectVELateCode(VE_LATE_CODE_INVESTIGATION_WITH_HR, VE_DISCIPLINARY_ACTION_COACHING_OR_COUNSELING);
                } else {
                    selectLateCode(LATE_CODE_DEADLINE_MISSED);
                }
            }
            submitScreen.submitButton.click();
            verifySubmitStatus(workflowType, workflowCount);
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
            selectLateCodeValue(lateCode);
            enterLateComment();
        }
    }

    /**
     * This method will select Late Code value and enter Late Comments
     *
     * @param lateCode
     */
    private void selectLateCode(String lateCode) {
        if (lateCode != null) {
            selectLateCodeValue(lateCode);
            enterLateComment();
        }
    }

    /**
     * This method will select Disciplinary Action Value from Picker
     *
     * @param disciplinaryAction
     */
    private void selectDisciplinaryActionPickerValue(String disciplinaryAction) {
        submitScreen.disciplinaryActionTextbox.click();
        submitScreen.pickerWheel.sendKeys(disciplinaryAction);
        submitScreen.pickerDoneButton.click();
    }

    /**
     * This method will select Severity Value
     *
     * @param severityValue
     */
    private void selectSeverityValue(String severityValue) {
        submitScreen.severityTextBox.click();
        submitScreen.pickerWheel.sendKeys(severityValue);
        submitScreen.pickerDoneButton.click();
    }

    /**
     * This method will select Potential Loss Value
     *
     * @param potentialLostValue
     */
    private void selectPotentialLossValue(String potentialLostValue) {
        submitScreen.potentialLossTextBox.click();
        submitScreen.pickerWheel.sendKeys(potentialLostValue);
        submitScreen.pickerDoneButton.click();
    }

    /**
     * This method will select CE Late Code
     *
     * @param lateCode
     */
    private void selectCELateCode(String lateCode) {
        submitScreen.ceLateCode.click();
        waitForElementById(lateCode).click();
    }

    /**
     * This method will key in late comment
     */
    private void enterLateComment() {
        submitScreen.lateCommentTextbox.sendKeys(MSG_ENTER_LATE_COMMENT);
    }

    /**
     * This method will select late code from the picker
     *
     * @param lateCode
     */
    private void selectLateCodeValue(String lateCode) {
        submitScreen.lateCodeTextBox.click();
        submitScreen.pickerWheel.sendKeys(lateCode);
        submitScreen.pickerDoneButton.click();
    }

    /**
     * This method will select Disciplinary Action Value from Drop Down List
     *
     * @param disciplinaryAction
     */
    private void selectDisciplinaryActionDropDownValue(String disciplinaryAction) {
        submitScreen.disciplinaryActionButton.click();
        waitForElementById(disciplinaryAction).click();
    }

    /**
     * This method will enter Comment for the First Textbox
     *
     * @param comment
     */
    private void enterFirstComment(String comment) {
        submitScreen.firstCommentTextbox.clear();
        submitScreen.firstCommentTextbox.sendKeys(comment);
        iosDriver.hideKeyboard();
    }

    /**
     * This method will enter Comment for the Second Textbox
     *
     * @param comment
     */
    private void enterSecondComment(String comment) {
        submitScreen.secondCommentTextbox.clear();
        submitScreen.secondCommentTextbox.sendKeys(comment);
        iosDriver.hideKeyboard();
    }

    /**
     * This method will enter Comment for the Third Textbox
     *
     * @param comment
     */
    private void enterThirdComment(String comment) {
        submitScreen.thirdCommentTextbox.clear();
        submitScreen.thirdCommentTextbox.sendKeys(comment);
        iosDriver.hideKeyboard();
    }

    /**
     * This method will enter Comment for the Fourth Textbox
     *
     * @param comment
     */
    private void enterFourthComment(String comment) {
        submitScreen.fourthCommentTextbox.clear();
        submitScreen.fourthCommentTextbox.sendKeys(comment);
        iosDriver.hideKeyboard();
    }

    /**
     * This method will enter Comment for the Fifth Textbox
     *
     * @param comment
     */
    private void enterFifthComment(String comment) {
        submitScreen.fifthCommentTextbox.sendKeys(comment);
        iosDriver.hideKeyboard();
    }

    /**
     * This method will enter Comment for the Sixth Textbox
     *
     * @param comment
     */
    private void enterSixthComment(String comment) {
        submitScreen.sixthCommentTextbox.sendKeys(comment);
        iosDriver.hideKeyboard();
    }

    /**
     * This method will enter Comment for the Seventh Textbox
     *
     * @param comment
     */
    private void enterSeventhComment(String comment) {
        submitScreen.seventhCommentTextbox.sendKeys(comment);
        iosDriver.hideKeyboard();
    }

    class PageObjects {
        @FindBy(xpath = "//XCUIElementTypeAlert//XCUIElementTypeButton[1]")
        WebElement alertOkButton;

        @FindBy(xpath = "//XCUIElementTypeAlert//XCUIElementTypeStaticText[2]")
        WebElement alertMessage;

        @FindBy(xpath = "//XCUIElementTypeAlert//XCUIElementTypeStaticText[1]")
        WebElement alertTitle;

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

        @FindBy(id = "comment6")
        WebElement seventhCommentTextbox;

        @FindBy(xpath = "//XCUIElementTypeNavigationBar/XCUIElementTypeButton[2]")
        WebElement submitButton;

        @FindBy(id = "lateComment")
        WebElement lateCommentTextbox;

        @FindBy(id = "SelectPicker")
        WebElement lateCodeTextBox;

        @FindBy(id = "SelectPicker")
        WebElement disciplinaryActionTextbox;

        @FindBy(xpath = "//XCUIElementTypePickerWheel")
        WebElement pickerWheel;

        @FindBy(id = "Done")
        WebElement pickerDoneButton;

        @FindBy(id = "Late Response Code")
        WebElement ceLateCode;

        @FindBy(id = "OneMore_Select")
        WebElement potentialLossTextBox;

        @FindBy(id = "SelectPicker")
        WebElement severityTextBox;

        @FindBy(id = "Disciplinary Action taken*")
        WebElement disciplinaryActionButton;
    }
}
