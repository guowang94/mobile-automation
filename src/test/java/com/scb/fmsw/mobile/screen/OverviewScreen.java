package com.scb.fmsw.mobile.screen;

import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

import com.scb.fmsw.mobile.base.BaseScreen;

import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

import java.time.Duration;

public class OverviewScreen extends BaseScreen {

    //xpath
    private String workflowCount = "//XCUIElementTypeStaticText[@name='$1']/ancestor::XCUIElementTypeCell/descendant::XCUIElementTypeStaticText[@name='$2']";
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
        return waitForElementByXpath(container, true).isDisplayed();
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
            String tempWorkflow = WORKFLOW_IPV.equalsIgnoreCase(workflowType) ? WORKFLOW_PNL : workflowType;
            WebElement workflowTypeElement = waitForElementByXpath(this.workflowType.replace("$1", tempWorkflow), true);
            TouchAction action = new TouchAction(iosDriver);
            while (!workflowTypeElement.isDisplayed()) {
                Dimension size = iosDriver.manage().window().getSize();
                int startY = (int) (size.height * 0.9);
                int endY = (int) (size.height * 0.7);
                int startX = (int) (size.width / 2.2);
                //Logging purpose
//                System.out.println("Trying to swipe from x:" + startX + " y:" + startY + ", to x:" + startX + " y:" + endY);
                action.press(PointOption.point(startX, startY)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2)))
                        .moveTo(PointOption.point(startX, endY)).release().perform();
            }
            waitForElementByXpath(workflowCountXpath(workflowType, workflowStatus), true).click();
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
