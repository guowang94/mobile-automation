package com.scb.fmsw.mobile.screen.delegation;

import com.scb.fmsw.mobile.base.BaseScreen;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class DelegationCountrySearchScreen extends BaseScreen {

    private PageObjects delegationCountrySearchScreen;

    public DelegationCountrySearchScreen(IOSDriver<IOSElement> testDriver) {
        iosDriver = testDriver;
        delegationCountrySearchScreen = new PageObjects();
        PageFactory.initElements(iosDriver, delegationCountrySearchScreen);
    }

    /**
     * This method will check if Table Container is loaded
     *
     * @return boolean
     */
    private boolean hasTableContainerLoaded() {
        return delegationCountrySearchScreen.tableContainer.isDisplayed();
    }

    /**
     * This method will search and select country
     *
     * @param countryList
     */
    public void searchCountry(List<String> countryList) {
        if (hasTableContainerLoaded()) {
            if (countryList.size() > 0) {
                for (String country : countryList) {
                    if (!delegationCountrySearchScreen.searchField.getText().isEmpty()) {
                        delegationCountrySearchScreen.searchField.clear();
                    }
                    country = country.length() < 6 ? country : country.substring(0, 5);
                    delegationCountrySearchScreen.searchField.sendKeys(country);
                    delegationCountrySearchScreen.searchResult.click();
                }

                delegationCountrySearchScreen.saveButton.click();
            } else {
                throw new RuntimeException(ERROR_MSG_NO_COUNTRY);
            }
        } else {
            throw new RuntimeException(ERROR_MSG_TABLE_CONTAINER_NOT_LOADED);
        }
    }

    private class PageObjects {
        @FindBy(id = "Save")
        WebElement saveButton;

        @FindBy(className = "XCUIElementTypeTextField")
        WebElement searchField;

        @FindBy(xpath = "//XCUIElementTypeTable[@visible='true']/XCUIElementTypeCell[1]")
        WebElement searchResult;

        @FindBy(xpath = "//XCUIElementTypeTable[@visible='true']")
        WebElement tableContainer;
    }
}
