package com.scb.fmsw.mobile.screen;

import com.scb.fmsw.mobile.WorkflowConstants;
import com.scb.fmsw.mobile.base.BaseScreen;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class InboxScreen extends BaseScreen implements WorkflowConstants {

    private PageObjects inboxScreen;

    //accessibility id
    private String swipeAcknowledgeButton = "acknowledge";
    private String swipeBookmarkButton = "Bookmark";
    private String swipeClarificationButton = "Clarification";
    private String swipeNewClarificationButton = "Request for Clarification";
    private String swipeDelegateButton = "Delegate";
    private String swipeSubmitButton = "Submit";
    private String swipeRespondButton = "Respond";
    private String swipeReassignButton = "Reassign";
    private String loadMoreResultsButton = "Load More Results";


    //xpath
    private String workflow = "//XCUIElementTypeStaticText[@value='$1']";
    private String workflowCell = "//XCUIElementTypeStaticText[@value='$1']/ancestor::XCUIElementTypeCell";
    private String bucketCount = "//XCUIElementTypeStaticText[@name='$1']/following-sibling::XCUIElementTypeStaticText";
    private String options = "//XCUIElementTypeStaticText[@name='$1']";
    private String optionsCell = "//XCUIElementTypeStaticText[@name='$1']/ancestor::XCUIElementTypeCell";
    private String workflowList = "//XCUIElementTypeTable[@visible='true']//XCUIElementTypeStaticText[@name='WorkFlowID']";
    private String cnaWorkflowList = "//XCUIElementTypeTable[@visible='true']//XCUIElementTypeStaticText[@name='CNAWorkflowID']";
    private String workflowID = "(//XCUIElementTypeStaticText[@name='WorkFlowID'])[1]";
    private String cnaWorkflowID = "(//XCUIElementTypeStaticText[@name='CNAWorkflowID'])[1]";
    private String tabContainer = "//XCUIElementTypeCollectionView";
    private String workflowStatusTextfield = "//XCUIElementTypeStaticText[@value='$1']/preceding-sibling::XCUIElementTypeStaticText";
    private String loadMoreResultsCell = "//XCUIElementTypeButton[@name='Load More Results']/ancestor::XCUIElementTypeCell";
    private String subWorkflowIDByWorkflowID = "//XCUIElementTypeStaticText[@value='$1']/ancestor::XCUIElementTypeCell/descendant::XCUIElementTypeStaticText[@name='CNAWorkflowID']";

    public InboxScreen(IOSDriver<IOSElement> testDriver) {
        iosDriver = testDriver;
        inboxScreen = new PageObjects();
        PageFactory.initElements(iosDriver, inboxScreen);
    }

    /**
     * This method will check if Tab Container has loaded
     *
     * @return boolean
     */
    private boolean hasTabContainerLoaded() {
        return waitForElementByXpath(tabContainer, true).isDisplayed();
    }

    /**
     * This method will return the workflowID of the first workflow
     *
     * @return String
     */
    public String getFirstWorkflowId() {
        String workflowID;
        hasLoadingCompleted();
        if (hasTabContainerLoaded()) {
            try {
                workflowID = waitForElementByXpath(this.workflowID, true).getText();
                System.out.println("Workflow Id: " + workflowID);
            } catch (Exception e) {
                screenshot(SCREENSHOT_MSG_NO_WORKFLOW_FOUND);
                throw new RuntimeException(ERROR_MSG_NO_WORKFLOW_FOUND);
            }
        } else {
            throw new RuntimeException(ERROR_MSG_TAB_CONTAINER_NOT_LOADED);
        }
        return workflowID;
    }

    /**
     * This method will return the Workflow ID of the first CNA workflow
     *
     * @return String
     */
    public String getFirstCNAWorkflowId() {
        String workflowID;
        hasLoadingCompleted();
        if (hasTabContainerLoaded()) {
            try {
                workflowID = waitForElementByXpath(cnaWorkflowID, true).getText();
                System.out.println("Workflow Id: " + workflowID);
            } catch (Exception e) {
                screenshot(SCREENSHOT_MSG_NO_WORKFLOW_FOUND);
                throw new RuntimeException(ERROR_MSG_NO_WORKFLOW_FOUND);
            }
        } else {
            throw new RuntimeException(ERROR_MSG_TAB_CONTAINER_NOT_LOADED);
        }
        return workflowID;
    }

    /**
     * This method will return Sub Workflow ID/Trade ID
     *
     * @param workflowID
     * @return String
     */
    public String getSubWorkflowId(String workflowID) {
        String subWorkflowID;
        hasLoadingCompleted();
        if (hasTabContainerLoaded()) {
            try {
                subWorkflowID = waitForElementByXpath(subWorkflowIDByWorkflowID.replace("$1", workflowID), true).getText();
                System.out.println("Sub Workflow Id: " + subWorkflowID);
            } catch (Exception e) {
                screenshot(SCREENSHOT_MSG_NO_WORKFLOW_FOUND);
                throw new RuntimeException(ERROR_MSG_NO_WORKFLOW_FOUND_WITH_THAT_WORKFLOW_ID);
            }
        } else {
            throw new RuntimeException(ERROR_MSG_TAB_CONTAINER_NOT_LOADED);
        }
        return subWorkflowID;
    }

    /**
     * This method will tap on the workflow based on the workflow id
     *
     * @param workflowID of the workflow that need to be tapped
     * @return InboxDetailViewScreen
     */
    public InboxDetailViewScreen tapOnWorkflow(String workflowID) {
        try {
            tapOnElement(waitForElementByXpath(workflow.replace("$1", workflowID), true));
            System.out.println("Navigate to Detail View");
        } catch (Exception e) {
            screenshot(SCREENSHOT_MSG_NO_WORKFLOW_FOUND);
            throw new RuntimeException(ERROR_MSG_NO_WORKFLOW_FOUND_WITH_THAT_WORKFLOW_ID);
        }
        return new InboxDetailViewScreen(iosDriver);
    }

    /**
     * This method will verify the workflow in the respective bucket
     *
     * @param workflowID of the workflow that need to be verified
     * @param count      is <b>always</b> 1
     * @return boolean
     */
    public boolean verifyWorkflowInBucket(String workflowID, int count, String currentBucket) {
        WebElement element;

        hasLoadingCompleted();
        if (hasTabContainerLoaded()) {
            System.out.println("Finding workflow with this id: " + workflowID);
            try {
                //Find workflow
                element = waitForElementByXpath(workflowCell.replace("$1", workflowID), true);
                scrollDownUntilElementIsDisplayed(element);
                screenshot(SCREENSHOT_MSG_VERIFIED_WORKFLOW_IS_IN_BUCKET.replace("$1", workflowID).replace("$2", currentBucket));
                return true;
            } catch (Exception e) {
                if (Integer.parseInt(waitForElementByXpath(bucketCount.replace("$1", currentBucket), true).getText()) > 50 * count) {
                    tapOnLoadMoreResultsButton();
                    System.out.println("Retrying to verify workflow " + workflowID);
                    count++;
                    return verifyWorkflowInBucket(workflowID, count, currentBucket);
                } else {
                    screenshot(ERROR_MSG_NO_WORKFLOW_FOUND_WITH_THAT_WORKFLOW_ID);
                    throw new RuntimeException(ERROR_MSG_NO_WORKFLOW_FOUND_WITH_THAT_WORKFLOW_ID);
                }
            }
        } else {
            throw new RuntimeException(ERROR_MSG_TAB_CONTAINER_NOT_LOADED);
        }
    }

    /**
     * This method will verify the details of the workflow after action has been performed
     *
     * @param workflowStatus e.g. overdue, due or open
     * @param workflowType   workflow type
     * @param workflowID     of the workflow
     * @return boolean
     */
    public boolean verifyDetailsPostActionPerformed(String workflowStatus, String workflowType, String workflowID) {
        InboxDetailViewScreen inboxDetailViewScreen = navigateToDetailView(workflowID);

        //Verify Prev Actor Comment and Workflow Status
        //------------------------------ VE WORKFLOW ------------------------------
        if (workflowType.equals(WORKFLOW_VE)) {
            switch (workflowStatus) {
                case WORKFLOW_STATUS_PENDING_MTCR_OR_MRO_UPLOADER_CLARIFICATION:
                    if (inboxDetailViewScreen.compareComment(INBOX_DETAIL_COMPLIANCE_COMMENTS_CELL) &&
                            verifyWorkflowStatus(inboxDetailViewScreen, workflowStatus, workflowType)) {
                        return true;
                    } else {
                        throw new RuntimeException(ERROR_MSG_FAILED_TO_VERIFY_WORKFLOW);
                    }
                case WORKFLOW_STATUS_PENDING_VDO_CLARIFICATION:
                    if (inboxDetailViewScreen.compareComment(INBOX_DETAIL_COMPLIANCE_COMMENTS_CELL) &&
                            verifyWorkflowStatus(inboxDetailViewScreen, workflowStatus, workflowType)) {
                        return true;
                    } else {
                        throw new RuntimeException(ERROR_MSG_FAILED_TO_VERIFY_WORKFLOW);
                    }
                case WORKFLOW_STATUS_PENDING_COMPLIANCE_ACTION_POST_CLARIFICATION:
                    String prevActorType = inboxDetailViewScreen.getPrevActorTypeValue();
                    if (prevActorType.equals(PREV_ACTOR_TYPE_ROLE_MTCR_OR_MRO_UPLOADER)) {
                        if (inboxDetailViewScreen.compareComment(INBOX_DETAIL_MRO_OR_MTCR_UPLOADER_COMMENTS_CELL) &&
                                verifyWorkflowStatus(inboxDetailViewScreen, workflowStatus, workflowType)) {
                            return true;
                        } else {
                            throw new RuntimeException(ERROR_MSG_FAILED_TO_VERIFY_WORKFLOW);
                        }
                    } else if (prevActorType.equals(PREV_ACTOR_TYPE_ROLE_VOLCKER_DESK_OWNER)) {
                        if (inboxDetailViewScreen.compareComment(INBOX_DETAIL_VDO_COMMENTS_CELL) &&
                                verifyWorkflowStatus(inboxDetailViewScreen, workflowStatus, workflowType)) {
                            return true;
                        } else {
                            throw new RuntimeException(ERROR_MSG_FAILED_TO_VERIFY_WORKFLOW);
                        }
                    } else {
                        throw new RuntimeException(ERROR_MSG_FAILED_TO_VERIFY_WORKFLOW);
                    }
                case WORKFLOW_STATUS_PENDING_COMPLIANCE_CLARIFICATION_UT:
                    if (inboxDetailViewScreen.compareComment(INBOX_DETAIL_VDO_COMMENTS_CELL) &&
                            verifyWorkflowStatus(inboxDetailViewScreen, workflowStatus, workflowType)) {
                        return true;
                    } else {
                        throw new RuntimeException(ERROR_MSG_FAILED_TO_VERIFY_WORKFLOW);
                    }
                case WORKFLOW_STATUS_PENDING_COMPLIANCE_ACTION_UT:
                    if (inboxDetailViewScreen.compareComment(INBOX_DETAIL_JUSTIFICATION_FOR_DISCIPLINARY_ACTION_COMMENT_CELL, MSG_ENTER_FO_JUSTIFICATION_COMMENT) &&
                            inboxDetailViewScreen.compareComment(INBOX_DETAIL_HOW_TO_PREVENT_RECURRENCE_COMMENT_CELL, MSG_ENTER_PREVENT_RECURRENCE_COMMENT) &&
                            inboxDetailViewScreen.compareComment(INBOX_DETAIL_REMEDIATION_ACTION_COMMENT_CELL, MSG_ENTER_REMEDIATION_ACTION_COMMENT) &&
                            inboxDetailViewScreen.compareComment(INBOX_DETAIL_VDO_COMMENT_CELL, MSG_ENTER_VDO_COMMENT) &&
                            verifyWorkflowStatus(inboxDetailViewScreen, workflowStatus, workflowType)) {
                        return true;
                    } else {
                        throw new RuntimeException(ERROR_MSG_FAILED_TO_VERIFY_WORKFLOW);
                    }
                case WORKFLOW_STATUS_PENDING_COMPLIANCE_ACTION_POST_CLARIFICATION_UT:
                    if (inboxDetailViewScreen.compareComment(INBOX_DETAIL_JUSTIFICATION_FOR_DISCIPLINARY_ACTION_COMMENT_CELL, MSG_ENTER_FO_JUSTIFICATION_COMMENT_EDIT) &&
                            inboxDetailViewScreen.compareComment(INBOX_DETAIL_HOW_TO_PREVENT_RECURRENCE_COMMENT_CELL, MSG_ENTER_PREVENT_RECURRENCE_COMMENT_EDIT) &&
                            inboxDetailViewScreen.compareComment(INBOX_DETAIL_REMEDIATION_ACTION_COMMENT_CELL, MSG_ENTER_REMEDIATION_ACTION_COMMENT_EDIT) &&
                            inboxDetailViewScreen.compareComment(INBOX_DETAIL_VDO_COMMENT_CELL, MSG_ENTER_VDO_COMMENT_EDIT) &&
                            verifyWorkflowStatus(inboxDetailViewScreen, workflowStatus, workflowType)) {
                        return true;
                    } else {
                        throw new RuntimeException(ERROR_MSG_FAILED_TO_VERIFY_WORKFLOW);
                    }
                default:
                    throw new RuntimeException(ERROR_MSG_FAILED_TO_VERIFY_WORKFLOW);
            }

        }
        //------------------------------ CE WORKFLOW ------------------------------
        else if (workflowType.equals(WORKFLOW_CE) &&
                !workflowStatus.equals(WORKFLOW_STATUS_PENDING_CLARIFICATION_WITH_LIMIT_MONITORING) &&
                !workflowStatus.equals(WORKFLOW_STATUS_PENDING_MTCR_REVIEW_POST_CLARIFICATION) &&
                !workflowStatus.equals(WORKFLOW_STATUS_PENDING_CLARIFICATION_WITH_MTCR) &&
                !workflowStatus.equals(WORKFLOW_STATUS_PENDING_FO_REVIEW_POST_CLARIFICATION)) {

            if (workflowStatus.equals(WORKFLOW_STATUS_PENDING_FO_REVIEW)) {
                if (inboxDetailViewScreen.compareComment(INBOX_DETAIL_ISSUE_FLAG_COMMENT_CELL, MSG_ENTER_ISSUE_FLAG_COMMENT) &&
                        inboxDetailViewScreen.compareComment(INBOX_DETAIL_RISK_ASSESSMENT_COMMENT_CELL, MSG_ENTER_RISK_ASSESSMENT_COMMENT) &&
                        inboxDetailViewScreen.compareComment(INBOX_DETAIL_EXPLANATION_COMMENT_CELL, MSG_ENTER_EXPLANATION_COMMENT) &&
                        inboxDetailViewScreen.compareComment(INBOX_DETAIL_CONTROL_BREAKDOWN_COMMENT_CELL, MSG_ENTER_CONTROL_BREAKDOWN_COMMENT) &&
                        inboxDetailViewScreen.compareComment(INBOX_DETAIL_OUTCOME_COMMENT_CELL, MSG_ENTER_OUTCOME_COMMENT) &&
                        inboxDetailViewScreen.compareComment(INBOX_DETAIL_GROUP_REMARK_COMMENT_CELL, MSG_ENTER_GROUP_REMARK)) {
                    return true;
                } else {
                    throw new RuntimeException(ERROR_MSG_FAILED_TO_VERIFY_WORKFLOW);
                }
            } else if (workflowStatus.equals(WORKFLOW_STATUS_REVIEWED_AND_DISCIPLINARY_ACTION_TAKEN)) {
                if (inboxDetailViewScreen.compareComment(INBOX_DETAIL_FO_JUSTIFICATION_COMMENT_CELL, MSG_ENTER_FO_JUSTIFICATION_COMMENT) &&
                        inboxDetailViewScreen.compareComment(INBOX_DETAIL_PREVENT_RECURRENCE_COMMENT_CELL, MSG_ENTER_PREVENT_RECURRENCE_COMMENT) &&
                        inboxDetailViewScreen.compareComment(INBOX_DETAIL_SUPERVISOR_REMARK_CELL, MSG_ENTER_SUPERVISOR_REMARK)) {
                    return true;
                } else {
                    throw new RuntimeException(ERROR_MSG_FAILED_TO_VERIFY_WORKFLOW);
                }
            }

        }
        //-------------------------- PNL WORKFLOW's Review & Accepted ------------------------------
        else if (workflowType.equals(WORKFLOW_PNL) && workflowStatus.equals(WORKFLOW_STATUS_REVIEWED_AND_ACCEPTED)) {
            if (inboxDetailViewScreen.compareWorkflowEventStatus(workflowStatus)) {
                return true;
            } else {
                throw new RuntimeException(ERROR_MSG_FAILED_TO_VERIFY_WORKFLOW);
            }
        }
        //---------------------------- OMR WORKFLOW's Acknowledged ------------------------------
        else if (workflowType.equals(WORKFLOW_OMR) && WORKFLOW_STATUS_ACKNOWLEDGED.equals(workflowStatus)) {
            if (inboxDetailViewScreen.compareComment(INBOX_DETAIL_ACKNOWLEDGEMENT_COMMENTS_CELL) &&
                    verifyWorkflowStatus(inboxDetailViewScreen, workflowStatus, workflowType)) {
                return true;
            } else {
                throw new RuntimeException(ERROR_MSG_FAILED_TO_VERIFY_WORKFLOW);
            }
        }
        //------------------------------ OTHERS WORKFLOW ------------------------------
        else if (!workflowStatus.equals(WORKFLOW_STATUS_ACKNOWLEDGED) &&
                !workflowStatus.equals(WORKFLOW_STATUS_APPROVED)) {

            //Verify Comments and Workflow Status/Workflow Event Status
            if (WORKFLOW_FVA.equals(workflowType) || WORKFLOW_PNL.equals(workflowType) ||
                    WORKFLOW_IPV.equals(workflowType)) {
                if (inboxDetailViewScreen.compareComment(INBOX_DETAIL_PREV_ACTOR_COMMENTS_CELL) &&
                        inboxDetailViewScreen.compareWorkflowEventStatus(workflowStatus)) {
                    return true;
                } else {
                    throw new RuntimeException(ERROR_MSG_FAILED_TO_VERIFY_WORKFLOW);
                }
            } else {
                if (inboxDetailViewScreen.compareComment(INBOX_DETAIL_PREV_ACTOR_COMMENTS_CELL) &&
                        verifyWorkflowStatus(inboxDetailViewScreen, workflowStatus, workflowType)) {
                    return true;
                } else {
                    throw new RuntimeException(ERROR_MSG_FAILED_TO_VERIFY_WORKFLOW);
                }
            }
        } else if (verifyWorkflowStatus(inboxDetailViewScreen, workflowStatus, workflowType)) {
            return true;
        } else {
            throw new RuntimeException(ERROR_MSG_FAILED_TO_VERIFY_WORKFLOW);
        }
        return false;
    }

    /**
     * This method will verify Curr Actor Type/Group
     *
     * @param clarificationOption
     * @param workflowType
     * @param workflowID
     * @return boolean
     */
    public boolean verifyCurrActorTypeOrGroup(String clarificationOption, String workflowType, String workflowID) {
        InboxDetailViewScreen inboxDetailViewScreen = navigateToDetailView(workflowID);

        //Verify Curr Actor Type/Group
        switch (clarificationOption) {
            case CLARIFICATION_OPTION_CNA_LM:
                if (inboxDetailViewScreen.compareCurrActorType(CURR_ACTOR_TYPE_CNA_PERFORMER_LM)) {
                    break;
                } else {
                    throw new RuntimeException(ERROR_MSG_FAILED_TO_VERIFY_WORKFLOW);
                }
            case CLARIFICATION_OPTION_CNA_PERFORMER:
                if (inboxDetailViewScreen.compareCurrActorType(CURR_ACTOR_TYPE_CNA_PERFORMER)) {
                    break;
                } else {
                    throw new RuntimeException(ERROR_MSG_FAILED_TO_VERIFY_WORKFLOW);
                }
            case CLARIFICATION_OPTION_OMR_LM:
                if (inboxDetailViewScreen.compareCurrActorType(CURR_ACTOR_TYPE_OMR_PERFORMER_LM)) {
                    break;
                } else {
                    throw new RuntimeException(ERROR_MSG_FAILED_TO_VERIFY_WORKFLOW);
                }
            case CLARIFICATION_OPTION_OMR_PERFORMER:
                if (inboxDetailViewScreen.compareCurrActorType(CURR_ACTOR_TYPE_OMR_PERFORMER)) {
                    break;
                } else {
                    throw new RuntimeException(ERROR_MSG_FAILED_TO_VERIFY_WORKFLOW);
                }
            case CLARIFICATION_OPTION_SEND_TO:
                if (inboxDetailViewScreen.compareCurrActorType(CURR_ACTOR_TYPE_OTHER_USER)) {
                    break;
                } else {
                    throw new RuntimeException(ERROR_MSG_FAILED_TO_VERIFY_WORKFLOW);
                }
            case CLARIFICATION_OPTION_PC:
                if (WORKFLOW_PNL.equals(workflowType)) {
                    if (inboxDetailViewScreen.compareCurrActorType(CURR_ACTOR_TYPE_PC_USER)) {
                        break;
                    } else {
                        throw new RuntimeException(ERROR_MSG_FAILED_TO_VERIFY_WORKFLOW);
                    }
                } else {
                    if (inboxDetailViewScreen.compareCurrActorType(CURR_ACTOR_TYPE_PC_USER) &&
                            inboxDetailViewScreen.compareCurrActorGroup(CURR_ACTOR_GROUP_PC_GBS)) {
                        break;
                    } else {
                        throw new RuntimeException(ERROR_MSG_FAILED_TO_VERIFY_WORKFLOW);
                    }
                }
            case CLARIFICATION_OPTION_MO:
                if (inboxDetailViewScreen.compareCurrActorType(CURR_ACTOR_TYPE_MO_USER) &&
                        inboxDetailViewScreen.isCurrActorGroupValuePresent()) {
                    break;
                } else {
                    throw new RuntimeException(ERROR_MSG_FAILED_TO_VERIFY_WORKFLOW);
                }
            case CLARIFICATION_OPTION_MRO_MTRC_GT:
                if (inboxDetailViewScreen.compareCurrActorType(CURR_ACTOR_TYPE_MRO_MTCR_GT_USER)) {
                    break;
                } else {
                    throw new RuntimeException(ERROR_MSG_FAILED_TO_VERIFY_WORKFLOW);
                }
            case CLARIFICATION_OPTION_VALUATION_CONTROL_USER:
                if (inboxDetailViewScreen.compareCurrActorType(CURR_ACTOR_TYPE_VC_USER)) {
                    break;
                } else {
                    throw new RuntimeException(ERROR_MSG_FAILED_TO_VERIFY_WORKFLOW);
                }
            case CLARIFICATION_OPTION_VOLCKER_COMPLIANCE:
                if (inboxDetailViewScreen.compareCurrActorType(CURR_ACTOR_TYPE_COMPLIANCE) &&
                        inboxDetailViewScreen.isCurrActorGroupValuePresent()) {
                    break;
                } else {
                    throw new RuntimeException(ERROR_MSG_FAILED_TO_VERIFY_WORKFLOW);
                }
            default:
                throw new RuntimeException(ERROR_MSG_CLARIFICATION_OPTION_NOT_MATCHED);
        }
        inboxDetailViewScreen.tapOnBackButton();
        return true;
    }

    /**
     * This method will contains steps for Verifying Workflow Status
     *
     * @param inboxDetailViewScreen
     * @param workflowStatus
     * @return boolean
     */
    private boolean verifyWorkflowStatus(InboxDetailViewScreen inboxDetailViewScreen, String workflowStatus,
                                         String workflowType) {
        boolean result;
        //TODO need to find out which workflow type also does not have Response Grid
        if (workflowType.equalsIgnoreCase(WORKFLOW_CNA) || workflowType.equalsIgnoreCase(WORKFLOW_OMR) ||
                workflowType.equalsIgnoreCase(WORKFLOW_PNL) || workflowType.equalsIgnoreCase(WORKFLOW_VE) ||
                workflowType.equalsIgnoreCase(WORKFLOW_CE)) {
            result = inboxDetailViewScreen.compareWorkflowStatus(workflowStatus);
        } else {
            //Navigate to Response Grid Screen and validate Workflow Status
            InboxDetailsOptionScreen inboxDetailsOptionScreen = inboxDetailViewScreen.navigateToInboxDetailsOptionScreen();
            InboxResponsesGridScreen inboxResponsesGridScreen = inboxDetailsOptionScreen.navigateToInboxResponseScreen();
            result = inboxResponsesGridScreen.verifyWorkflowStatus(workflowStatus, workflowType);

            //Navigate back to Inbox Detail View Screen
            inboxDetailsOptionScreen = inboxResponsesGridScreen.tapOnBackButton();
            inboxDetailViewScreen = inboxDetailsOptionScreen.tapOnBackButton();
        }
        inboxDetailViewScreen.tapOnBackButton();
        return result;
    }

    /**
     * This method will tap on load more button in inbox screen
     *
     * @return boolean
     */
    public boolean tapOnLoadMoreResultsButton() {
        TouchAction action = new TouchAction(iosDriver);
        boolean loop = true;
        hasLoadingCompleted();
        try {
            waitForElementById(loadMoreResultsButton, 15, true); // To check if element is available
            while (loop) {
                try {
                    loop = (!waitForElementByXpath(loadMoreResultsCell, 10, true).isDisplayed());
                } catch (Exception e) {
                    action.press(180, 620).moveTo(0, -620).release().perform();
                }
            }
            inboxScreen.loadMoreResultsButton.click();
            System.out.println("Tap On Load More Button");
            return true;
        } catch (Exception e) {
            System.out.println(MSG_NO_MORE_LOAD_RESULTS_BUTTON);
            return false;
        }
    }

    /**
     * This method will tap on the workflow based on the workflow id and navigate to Detail View Screen
     *
     * @param workflowID of the workflow that need to be tapped
     * @return InboxDetailViewScreen
     */
    public InboxDetailViewScreen navigateToDetailView(String workflowID) {
        waitForElementByXpath(workflowCell.replace("$1", workflowID), true).click();
        System.out.println("Navigate to Inbox Detail View Screen");
        return new InboxDetailViewScreen(iosDriver);
    }

    /**
     * This method navigate to any bucket
     *
     * @param currentBucket eg. TO DO, IN PROGRESS, CLOSED, TO REVIEW, TO CLAIM
     */
    public void navigateToBucket(String currentBucket) {
        hasLoadingCompleted();
        if (hasTabContainerLoaded()) {
            waitForElementById(currentBucket, true).click();
            System.out.println("Navigate to " + currentBucket + " bucket");
        } else {
            throw new RuntimeException(ERROR_MSG_TAB_CONTAINER_NOT_LOADED);
        }
    }

    /**
     * This method will tap on more option button
     */
    private void tapOnMoreOption() {
        hasLoadingCompleted();
        if (hasTabContainerLoaded()) {
            inboxScreen.moreOptionButton.click();
            System.out.println("Tap on more option button");
        } else {
            throw new RuntimeException(ERROR_MSG_TAB_CONTAINER_NOT_LOADED);
        }
    }

    /**
     * This method will tap on fullscreen button
     */
    private void tapOnFullscreenButton() {
        hasLoadingCompleted();
        if (hasTabContainerLoaded()) {
            inboxScreen.fullscreenButton.click();
        } else {
            throw new RuntimeException(ERROR_MSG_TAB_CONTAINER_NOT_LOADED);
        }
    }

    /**
     * This method will return all the workflow id in the current bucket
     *
     * @return List<String>
     */
    public List<String> getAllWorkflowId() {
        List<String> workflowIdList = new ArrayList<>();
        boolean loop = true;
        hasLoadingCompleted();
        if (hasTabContainerLoaded()) {
            System.out.println("Get all the workflow id in the bucket");
            while (loop) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    //Do nothing
                }
                loop = tapOnLoadMoreResultsButton();
            }
            List<WebElement> workflowIdElementList;
            try {
                workflowIdElementList = waitForElementsByXpath(workflowList, true);
            } catch (NoSuchElementException e) {
                screenshot(ERROR_MSG_NO_WORKFLOW_FOUND);
                throw new RuntimeException(ERROR_MSG_NO_WORKFLOW_FOUND);
            }
            System.out.println("Begin to add each workflow id to the list");
            System.out.println("size: " + workflowIdElementList.size());
            for (WebElement element : workflowIdElementList) {
                workflowIdList.add(element.getText());
            }
        } else {
            throw new RuntimeException(ERROR_MSG_TAB_CONTAINER_NOT_LOADED);
        }
        return workflowIdList;
    }

    /**
     * This method will return all the workflow id in the current bucket
     *
     * @return List<String>
     */
    public List<String> getAllCNAWorkflowId() {
        List<String> workflowIdList = new ArrayList<>();
        boolean loop = true;
        hasLoadingCompleted();
        if (hasTabContainerLoaded()) {
            System.out.println("Get all the workflow id in the bucket");
            while (loop) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    //Do nothing
                }
                loop = tapOnLoadMoreResultsButton();
            }
            List<WebElement> workflowIdElementList;
            try {
                workflowIdElementList = waitForElementsByXpath(cnaWorkflowList, true);
            } catch (NoSuchElementException e) {
                screenshot(ERROR_MSG_NO_WORKFLOW_FOUND);
                throw new RuntimeException(ERROR_MSG_NO_WORKFLOW_FOUND);
            }
            System.out.println("Begin to add each workflow id to the list");
            System.out.println("size: " + workflowIdElementList.size());
            for (WebElement element : workflowIdElementList) {
                workflowIdList.add(element.getText());
            }
        } else {
            throw new RuntimeException(ERROR_MSG_TAB_CONTAINER_NOT_LOADED);
        }
        return workflowIdList;
    }

    /**
     * This method will tap on Acknowledge Selected for CNA, IPV, FVA and OMR, Review & Accept Selected for PNL,
     * Approve Selected for GMR and GT, Review and Assess Selected for CE and Clarify Selected
     *
     * @param numbersOfWorkflowToBeSelected number of workflow to be selected
     * @param currentBucket                 current bucket
     * @param action                        Acknowledge Selected, Review & Accept Selected, Approve Selected or Clarify Selected
     * @return SelectMultipleWorkflowScreen
     */
    public SelectMultipleWorkflowScreen navigateToSelectMultipleWorkflowScreen(int numbersOfWorkflowToBeSelected,
                                                                               String currentBucket, String action) {
        if (numbersOfWorkflowToBeSelected <= Integer.parseInt(waitForElementByXpath(
                bucketCount.replace("$1", currentBucket), true).getText())) {
            tapOnMoreOption();
            scrollDownUntilElementIsDisplayed(waitForElementByXpath(options.replace("$1", action), true)).click();
            System.out.println("Navigate to Select Multiple Workflow Screen");
        } else {
            screenshot(ERROR_MSG_NOT_ENOUGH_WORKFLOW);
            throw new RuntimeException(ERROR_MSG_NOT_ENOUGH_WORKFLOW);
        }
        return new SelectMultipleWorkflowScreen(iosDriver);
    }

    /**
     * This method will tap on Acknowledge All for CNA, IPV, FVA and OMR, Review & Accept All for PNL,
     * Review and Assess All for CE and Approve All for GMR, GT
     *
     * @param action Acknowledge All, Review & Accept All, Review and Assess All or Approve All
     * @return AcknowledgeScreen
     */
    public AcknowledgeScreen acknowledgeAllWorkflow(String action) {
        tapOnMoreOption();
        scrollDownUntilElementIsDisplayed(waitForElementByXpath(optionsCell.replace("$1", action), true));
        waitForElementByXpath(options.replace("$1", action), true).click();
        waitForElementUntilClickable(inboxScreen.alertOkButton, true).click();
        System.out.println("Navigate to Acknowledge Screen");
        return new AcknowledgeScreen(iosDriver);
    }

    /**
     * This method will tap on Clarify All
     *
     * @return ClarificationOptionScreen
     */
    public ClarificationOptionScreen clarifyAllWorkflow() {
        tapOnMoreOption();
        scrollDownUntilElementIsDisplayed(waitForElementByXpath(optionsCell.replace("$1", MORE_OPTION_CLARIFY_ALL), true));
        waitForElementByXpath(options.replace("$1", MORE_OPTION_CLARIFY_ALL), true).click();
        waitForElementUntilClickable(inboxScreen.alertOkButton, true).click();
        System.out.println("Navigate to Clarification Option Screen");
        return new ClarificationOptionScreen(iosDriver);
    }

    /**
     * This method will tap on Clarify All For CE
     *
     * @return ClarificationScreen
     */
    public ClarificationScreen clarifyAllWorkflowForCE() {
        tapOnMoreOption();
        scrollDownUntilElementIsDisplayed(waitForElementByXpath(optionsCell.replace("$1", MORE_OPTION_CLARIFY_ALL), true));
        waitForElementByXpath(options.replace("$1", MORE_OPTION_CLARIFY_ALL), true).click();
        waitForElementUntilClickable(inboxScreen.alertOkButton, true).click();
        System.out.println("Navigate to Clarification Screen");
        return new ClarificationScreen(iosDriver);
    }

    /**
     * This method will tap on Reassign All
     *
     * @return ReassignScreen
     */
    public ReassignScreen reassignAllWorkflow() {
        tapOnMoreOption();
        scrollDownUntilElementIsDisplayed(waitForElementByXpath(optionsCell.replace("$1", MORE_OPTION_REASSIGN_ALL), true));
        waitForElementByXpath(options.replace("$1", MORE_OPTION_REASSIGN_ALL), true).click();
        waitForElementUntilClickable(inboxScreen.alertOkButton, true).click();
        System.out.println("Navigate to Reassign Screen");
        return new ReassignScreen(iosDriver);
    }

    /**
     * This method will tap on Submit All
     *
     * @return SubmitScreen
     */
    public SubmitScreen submitAllWorkflow() {
        tapOnMoreOption();
        scrollDownUntilElementIsDisplayed(waitForElementByXpath(optionsCell.replace("$1", MORE_OPTION_SUBMIT_ALL), true));
        waitForElementByXpath(options.replace("$1", MORE_OPTION_SUBMIT_ALL), true).click();
        waitForElementUntilClickable(inboxScreen.alertOkButton, true).click();
        System.out.println("Navigate to Submit Screen");
        return new SubmitScreen(iosDriver);
    }

    /**
     * This method will tap on Submit All
     *
     * @return SubmitOptionScreen
     */
    public SubmitOptionScreen submitAllWorkflowCE() {
        tapOnMoreOption();
        scrollDownUntilElementIsDisplayed(waitForElementByXpath(optionsCell.replace("$1", MORE_OPTION_SUBMIT_ALL), true));
        waitForElementByXpath(options.replace("$1", MORE_OPTION_SUBMIT_ALL), true).click();
        waitForElementUntilClickable(inboxScreen.alertOkButton, true).click();
        System.out.println("Navigate to Submit Option Screen");
        return new SubmitOptionScreen(iosDriver);
    }

    /**
     * This method will tap on Delegate All
     *
     * @return ExplicitDelegationScreen
     */
    public ExplicitDelegationScreen delegateAllWorkflow() {
        tapOnMoreOption();
        scrollDownUntilElementIsDisplayed(waitForElementByXpath(optionsCell.replace("$1", MORE_OPTION_DELEGATE_ALL), true));
        waitForElementByXpath(options.replace("$1", MORE_OPTION_DELEGATE_ALL), true).click();
        waitForElementUntilClickable(inboxScreen.alertOkButton, true).click();
        System.out.println("Navigate to Explicit Delegation Screen");
        return new ExplicitDelegationScreen(iosDriver);
    }

    /**
     * This method will tap on Bookmark All
     *
     * @return BookmarkScreen
     */
    public BookmarkScreen bookmarkAllWorkflow() {
        tapOnMoreOption();
        scrollDownUntilElementIsDisplayed(waitForElementByXpath(optionsCell.replace("$1", MORE_OPTION_BOOKMARK_ALL), true));
        waitForElementByXpath(options.replace("$1", MORE_OPTION_BOOKMARK_ALL), true).click();
        waitForElementUntilClickable(inboxScreen.alertOkButton, true).click();
        System.out.println("Navigate to Bookmark Screen");
        return new BookmarkScreen(iosDriver);
    }

    /**
     * This method will swipe right on a workflow and tap on Acknowledge for CNA, IPV, FVA and OMR, Review & Accept for PNL,
     * Approve for GMR, GT
     *
     * @param workflowID
     * @return AcknowledgeScreen
     */
    public AcknowledgeScreen swipeRightAndTapOnAcknowledge(String workflowID, String workflowType) {
        if (hasTabContainerLoaded()) {
            swipeRight(workflowID);
            waitForElementById(swipeAcknowledgeButton, true).click();
            System.out.println("Navigate to Acknowledge Screen");
        }
        return new AcknowledgeScreen(iosDriver);
    }

    /**
     * This method will swipe right on a workflow and tap on Submit
     *
     * @param workflowID
     * @return SubmitScreen
     */
    public SubmitScreen swipeRightAndTapOnSubmit(String workflowID) {
        if (hasTabContainerLoaded()) {
            swipeRight(workflowID);
            waitForElementById(swipeSubmitButton, true).click();
            System.out.println("Navigate to Submit Screen");
        }
        return new SubmitScreen(iosDriver);
    }

    /**
     * This method will swipe right on a workflow and tap on Submit
     *
     * @param workflowID
     * @return SubmitOptionScreen
     */
    public SubmitOptionScreen swipeRightAndTapOnSubmitCE(String workflowID) {
        if (hasTabContainerLoaded()) {
            swipeRight(workflowID);
            waitForElementById(swipeSubmitButton, true).click();
            System.out.println("Navigate to Submit Option Screen");
        }
        return new SubmitOptionScreen(iosDriver);
    }

    /**
     * This method will swipe right on a workflow and tap on Respond will navigate to Submit Screen
     *
     * @param workflowID
     * @return SubmitScreen
     */
    public SubmitScreen swipeRightAndTapOnRespond(String workflowID) {
        hasLoadingCompleted();
        if (hasTabContainerLoaded()) {
            swipeRight(workflowID);
            waitForElementById(swipeRespondButton, true).click();
            System.out.println("Navigate to Submit Screen");
        }
        return new SubmitScreen(iosDriver);
    }

    /**
     * This method will swipe right on a workflow and tap on Respond will navigate to Submit Option Screen
     *
     * @param workflowID
     * @return SubmitOptionScreen
     */
    public SubmitOptionScreen swipeRightAndTapOnRespondVE(String workflowID) {
        hasLoadingCompleted();
        if (hasTabContainerLoaded()) {
            swipeRight(workflowID);
            waitForElementById(swipeRespondButton, true).click();
            System.out.println("Navigate to Submit Option Screen");
        }
        return new SubmitOptionScreen(iosDriver);
    }

    /**
     * This method will swipe left on a workflow and tap on Clarification
     *
     * @param workflowID
     * @return ClarificationOptionScreen
     */
    public ClarificationOptionScreen swipeLeftAndTapOnClarification(String workflowID) {
        hasLoadingCompleted();
        if (hasTabContainerLoaded()) {
            swipeLeft(workflowID);
            try {
                waitForElementById(swipeClarificationButton, true).click();
            } catch (Exception e) {
                waitForElementById(swipeNewClarificationButton, true).click();
            }
            System.out.println("Navigate to Clarification Option Screen");
        }
        return new ClarificationOptionScreen(iosDriver);
    }

    /**
     * This method will swipe left on a workflow and tap on Clarification for CE workflow
     *
     * @param workflowID
     * @return ClarificationScreen
     */
    public ClarificationScreen swipeLeftAndTapOnClarificationForCE(String workflowID) {
        hasLoadingCompleted();
        if (hasTabContainerLoaded()) {
            swipeLeft(workflowID);
            try {
                waitForElementById(swipeClarificationButton, true).click();
            } catch (Exception e) {
                waitForElementById(swipeNewClarificationButton, true).click();
            }
            System.out.println("Navigate to Clarification Screen");
        }
        return new ClarificationScreen(iosDriver);
    }

    /**
     * This method will swipe left on a workflow and tap on Delegate
     *
     * @param workflowID
     * @return ExplicitDelegationScreen
     */
    public ExplicitDelegationScreen swipeLeftAndTapOnDelegate(String workflowID) {
        hasLoadingCompleted();
        if (hasTabContainerLoaded()) {
            swipeLeft(workflowID);
            waitForElementById(swipeDelegateButton, true).click();
            System.out.println("Navigate to Explicit Delegation Screen");
        }
        return new ExplicitDelegationScreen(iosDriver);
    }

    /**
     * This method will swipe left on a workflow and tap on Bookmark
     *
     * @param workflowID
     * @return BookmarkScreen
     */
    public BookmarkScreen swipeLeftAndTapOnBookmark(String workflowID) {
        hasLoadingCompleted();
        if (hasTabContainerLoaded()) {
            swipeLeft(workflowID);
            waitForElementById(swipeBookmarkButton, true).click();
            System.out.println("Navigate to Bookmark Screen");
        }
        return new BookmarkScreen(iosDriver);
    }

    /**
     * This method will swipe left on a workflow and tap on Reassign for PC GBS
     *
     * @param workflowID
     * @return ReassignScreen
     */
    public ReassignScreen swipeLeftAndTapOnReassign(String workflowID) {
        hasLoadingCompleted();
        if (hasTabContainerLoaded()) {
            swipeLeft(workflowID);
            waitForElementById(swipeReassignButton, true).click();
            System.out.println("Navigate to Reassign Screen");
        }
        return new ReassignScreen(iosDriver);
    }

    /**
     * This method will swipe right on a workflow and tap on Reassign for FO Admin
     *
     * @param workflowID
     * @return ReassignScreen
     */
    public ReassignScreen swipeRightAndTapOnReassign(String workflowID) {
        hasLoadingCompleted();
        if (hasTabContainerLoaded()) {
            swipeRight(workflowID);
            waitForElementById(swipeReassignButton, true).click();
            System.out.println("Navigate to Reassign Screen");
        }
        return new ReassignScreen(iosDriver);
    }

    /**
     * This method will swipe left
     *
     * @param workflowID
     */
    private void swipeLeft(String workflowID) {
        WebElement workflowCellElement = findElementByXpath(workflowCell.replace("$1", workflowID), true);
        scrollDownUntilElementIsDisplayed(findElementByXpath(workflowStatusTextfield.replace("$1", workflowID), true));
        new TouchAction(iosDriver).press(getElementLocationX(workflowCellElement),
                getElementLocationY(workflowCellElement)).moveTo(-100, 0).release().perform();
    }

    /**
     * This method will swipe right
     *
     * @param workflowID
     */
    private void swipeRight(String workflowID) {
        WebElement workflowCellElement = findElementByXpath(workflowCell.replace("$1", workflowID), true);
        scrollDownUntilElementIsDisplayed(findElementByXpath(workflowStatusTextfield.replace("$1", workflowID), true));
        new TouchAction(iosDriver).press(getElementLocationX(workflowCellElement),
                getElementLocationY(workflowCellElement)).moveTo(100, 0).release().perform();
    }

    /**
     * This method will get the x axis of the element
     *
     * @param element
     * @return int
     */
    private int getElementLocationX(WebElement element) {
        return element.getLocation().getX() + element.getRect().getWidth() / 2;
    }

    /**
     * This method will get the y axis of the element
     *
     * @param element
     * @return int
     */
    private int getElementLocationY(WebElement element) {
        return element.getLocation().getY() + element.getRect().getHeight() / 2;
    }

    /**
     * This method will tap on For Acknowledgement Sub Tab
     */
    public void tapOnForAcknowledgementSubTab() {
        hasLoadingCompleted();
        inboxScreen.forAcknowledgementSubTab.click();
    }

    /**
     * This method will tap on For Review Sub Tab
     */
    public void tapOnForReviewSubTab() {
        hasLoadingCompleted();
        inboxScreen.forReviewSubTab.click();
    }

    /**
     * This method will tap on For Approval Sub Tab
     */
    public void tapOnForApprovalSubTab() {
        hasLoadingCompleted();
        inboxScreen.forApprovalSubTab.click();
    }

    /**
     * This method will tap on For Review & Acceptance Sub Tab
     */
    public void tapOnForReviewAndAcceptanceSubTab() {
        hasLoadingCompleted();
        inboxScreen.forReviewAndAcceptanceSubTab.click();
    }

    /**
     * This method will tap on For Clarification Sub Tab
     */
    public void tapOnForClarificationSubTab() {
        hasLoadingCompleted();
        inboxScreen.forClarificationSubTab.click();
    }

    /**
     * This method will tap on For Verification Sub Tab
     */
    public void tapOnForVerificationSubTab() {
        hasLoadingCompleted();
        inboxScreen.forVerificationSubTab.click();
    }

    /**
     * This method will tap on For Exception Sub Tab
     */
    public void tapOnForExceptionSubTab() {
        hasLoadingCompleted();
        inboxScreen.forExceptionSubTab.click();
    }

    /**
     * This method will tap on For Review & Assessment Sub Tab
     */
    public void tapOnForReviewAndAssessmentSubTab() {
        hasLoadingCompleted();
        inboxScreen.forReviewAndAssessmentSubTab.click();
    }

    /**
     * This method will tap on For Review & Action Sub Tab
     */
    public void tapOnForReviewAndActionSubTab() {
        hasLoadingCompleted();
        inboxScreen.forReviewAndActionSubTab.click();
    }

    class PageObjects {
        @FindBy(id = "Load More Results")
        WebElement loadMoreResultsButton;

        @FindBy(xpath = "(//XCUIElementTypeStaticText[@name='WorkFlowId'])[1]")
        WebElement firstWorkflowId;

        @FindBy(id = "fullscreen")
        WebElement fullscreenButton;

        @FindBy(id = "more option")
        WebElement moreOptionButton;

        @FindBy(xpath = "//XCUIElementTypeAlert//XCUIElementTypeButton[1]")
        WebElement alertOkButton;

        @FindBy(id = "FOR ACKNOWLEDGEMENT")
        WebElement forAcknowledgementSubTab;

        @FindBy(id = "FOR APPROVAL")
        WebElement forApprovalSubTab;

        @FindBy(id = "FOR CLARIFICATION")
        WebElement forClarificationSubTab;

        @FindBy(id = "FOR VERIFICATION")
        WebElement forVerificationSubTab;

        @FindBy(id = "FOR EXCEPTION")
        WebElement forExceptionSubTab;

        @FindBy(id = "FOR REVIEW & ACCEPTANCE")
        WebElement forReviewAndAcceptanceSubTab;

        @FindBy(id = "FOR REVIEW")
        WebElement forReviewSubTab;

        @FindBy(xpath = "(//XCUIElementTypeCollectionView[@visible='true'])[2]/XCUIElementTypeCell[1]")
        WebElement forReviewAndAssessmentSubTab;

        @FindBy(xpath = "(//XCUIElementTypeCollectionView[@visible='true'])[2]/XCUIElementTypeCell[1]")
        WebElement forReviewAndActionSubTab;
    }

}
