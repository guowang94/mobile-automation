package com.scb.fmsw.mobile.screen;

import com.scb.fmsw.mobile.base.BaseScreen;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class DelegationDefaultScreen extends BaseScreen {

    private PageObjects defaultDelegationScreen;

    public DelegationDefaultScreen(IOSDriver<IOSElement> testDriver) {
        iosDriver = testDriver;
        defaultDelegationScreen = new PageObjects();
        PageFactory.initElements(iosDriver, defaultDelegationScreen);
    }

    /**
     * This method will check if Table Container is loaded
     *
     * @return boolean
     */
    private boolean hasTableContainerLoaded() {
        return defaultDelegationScreen.tableContainer.isDisplayed();
    }

    /**
     * This method will select all the value in Default Delegation screen
     *
     * @param workflowTypeList
     * @param type
     * @param businessGroup
     * @param business
     * @param subBusiness
     * @param desk
     * @param countryList
     * @return DelegationPortfolioScreen
     */
    public DelegationPortfolioScreen fillInDefaultForm(List<String> workflowTypeList, String type, String businessGroup, String business, String subBusiness, String desk, List<String> countryList) {
        hasLoadingCompleted();
        if (hasTableContainerLoaded()) {
            selectWorkflowType(workflowTypeList);
            hasLoadingCompleted();
            selectType(type);
            selectBusinessGroup(businessGroup);
            selectBusiness(business);
            selectSubBusiness(subBusiness);
            selectDesk(desk);
            searchCountry(countryList);

            defaultDelegationScreen.searchButton.click();
            System.out.println("Navigate to Delegation Portfolio Screen");
        } else {
            throw new RuntimeException(ERROR_MSG_TABLE_CONTAINER_NOT_LOADED);
        }
        return new DelegationPortfolioScreen(iosDriver);
    }

    /**
     * This method will select all the value in Default Delegation screen for CE
     *
     * @param workflowTypeList
     * @param type
     * @return DelegationDefaultCreationScreen
     */
    public DelegationDefaultCreationScreen fillInDefaultCEForm(List<String> workflowTypeList, String type) {
        hasLoadingCompleted();
        if (hasTableContainerLoaded()) {
            selectWorkflowType(workflowTypeList);
            selectType(type);

            defaultDelegationScreen.plusButton.click();
            System.out.println("Navigate to Default Delegation Creation Screen");
        } else {
            throw new RuntimeException(ERROR_MSG_TABLE_CONTAINER_NOT_LOADED);
        }
        return new DelegationDefaultCreationScreen(iosDriver);
    }

    /**
     * This method will select Workflow Type
     *
     * @param workflowTypeList
     */
    private void selectWorkflowType(List<String> workflowTypeList) {
        defaultDelegationScreen.workflowTypeField.click();
        System.out.println("Navigate to Delegation Workflow Type Screen");
        new DelegationWorkflowTypeScreen(iosDriver).tapOnWorkflowType(workflowTypeList);
    }

    /**
     * This method will select value from picker wheel
     *
     * @param value
     */
    private void selectPickerValue(String value) {
        if (!value.equalsIgnoreCase(defaultDelegationScreen.picker.getText())) {
            defaultDelegationScreen.picker.sendKeys(value);
        }
        waitForElementByIdUntilClickable("Done", true);
        defaultDelegationScreen.doneButton.click();
    }

    /**
     * This method will select Type value
     *
     * @param type
     */
    public void selectType(String type) {
        defaultDelegationScreen.typeField.click();
        selectPickerValue(type);
    }

    /**
     * This method will select Business Group value
     *
     * @param businessGroup
     */
    public void selectBusinessGroup(String businessGroup) {
        defaultDelegationScreen.businessGroupField.click();
        selectPickerValue(businessGroup);
    }

    /**
     * This method will select Business value
     *
     * @param business
     */
    public void selectBusiness(String business) {
        defaultDelegationScreen.businessField.click();
        selectPickerValue(business);
    }

    /**
     * This method will select Sub Business value
     *
     * @param subBusiness
     */
    public void selectSubBusiness(String subBusiness) {
        defaultDelegationScreen.subBusinessField.click();
        selectPickerValue(subBusiness);
    }

    /**
     * This method will select Desk value
     *
     * @param desk
     */
    public void selectDesk(String desk) {
        defaultDelegationScreen.deskField.click();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            //do nothing
        }
        selectPickerValue(desk);
    }

    /**
     * This method will search and select country
     *
     * @param countryList
     */
    public void searchCountry(List<String> countryList) {
        defaultDelegationScreen.countrySearchField.click();
        System.out.println("Navigate to Delegation Country Search Screen");
        new DelegationCountrySearchScreen(iosDriver).searchCountry(countryList);
    }

    class PageObjects {
        @FindBy(xpath = "//XCUIElementTypeTable[@visible='true']")
        WebElement tableContainer;

        @FindBy(xpath = "//XCUIElementTypeTextField[@name='Choose Workflow Type']")
        WebElement workflowTypeField;

        @FindBy(id = "Choose TypeBtn")
        WebElement typeField;

        @FindBy(id = "Choose Business GroupBtn")
        WebElement businessGroupField;

        @FindBy(id = "Choose BusinessBtn")
        WebElement businessField;

        @FindBy(id = "Choose Sub BusinessBtn")
        WebElement subBusinessField;

        @FindBy(id = "Choose DeskBtn")
        WebElement deskField;

        @FindBy(xpath = "//XCUIElementTypeTextField[@name='Multiple selection allowed']")
        WebElement countrySearchField;

        @FindBy(id = "search icon sm")
        WebElement searchButton;

        @FindBy(id = "greenplus")
        WebElement plusButton;

        @FindBy(className = "XCUIElementTypePickerWheel")
        WebElement picker;

        @FindBy(id = "Done")
        WebElement doneButton;
    }
}
