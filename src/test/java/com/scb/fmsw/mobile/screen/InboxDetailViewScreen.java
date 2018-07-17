package com.scb.fmsw.mobile.screen;

import com.scb.fmsw.mobile.WorkflowConstants;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.scb.fmsw.mobile.base.BaseScreen;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

public class InboxDetailViewScreen extends BaseScreen implements WorkflowConstants {

    private PageObjects inboxDetailViewScreen;

    //xpath
    private String prevActorTypeValue = "//XCUIElementTypeStaticText[@name='Prev Actor Type']/preceding-sibling::XCUIElementTypeStaticText";
    private String workflowStatusValue = "//XCUIElementTypeStaticText[@name='Workflow Status']/preceding-sibling::XCUIElementTypeStaticText";
    private String workflowEventStatusValue = "//XCUIElementTypeStaticText[@name='Workflow Event Status']/preceding-sibling::XCUIElementTypeStaticText";
    private String cellValue = "//XCUIElementTypeStaticText[@name='$1']/preceding-sibling::XCUIElementTypeStaticText[@value='$2']";
    private String tableCell = "//XCUIElementTypeStaticText[@name='$1']/ancestor::XCUIElementTypeCell";

    public InboxDetailViewScreen(IOSDriver<IOSElement> testDriver) {
        iosDriver = testDriver;
        inboxDetailViewScreen = new PageObjects();
        PageFactory.initElements(iosDriver, inboxDetailViewScreen);
    }

    /**
     * This method will check if Button Container has loaded
     *
     * @return boolean
     */
    private boolean hasButtonContainerLoaded() {
        return inboxDetailViewScreen.buttonContainer.isDisplayed();
    }

    /**
     * This method will tap on Acknowledge button
     *
     * @return AcknowledgeScreen
     */
    public AcknowledgeScreen tapOnAcknowledgeButton() {
        hasLoadingCompleted();
        if (hasButtonContainerLoaded()) {
            inboxDetailViewScreen.acknowledgeButton.click();
            System.out.println("Navigate to Acknowledge Screen");
        } else {
            throw new RuntimeException(ERROR_MSG_BUTTON_CONTAINER_NOT_LOADED);
        }
        return new AcknowledgeScreen(iosDriver);
    }

    /**
     * This method will tap on Bookmark button
     *
     * @return BookmarkScreen
     */
    public BookmarkScreen tapOnBookmarkButton() {
        hasLoadingCompleted();
        if (hasButtonContainerLoaded()) {
            inboxDetailViewScreen.bookmarkButton.click();
            System.out.println("Navigate to Bookmark Screen");
        } else {
            throw new RuntimeException(ERROR_MSG_BUTTON_CONTAINER_NOT_LOADED);
        }
        return new BookmarkScreen(iosDriver);
    }

    /**
     * This method will tap on Clarification button
     *
     * @return ClarificationOptionScreen
     */
    public ClarificationOptionScreen tapOnClarificationButton() {
        hasLoadingCompleted();
        if (hasButtonContainerLoaded()) {
            try {
                inboxDetailViewScreen.clarificationButton.click();
            } catch (Exception e) {
                inboxDetailViewScreen.newClarificationButton.click();
            }
            System.out.println("Navigate to Clarification Option Screen");
        } else {
            throw new RuntimeException(ERROR_MSG_BUTTON_CONTAINER_NOT_LOADED);
        }
        return new ClarificationOptionScreen(iosDriver);
    }

    /**
     * This method will tap on Clarification button for CE workflow
     *
     * @return
     */
    public ClarificationScreen tapOnClarificationButtonForCE() {
        hasLoadingCompleted();
        if (hasButtonContainerLoaded()) {
            try {
                inboxDetailViewScreen.clarificationButton.click();
            } catch (Exception e) {
                inboxDetailViewScreen.newClarificationButton.click();
            }
            System.out.println("Navigate to Clarification Screen");
        } else {
            throw new RuntimeException(ERROR_MSG_BUTTON_CONTAINER_NOT_LOADED);
        }
        return new ClarificationScreen(iosDriver);
    }

    /**
     * This method will tap on Delegate button
     *
     * @return ExplicitDelegationScreen
     */
    public ExplicitDelegationScreen tapOnDelegateButton() {
        hasLoadingCompleted();
        if (hasButtonContainerLoaded()) {
            inboxDetailViewScreen.delegateButton.click();
            System.out.println("Navigate to Explicit Delegation Screen");
        } else {
            throw new RuntimeException(ERROR_MSG_BUTTON_CONTAINER_NOT_LOADED);
        }
        return new ExplicitDelegationScreen(iosDriver);
    }

