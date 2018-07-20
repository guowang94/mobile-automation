package com.scb.fmsw.mobile.screen;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.scb.fmsw.mobile.base.BaseScreen;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

public class BookmarkScreen extends BaseScreen {

    private PageObjects bookmarkScreen;
    private String alertTitle = "//XCUIElementTypeAlert//XCUIElementTypeStaticText[1]";

    public BookmarkScreen(IOSDriver<IOSElement> testDriver) {
        iosDriver = testDriver;
        bookmarkScreen = new PageObjects();
        PageFactory.initElements(iosDriver, bookmarkScreen);
    }

    /**
     * This method will bookmark the workflow
     *
     * @param workflowType
     * @param workflowCount
     * @return InboxScreen
     */
    public InboxScreen bookmarkWorkflow(String workflowType, int workflowCount) {
        hasLoadingCompleted();
        if (hasFormContainerLoaded()) {
            enterComments(FORM_LABEL_COMMENTS, MSG_ENTER_COMMENT);
            tapOnFormDoneButton();
            verifyBookmarkStatus(workflowType, workflowCount);
        } else {
            throw new RuntimeException(ERROR_MSG_CONTAINER_NOT_LOADED);
        }
        return new InboxScreen(iosDriver);
    }

    /**
     * This method will verify the status of Bookmark
     *
     * @param workflowType
     * @param workflowCount
     */
    private void verifyBookmarkStatus(String workflowType, int workflowCount) {
        hasLoadingCompleted();
        int duration = 20;

        if (workflowCount == 1) {
            duration = 30;
        } else if (workflowCount > 1) {
            duration = duration * workflowCount;
        }
        try {
            waitForElementByXpath(alertTitle, duration);
            if (ALERT_TITLE_SUCCESS.equalsIgnoreCase(bookmarkScreen.alertTitle.getText())) {
                screenshot(SCREENSHOT_MSG_SUCCESSFULLY_BOOKMARK_WORKFLOW.replace("$1 ", workflowType));
                System.out.println(SUCCESS_MSG_SUCCESSFULLY_BOOKMARK_WORKFLOW);
                bookmarkScreen.alertOkButton.click();
            } else if (ALERT_TITLE_FAILED.equalsIgnoreCase(bookmarkScreen.alertTitle.getText())) {
                screenshot(SCREENSHOT_MSG_FAILED_TO_BOOKMARK_WORKFLOW.replace("$1 ", workflowType));
                throw new RuntimeException(FAILED_MSG_FAILED_TO_BOOKMARK_WORKFLOW.replace("$1 ", workflowType));
            }
        } catch (Exception e) {
            throw new RuntimeException(FAILED_MSG_FAILED_TO_BOOKMARK_WORKFLOW.replace("$1 ", workflowType));
        }
    }

    class PageObjects {
        @FindBy(xpath = "//XCUIElementTypeAlert//XCUIElementTypeButton[1]")
        public WebElement alertOkButton;

        @FindBy(xpath = "//XCUIElementTypeAlert//XCUIElementTypeStaticText[1]")
        public WebElement alertTitle;
    }

}
