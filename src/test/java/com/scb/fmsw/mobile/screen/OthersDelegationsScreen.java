package com.scb.fmsw.mobile.screen;

import com.scb.fmsw.mobile.base.BaseScreen;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;
import java.util.List;

public class OthersDelegationsScreen extends BaseScreen {

    private PageObjects othersDelegationsScreen;

    public OthersDelegationsScreen(IOSDriver<IOSElement> testDriver) {
        iosDriver = testDriver;
        othersDelegationsScreen = new PageObjects();
        PageFactory.initElements(iosDriver, othersDelegationsScreen);
    }

    /**
     * This method will check if Table Container is loaded
     *
     * @return boolean
     */
    private boolean hasTableContainerLoaded() {
        return othersDelegationsScreen.tableContainer.isDisplayed();
    }

    /**
     * This method will return the Delegation index
     *
     * @param delegatorID
     * @param delegationType
     * @param workflowType
     * @param comment
     * @param status
     * @return int
     */
    public int getDelegationIndex(String delegatorID, String delegationType, String workflowType, String comment, String status) {
        DelegationDetailScreen delegationDetailScreen = new DelegationDetailScreen(iosDriver);

        if (hasTableContainerLoaded()) {
            if (othersDelegationsScreen.delegatorIDList.size() > 0) {
                for (int i = 0; i < othersDelegationsScreen.delegatorIDList.size(); i++) {

                    System.out.println("Current user id: " + othersDelegationsScreen.delegatorIDList.get(i).getText());

                    if (!othersDelegationsScreen.delegatorIDList.get(i).isDisplayed()) {
                        scrollDownUntilElementIsDisplayed(othersDelegationsScreen.delegatorIDList.get(i));
                    }

                    if (othersDelegationsScreen.delegatorIDList.get(i).getText().substring(0, 7).equals(delegatorID) &&
                            othersDelegationsScreen.delegationTypeList.get(i).getText().equals(delegationType) &&
                            othersDelegationsScreen.statusList.get(i).getText().equals(status)) {

                        othersDelegationsScreen.delegatorIDList.get(i).click();

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
     * This method will accept the Delegation
     *
     * @param index
     */
    public void acceptDelegation(int index) {
        //scroll till element is visible then swipe left
        scrollDownUntilElementIsDisplayed(othersDelegationsScreen.delegatorIDList.get(index));
        //Logging purpose
//        System.out.println("Trying to swipe from x:" + getElementLocationX(othersDelegationsScreen.delegatorIDList.get(index))
//                + " y:" + getElementLocationY(othersDelegationsScreen.delegatorIDList.get(index))
//                + ", to x:" + (getElementLocationX(othersDelegationsScreen.delegatorIDList.get(index)) - 100)
//                + " y:" + getElementLocationY(othersDelegationsScreen.delegatorIDList.get(index)));

        new TouchAction(iosDriver)
                .press(PointOption.point(getElementLocationX(othersDelegationsScreen.delegatorIDList.get(index)),
                        getElementLocationY(othersDelegationsScreen.delegatorIDList.get(index))))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2)))
                .moveTo(PointOption.point((getElementLocationX(othersDelegationsScreen.delegatorIDList.get(index)) - 100),
                        getElementLocationY(othersDelegationsScreen.delegatorIDList.get(index)))).release().perform();

        othersDelegationsScreen.acceptButton.click();
        if (othersDelegationsScreen.alertMessage.getText().equals(ALERT_MSG_ACCEPT_DELEGATION)) {
            othersDelegationsScreen.alertOKButton.click();

            if (othersDelegationsScreen.alertMessage.getText().equals(ALERT_MSG_ACCEPTED_SUCCESSFULLY)) {
                screenshot(SCREENSHOT_MSG_SUCCESSFULLY_ACCEPTED_DELEGATION);
                othersDelegationsScreen.alertOkButton.click();
            } else if (othersDelegationsScreen.alertMessage.getText().equals(ALERT_MSG_CAN_ONLY_ACCEPT_PENDING_DELEGATION)) {
                screenshot(SCREENSHOT_MSG_FAILED_TO_ACCEPT_DELEGATION);
                throw new RuntimeException(SCREENSHOT_MSG_FAILED_TO_ACCEPT_DELEGATION);
            }
        }
    }

    /**
     * This method will reject the Delegation
     *
     * @param index
     */
    public void rejectDelegation(int index) {
        //scroll till element is visible then swipe left
        scrollDownUntilElementIsDisplayed(othersDelegationsScreen.delegatorIDList.get(index));
        //Logging purpose
//        System.out.println("Trying to swipe from x:" + getElementLocationX(othersDelegationsScreen.delegatorIDList.get(index))
//                + " y:" + getElementLocationY(othersDelegationsScreen.delegatorIDList.get(index))
//                + ", to x:" + (getElementLocationX(othersDelegationsScreen.delegatorIDList.get(index)) - 100)
//                + " y:" + getElementLocationY(othersDelegationsScreen.delegatorIDList.get(index)));

        new TouchAction(iosDriver)
                .press(PointOption.point(getElementLocationX(othersDelegationsScreen.delegatorIDList.get(index)),
                        getElementLocationY(othersDelegationsScreen.delegatorIDList.get(index))))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2)))
                .moveTo(PointOption.point((getElementLocationX(othersDelegationsScreen.delegatorIDList.get(index)) - 100),
                        getElementLocationY(othersDelegationsScreen.delegatorIDList.get(index)))).release().perform();

