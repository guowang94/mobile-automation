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
     * @param toggleSwitch always false for Middle Office, Line Manager and Performer
     * @param workflowType
     * @param workflowCount
     * @return InboxScreen
     */
    public InboxScreen clarifyWorkflow(String lateCode, String userID, boolean toggleSwitch, String workflowType, int workflowCount) {
        hasLoadingCompleted();
        if (hasContainerLoaded()) {
            if (userID != null) {
                searchForUser(userID);
            }
            if (lateCode != null) {
                enterLateComment();
                selectLateCode(lateCode);
            }
            enterComment();
            toggleSwitch(toggleSwitch);
            clarificationScreen.clarifyButton.click();
            verifyClarificationStatus(workflowType, workflowCount);
        } else {
            System.out.println(hasContainerLoaded());
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
        if (hasContainerLoaded()) {
            if (lateCode != null) {
                selectLateCode(lateCode);
                enterLateComment();
            }
            enterComment();
            clarificationScreen.clarifyButton.click();
            verifyClarificationStatus(workflowType, workflowCount);
        } else {
            System.out.println(hasContainerLoaded());
            throw new RuntimeException(ERROR_MSG_CONTAINER_NOT_LOADED);
        }
        return new InboxScreen(iosDriver);
    }

    /**
     * this method will key in late comment
     */
    private void enterLateComment() {
        clarificationScreen.lateCommentTextbox.sendKeys(MSG_ENTER_LATE_COMMENT);
    }

    /**
     * this method will select late code from the picker
     *
     * @param lateCode
     */
    private void selectLateCode(String lateCode) {
        clarificationScreen.lateCodeTextBox.click();
        clarificationScreen.lateCodePicker.sendKeys(lateCode);
        clarificationScreen.pickerDoneButton.click();
    }

    /**
     * this method will key in comment
     */
    private void enterComment() {
        clarificationScreen.commentTextbox.sendKeys(MSG_ENTER_COMMENT);
        iosDriver.hideKeyboard();
    }

    /**
     * this method will toggle the switch based on the user choice
     *
     * @param userChoice
     */
    private void toggleSwitch(boolean userChoice) {
        if (userChoice) {
            try {
                clarificationScreen.toggleSwitch.click();
            } catch (Exception e) {
                System.out.println(ERROR_MSG_DO_NOT_HAVE_SWITCH_ELEMENT);
            }
        }
    }

    /**
     * this method will search for user based on the user id
     *
     * @param userid
     */
    private void searchForUser(String userid) {
        clarificationScreen.psidSearchTextbox.sendKeys(userid.substring(0, 5));
        hasLoadingCompleted();
        try {
            waitForElementById(userid).click();
        } catch (Exception e) {
            screenshot(SCREENSHOT_MSG_NO_USER_FOUND);
            throw new RuntimeException(ERROR_MSG_NO_USER_FOUND.replace("$1", userid));
        }
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
            if (ALERT_MSG_WORKFLOW_STATUS_HAS_BEEN_UPDATED.equalsIgnoreCase(clarificationScreen.alertMessage.getText())) {
                throw new RuntimeException(ALERT_MSG_WORKFLOW_STATUS_HAS_BEEN_UPDATED);
            } else if (ALERT_MSG_ENTER_LATE_COMMENT.equalsIgnoreCase(clarificationScreen.alertMessage.getText())) {
                clarificationScreen.alertOkButton.click();
                iosDriver.hideKeyboard();
                if (workflowType.equals(WORKFLOW_CE)) {
                    clarifyCEWorkflow(CE_LATE_CODE, workflowType, workflowCount);
                } else {
                    clarifyWorkflow(LATE_CODE_DEADLINE_MISSED, null, false, workflowType, workflowCount);
                }
            }
        }
    }

    class PageObjects {
        @FindBy(id = "lateComment")
        WebElement lateCommentTextbox;

        @FindBy(id = "SelectPicker")
        WebElement lateCodeTextBox;

        @FindBy(xpath = "//XCUIElementTypePickerWheel")
        WebElement lateCodePicker;

        @FindBy(id = "Done")
        WebElement pickerDoneButton;

        @FindBy(id = "comment0")
        WebElement commentTextbox;

        @FindBy(id = "Send")
        WebElement clarifyButton;

        @FindBy(xpath = "//XCUIElementTypeAlert//XCUIElementTypeStaticText[1]")
        WebElement alertTitle;

        @FindBy(xpath = "//XCUIElementTypeAlert//XCUIElementTypeStaticText[2]")
        WebElement alertMessage;

        @FindBy(xpath = "//XCUIElementTypeAlert//XCUIElementTypeButton[1]")
        WebElement alertOkButton;

        @FindBy(className = "XCUIElementTypeSwitch")
        WebElement toggleSwitch;

        @FindBy(id = "SearchText")
        WebElement psidSearchTextbox;
    }

}
