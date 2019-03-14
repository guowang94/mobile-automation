package com.scb.fmsw.mobile.screen;

import com.scb.fmsw.mobile.WorkflowConstants;
import com.scb.fmsw.mobile.base.BaseScreen;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class InboxResponsesGridScreen extends BaseScreen implements WorkflowConstants {

    private String tableContainer = "//XCUIElementTypeTable[@visible='true']";
    private PageObjects inboxResponseScreen;

    public InboxResponsesGridScreen(IOSDriver<IOSElement> testDriver) {
        iosDriver = testDriver;
        inboxResponseScreen = new PageObjects();
        PageFactory.initElements(iosDriver, inboxResponseScreen);
    }

    /**
     * This method will check if Table Container is loaded
     *
     * @return boolean
     */
    private boolean hasTableContainerLoaded() {
        return waitForElementByXpath(tableContainer, true).isDisplayed();
    }

    /**
     * This method will return the result from verifying the workflow status
     *
     * @param workflowStatus
     * @param workflowType
     * @return boolean
     */
    public boolean verifyWorkflowStatus(String workflowStatus, String workflowType) {
        boolean result;

        if (hasTableContainerLoaded()) {
            scrollDownUntilElementIsDisplayed(inboxResponseScreen.tableCellsList.get(inboxResponseScreen.tableCellsList.size() - 1)).click();
            System.out.println("Navigate to Responses Detail View");

            ResponsesDetailViewScreen responsesDetailViewScreen = new ResponsesDetailViewScreen(iosDriver);

            if (WORKFLOW_GMR.equals(workflowType)) {
                result =  responsesDetailViewScreen.verifyWorkflowEventStatus(workflowStatus);
            } else {
                result =  responsesDetailViewScreen.verifyWorkflowStatus(workflowStatus);
            }

            if (result) {
                System.out.println("Verified Workflow Status");
            } else {
                System.out.println("Failed to verified Workflow Status");
            }

            responsesDetailViewScreen.tapOnBackButton();
            return result;
        } else {
            throw new RuntimeException(ERROR_MSG_TABLE_CONTAINER_NOT_LOADED);
        }
    }

    /**
     * This method will tap on Back Button
     *
     * @return InboxDetailsOptionScreen
     */
    public InboxDetailsOptionScreen tapOnBackButton() {
        inboxResponseScreen.backButton.click();
        System.out.println("Navigate back to Inbox Details Option Screen");
        return new InboxDetailsOptionScreen(iosDriver);
    }

    private class PageObjects {
        @FindBy(id = "backButton")
        WebElement backButton;

        @CacheLookup
        @FindAll({
                @FindBy(xpath = "//XCUIElementTypeTable[@visible='true']/XCUIElementTypeCell")
        })
        List<WebElement> tableCellsList;
    }
}