        othersDelegationsScreen.rejectButton.click();
        if (othersDelegationsScreen.alertMessage.getText().equals(ALERT_MSG_REJECT_DELEGATION)) {
            othersDelegationsScreen.alertOKButton.click();

            if (othersDelegationsScreen.alertMessage.getText().equals(ALERT_MSG_REJECTED_SUCCESSFULLY)) {
                screenshot(SCREENSHOT_MSG_SUCCESSFULLY_REJECTED_DELEGATION);
                othersDelegationsScreen.alertOkButton.click();
            } else if (othersDelegationsScreen.alertMessage.getText().equals(ALERT_MSG_CAN_ONLY_ACCEPT_PENDING_DELEGATION)) {
                screenshot(SCREENSHOT_MSG_FAILED_TO_REJECT_DELEGATION);
                throw new RuntimeException(SCREENSHOT_MSG_FAILED_TO_REJECT_DELEGATION);
            }
        }
    }

    /**
     * This method will verify the status of the delegation
     *
     * @param index
     * @param status
     * @return boolean
     */
    public boolean verifyDelegationStatus(int index, String status) {
        hasLoadingCompleted();
        System.out.println("Actual: " + othersDelegationsScreen.statusList.get(index).getText());
        System.out.println("Expected: " + status + ", index: " + index);
        return scrollDownUntilElementIsDisplayed(othersDelegationsScreen.statusList.get(index)).getText().equals(status);
    }

    class PageObjects {
        @FindBy(xpath = "//XCUIElementTypeTable[@visible='true']")
        WebElement tableContainer;

        @FindAll({
                @FindBy(id = "EMPLID")
        })
        List<WebElement> delegatorIDList;

        @FindAll({
                @FindBy(id = "DelegationType")
        })
        List<WebElement> delegationTypeList;

        @FindAll({
                @FindBy(id = "status")
        })
        List<WebElement> statusList;

        @FindBy(id = "Accept")
        WebElement acceptButton;

        @FindBy(id = "Reject")
        WebElement rejectButton;

        @FindBy(xpath = "//XCUIElementTypeAlert//XCUIElementTypeStaticText[2]")
        WebElement alertMessage;

        @FindBy(id = "OK")
        WebElement alertOKButton;

        @FindBy(id = "Ok")
        WebElement alertOkButton;
    }
}
