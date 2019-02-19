package com.scb.fmsw.mobile.screen;

import com.scb.fmsw.mobile.base.BaseScreen;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DelegationOptionScreen extends BaseScreen {

    private PageObjects delegationOptionScreen;

    public DelegationOptionScreen(IOSDriver<IOSElement> testDriver) {
        iosDriver = testDriver;
        delegationOptionScreen = new PageObjects();
        PageFactory.initElements(iosDriver, delegationOptionScreen);
    }

    /**
     * This method will check if Table Container is loaded
     *
     * @return boolean
     */
    private boolean hasTableContainerLoaded() {
        return delegationOptionScreen.tableContainer.isDisplayed();
    }

    /**
     * This method will tap on Auto Out of Office Delegation
     *
     * @return DelegationAutoOutOfOfficeScreen
     */
    public DelegationAutoOutOfOfficeScreen tapOnAutoOutOfOfficeDelegation() {
        if (hasTableContainerLoaded()) {
            delegationOptionScreen.autoOutOfOfficeButton.click();
            System.out.println("Navigate to Auto Out of Office Delegation Screen");
        } else {
            throw new RuntimeException(ERROR_MSG_TABLE_CONTAINER_NOT_LOADED);
        }
        return new DelegationAutoOutOfOfficeScreen(iosDriver);
    }

    /**
     * This method will tap on Period Delegation
     *
     * @return DelegationPeriodScreen
     */
    public DelegationPeriodScreen tapOnPeriodDelegation() {
        if (hasTableContainerLoaded()) {
            delegationOptionScreen.periodButton.click();
            System.out.println("Navigate to Period Delegation Screen");
        } else {
            throw new RuntimeException(ERROR_MSG_TABLE_CONTAINER_NOT_LOADED);
        }
        return new DelegationPeriodScreen(iosDriver);
    }

    class PageObjects {
        @FindBy(xpath = "//XCUIElementTypeStaticText[@name='Auto Out of Office']")
        WebElement autoOutOfOfficeButton;

        @FindBy(xpath = "//XCUIElementTypeStaticText[@name='Period']")
        WebElement periodButton;

        @FindBy(xpath = "//XCUIElementTypeTable[@visible='true']")
        WebElement tableContainer;
    }
}
