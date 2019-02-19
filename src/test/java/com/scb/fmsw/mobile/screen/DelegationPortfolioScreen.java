package com.scb.fmsw.mobile.screen;

import com.scb.fmsw.mobile.base.BaseScreen;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DelegationPortfolioScreen extends BaseScreen {

    private PageObjects delegationPortfolioScreen;
    private String portfolioCell = "//XCUIElementTypeTable[@visible='true']/XCUIElementTypeCell[$1]";
    private String navigationBarTitleXpath = "//XCUIElementTypeNavigationBar//XCUIElementTypeOther";

    public DelegationPortfolioScreen(IOSDriver<IOSElement> testDriver) {
        iosDriver = testDriver;
        delegationPortfolioScreen = new PageObjects();
        PageFactory.initElements(iosDriver, delegationPortfolioScreen);
    }

    /**
     * This method will check if Table Container is loaded
     *
     * @return boolean
     */
    private boolean hasTableContainerLoaded() {
        return delegationPortfolioScreen.tableContainer.isDisplayed();
    }

    /**
     * This method will tap on Select button
     * @param isDeskOrCountry if false, select portfolio based on count
     * @param count
     * @return
     */
    public DelegationDefaultCreationScreen selectPortfolio(boolean isDeskOrCountry, int count) {
        hasLoadingCompleted();
        if (hasTableContainerLoaded()) {
            if (!waitForElementByXpath(navigationBarTitleXpath, false).getText()
                    .equalsIgnoreCase("Create Delegation")) {

                if (!isDeskOrCountry) {
                    for (int i = 1; i < count + 1; i++) {
                        WebElement element = waitForElementByXpath(portfolioCell.replace("$1", String.valueOf(i)), true);
                        scrollDownUntilElementIsDisplayed(element);
                        element.click();
                    }
                }
                delegationPortfolioScreen.selectButton.click();
                System.out.println("Navigate to Auto Out of Office Delegation Creation Screen");

            }
        } else {
            throw new RuntimeException(ERROR_MSG_TABLE_CONTAINER_NOT_LOADED);
        }
        return new DelegationDefaultCreationScreen(iosDriver);
    }

    class PageObjects {
        @FindBy(xpath = "//XCUIElementTypeTable[@visible='true']")
        WebElement tableContainer;

        @FindBy(id = "Select")
        WebElement selectButton;
    }
}
