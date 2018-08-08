package com.scb.fmsw.mobile.screen;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.scb.fmsw.mobile.base.BaseScreen;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

public class RecallScreen extends BaseScreen {

    private PageObjects recallScreen;
    private String recallType = "//XCUIElementTypeStaticText[@name='$1']";
    private String container = "(//XCUIElementTypeImage[@name='Rectangle 4'])[2]";
    private String alertTitle = "//XCUIElementTypeAlert//XCUIElementTypeStaticText[1]";

    public RecallScreen(IOSDriver<IOSElement> testDriver) {
        iosDriver = testDriver;
        recallScreen = new PageObjects();
        PageFactory.initElements(iosDriver, recallScreen);
    }

    /**
     * This method will check if Container has loaded
     *
     * @return boolean
     */
    private boolean hasContainerLoaded() {
        return waitForElementByXpath(container, true).isEnabled();
    }

    /**
     * This method will recall overdue workflow
     *
     * @param recallType     Close, Re-Upload
     * @param lateActionCode null if not late
     * @param isNilReport    always false when recall type is re-upload
     * @param workflowType
     * @param workflowCount
     * @return InboxScreen
     */
    public InboxScreen recallWorkflow(String recallType, String lateActionCode, boolean isNilReport, String workflowType, int workflowCount) {
        hasLoadingCompleted();
        if (hasContainerLoaded()) {

            if (lateActionCode != null) {
                selectCode(lateActionCode);
                enterLateComment();
            }

            selectRecallType(recallType);
            enterComment();
            toggleSwitch(isNilReport);
            recallScreen.recallButton.click();
            verifyRecallStatus(recallType, workflowType, workflowCount);
        }
        return new InboxScreen(iosDriver);
    }

    /**
     * This method will select the recall type
     *
     * @param recallType
     */
    private void selectRecallType(String recallType) {
        recallScreen.recallPicker.click();
        iosDriver.findElementByXPath(this.recallType.replace("$1", recallType)).click();
    }

    /**
     * This method will key in late comment
     */
    private void enterLateComment() {
        recallScreen.lateCommentTextbox.sendKeys(MSG_ENTER_LATE_COMMENT);
    }


    /**
     * This method will either select Recall type or Late Action code
     *
     * @param code
     */
    private void selectCode(String code) {
        recallScreen.codeTextBox.click();
        recallScreen.codePicker.sendKeys(code);
        recallScreen.pickerDoneButton.click();
    }

    /**
     * This method will key in comment
     */
    private void enterComment() {
        recallScreen.commentTextbox.sendKeys(MSG_ENTER_COMMENT);
    }

    /**
     * This method will toggle the switch
     *
     * @param isNilReport
     */
    private void toggleSwitch(boolean isNilReport) {
        iosDriver.hideKeyboard();
        if (isNilReport) {
            try {
                recallScreen.toggleSwitch.click();
                waitForElementByName("Ok", true);
                recallScreen.alertOkButton.click();
            } catch (Exception e) {
                System.out.println(ERROR_MSG_DO_NOT_HAVE_SWITCH_ELEMENT);
            }
        }
    }

    /**
     * This method will verify the Recall status
     *
     * @param recallType
     * @param workflowType
     * @param workflowCount
     */
    private void verifyRecallStatus(String recallType, String workflowType, int workflowCount) {
        hasLoadingCompleted();
        int duration = 20;

        if (workflowCount == 1) {
            duration = 30;
        } else if (workflowCount > 1) {
            duration = duration * workflowCount;
        }
        try {
            waitForElementByXpath(alertTitle, duration, true);
            if ("Successfully Completed".equalsIgnoreCase(recallScreen.alertMessage.getText())) {
                screenshot("Recalled " + recallType + " " + workflowType + " Workflow");
                System.out.println("Successfully Recall Workflow");
                recallScreen.alertOkButton.click();
            } else {
                screenshot("Failed to Recall " + recallType + " " + workflowType + " Workflow");
                System.out.println("Failed to Recall Workflow");
                recallScreen.alertOkButton.click();
                recallScreen.backButtonInRecallScreen.click();
                recallScreen.backButtonInDetailScreen.click();
            }
        } catch (Exception e) {

        }
    }

        class PageObjects {
            @FindBy(id = "Recall Type")
            public WebElement recallPicker;

            @FindBy(id = "lateComment")
            public WebElement lateCommentTextbox;

            @FindBy(id = "SelectText")
            public WebElement codeTextBox;

            @FindBy(xpath = "//XCUIElementTypePickerWheel")
            public WebElement codePicker;

            @FindBy(id = "Done")
            public WebElement pickerDoneButton;

            @FindBy(id = "comment0")
            public WebElement commentTextbox;

            @FindBy(xpath = "//XCUIElementTypeNavigationBar[1]/XCUIElementTypeButton[2]")
            public WebElement recallButton;

            @FindBy(xpath = "//XCUIElementTypeAlert//XCUIElementTypeStaticText[2]")
            public WebElement alertMessage;

            @FindBy(xpath = "//XCUIElementTypeAlert//XCUIElementTypeButton[1]")
            public WebElement alertOkButton;

            @FindBy(xpath = "//XCUIElementTypeNavigationBar[1]/XCUIElementTypeButton[1]")
            public WebElement backButtonInRecallScreen;

            @FindBy(id = "left angle")
            public WebElement backButtonInDetailScreen;

            @FindBy(className = "XCUIElementTypeSwitch")
            public WebElement toggleSwitch;
        }
    }
