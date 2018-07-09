package com.scb.fmsw.mobile.screen;

import com.scb.fmsw.mobile.WorkflowConstants;
import com.scb.fmsw.mobile.base.BaseScreen;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

public class SubmitOptionScreen extends BaseScreen implements WorkflowConstants {

    private String tableContainer = "//XCUIElementTypeTable[@visible='true']";

    public SubmitOptionScreen(IOSDriver<IOSElement> testDriver) {
        iosDriver = testDriver;
    }

    /**
     * This method will check if Table Container is loaded
     *
     * @return boolean
     */
    private boolean hasTableContainerLoaded() {
        return waitForElementByXpath(tableContainer).isDisplayed();
    }

    //TODO this is for VE workflow
    /**
     * This method will navigate to Submit Screen based on the option
     *
     * @param option
     * @return SubmitScreen
     */
    public SubmitScreen selectSubmitOption(String option) {
        if (hasTableContainerLoaded()) {
            waitForElementById(option).click();
            System.out.println("Navigate to Submit Screen");
        } else {
            throw new RuntimeException(ERROR_MSG_TABLE_CONTAINER_NOT_LOADED);
        }
        return new SubmitScreen(iosDriver);
    }
}
