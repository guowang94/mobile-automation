package com.scb.fmsw.mobile.screen.delegation;

import com.scb.fmsw.mobile.base.BaseScreen;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class DelegationAutoOutOfOfficeScreen extends BaseScreen {

    private PageObjects defaultDelegationScreen;

    public DelegationAutoOutOfOfficeScreen(IOSDriver<IOSElement> testDriver) {
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
     * This method will select all the value in Auto Out of Office Delegation screen
     *
     * @param workflowTypeList
     * @param type
     * @param businessGroup
     * @param business
     * @param subBusiness
     * @param desk
     * @param countryList
     * @param myEntitlement
     * @return DelegationPortfolioScreen
     */
    public DelegationPortfolioScreen fillInDefaultForm(List<String> workflowTypeList, String type, String businessGroup,
                                                       String business, String subBusiness, String desk,
                                                       List<String> countryList, String myEntitlement) {
        hasLoadingCompleted();
        if (hasTableContainerLoaded()) {
            selectWorkflowType(workflowTypeList);
            hasLoadingCompleted();

            boolean result = this.verifyDefaultSelectedValue(workflowTypeList.get(0));

            if (result) {
                selectType(type);
                selectBusinessGroup(businessGroup);
                selectBusiness(business);
                selectSubBusiness(subBusiness);
                selectDesk(desk);
                searchCountry(countryList);

                if (workflowTypeList.contains(WORKFLOW_CNA) || workflowTypeList.contains(WORKFLOW_TRR)) {
                    selectMyEntitlement(myEntitlement);
                }

                scrollToElement(defaultDelegationScreen.searchButton).click();
                System.out.println("Navigate to Delegation Portfolio Screen");
            } else {
                throw new RuntimeException(ERROR_MSG_FAILED_TO_VERIFY_DEFAULT_SELECTION_FOR_DELEGATION);
            }
        } else {
            throw new RuntimeException(ERROR_MSG_TABLE_CONTAINER_NOT_LOADED);
        }
        return new DelegationPortfolioScreen(iosDriver);
    }

    /**
     * This method will select all the value in Auto Out of Office Delegation screen for CE, VE, AFO, Missed Trade,
     * Best Ex Alerts and Benchmark Rate
     *
     * @param workflowTypeList
     * @param type
     * @return DelegationDefaultCreationScreen
     */
    public DelegationDefaultCreationScreen fillInDefaultForm(List<String> workflowTypeList, String type) {
        hasLoadingCompleted();
        if (hasTableContainerLoaded()) {
            selectWorkflowType(workflowTypeList);
            hasLoadingCompleted();

            boolean result = this.verifyDefaultSelectedValue(workflowTypeList.get(0));

            if (result) {
                selectType(type);

                defaultDelegationScreen.plusButton.click();
                System.out.println("Navigate to Auto Out of Office Delegation Creation Screen");
            } else {
                throw new RuntimeException(ERROR_MSG_FAILED_TO_VERIFY_DEFAULT_SELECTION_FOR_DELEGATION);
            }
        } else {
            throw new RuntimeException(ERROR_MSG_TABLE_CONTAINER_NOT_LOADED);
        }
        return new DelegationDefaultCreationScreen(iosDriver);
    }

    /**
     * This method will click on the 'Search' button and navigate to Delegation Portfolio Screen
     *
     * @return DelegationPortfolioScreen
     */
    public DelegationDefaultCreationScreen navigateToDelegationPortfolioScreen() {
        hasLoadingCompleted();
        if (hasTableContainerLoaded()) {
            scrollToElement(defaultDelegationScreen.searchButton).click();
            System.out.println("Navigate to Delegation Portfolio Screen");
        } else {
            throw new RuntimeException(ERROR_MSG_TABLE_CONTAINER_NOT_LOADED);
        }
        return new DelegationDefaultCreationScreen(iosDriver);
    }

    /**
     * This method will click on the '+' button and navigate to Delegation Default Creation Screen
     *
     * @return DelegationDefaultCreationScreen
     */
    public DelegationDefaultCreationScreen navigateToDelegationDefaultCreationScreen() {
        hasLoadingCompleted();
        if (hasTableContainerLoaded()) {
            defaultDelegationScreen.plusButton.click();
            System.out.println("Navigate to Auto Out of Office Delegation Creation Screen");
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
        selectPickerValue(desk);
    }

    /**
     * This method will select Sub Business value
     *
     * @param myEntitlement
     */
    public void selectMyEntitlement(String myEntitlement) {
        scrollToElement(defaultDelegationScreen.myEntitlementField).click();
        selectPickerValue(myEntitlement);
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

    /**
     * This method will verify Default Value of Business hierarchy and Type
     *
     * @param workflowType
     * @return boolean
     */
    public boolean verifyDefaultSelectedValue(String workflowType) {
        boolean result = false;
        String verifiedMsg = "Failed to verify default selected value";

        if (WORKFLOW_CNA.equalsIgnoreCase(workflowType) || WORKFLOW_TRR.equalsIgnoreCase(workflowType) ||
                WORKFLOW_GMR.equalsIgnoreCase(workflowType) || WORKFLOW_GT.equalsIgnoreCase(workflowType) ||
                WORKFLOW_IPV.equalsIgnoreCase(workflowType) || WORKFLOW_FVA.equalsIgnoreCase(workflowType) ||
                WORKFLOW_PNL.equalsIgnoreCase(workflowType)) {

            result = DELEGATION_TYPE_DESK_OR_COUNTRY.equalsIgnoreCase(defaultDelegationScreen.typeField.getText());

            if (result) {
                verifiedMsg = "Verified Type";

                result = DELEGATION_OPTION_ALL.equalsIgnoreCase(defaultDelegationScreen.businessField.getText());

                if (result) {
                    verifiedMsg = verifiedMsg + ", Business";
                }
            }

            if (result) {
                result = DELEGATION_OPTION_ALL.equalsIgnoreCase(defaultDelegationScreen.subBusinessField.getText());

                if (result) {
                    verifiedMsg = verifiedMsg + ", Sub Business";
                }
            }

            if (result) {
                result = DELEGATION_OPTION_ALL.equalsIgnoreCase(defaultDelegationScreen.deskField.getText());

                if (result) {
                    verifiedMsg = verifiedMsg + ", Desk";
                }
            }

            if (result) {
                result = DELEGATION_OPTION_ALL.equalsIgnoreCase(defaultDelegationScreen.countrySearchField.getText());

                if (result) {
                    verifiedMsg = verifiedMsg + ", Country";
                }
            }

            if (result && WORKFLOW_CNA.equalsIgnoreCase(workflowType) || WORKFLOW_TRR.equalsIgnoreCase(workflowType)) {
                result = DELEGATION_OPTION_Y.equalsIgnoreCase(defaultDelegationScreen.myEntitlementField.getText());

                if (result) {
                    verifiedMsg = verifiedMsg + ", My Entitlement";
                }
            }
        } else if (WORKFLOW_CE.equalsIgnoreCase(workflowType) || WORKFLOW_VE.equalsIgnoreCase(workflowType) ||
                WORKFLOW_AFO.equalsIgnoreCase(workflowType) || WORKFLOW_MT.equalsIgnoreCase(workflowType) ||
                WORKFLOW_BEX.equalsIgnoreCase(workflowType) || WORKFLOW_BRS.equalsIgnoreCase(workflowType)) {
            result = DELEGATION_TYPE_USER.equalsIgnoreCase(defaultDelegationScreen.typeField.getText());

            if (result) {
                verifiedMsg = "Verified Type";
            }
        }

        System.out.println(verifiedMsg);
        return result;
    }

    private class PageObjects {
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

        @FindBy(id = "Choose My EntitlementsBtn")
        WebElement myEntitlementField;

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
