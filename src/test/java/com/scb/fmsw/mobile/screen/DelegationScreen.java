package com.scb.fmsw.mobile.screen;

import com.scb.fmsw.mobile.base.BaseScreen;

import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;
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
     *
     * @return
     */
    public OthersDelegationsScreen navigateToOthersDelegation() {
        hasLoadingCompleted();
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
     * This method will delete the Delegation
     *
     * @param index
     */
    public void deleteDelegation(int index) {
        //scroll till element is visible then swipe left
        scrollDownUntilElementIsDisplayed(delegationScreen.delegateeIDList.get(index));
        //Logging purpose
//        System.out.println("Trying to swipe up from x:" + getElementLocationX(delegationScreen.delegateeIDList.get(index))
//                + " y:" + getElementLocationY(delegationScreen.delegateeIDList.get(index))
//                + ", to x:" + (getElementLocationX(delegationScreen.delegateeIDList.get(index)) - 100)
//                + " y:" + getElementLocationY(delegationScreen.delegateeIDList.get(index)));

        new TouchAction(iosDriver)
                .press(PointOption.point(getElementLocationX(delegationScreen.delegateeIDList.get(index)),
                        getElementLocationY(delegationScreen.delegateeIDList.get(index))))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2)))
                .moveTo(PointOption.point((getElementLocationX(delegationScreen.delegateeIDList.get(index)) - 100),
                        getElementLocationY(delegationScreen.delegateeIDList.get(index)))).release().perform();

        delegationScreen.deleteButton.click();
        if (delegationScreen.alertMessage.getText().equals(ALERT_MSG_DELETE_DELEGATION)) {
            delegationScreen.alertOKButton.click();

            if (delegationScreen.alertMessage.getText().equals(ALERT_MSG_DELETED_SUCCESSFULLY)) {
                screenshot(SCREENSHOT_MSG_SUCCESSFULLY_DELETED_DELEGATION);
                delegationScreen.alertOkButton.click();
            } else {
                screenshot(SCREENSHOT_MSG_FAILED_TO_DELETE_DELEGATION);
                throw new RuntimeException(SCREENSHOT_MSG_FAILED_TO_DELETE_DELEGATION);
            }
        }
    }

    /**
     * This method will return the Delegation index
     *
     * @param delegateeID
     * @param delegationType
     * @param workflowType
     * @param comment
     * @param status
     * @return int
     */
    public int getDelegationIndex(String delegateeID, String delegationType, String workflowType, String comment, String status) {
        DelegationDetailScreen delegationDetailScreen = new DelegationDetailScreen(iosDriver);

        if (hasTableContainerLoaded()) {
            if (delegationScreen.delegateeIDList.size() > 0) {
                for (int i = 0; i < delegationScreen.delegateeIDList.size(); i++) {

                    System.out.println("Current user id: " + delegationScreen.delegateeIDList.get(i).getText());

                    if (!delegationScreen.delegateeIDList.get(i).isDisplayed()) {
                        scrollDownUntilElementIsDisplayed(delegationScreen.delegateeIDList.get(i));
                    }

                    if (delegationScreen.delegateeIDList.get(i).getText().substring(0, 7).equals(delegateeID) &&
                            delegationScreen.delegationTypeList.get(i).getText().equals(delegationType) &&
                            delegationScreen.statusList.get(i).getText().equals(status)) {

                        delegationScreen.delegateeIDList.get(i).click();

                        if (delegationDetailScreen.verifyWorkflowType(workflowType)) {
                            if (delegationDetailScreen.verifyDelegatorComment(comment)) {
                                delegationDetailScreen.back();
                                return i;
                            }
                        } else {
                            delegationDetailScreen.back();
                        }
                    }
                }
            }
        } else {
            throw new RuntimeException(ERROR_MSG_TABLE_CONTAINER_NOT_LOADED);
        }
        throw new RuntimeException(ERROR_MSG_DELEGATION_NOT_MATCHED);
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
                            System.out.println("Verified Delegation Details");
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

        @FindBy(id = "Remove")
        WebElement deleteButton;

        @FindBy(xpath = "//XCUIElementTypeAlert//XCUIElementTypeStaticText[2]")
        WebElement alertMessage;

        @FindBy(id = "OK")
        WebElement alertOKButton;

        @FindBy(xpath = "//XCUIElementTypeAlert//XCUIElementTypeButton")
        WebElement alertOkButton;

        @FindBy(id = "OTHERS DELEGATIONS")
        WebElement othersDelegationsTab;
    }
}
