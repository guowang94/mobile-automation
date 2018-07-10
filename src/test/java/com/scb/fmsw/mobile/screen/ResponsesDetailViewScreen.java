package com.scb.fmsw.mobile.screen;

import com.scb.fmsw.mobile.WorkflowConstants;
import com.scb.fmsw.mobile.base.BaseScreen;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ResponsesDetailViewScreen extends BaseScreen implements WorkflowConstants {

    private PageObjects responseDetailViewScreen;
    private String tableContainer = "//XCUIElementTypeTable[@visible='true']";

    public ResponsesDetailViewScreen(IOSDriver<IOSElement> testDriver) {
        iosDriver = testDriver;
        responseDetailViewScreen = new PageObjects();
        PageFactory.initElements(iosDriver, responseDetailViewScreen);
    }

    /**
     * This method will check if container is loaded
     *
     * @return boolean
     */
    private boolean hasContainerLoaded() {
        return waitForElementByXpath(tableContainer).isDisplayed();
    }

    /**
     * This method verify Workflow Status
     *
     * @return boolean
     */
    public boolean verifyWorkflowStatus(String workflowStatus) {
        if (hasContainerLoaded()) {
            scrollDownUntilElementIsDisplayed(responseDetailViewScreen.workflowStatusCell);
            return workflowStatus.equalsIgnoreCase(responseDetailViewScreen.workflowStatusValue.getText());
        } else {
            throw new RuntimeException(ERROR_MSG_TABLE_CONTAINER_NOT_LOADED);
        }
    }

    /**
     * This method verify Workflow Event Status
     *
     * @return boolean
     */
    public boolean verifyWorkflowEventStatus(String workflowEventStatus) {
        if (hasContainerLoaded()) {
            scrollDownUntilElementIsDisplayed(responseDetailViewScreen.workflowEventStatusCell);
            return workflowEventStatus.equalsIgnoreCase(responseDetailViewScreen.workflowEventStatusValue.getText());
        } else {
            throw new RuntimeException(ERROR_MSG_TABLE_CONTAINER_NOT_LOADED);
        }
    }

    /**
     * This method will tap on Back Button
     *
     * @return InboxResponsesGridScreen
     */
    public InboxResponsesGridScreen tapOnBackButton() {
        responseDetailViewScreen.backButton.click();
        System.out.println("Navigate back to Inbox Responses Grid Screen");
        return new InboxResponsesGridScreen(iosDriver);
    }

    class PageObjects {
        @FindBy(xpath = "//XCUIElementTypeTable[@visible='true']//XCUIElementTypeStaticText[@name='Workflow Status']/following-sibling::XCUIElementTypeStaticText")
        WebElement workflowStatusValue;

        @FindBy(xpath = "//XCUIElementTypeTable[@visible='true']//XCUIElementTypeStaticText[@name='Workflow Status']/ancestor::XCUIElementTypeCell")
        WebElement workflowStatusCell;

        @FindBy(xpath = "//XCUIElementTypeTable[@visible='true']//XCUIElementTypeStaticText[@name='Workflow Event Status']/following-sibling::XCUIElementTypeStaticText")
        WebElement workflowEventStatusValue;

        @FindBy(xpath = "//XCUIElementTypeTable[@visible='true']//XCUIElementTypeStaticText[@name='Workflow Event Status']/ancestor::XCUIElementTypeCell")
        WebElement workflowEventStatusCell;

        @FindBy(id = "cross")
        WebElement backButton;
    }
}
