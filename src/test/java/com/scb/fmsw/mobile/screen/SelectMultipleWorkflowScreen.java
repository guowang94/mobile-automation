package com.scb.fmsw.mobile.screen;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.scb.fmsw.mobile.base.BaseScreen;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

public class SelectMultipleWorkflowScreen extends BaseScreen {

    private PageObjects selectMultipleWorkflowScreen;

    //xpath
    private String workflow = "//XCUIElementTypeStaticText[@value='$1']";
    private String workflowID = "(//XCUIElementTypeStaticText[@name='WorkflowId'])[$1]";
    private String cnaWorkflowID = "(//XCUIElementTypeStaticText[@name='CNAWorkflowId'])[$1]";
    private String tableContainer = "//XCUIElementTypeTable[@visible='true']";

    public SelectMultipleWorkflowScreen(IOSDriver<IOSElement> testDriver) {
        iosDriver = testDriver;
        selectMultipleWorkflowScreen = new PageObjects();
        PageFactory.initElements(iosDriver, selectMultipleWorkflowScreen);
    }

    /**
     * This method will check if Table Container has loaded
     *
     * @return boolean
     */
    private boolean hasTableContainerLoaded() {
        return waitForElementByXpath(tableContainer, true).isDisplayed();
    }

    /**
     * This method will select 'n' workflow and return a list of selected workflow's ID
     *
     * @param workflowCount - number of workflow to be selected
     * @return List<String>
     */
    public List<String> selectNumberOfWorkflow(int workflowCount) {
        hasLoadingCompleted();
        List<String> workflowIdList = new ArrayList<>();
        if (hasTableContainerLoaded()) {
            for (int i = 1; i < workflowCount + 1; i++) {
                WebElement element = waitForElementByXpath(workflowID.replace("$1", String.valueOf(i)), true);
                scrollDownUntilElementIsDisplayed(element);
                workflowIdList.add(element.getText());
                element.click();
            }
        } else {
            throw new RuntimeException(ERROR_MSG_TABLE_CONTAINER_NOT_LOADED);
        }
        return workflowIdList;
    }

    /**
     * This method will select 'n' workflow and return a list of selected workflow's ID
     *
     * @param workflowCount - number of workflow to be selected
     * @return List<String>
     */
    public List<String> selectNumberOfCNAWorkflow(int workflowCount) {
        hasLoadingCompleted();
        List<String> workflowIdList = new ArrayList<>();
        if (hasTableContainerLoaded()) {
            for (int i = 1; i < workflowCount + 1; i++) {
                WebElement element = waitForElementByXpath(cnaWorkflowID.replace("$1", String.valueOf(i)), true);
                scrollDownUntilElementIsDisplayed(element);
                System.out.println("Workflow ID: " + element.getText());
                workflowIdList.add(element.getText());
                element.click();
            }
            screenshot("Selected Workflow(s)");
        } else {
            throw new RuntimeException(ERROR_MSG_TABLE_CONTAINER_NOT_LOADED);
        }
        return workflowIdList;
    }

    /**
     * This method will select the workflow that is included in the workflow ID list
     *
     * @param workflowIDList
     */
    public void selectNumberOfWorkflow(List<String> workflowIDList) {
        hasLoadingCompleted();
        if (hasTableContainerLoaded()) {
            for (int i = 0; i < workflowIDList.size(); i++) {
                WebElement element = waitForElementByXpath(workflow.replace("$1", workflowIDList.get(i)), true);
                scrollDownUntilElementIsDisplayed(element);
                element.click();
            }
            screenshot("Selected Workflow(s)");
        } else {
            throw new RuntimeException(ERROR_MSG_TABLE_CONTAINER_NOT_LOADED);
        }
    }

    /**
     * This method will select the workflow that is included in the workflow ID list
     *
     * @param workflowIDList
     */
    public void selectNumberOfCNAWorkflow(List<String> workflowIDList) {
        hasLoadingCompleted();
        if (hasTableContainerLoaded()) {
            for (int i = 0; i < workflowIDList.size(); i++) {
                WebElement element = waitForElementByXpath(cnaWorkflowID.replace("$1", workflowIDList.get(i)), true);
                scrollDownUntilElementIsDisplayed(element);
                element.click();
            }
        } else {
            throw new RuntimeException(ERROR_MSG_TABLE_CONTAINER_NOT_LOADED);
        }
    }

