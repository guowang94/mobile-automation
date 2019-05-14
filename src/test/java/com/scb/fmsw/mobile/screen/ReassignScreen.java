package com.scb.fmsw.mobile.screen;

import com.scb.fmsw.mobile.WorkflowConstants;
import com.scb.fmsw.mobile.base.BaseScreen;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ReassignScreen extends BaseScreen implements WorkflowConstants {

    private PageObjects reassignScreen;
    private String alertTitle = "//XCUIElementTypeAlert//XCUIElementTypeStaticText[1]";

    public ReassignScreen(IOSDriver testDriver) {
        iosDriver = testDriver;
        reassignScreen = new PageObjects();
        PageFactory.initElements(iosDriver, reassignScreen);
    }

    /**
     * This method will reassign workflow
     *
     * @param searchQuery
     * @param workflowType
     * @param workflowCount
     * @param toGroup
     * @return InboxScreen
     */
    public InboxScreen reassignWorkflow(String searchQuery, String workflowType, int workflowCount, boolean toGroup) {
        hasLoadingCompleted();
        if (hasFormContainerLoaded()) {

            if (toGroup) {
                searchForGroup(FORM_LABEL_TO_GROUP, searchQuery);
            } else {
                searchForUser(FORM_LABEL_PSID_OR_NAME, searchQuery);
            }
            enterComments(FORM_LABEL_COMMENTS, MSG_ENTER_COMMENT);
            tapOnFormDoneButton();

            verifyReassignStatus(workflowType, workflowCount);
        } else {
            throw new RuntimeException(ERROR_MSG_CONTAINER_NOT_LOADED);
        }
        return new InboxScreen(iosDriver);
    }

    /**
     * this method will verify the Reassign status
     *
     * @param workflowType
     * @param workflowCount
     */
    private void verifyReassignStatus(String workflowType, int workflowCount) {
        hasLoadingCompleted();
        int duration = 20;
        if (workflowCount == 1) {
            duration = 30;
        } else if (workflowCount > 1) {
            duration = duration * workflowCount;
        }
        try {
            waitForElementByXpath(alertTitle, duration, true);
            if (ALERT_TITLE_SUCCESS.equalsIgnoreCase(reassignScreen.alertTitle.getText())) {
                screenshot(SCREENSHOT_MSG_SUCCESSFULLY_REASSIGN_WORKFLOW.replace("$1", workflowType));
                System.out.println(SUCCESS_MSG_SUCCESSFULLY_REASSIGN_WORKFLOW);
                reassignScreen.alertOkButton.click();
            } else if (ALERT_TITLE_FAILED.equalsIgnoreCase(reassignScreen.alertTitle.getText())) {
                screenshot(SCREENSHOT_MSG_FAILED_TO_REASSIGN_WORKFLOW.replace("$1 ", workflowType));
                throw new RuntimeException(FAILED_MSG_FAILED_TO_REASSIGN_WORKFLOW.replace("$1 ", ""));
            }
        } catch (Exception e) {
            throw new RuntimeException(FAILED_MSG_FAILED_TO_REASSIGN_WORKFLOW.replace("$1 ", ""));
        }
    }

    private class PageObjects {
        @FindBy(xpath = "//XCUIElementTypeAlert//XCUIElementTypeStaticText[1]")
        WebElement alertTitle;

        @FindBy(xpath = "//XCUIElementTypeAlert//XCUIElementTypeButton[1]")
        WebElement alertOkButton;
    }
}
