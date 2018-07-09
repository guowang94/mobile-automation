package com.scb.fmsw.mobile.screen;

import com.scb.fmsw.mobile.base.BaseScreen;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class DelegationScreen extends BaseScreen {

    private PageObjects delegationScreen;

    public DelegationScreen(IOSDriver<IOSElement> testDriver) {
        iosDriver = testDriver;
        delegationScreen = new PageObjects();
        PageFactory.initElements(iosDriver, delegationScreen);
    }

    /**
     * This method will check if Table Container is loaded
     *
     * @return boolean
     */
    private boolean hasTableContainerLoaded() {
        return delegationScreen.tableContainer.isDisplayed();
    }

    /**
     * This method will navigate to Others Delegations Screen
     * @return
     */
    public OthersDelegationsScreen navigateToOthersDelegation() {
        if (hasTableContainerLoaded()) {
            delegationScreen.othersDelegationsTab.click();
            System.out.println("Navigate to Others Delegations Screen");
        } else {
            throw new RuntimeException(ERROR_MSG_TABLE_CONTAINER_NOT_LOADED);
        }
        return new OthersDelegationsScreen(iosDriver);
    }

    /**
     * This method will tap on Create Delegation Button
     *
     * @return DelegationOptionScreen
     */
    public DelegationOptionScreen tapOnCreateDelegation() {
        hasLoadingCompleted();
        if (hasTableContainerLoaded()) {
            delegationScreen.createDelegationButton.click();
            System.out.println("Navigate to Delegation Option Screen");
        } else {
            throw new RuntimeException(ERROR_MSG_TABLE_CONTAINER_NOT_LOADED);
        }
        return new DelegationOptionScreen(iosDriver);
    }

    /**
     * This method will verify the Delegation by checking the comments
     *
     * @param delegateeID
     * @param delegationType
     * @param workflowType
     * @param comment
     * @return boolean
     */
    public boolean verifyDelegation(String delegateeID, String delegationType, String workflowType, String comment) {
        DelegationDetailScreen delegationDetailScreen = new DelegationDetailScreen(iosDriver);

        if (delegationScreen.delegateeIDList.size() > 0) {
            for (int i = 0; i < delegationScreen.delegateeIDList.size(); i++) {

                System.out.println("Current user id: " + delegationScreen.delegateeIDList.get(i).getText());

                if (!delegationScreen.delegateeIDList.get(i).isDisplayed()) {
                    scrollDownUntilElementIsDisplayed(delegationScreen.delegateeIDList.get(i));
                }

                if (delegationScreen.delegateeIDList.get(i).getText().substring(0, 7).equals(delegateeID) &&
                        delegationScreen.delegationTypeList.get(i).getText().equals(delegationType) &&
                        delegationScreen.statusList.get(i).getText().equals(DELEGATION_STATUS_PENDING)) {

                    delegationScreen.delegateeIDList.get(i).click();

                    if (delegationDetailScreen.verifyWorkflowType(workflowType)) {
                        if (delegationDetailScreen.verifyDelegatorComment(comment)) {
                            delegationDetailScreen.back();
                            return true;
                        }
                    } else {
                        delegationDetailScreen.back();
                    }
                }
            }
        }
        return false;
    }

    class PageObjects {
        @FindBy(id = "ic edit colored")
        WebElement createDelegationButton;

        @FindBy(xpath = "//XCUIElementTypeTable[@visible='true']")
        WebElement tableContainer;

        @CacheLookup
        @FindAll({
                @FindBy(id = "EMPLID")
        })
        List<WebElement> delegateeIDList;

        @CacheLookup
        @FindAll({
                @FindBy(id = "DelegationType")
        })
        List<WebElement> delegationTypeList;

        @CacheLookup
        @FindAll({
                @FindBy(id = "status")
        })
        List<WebElement> statusList;

        @FindBy(id = "OTHERS DELEGATIONS")
        WebElement othersDelegationsTab;
    }
}
