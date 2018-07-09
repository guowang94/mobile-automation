package com.scb.fmsw.mobile.screen;

import com.scb.fmsw.mobile.WorkflowConstants;
import com.scb.fmsw.mobile.base.BaseScreen;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class InboxDetailsOptionScreen extends BaseScreen implements WorkflowConstants {

    private String tableContainer = "//XCUIElementTypeTable[@visible='true']";
    private PageObjects inboxDetailsOptionScreen;

    public InboxDetailsOptionScreen(IOSDriver<IOSElement> testDriver) {
        iosDriver = testDriver;
        inboxDetailsOptionScreen = new PageObjects();
        PageFactory.initElements(iosDriver, inboxDetailsOptionScreen);
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
     * This method will navigate to Inbox Response Screen
     *
     * @return InboxResponsesGridScreen
     */
    public InboxResponsesGridScreen navigateToInboxResponseScreen() {
        if (hasTableContainerLoaded()) {
            inboxDetailsOptionScreen.responsesCell.click();
            System.out.println("Navigate to Inbox Response Grid Screen");
        } else {
            throw new RuntimeException(ERROR_MSG_TABLE_CONTAINER_NOT_LOADED);
        }
        return new InboxResponsesGridScreen(iosDriver);
    }

    /**
     * This method will tap on Back button and navigate back to Inbox Detail View Screen
     *
     * @return InboxDetailViewScreen
     */
    public InboxDetailViewScreen tapOnBackButton() {
        if (hasTableContainerLoaded()) {
            inboxDetailsOptionScreen.backButton.click();
            System.out.println("Navigate back to Inbox Detail View Screen");
        } else {
            throw new RuntimeException(ERROR_MSG_TABLE_CONTAINER_NOT_LOADED);
        }
        return new InboxDetailViewScreen(iosDriver);
    }

    class PageObjects {
        @FindBy(id = "BackArrowWhite")
        WebElement backButton;

        @FindBy(id = "Responses")
        WebElement responsesCell;
    }
}
