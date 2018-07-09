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
     * This method will tap on Default Delegation
     *
     * @return DelegationDefaultScreen
     */
    public DelegationDefaultScreen tapOnDefaultDelegation() {
        if (hasTableContainerLoaded()) {
            delegationOptionScreen.defaultButton.click();
            System.out.println("Navigate to Default Delegation Screen");
        } else {
            throw new RuntimeException(ERROR_MSG_TABLE_CONTAINER_NOT_LOADED);
        }
        return new DelegationDefaultScreen(iosDriver);
    }

    /**
     * This method will tap on Users Delegation
     *
     * @return DelegationUsersScreen
     */
    public DelegationUsersScreen tapOnUsersDelegation() {
        if (hasTableContainerLoaded()) {
            delegationOptionScreen.usersButton.click();
            System.out.println("Navigate to Users Delegation Screen");
        } else {
            throw new RuntimeException(ERROR_MSG_TABLE_CONTAINER_NOT_LOADED);
        }
        return new DelegationUsersScreen(iosDriver);
    }

    class PageObjects {
        @FindBy(id = "Default")
        WebElement defaultButton;

        @FindBy(id = "Users")
        WebElement usersButton;

        @FindBy(id = "Hierarchy")
        WebElement hierarchyButton;

        @FindBy(xpath = "//XCUIElementTypeTable[@visible='true']")
        WebElement tableContainer;
    }
}