    /**
     * This method will tap on Submit button will navigate to Submit Screen
     *
     * @return SubmitScreen
     */
    public SubmitScreen tapOnSubmitButton() {
        hasLoadingCompleted();
        if (hasButtonContainerLoaded()) {
            try {
                inboxDetailViewScreen.submitButton.click();
            } catch (Exception e) {
                inboxDetailViewScreen.newSubmitButton.click();
            }
            System.out.println("Navigate to Submit Screen");
        } else {
            throw new RuntimeException(ERROR_MSG_BUTTON_CONTAINER_NOT_LOADED);
        }
        return new SubmitScreen(iosDriver);
    }

    /**
     * This method will tap on Submit button will navigate to Submit Option Screen
     *
     * @return SubmitOptionScreen
     */
    public SubmitOptionScreen tapOnSubmitButtonVE() {
        hasLoadingCompleted();
        if (hasButtonContainerLoaded()) {
            try {
                inboxDetailViewScreen.submitButton.click();
            } catch (Exception e) {
                inboxDetailViewScreen.newSubmitButton.click();
            }
            System.out.println("Navigate to Submit Option Screen");
        } else {
            throw new RuntimeException(ERROR_MSG_BUTTON_CONTAINER_NOT_LOADED);
        }
        return new SubmitOptionScreen(iosDriver);
    }

    /**
     * This method will tap on Recall button
     *
     * @return RecallScreen
     */
    public RecallScreen tapOnRecallButton() {
        hasLoadingCompleted();
        if (hasButtonContainerLoaded()) {
            inboxDetailViewScreen.recallButton.click();
            System.out.println("Navigate to Recall Screen");
        } else {
            throw new RuntimeException(ERROR_MSG_BUTTON_CONTAINER_NOT_LOADED);
        }
        return new RecallScreen(iosDriver);
    }

    /**
     * This method will tap on Reassign button
     *
     * @return ReassignScreen
     */
    public ReassignScreen tapOnReassignButton() {
        hasLoadingCompleted();
        if (hasButtonContainerLoaded()) {
            inboxDetailViewScreen.reassignButton.click();
            System.out.println("Navigate to Reassign Screen");
        } else {
            throw new RuntimeException(ERROR_MSG_BUTTON_CONTAINER_NOT_LOADED);
        }
        return new ReassignScreen(iosDriver);
    }

    /**
     * This method return Bookmark value
     *
     * @return String
     */
    public String getBookmarkValue() {
        scrollToTop();
        try {
            scrollDownUntilElementIsDisplayed(waitForElementByXpath(tableCell.replace("$1", "Bookmarked?")));
            return waitForElementByXpath(cellValue.replace("$1", "Bookmarked?")
                    .replace("$2", "Y")).getText();
        } catch (Exception e) {
            throw new RuntimeException(ERROR_MSG_UNABLE_TO_FIND_BOOKMARK_ELEMENT);
        }
    }

    /**
     * This method return Prev Actor Type value
     *
     * @return String
     */
    public String getPrevActorTypeValue() {
        scrollToTop();
        try {
            scrollDownUntilElementIsDisplayed(waitForElementByXpath(tableCell.replace("$1", "Prev Actor Type")));
            String prevActorType = waitForElementByXpath(prevActorTypeValue).getText();
            System.out.println("Prev Actor Type: " + prevActorType);
            return prevActorType;
        } catch (Exception e) {
            throw new RuntimeException(ERROR_MSG_UNABLE_TO_FIND_PREV_ACTOR_TYPE_ELEMENT);
        }
    }

    /**
     * This method return Workflow Status value
     *
     * @return String
     */
    public String getWorkflowStatusValue() {
        scrollToTop();
        try {
            scrollDownUntilElementIsDisplayed(waitForElementByXpath(tableCell.replace("$1", "Workflow Status")));
            String workflowStatus = waitForElementByXpath(workflowStatusValue).getText();
            System.out.println("Workflow Status: " + workflowStatus);
            return workflowStatus;
        } catch (Exception e) {
            throw new RuntimeException(ERROR_MSG_UNABLE_TO_FIND_WORKFLOW_STATUS_ELEMENT);
        }
    }

