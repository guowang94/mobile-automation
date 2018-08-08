package com.scb.fmsw.mobile.screen;

import com.scb.fmsw.mobile.base.BaseScreen;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class DelegationWorkflowTypeScreen extends BaseScreen {

    private PageObjects delegationWorkflowType;

    String workflowType = "//XCUIElementTypeStaticText[@visible='true'][@name='$1']";

    public DelegationWorkflowTypeScreen(IOSDriver<IOSElement> testDriver) {
        iosDriver = testDriver;
        delegationWorkflowType = new PageObjects();
        PageFactory.initElements(iosDriver, delegationWorkflowType);
    }

    /**
     * This method will check if Table Container is loaded
     *
     * @return boolean
     */
    private boolean hasTableContainerLoaded() {
        return delegationWorkflowType.tableContainer.isDisplayed();
    }

    /**
     * This method will tap on the Workflow Type and tap Save
     *
     * @param workflowTypeList
     */
    public void tapOnWorkflowType(List<String> workflowTypeList) {
        if (hasTableContainerLoaded()) {
            if (workflowTypeList.size() > 0) {
                for (String workflowType : workflowTypeList) {

                    if (!workflowTypeList.contains(WORKFLOW_CNA)) {
                        waitForElementByXpath(this.workflowType.replace("$1", WORKFLOW_CNA), true).click();
                    }

                    if (workflowType.equals(WORKFLOW_CE)) {
                        waitForElementByXpath(this.workflowType.replace("$1", workflowType), true).click();
                    } else if (!workflowType.equals(WORKFLOW_CNA) && !workflowType.equals(WORKFLOW_CE)) {
                        waitForElementByXpath(this.workflowType.replace("$1", workflowType), true).click();
                    }
                }
            } else {
                System.out.println(ERROR_MSG_NO_WORKFLOW_TYPE);
            }
            delegationWorkflowType.saveButton.click();
        } else {
            throw new RuntimeException(ERROR_MSG_TABLE_CONTAINER_NOT_LOADED);
        }
    }

    class PageObjects {
        @FindBy(xpath = "//XCUIElementTypeTable[@visible='true']")
        WebElement tableContainer;

        @FindBy(id = "Save")
        WebElement saveButton;
    }
}
