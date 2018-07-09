package com.scb.fmsw.mobile.screen;

import com.scb.fmsw.mobile.WorkflowConstants;
import com.scb.fmsw.mobile.base.BaseScreen;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.WebElement;

public class ClarificationOptionScreen extends BaseScreen implements WorkflowConstants {

    private String tableContainer = "//XCUIElementTypeTable[@visible='true']";

    public ClarificationOptionScreen(IOSDriver<IOSElement> testDriver) {
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

    /**
     * This method will navigate to Clarification Screen based on the option
     *
     * @param option - eg. Product Control, Middle Office, Send to, Valuation Control User, MRO/MTCR/GT
     * @return ClarificationScreen
     */
    public ClarificationScreen selectClarificationOption(String option) {
        hasLoadingCompleted();
        if (hasTableContainerLoaded()) {
            waitForElementById(option).click();
            System.out.println("Navigate to Clarification Screen");
        } else {
            throw new RuntimeException(ERROR_MSG_TABLE_CONTAINER_NOT_LOADED);
        }
        return new ClarificationScreen(iosDriver);
    }
}