    /**
     * This method compare Comment
     *
     * @return boolean
     */
    public boolean compareComment(String columnName) {
        scrollToTop();
        try {
            scrollDownUntilElementIsDisplayed(waitForElementByXpath(tableCell.replace("$1", columnName)));
            if (MSG_ENTER_COMMENT.equals(waitForElementByXpath(cellValue.replace("$1", columnName)
                    .replace("$2", MSG_ENTER_COMMENT)).getText())) {
                System.out.println("Verified " + columnName);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(ERROR_MSG_UNABLE_TO_FIND_COMMENT_ELEMENT.replace("$1", columnName));
        }
        return false;
    }

    /**
     * This method compare Comment
     *
     * @return boolean
     */
    public boolean compareComment(String columnName, String comment) {
        scrollToTop();
        try {
            scrollDownUntilElementIsDisplayed(waitForElementByXpath(tableCell.replace("$1", columnName)));
            if (comment.equals(waitForElementByXpath(cellValue.replace("$1", columnName)
                    .replace("$2", comment)).getText())) {
                System.out.println("Verified " + columnName);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(ERROR_MSG_UNABLE_TO_FIND_COMMENT_ELEMENT.replace("$1", columnName));
        }
        return false;
    }

    /**
     * This method compare Workflow Status
     *
     * @param workflowStatus
     * @return boolean
     */
    public boolean compareWorkflowStatus(String workflowStatus) {
        scrollToTop();
        try {
            scrollDownUntilElementIsDisplayed(waitForElementByXpath(tableCell.replace("$1", "Workflow Status")));
            if (workflowStatus.equals(waitForElementByXpath(cellValue.replace("$1", "Workflow Status")
                    .replace("$2", workflowStatus)).getText().trim())) {
                System.out.println("Verified Workflow Status");
                return true;
            }
        } catch (Exception e) {
            throw new RuntimeException(ERROR_MSG_UNABLE_TO_FIND_WORKFLOW_STATUS_ELEMENT);
        }
        return false;
    }

    /**
     * This method compare Workflow Event Status
     *
     * @param workflowEventStatus
     * @return boolean
     */
    public boolean compareWorkflowEventStatus(String workflowEventStatus) {
        scrollToTop();
        try {
            scrollDownUntilElementIsDisplayed(waitForElementByXpath(tableCell.replace("$1", "Workflow Event Status")));
            if (workflowEventStatus.equals(waitForElementByXpath(workflowEventStatusValue).getText().trim())) {
                System.out.println("Verified Workflow Event Status");
                tapOnBackButton();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            screenshot("test");
            throw new RuntimeException(ERROR_MSG_UNABLE_TO_FIND_WORKFLOW_EVENT_STATUS_ELEMENT);
        }
        return false;
    }

    /**
     * This method will tap on back button
     *
     * @return InboxScreen
     */
    public InboxScreen tapOnBackButton() {
        inboxDetailViewScreen.backButton.click();
        return new InboxScreen(iosDriver);
    }

    /**
     * This method will navigate to Inbox Details Option Screen
     *
     * @return InboxDetailsOptionScreen
     */
    public InboxDetailsOptionScreen navigateToInboxDetailsOptionScreen() {
        tapOnMoreOptionButton();
        System.out.println("Navigate to Inbox Details Option Screen");
        return new InboxDetailsOptionScreen(iosDriver);
    }

    class PageObjects {
        @FindBy(id = "Group 7 Copy")
        WebElement acknowledgeButton;

        @FindBy(id = "clarificationWhite")
        WebElement newClarificationButton;

        @FindBy(id = "ic_quick_contacts_mail copy")
        WebElement clarificationButton;

        @FindBy(id = "re-assign copy")
        WebElement delegateButton;

        @FindBy(id = "ic_book_white")
        WebElement bookmarkButton;

        @FindBy(id = "ic_reply copy")
        WebElement submitButton;

        @FindBy(id = "submitWhite")
        WebElement newSubmitButton;

        @FindBy(id = "recall_White")
        WebElement recallButton;

        @FindBy(xpath = "//XCUIElementTypeCollectionView")
        WebElement buttonContainer;

        @FindBy(id = "left angle")
        WebElement backButton;

        @FindBy(id = "account-convert")
        WebElement reassignButton;
    }

}
