package com.scb.fmsw.mobile.test;

import com.scb.fmsw.mobile.WorkflowConstants;
import com.scb.fmsw.mobile.base.BaseTest;
import com.scb.fmsw.mobile.screen.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.nio.file.StandardOpenOption;
import java.util.List;

public class ClarificationTest extends BaseTest {

    //next time need to check for prev actor comment, isDisputed?, isEscalated?, workflow status
    //create test method for CNA, OMR, PNL, GT/GMR, IPV/FVA

    //-------------------------------- CNA ---------------------------------

    @Test(groups = {TEST_GRP_CLARIFICATION, TEST_GRP_CNA, TEST_GRP_CLARIFICATION_PC})
    public void sendCNAWorkflowToPCForClarificationDetailViewTest() {
        System.out.println("Method: sendCNAWorkflowToPCForClarificationDetailViewTest()");
        boolean hasDispute = true;
        String workflowID;

        //--------------Send for Clarification-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CNA, STATUS_OVERDUE);
        inboxScreen.navigateToBucket(BUCKET_TO_REVIEW);
        inboxScreen.tapOnForAcknowledgementSubTab();
        workflowID = inboxScreen.getFirstCNAWorkflowId();
        InboxDetailViewScreen detailViewScreen = inboxScreen.tapOnWorkflow(workflowID);
        ClarificationOptionScreen clarificationOptionScreen = detailViewScreen.tapOnClarificationButton();
        ClarificationScreen clarificationScreen = clarificationOptionScreen.selectClarificationOption(CLARIFICATION_OPTION_PC);
        inboxScreen = clarificationScreen.clarifyWorkflow(null, null, hasDispute, WORKFLOW_CNA, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_CLARIFICATION, WORKFLOW_CNA, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        inboxScreen.logout();

        //---------Login as GBS user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.GBSUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CNA, STATUS_OPEN);
        inboxScreen.tapOnForClarificationSubTab();
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_TO_DO),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_CLARIFICATION, WORKFLOW_CNA, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));

        //----------------Submit the workflow back to user-------------------

        InboxDetailViewScreen inboxDetailViewScreen = inboxScreen.tapOnWorkflow(workflowID);
        SubmitScreen submitScreen = inboxDetailViewScreen.tapOnSubmitButton();
        inboxScreen = submitScreen.submitWorkflow(null, WORKFLOW_CNA, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_SUBMIT_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_ACK_POST_CLARIFICATION, WORKFLOW_CNA, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        inboxScreen.logout();

        //---------Login as CNA user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CNA, STATUS_OPEN);
        inboxScreen.tapOnForAcknowledgementSubTab();
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_TO_DO),
                FAILED_MSG_FAILED_TO_SUBMIT_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_ACK_POST_CLARIFICATION, WORKFLOW_CNA, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));

        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_CLARIFICATION, TEST_GRP_CNA, TEST_GRP_CLARIFICATION_LM})
    public void sendCNAWorkflowToLMForClarificationDetailViewTest() {
        System.out.println("Method: sendCNAWorkflowToLMForClarificationDetailViewTest()");
        boolean hasDispute = true;
        String workflowID;

        //--------------Send for Clarification-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CNA, STATUS_OVERDUE);
        inboxScreen.navigateToBucket(BUCKET_TO_REVIEW);
        inboxScreen.tapOnForAcknowledgementSubTab();
        workflowID = inboxScreen.getFirstCNAWorkflowId();
        InboxDetailViewScreen detailViewScreen = inboxScreen.tapOnWorkflow(workflowID);
        ClarificationOptionScreen clarificationOptionScreen = detailViewScreen.tapOnClarificationButton();
        ClarificationScreen clarificationScreen = clarificationOptionScreen.selectClarificationOption(CLARIFICATION_OPTION_CNA_LM);
        inboxScreen = clarificationScreen.clarifyWorkflow(null, null, hasDispute, WORKFLOW_CNA, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_CLARIFICATION, WORKFLOW_CNA, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));

        //---------Login as another user to check if workflow is there-------

        //This part will not work as the workflow will not be able to send out
        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_CLARIFICATION, TEST_GRP_CNA, TEST_GRP_CLARIFICATION_PERFORMER})
    public void sendCNAWorkflowToCNAPerformerForClarificationDetailViewTest() {
        System.out.println("Method: sendCNAWorkflowToCNAPerformerForClarificationDetailViewTest()");
        boolean hasDispute = false;
        String workflowID;

        //--------------Send for Clarification-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CNA, STATUS_OVERDUE);
        inboxScreen.navigateToBucket(BUCKET_TO_REVIEW);
        inboxScreen.tapOnForAcknowledgementSubTab();
        workflowID = inboxScreen.getFirstCNAWorkflowId();
        InboxDetailViewScreen detailViewScreen = inboxScreen.tapOnWorkflow(workflowID);
        ClarificationOptionScreen clarificationOptionScreen = detailViewScreen.tapOnClarificationButton();
        ClarificationScreen clarificationScreen = clarificationOptionScreen.selectClarificationOption(CLARIFICATION_OPTION_CNA_PERFORMER);
        inboxScreen = clarificationScreen.clarifyWorkflow(null, null, hasDispute, WORKFLOW_CNA, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_CLARIFICATION, WORKFLOW_CNA, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));

        //---------Login as another user to check if workflow is there-------

        //This part will not work as the workflow will not be able to send out
        //TODO need to write code for this, need to get the cna performer id from detail view as well
        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_CLARIFICATION, TEST_GRP_CNA, TEST_GRP_CLARIFICATION_MO})
    public void sendCNAWorkflowToMOForClarificationDetailViewTest() {
        System.out.println("Method: sendCNAWorkflowToMOForClarificationDetailViewTest()");
        boolean hasDispute = false;
        String workflowID;

        //--------------Send for Clarification-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CNA, STATUS_OVERDUE);
        inboxScreen.navigateToBucket(BUCKET_TO_REVIEW);
        inboxScreen.tapOnForAcknowledgementSubTab();
        workflowID = inboxScreen.getFirstCNAWorkflowId();
        InboxDetailViewScreen detailViewScreen = inboxScreen.tapOnWorkflow(workflowID);
        ClarificationOptionScreen clarificationOptionScreen = detailViewScreen.tapOnClarificationButton();
        ClarificationScreen clarificationScreen = clarificationOptionScreen.selectClarificationOption(CLARIFICATION_OPTION_MO);
        inboxScreen = clarificationScreen.clarifyWorkflow(null, null, hasDispute, WORKFLOW_CNA, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_CLARIFICATION, WORKFLOW_CNA, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        inboxScreen.logout();

        //There is MO Group now therefore it wont go to MO Admin
        /*//---------Login as MO Admin user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.MOAdminUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CNA, STATUS_OPEN);
        inboxScreen.navigateToBucket(BUCKET_TO_DO);
        inboxScreen.tapOnForExceptionSubTab();
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_TO_DO),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));

        //----------------Reassign the workflow to MO user-------------------

        InboxDetailViewScreen inboxDetailViewScreen = inboxScreen.tapOnWorkflow(workflowID);
        ReassignScreen reassignScreen = inboxDetailViewScreen.tapOnReassignButton();
        inboxScreen = reassignScreen.reassignWorkflow(prop.getProperty("uat.MOUsername"), WORKFLOW_CNA, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_REASSIGN_WORKFLOW.replace("$1", workflowID));
        inboxScreen.logout();*/

        //---------Login as the MO user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.MOUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CNA, STATUS_OPEN);
        inboxScreen.tapOnForClarificationSubTab();
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_TO_DO),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_CLARIFICATION, WORKFLOW_CNA, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));

        //----------------Submit the workflow back to CNA user-------------------

        InboxDetailViewScreen inboxDetailViewScreen = inboxScreen.tapOnWorkflow(workflowID);
        SubmitScreen submitScreen = inboxDetailViewScreen.tapOnSubmitButton();
        inboxScreen = submitScreen.submitWorkflow(null, WORKFLOW_CNA, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_SUBMIT_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_ACK_POST_CLARIFICATION, WORKFLOW_CNA, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        inboxScreen.logout();

        //---------Login as the CNA user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CNA, STATUS_OPEN);
        inboxScreen.tapOnForAcknowledgementSubTab();
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_TO_DO),
                FAILED_MSG_FAILED_TO_SUBMIT_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_ACK_POST_CLARIFICATION, WORKFLOW_CNA, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));

        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_CLARIFICATION, TEST_GRP_CNA, TEST_GRP_CLARIFICATION_OTHERS})
    public void sendCNAWorkflowToAnyoneForClarificationDetailViewTest() {
        System.out.println("Method: sendCNAWorkflowToAnyoneForClarificationDetailViewTest()");
        boolean hasEscalate = true;
        String workflowID;

        //--------------Send for Clarification-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername01"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CNA, STATUS_OVERDUE);
        inboxScreen.navigateToBucket(BUCKET_TO_REVIEW);
        inboxScreen.tapOnForAcknowledgementSubTab();
        workflowID = inboxScreen.getFirstCNAWorkflowId();
        InboxDetailViewScreen detailViewScreen = inboxScreen.tapOnWorkflow(workflowID);
        ClarificationOptionScreen clarificationOptionScreen = detailViewScreen.tapOnClarificationButton();
        ClarificationScreen clarificationScreen = clarificationOptionScreen.selectClarificationOption(CLARIFICATION_OPTION_SEND_TO);
        inboxScreen = clarificationScreen.clarifyWorkflow(null, prop.getProperty("uat.DelegateUsername"),
                hasEscalate, WORKFLOW_CNA, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_CLARIFICATION, WORKFLOW_CNA, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
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

        InboxDetailViewScreen inboxDetailViewScreen = inboxScreen.tapOnWorkflow(workflowID);
        SubmitScreen submitScreen = inboxDetailViewScreen.tapOnSubmitButton();
        inboxScreen = submitScreen.submitWorkflow(null, WORKFLOW_CNA, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_SUBMIT_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_ACK_POST_CLARIFICATION, WORKFLOW_CNA, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        inboxScreen.logout();

        //---------Login as CNA user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.FOUsername01"));
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

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername01"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CNA, STATUS_OVERDUE);
        inboxScreen.navigateToBucket(BUCKET_TO_REVIEW);
        inboxScreen.tapOnForAcknowledgementSubTab();
        workflowID = inboxScreen.getFirstCNAWorkflowId();
        ClarificationOptionScreen clarificationOptionScreen = inboxScreen.swipeLeftAndTapOnClarification(workflowID);
        ClarificationScreen clarificationScreen = clarificationOptionScreen.selectClarificationOption(CLARIFICATION_OPTION_SEND_TO);
        inboxScreen = clarificationScreen.clarifyWorkflow(null, prop.getProperty("uat.DelegateUsername"),
                hasEscalate, WORKFLOW_CNA, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_CLARIFICATION, WORKFLOW_CNA, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
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
        inboxScreen = submitScreen.submitWorkflow(null, WORKFLOW_CNA, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_SUBMIT_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_ACK_POST_CLARIFICATION, WORKFLOW_CNA, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        inboxScreen.logout();

        //---------Login as CNA user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.FOUsername01"));
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
        ClarificationScreen clarificationScreen = clarificationOptionScreen.selectClarificationOption(CLARIFICATION_OPTION_SEND_TO);
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
        inboxScreen = submitScreen.submitWorkflow(null, WORKFLOW_CNA, workflowCount);
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

    @Test(groups = {TEST_GRP_CLARIFICATION_ALL, TEST_GRP_CNA, TEST_GRP_CLARIFICATION_OTHERS},
            dependsOnMethods = {"clarifySelectedCNAWorkflowToAnyoneTest"})
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
        ClarificationScreen clarificationScreen = clarificationOptionScreen.selectClarificationOption(CLARIFICATION_OPTION_SEND_TO);
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
        inboxScreen = submitScreen.submitWorkflow(null, WORKFLOW_CNA, workflowIDList.size());
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
        InboxDetailViewScreen detailViewScreen = inboxScreen.tapOnWorkflow(workflowID);
        ClarificationOptionScreen clarificationOptionScreen = detailViewScreen.tapOnClarificationButton();
        ClarificationScreen clarificationScreen = clarificationOptionScreen.selectClarificationOption(CLARIFICATION_OPTION_SEND_TO);
        inboxScreen = clarificationScreen.clarifyWorkflow(null, prop.getProperty("uat.ALMUsername"),
                hasEscalate, WORKFLOW_PNL, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_CLARIFICATION, WORKFLOW_PNL, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
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

        InboxDetailViewScreen inboxDetailViewScreen = inboxScreen.tapOnWorkflow(workflowID);
        SubmitScreen submitScreen = inboxDetailViewScreen.tapOnSubmitButton();
        inboxScreen = submitScreen.submitWorkflow(null, WORKFLOW_PNL, 1);
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
        ClarificationScreen clarificationScreen = clarificationOptionScreen.selectClarificationOption(CLARIFICATION_OPTION_SEND_TO);
        inboxScreen = clarificationScreen.clarifyWorkflow(null, prop.getProperty("uat.ALMUsername"),
                hasEscalate, WORKFLOW_PNL, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS
                ),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_CLARIFICATION, WORKFLOW_PNL, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        inboxScreen.logout();

        //---------Login as others user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.ALMUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_PNL, STATUS_OPEN);
        inboxScreen.tapOnForClarificationSubTab();
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_TO_DO
                ),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_CLARIFICATION, WORKFLOW_PNL, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));

        //----------------Submit the workflow back to CNA user-------------------

        InboxDetailViewScreen inboxDetailViewScreen = inboxScreen.tapOnWorkflow(workflowID);
        SubmitScreen submitScreen = inboxDetailViewScreen.tapOnSubmitButton();
        inboxScreen = submitScreen.submitWorkflow(null, WORKFLOW_PNL, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS
                ),
                FAILED_MSG_FAILED_TO_SUBMIT_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(
                WORKFLOW_STATUS_PENDING_REVIEW_AND_ACCEPTANCE_POST_CLARIFICATION, WORKFLOW_PNL, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        inboxScreen.logout();

        //---------Login as PNL user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_PNL, STATUS_OPEN);
        inboxScreen.tapOnForReviewAndAcceptanceSubTab();
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_TO_DO
                ),
                FAILED_MSG_FAILED_TO_SUBMIT_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(
                WORKFLOW_STATUS_PENDING_REVIEW_AND_ACCEPTANCE_POST_CLARIFICATION, WORKFLOW_PNL, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));

        //----------------Acknowledge the workflow-------------------

        AcknowledgeScreen acknowledgeScreen = inboxScreen.swipeRightAndTapOnAcknowledge(workflowID, WORKFLOW_PNL);
        inboxScreen = acknowledgeScreen.acknowledgeWorkflow(null, WORKFLOW_PNL, 1);
        inboxScreen.navigateToBucket(BUCKET_CLOSED);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_CLOSED
                ),
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
        ClarificationScreen clarificationScreen = clarificationOptionScreen.selectClarificationOption(CLARIFICATION_OPTION_SEND_TO);
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
        inboxScreen = submitScreen.submitWorkflow(null, WORKFLOW_PNL, workflowCount);
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

    @Test(groups = {TEST_GRP_CLARIFICATION_ALL, TEST_GRP_PNL, TEST_GRP_CLARIFICATION_OTHERS}/*,
            dependsOnMethods = {"clarifySelectedPNLWorkflowToAnyoneTest"}*/)
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
        ClarificationScreen clarificationScreen = clarificationOptionScreen.selectClarificationOption(CLARIFICATION_OPTION_SEND_TO);
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
        inboxScreen = submitScreen.submitWorkflow(null, WORKFLOW_PNL, workflowIDList.size());
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

    @Test(groups = {TEST_GRP_CLARIFICATION, TEST_GRP_OMR, TEST_GRP_CLARIFICATION_OTHERS})
    public void sendOMRWorkflowToAnyoneForClarificationDetailViewTest() {
        System.out.println("Method: sendOMRWorkflowToAnyoneForClarificationDetailViewTest()");
        boolean hasEscalate = true;
        String workflowID;

        //--------------Send for Clarification-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_OMR, STATUS_OPEN);
        inboxScreen.tapOnForAcknowledgementSubTab();
        workflowID = inboxScreen.getFirstCNAWorkflowId();
        InboxDetailViewScreen detailViewScreen = inboxScreen.tapOnWorkflow(workflowID);
        ClarificationOptionScreen clarificationOptionScreen = detailViewScreen.tapOnClarificationButton();
        ClarificationScreen clarificationScreen = clarificationOptionScreen.selectClarificationOption(CLARIFICATION_OPTION_SEND_TO);
        inboxScreen = clarificationScreen.clarifyWorkflow(null, prop.getProperty("uat.DelegateUsername"),
                hasEscalate, WORKFLOW_OMR, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_CLARIFICATION, WORKFLOW_OMR, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        inboxScreen.logout();

        //---------Login as another user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_OMR, STATUS_OPEN);
        inboxScreen.tapOnForClarificationSubTab();
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_TO_DO
                ),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_CLARIFICATION, WORKFLOW_OMR, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));

        //----------------Submit the workflow back to OMR user-------------------

        InboxDetailViewScreen inboxDetailViewScreen = inboxScreen.tapOnWorkflow(workflowID);
        SubmitScreen submitScreen = inboxDetailViewScreen.tapOnSubmitButton();
        inboxScreen = submitScreen.submitWorkflow(null, WORKFLOW_OMR, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS
                ),
                FAILED_MSG_FAILED_TO_SUBMIT_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_ACK_POST_CLARIFICATION, WORKFLOW_OMR, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        inboxScreen.logout();

        //---------Login as OMR user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_OMR, STATUS_OPEN);
        inboxScreen.tapOnForAcknowledgementSubTab();
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_TO_DO
                ),
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
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_OMR, STATUS_OPEN);
        inboxScreen.tapOnForAcknowledgementSubTab();
        workflowID = inboxScreen.getFirstCNAWorkflowId();
        ClarificationOptionScreen clarificationOptionScreen = inboxScreen.swipeLeftAndTapOnClarification(workflowID);
        ClarificationScreen clarificationScreen = clarificationOptionScreen.selectClarificationOption(CLARIFICATION_OPTION_SEND_TO);
        inboxScreen = clarificationScreen.clarifyWorkflow(null, prop.getProperty("uat.DelegateUsername"),
                hasEscalate, WORKFLOW_OMR, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS
                ),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_CLARIFICATION, WORKFLOW_OMR, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        inboxScreen.logout();

        //---------Login as Other user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_OMR, STATUS_OPEN);
        inboxScreen.tapOnForClarificationSubTab();
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_TO_DO
                ),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_CLARIFICATION, WORKFLOW_OMR, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));

        //----------------Submit the workflow back to user-------------------

        SubmitScreen submitScreen = inboxScreen.swipeRightAndTapOnSubmit(workflowID);
        inboxScreen = submitScreen.submitWorkflow(null, WORKFLOW_OMR, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS
                ),
                FAILED_MSG_FAILED_TO_SUBMIT_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_ACK_POST_CLARIFICATION, WORKFLOW_OMR, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        inboxScreen.logout();

        //---------Login as OMR user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_OMR, STATUS_OPEN);
        inboxScreen.tapOnForAcknowledgementSubTab();
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_TO_DO
                ),
                FAILED_MSG_FAILED_TO_SUBMIT_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_ACK_POST_CLARIFICATION, WORKFLOW_OMR, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));

        //----------------Acknowledge the workflow-------------------

        AcknowledgeScreen acknowledgeScreen = inboxScreen.swipeRightAndTapOnAcknowledge(workflowID, WORKFLOW_OMR);
        inboxScreen = acknowledgeScreen.acknowledgeOMRWorkflow(null,
                ACKNOWLEDGEMENT_CODE_HRR_DEAL, WORKFLOW_OMR, 1);
        inboxScreen.navigateToBucket(BUCKET_CLOSED);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_CLOSED
                ),
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
        int workflowCount = 2;

        //--------------Send for Clarification-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_OMR, STATUS_OPEN);
        inboxScreen.tapOnForAcknowledgementSubTab();
        SelectMultipleWorkflowScreen selectMultipleWorkflowScreen = inboxScreen.navigateToSelectMultipleWorkflowScreen(workflowCount,
                BUCKET_TO_REVIEW, MORE_OPTION_CLARIFY_SELECTED);
        workflowIDList = selectMultipleWorkflowScreen.selectNumberOfCNAWorkflow(workflowCount);
        ClarificationOptionScreen clarificationOptionScreen = selectMultipleWorkflowScreen.tapOnClarifySelectedScreenDoneButton();
        ClarificationScreen clarificationScreen = clarificationOptionScreen.selectClarificationOption(CLARIFICATION_OPTION_SEND_TO);
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
        inboxScreen = submitScreen.submitWorkflow(null, WORKFLOW_OMR, workflowCount);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_SUBMIT_SELECTED_WORKFLOW);
        inboxScreen.logout();

        //---------Login as OMR user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.FOUsername02"));
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

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_OMR, STATUS_OPEN);
        inboxScreen.tapOnForAcknowledgementSubTab();
        workflowIDList = inboxScreen.getAllCNAWorkflowId();
        ClarificationOptionScreen clarificationOptionScreen = inboxScreen.clarifyAllWorkflow();
        ClarificationScreen clarificationScreen = clarificationOptionScreen.selectClarificationOption(CLARIFICATION_OPTION_SEND_TO);
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
        inboxScreen = submitScreen.submitWorkflow(null, WORKFLOW_OMR, workflowIDList.size());
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_SUBMIT_ALL_WORKFLOW);
        inboxScreen.logout();

        //---------Login as OMR user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_OMR, STATUS_OPEN);
        inboxScreen.tapOnForAcknowledgementSubTab();
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_SUBMIT_ALL_WORKFLOW);

        System.out.println("Complete!");
    }

    //-------------------------------- CE ---------------------------------

    @Test(groups = {TEST_GRP_CLARIFICATION, TEST_GRP_CE})
    public void clarifyCEWorkflowDetailViewTest() {
        System.out.println("Method: clarifyCEWorkflowDetailViewTest()");
        String workflowID;

        //--------------Send for Clarification-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.MTCRUsername"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CE, STATUS_OPEN);
        inboxScreen.tapOnForReviewSubTab();
        workflowID = inboxScreen.getFirstCNAWorkflowId();
        InboxDetailViewScreen detailViewScreen = inboxScreen.tapOnWorkflow(workflowID);
        ClarificationScreen clarificationScreen = detailViewScreen.tapOnClarificationButtonForCE();
        inboxScreen = clarificationScreen.clarifyCEWorkflow(null, WORKFLOW_CE, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS
                ),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_CLARIFICATION_WITH_LIMIT_MONITORING, WORKFLOW_CE, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        inboxScreen.logout();

        //---------Login as Limit Monitoring user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.LimitMonitoringUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CE, STATUS_OPEN);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_TO_DO
                ),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_CLARIFICATION_WITH_LIMIT_MONITORING, WORKFLOW_CE, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));

        //----------------Submit the workflow back to MTCR user-------------------

        InboxDetailViewScreen inboxDetailViewScreen = inboxScreen.tapOnWorkflow(workflowID);
        SubmitScreen submitScreen = inboxDetailViewScreen.tapOnSubmitButton();
        inboxScreen = submitScreen.submitWorkflow(null, WORKFLOW_CE, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS
                ),
                FAILED_MSG_FAILED_TO_SUBMIT_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_MTCR_REVIEW_POST_CLARIFICATION, WORKFLOW_CE, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        inboxScreen.logout();

        //---------Login as MTCR user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.MTCRUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CE, STATUS_OPEN);
        inboxScreen.tapOnForReviewSubTab();
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_TO_DO
                ),
                FAILED_MSG_FAILED_TO_SUBMIT_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_MTCR_REVIEW_POST_CLARIFICATION, WORKFLOW_CE, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));

        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_CLARIFICATION, TEST_GRP_CE})
    public void swipeToClarifyCEWorkflowTest() {
        System.out.println("Method: swipeToClarifyCEWorkflowTest()");
        String workflowID;

        //--------------Send for Clarification-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.MTCRUsername"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CE, STATUS_OPEN);
        inboxScreen.tapOnForReviewSubTab();
        workflowID = inboxScreen.getFirstCNAWorkflowId();
        ClarificationScreen clarificationScreen = inboxScreen.swipeLeftAndTapOnClarificationForCE(workflowID);
        inboxScreen = clarificationScreen.clarifyCEWorkflow(null, WORKFLOW_CE, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS
                ),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_CLARIFICATION_WITH_LIMIT_MONITORING, WORKFLOW_CE, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        inboxScreen.logout();

        //---------Login as Limit Monitoring user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.LimitMonitoringUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CE, STATUS_OPEN);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_TO_DO
                ),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_CLARIFICATION_WITH_LIMIT_MONITORING, WORKFLOW_CE, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));

        //----------------Submit the workflow back to user-------------------

        SubmitScreen submitScreen = inboxScreen.swipeRightAndTapOnSubmit(workflowID);
        inboxScreen = submitScreen.submitWorkflow(null, WORKFLOW_CE, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS
                ),
                FAILED_MSG_FAILED_TO_SUBMIT_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_MTCR_REVIEW_POST_CLARIFICATION, WORKFLOW_CE, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        inboxScreen.logout();

        //---------Login as MTCR user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.MTCRUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CE, STATUS_OPEN);
        inboxScreen.tapOnForReviewSubTab();
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_TO_DO
                ),
                FAILED_MSG_FAILED_TO_SUBMIT_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_MTCR_REVIEW_POST_CLARIFICATION, WORKFLOW_CE, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));

        //----------------Acknowledge the workflow-------------------

        AcknowledgeScreen acknowledgeScreen = inboxScreen.swipeRightAndTapOnAcknowledge(workflowID, WORKFLOW_CE);
        inboxScreen = acknowledgeScreen.reviewAndAssessWorkflow(null, CE_SEVERITY_HIGH,
                CE_POTENTIAL_LOSS_YES, WORKFLOW_CE, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS
                ),
                FAILED_MSG_FAILED_TO_REVIEW_AND_ASSESS_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_FO_REVIEW, WORKFLOW_CE, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));

        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_CLARIFICATION, TEST_GRP_CE}/*,
            dependsOnMethods = {"clarifyCEWorkflowDetailViewTest",
                    "swipeToClarifyCEWorkflowTest"}*/)
    public void clarifySelectedCEWorkflowTest() {
        System.out.println("Method: clarifySelectedCEWorkflowTest()");
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
        inboxScreen = clarificationScreen.clarifyCEWorkflow(null, WORKFLOW_CE, workflowCount);
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
        inboxScreen = submitScreen.submitWorkflow(null, WORKFLOW_CE, workflowCount);
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

    @Test(groups = {TEST_GRP_CLARIFICATION, TEST_GRP_CE},
            dependsOnMethods = {"clarifySelectedCEWorkflowTest"})
    public void clarifyAllCEWorkflowTest() {
        System.out.println("Method: clarifyAllCNAWorkflowToAnyoneTest()");
        List<String> workflowIDList;
        List<String> allWorkflowIDList;
        InboxDetailViewScreen inboxDetailViewScreen = new InboxDetailViewScreen(iosDriver);

        //--------------Send for Clarification-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.MTCRUsername"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CE, STATUS_OPEN);
        inboxScreen.tapOnForReviewSubTab();
        workflowIDList = inboxScreen.getAllCNAWorkflowId();
        ClarificationScreen clarificationScreen = inboxScreen.clarifyAllWorkflowForCE();
        inboxScreen = clarificationScreen.clarifyCEWorkflow(null, WORKFLOW_CE, workflowIDList.size());
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
        inboxScreen = submitScreen.submitWorkflow(null, WORKFLOW_CE, workflowIDList.size());
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
        InboxDetailViewScreen detailViewScreen = inboxScreen.tapOnWorkflow(workflowID);
        ClarificationOptionScreen clarificationOptionScreen = detailViewScreen.tapOnClarificationButton();
        ClarificationScreen clarificationScreen = clarificationOptionScreen
                .selectClarificationOption(CLARIFICATION_OPTION_VOLCKER_COMPLIANCE);
        inboxScreen = clarificationScreen.clarifyWorkflow(null, null, false, WORKFLOW_VE, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(
                WORKFLOW_STATUS_PENDING_COMPLIANCE_CLARIFICATION_UT, WORKFLOW_VE, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));

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
                .selectClarificationOption(CLARIFICATION_OPTION_VOLCKER_COMPLIANCE);
        inboxScreen = clarificationScreen.clarifyWorkflow(null, null, false, WORKFLOW_VE, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_COMPLIANCE_CLARIFICATION_UT,
                WORKFLOW_VE, workflowID), FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));

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
                .selectClarificationOption(CLARIFICATION_OPTION_VOLCKER_COMPLIANCE);
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
        InboxDetailViewScreen detailViewScreen = inboxScreen.tapOnWorkflow(workflowID);
        workflowStatus = detailViewScreen.getWorkflowStatusValue();
        if (WORKFLOW_STATUS_PENDING_VDO_CLARIFICATION.equals(workflowStatus)) {
            SubmitScreen submitScreen = detailViewScreen.tapOnSubmitButton();
            inboxScreen = submitScreen.submitWorkflow(null, WORKFLOW_VE, 1);
            inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
            Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                    FAILED_MSG_FAILED_TO_SUBMIT_WORKFLOW.replace("$1", workflowID));
            Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(
                    WORKFLOW_STATUS_PENDING_COMPLIANCE_ACTION_POST_CLARIFICATION, WORKFLOW_VE, workflowID),
                    FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        } else {
            SubmitOptionScreen submitOptionScreen = detailViewScreen.tapOnSubmitButtonVE();
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

        InboxDetailViewScreen detailViewScreen = inboxScreen.tapOnWorkflow(workflowID);
        workflowStatus = detailViewScreen.getWorkflowStatusValue();
        detailViewScreen.tapOnBackButton();
        if (WORKFLOW_STATUS_PENDING_VDO_CLARIFICATION.equals(workflowStatus)) {
            SubmitScreen submitScreen = inboxScreen.swipeRightAndTapOnRespond(workflowID);
            inboxScreen = submitScreen.submitWorkflow(null, WORKFLOW_VE, 1);
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
        InboxDetailViewScreen detailViewScreen = inboxScreen.tapOnWorkflow(workflowID);
        workflowStatus = detailViewScreen.getWorkflowStatusValue();
        detailViewScreen.tapOnBackButton();
        SelectMultipleWorkflowScreen selectMultipleWorkflowScreen = inboxScreen.navigateToSelectMultipleWorkflowScreen(
                1, BUCKET_TO_DO, MORE_OPTION_RESPOND_SELECTED);
        selectMultipleWorkflowScreen.selectNumberOfWorkflow(workflowID);
        if (WORKFLOW_STATUS_PENDING_VDO_CLARIFICATION.equals(workflowStatus)) {
            SubmitScreen submitScreen = selectMultipleWorkflowScreen.tapOnSubmitSelectedScreenDoneButton();
            inboxScreen = submitScreen.submitWorkflow(null, WORKFLOW_VE, 1);
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

    @Test(groups = {TEST_GRP_CLARIFICATION, TEST_GRP_IPV_FVA, TEST_GRP_CLARIFICATION_OTHERS})
    public void sendFVAWorkflowToAnyoneForClarificationDetailViewTest() {
        System.out.println("Method: sendFVAWorkflowToAnyoneForClarificationDetailViewTest()");
        boolean hasEscalate = true;
        String workflowID;

        //--------------Send for Clarification-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername01"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_FVA, STATUS_OPEN);
        inboxScreen.tapOnForAcknowledgementSubTab();
        workflowID = inboxScreen.getFirstCNAWorkflowId();
        InboxDetailViewScreen detailViewScreen = inboxScreen.tapOnWorkflow(workflowID);
        ClarificationOptionScreen clarificationOptionScreen = detailViewScreen.tapOnClarificationButton();
        ClarificationScreen clarificationScreen = clarificationOptionScreen.selectClarificationOption(CLARIFICATION_OPTION_SEND_TO);
        inboxScreen = clarificationScreen.clarifyWorkflow(null, prop.getProperty("uat.DelegateUsername"),
                hasEscalate, WORKFLOW_CNA, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_CLARIFICATION, WORKFLOW_FVA, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        inboxScreen.logout();

        //---------Login as another user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_FVA, STATUS_OPEN);
        inboxScreen.tapOnForClarificationSubTab();
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_TO_DO),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_CLARIFICATION, WORKFLOW_FVA, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));

        //----------------Submit the workflow back to FVA user-------------------

        InboxDetailViewScreen inboxDetailViewScreen = inboxScreen.tapOnWorkflow(workflowID);
        SubmitScreen submitScreen = inboxDetailViewScreen.tapOnSubmitButton();
        inboxScreen = submitScreen.submitWorkflow(null, WORKFLOW_FVA, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_SUBMIT_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_ACK_POST_CLARIFICATION, WORKFLOW_FVA, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        inboxScreen.logout();

        //---------Login as FVA user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.FOUsername01"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_FVA, STATUS_OPEN);
        inboxScreen.tapOnForAcknowledgementSubTab();
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_TO_DO),
                FAILED_MSG_FAILED_TO_SUBMIT_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_ACK_POST_CLARIFICATION, WORKFLOW_FVA, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));

        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_CLARIFICATION, TEST_GRP_IPV_FVA, TEST_GRP_CLARIFICATION_OTHERS})
    public void swipeToSendFVAWorkflowToAnyoneForClarificationTest() {
        System.out.println("Method: swipeToSendFVAWorkflowToAnyoneForClarificationTest()");
        boolean hasEscalate = false;
        String workflowID;

        //--------------Send for Clarification-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername01"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_FVA, STATUS_OPEN);
        inboxScreen.tapOnForAcknowledgementSubTab();
        workflowID = inboxScreen.getFirstCNAWorkflowId();
        ClarificationOptionScreen clarificationOptionScreen = inboxScreen.swipeLeftAndTapOnClarification(workflowID);
        ClarificationScreen clarificationScreen = clarificationOptionScreen.selectClarificationOption(CLARIFICATION_OPTION_SEND_TO);
        inboxScreen = clarificationScreen.clarifyWorkflow(null, prop.getProperty("uat.DelegateUsername"),
                hasEscalate, WORKFLOW_FVA, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_CLARIFICATION, WORKFLOW_FVA, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        inboxScreen.logout();

        //---------Login as Other user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_FVA, STATUS_OPEN);
        inboxScreen.tapOnForClarificationSubTab();
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_TO_DO),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_CLARIFICATION, WORKFLOW_FVA, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));

        //----------------Submit the workflow back to user-------------------

        SubmitScreen submitScreen = inboxScreen.swipeRightAndTapOnSubmit(workflowID);
        inboxScreen = submitScreen.submitWorkflow(null, WORKFLOW_FVA, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_SUBMIT_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_ACK_POST_CLARIFICATION, WORKFLOW_FVA, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        inboxScreen.logout();

        //---------Login as FVA user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.FOUsername01"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_FVA, STATUS_OPEN);
        inboxScreen.tapOnForAcknowledgementSubTab();
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_TO_DO),
                FAILED_MSG_FAILED_TO_SUBMIT_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_ACK_POST_CLARIFICATION, WORKFLOW_FVA, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));

        //----------------Acknowledge the workflow-------------------

        AcknowledgeScreen acknowledgeScreen = inboxScreen.swipeRightAndTapOnAcknowledge(workflowID, WORKFLOW_FVA);
        inboxScreen = acknowledgeScreen.acknowledgeWorkflow(null, WORKFLOW_FVA, 1);
        inboxScreen.navigateToBucket(BUCKET_CLOSED);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_CLOSED),
                FAILED_MSG_FAILED_TO_ACKNOWLEDGE_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_ACKNOWLEDGED, WORKFLOW_FVA, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));

        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_CLARIFICATION_SELECTED, TEST_GRP_IPV_FVA, TEST_GRP_CLARIFICATION_OTHERS},
            dependsOnMethods = {"swipeToSendFVAWorkflowToAnyoneForClarificationTest",
                    "sendFVAWorkflowToAnyoneForClarificationDetailViewTest"})
    public void clarifySelectedFVAWorkflowToAnyoneTest() {
        System.out.println("Method: clarifySelectedFVAWorkflowToAnyoneTest()");
        boolean hasEscalate = true;
        List<String> workflowIDList;
        List<String> allWorkflowIDList;
        int workflowCount = 1;

        //--------------Send for Clarification-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername01"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_FVA, STATUS_OVERDUE);
        inboxScreen.tapOnForAcknowledgementSubTab();
        SelectMultipleWorkflowScreen selectMultipleWorkflowScreen = inboxScreen.navigateToSelectMultipleWorkflowScreen(workflowCount,
                BUCKET_TO_DO, MORE_OPTION_CLARIFY_SELECTED);
        workflowIDList = selectMultipleWorkflowScreen.selectNumberOfCNAWorkflow(workflowCount);
        ClarificationOptionScreen clarificationOptionScreen = selectMultipleWorkflowScreen.tapOnClarifySelectedScreenDoneButton();
        ClarificationScreen clarificationScreen = clarificationOptionScreen.selectClarificationOption(CLARIFICATION_OPTION_SEND_TO);
        inboxScreen = clarificationScreen.clarifyWorkflow(null, prop.getProperty("uat.DelegateUsername"),
                hasEscalate, WORKFLOW_FVA, workflowCount);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList),
                FAILED_MSG_FAILED_TO_SENT_SELECTED_WORKFLOW_FOR_CLARIFICATION);
        inboxScreen.logout();

        //---------Login as other user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_FVA, STATUS_OPEN);
        inboxScreen.tapOnForClarificationSubTab();
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList),
                FAILED_MSG_FAILED_TO_SENT_SELECTED_WORKFLOW_FOR_CLARIFICATION);


        //----------------Submit the workflow back to FVA user-------------------

        selectMultipleWorkflowScreen = inboxScreen.navigateToSelectMultipleWorkflowScreen(allWorkflowIDList.size(),
                BUCKET_TO_DO, MORE_OPTION_SUBMIT_SELECTED);
        selectMultipleWorkflowScreen.selectNumberOfWorkflow(workflowIDList);
        SubmitScreen submitScreen = selectMultipleWorkflowScreen.tapOnSubmitSelectedScreenDoneButton();
        inboxScreen = submitScreen.submitWorkflow(null, WORKFLOW_FVA, workflowCount);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_SUBMIT_SELECTED_WORKFLOW);
        inboxScreen.logout();

        //---------Login as FVA user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.FOUsername01"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_FVA, STATUS_OPEN);
        inboxScreen.tapOnForAcknowledgementSubTab();
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_SUBMIT_SELECTED_WORKFLOW);

        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_CLARIFICATION_ALL, TEST_GRP_IPV_FVA, TEST_GRP_CLARIFICATION_OTHERS},
            dependsOnMethods = {"clarifySelectedFVAWorkflowToAnyoneTest"})
    public void clarifyAllFVAWorkflowToAnyoneTest() {
        System.out.println("Method: clarifyAllFVAWorkflowToAnyoneTest()");
        List<String> workflowIDList;
        List<String> allWorkflowIDList;
        boolean hasEscalate = true;

        //--------------Send for Clarification-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername01"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_FVA, STATUS_OVERDUE);
        inboxScreen.navigateToBucket(BUCKET_TO_DO);
        inboxScreen.tapOnForAcknowledgementSubTab();
        workflowIDList = inboxScreen.getAllCNAWorkflowId();
        ClarificationOptionScreen clarificationOptionScreen = inboxScreen.clarifyAllWorkflow();
        ClarificationScreen clarificationScreen = clarificationOptionScreen.selectClarificationOption(CLARIFICATION_OPTION_SEND_TO);
        clarificationScreen.clarifyWorkflow(null, prop.getProperty("uat.DelegateUsername"), hasEscalate,
                WORKFLOW_FVA, workflowIDList.size());
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList),
                FAILED_MSG_FAILED_TO_SENT_ALL_WORKFLOW_FOR_CLARIFICATION);
        inboxScreen.logout();

        //---------Login as other user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_FVA, STATUS_OPEN);
        inboxScreen.tapOnForClarificationSubTab();
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList),
                FAILED_MSG_FAILED_TO_SENT_ALL_WORKFLOW_FOR_CLARIFICATION);

        //----------------Submit the workflow back to FVA user-------------------

        SubmitScreen submitScreen = inboxScreen.submitAllWorkflow();
        inboxScreen = submitScreen.submitWorkflow(null, WORKFLOW_FVA, workflowIDList.size());
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_SUBMIT_ALL_WORKFLOW);
        inboxScreen.logout();

        //---------Login as FVA user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.FOUsername01"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_FVA, STATUS_OPEN);
        inboxScreen.tapOnForAcknowledgementSubTab();
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_SUBMIT_ALL_WORKFLOW);

        System.out.println("Complete!");
    }

    //--------------------------- GMR/GT ------------------------------

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
        InboxDetailViewScreen detailViewScreen = inboxScreen.tapOnWorkflow(workflowID);
        ClarificationOptionScreen clarificationOptionScreen = detailViewScreen.tapOnClarificationButton();
        ClarificationScreen clarificationScreen = clarificationOptionScreen.selectClarificationOption(CLARIFICATION_OPTION_SEND_TO);
        inboxScreen = clarificationScreen.clarifyWorkflow(null, prop.getProperty("uat.DelegateUsername"),
                hasEscalate, WORKFLOW_GMR, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_CLARIFICATION, WORKFLOW_GMR, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
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

        InboxDetailViewScreen inboxDetailViewScreen = inboxScreen.tapOnWorkflow(workflowID);
        SubmitScreen submitScreen = inboxDetailViewScreen.tapOnSubmitButton();
        inboxScreen = submitScreen.submitWorkflow(null, WORKFLOW_GMR, 1);
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
        ClarificationScreen clarificationScreen = clarificationOptionScreen.selectClarificationOption(CLARIFICATION_OPTION_SEND_TO);
        inboxScreen = clarificationScreen.clarifyWorkflow(null, prop.getProperty("uat.DelegateUsername"),
                hasEscalate, WORKFLOW_GMR, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_SENT_WORKFLOW_FOR_CLARIFICATION.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_CLARIFICATION, WORKFLOW_GMR, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
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
        inboxScreen = submitScreen.submitWorkflow(null, WORKFLOW_GMR, 1);
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
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_GMR, STATUS_OPEN);
        inboxScreen.tapOnForApprovalSubTab();
        SelectMultipleWorkflowScreen selectMultipleWorkflowScreen = inboxScreen.navigateToSelectMultipleWorkflowScreen(workflowCount,
                BUCKET_TO_DO, MORE_OPTION_CLARIFY_SELECTED);
        workflowIDList = selectMultipleWorkflowScreen.selectNumberOfCNAWorkflow(workflowCount);
        ClarificationOptionScreen clarificationOptionScreen = selectMultipleWorkflowScreen.tapOnClarifySelectedScreenDoneButton();
        ClarificationScreen clarificationScreen = clarificationOptionScreen.selectClarificationOption(CLARIFICATION_OPTION_SEND_TO);
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
        inboxScreen = submitScreen.submitWorkflow(null, WORKFLOW_GMR, workflowCount);
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
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_GMR, STATUS_OPEN);
        inboxScreen.navigateToBucket(BUCKET_TO_DO);
        inboxScreen.tapOnForApprovalSubTab();
        workflowIDList = inboxScreen.getAllCNAWorkflowId();
        ClarificationOptionScreen clarificationOptionScreen = inboxScreen.clarifyAllWorkflow();
        ClarificationScreen clarificationScreen = clarificationOptionScreen.selectClarificationOption(CLARIFICATION_OPTION_SEND_TO);
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
        inboxScreen = submitScreen.submitWorkflow(null, WORKFLOW_GMR, workflowIDList.size());
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
