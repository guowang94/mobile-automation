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
    private String prevActorTypeValue = "//XCUIElementTypeStaticText[@name='Prev Actor Type']/preceding-sibling::XCUIElementTypeStaticText[1]";
    private String currActorValue = "//XCUIElementTypeStaticText[@name='Curr Actor']/preceding-sibling::XCUIElementTypeStaticText[1]";
    private String workflowStatusValue = "//XCUIElementTypeStaticText[@name='Workflow Status']/preceding-sibling::XCUIElementTypeStaticText[1]";
    private String workflowEventStatusValue = "//XCUIElementTypeStaticText[@name='Workflow Event Status']/preceding-sibling::XCUIElementTypeStaticText[1]";
    private String currActorTypeValue = "//XCUIElementTypeStaticText[@name='Curr Actor Type']/preceding-sibling::XCUIElementTypeStaticText[1]";
    private String currActorGroupValue = "//XCUIElementTypeStaticText[@name='Curr Actor Group']/preceding-sibling::XCUIElementTypeStaticText[1]";
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
    public SubmitOptionScreen tapOnSubmitButtonVECE() {
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
     * @param workflowType
     * @return String
     */
    public String getBookmarkValue(String workflowType) {
        scrollToTop();
        try {
            if (WORKFLOW_VE.equals(workflowType)) {
                scrollDownUntilElementIsDisplayed(waitForElementByXpath(tableCell.replace("$1", "Bookmarked"), true));
                return waitForElementByXpath(cellValue.replace("$1", "Bookmarked")
                        .replace("$2", "Y"), true).getText();
            } else {
                scrollDownUntilElementIsDisplayed(waitForElementByXpath(tableCell.replace("$1", "Bookmarked?"), true));
                return waitForElementByXpath(cellValue.replace("$1", "Bookmarked?")
                        .replace("$2", "Y"), true).getText();
            }
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
            scrollDownUntilElementIsDisplayed(waitForElementByXpath(tableCell.replace("$1", "Prev Actor Type"), true));
            String prevActorType = waitForElementByXpath(prevActorTypeValue, true).getText();
            System.out.println("Prev Actor Type: " + prevActorType);
            return prevActorType;
        } catch (Exception e) {
            throw new RuntimeException(ERROR_MSG_UNABLE_TO_FIND_PREV_ACTOR_TYPE_ELEMENT);
        }
    }

    /**
     * This method return Curr Actor value
     *
     * @return String
     */
    public String getCurrActorValue() {
        scrollToTop();
        try {
            scrollDownUntilElementIsDisplayed(waitForElementByXpath(tableCell.replace("$1", "Curr Actor"), true));
            String currActor = waitForElementByXpath(currActorValue, true).getText();
            System.out.println("Curr Actor: " + currActor);
            return currActor.substring(0, 7);
        } catch (Exception e) {
            throw new RuntimeException(ERROR_MSG_UNABLE_TO_FIND_CURR_ACTOR_ELEMENT);
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
            scrollDownUntilElementIsDisplayed(waitForElementByXpath(tableCell.replace("$1", "Workflow Status"), true));
            String workflowStatus = waitForElementByXpath(workflowStatusValue, true).getText();
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
            scrollDownUntilElementIsDisplayed(waitForElementByXpath(tableCell.replace("$1", columnName), true));
            if (MSG_ENTER_COMMENT.equals(waitForElementByXpath(cellValue.replace("$1", columnName)
                    .replace("$2", MSG_ENTER_COMMENT), true).getText())) {
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
            scrollDownUntilElementIsDisplayed(waitForElementByXpath(tableCell.replace("$1", columnName), true));
            if (comment.equals(waitForElementByXpath(cellValue.replace("$1", columnName)
                    .replace("$2", comment), true).getText())) {
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
            scrollDownUntilElementIsDisplayed(waitForElementByXpath(tableCell.replace("$1", "Workflow Status"), true));
            if (workflowStatus.equals(waitForElementByXpath(cellValue.replace("$1", "Workflow Status")
                    .replace("$2", workflowStatus), true).getText().trim())) {
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
            scrollDownUntilElementIsDisplayed(waitForElementByXpath(tableCell.replace("$1", "Workflow Event Status"), true));
            if (workflowEventStatus.equals(waitForElementByXpath(workflowEventStatusValue, true).getText().trim())) {
                System.out.println("Verified Workflow Event Status");
                tapOnBackButton();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(ERROR_MSG_UNABLE_TO_FIND_WORKFLOW_EVENT_STATUS_ELEMENT);
        }
        return false;
    }

    /**
     * This method compare Curr Actor Type
     *
     * @param currActorType
     * @return boolean
     */
    public boolean compareCurrActorType(String currActorType) {
        scrollToTop();
        try {
            scrollDownUntilElementIsDisplayed(waitForElementByXpath(tableCell.replace("$1", "Curr Actor Type"), true));
            if (currActorType.equals(waitForElementByXpath(currActorTypeValue, true).getText().trim())) {
                System.out.println("Verified Curr Actor Type");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(ERROR_MSG_UNABLE_TO_FIND_CURR_ACTOR_TYPE_ELEMENT);
        }
        return false;
    }

    /**
     * This method compare Curr Actor Group
     *
     * @param currActorGroup
     * @return boolean
     */
    public boolean compareCurrActorGroup(String currActorGroup) {
        scrollToTop();
        try {
            scrollDownUntilElementIsDisplayed(waitForElementByXpath(tableCell.replace("$1", "Curr Actor Group"), true));
            if (currActorGroup.equals(waitForElementByXpath(currActorGroupValue, true).getText().trim())) {
                System.out.println("Verified Curr Actor Group");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(ERROR_MSG_UNABLE_TO_FIND_CURR_ACTOR_GROUP_ELEMENT);
        }
        return false;
    }

    /**
     * This method check if Curr Actor Group's value is present
     *
     * @return boolean
     */
    public boolean isCurrActorGroupValuePresent() {
        scrollToTop();
        try {
            scrollDownUntilElementIsDisplayed(waitForElementByXpath(tableCell.replace("$1", "Curr Actor Group"), true));
            if (waitForElementByXpath(currActorGroupValue, true).getText() != null) {
                System.out.println("Curr Actor Group value is present");
                return true;
            } else {
                screenshot("Curr Actor Group value is null");
                System.out.println("Curr Actor Group value is null");
            }
        } catch (NullPointerException e) {
            screenshot("Curr Actor Group value is null");
            System.out.println("Curr Actor Group value is null");
        } catch (Exception e) {
            screenshot(ERROR_MSG_UNABLE_TO_FIND_CURR_ACTOR_GROUP_ELEMENT);
            throw new RuntimeException(ERROR_MSG_UNABLE_TO_FIND_CURR_ACTOR_GROUP_ELEMENT);
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
