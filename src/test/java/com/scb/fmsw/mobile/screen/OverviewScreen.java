package com.scb.fmsw.mobile.screen;

import org.openqa.selenium.WebElement;

import com.scb.fmsw.mobile.base.BaseScreen;

import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

public class OverviewScreen extends BaseScreen {

    //xpath
    private String workflowCount = "//XCUIElementTypeStaticText[@name='$1'][@visible='true']/ancestor::XCUIElementTypeCell/descendant::XCUIElementTypeStaticText[@name='$2']";
    private String workflowType = "//XCUIElementTypeTable[@visible='true']//XCUIElementTypeStaticText[@value='$1']/ancestor::XCUIElementTypeCell";
    private String container = "//XCUIElementTypeTable[@visible='true']";

    public OverviewScreen(IOSDriver<IOSElement> testDriver) {
        iosDriver = testDriver;
    }

    /**
     * This method will check if Tab Container has loaded
     *
     * @return boolean
     */
    private boolean hasTabContainerLoaded() {
        return waitForElementByXpath(container).isDisplayed();
    }

    /**
     * This method will tap on the workflow count based on the workflow type and workflow status
     *
     * @param workflowType
     * @param workflowStatus
     * @return InboxScreen
     */
    public InboxScreen tapOnWorkflowCount(String workflowType, String workflowStatus) {
        hasLoadingCompleted();
        if (hasTabContainerLoaded()) {
            WebElement workflowTypeElement = waitForElementByXpath(this.workflowType.replace("$1", workflowType));
            TouchAction action = new TouchAction(iosDriver);
            while (!workflowTypeElement.isDisplayed()) {
                action.press(180, 620).moveTo(0, -180).release().perform();
            }
            waitForElementByXpath(workflowCountXpath(workflowType, workflowStatus)).click();
            System.out.println("Navigate to " + workflowType + " inbox");
        } else {
            throw new RuntimeException(ERROR_MSG_CONTAINER_NOT_LOADED);
        }
        return new InboxScreen(iosDriver);
    }

    /**
     * This method will return the xpath of workflow count
     *
     * @param workflowStatus
     * @return String
     */
    private String workflowCountXpath(String workflowType, String workflowStatus) {
        if ("overdue".equals(workflowStatus)) {
            return workflowCount.replace("$1", workflowType).replace("$2", "red");
        } else if ("due".equals(workflowStatus)) {
            return workflowCount.replace("$1", workflowType).replace("$2", "amber");
        } else if ("open".equals(workflowStatus)) {
            return workflowCount.replace("$1", workflowType).replace("$2", "green");
        }
        return null;
    }
}