    /**
     * This method will select the workflow based on workflow ID
     *
     * @param workflowID
     */
    public void selectNumberOfWorkflow(String workflowID) {
        hasLoadingCompleted();
        if (hasTableContainerLoaded()) {
            WebElement element = waitForElementByXpath(workflow.replace("$1", workflowID), true);
            scrollDownUntilElementIsDisplayed(element);
            element.click();
        } else {
            throw new RuntimeException(ERROR_MSG_TABLE_CONTAINER_NOT_LOADED);
        }
    }

    /**
     * This method will select the workflow based on workflow ID
     *
     * @param workflowID
     */
    public void selectNumberOfCNAWorkflow(String workflowID) {
        hasLoadingCompleted();
        if (hasTableContainerLoaded()) {
            WebElement element = waitForElementByXpath(cnaWorkflowID.replace("$1", workflowID), true);
            scrollDownUntilElementIsDisplayed(element);
            element.click();
        } else {
            throw new RuntimeException(ERROR_MSG_TABLE_CONTAINER_NOT_LOADED);
        }
    }

    /**
     * This method will tap on Done button for Acknowledge Selected
     *
     * @return AcknowledgeScreen
     */
    public AcknowledgeScreen tapOnAcknowledgeSelectedScreenDoneButton() {
        waitForElementById("Done", true);
        selectMultipleWorkflowScreen.doneButton.click();
        System.out.println("Navigate to Acknowledge Screen");
        return new AcknowledgeScreen(iosDriver);
    }

    /**
     * This method will tap on Done button for Clarify Selected
     *
     * @return ClarificationOptionScreen
     */
    public ClarificationOptionScreen tapOnClarifySelectedScreenDoneButton() {
        waitForElementById("Done", true);
        selectMultipleWorkflowScreen.doneButton.click();
        System.out.println("Navigate to Clarification Option Screen");
        return new ClarificationOptionScreen(iosDriver);
    }

    /**
     * This method will tap on Done button for Respond Selected
     *
     * @return SubmitOptionScreen
     */
    public SubmitOptionScreen tapOnRespondSelectedScreenDoneButton() {
        waitForElementById("Done", true);
        selectMultipleWorkflowScreen.doneButton.click();
        System.out.println("Navigate to Respond Option Screen");
        return new SubmitOptionScreen(iosDriver);
    }

    /**
     * This method will tap on Done button for Clarify Selected For CE
     *
     * @return ClarificationScreen
     */
    public ClarificationScreen tapOnClarifySelectedScreenDoneButtonForCE() {
        waitForElementById("Done", true);
        selectMultipleWorkflowScreen.doneButton.click();
        System.out.println("Navigate to Clarification Screen");
        return new ClarificationScreen(iosDriver);
    }

    /**
     * This method will tap on Done button for Clarify Selected
     *
     * @return ReassignScreen
     */
    public ReassignScreen tapOnReassignSelectedScreenDoneButton() {
        waitForElementById("Done", true);
        selectMultipleWorkflowScreen.doneButton.click();
        System.out.println("Navigate to Reassign Screen");
        return new ReassignScreen(iosDriver);
    }

    /**
     * This method will tap on Done button for Clarify Selected
     *
     * @return SubmitScreen
     */
    public SubmitScreen tapOnSubmitSelectedScreenDoneButton() {
        waitForElementById("Done", true);
        selectMultipleWorkflowScreen.doneButton.click();
        System.out.println("Navigate to Submit Screen");
        return new SubmitScreen(iosDriver);
    }

    /**
     * This method will tap on Done button for Clarify Selected
     *
     * @return ExplicitDelegationScreen
     */
    public ExplicitDelegationScreen tapOnDelegateSelectedScreenDoneButton() {
        waitForElementById("Done", true);
        selectMultipleWorkflowScreen.doneButton.click();
        System.out.println("Navigate to Explicit Delegation Screen");
        return new ExplicitDelegationScreen(iosDriver);
    }

    /**
     * This method will tap on Done button for Bookmark Selected
     *
     * @return BookmarkScreen
     */
    public BookmarkScreen tapOnBookmarkSelectedScreenDoneButton() {
        waitForElementById("Done", true);
        selectMultipleWorkflowScreen.doneButton.click();
        System.out.println("Navigate to Bookmark Screen");
        return new BookmarkScreen(iosDriver);
    }

    class PageObjects {
        @FindBy(id = "Done")
        WebElement doneButton;
    }
}
