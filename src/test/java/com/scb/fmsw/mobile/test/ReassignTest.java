package com.scb.fmsw.mobile.test;

import com.scb.fmsw.mobile.base.BaseTest;
import com.scb.fmsw.mobile.screen.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class ReassignTest extends BaseTest {

    //create test method for CNA, OMR, PNL, GT/GMR, IPV/FVA

    //------------------- PC GBS ----------------------

    @Test(groups = TEST_GRP_REASSIGN, priority = 1)
    public void pcGBSReassignCNAWorkflowDetailViewTest() {
        System.out.println("Method: pcGBSReassignCNAWorkflowDetailViewTest()");
        String workflowID;

        //--------------Reassign workflow-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.GBSOMRUsername"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CNA, STATUS_OPEN);
        inboxScreen.tapOnForClarificationSubTab();
        workflowID = inboxScreen.getFirstWorkflowId();
        InboxDetailViewScreen inboxDetailViewScreen = inboxScreen.tapOnWorkflow(workflowID);
        ReassignScreen reassignScreen = inboxDetailViewScreen.tapOnReassignButton();
        inboxScreen = reassignScreen.reassignWorkflow(PC_GRP_PC_ALM, WORKFLOW_CNA, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS), FAILED_MSG_FAILED_TO_REASSIGN_WORKFLOW.replace("$1", workflowID));
        inboxScreen.logout();

        //---------Login as ALM user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.ALMUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CNA, STATUS_OPEN);
        inboxScreen.tapOnForClarificationSubTab();
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS), FAILED_MSG_FAILED_TO_REASSIGN_WORKFLOW.replace("$1", workflowID));
        System.out.println("Complete!");
    }

    @Test(groups = TEST_GRP_REASSIGN, priority = 1)
    public void pcGBSSwipeToReassignCNAWorkflowTest() {
        System.out.println("Method: pcGBSSwipeToReassignCNAWorkflowTest()");
        String workflowID;

        //--------------Reassign workflow-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.GBSUsername"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CNA, STATUS_OPEN);
        inboxScreen.tapOnForClarificationSubTab();
        workflowID = inboxScreen.getFirstWorkflowId();
        ReassignScreen reassignScreen = inboxScreen.swipeLeftAndTapOnReassign(workflowID);
        inboxScreen = reassignScreen.reassignWorkflow(PC_GRP_PC_ALM, WORKFLOW_CNA, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS), FAILED_MSG_FAILED_TO_REASSIGN_WORKFLOW.replace("$1", workflowID));
        inboxScreen.logout();

        //---------Login as ALM user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.ALMUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CNA, STATUS_OPEN);
        inboxScreen.tapOnForClarificationSubTab();
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS), FAILED_MSG_FAILED_TO_REASSIGN_WORKFLOW.replace("$1", workflowID));
        System.out.println("Complete!");
    }

    @Test(groups = TEST_GRP_REASSIGN_SELECTED, priority = 2)
    public void pcGBSReassignSelectedCNAWorkflowTest() {
        System.out.println("Method: pcGBSReassignSelectedCNAWorkflowTest()");
        List<String> workflowIDList;
        List<String> allWorkflowIDList;
        int count = 2;

        //--------------Reassign workflow-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.GBSOMRUsername"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CNA, STATUS_OVERDUE);
        inboxScreen.navigateToBucket(BUCKET_TO_DO);
        inboxScreen.tapOnForClarificationSubTab();
        SelectMultipleWorkflowScreen selectMultipleWorkflowScreen = inboxScreen.navigateToSelectMultipleWorkflowScreen(count, BUCKET_TO_DO, MORE_OPTION_REASSIGN_SELECTED);
        workflowIDList = selectMultipleWorkflowScreen.selectNumberOfCNAWorkflow(count);
        ReassignScreen reassignScreen = selectMultipleWorkflowScreen.tapOnReassignSelectedScreenDoneButton();
        inboxScreen = reassignScreen.reassignWorkflow(PC_GRP_PC_ALM, WORKFLOW_CNA, count);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        System.out.println("Get all workflow id in In Progress bucket");
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_REASSIGN_SELECTED_WORKFLOW);
        inboxScreen.logout();

        //---------Login as ALM user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.ALMUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CNA, STATUS_OPEN);
        inboxScreen.navigateToBucket(BUCKET_TO_DO);
        inboxScreen.tapOnForClarificationSubTab();
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_REASSIGN_SELECTED_WORKFLOW);
        System.out.println("Complete!");
    }

    @Test(groups = TEST_GPR_REASSIGN_ALL, priority = 3)
    public void pcALMReassignAllCNAWorkflowTest() {
        System.out.println("Method: pcALMReassignAllCNAWorkflowTest()");
        List<String> workflowIDList;
        List<String> allWorkflowIDList;

        //--------------Reassign workflow-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.ALMUsername"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CNA, STATUS_OVERDUE);
        inboxScreen.navigateToBucket(BUCKET_TO_DO);
        inboxScreen.tapOnForClarificationSubTab();
        workflowIDList = inboxScreen.getAllCNAWorkflowId();
        ReassignScreen reassignScreen = inboxScreen.reassignAllWorkflow();
        inboxScreen = reassignScreen.reassignWorkflow(PC_GRP_PC_GBS, WORKFLOW_CNA, workflowIDList.size());
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_REASSIGN_ALL_WORKFLOW);
        inboxScreen.logout();

        //---------Login as GBS user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.GBSUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CNA, STATUS_OPEN);
        inboxScreen.navigateToBucket(BUCKET_TO_DO);
        inboxScreen.tapOnForClarificationSubTab();
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_REASSIGN_ALL_WORKFLOW);
        System.out.println("Complete!");
    }

    //------------------- FO ADMIN ----------------------

    @Test(groups = TEST_GRP_REASSIGN, priority = 4)
    public void foAdminReassignCNAWorkflowDetailViewTest() {
        System.out.println("Method: foAdminReassignCNAWorkflowDetailViewTest()");
        String workflowID;

        //--------------Reassign workflow-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOAdminUsername"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CNA, STATUS_OPEN);
        inboxScreen.tapOnForExceptionSubTab();
        workflowID = inboxScreen.getFirstWorkflowId();
        InboxDetailViewScreen inboxDetailViewScreen = inboxScreen.tapOnWorkflow(workflowID);
        ReassignScreen reassignScreen = inboxDetailViewScreen.tapOnReassignButton();
        inboxScreen = reassignScreen.reassignWorkflow(prop.getProperty("uat.DelegateUsername"), WORKFLOW_CNA, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS), FAILED_MSG_FAILED_TO_REASSIGN_WORKFLOW.replace("$1", workflowID));
        inboxScreen.logout();

        //---------Login as the user that received the workflow to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CNA, STATUS_OPEN);
        inboxScreen.navigateToBucket(BUCKET_TO_REVIEW);
        inboxScreen.tapOnForAcknowledgementSubTab();
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS), FAILED_MSG_FAILED_TO_REASSIGN_WORKFLOW.replace("$1", workflowID));
        System.out.println("Complete!");
    }

    @Test(groups = TEST_GRP_REASSIGN, priority = 4)
    public void foAdminSwipeToReassignCNAWorkflowTest() {
        System.out.println("Method: foAdminSwipeToReassignCNAWorkflowTest()");
        String workflowID;

        //--------------Reassign workflow-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOAdminUsername"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CNA, STATUS_OPEN);
        inboxScreen.tapOnForExceptionSubTab();
        workflowID = inboxScreen.getFirstWorkflowId();
        ReassignScreen reassignScreen = inboxScreen.swipeRightAndTapOnReassign(workflowID);
        inboxScreen = reassignScreen.reassignWorkflow(prop.getProperty("uat.DelegateUsername"), WORKFLOW_CNA, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS), FAILED_MSG_FAILED_TO_REASSIGN_WORKFLOW.replace("$1", workflowID));
        inboxScreen.logout();

        //---------Login as the user that received the workflow to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CNA, STATUS_OPEN);
        inboxScreen.navigateToBucket(BUCKET_TO_REVIEW);
        inboxScreen.tapOnForAcknowledgementSubTab();
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS), FAILED_MSG_FAILED_TO_REASSIGN_WORKFLOW.replace("$1", workflowID));
        System.out.println("Complete!");
    }

    @Test(groups = TEST_GRP_REASSIGN_SELECTED, priority = 5)
    public void foAdminReassignSelectedCNAWorkflowTest() {
        System.out.println("Method: foAdminReassignSelectedCNAWorkflowTest()");
        List<String> workflowIDList;
        List<String> allWorkflowIDList;
        int count = 2;

        //--------------Reassign workflow-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOAdminUsername"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CNA, STATUS_OPEN);
        inboxScreen.navigateToBucket(BUCKET_TO_DO);
        inboxScreen.tapOnForExceptionSubTab();
        SelectMultipleWorkflowScreen selectMultipleWorkflowScreen = inboxScreen.navigateToSelectMultipleWorkflowScreen(count, BUCKET_TO_DO, MORE_OPTION_REASSIGN_SELECTED);
        workflowIDList = selectMultipleWorkflowScreen.selectNumberOfWorkflow(count);
        ReassignScreen reassignScreen = selectMultipleWorkflowScreen.tapOnReassignSelectedScreenDoneButton();
        inboxScreen = reassignScreen.reassignWorkflow(prop.getProperty("uat.DelegateUsername"), WORKFLOW_CNA, count);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        System.out.println("Get all workflow id in In Progress bucket");
        allWorkflowIDList = inboxScreen.getAllWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_REASSIGN_SELECTED_WORKFLOW);
        inboxScreen.logout();

        //---------Login as the user that received the workflow to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CNA, STATUS_OPEN);
        inboxScreen.navigateToBucket(BUCKET_TO_REVIEW);
        inboxScreen.tapOnForAcknowledgementSubTab();
        allWorkflowIDList = inboxScreen.getAllWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_REASSIGN_SELECTED_WORKFLOW);
        System.out.println("Complete!");
    }

    @Test(groups = TEST_GPR_REASSIGN_ALL, priority = 6)
    public void foAdminReassignAllCNAWorkflowTest() {
        System.out.println("Method: foAdminReassignAllCNAWorkflowTest()");
        List<String> workflowIDList;
        List<String> allWorkflowIDList;

        //--------------Reassign workflow-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOAdminUsername"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CNA, STATUS_OPEN);
        inboxScreen.navigateToBucket(BUCKET_TO_DO);
        inboxScreen.tapOnForExceptionSubTab();
        workflowIDList = inboxScreen.getAllWorkflowId();
        ReassignScreen reassignScreen = inboxScreen.reassignAllWorkflow();
        inboxScreen = reassignScreen.reassignWorkflow(prop.getProperty("uat.DelegateUsername"), WORKFLOW_CNA, workflowIDList.size());
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        allWorkflowIDList = inboxScreen.getAllWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_REASSIGN_ALL_WORKFLOW);
        inboxScreen.logout();

        //---------Login as the user that received the workflow to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CNA, STATUS_OPEN);
        inboxScreen.navigateToBucket(BUCKET_TO_REVIEW);
        inboxScreen.tapOnForAcknowledgementSubTab();
        allWorkflowIDList = inboxScreen.getAllWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_REASSIGN_ALL_WORKFLOW);
        System.out.println("Complete!");
    }

}
