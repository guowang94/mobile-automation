package com.scb.fmsw.mobile.screen;

import com.scb.fmsw.mobile.WorkflowConstants;
import com.scb.fmsw.mobile.base.BaseScreen;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ReassignScreen extends BaseScreen implements WorkflowConstants {

    private PageObjects reassignScreen;
    private String container = "(//XCUIElementTypeImage[@name='Rectangle 4'])[2]";
    private String alertTitle = "//XCUIElementTypeAlert//XCUIElementTypeStaticText[1]";

    public ReassignScreen(IOSDriver testDriver) {
        iosDriver = testDriver;
        reassignScreen = new PageObjects();
        PageFactory.initElements(iosDriver, reassignScreen);
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
     * This method will reassign workflow
     *
     * @param searchQuery
     * @param workflowType
     * @param workflowCount
     * @return InboxScreen
     */
    public InboxScreen reassignWorkflow(String searchQuery, String workflowType, int workflowCount) {
        hasLoadingCompleted();
        if (hasContainerLoaded()) {
            searchResult(searchQuery);
            enterComment();
            reassignScreen.reassignButton.click();
            verifyReassignStatus(workflowType, workflowCount);
        } else {
            throw new RuntimeException(ERROR_MSG_CONTAINER_NOT_LOADED);
        }
        return new InboxScreen(iosDriver);
    }

    /**
     * this method will key in comment
     */
    private void enterComment() {
        reassignScreen.commentTextbox.sendKeys(MSG_ENTER_COMMENT);
    }

    /**
     * this method will search for group
     *
     * @param searchQuery
     */
    private void searchResult(String searchQuery) {
        reassignScreen.SearchTextbox.sendKeys(searchQuery);
        hasLoadingCompleted();
        try {
            reassignScreen.searchResult.click();
        } catch (Exception e) {
            screenshot(SCREENSHOT_MSG_NO_RESULT_FOUND);
            throw new RuntimeException(ERROR_MSG_NO_RESULT_FOUND.replace("$1", searchQuery));
        }
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
            waitForElementByXpath(alertTitle, duration);
            if ("Message".equalsIgnoreCase(reassignScreen.alertTitle.getText())) {
                screenshot(SCREENSHOT_MSG_SUCCESSFULLY_REASSIGN_WORKFLOW.replace("$1", workflowType));
                System.out.println(SUCCESS_MSG_SUCCESSFULLY_REASSIGN_WORKFLOW);
                reassignScreen.alertOkButton.click();
            } else if ("Alert!".equalsIgnoreCase(reassignScreen.alertTitle.getText())) {
                screenshot(SCREENSHOT_MSG_FAILED_TO_REASSIGN_WORKFLOW.replace("$1 ", workflowType));
                throw new RuntimeException(FAILED_MSG_FAILED_TO_REASSIGN_WORKFLOW.replace("$1 ", "")); // so it will go to catch loop
            }
        } catch (Exception e) {
            throw new RuntimeException(FAILED_MSG_FAILED_TO_REASSIGN_WORKFLOW.replace("$1 ", "")); // print out the error message
        }
    }

    class PageObjects {
        @FindBy(id = "Reassign")
        WebElement reassignButton;

        @FindBy(xpath = "//XCUIElementTypeAlert//XCUIElementTypeStaticText[1]")
        WebElement alertTitle;

        @FindBy(xpath = "//XCUIElementTypeAlert//XCUIElementTypeButton[1]")
        WebElement alertOkButton;

        @FindBy(id = "SearchText")
        WebElement SearchTextbox;

        @FindBy(xpath = "//XCUIElementTypeCell[@visible='true']")
        WebElement searchResult;

        @FindBy(id = "comment0")
        WebElement commentTextbox;
    }
}
