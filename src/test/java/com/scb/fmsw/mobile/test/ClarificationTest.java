package com.scb.fmsw.mobile.test;

import com.scb.fmsw.mobile.base.BaseTest;
import com.scb.fmsw.mobile.screen.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class ClarificationTest extends BaseTest {

    //next time need to check for prev actor comment, isDisputed?, isEscalated?, workflow status
    //create test method for CNA, OMR, PNL, GT/GMR, IPV/FVA, CE, VE

    //-------------------------------- CNA ---------------------------------

    @Test(groups = {TEST_GRP_CLARIFICATION, TEST_GRP_CNA, TEST_GRP_CLARIFICATION_PC})
    public void swipeToSendCNAWorkflowToPCForClarificationTest() {
        System.out.println("Method: swipeToSendCNAWorkflowToPCForClarificationTest()");
        boolean hasDispute = true;
        String workflowID;

        //--------------Send for Clarification-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername03"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CNA, STATUS_OVERDUE);
        inboxScreen.navigateToBucket(BUCKET_TO_REVIEW);
        inboxScreen.tapOnForAcknowledgementSubTab();
        workflowID = inboxScreen.getFirstCNAWorkflowId();
        ClarificationOptionScreen clarificationOptionScreen = inboxScreen.swipeLeftAndTapOnClarification(workflowID);
        ClarificationScreen clarificationScreen = clarificationOptionScreen.selectClarificationOption(CLARIFICATION_OPTION_PC, WORKFLOW_CNA);
        inboxScreen = clarificationScreen.clarifyWorkflow(null, null, hasDispute, WORKFLOW_CNA, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_CLARIFICATION, WORKFLOW_CNA, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyCurrActorTypeOrGroup(CLARIFICATION_OPTION_PC, WORKFLOW_CNA, workflowID));
        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_CLARIFICATION, TEST_GRP_CNA, TEST_GRP_CLARIFICATION_LM})
    public void swipeToSendCNAWorkflowToLMForClarificationTest() {
        System.out.println("Method: swipeToSendCNAWorkflowToLMForClarificationTest()");
        String workflowID;

        //--------------Send for Clarification-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername03"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CNA, STATUS_OVERDUE);
        inboxScreen.navigateToBucket(BUCKET_TO_REVIEW);
        inboxScreen.tapOnForAcknowledgementSubTab();
        workflowID = inboxScreen.getFirstCNAWorkflowId();
        ClarificationOptionScreen clarificationOptionScreen = inboxScreen.swipeLeftAndTapOnClarification(workflowID);
        ClarificationScreen clarificationScreen = clarificationOptionScreen.selectClarificationOption(CLARIFICATION_OPTION_CNA_LM, WORKFLOW_CNA);
        inboxScreen = clarificationScreen.clarifyWorkflow(null, null, false, WORKFLOW_CNA, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_CLARIFICATION, WORKFLOW_CNA, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyCurrActorTypeOrGroup(CLARIFICATION_OPTION_CNA_LM, WORKFLOW_CNA, workflowID));

        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_CLARIFICATION, TEST_GRP_CNA, TEST_GRP_CLARIFICATION_PERFORMER})
    public void swipeToSendCNAWorkflowToCNAPerformerForClarificationTest() {
        System.out.println("Method: swipeToSendCNAWorkflowToCNAPerformerForClarificationTest()");
        String workflowID;

        //--------------Send for Clarification-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername03"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CNA, STATUS_OVERDUE);
        inboxScreen.navigateToBucket(BUCKET_TO_REVIEW);
        inboxScreen.tapOnForAcknowledgementSubTab();
        workflowID = inboxScreen.getFirstCNAWorkflowId();
        ClarificationOptionScreen clarificationOptionScreen = inboxScreen.swipeLeftAndTapOnClarification(workflowID);
        ClarificationScreen clarificationScreen = clarificationOptionScreen.selectClarificationOption(CLARIFICATION_OPTION_CNA_PERFORMER, WORKFLOW_CNA);
        inboxScreen = clarificationScreen.clarifyWorkflow(null, null, false, WORKFLOW_CNA, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_CLARIFICATION, WORKFLOW_CNA, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyCurrActorTypeOrGroup(CLARIFICATION_OPTION_CNA_PERFORMER, WORKFLOW_CNA, workflowID));

        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_CLARIFICATION, TEST_GRP_CNA, TEST_GRP_CLARIFICATION_MO})
    public void swipeToSendCNAWorkflowToMOForClarificationTest() {
        System.out.println("Method: swipeToSendCNAWorkflowToMOForClarificationTest()");
        String workflowID;

        //--------------Send for Clarification-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername03"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CNA, STATUS_OVERDUE);
        inboxScreen.navigateToBucket(BUCKET_TO_REVIEW);
        inboxScreen.tapOnForAcknowledgementSubTab();
        workflowID = inboxScreen.getFirstCNAWorkflowId();
        ClarificationOptionScreen clarificationOptionScreen = inboxScreen.swipeLeftAndTapOnClarification(workflowID);
        ClarificationScreen clarificationScreen = clarificationOptionScreen.selectClarificationOption(CLARIFICATION_OPTION_MO, WORKFLOW_CNA);
        inboxScreen = clarificationScreen.clarifyWorkflow(null, null, false, WORKFLOW_CNA, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_CLARIFICATION, WORKFLOW_CNA, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyCurrActorTypeOrGroup(CLARIFICATION_OPTION_MO, WORKFLOW_CNA, workflowID));

        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_CLARIFICATION, TEST_GRP_CNA, TEST_GRP_CLARIFICATION_OTHERS})
    public void sendCNAWorkflowToAnyoneForClarificationDetailViewTest() {
        System.out.println("Method: sendCNAWorkflowToAnyoneForClarificationDetailViewTest()");
        boolean hasEscalate = true;
        String workflowID;

        //--------------Send for Clarification-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername03"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CNA, STATUS_OVERDUE);
        inboxScreen.navigateToBucket(BUCKET_TO_REVIEW);
        inboxScreen.tapOnForAcknowledgementSubTab();
        workflowID = inboxScreen.getFirstCNAWorkflowId();
        InboxDetailViewScreen detailViewScreen = inboxScreen.tapOnWorkflow(workflowID, true);
        ClarificationOptionScreen clarificationOptionScreen = detailViewScreen.tapOnClarificationButton();
        ClarificationScreen clarificationScreen = clarificationOptionScreen.selectClarificationOption(CLARIFICATION_OPTION_SEND_TO, WORKFLOW_CNA);
        inboxScreen = clarificationScreen.clarifyWorkflow(null, prop.getProperty("uat.DelegateUsername"),
                hasEscalate, WORKFLOW_CNA, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_CLARIFICATION, WORKFLOW_CNA, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyCurrActorTypeOrGroup(CLARIFICATION_OPTION_SEND_TO, WORKFLOW_CNA, workflowID));
        inboxScreen.logout();

        //---------Login as another user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CNA, STATUS_OPEN);
        inboxScreen.tapOnForClarificationSubTab();
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_TO_DO),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_CLARIFICATION, WORKFLOW_CNA, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));

        //----------------Submit the workflow back to CNA user-------------------

        InboxDetailViewScreen inboxDetailViewScreen = inboxScreen.tapOnWorkflow(workflowID, true);
        SubmitScreen submitScreen = inboxDetailViewScreen.tapOnSubmitButton();
        inboxScreen = submitScreen.submitWorkflow(null, WORKFLOW_CNA, 1, false);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_SUBMIT_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_ACK_POST_CLARIFICATION, WORKFLOW_CNA, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        inboxScreen.logout();

        //---------Login as CNA user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.FOUsername03"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CNA, STATUS_OPEN);
        inboxScreen.tapOnForAcknowledgementSubTab();
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_TO_DO),
                FAILED_MSG_FAILED_TO_SUBMIT_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_ACK_POST_CLARIFICATION, WORKFLOW_CNA, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));

        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_CLARIFICATION, TEST_GRP_CNA, TEST_GRP_CLARIFICATION_OTHERS})
    public void swipeToSendCNAWorkflowToAnyoneForClarificationTest() {
        System.out.println("Method: swipeToSendCNAWorkflowToPCForClarificationTest()");
        boolean hasEscalate = false;
        String workflowID;

        //--------------Send for Clarification-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername03"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CNA, STATUS_OVERDUE);
        inboxScreen.navigateToBucket(BUCKET_TO_REVIEW);
        inboxScreen.tapOnForAcknowledgementSubTab();
        workflowID = inboxScreen.getFirstCNAWorkflowId();
        ClarificationOptionScreen clarificationOptionScreen = inboxScreen.swipeLeftAndTapOnClarification(workflowID);
        ClarificationScreen clarificationScreen = clarificationOptionScreen.selectClarificationOption(CLARIFICATION_OPTION_SEND_TO, WORKFLOW_CNA);
        inboxScreen = clarificationScreen.clarifyWorkflow(null, prop.getProperty("uat.DelegateUsername"),
                hasEscalate, WORKFLOW_CNA, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_CLARIFICATION, WORKFLOW_CNA, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyCurrActorTypeOrGroup(CLARIFICATION_OPTION_SEND_TO, WORKFLOW_CNA, workflowID));
        inboxScreen.logout();

        //---------Login as Other user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CNA, STATUS_OPEN);
        inboxScreen.tapOnForClarificationSubTab();
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_TO_DO),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_CLARIFICATION, WORKFLOW_CNA, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));

        //----------------Submit the workflow back to user-------------------

        SubmitScreen submitScreen = inboxScreen.swipeRightAndTapOnSubmit(workflowID);
        inboxScreen = submitScreen.submitWorkflow(null, WORKFLOW_CNA, 1, false);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_SUBMIT_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_ACK_POST_CLARIFICATION, WORKFLOW_CNA, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        inboxScreen.logout();

        //---------Login as CNA user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.FOUsername03"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CNA, STATUS_OPEN);
        inboxScreen.tapOnForAcknowledgementSubTab();
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_TO_DO),
                FAILED_MSG_FAILED_TO_SUBMIT_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_ACK_POST_CLARIFICATION, WORKFLOW_CNA, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));

        //----------------Acknowledge the workflow-------------------

        AcknowledgeScreen acknowledgeScreen = inboxScreen.swipeRightAndTapOnAcknowledge(workflowID, WORKFLOW_CNA);
        inboxScreen = acknowledgeScreen.acknowledgeWorkflow(null, WORKFLOW_CNA, 1);
        inboxScreen.navigateToBucket(BUCKET_CLOSED);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_CLOSED),
                FAILED_MSG_FAILED_TO_ACKNOWLEDGE_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_ACKNOWLEDGED, WORKFLOW_CNA, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));

        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_CLARIFICATION_SELECTED, TEST_GRP_CNA, TEST_GRP_CLARIFICATION_OTHERS},
            dependsOnMethods = {"swipeToSendCNAWorkflowToAnyoneForClarificationTest",
                    "sendCNAWorkflowToAnyoneForClarificationDetailViewTest"})
    public void clarifySelectedCNAWorkflowToAnyoneTest() {
        System.out.println("Method: clarifySelectedCNAWorkflowToAnyoneTest()");
        boolean hasEscalate = true;
        List<String> workflowIDList;
        List<String> allWorkflowIDList;
        int workflowCount = 1;

        //--------------Send for Clarification-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername01"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CNA, STATUS_OVERDUE);
        inboxScreen.navigateToBucket(BUCKET_TO_REVIEW);
        inboxScreen.tapOnForAcknowledgementSubTab();
        SelectMultipleWorkflowScreen selectMultipleWorkflowScreen = inboxScreen.navigateToSelectMultipleWorkflowScreen(workflowCount,
                BUCKET_TO_REVIEW, MORE_OPTION_CLARIFY_SELECTED);
        workflowIDList = selectMultipleWorkflowScreen.selectNumberOfCNAWorkflow(workflowCount);
        ClarificationOptionScreen clarificationOptionScreen = selectMultipleWorkflowScreen.tapOnClarifySelectedScreenDoneButton();
        ClarificationScreen clarificationScreen = clarificationOptionScreen.selectClarificationOption(CLARIFICATION_OPTION_SEND_TO, WORKFLOW_CNA);
        inboxScreen = clarificationScreen.clarifyWorkflow(null, prop.getProperty("uat.DelegateUsername"),
                hasEscalate, WORKFLOW_CNA, workflowCount);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList),
                FAILED_MSG_FAILED_TO_SENT_SELECTED_WORKFLOW_FOR_CLARIFICATION);
        inboxScreen.logout();

        //---------Login as other user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CNA, STATUS_OPEN);
        inboxScreen.tapOnForClarificationSubTab();
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList),
                FAILED_MSG_FAILED_TO_SENT_SELECTED_WORKFLOW_FOR_CLARIFICATION);


        //----------------Submit the workflow back to CNA user-------------------

        selectMultipleWorkflowScreen = inboxScreen.navigateToSelectMultipleWorkflowScreen(allWorkflowIDList.size(),
                BUCKET_TO_DO, MORE_OPTION_SUBMIT_SELECTED);
        selectMultipleWorkflowScreen.selectNumberOfWorkflow(workflowIDList);
        SubmitScreen submitScreen = selectMultipleWorkflowScreen.tapOnSubmitSelectedScreenDoneButton();
        inboxScreen = submitScreen.submitWorkflow(null, WORKFLOW_CNA, workflowCount, false);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList),
                FAILED_MSG_FAILED_TO_SUBMIT_SELECTED_WORKFLOW);
        inboxScreen.logout();

        //---------Login as CNA user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.FOUsername01"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CNA, STATUS_OPEN);
        inboxScreen.tapOnForAcknowledgementSubTab();
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList),
                FAILED_MSG_FAILED_TO_SUBMIT_SELECTED_WORKFLOW);

        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_CLARIFICATION_ALL, TEST_GRP_CNA, TEST_GRP_CLARIFICATION_OTHERS}/*,
            dependsOnMethods = {"clarifySelectedCNAWorkflowToAnyoneTest"}*/)
    public void clarifyAllCNAWorkflowToAnyoneTest() {
        System.out.println("Method: clarifyAllCNAWorkflowToAnyoneTest()");
        List<String> workflowIDList;
        List<String> allWorkflowIDList;
        boolean hasEscalate = true;

        //--------------Send for Clarification-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CNA, STATUS_OVERDUE);
        inboxScreen.navigateToBucket(BUCKET_TO_DO);
        inboxScreen.tapOnForAcknowledgementSubTab();
        workflowIDList = inboxScreen.getAllCNAWorkflowId();
        ClarificationOptionScreen clarificationOptionScreen = inboxScreen.clarifyAllWorkflow();
        ClarificationScreen clarificationScreen = clarificationOptionScreen.selectClarificationOption(CLARIFICATION_OPTION_SEND_TO, WORKFLOW_CNA);
        clarificationScreen.clarifyWorkflow(null, prop.getProperty("uat.DelegateUsername"), hasEscalate,
                WORKFLOW_CNA, workflowIDList.size());
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList),
                FAILED_MSG_FAILED_TO_SENT_ALL_WORKFLOW_FOR_CLARIFICATION);
        inboxScreen.logout();

        //---------Login as other user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CNA, STATUS_OPEN);
        inboxScreen.tapOnForClarificationSubTab();
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList),
                FAILED_MSG_FAILED_TO_SENT_ALL_WORKFLOW_FOR_CLARIFICATION);

        //----------------Submit the workflow back to CNA user-------------------

        SubmitScreen submitScreen = inboxScreen.submitAllWorkflow();
        inboxScreen = submitScreen.submitWorkflow(null, WORKFLOW_CNA, workflowIDList.size(), false);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_SUBMIT_ALL_WORKFLOW);
        inboxScreen.logout();

        //---------Login as CNA user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CNA, STATUS_OPEN);
        inboxScreen.tapOnForAcknowledgementSubTab();
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_SUBMIT_ALL_WORKFLOW);

        System.out.println("Complete!");
    }

    //-------------------------------- PNL ---------------------------------

    @Test(groups = {TEST_GRP_CLARIFICATION, TEST_GRP_PNL, TEST_GRP_CLARIFICATION_PC})
    public void swipeToSendPNLWorkflowToPCForClarificationTest() {
        System.out.println("Method: swipeToSendPNLWorkflowToPCForClarificationTest()");
        boolean hasDispute = true;
        String workflowID;

        //--------------Send for Clarification-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_PNL, STATUS_OPEN);
        inboxScreen.tapOnForReviewAndAcceptanceSubTab();
        workflowID = inboxScreen.getFirstCNAWorkflowId();
        ClarificationOptionScreen clarificationOptionScreen = inboxScreen.swipeLeftAndTapOnClarification(workflowID);
        ClarificationScreen clarificationScreen = clarificationOptionScreen.selectClarificationOption(CLARIFICATION_OPTION_PC, WORKFLOW_PNL);
        inboxScreen = clarificationScreen.clarifyWorkflow(null, null, hasDispute, WORKFLOW_PNL, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_CLARIFICATION, WORKFLOW_PNL, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyCurrActorTypeOrGroup(CLARIFICATION_OPTION_PC, WORKFLOW_PNL, workflowID));

        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_CLARIFICATION, TEST_GRP_PNL, TEST_GRP_CLARIFICATION_OTHERS})
    public void sendPNLWorkflowToAnyoneForClarificationDetailViewTest() {
        System.out.println("Method: sendPNLWorkflowToAnyoneForClarificationDetailViewTest()");
        boolean hasEscalate = true;
        String workflowID;

        //--------------Send for Clarification-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_PNL, STATUS_OPEN);
        inboxScreen.navigateToBucket(BUCKET_TO_DO);
        inboxScreen.tapOnForReviewAndAcceptanceSubTab();
        workflowID = inboxScreen.getFirstCNAWorkflowId();
        InboxDetailViewScreen detailViewScreen = inboxScreen.tapOnWorkflow(workflowID, true);
        ClarificationOptionScreen clarificationOptionScreen = detailViewScreen.tapOnClarificationButton();
        ClarificationScreen clarificationScreen = clarificationOptionScreen.selectClarificationOption(CLARIFICATION_OPTION_SEND_TO, WORKFLOW_PNL);
        inboxScreen = clarificationScreen.clarifyWorkflow(null, prop.getProperty("uat.ALMUsername"),
                hasEscalate, WORKFLOW_PNL, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_CLARIFICATION, WORKFLOW_PNL, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyCurrActorTypeOrGroup(CLARIFICATION_OPTION_SEND_TO, WORKFLOW_PNL, workflowID));
        inboxScreen.logout();

        //---------Login as another user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.ALMUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_PNL, STATUS_OPEN);
        inboxScreen.tapOnForClarificationSubTab();
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_TO_DO),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_CLARIFICATION, WORKFLOW_PNL, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));

        //----------------Submit the workflow back to CNA user-------------------

        InboxDetailViewScreen inboxDetailViewScreen = inboxScreen.tapOnWorkflow(workflowID, true);
        SubmitScreen submitScreen = inboxDetailViewScreen.tapOnSubmitButton();
        inboxScreen = submitScreen.submitWorkflow(null, WORKFLOW_PNL, 1, false);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_SUBMIT_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(
                WORKFLOW_STATUS_PENDING_REVIEW_AND_ACCEPTANCE_POST_CLARIFICATION, WORKFLOW_PNL, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        inboxScreen.logout();

        //---------Login as PNL user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_PNL, STATUS_OPEN);
        inboxScreen.tapOnForReviewAndAcceptanceSubTab();
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_TO_DO),
                FAILED_MSG_FAILED_TO_SUBMIT_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(
                WORKFLOW_STATUS_PENDING_REVIEW_AND_ACCEPTANCE_POST_CLARIFICATION, WORKFLOW_PNL, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));

        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_CLARIFICATION, TEST_GRP_PNL, TEST_GRP_CLARIFICATION_OTHERS})
    public void swipeToSendPNLWorkflowToAnyoneForClarificationTest() {
        System.out.println("Method: swipeToSendPNLWorkflowToAnyoneForClarificationTest()");
        boolean hasEscalate = true;
        String workflowID;

        //--------------Send for Clarification-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_PNL, STATUS_OPEN);
        inboxScreen.navigateToBucket(BUCKET_TO_DO);
        inboxScreen.tapOnForReviewAndAcceptanceSubTab();
        workflowID = inboxScreen.getFirstCNAWorkflowId();
        ClarificationOptionScreen clarificationOptionScreen = inboxScreen.swipeLeftAndTapOnClarification(workflowID);
        ClarificationScreen clarificationScreen = clarificationOptionScreen.selectClarificationOption(CLARIFICATION_OPTION_SEND_TO, WORKFLOW_PNL);
        inboxScreen = clarificationScreen.clarifyWorkflow(null, prop.getProperty("uat.ALMUsername"),
                hasEscalate, WORKFLOW_PNL, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_CLARIFICATION, WORKFLOW_PNL, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyCurrActorTypeOrGroup(CLARIFICATION_OPTION_SEND_TO, WORKFLOW_PNL, workflowID));
        inboxScreen.logout();

        //---------Login as others user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.ALMUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_PNL, STATUS_OPEN);
        inboxScreen.tapOnForClarificationSubTab();
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_TO_DO),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_CLARIFICATION, WORKFLOW_PNL, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));

        //----------------Submit the workflow back to CNA user-------------------

        InboxDetailViewScreen inboxDetailViewScreen = inboxScreen.tapOnWorkflow(workflowID, true);
        SubmitScreen submitScreen = inboxDetailViewScreen.tapOnSubmitButton();
        inboxScreen = submitScreen.submitWorkflow(null, WORKFLOW_PNL, 1, false);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_SUBMIT_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(
                WORKFLOW_STATUS_PENDING_REVIEW_AND_ACCEPTANCE_POST_CLARIFICATION, WORKFLOW_PNL, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        inboxScreen.logout();

        //---------Login as PNL user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_PNL, STATUS_OPEN);
        inboxScreen.tapOnForReviewAndAcceptanceSubTab();
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_TO_DO),
                FAILED_MSG_FAILED_TO_SUBMIT_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(
                WORKFLOW_STATUS_PENDING_REVIEW_AND_ACCEPTANCE_POST_CLARIFICATION, WORKFLOW_PNL, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));

        //----------------Acknowledge the workflow-------------------

        AcknowledgeScreen acknowledgeScreen = inboxScreen.swipeRightAndTapOnAcknowledge(workflowID, WORKFLOW_PNL);
        inboxScreen = acknowledgeScreen.acknowledgeWorkflow(null, WORKFLOW_PNL, 1);
        inboxScreen.navigateToBucket(BUCKET_CLOSED);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_CLOSED),
                FAILED_MSG_FAILED_TO_ACKNOWLEDGE_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_REVIEWED_AND_ACCEPTED, WORKFLOW_PNL, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));

        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_CLARIFICATION_SELECTED, TEST_GRP_PNL, TEST_GRP_CLARIFICATION_OTHERS},
            dependsOnMethods = {"sendPNLWorkflowToAnyoneForClarificationDetailViewTest",
                    "swipeToSendPNLWorkflowToAnyoneForClarificationTest"})
    public void clarifySelectedPNLWorkflowToAnyoneTest() {
        System.out.println("Method: clarifySelectedPNLWorkflowToAnyoneTest()");
        boolean hasEscalate = true;
        List<String> workflowIDList;
        List<String> allWorkflowIDList;
        int workflowCount = 1;

        //--------------Send for Clarification-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_PNL, STATUS_OPEN);
        inboxScreen.navigateToBucket(BUCKET_TO_DO);
//        inboxScreen.tapOnForReviewAndAcceptanceSubTab();
        SelectMultipleWorkflowScreen selectMultipleWorkflowScreen = inboxScreen.navigateToSelectMultipleWorkflowScreen(workflowCount,
                BUCKET_TO_DO, MORE_OPTION_CLARIFY_SELECTED);
        workflowIDList = selectMultipleWorkflowScreen.selectNumberOfCNAWorkflow(workflowCount);
        ClarificationOptionScreen clarificationOptionScreen = selectMultipleWorkflowScreen.tapOnClarifySelectedScreenDoneButton();
        ClarificationScreen clarificationScreen = clarificationOptionScreen.selectClarificationOption(CLARIFICATION_OPTION_SEND_TO, WORKFLOW_PNL);
        inboxScreen = clarificationScreen.clarifyWorkflow(null, prop.getProperty("uat.ALMUsername"),
                hasEscalate, WORKFLOW_PNL, workflowCount);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList),
                FAILED_MSG_FAILED_TO_SENT_SELECTED_WORKFLOW_FOR_CLARIFICATION);
        inboxScreen.logout();

        //---------Login as other user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.ALMUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_PNL, STATUS_OPEN);
        inboxScreen.tapOnForClarificationSubTab();
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList),
                FAILED_MSG_FAILED_TO_SENT_SELECTED_WORKFLOW_FOR_CLARIFICATION);


        //----------------Submit the workflow back to PNL user-------------------

        selectMultipleWorkflowScreen = inboxScreen.navigateToSelectMultipleWorkflowScreen(allWorkflowIDList.size(),
                BUCKET_TO_DO, MORE_OPTION_SUBMIT_SELECTED);
        selectMultipleWorkflowScreen.selectNumberOfWorkflow(workflowIDList);
        SubmitScreen submitScreen = selectMultipleWorkflowScreen.tapOnSubmitSelectedScreenDoneButton();
        inboxScreen = submitScreen.submitWorkflow(null, WORKFLOW_PNL, workflowCount, false);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_SUBMIT_SELECTED_WORKFLOW);
        inboxScreen.logout();

        //---------Login as PNL user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_PNL, STATUS_OPEN);
        inboxScreen.tapOnForReviewAndAcceptanceSubTab();
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_SUBMIT_SELECTED_WORKFLOW);

        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_CLARIFICATION_ALL, TEST_GRP_PNL, TEST_GRP_CLARIFICATION_OTHERS},
            dependsOnMethods = {"clarifySelectedPNLWorkflowToAnyoneTest"})
    public void clarifyAllPNLWorkflowToAnyoneTest() {
        System.out.println("Method: clarifyAllPNLWorkflowToAnyoneTest()");
        List<String> workflowIDList;
        List<String> allWorkflowIDList;
        boolean hasEscalate = true;

        //--------------Send for Clarification-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_PNL, STATUS_OPEN);
        inboxScreen.navigateToBucket(BUCKET_TO_DO);
        inboxScreen.tapOnForReviewAndAcceptanceSubTab();
        workflowIDList = inboxScreen.getAllCNAWorkflowId();
        ClarificationOptionScreen clarificationOptionScreen = inboxScreen.clarifyAllWorkflow();
        ClarificationScreen clarificationScreen = clarificationOptionScreen.selectClarificationOption(CLARIFICATION_OPTION_SEND_TO, WORKFLOW_PNL);
        clarificationScreen.clarifyWorkflow(null, prop.getProperty("uat.ALMUsername"),
                hasEscalate, WORKFLOW_PNL, workflowIDList.size());
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_SENT_ALL_WORKFLOW_FOR_CLARIFICATION);
        inboxScreen.logout();

        //---------Login as other user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.ALMUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_PNL, STATUS_OPEN);
        inboxScreen.tapOnForClarificationSubTab();
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_SENT_ALL_WORKFLOW_FOR_CLARIFICATION);

        //----------------Submit the workflow back to PNL user-------------------

        SubmitScreen submitScreen = inboxScreen.submitAllWorkflow();
        inboxScreen = submitScreen.submitWorkflow(null, WORKFLOW_PNL, workflowIDList.size(), false);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_SUBMIT_ALL_WORKFLOW);
        inboxScreen.logout();

        //---------Login as PNL user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_PNL, STATUS_OPEN);
        inboxScreen.tapOnForReviewAndAcceptanceSubTab();
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_SUBMIT_ALL_WORKFLOW);

        System.out.println("Complete!");
    }

    //-------------------------------- OMR ---------------------------------

    @Test(groups = {TEST_GRP_CLARIFICATION, TEST_GRP_OMR, TEST_GRP_CLARIFICATION_LM})
    public void swipeToSendOMRWorkflowToLMForClarificationTest() {
        System.out.println("Method: swipeToSendOMRWorkflowToLMForClarificationTest()");
        String workflowID;

        //--------------Send for Clarification-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.OMRUsername01"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_OMR, STATUS_OVERDUE);
        inboxScreen.tapOnForAcknowledgementSubTab();
        workflowID = inboxScreen.getFirstCNAWorkflowId();
        ClarificationOptionScreen clarificationOptionScreen = inboxScreen.swipeLeftAndTapOnClarification(workflowID);
        ClarificationScreen clarificationScreen = clarificationOptionScreen.selectClarificationOption(CLARIFICATION_OPTION_OMR_LM, WORKFLOW_OMR);
        inboxScreen = clarificationScreen.clarifyWorkflow(null, null, false, WORKFLOW_OMR, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_CLARIFICATION, WORKFLOW_OMR, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyCurrActorTypeOrGroup(CLARIFICATION_OPTION_OMR_LM, WORKFLOW_OMR, workflowID));

        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_CLARIFICATION, TEST_GRP_OMR, TEST_GRP_CLARIFICATION_PERFORMER})
    public void swipeToSendOMRWorkflowToOMRPerformerForClarificationTest() {
        System.out.println("Method: swipeToSendOMRWorkflowToOMRPerformerForClarificationTest()");
        String workflowID;

        //--------------Send for Clarification-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.OMRUsername01"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_OMR, STATUS_OVERDUE);
        inboxScreen.tapOnForAcknowledgementSubTab();
        workflowID = inboxScreen.getFirstCNAWorkflowId();
        ClarificationOptionScreen clarificationOptionScreen = inboxScreen.swipeLeftAndTapOnClarification(workflowID);
        ClarificationScreen clarificationScreen = clarificationOptionScreen.selectClarificationOption(CLARIFICATION_OPTION_OMR_PERFORMER, WORKFLOW_OMR);
        inboxScreen = clarificationScreen.clarifyWorkflow(null, null, false, WORKFLOW_OMR, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_CLARIFICATION, WORKFLOW_OMR, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyCurrActorTypeOrGroup(CLARIFICATION_OPTION_OMR_PERFORMER, WORKFLOW_OMR, workflowID));

        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_CLARIFICATION, TEST_GRP_OMR, TEST_GRP_CLARIFICATION_PC})
    public void swipeToSendOMRWorkflowToPCForClarificationTest() {
        System.out.println("Method: swipeToSendOMRWorkflowToPCForClarificationTest()");
        boolean hasDispute = false;
        String workflowID;

        //--------------Send for Clarification-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_OMR, STATUS_OVERDUE);
        inboxScreen.tapOnForAcknowledgementSubTab();
        workflowID = inboxScreen.getFirstCNAWorkflowId();
        ClarificationOptionScreen clarificationOptionScreen = inboxScreen.swipeLeftAndTapOnClarification(workflowID);
        ClarificationScreen clarificationScreen = clarificationOptionScreen.selectClarificationOption(CLARIFICATION_OPTION_PC, WORKFLOW_OMR);
        inboxScreen = clarificationScreen.clarifyWorkflow(null, null, hasDispute, WORKFLOW_OMR, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_CLARIFICATION, WORKFLOW_OMR, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyCurrActorTypeOrGroup(CLARIFICATION_OPTION_PC, WORKFLOW_OMR, workflowID));

        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_CLARIFICATION, TEST_GRP_OMR, TEST_GRP_CLARIFICATION_OTHERS})
    public void sendOMRWorkflowToAnyoneForClarificationDetailViewTest() {
        System.out.println("Method: sendOMRWorkflowToAnyoneForClarificationDetailViewTest()");
        boolean hasEscalate = true;
        String workflowID;

        //--------------Send for Clarification-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.OMRUsername01"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_OMR, STATUS_OPEN);
        inboxScreen.tapOnForAcknowledgementSubTab();
        workflowID = inboxScreen.getFirstCNAWorkflowId();
        InboxDetailViewScreen detailViewScreen = inboxScreen.tapOnWorkflow(workflowID, true);
        ClarificationOptionScreen clarificationOptionScreen = detailViewScreen.tapOnClarificationButton();
        ClarificationScreen clarificationScreen = clarificationOptionScreen.selectClarificationOption(CLARIFICATION_OPTION_SEND_TO, WORKFLOW_OMR);
        inboxScreen = clarificationScreen.clarifyWorkflow(null, prop.getProperty("uat.DelegateUsername"),
                hasEscalate, WORKFLOW_OMR, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_CLARIFICATION, WORKFLOW_OMR, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyCurrActorTypeOrGroup(CLARIFICATION_OPTION_SEND_TO, WORKFLOW_OMR, workflowID));
        inboxScreen.logout();

        //---------Login as another user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_OMR, STATUS_OPEN);
        inboxScreen.tapOnForClarificationSubTab();
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_TO_DO),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_CLARIFICATION, WORKFLOW_OMR, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));

        //----------------Submit the workflow back to OMR user-------------------

        InboxDetailViewScreen inboxDetailViewScreen = inboxScreen.tapOnWorkflow(workflowID, true);
        SubmitScreen submitScreen = inboxDetailViewScreen.tapOnSubmitButton();
        inboxScreen = submitScreen.submitWorkflow(null, WORKFLOW_OMR, 1, false);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_SUBMIT_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_ACK_POST_CLARIFICATION, WORKFLOW_OMR, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        inboxScreen.logout();

        //---------Login as OMR user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.OMRUsername01"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_OMR, STATUS_OPEN);
        inboxScreen.tapOnForAcknowledgementSubTab();
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_TO_DO),
                FAILED_MSG_FAILED_TO_SUBMIT_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_ACK_POST_CLARIFICATION, WORKFLOW_OMR, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));

        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_CLARIFICATION, TEST_GRP_OMR, TEST_GRP_CLARIFICATION_OTHERS})
    public void swipeToSendOMRWorkflowToAnyoneForClarificationTest() {
        System.out.println("Method: swipeToSendOMRWorkflowToAnyoneForClarificationTest()");
        boolean hasEscalate = false;
        String workflowID;

        //--------------Send for Clarification-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_OMR, STATUS_OVERDUE);
        inboxScreen.tapOnForAcknowledgementSubTab();
        workflowID = inboxScreen.getFirstCNAWorkflowId();
        ClarificationOptionScreen clarificationOptionScreen = inboxScreen.swipeLeftAndTapOnClarification(workflowID);
        ClarificationScreen clarificationScreen = clarificationOptionScreen.selectClarificationOption(CLARIFICATION_OPTION_SEND_TO, WORKFLOW_OMR);
        inboxScreen = clarificationScreen.clarifyWorkflow(null, prop.getProperty("uat.DelegateUsername"),
                hasEscalate, WORKFLOW_OMR, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_CLARIFICATION, WORKFLOW_OMR, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyCurrActorTypeOrGroup(CLARIFICATION_OPTION_SEND_TO, WORKFLOW_OMR, workflowID));
        inboxScreen.logout();

        //---------Login as Other user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_OMR, STATUS_OPEN);
        inboxScreen.tapOnForClarificationSubTab();
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_TO_DO),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_CLARIFICATION, WORKFLOW_OMR, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));

        //----------------Submit the workflow back to user-------------------

        SubmitScreen submitScreen = inboxScreen.swipeRightAndTapOnSubmit(workflowID);
        inboxScreen = submitScreen.submitWorkflow(null, WORKFLOW_OMR, 1, false);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_SUBMIT_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_ACK_POST_CLARIFICATION, WORKFLOW_OMR, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        inboxScreen.logout();

        //---------Login as OMR user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_OMR, STATUS_OPEN);
        inboxScreen.tapOnForAcknowledgementSubTab();
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_TO_DO),
                FAILED_MSG_FAILED_TO_SUBMIT_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_ACK_POST_CLARIFICATION, WORKFLOW_OMR, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));

        //----------------Acknowledge the workflow-------------------

        AcknowledgeScreen acknowledgeScreen = inboxScreen.swipeRightAndTapOnAcknowledge(workflowID, WORKFLOW_OMR);
        inboxScreen = acknowledgeScreen.acknowledgeOMRWorkflow(null,
                ACKNOWLEDGEMENT_CODE_HRR_DEAL, WORKFLOW_OMR, 1);
        inboxScreen.navigateToBucket(BUCKET_CLOSED);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_CLOSED),
                FAILED_MSG_FAILED_TO_ACKNOWLEDGE_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_ACKNOWLEDGED, WORKFLOW_OMR, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));

        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_CLARIFICATION_SELECTED, TEST_GRP_OMR, TEST_GRP_CLARIFICATION_OTHERS},
            dependsOnMethods = {"sendOMRWorkflowToAnyoneForClarificationDetailViewTest",
                    "swipeToSendOMRWorkflowToAnyoneForClarificationTest"})
    public void clarifySelectedOMRWorkflowToAnyoneTest() {
        System.out.println("Method: clarifySelectedOMRWorkflowToAnyoneTest()");
        boolean hasEscalate = true;
        List<String> workflowIDList;
        List<String> allWorkflowIDList;
        int workflowCount = 1;

        //--------------Send for Clarification-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.OMRUsername01"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_OMR, STATUS_OPEN);
        inboxScreen.tapOnForAcknowledgementSubTab();
        SelectMultipleWorkflowScreen selectMultipleWorkflowScreen = inboxScreen.navigateToSelectMultipleWorkflowScreen(workflowCount,
                BUCKET_TO_DO, MORE_OPTION_CLARIFY_SELECTED);
        workflowIDList = selectMultipleWorkflowScreen.selectNumberOfCNAWorkflow(workflowCount);
        ClarificationOptionScreen clarificationOptionScreen = selectMultipleWorkflowScreen.tapOnClarifySelectedScreenDoneButton();
        ClarificationScreen clarificationScreen = clarificationOptionScreen.selectClarificationOption(CLARIFICATION_OPTION_SEND_TO, WORKFLOW_OMR);
        inboxScreen = clarificationScreen.clarifyWorkflow(null, prop.getProperty("uat.DelegateUsername"),
                hasEscalate, WORKFLOW_OMR, workflowCount);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_SENT_SELECTED_WORKFLOW_FOR_CLARIFICATION);
        inboxScreen.logout();

        //---------Login as other user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_OMR, STATUS_OPEN);
        inboxScreen.tapOnForClarificationSubTab();
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_SENT_SELECTED_WORKFLOW_FOR_CLARIFICATION);


        //----------------Submit the workflow back to CNA user-------------------

        selectMultipleWorkflowScreen = inboxScreen.navigateToSelectMultipleWorkflowScreen(allWorkflowIDList.size(),
                BUCKET_TO_DO, MORE_OPTION_SUBMIT_SELECTED);
        selectMultipleWorkflowScreen.selectNumberOfWorkflow(workflowIDList);
        SubmitScreen submitScreen = selectMultipleWorkflowScreen.tapOnSubmitSelectedScreenDoneButton();
        inboxScreen = submitScreen.submitWorkflow(null, WORKFLOW_OMR, workflowCount, false);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_SUBMIT_SELECTED_WORKFLOW);
        inboxScreen.logout();

        //---------Login as OMR user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.OMRUsername01"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_OMR, STATUS_OPEN);
        inboxScreen.tapOnForAcknowledgementSubTab();
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_SUBMIT_SELECTED_WORKFLOW);

        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_CLARIFICATION_ALL, TEST_GRP_OMR, TEST_GRP_CLARIFICATION_OTHERS},
            dependsOnMethods = {"clarifySelectedOMRWorkflowToAnyoneTest"})
    public void clarifyAllOMRWorkflowToAnyoneTest() {
        System.out.println("Method: clarifyAllOMRWorkflowToAnyoneTest()");
        List<String> workflowIDList;
        List<String> allWorkflowIDList;
        boolean hasEscalate = true;

        //--------------Send for Clarification-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.OMRUsername01"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_OMR, STATUS_OPEN);
        inboxScreen.navigateToBucket(BUCKET_TO_DO);
        inboxScreen.tapOnForAcknowledgementSubTab();
        workflowIDList = inboxScreen.getAllCNAWorkflowId();
        ClarificationOptionScreen clarificationOptionScreen = inboxScreen.clarifyAllWorkflow();
        ClarificationScreen clarificationScreen = clarificationOptionScreen.selectClarificationOption(CLARIFICATION_OPTION_SEND_TO, WORKFLOW_OMR);
        clarificationScreen.clarifyWorkflow(null, prop.getProperty("uat.DelegateUsername"), hasEscalate,
                WORKFLOW_OMR, workflowIDList.size());
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_SENT_ALL_WORKFLOW_FOR_CLARIFICATION);
        inboxScreen.logout();

        //---------Login as other user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_OMR, STATUS_OPEN);
        inboxScreen.tapOnForClarificationSubTab();
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_SENT_ALL_WORKFLOW_FOR_CLARIFICATION);

        //----------------Submit the workflow back to CNA user-------------------

        SubmitScreen submitScreen = inboxScreen.submitAllWorkflow();
        inboxScreen = submitScreen.submitWorkflow(null, WORKFLOW_OMR, workflowIDList.size(), false);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_SUBMIT_ALL_WORKFLOW);
        inboxScreen.logout();

        //---------Login as OMR user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.OMRUsername01"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_OMR, STATUS_OPEN);
        inboxScreen.tapOnForAcknowledgementSubTab();
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_SUBMIT_ALL_WORKFLOW);

        System.out.println("Complete!");
    }

    //-------------------------------- CE ---------------------------------

    //------------ MTCR -------------

    @Test(groups = {TEST_GRP_CLARIFICATION, TEST_GRP_CE})
    public void mtcrClarifyCEWorkflowDetailViewTest() {
        System.out.println("Method: mtcrClarifyCEWorkflowDetailViewTest()");
        String workflowID;

        //--------------Send for Clarification-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.MTCRUsername"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CE, STATUS_OPEN);
        inboxScreen.tapOnForReviewSubTab();
        workflowID = inboxScreen.getFirstCNAWorkflowId();
        InboxDetailViewScreen detailViewScreen = inboxScreen.tapOnWorkflow(workflowID, true);
        ClarificationScreen clarificationScreen = detailViewScreen.tapOnClarificationButtonForCE();
        inboxScreen = clarificationScreen.clarifyCEWorkflow(null, WORKFLOW_CE, 1, "Limit Monitoring");
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_CLARIFICATION_WITH_LIMIT_MONITORING, WORKFLOW_CE, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        inboxScreen.logout();

        //---------Login as Limit Monitoring user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.LimitMonitoringUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CE, STATUS_OPEN);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_TO_DO),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_CLARIFICATION_WITH_LIMIT_MONITORING, WORKFLOW_CE, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));

        //----------------Submit the workflow back to MTCR user-------------------

        InboxDetailViewScreen inboxDetailViewScreen = inboxScreen.tapOnWorkflow(workflowID, true);
        SubmitScreen submitScreen = inboxDetailViewScreen.tapOnSubmitButton();
        inboxScreen = submitScreen.submitWorkflow(null, WORKFLOW_CE, 1, false);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_SUBMIT_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_MTCR_REVIEW_POST_CLARIFICATION, WORKFLOW_CE, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        inboxScreen.logout();

        //---------Login as MTCR user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.MTCRUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CE, STATUS_OPEN);
        inboxScreen.tapOnForReviewSubTab();
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_TO_DO),
                FAILED_MSG_FAILED_TO_SUBMIT_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_MTCR_REVIEW_POST_CLARIFICATION, WORKFLOW_CE, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));

        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_CLARIFICATION, TEST_GRP_CE})
    public void mtcrSwipeToClarifyCEWorkflowTest() {
        System.out.println("Method: mtcrSwipeToClarifyCEWorkflowTest()");
        String workflowID;

        //--------------Send for Clarification-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.MTCRUsername"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CE, STATUS_OPEN);
        inboxScreen.tapOnForReviewSubTab();
        workflowID = inboxScreen.getFirstCNAWorkflowId();
        ClarificationScreen clarificationScreen = inboxScreen.swipeLeftAndTapOnClarificationForCE(workflowID);
        inboxScreen = clarificationScreen.clarifyCEWorkflow(null, WORKFLOW_CE, 1, "Limit Monitoring");
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_CLARIFICATION_WITH_LIMIT_MONITORING, WORKFLOW_CE, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        inboxScreen.logout();

        //---------Login as Limit Monitoring user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.LimitMonitoringUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CE, STATUS_OPEN);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_TO_DO),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_CLARIFICATION_WITH_LIMIT_MONITORING, WORKFLOW_CE, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));

        //----------------Submit the workflow back to user-------------------

        SubmitScreen submitScreen = inboxScreen.swipeRightAndTapOnSubmit(workflowID);
        inboxScreen = submitScreen.submitWorkflow(null, WORKFLOW_CE, 1, false);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_SUBMIT_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_MTCR_REVIEW_POST_CLARIFICATION, WORKFLOW_CE, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        inboxScreen.logout();

        //---------Login as MTCR user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.MTCRUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CE, STATUS_OPEN);
        inboxScreen.tapOnForReviewSubTab();
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_TO_DO),
                FAILED_MSG_FAILED_TO_SUBMIT_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_MTCR_REVIEW_POST_CLARIFICATION, WORKFLOW_CE, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));

        //----------------Acknowledge the workflow-------------------

        AcknowledgeScreen acknowledgeScreen = inboxScreen.swipeRightAndTapOnAcknowledge(workflowID, WORKFLOW_CE);
        inboxScreen = acknowledgeScreen.reviewAndAssessWorkflow(null, CE_SEVERITY_HIGH,
                CE_POTENTIAL_LOSS_YES, WORKFLOW_CE, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_REVIEW_AND_ASSESS_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_FO_REVIEW, WORKFLOW_CE, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));

        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_CLARIFICATION, TEST_GRP_CE},
            dependsOnMethods = {"mtcrClarifyCEWorkflowDetailViewTest", "mtcrSwipeToClarifyCEWorkflowTest"})
    public void mtcrClarifySelectedCEWorkflowTest() {
        System.out.println("Method: mtcrClarifySelectedCEWorkflowTest()");
        List<String> workflowIDList;
        List<String> allWorkflowIDList;
        int workflowCount = 1;

        //--------------Send for Clarification-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.MTCRUsername"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CE, STATUS_OPEN);
        inboxScreen.tapOnForReviewSubTab();
        SelectMultipleWorkflowScreen selectMultipleWorkflowScreen = inboxScreen.navigateToSelectMultipleWorkflowScreen(workflowCount,
                BUCKET_TO_DO, MORE_OPTION_CLARIFY_SELECTED);
        workflowIDList = selectMultipleWorkflowScreen.selectNumberOfCNAWorkflow(workflowCount);
        ClarificationScreen clarificationScreen = selectMultipleWorkflowScreen.tapOnClarifySelectedScreenDoneButtonForCE();
        inboxScreen = clarificationScreen.clarifyCEWorkflow(null, WORKFLOW_CE, workflowCount, "Limit Monitoring");
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList),
                FAILED_MSG_FAILED_TO_SENT_SELECTED_WORKFLOW_FOR_CLARIFICATION);
        inboxScreen.logout();

        //---------Login as Limit Monitoring user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.LimitMonitoringUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CE, STATUS_OPEN);
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList),
                FAILED_MSG_FAILED_TO_SENT_SELECTED_WORKFLOW_FOR_CLARIFICATION);


        //----------------Submit the workflow back to MTCR user-------------------

        selectMultipleWorkflowScreen = inboxScreen.navigateToSelectMultipleWorkflowScreen(allWorkflowIDList.size(),
                BUCKET_TO_DO, MORE_OPTION_SUBMIT_SELECTED);
        selectMultipleWorkflowScreen.selectNumberOfWorkflow(workflowIDList);
        SubmitScreen submitScreen = selectMultipleWorkflowScreen.tapOnSubmitSelectedScreenDoneButton();
        inboxScreen = submitScreen.submitWorkflow(null, WORKFLOW_CE, workflowCount, false);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList),
                FAILED_MSG_FAILED_TO_SUBMIT_SELECTED_WORKFLOW);
        inboxScreen.logout();

        //---------Login as MTCR user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.MTCRUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CE, STATUS_OPEN);
        inboxScreen.tapOnForReviewSubTab();
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList),
                FAILED_MSG_FAILED_TO_SUBMIT_SELECTED_WORKFLOW);

        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_CLARIFICATION, TEST_GRP_CE}/*,
            dependsOnMethods = {"mtcrClarifySelectedCEWorkflowTest"}*/)
    public void mtcrClarifyAllCEWorkflowTest() {
        System.out.println("Method: mtcrClarifyAllCEWorkflowTest()");
        List<String> workflowIDList;
        List<String> allWorkflowIDList;

        //--------------Send for Clarification-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.MTCRUsername"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CE, STATUS_OPEN);
        inboxScreen.navigateToBucket(BUCKET_TO_DO);
        inboxScreen.tapOnForReviewSubTab();
        workflowIDList = inboxScreen.getAllCNAWorkflowId();
        ClarificationScreen clarificationScreen = inboxScreen.clarifyAllWorkflowForCE();
        inboxScreen = clarificationScreen.clarifyCEWorkflow(null, WORKFLOW_CE, workflowIDList.size(), "Limit Monitoring");
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList),
                FAILED_MSG_FAILED_TO_SENT_ALL_WORKFLOW_FOR_CLARIFICATION);
        inboxScreen.logout();

        //---------Login as Limit Monitoring user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.LimitMonitoringUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CE, STATUS_OPEN);
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList),
                FAILED_MSG_FAILED_TO_SENT_ALL_WORKFLOW_FOR_CLARIFICATION);

        //----------------Submit the workflow back to MTCR user-------------------

        SubmitScreen submitScreen = inboxScreen.submitAllWorkflow();
        inboxScreen = submitScreen.submitWorkflow(null, WORKFLOW_CE, workflowIDList.size(), false);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_SUBMIT_ALL_WORKFLOW);
        inboxScreen.logout();

        //---------Login as MTCR user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.MTCRUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CE, STATUS_OPEN);
        inboxScreen.tapOnForReviewSubTab();
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_SUBMIT_ALL_WORKFLOW);

        System.out.println("Complete!");
    }

    //------------ Dealer ------------

    @Test(groups = {TEST_GRP_CLARIFICATION, TEST_GRP_CE})
    public void dealerClarifyCEWorkflowDetailViewTest() {
        System.out.println("Method: dealerClarifyCEWorkflowDetailViewTest()");
        String workflowID;

        //--------------Send for Clarification-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DealerSupervisor"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CE, STATUS_OPEN);
        inboxScreen.tapOnForReviewSubTab();
        workflowID = inboxScreen.getFirstCNAWorkflowId();
        InboxDetailViewScreen detailViewScreen = inboxScreen.tapOnWorkflow(workflowID, true);
        ClarificationScreen clarificationScreen = detailViewScreen.tapOnClarificationButtonForCE();
        inboxScreen = clarificationScreen.clarifyCEWorkflow(null, WORKFLOW_CE, 1, "MTCR");
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_CLARIFICATION_WITH_MTCR, WORKFLOW_CE, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        inboxScreen.logout();

        //---------Login as MTCR user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.MTCRUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CE, STATUS_OPEN);
        inboxScreen.tapOnForClarificationSubTab();
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_TO_DO),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_CLARIFICATION_WITH_MTCR, WORKFLOW_CE, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));

        //----------------Submit the workflow back to Dealer user-------------------

        InboxDetailViewScreen inboxDetailViewScreen = inboxScreen.tapOnWorkflow(workflowID, true);
        SubmitOptionScreen submitOptionScreen = inboxDetailViewScreen.tapOnSubmitButtonVECE();
        SubmitScreen submitScreen = submitOptionScreen.selectSubmitOption(RESPOND_OPTION_RESPOND);
        inboxScreen = submitScreen.submitWorkflow(null, WORKFLOW_CE, 1, true);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_SUBMIT_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_FO_REVIEW_POST_CLARIFICATION, WORKFLOW_CE, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        inboxScreen.logout();

        //---------Login as Dealer user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.DealerSupervisor"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CE, STATUS_OPEN);
        inboxScreen.tapOnForReviewSubTab();
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_TO_DO),
                FAILED_MSG_FAILED_TO_SUBMIT_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_FO_REVIEW_POST_CLARIFICATION, WORKFLOW_CE, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));

        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_CLARIFICATION, TEST_GRP_CE})
    public void dealerSwipeToClarifyCEWorkflowTest() {
        System.out.println("Method: dealerSwipeToClarifyCEWorkflowTest()");
        String workflowID;

        //--------------Send for Clarification-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DealerSupervisor"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CE, STATUS_OPEN);
        inboxScreen.tapOnForReviewSubTab();
        workflowID = inboxScreen.getFirstCNAWorkflowId();
        ClarificationScreen clarificationScreen = inboxScreen.swipeLeftAndTapOnClarificationForCE(workflowID);
        inboxScreen = clarificationScreen.clarifyCEWorkflow(null, WORKFLOW_CE, 1, "MTCR");
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(
                WORKFLOW_STATUS_PENDING_CLARIFICATION_WITH_MTCR, WORKFLOW_CE, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        inboxScreen.logout();

        //---------Login as MTCR user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.MTCRUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CE, STATUS_OPEN);
        inboxScreen.tapOnForClarificationSubTab();
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_TO_DO),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(
                WORKFLOW_STATUS_PENDING_CLARIFICATION_WITH_MTCR, WORKFLOW_CE, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));

        //----------------Submit the workflow back to user-------------------

        SubmitOptionScreen submitOptionScreen = inboxScreen.swipeRightAndTapOnSubmitCE(workflowID);
        SubmitScreen submitScreen = submitOptionScreen.selectSubmitOption(RESPOND_OPTION_RESPOND);
        inboxScreen = submitScreen.submitWorkflow(null, WORKFLOW_CE, 1, true);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_SUBMIT_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(
                WORKFLOW_STATUS_PENDING_FO_REVIEW_POST_CLARIFICATION, WORKFLOW_CE, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        inboxScreen.logout();

        //---------Login as Dealer user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.DealerSupervisor"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CE, STATUS_OPEN);
        inboxScreen.tapOnForReviewSubTab();
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_TO_DO),
                FAILED_MSG_FAILED_TO_SUBMIT_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(
                WORKFLOW_STATUS_PENDING_FO_REVIEW_POST_CLARIFICATION, WORKFLOW_CE, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));

        //----------------Acknowledge the workflow-------------------

        AcknowledgeScreen acknowledgeScreen = inboxScreen.swipeRightAndTapOnAcknowledge(workflowID, WORKFLOW_CE);
        inboxScreen = acknowledgeScreen.reviewAndApproveWorkflow(null, CE_DISCIPLINARY_ACTION_DISMISSAL,
                WORKFLOW_CE, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_REVIEW_AND_ASSESS_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(
                WORKFLOW_STATUS_REVIEWED_AND_DISCIPLINARY_ACTION_TAKEN, WORKFLOW_CE, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));

        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_CLARIFICATION, TEST_GRP_CE},
            dependsOnMethods = {"dealerClarifyCEWorkflowDetailViewTest",
                    "dealerSwipeToClarifyCEWorkflowTest"})
    public void dealerClarifySelectedCEWorkflowTest() {
        System.out.println("Method: dealerClarifySelectedCEWorkflowTest()");
        List<String> workflowIDList;
        List<String> allWorkflowIDList;
        int workflowCount = 1;

        //--------------Send for Clarification-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DealerSupervisor"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CE, STATUS_OPEN);
        inboxScreen.tapOnForReviewSubTab();
        SelectMultipleWorkflowScreen selectMultipleWorkflowScreen = inboxScreen.navigateToSelectMultipleWorkflowScreen(workflowCount,
                BUCKET_TO_DO, MORE_OPTION_CLARIFY_SELECTED);
        workflowIDList = selectMultipleWorkflowScreen.selectNumberOfCNAWorkflow(workflowCount);
        ClarificationScreen clarificationScreen = selectMultipleWorkflowScreen.tapOnClarifySelectedScreenDoneButtonForCE();
        inboxScreen = clarificationScreen.clarifyCEWorkflow(null, WORKFLOW_CE, workflowCount, "MTCR");
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList),
                FAILED_MSG_FAILED_TO_SENT_SELECTED_WORKFLOW_FOR_CLARIFICATION);
        inboxScreen.logout();

        //---------Login as MTCR user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.MTCRUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CE, STATUS_OPEN);
        inboxScreen.tapOnForClarificationSubTab();
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList),
                FAILED_MSG_FAILED_TO_SENT_SELECTED_WORKFLOW_FOR_CLARIFICATION);


        //----------------Submit the workflow back to Dealer user-------------------

        selectMultipleWorkflowScreen = inboxScreen.navigateToSelectMultipleWorkflowScreen(allWorkflowIDList.size(),
                BUCKET_TO_DO, MORE_OPTION_SUBMIT_SELECTED);
        selectMultipleWorkflowScreen.selectNumberOfWorkflow(workflowIDList);
        SubmitOptionScreen submitOptionScreen = selectMultipleWorkflowScreen.tapOnRespondSelectedScreenDoneButton();
        SubmitScreen submitScreen = submitOptionScreen.selectSubmitOption(RESPOND_OPTION_RESPOND);
        inboxScreen = submitScreen.submitWorkflow(null, WORKFLOW_CE, workflowCount, true);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList),
                FAILED_MSG_FAILED_TO_SUBMIT_SELECTED_WORKFLOW);
        inboxScreen.logout();

        //---------Login as Dealer user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.DealerSupervisor"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CE, STATUS_OPEN);
        inboxScreen.tapOnForReviewSubTab();
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList),
                FAILED_MSG_FAILED_TO_SUBMIT_SELECTED_WORKFLOW);

        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_CLARIFICATION, TEST_GRP_CE}/*,
            dependsOnMethods = {"dealerClarifySelectedCEWorkflowTest"}*/)
    public void dealerClarifyAllCEWorkflowTest() {
        System.out.println("Method: dealerClarifyAllCEWorkflowTest()");
        List<String> workflowIDList;
        List<String> allWorkflowIDList;

        //--------------Send for Clarification-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DealerSupervisor"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CE, STATUS_OPEN);
        inboxScreen.navigateToBucket(BUCKET_TO_DO);
        inboxScreen.tapOnForReviewSubTab();
        workflowIDList = inboxScreen.getAllCNAWorkflowId();
        ClarificationScreen clarificationScreen = inboxScreen.clarifyAllWorkflowForCE();
        inboxScreen = clarificationScreen.clarifyCEWorkflow(null, WORKFLOW_CE, workflowIDList.size(), "MTCR");
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList),
                FAILED_MSG_FAILED_TO_SENT_ALL_WORKFLOW_FOR_CLARIFICATION);
        inboxScreen.logout();

        //---------Login as MTCR user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.MTCRUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CE, STATUS_OPEN);
        inboxScreen.tapOnForClarificationSubTab();
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList),
                FAILED_MSG_FAILED_TO_SENT_ALL_WORKFLOW_FOR_CLARIFICATION);

        //----------------Submit the workflow back to Dealer user-------------------

        //TODO Submit all will submit all the workflow in both For Review and For Clarification sub tab. FMSW-8791
        SubmitOptionScreen submitOptionScreenScreen = inboxScreen.submitAllWorkflowCE();
        SubmitScreen submitScreen = submitOptionScreenScreen.selectSubmitOption(RESPOND_OPTION_RESPOND);
        inboxScreen = submitScreen.submitWorkflow(null, WORKFLOW_CE, workflowIDList.size(), true);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_SUBMIT_ALL_WORKFLOW);
        inboxScreen.logout();

        //---------Login as Dealer user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.DealerSupervisor"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CE, STATUS_OPEN);
        inboxScreen.tapOnForReviewSubTab();
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_SUBMIT_ALL_WORKFLOW);

        System.out.println("Complete!");
    }

    //-------------------------------- VE ---------------------------------

    @Test(groups = {TEST_GRP_CLARIFICATION, TEST_GRP_VE})
    public void clarifyVEWorkflowToComplianceDetailViewTest() {
        System.out.println("Method: clarifyVEWorkflowToComplianceDetailViewTest()");
        String workflowID;

        //--------------Send for Clarification-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.VEVDOUsername"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_VE, STATUS_OPEN);
        inboxScreen.tapOnForReviewAndActionSubTab();
        workflowID = inboxScreen.getFirstWorkflowId();
        InboxDetailViewScreen detailViewScreen = inboxScreen.tapOnWorkflow(workflowID, true);
        ClarificationOptionScreen clarificationOptionScreen = detailViewScreen.tapOnClarificationButton();
        ClarificationScreen clarificationScreen = clarificationOptionScreen
                .selectClarificationOption(CLARIFICATION_OPTION_VOLCKER_COMPLIANCE, WORKFLOW_VE);
        inboxScreen = clarificationScreen.clarifyWorkflow(null, null, false, WORKFLOW_VE, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(
                WORKFLOW_STATUS_PENDING_COMPLIANCE_CLARIFICATION_UT, WORKFLOW_VE, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyCurrActorTypeOrGroup(CLARIFICATION_OPTION_VOLCKER_COMPLIANCE, WORKFLOW_VE, workflowID));

        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_CLARIFICATION, TEST_GRP_VE})
    public void swipeToClarifyVEWorkflowToComplianceTest() {
        System.out.println("Method: swipeToClarifyVEWorkflowToComplianceTest()");
        String workflowID;

        //--------------Send for Clarification-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.VEVDOUsername"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_VE, STATUS_OPEN);
        inboxScreen.tapOnForReviewAndActionSubTab();
        workflowID = inboxScreen.getFirstWorkflowId();
        ClarificationOptionScreen clarificationOptionScreen = inboxScreen.swipeLeftAndTapOnClarification(workflowID);
        ClarificationScreen clarificationScreen = clarificationOptionScreen
                .selectClarificationOption(CLARIFICATION_OPTION_VOLCKER_COMPLIANCE, WORKFLOW_VE);
        inboxScreen = clarificationScreen.clarifyWorkflow(null, null, false, WORKFLOW_VE, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_COMPLIANCE_CLARIFICATION_UT,
                WORKFLOW_VE, workflowID), FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyCurrActorTypeOrGroup(CLARIFICATION_OPTION_VOLCKER_COMPLIANCE, WORKFLOW_VE, workflowID));

        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_CLARIFICATION_SELECTED, TEST_GRP_VE})
    public void clarifyVEWorkflowToComplianceSelectedTest() {
        System.out.println("Method: clarifyVEWorkflowToComplianceSelectedTest()");
        List<String> workflowIDList;
        int workflowCount = 1;

        //--------------Send for Clarification-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.VEVDOUsername"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_VE, STATUS_OPEN);
        inboxScreen.tapOnForReviewAndActionSubTab();
        SelectMultipleWorkflowScreen selectMultipleWorkflowScreen = inboxScreen.navigateToSelectMultipleWorkflowScreen(
                workflowCount, BUCKET_TO_DO, MORE_OPTION_REQUEST_FOR_CLARIFICATION_SELECTED);
        workflowIDList = selectMultipleWorkflowScreen.selectNumberOfWorkflow(workflowCount);
        ClarificationOptionScreen clarificationOptionScreen = selectMultipleWorkflowScreen.tapOnClarifySelectedScreenDoneButton();
        ClarificationScreen clarificationScreen = clarificationOptionScreen
                .selectClarificationOption(CLARIFICATION_OPTION_VOLCKER_COMPLIANCE, WORKFLOW_VE);
        inboxScreen = clarificationScreen.clarifyWorkflow(null, null, false, WORKFLOW_VE, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowIDList.get(0), workflowCount, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowIDList.get(0)));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_COMPLIANCE_CLARIFICATION_UT,
                WORKFLOW_VE, workflowIDList.get(0)), FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS
                .replace("$1", workflowIDList.get(0)));

        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_RESPOND, TEST_GRP_VE})
    public void respondVEWorkflowToComplianceDetailViewTest() {
        System.out.println("Method: respondVEWorkflowToComplianceDetailViewTest()");
        String workflowID;
        String workflowStatus;

        //--------------Send for Clarification-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.VEVDOUsername"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_VE, STATUS_OPEN);
        inboxScreen.tapOnForClarificationSubTab();
        workflowID = inboxScreen.getFirstWorkflowId();
        InboxDetailViewScreen detailViewScreen = inboxScreen.tapOnWorkflow(workflowID, true);
        workflowStatus = detailViewScreen.getWorkflowStatusValue();
        if (WORKFLOW_STATUS_PENDING_VDO_CLARIFICATION.equals(workflowStatus)) {
            SubmitScreen submitScreen = detailViewScreen.tapOnSubmitButton();
            inboxScreen = submitScreen.submitWorkflow(null, WORKFLOW_VE, 1, false);
            inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
            Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                    FAILED_MSG_FAILED_TO_SUBMIT_WORKFLOW.replace("$1", workflowID));
            Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(
                    WORKFLOW_STATUS_PENDING_COMPLIANCE_ACTION_POST_CLARIFICATION, WORKFLOW_VE, workflowID),
                    FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        } else {
            SubmitOptionScreen submitOptionScreen = detailViewScreen.tapOnSubmitButtonVECE();
            SubmitScreen submitScreen = submitOptionScreen.selectSubmitOption(RESPOND_OPTION_EDIT_AND_RESPOND);
            inboxScreen = submitScreen.editAndRespondWorkflow(null,
                    VE_DISCIPLINARY_ACTION_COACHING_OR_COUNSELING, WORKFLOW_VE, 1);
            inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
            Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                    FAILED_MSG_FAILED_TO_SUBMIT_WORKFLOW.replace("$1", workflowID));
            Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(
                    WORKFLOW_STATUS_PENDING_COMPLIANCE_ACTION_POST_CLARIFICATION_UT, WORKFLOW_VE, workflowID),
                    FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        }
        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_RESPOND, TEST_GRP_VE})
    public void swipeToRespondVEWorkflowToComplianceTest() {
        System.out.println("Method: swipeToRespondVEWorkflowToComplianceTest()");
        String workflowID;
        String workflowStatus;

        //--------------Send for Clarification-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.VEVDOUsername"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_VE, STATUS_OPEN);
        inboxScreen.tapOnForClarificationSubTab();
        workflowID = inboxScreen.getFirstWorkflowId();

        InboxDetailViewScreen detailViewScreen = inboxScreen.tapOnWorkflow(workflowID, true);
        workflowStatus = detailViewScreen.getWorkflowStatusValue();
        detailViewScreen.tapOnBackButton();
        if (WORKFLOW_STATUS_PENDING_VDO_CLARIFICATION.equals(workflowStatus)) {
            SubmitScreen submitScreen = inboxScreen.swipeRightAndTapOnRespond(workflowID);
            inboxScreen = submitScreen.submitWorkflow(null, WORKFLOW_VE, 1, false);
            inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
            Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                    FAILED_MSG_FAILED_TO_SUBMIT_WORKFLOW.replace("$1", workflowID));
            Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(
                    WORKFLOW_STATUS_PENDING_COMPLIANCE_ACTION_POST_CLARIFICATION, WORKFLOW_VE, workflowID),
                    FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        } else {
            SubmitOptionScreen submitOptionScreen = inboxScreen.swipeRightAndTapOnRespondVE(workflowID);
            SubmitScreen submitScreen = submitOptionScreen.selectSubmitOption(RESPOND_OPTION_EDIT_AND_RESPOND);
            inboxScreen = submitScreen.editAndRespondWorkflow(null,
                    VE_DISCIPLINARY_ACTION_COACHING_OR_COUNSELING, WORKFLOW_VE, 1);
            inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
            Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                    FAILED_MSG_FAILED_TO_SUBMIT_WORKFLOW.replace("$1", workflowID));
            Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(
                    WORKFLOW_STATUS_PENDING_COMPLIANCE_ACTION_POST_CLARIFICATION_UT, WORKFLOW_VE, workflowID),
                    FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        }

        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_RESPOND_SELECTED, TEST_GRP_VE})
    public void respondSelectedVEWorkflowToComplianceTest() {
        System.out.println("Method: respondSelectedVEWorkflowToComplianceTest()");
        String workflowID;
        String workflowStatus;

        //--------------Send for Clarification-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.VEVDOUsername"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_VE, STATUS_OPEN);
        inboxScreen.tapOnForClarificationSubTab();
        workflowID = inboxScreen.getFirstWorkflowId();
        InboxDetailViewScreen detailViewScreen = inboxScreen.tapOnWorkflow(workflowID, true);
        workflowStatus = detailViewScreen.getWorkflowStatusValue();
        detailViewScreen.tapOnBackButton();
        SelectMultipleWorkflowScreen selectMultipleWorkflowScreen = inboxScreen.navigateToSelectMultipleWorkflowScreen(
                1, BUCKET_TO_DO, MORE_OPTION_RESPOND_SELECTED);
        selectMultipleWorkflowScreen.selectNumberOfWorkflow(workflowID);
        if (WORKFLOW_STATUS_PENDING_VDO_CLARIFICATION.equals(workflowStatus)) {
            SubmitScreen submitScreen = selectMultipleWorkflowScreen.tapOnSubmitSelectedScreenDoneButton();
            inboxScreen = submitScreen.submitWorkflow(null, WORKFLOW_VE, 1, false);
            inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
            Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                    FAILED_MSG_FAILED_TO_SUBMIT_WORKFLOW.replace("$1", workflowID));
            Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(
                    WORKFLOW_STATUS_PENDING_COMPLIANCE_ACTION_POST_CLARIFICATION, WORKFLOW_VE, workflowID),
                    FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        } else {
            SubmitOptionScreen submitOptionScreen = selectMultipleWorkflowScreen.tapOnRespondSelectedScreenDoneButton();
            SubmitScreen submitScreen = submitOptionScreen.selectSubmitOption(RESPOND_OPTION_EDIT_AND_RESPOND);
            inboxScreen = submitScreen.editAndRespondWorkflow(null,
                    VE_DISCIPLINARY_ACTION_COACHING_OR_COUNSELING, WORKFLOW_VE, 1);
            inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
            Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                    FAILED_MSG_FAILED_TO_SUBMIT_WORKFLOW.replace("$1", workflowID));
            Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(
                    WORKFLOW_STATUS_PENDING_COMPLIANCE_ACTION_POST_CLARIFICATION_UT, WORKFLOW_VE, workflowID),
                    FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        }

        System.out.println("Complete!");
    }

    //--------------------------- IPV/FVA ------------------------------

    @Test(groups = {TEST_GRP_CLARIFICATION, TEST_GRP_IPV_FVA, TEST_GRP_CLARIFICATION_VC})
    public void swipeToSendIPVWorkflowToVCForClarificationTest() {
        System.out.println("Method: swipeToSendIPVWorkflowToVCForClarificationTest()");
        boolean hasDispute = false;
        String workflowID;

        //--------------Send for Clarification-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername01"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_IPV, STATUS_OPEN);
        inboxScreen.tapOnForAcknowledgementSubTab();
        workflowID = inboxScreen.getFirstCNAWorkflowId();
        ClarificationOptionScreen clarificationOptionScreen = inboxScreen.swipeLeftAndTapOnClarification(workflowID);
        ClarificationScreen clarificationScreen = clarificationOptionScreen.selectClarificationOption(CLARIFICATION_OPTION_VALUATION_CONTROL_USER, WORKFLOW_IPV);
        inboxScreen = clarificationScreen.clarifyWorkflow(null, null, hasDispute, WORKFLOW_IPV, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_CLARIFICATION, WORKFLOW_IPV, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyCurrActorTypeOrGroup(CLARIFICATION_OPTION_VALUATION_CONTROL_USER, WORKFLOW_IPV, workflowID));

        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_CLARIFICATION, TEST_GRP_IPV_FVA, TEST_GRP_CLARIFICATION_OTHERS})
    public void sendIPVWorkflowToAnyoneForClarificationDetailViewTest() {
        System.out.println("Method: sendIPVWorkflowToAnyoneForClarificationDetailViewTest()");
        boolean hasEscalate = true;
        String workflowID;

        //--------------Send for Clarification-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername01"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_IPV, STATUS_OPEN);
        inboxScreen.tapOnForAcknowledgementSubTab();
        workflowID = inboxScreen.getFirstCNAWorkflowId();
        InboxDetailViewScreen detailViewScreen = inboxScreen.tapOnWorkflow(workflowID, true);
        ClarificationOptionScreen clarificationOptionScreen = detailViewScreen.tapOnClarificationButton();
        ClarificationScreen clarificationScreen = clarificationOptionScreen.selectClarificationOption(CLARIFICATION_OPTION_SEND_TO, WORKFLOW_IPV);
        inboxScreen = clarificationScreen.clarifyWorkflow(null, prop.getProperty("uat.DelegateUsername"),
                hasEscalate, WORKFLOW_CNA, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_CLARIFICATION, WORKFLOW_IPV, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyCurrActorTypeOrGroup(CLARIFICATION_OPTION_SEND_TO, WORKFLOW_IPV, workflowID));
        inboxScreen.logout();

        //---------Login as another user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_IPV, STATUS_OPEN);
        inboxScreen.tapOnForClarificationSubTab();
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_TO_DO),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_CLARIFICATION, WORKFLOW_IPV, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));

        //----------------Submit the workflow back to FVA user-------------------

        InboxDetailViewScreen inboxDetailViewScreen = inboxScreen.tapOnWorkflow(workflowID, true);
        SubmitScreen submitScreen = inboxDetailViewScreen.tapOnSubmitButton();
        inboxScreen = submitScreen.submitWorkflow(null, WORKFLOW_IPV, 1, false);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_SUBMIT_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_ACK_POST_CLARIFICATION, WORKFLOW_IPV, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        inboxScreen.logout();

        //---------Login as FVA user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.FOUsername01"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_IPV, STATUS_OPEN);
        inboxScreen.tapOnForAcknowledgementSubTab();
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_TO_DO),
                FAILED_MSG_FAILED_TO_SUBMIT_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_ACK_POST_CLARIFICATION, WORKFLOW_IPV, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));

        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_CLARIFICATION, TEST_GRP_IPV_FVA, TEST_GRP_CLARIFICATION_OTHERS})
    public void swipeToSendIPVWorkflowToAnyoneForClarificationTest() {
        System.out.println("Method: swipeToSendIPVWorkflowToAnyoneForClarificationTest()");
        boolean hasEscalate = false;
        String workflowID;

        //--------------Send for Clarification-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername01"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_IPV, STATUS_OPEN);
        inboxScreen.tapOnForAcknowledgementSubTab();
        workflowID = inboxScreen.getFirstCNAWorkflowId();
        ClarificationOptionScreen clarificationOptionScreen = inboxScreen.swipeLeftAndTapOnClarification(workflowID);
        ClarificationScreen clarificationScreen = clarificationOptionScreen.selectClarificationOption(CLARIFICATION_OPTION_SEND_TO, WORKFLOW_IPV);
        inboxScreen = clarificationScreen.clarifyWorkflow(null, prop.getProperty("uat.DelegateUsername"),
                hasEscalate, WORKFLOW_IPV, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_CLARIFICATION, WORKFLOW_IPV, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyCurrActorTypeOrGroup(CLARIFICATION_OPTION_SEND_TO, WORKFLOW_IPV, workflowID));
        inboxScreen.logout();

        //---------Login as Other user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_IPV, STATUS_OPEN);
        inboxScreen.tapOnForClarificationSubTab();
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_TO_DO),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_CLARIFICATION, WORKFLOW_IPV, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));

        //----------------Submit the workflow back to user-------------------

        SubmitScreen submitScreen = inboxScreen.swipeRightAndTapOnSubmit(workflowID);
        inboxScreen = submitScreen.submitWorkflow(null, WORKFLOW_IPV, 1, false);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_SUBMIT_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_ACK_POST_CLARIFICATION, WORKFLOW_IPV, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        inboxScreen.logout();

        //---------Login as FVA user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.FOUsername01"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_IPV, STATUS_OPEN);
        inboxScreen.tapOnForAcknowledgementSubTab();
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_TO_DO),
                FAILED_MSG_FAILED_TO_SUBMIT_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_ACK_POST_CLARIFICATION, WORKFLOW_IPV, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));

        //----------------Acknowledge the workflow-------------------

        AcknowledgeScreen acknowledgeScreen = inboxScreen.swipeRightAndTapOnAcknowledge(workflowID, WORKFLOW_IPV);
        inboxScreen = acknowledgeScreen.acknowledgeWorkflow(null, WORKFLOW_IPV, 1);
        inboxScreen.navigateToBucket(BUCKET_CLOSED);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_CLOSED),
                FAILED_MSG_FAILED_TO_ACKNOWLEDGE_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_ACKNOWLEDGED, WORKFLOW_IPV, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));

        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_CLARIFICATION_SELECTED, TEST_GRP_IPV_FVA, TEST_GRP_CLARIFICATION_OTHERS},
            dependsOnMethods = {"swipeToSendIPVWorkflowToAnyoneForClarificationTest",
                    "sendIPVWorkflowToAnyoneForClarificationDetailViewTest"})
    public void clarifySelectedIPVWorkflowToAnyoneTest() {
        System.out.println("Method: clarifySelectedIPVWorkflowToAnyoneTest()");
        boolean hasEscalate = true;
        List<String> workflowIDList;
        List<String> allWorkflowIDList;
        int workflowCount = 1;

        //--------------Send for Clarification-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername01"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_IPV, STATUS_OPEN);
        inboxScreen.tapOnForAcknowledgementSubTab();
        SelectMultipleWorkflowScreen selectMultipleWorkflowScreen = inboxScreen.navigateToSelectMultipleWorkflowScreen(workflowCount,
                BUCKET_TO_DO, MORE_OPTION_CLARIFY_SELECTED);
        workflowIDList = selectMultipleWorkflowScreen.selectNumberOfCNAWorkflow(workflowCount);
        ClarificationOptionScreen clarificationOptionScreen = selectMultipleWorkflowScreen.tapOnClarifySelectedScreenDoneButton();
        ClarificationScreen clarificationScreen = clarificationOptionScreen.selectClarificationOption(CLARIFICATION_OPTION_SEND_TO, WORKFLOW_IPV);
        inboxScreen = clarificationScreen.clarifyWorkflow(null, prop.getProperty("uat.DelegateUsername"),
                hasEscalate, WORKFLOW_IPV, workflowCount);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList),
                FAILED_MSG_FAILED_TO_SENT_SELECTED_WORKFLOW_FOR_CLARIFICATION);
        inboxScreen.logout();

        //---------Login as other user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_IPV, STATUS_OPEN);
        inboxScreen.tapOnForClarificationSubTab();
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList),
                FAILED_MSG_FAILED_TO_SENT_SELECTED_WORKFLOW_FOR_CLARIFICATION);


        //----------------Submit the workflow back to FVA user-------------------

        selectMultipleWorkflowScreen = inboxScreen.navigateToSelectMultipleWorkflowScreen(allWorkflowIDList.size(),
                BUCKET_TO_DO, MORE_OPTION_SUBMIT_SELECTED);
        selectMultipleWorkflowScreen.selectNumberOfWorkflow(workflowIDList);
        SubmitScreen submitScreen = selectMultipleWorkflowScreen.tapOnSubmitSelectedScreenDoneButton();
        inboxScreen = submitScreen.submitWorkflow(null, WORKFLOW_IPV, workflowCount, false);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_SUBMIT_SELECTED_WORKFLOW);
        inboxScreen.logout();

        //---------Login as FVA user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.FOUsername01"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_IPV, STATUS_OPEN);
        inboxScreen.tapOnForAcknowledgementSubTab();
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_SUBMIT_SELECTED_WORKFLOW);

        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_CLARIFICATION_ALL, TEST_GRP_IPV_FVA, TEST_GRP_CLARIFICATION_OTHERS},
            dependsOnMethods = {"clarifySelectedIPVWorkflowToAnyoneTest"})
    public void clarifyAllIPVWorkflowToAnyoneTest() {
        System.out.println("Method: clarifyAllIPVWorkflowToAnyoneTest()");
        List<String> workflowIDList;
        List<String> allWorkflowIDList;
        boolean hasEscalate = true;

        //--------------Send for Clarification-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername01"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_IPV, STATUS_OPEN);
        inboxScreen.navigateToBucket(BUCKET_TO_DO);
        inboxScreen.tapOnForAcknowledgementSubTab();
        workflowIDList = inboxScreen.getAllCNAWorkflowId();
        ClarificationOptionScreen clarificationOptionScreen = inboxScreen.clarifyAllWorkflow();
        ClarificationScreen clarificationScreen = clarificationOptionScreen.selectClarificationOption(CLARIFICATION_OPTION_SEND_TO, WORKFLOW_IPV);
        clarificationScreen.clarifyWorkflow(null, prop.getProperty("uat.DelegateUsername"), hasEscalate,
                WORKFLOW_IPV, workflowIDList.size());
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList),
                FAILED_MSG_FAILED_TO_SENT_ALL_WORKFLOW_FOR_CLARIFICATION);
        inboxScreen.logout();

        //---------Login as other user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_IPV, STATUS_OPEN);
        inboxScreen.tapOnForClarificationSubTab();
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList),
                FAILED_MSG_FAILED_TO_SENT_ALL_WORKFLOW_FOR_CLARIFICATION);

        //----------------Submit the workflow back to FVA user-------------------

        SubmitScreen submitScreen = inboxScreen.submitAllWorkflow();
        inboxScreen = submitScreen.submitWorkflow(null, WORKFLOW_IPV, workflowIDList.size(), false);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_SUBMIT_ALL_WORKFLOW);
        inboxScreen.logout();

        //---------Login as FVA user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.FOUsername01"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_IPV, STATUS_OPEN);
        inboxScreen.tapOnForAcknowledgementSubTab();
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_SUBMIT_ALL_WORKFLOW);

        System.out.println("Complete!");
    }

    //--------------------------- GMR/GT ------------------------------

    @Test(groups = {TEST_GRP_CLARIFICATION, TEST_GRP_GT_GMR, TEST_GRP_CLARIFICATION_MRO_OR_MTCR_OR_GT})
    public void swipeToSendGMRWorkflowToMROOrMTCROrGTForClarificationTest() {
        System.out.println("Method: swipeToSendGMRWorkflowToMROOrMTCROrGTForClarificationTest()");
        boolean hasDispute = false;
        String workflowID;

        //--------------Send for Clarification-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername01"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_GMR, STATUS_OPEN);
        inboxScreen.tapOnForApprovalSubTab();
        workflowID = inboxScreen.getFirstCNAWorkflowId();
        ClarificationOptionScreen clarificationOptionScreen = inboxScreen.swipeLeftAndTapOnClarification(workflowID);
        ClarificationScreen clarificationScreen = clarificationOptionScreen.selectClarificationOption(CLARIFICATION_OPTION_MRO_MTRC_GT, WORKFLOW_GMR);
        inboxScreen = clarificationScreen.clarifyWorkflow(null, null, hasDispute, WORKFLOW_GMR, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_CLARIFICATION, WORKFLOW_GMR, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyCurrActorTypeOrGroup(CLARIFICATION_OPTION_MRO_MTRC_GT, WORKFLOW_GMR, workflowID));

        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_CLARIFICATION, TEST_GRP_GT_GMR, TEST_GRP_CLARIFICATION_OTHERS})
    public void sendGMRWorkflowToAnyoneForClarificationDetailViewTest() {
        System.out.println("Method: sendGMRWorkflowToAnyoneForClarificationDetailViewTest()");
        boolean hasEscalate = true;
        String workflowID;

        //--------------Send for Clarification-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_GMR, STATUS_OPEN);
        inboxScreen.tapOnForApprovalSubTab();
        workflowID = inboxScreen.getFirstCNAWorkflowId();
        InboxDetailViewScreen detailViewScreen = inboxScreen.tapOnWorkflow(workflowID, true);
        ClarificationOptionScreen clarificationOptionScreen = detailViewScreen.tapOnClarificationButton();
        ClarificationScreen clarificationScreen = clarificationOptionScreen.selectClarificationOption(CLARIFICATION_OPTION_SEND_TO, WORKFLOW_GMR);
        inboxScreen = clarificationScreen.clarifyWorkflow(null, prop.getProperty("uat.DelegateUsername"),
                hasEscalate, WORKFLOW_GMR, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_CLARIFICATION, WORKFLOW_GMR, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyCurrActorTypeOrGroup(CLARIFICATION_OPTION_SEND_TO, WORKFLOW_GMR, workflowID));
        inboxScreen.logout();

        //---------Login as another user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_GMR, STATUS_OPEN);
        inboxScreen.tapOnForClarificationSubTab();
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_TO_DO),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_CLARIFICATION, WORKFLOW_GMR, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));

        //----------------Submit the workflow back to GMR user-------------------

        InboxDetailViewScreen inboxDetailViewScreen = inboxScreen.tapOnWorkflow(workflowID, true);
        SubmitScreen submitScreen = inboxDetailViewScreen.tapOnSubmitButton();
        inboxScreen = submitScreen.submitWorkflow(null, WORKFLOW_GMR, 1, false);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_SUBMIT_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_APPROVAL_POST_CLARIFICATION, WORKFLOW_GMR, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        inboxScreen.logout();

        //---------Login as GMR user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_GMR, STATUS_OPEN);
        inboxScreen.tapOnForApprovalSubTab();
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_TO_DO),
                FAILED_MSG_FAILED_TO_SUBMIT_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_APPROVAL_POST_CLARIFICATION, WORKFLOW_GMR, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));

        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_CLARIFICATION, TEST_GRP_GT_GMR, TEST_GRP_CLARIFICATION_OTHERS})
    public void swipeToSendGMRWorkflowToAnyoneForClarificationTest() {
        System.out.println("Method: swipeToSendGMRWorkflowToAnyoneForClarificationTest()");
        boolean hasEscalate = false;
        String workflowID;

        //--------------Send for Clarification-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername01"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_GMR, STATUS_OPEN);
        inboxScreen.tapOnForApprovalSubTab();
        workflowID = inboxScreen.getFirstCNAWorkflowId();
        ClarificationOptionScreen clarificationOptionScreen = inboxScreen.swipeLeftAndTapOnClarification(workflowID);
        ClarificationScreen clarificationScreen = clarificationOptionScreen.selectClarificationOption(CLARIFICATION_OPTION_SEND_TO, WORKFLOW_GMR);
        inboxScreen = clarificationScreen.clarifyWorkflow(null, prop.getProperty("uat.DelegateUsername"),
                hasEscalate, WORKFLOW_GMR, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_CLARIFICATION, WORKFLOW_GMR, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyCurrActorTypeOrGroup(CLARIFICATION_OPTION_SEND_TO, WORKFLOW_GMR, workflowID));
        inboxScreen.logout();

        //---------Login as Other user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_GMR, STATUS_OPEN);
        inboxScreen.tapOnForClarificationSubTab();
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_TO_DO),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_CLARIFICATION, WORKFLOW_GMR, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));

        //----------------Submit the workflow back to user-------------------

        SubmitScreen submitScreen = inboxScreen.swipeRightAndTapOnSubmit(workflowID);
        inboxScreen = submitScreen.submitWorkflow(null, WORKFLOW_GMR, 1, false);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_SUBMIT_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_APPROVAL_POST_CLARIFICATION, WORKFLOW_GMR, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        inboxScreen.logout();

        //---------Login as GMR user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.FOUsername01"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_GMR, STATUS_OPEN);
        inboxScreen.tapOnForApprovalSubTab();
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_TO_DO),
                FAILED_MSG_FAILED_TO_SUBMIT_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_APPROVAL_POST_CLARIFICATION, WORKFLOW_GMR, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));

        //----------------Acknowledge the workflow-------------------

        AcknowledgeScreen acknowledgeScreen = inboxScreen.swipeRightAndTapOnAcknowledge(workflowID, WORKFLOW_GMR);
        inboxScreen = acknowledgeScreen.acknowledgeWorkflow(null, WORKFLOW_GMR, 1);
        inboxScreen.navigateToBucket(BUCKET_CLOSED);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_CLOSED),
                FAILED_MSG_FAILED_TO_ACKNOWLEDGE_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_APPROVED, WORKFLOW_GMR, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));

        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_CLARIFICATION_SELECTED, TEST_GRP_GT_GMR, TEST_GRP_CLARIFICATION_OTHERS},
            dependsOnMethods = {"swipeToSendGMRWorkflowToAnyoneForClarificationTest",
                    "sendGMRWorkflowToAnyoneForClarificationDetailViewTest"})
    public void clarifySelectedGMRWorkflowToAnyoneTest() {
        System.out.println("Method: clarifySelectedGMRWorkflowToAnyoneTest()");
        boolean hasEscalate = true;
        List<String> workflowIDList;
        List<String> allWorkflowIDList;
        int workflowCount = 1;

        //--------------Send for Clarification-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername01"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_GMR, STATUS_OVERDUE);
        inboxScreen.tapOnForApprovalSubTab();
        SelectMultipleWorkflowScreen selectMultipleWorkflowScreen = inboxScreen.navigateToSelectMultipleWorkflowScreen(workflowCount,
                BUCKET_TO_DO, MORE_OPTION_CLARIFY_SELECTED);
        workflowIDList = selectMultipleWorkflowScreen.selectNumberOfCNAWorkflow(workflowCount);
        ClarificationOptionScreen clarificationOptionScreen = selectMultipleWorkflowScreen.tapOnClarifySelectedScreenDoneButton();
        ClarificationScreen clarificationScreen = clarificationOptionScreen.selectClarificationOption(CLARIFICATION_OPTION_SEND_TO, WORKFLOW_GMR);
        inboxScreen = clarificationScreen.clarifyWorkflow(null, prop.getProperty("uat.DelegateUsername"),
                hasEscalate, WORKFLOW_GMR, workflowCount);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList),
                FAILED_MSG_FAILED_TO_SENT_SELECTED_WORKFLOW_FOR_CLARIFICATION);
        inboxScreen.logout();

        //---------Login as other user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_GMR, STATUS_OPEN);
        inboxScreen.tapOnForClarificationSubTab();
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList),
                FAILED_MSG_FAILED_TO_SENT_SELECTED_WORKFLOW_FOR_CLARIFICATION);


        //----------------Submit the workflow back to GMR user-------------------

        selectMultipleWorkflowScreen = inboxScreen.navigateToSelectMultipleWorkflowScreen(allWorkflowIDList.size(),
                BUCKET_TO_DO, MORE_OPTION_SUBMIT_SELECTED);
        selectMultipleWorkflowScreen.selectNumberOfWorkflow(workflowIDList);
        SubmitScreen submitScreen = selectMultipleWorkflowScreen.tapOnSubmitSelectedScreenDoneButton();
        inboxScreen = submitScreen.submitWorkflow(null, WORKFLOW_GMR, workflowCount, false);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList),
                FAILED_MSG_FAILED_TO_SUBMIT_SELECTED_WORKFLOW);
        inboxScreen.logout();

        //---------Login as GMR user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.FOUsername01"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_GMR, STATUS_OPEN);
        inboxScreen.tapOnForApprovalSubTab();
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList),
                FAILED_MSG_FAILED_TO_SUBMIT_SELECTED_WORKFLOW);

        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_CLARIFICATION_ALL, TEST_GRP_GT_GMR, TEST_GRP_CLARIFICATION_OTHERS},
            dependsOnMethods = {"clarifySelectedGMRWorkflowToAnyoneTest"})
    public void clarifyAllGMRWorkflowToAnyoneTest() {
        System.out.println("Method: clarifyAllGMRWorkflowToAnyoneTest()");
        List<String> workflowIDList;
        List<String> allWorkflowIDList;
        boolean hasEscalate = true;

        //--------------Send for Clarification-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_GMR, STATUS_OVERDUE);
        inboxScreen.navigateToBucket(BUCKET_TO_DO);
        inboxScreen.tapOnForApprovalSubTab();
        workflowIDList = inboxScreen.getAllCNAWorkflowId();
        ClarificationOptionScreen clarificationOptionScreen = inboxScreen.clarifyAllWorkflow();
        ClarificationScreen clarificationScreen = clarificationOptionScreen.selectClarificationOption(CLARIFICATION_OPTION_SEND_TO, WORKFLOW_GMR);
        clarificationScreen.clarifyWorkflow(null, prop.getProperty("uat.DelegateUsername"), hasEscalate,
                WORKFLOW_GMR, workflowIDList.size());
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList),
                FAILED_MSG_FAILED_TO_SENT_ALL_WORKFLOW_FOR_CLARIFICATION);
        inboxScreen.logout();

        //---------Login as other user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_GMR, STATUS_OPEN);
        inboxScreen.tapOnForClarificationSubTab();
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList),
                FAILED_MSG_FAILED_TO_SENT_ALL_WORKFLOW_FOR_CLARIFICATION);

        //----------------Submit the workflow back to GMR user-------------------

        SubmitScreen submitScreen = inboxScreen.submitAllWorkflow();
        inboxScreen = submitScreen.submitWorkflow(null, WORKFLOW_GMR, workflowIDList.size(), false);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_SUBMIT_ALL_WORKFLOW);
        inboxScreen.logout();

        //---------Login as GMR user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_GMR, STATUS_OPEN);
        inboxScreen.tapOnForApprovalSubTab();
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_SUBMIT_ALL_WORKFLOW);

        System.out.println("Complete!");
    }
}
