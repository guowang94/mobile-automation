package com.scb.fmsw.mobile.screen;

import com.scb.fmsw.mobile.WorkflowConstants;
import com.scb.fmsw.mobile.base.BaseScreen;

import com.sun.jndi.ldap.Ber;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

public class ClarificationOptionScreen extends BaseScreen implements WorkflowConstants {

    private String tableContainer = "//XCUIElementTypeTable[@visible='true']";

    public ClarificationOptionScreen(IOSDriver<IOSElement> testDriver) {
        iosDriver = testDriver;
    }

    /**
     * This method will check if Table Container is loaded
     *
     * @return boolean
     */
    private boolean hasTableContainerLoaded() {
        return waitForElementByXpath(tableContainer, true).isDisplayed();
    }

    /**
     * This method will navigate to Clarification Screen based on the option
     *
     * @param option       - eg. Product Control, Middle Office, Send to, Valuation Control User, MRO/MTCR/GT
     * @param workflowType
     * @return ClarificationScreen
     */
    public ClarificationScreen selectClarificationOption(String option, String workflowType) {
        hasLoadingCompleted();
        if (hasTableContainerLoaded()) {
            verifyClarificationOptions(workflowType);
            waitForElementById(option, true).click();
            System.out.println("Navigate to Clarification Screen");
        } else {
            throw new RuntimeException(ERROR_MSG_TABLE_CONTAINER_NOT_LOADED);
        }
        return new ClarificationScreen(iosDriver);
    }

    private void verifyClarificationOptions(String workflowType) {
        switch (workflowType) {
            case WORKFLOW_CNA:
                if (waitForElementById(CLARIFICATION_OPTION_CNA_LM, true).isDisplayed() &&
                        waitForElementById(CLARIFICATION_OPTION_CNA_PERFORMER, true).isDisplayed() &&
                        waitForElementById(CLARIFICATION_OPTION_PC, true).isDisplayed() &&
                        waitForElementById(CLARIFICATION_OPTION_MO, true).isDisplayed() &&
                        waitForElementById(CLARIFICATION_OPTION_SEND_TO, true).isDisplayed()) {
                    System.out.println(MSG_VERIFIED_CLARIFICATION_OPTIONS);
                    break;
                } else {
                    screenshot(ERROR_MSG_FAILED_TO_VERIFY_CLARIFICATION_OPTION);
                    throw new RuntimeException(ERROR_MSG_FAILED_TO_VERIFY_CLARIFICATION_OPTION);
                }
            case WORKFLOW_OMR:
                if (waitForElementById(CLARIFICATION_OPTION_OMR_LM, true).isDisplayed() &&
                        waitForElementById(CLARIFICATION_OPTION_OMR_PERFORMER, true).isDisplayed() &&
                        waitForElementById(CLARIFICATION_OPTION_PC, true).isDisplayed() &&
                        waitForElementById(CLARIFICATION_OPTION_SEND_TO, true).isDisplayed()) {
                    System.out.println(MSG_VERIFIED_CLARIFICATION_OPTIONS);
                    break;
                } else {
                    screenshot(ERROR_MSG_FAILED_TO_VERIFY_CLARIFICATION_OPTION);
                    throw new RuntimeException(ERROR_MSG_FAILED_TO_VERIFY_CLARIFICATION_OPTION);
                }
            case WORKFLOW_GMR:
                //TODO need to delete element is not null when the bug is fixed - FMSW-8791
                if (waitForElementById(CLARIFICATION_OPTION_MRO_MTRC_GT, false) != null &&
                        waitForElementById(CLARIFICATION_OPTION_MRO_MTRC_GT, true).isDisplayed() &&
                        waitForElementById(CLARIFICATION_OPTION_SEND_TO, true).isDisplayed()) {
                    System.out.println(MSG_VERIFIED_CLARIFICATION_OPTIONS);
                    break;
                } else {
                    screenshot(ERROR_MSG_FAILED_TO_VERIFY_CLARIFICATION_OPTION);
                    //TODO need to uncomment when they have fixed the clarification option bug - FMSW-8791
//                    throw new RuntimeException(ERROR_MSG_FAILED_TO_VERIFY_CLARIFICATION_OPTION);
                    System.out.println(ERROR_MSG_FAILED_TO_VERIFY_CLARIFICATION_OPTION);
                    break;
                }
            case WORKFLOW_FVA:
                //TODO need to delete element is not null when the bug is fixed - FMSW-8791
                if (waitForElementById(CLARIFICATION_OPTION_VALUATION_CONTROL_USER, false) != null &&
                        waitForElementById(CLARIFICATION_OPTION_VALUATION_CONTROL_USER, true).isDisplayed() &&
                        waitForElementById(CLARIFICATION_OPTION_SEND_TO, true).isDisplayed()) {
                    System.out.println(MSG_VERIFIED_CLARIFICATION_OPTIONS);
                    break;
                } else {
                    screenshot(ERROR_MSG_FAILED_TO_VERIFY_CLARIFICATION_OPTION);
                    //TODO need to uncomment when they have fixed the clarification option bug - FMSW-8791
//                    throw new RuntimeException(ERROR_MSG_FAILED_TO_VERIFY_CLARIFICATION_OPTION);
                    System.out.println(ERROR_MSG_FAILED_TO_VERIFY_CLARIFICATION_OPTION);
                    break;
                }
            case WORKFLOW_IPV:
                //TODO need to delete element is not null when the bug is fixed - FMSW-8791
                if (waitForElementById(CLARIFICATION_OPTION_VALUATION_CONTROL_USER, false) != null &&
                        waitForElementById(CLARIFICATION_OPTION_VALUATION_CONTROL_USER, true).isDisplayed() &&
                        waitForElementById(CLARIFICATION_OPTION_SEND_TO, true).isDisplayed()) {
                    System.out.println(MSG_VERIFIED_CLARIFICATION_OPTIONS);
                    break;
                } else {
                    screenshot(ERROR_MSG_FAILED_TO_VERIFY_CLARIFICATION_OPTION);
                    //TODO need to uncomment when they have fixed the clarification option bug - FMSW-8791
//                    throw new RuntimeException(ERROR_MSG_FAILED_TO_VERIFY_CLARIFICATION_OPTION);
                    System.out.println(ERROR_MSG_FAILED_TO_VERIFY_CLARIFICATION_OPTION);
                    break;
                }
            case WORKFLOW_PNL:
                if (waitForElementById(CLARIFICATION_OPTION_PC, true).isDisplayed() &&
                        waitForElementById(CLARIFICATION_OPTION_SEND_TO, true).isDisplayed()) {
                    System.out.println(MSG_VERIFIED_CLARIFICATION_OPTIONS);
                    break;
                } else {
                    screenshot(ERROR_MSG_FAILED_TO_VERIFY_CLARIFICATION_OPTION);
                    throw new RuntimeException(ERROR_MSG_FAILED_TO_VERIFY_CLARIFICATION_OPTION);
                }
            case WORKFLOW_VE:
                if (waitForElementById(CLARIFICATION_OPTION_VOLCKER_COMPLIANCE, true).isDisplayed()) {
                    System.out.println(MSG_VERIFIED_CLARIFICATION_OPTIONS);
                    break;
                } else {
                    screenshot(ERROR_MSG_FAILED_TO_VERIFY_CLARIFICATION_OPTION);
                    throw new RuntimeException(ERROR_MSG_FAILED_TO_VERIFY_CLARIFICATION_OPTION);
                }
                default:
                    screenshot("verifyClarificationOptions(): in default");
                    throw new RuntimeException("verifyClarificationOptions(): in default");
        }
    }
}
