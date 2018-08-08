package com.scb.fmsw.mobile.test;

import com.scb.fmsw.mobile.WorkflowConstants;
import com.scb.fmsw.mobile.base.BaseTest;
import com.scb.fmsw.mobile.screen.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class ExplicitDelegationTest extends BaseTest {

    //create test method for CNA, OMR, PNL, GT/GMR, IPV/FVA, VE, CE

    //-------------------------------- CNA ---------------------------------

    @Test(groups = {TEST_GRP_EXPLICIT_DELEGATION, TEST_GRP_CNA})
    public void delegateCNAWorkflowDetailViewTest() {
        System.out.println("Method: delegateCNAWorkflowDetailViewTest()");
        String workflowID;

        //--------------Explicit Delegation-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CNA, STATUS_OPEN);
        inboxScreen.navigateToBucket(BUCKET_TO_REVIEW);
        inboxScreen.tapOnForAcknowledgementSubTab();
        workflowID = inboxScreen.getFirstCNAWorkflowId();
        InboxDetailViewScreen inboxDetailViewScreen = inboxScreen.tapOnWorkflow(workflowID);
        ExplicitDelegationScreen explicitDelegationScreen = inboxDetailViewScreen.tapOnDelegateButton();
        inboxScreen = explicitDelegationScreen.delegateWorkflow(prop.getProperty("uat.DelegateUsername"), WORKFLOW_CNA);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_DELEGATE_WORKFLOW.replace("$1", workflowID));
        inboxScreen.logout();

        //---------Login as Delegate user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CNA, STATUS_OPEN);
        inboxScreen.navigateToBucket(BUCKET_TO_REVIEW);
        inboxScreen.tapOnForAcknowledgementSubTab();
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_TO_REVIEW),
                FAILED_MSG_FAILED_TO_DELEGATE_WORKFLOW.replace("$1", workflowID));
    }

    @Test(groups = {TEST_GRP_EXPLICIT_DELEGATION, TEST_GRP_CNA})
    public void swipeToDelegateCNAWorkflowTest() {
        System.out.println("Method: swipeToDelegateCNAWorkflowTest()");
        String workflowID;

        //--------------Explicit Delegation-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CNA, STATUS_OPEN);
        inboxScreen.navigateToBucket(BUCKET_TO_REVIEW);
        inboxScreen.tapOnForAcknowledgementSubTab();
        workflowID = inboxScreen.getFirstCNAWorkflowId();
        ExplicitDelegationScreen explicitDelegationScreen = inboxScreen.swipeLeftAndTapOnDelegate(workflowID);
        inboxScreen = explicitDelegationScreen.delegateWorkflow(prop.getProperty("uat.DelegateUsername"), WORKFLOW_CNA);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_DELEGATE_WORKFLOW.replace("$1", workflowID));
        inboxScreen.logout();

        //---------Login as Delegate user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CNA, STATUS_OPEN);
        inboxScreen.navigateToBucket(BUCKET_TO_REVIEW);
        inboxScreen.tapOnForAcknowledgementSubTab();
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_TO_REVIEW),
                FAILED_MSG_FAILED_TO_DELEGATE_WORKFLOW.replace("$1", workflowID));
    }

    @Test(groups = {TEST_GRP_EXPLICIT_DELEGATION_SELECTED, TEST_GRP_CNA},
            dependsOnMethods = {"delegateCNAWorkflowDetailViewTest", "swipeToDelegateCNAWorkflowTest"})
    public void delegateSelectedCNAWorkflowTest() {
        System.out.println("Method: delegateSelectedCNAWorkflowTest()");
        int count = 1;
        List<String> workflowIDList;
        List<String> allWorkflowIDList;

        //--------------Explicit Delegation-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CNA, STATUS_OPEN);
        inboxScreen.navigateToBucket(BUCKET_TO_REVIEW);
        inboxScreen.tapOnForAcknowledgementSubTab();
        SelectMultipleWorkflowScreen selectMultipleWorkflowScreen = inboxScreen
                .navigateToSelectMultipleWorkflowScreen(count, BUCKET_TO_REVIEW, MORE_OPTION_DELEGATE_SELECTED);
        workflowIDList = selectMultipleWorkflowScreen.selectNumberOfCNAWorkflow(count);
        ExplicitDelegationScreen explicitDelegationScreen = selectMultipleWorkflowScreen.tapOnDelegateSelectedScreenDoneButton();
        inboxScreen = explicitDelegationScreen.delegateWorkflow(prop.getProperty("uat.DelegateUsername"), WORKFLOW_CNA);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_DELEGATE_SELECTED_WORKFLOW);
        inboxScreen.logout();

        //---------Login as Delegate user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CNA, STATUS_OPEN);
        inboxScreen.navigateToBucket(BUCKET_TO_REVIEW);
        inboxScreen.tapOnForAcknowledgementSubTab();
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_DELEGATE_SELECTED_WORKFLOW);
    }

    @Test(groups = {TEST_GRP_EXPLICIT_DELEGATION_ALL, TEST_GRP_CNA},
            dependsOnMethods = {"delegateSelectedCNAWorkflowTest"})
    public void delegateAllCNAWorkflowTest() {
        System.out.println("Method: delegateSelectedCNAWorkflowTest()");
        List<String> workflowIDList;
        List<String> allWorkflowIDList;

        //--------------Explicit Delegation-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CNA, STATUS_OPEN);
        inboxScreen.navigateToBucket(BUCKET_TO_DO);
        inboxScreen.tapOnForAcknowledgementSubTab();
        workflowIDList = inboxScreen.getAllCNAWorkflowId();
        ExplicitDelegationScreen explicitDelegationScreen = inboxScreen.delegateAllWorkflow();
        inboxScreen = explicitDelegationScreen.delegateWorkflow(prop.getProperty("uat.FOUsername02"), WORKFLOW_CNA);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_DELEGATE_ALL_WORKFLOW);
        inboxScreen.logout();

        //---------Login as Delegate user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CNA, STATUS_OPEN);
        inboxScreen.navigateToBucket(BUCKET_TO_REVIEW);
        inboxScreen.tapOnForAcknowledgementSubTab();
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_DELEGATE_ALL_WORKFLOW);
    }

    //-------------------------------- OMR ---------------------------------

    @Test(groups = {TEST_GRP_EXPLICIT_DELEGATION, TEST_GRP_CNA})
    public void delegateOMRWorkflowDetailViewTest() {
        System.out.println("Method: delegateCNAWorkflowDetailViewTest()");
        String workflowID;

        //--------------Explicit Delegation-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_OMR, STATUS_OVERDUE);
        inboxScreen.tapOnForAcknowledgementSubTab();
        workflowID = inboxScreen.getFirstCNAWorkflowId();
        InboxDetailViewScreen inboxDetailViewScreen = inboxScreen.tapOnWorkflow(workflowID);
        ExplicitDelegationScreen explicitDelegationScreen = inboxDetailViewScreen.tapOnDelegateButton();
        inboxScreen = explicitDelegationScreen.delegateWorkflow(prop.getProperty("uat.DelegateUsername"), WORKFLOW_OMR);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_DELEGATE_WORKFLOW.replace("$1", workflowID));
        inboxScreen.logout();

        //---------Login as Delegate user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_OMR, STATUS_OVERDUE);
        inboxScreen.tapOnForAcknowledgementSubTab();
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_TO_REVIEW),
                FAILED_MSG_FAILED_TO_DELEGATE_WORKFLOW.replace("$1", workflowID));
    }

    @Test(groups = {TEST_GRP_EXPLICIT_DELEGATION, TEST_GRP_OMR})
    public void swipeToDelegateOMRWorkflowTest() {
        System.out.println("Method: swipeToDelegateOMRWorkflowTest()");
        String workflowID;

        //--------------Explicit Delegation-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_OMR, STATUS_OVERDUE);
        inboxScreen.tapOnForAcknowledgementSubTab();
        workflowID = inboxScreen.getFirstCNAWorkflowId();
        ExplicitDelegationScreen explicitDelegationScreen = inboxScreen.swipeLeftAndTapOnDelegate(workflowID);
        inboxScreen = explicitDelegationScreen.delegateWorkflow(prop.getProperty("uat.DelegateUsername"), WORKFLOW_OMR);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_DELEGATE_WORKFLOW.replace("$1", workflowID));
        inboxScreen.logout();

        //---------Login as Delegate user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_OMR, STATUS_OVERDUE);
        inboxScreen.tapOnForAcknowledgementSubTab();
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_TO_REVIEW),
                FAILED_MSG_FAILED_TO_DELEGATE_WORKFLOW.replace("$1", workflowID));
    }

    @Test(groups = {TEST_GRP_EXPLICIT_DELEGATION_SELECTED, TEST_GRP_OMR},
            dependsOnMethods = {"swipeToDelegateOMRWorkflowTest", "delegateOMRWorkflowDetailViewTest"})
    public void delegateSelectedOMRWorkflowTest() {
        System.out.println("Method: delegateSelectedOMRWorkflowTest()");
        int count = 1;
        List<String> workflowIDList;
        List<String> allWorkflowIDList;

        //--------------Explicit Delegation-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_OMR, STATUS_OVERDUE);
        inboxScreen.tapOnForAcknowledgementSubTab();
        SelectMultipleWorkflowScreen selectMultipleWorkflowScreen = inboxScreen
                .navigateToSelectMultipleWorkflowScreen(count, BUCKET_TO_DO, MORE_OPTION_DELEGATE_SELECTED);
        workflowIDList = selectMultipleWorkflowScreen.selectNumberOfCNAWorkflow(count);
        ExplicitDelegationScreen explicitDelegationScreen = selectMultipleWorkflowScreen.tapOnDelegateSelectedScreenDoneButton();
        inboxScreen = explicitDelegationScreen.delegateWorkflow(prop.getProperty("uat.DelegateUsername"), WORKFLOW_OMR);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_DELEGATE_SELECTED_WORKFLOW);
        inboxScreen.logout();

        //---------Login as Delegate user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_OMR, STATUS_OVERDUE);
        inboxScreen.tapOnForAcknowledgementSubTab();
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_DELEGATE_SELECTED_WORKFLOW);
    }

    @Test(groups = {TEST_GRP_EXPLICIT_DELEGATION_ALL, TEST_GRP_OMR},
            dependsOnMethods = {"delegateSelectedOMRWorkflowTest"})
    public void delegateAllOMRWorkflowTest() {
        System.out.println("Method: delegateAllOMRWorkflowTest()");
        List<String> workflowIDList;
        List<String> allWorkflowIDList;

        //--------------Explicit Delegation-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_OMR, STATUS_OVERDUE);
        inboxScreen.navigateToBucket(BUCKET_TO_DO);
        inboxScreen.tapOnForAcknowledgementSubTab();
        workflowIDList = inboxScreen.getAllCNAWorkflowId();
        ExplicitDelegationScreen explicitDelegationScreen = inboxScreen.delegateAllWorkflow();
        inboxScreen = explicitDelegationScreen.delegateWorkflow(prop.getProperty("uat.FOUsername02"), WORKFLOW_OMR);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_DELEGATE_ALL_WORKFLOW);
        inboxScreen.logout();

        //---------Login as Delegate user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_OMR, STATUS_OVERDUE);
        inboxScreen.navigateToBucket(BUCKET_TO_DO);
        inboxScreen.tapOnForAcknowledgementSubTab();
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_DELEGATE_ALL_WORKFLOW);
    }

    //-------------------------------- PNL ---------------------------------

    @Test(groups = {TEST_GRP_EXPLICIT_DELEGATION, TEST_GRP_PNL})
    public void delegatePNLWorkflowDetailViewTest() {
        System.out.println("Method: delegatePNLWorkflowDetailViewTest()");
        String workflowID;

        //--------------Explicit Delegation-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername01"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_PNL, STATUS_OVERDUE);
        inboxScreen.tapOnForReviewAndAcceptanceSubTab();
        workflowID = inboxScreen.getFirstCNAWorkflowId();
        InboxDetailViewScreen inboxDetailViewScreen = inboxScreen.tapOnWorkflow(workflowID);
        ExplicitDelegationScreen explicitDelegationScreen = inboxDetailViewScreen.tapOnDelegateButton();
        inboxScreen = explicitDelegationScreen.delegateWorkflow(prop.getProperty("uat.DelegateUsername"), WORKFLOW_PNL);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_DELEGATE_WORKFLOW.replace("$1", workflowID));
        inboxScreen.logout();

        //---------Login as Delegate user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_PNL, STATUS_OVERDUE);
        inboxScreen.tapOnForReviewAndAcceptanceSubTab();
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_TO_DO),
                FAILED_MSG_FAILED_TO_DELEGATE_WORKFLOW.replace("$1", workflowID));
    }

    @Test(groups = {TEST_GRP_EXPLICIT_DELEGATION, TEST_GRP_PNL})
    public void swipeToDelegatePNLWorkflowTest() {
        System.out.println("Method: swipeToDelegatePNLWorkflowTest()");
        String workflowID;

        //--------------Explicit Delegation-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername01"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_PNL, STATUS_OVERDUE);
        inboxScreen.tapOnForReviewAndAcceptanceSubTab();
        workflowID = inboxScreen.getFirstCNAWorkflowId();
        ExplicitDelegationScreen explicitDelegationScreen = inboxScreen.swipeLeftAndTapOnDelegate(workflowID);
        inboxScreen = explicitDelegationScreen.delegateWorkflow(prop.getProperty("uat.DelegateUsername"), WORKFLOW_PNL);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_DELEGATE_WORKFLOW.replace("$1", workflowID));
        inboxScreen.logout();

        //---------Login as Delegate user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_PNL, STATUS_OVERDUE);
        inboxScreen.tapOnForReviewAndAcceptanceSubTab();
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_TO_DO),
                FAILED_MSG_FAILED_TO_DELEGATE_WORKFLOW.replace("$1", workflowID));
    }

    @Test(groups = {TEST_GRP_EXPLICIT_DELEGATION_SELECTED, TEST_GRP_PNL},
            dependsOnMethods = {"delegatePNLWorkflowDetailViewTest", "swipeToDelegatePNLWorkflowTest"})
    public void delegateSelectedPNLWorkflowTest() {
        System.out.println("Method: delegateSelectedPNLWorkflowTest()");
        int count = 1;
        List<String> workflowIDList;
        List<String> allWorkflowIDList;

        //--------------Explicit Delegation-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername01"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_PNL, STATUS_OVERDUE);
        inboxScreen.tapOnForReviewAndAcceptanceSubTab();
        SelectMultipleWorkflowScreen selectMultipleWorkflowScreen = inboxScreen
                .navigateToSelectMultipleWorkflowScreen(count, BUCKET_TO_DO, MORE_OPTION_DELEGATE_SELECTED);
        workflowIDList = selectMultipleWorkflowScreen.selectNumberOfCNAWorkflow(count);
        ExplicitDelegationScreen explicitDelegationScreen = selectMultipleWorkflowScreen.tapOnDelegateSelectedScreenDoneButton();
        inboxScreen = explicitDelegationScreen.delegateWorkflow(prop.getProperty("uat.DelegateUsername"), WORKFLOW_PNL);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_DELEGATE_SELECTED_WORKFLOW);
        inboxScreen.logout();

        //---------Login as Delegate user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_PNL, STATUS_OVERDUE);
        inboxScreen.tapOnForReviewAndAcceptanceSubTab();
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_DELEGATE_SELECTED_WORKFLOW);
    }

    @Test(groups = {TEST_GRP_EXPLICIT_DELEGATION_ALL, TEST_GRP_PNL},
            dependsOnMethods = {"delegateSelectedPNLWorkflowTest"})
    public void delegateAllPNLWorkflowTest() {
        System.out.println("Method: delegateAllPNLWorkflowTest()");
        List<String> workflowIDList;
        List<String> allWorkflowIDList;

        //--------------Explicit Delegation-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_PNL, STATUS_OVERDUE);
        inboxScreen.tapOnForReviewAndAcceptanceSubTab();
        workflowIDList = inboxScreen.getAllCNAWorkflowId();
        ExplicitDelegationScreen explicitDelegationScreen = inboxScreen.delegateAllWorkflow();
        inboxScreen = explicitDelegationScreen.delegateWorkflow(prop.getProperty("uat.FOUsername01"), WORKFLOW_PNL);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_DELEGATE_ALL_WORKFLOW);
        inboxScreen.logout();

        //---------Login as Delegate user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.FOUsername01"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_PNL, STATUS_OVERDUE);
        inboxScreen.tapOnForReviewAndAcceptanceSubTab();
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_DELEGATE_ALL_WORKFLOW);
    }

    //-------------------------------- GT/GMR ---------------------------------

    @Test(groups = {TEST_GRP_EXPLICIT_DELEGATION, TEST_GRP_GT_GMR})
    public void delegateGMRWorkflowDetailViewTest() {
        System.out.println("Method: delegateGMRWorkflowDetailViewTest()");
        String workflowID;

        //--------------Explicit Delegation-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername01"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_GMR, STATUS_OVERDUE);
        inboxScreen.tapOnForApprovalSubTab();
        workflowID = inboxScreen.getFirstCNAWorkflowId();
        InboxDetailViewScreen inboxDetailViewScreen = inboxScreen.tapOnWorkflow(workflowID);
        ExplicitDelegationScreen explicitDelegationScreen = inboxDetailViewScreen.tapOnDelegateButton();
        inboxScreen = explicitDelegationScreen.delegateWorkflow(prop.getProperty("uat.DelegateUsername"), WORKFLOW_GMR);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_DELEGATE_WORKFLOW.replace("$1", workflowID));
        inboxScreen.logout();

        //---------Login as Delegate user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_GMR, STATUS_OVERDUE);
        inboxScreen.tapOnForApprovalSubTab();
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_TO_DO),
                FAILED_MSG_FAILED_TO_DELEGATE_WORKFLOW.replace("$1", workflowID));
    }

    @Test(groups = {TEST_GRP_EXPLICIT_DELEGATION, TEST_GRP_GT_GMR})
    public void swipeToDelegateGMRWorkflowTest() {
        System.out.println("Method: swipeToDelegateGMRWorkflowTest()");
        String workflowID;

        //--------------Explicit Delegation-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername01"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_GMR, STATUS_OVERDUE);
        inboxScreen.tapOnForApprovalSubTab();
        workflowID = inboxScreen.getFirstCNAWorkflowId();
        ExplicitDelegationScreen explicitDelegationScreen = inboxScreen.swipeLeftAndTapOnDelegate(workflowID);
        inboxScreen = explicitDelegationScreen.delegateWorkflow(prop.getProperty("uat.DelegateUsername"), WORKFLOW_GMR);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_DELEGATE_WORKFLOW.replace("$1", workflowID));
        inboxScreen.logout();

        //---------Login as Delegate user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_GMR, STATUS_OVERDUE);
        inboxScreen.tapOnForApprovalSubTab();
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_TO_DO),
                FAILED_MSG_FAILED_TO_DELEGATE_WORKFLOW.replace("$1", workflowID));
    }

    @Test(groups = {TEST_GRP_EXPLICIT_DELEGATION_SELECTED, TEST_GRP_GT_GMR},
            dependsOnMethods = {"delegateGMRWorkflowDetailViewTest", "swipeToDelegateGMRWorkflowTest"})
    public void delegateSelectedGMRWorkflowTest() {
        System.out.println("Method: delegateSelectedGMRWorkflowTest()");
        int count = 1;
        List<String> workflowIDList;
        List<String> allWorkflowIDList;

        //--------------Explicit Delegation-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername01"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_GMR, STATUS_OVERDUE);
        inboxScreen.tapOnForApprovalSubTab();
        SelectMultipleWorkflowScreen selectMultipleWorkflowScreen = inboxScreen
                .navigateToSelectMultipleWorkflowScreen(count, BUCKET_TO_DO, MORE_OPTION_DELEGATE_SELECTED);
        workflowIDList = selectMultipleWorkflowScreen.selectNumberOfCNAWorkflow(count);
        ExplicitDelegationScreen explicitDelegationScreen = selectMultipleWorkflowScreen.tapOnDelegateSelectedScreenDoneButton();
        inboxScreen = explicitDelegationScreen.delegateWorkflow(prop.getProperty("uat.DelegateUsername"), WORKFLOW_GMR);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_DELEGATE_SELECTED_WORKFLOW);
        inboxScreen.logout();

        //---------Login as Delegate user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_GMR, STATUS_OVERDUE);
        inboxScreen.tapOnForApprovalSubTab();
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_DELEGATE_SELECTED_WORKFLOW);
    }

    @Test(groups = {TEST_GRP_EXPLICIT_DELEGATION_ALL, TEST_GRP_GT_GMR},
            dependsOnMethods = {"delegateSelectedGMRWorkflowTest"})
    public void delegateAllGMRWorkflowTest() {
        System.out.println("Method: delegateSelectedGMRWorkflowTest()");
        List<String> workflowIDList;
        List<String> allWorkflowIDList;

        //--------------Explicit Delegation-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_GMR, STATUS_OVERDUE);
        inboxScreen.tapOnForApprovalSubTab();
        workflowIDList = inboxScreen.getAllCNAWorkflowId();
        ExplicitDelegationScreen explicitDelegationScreen = inboxScreen.delegateAllWorkflow();
        inboxScreen = explicitDelegationScreen.delegateWorkflow(prop.getProperty("uat.FOUsername01"), WORKFLOW_GMR);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_DELEGATE_ALL_WORKFLOW);
        inboxScreen.logout();

        //---------Login as Delegate user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.FOUsername01"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_GMR, STATUS_OVERDUE);
        inboxScreen.tapOnForApprovalSubTab();
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_DELEGATE_ALL_WORKFLOW);
    }

    //-------------------------------- IPV/FVA ---------------------------------

    @Test(groups = {TEST_GRP_EXPLICIT_DELEGATION, TEST_GRP_IPV_FVA})
    public void delegateFVAWorkflowDetailViewTest() {
        System.out.println("Method: delegateFVAWorkflowDetailViewTest()");
        String workflowID;

        //--------------Explicit Delegation-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername01"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_FVA, STATUS_OVERDUE);
        inboxScreen.tapOnForAcknowledgementSubTab();
        workflowID = inboxScreen.getFirstCNAWorkflowId();
        InboxDetailViewScreen inboxDetailViewScreen = inboxScreen.tapOnWorkflow(workflowID);
        ExplicitDelegationScreen explicitDelegationScreen = inboxDetailViewScreen.tapOnDelegateButton();
        inboxScreen = explicitDelegationScreen.delegateWorkflow(prop.getProperty("uat.DelegateUsername"), WORKFLOW_FVA);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_DELEGATE_WORKFLOW.replace("$1", workflowID));
        inboxScreen.logout();

        //---------Login as Delegate user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_FVA, STATUS_OVERDUE);
        inboxScreen.tapOnForAcknowledgementSubTab();
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_TO_DO),
                FAILED_MSG_FAILED_TO_DELEGATE_WORKFLOW.replace("$1", workflowID));
    }

    @Test(groups = {TEST_GRP_EXPLICIT_DELEGATION, TEST_GRP_IPV_FVA})
    public void swipeToDelegateFVAWorkflowTest() {
        System.out.println("Method: swipeToDelegateFVAWorkflowTest()");
        String workflowID;

        //--------------Explicit Delegation-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername01"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_FVA, STATUS_OVERDUE);
        inboxScreen.tapOnForAcknowledgementSubTab();
        workflowID = inboxScreen.getFirstCNAWorkflowId();
        ExplicitDelegationScreen explicitDelegationScreen = inboxScreen.swipeLeftAndTapOnDelegate(workflowID);
        inboxScreen = explicitDelegationScreen.delegateWorkflow(prop.getProperty("uat.DelegateUsername"), WORKFLOW_FVA);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_DELEGATE_WORKFLOW.replace("$1", workflowID));
        inboxScreen.logout();

        //---------Login as Delegate user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_FVA, STATUS_OVERDUE);
        inboxScreen.tapOnForAcknowledgementSubTab();
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_TO_DO),
                FAILED_MSG_FAILED_TO_DELEGATE_WORKFLOW.replace("$1", workflowID));
    }

    @Test(groups = {TEST_GRP_EXPLICIT_DELEGATION_SELECTED, TEST_GRP_IPV_FVA},
            dependsOnMethods = {"delegateFVAWorkflowDetailViewTest", "swipeToDelegateFVAWorkflowTest"})
    public void delegateSelectedFVAWorkflowTest() {
        System.out.println("Method: delegateSelectedFVAWorkflowTest()");
        int count = 1;
        List<String> workflowIDList;
        List<String> allWorkflowIDList;

        //--------------Explicit Delegation-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername01"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_FVA, STATUS_OVERDUE);
        inboxScreen.tapOnForAcknowledgementSubTab();
        SelectMultipleWorkflowScreen selectMultipleWorkflowScreen = inboxScreen
                .navigateToSelectMultipleWorkflowScreen(count, BUCKET_TO_DO, MORE_OPTION_DELEGATE_SELECTED);
        workflowIDList = selectMultipleWorkflowScreen.selectNumberOfCNAWorkflow(count);
        ExplicitDelegationScreen explicitDelegationScreen = selectMultipleWorkflowScreen.tapOnDelegateSelectedScreenDoneButton();
        inboxScreen = explicitDelegationScreen.delegateWorkflow(prop.getProperty("uat.DelegateUsername"), WORKFLOW_FVA);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_DELEGATE_SELECTED_WORKFLOW);
        inboxScreen.logout();

        //---------Login as Delegate user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_FVA, STATUS_OVERDUE);
        inboxScreen.tapOnForAcknowledgementSubTab();
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_DELEGATE_SELECTED_WORKFLOW);
    }

    @Test(groups = {TEST_GRP_EXPLICIT_DELEGATION_ALL, TEST_GRP_IPV_FVA},
            dependsOnMethods = {"delegateSelectedFVAWorkflowTest"})
    public void delegateAllFVAWorkflowTest() {
        System.out.println("Method: delegateSelectedFVAWorkflowTest()");
        List<String> workflowIDList;
        List<String> allWorkflowIDList;

        //--------------Explicit Delegation-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_FVA, STATUS_OVERDUE);
        inboxScreen.tapOnForAcknowledgementSubTab();
        workflowIDList = inboxScreen.getAllCNAWorkflowId();
        ExplicitDelegationScreen explicitDelegationScreen = inboxScreen.delegateAllWorkflow();
        inboxScreen = explicitDelegationScreen.delegateWorkflow(prop.getProperty("uat.FOUsername01"), WORKFLOW_FVA);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_DELEGATE_ALL_WORKFLOW);
        inboxScreen.logout();

        //---------Login as Delegate user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.FOUsername01"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_FVA, STATUS_OVERDUE);
        inboxScreen.tapOnForAcknowledgementSubTab();
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_DELEGATE_ALL_WORKFLOW);
    }

    //-------------------------------- VE ---------------------------------

    @Test(groups = {TEST_GRP_EXPLICIT_DELEGATION, TEST_GRP_VE})
    public void delegateVEWorkflowDetailViewTest() {
        System.out.println("Method: delegateVEWorkflowDetailViewTest()");
        String workflowID;

        //--------------Explicit Delegation-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.VEVDOUsername"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_VE, STATUS_OPEN);
        inboxScreen.tapOnForReviewAndActionSubTab();
        workflowID = inboxScreen.getFirstWorkflowId();
        InboxDetailViewScreen inboxDetailViewScreen = inboxScreen.tapOnWorkflow(workflowID);
        ExplicitDelegationScreen explicitDelegationScreen = inboxDetailViewScreen.tapOnDelegateButton();
        inboxScreen = explicitDelegationScreen.delegateWorkflow(prop.getProperty("uat.DelegateUsername"), WORKFLOW_VE);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_DELEGATE_WORKFLOW.replace("$1", workflowID));
        inboxScreen.logout();

        //---------Login as Delegate user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_VE, STATUS_OPEN);
        inboxScreen.tapOnForReviewAndActionSubTab();
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_TO_DO),
                FAILED_MSG_FAILED_TO_DELEGATE_WORKFLOW.replace("$1", workflowID));
    }

    @Test(groups = {TEST_GRP_EXPLICIT_DELEGATION, TEST_GRP_VE})
    public void swipeToDelegateVEWorkflowTest() {
        System.out.println("Method: swipeToDelegateVEWorkflowTest()");
        String workflowID;

        //--------------Explicit Delegation-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.VEVDOUsername"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_VE, STATUS_OPEN);
        inboxScreen.tapOnForReviewAndActionSubTab();
        workflowID = inboxScreen.getFirstWorkflowId();
        ExplicitDelegationScreen explicitDelegationScreen = inboxScreen.swipeLeftAndTapOnDelegate(workflowID);
        inboxScreen = explicitDelegationScreen.delegateWorkflow(prop.getProperty("uat.DelegateUsername"), WORKFLOW_VE);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_DELEGATE_WORKFLOW.replace("$1", workflowID));
        inboxScreen.logout();

        //---------Login as Delegate user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_VE, STATUS_OPEN);
        inboxScreen.tapOnForReviewAndActionSubTab();
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_TO_DO),
                FAILED_MSG_FAILED_TO_DELEGATE_WORKFLOW.replace("$1", workflowID));
    }

    @Test(groups = {TEST_GRP_EXPLICIT_DELEGATION_SELECTED, TEST_GRP_VE},
            dependsOnMethods = {"delegateVEWorkflowDetailViewTest", "swipeToDelegateVEWorkflowTest"})
    public void delegateSelectedVEWorkflowTest() {
        System.out.println("Method: delegateSelectedVEWorkflowTest()");
        int count = 1;
        List<String> workflowIDList;
        List<String> allWorkflowIDList;

        //--------------Explicit Delegation-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.VEVDOUsername"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_VE, STATUS_OPEN);
        inboxScreen.tapOnForReviewAndActionSubTab();
        SelectMultipleWorkflowScreen selectMultipleWorkflowScreen = inboxScreen
                .navigateToSelectMultipleWorkflowScreen(count, BUCKET_TO_DO, MORE_OPTION_DELEGATE_SELECTED);
        workflowIDList = selectMultipleWorkflowScreen.selectNumberOfWorkflow(count);
        ExplicitDelegationScreen explicitDelegationScreen = selectMultipleWorkflowScreen.tapOnDelegateSelectedScreenDoneButton();
        inboxScreen = explicitDelegationScreen.delegateWorkflow(prop.getProperty("uat.DelegateUsername"), WORKFLOW_VE);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        allWorkflowIDList = inboxScreen.getAllWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_DELEGATE_SELECTED_WORKFLOW);
        inboxScreen.logout();

        //---------Login as Delegate user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_VE, STATUS_OPEN);
        inboxScreen.tapOnForReviewAndActionSubTab();
        allWorkflowIDList = inboxScreen.getAllWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_DELEGATE_SELECTED_WORKFLOW);
    }

    //-------------------------------- CE ---------------------------------

    @Test(groups = {TEST_GRP_EXPLICIT_DELEGATION, TEST_GRP_CE})
    public void delegateCEWorkflowDetailViewTest() {
        System.out.println("Method: delegateCEWorkflowDetailViewTest()");
        String workflowID;

        //--------------Explicit Delegation-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DealerSupervisor"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CE, STATUS_OPEN);
        inboxScreen.tapOnForReviewAndActionSubTab();
        workflowID = inboxScreen.getFirstCNAWorkflowId();
        InboxDetailViewScreen inboxDetailViewScreen = inboxScreen.tapOnWorkflow(workflowID);
        ExplicitDelegationScreen explicitDelegationScreen = inboxDetailViewScreen.tapOnDelegateButton();
        inboxScreen = explicitDelegationScreen.delegateWorkflow(prop.getProperty("uat.DelegateUsername"), WORKFLOW_CE);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_DELEGATE_WORKFLOW.replace("$1", workflowID));
        inboxScreen.logout();

        //---------Login as Delegate user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CE, STATUS_OPEN);
        inboxScreen.tapOnForReviewAndActionSubTab();
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_TO_DO),
                FAILED_MSG_FAILED_TO_DELEGATE_WORKFLOW.replace("$1", workflowID));
    }

    @Test(groups = {TEST_GRP_EXPLICIT_DELEGATION, TEST_GRP_CE})
    public void swipeToDelegateCEWorkflowTest() {
        System.out.println("Method: swipeToDelegateCEWorkflowTest()");
        String workflowID;

        //--------------Explicit Delegation-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DealerSupervisor"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CE, STATUS_OPEN);
        inboxScreen.tapOnForReviewAndActionSubTab();
        workflowID = inboxScreen.getFirstCNAWorkflowId();
        ExplicitDelegationScreen explicitDelegationScreen = inboxScreen.swipeLeftAndTapOnDelegate(workflowID);
        inboxScreen = explicitDelegationScreen.delegateWorkflow(prop.getProperty("uat.DelegateUsername"), WORKFLOW_CE);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_DELEGATE_WORKFLOW.replace("$1", workflowID));
        inboxScreen.logout();

        //---------Login as Delegate user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CE, STATUS_OPEN);
        inboxScreen.tapOnForReviewAndActionSubTab();
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_TO_DO),
                FAILED_MSG_FAILED_TO_DELEGATE_WORKFLOW.replace("$1", workflowID));
    }

    @Test(groups = {TEST_GRP_EXPLICIT_DELEGATION_SELECTED, TEST_GRP_CE}/*,
            dependsOnMethods = {"delegateCEWorkflowDetailViewTest", "swipeToDelegateCEWorkflowTest"}*/)
    public void delegateSelectedCEWorkflowTest() {
        System.out.println("Method: delegateSelectedCEWorkflowTest()");
        int count = 1;
        List<String> workflowIDList;
        List<String> allWorkflowIDList;

        //--------------Explicit Delegation-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DealerSupervisor"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CE, STATUS_OPEN);
        inboxScreen.tapOnForReviewAndActionSubTab();
        SelectMultipleWorkflowScreen selectMultipleWorkflowScreen = inboxScreen
                .navigateToSelectMultipleWorkflowScreen(count, BUCKET_TO_DO, MORE_OPTION_DELEGATE_SELECTED);
        workflowIDList = selectMultipleWorkflowScreen.selectNumberOfCNAWorkflow(count);
        ExplicitDelegationScreen explicitDelegationScreen = selectMultipleWorkflowScreen.tapOnDelegateSelectedScreenDoneButton();
        inboxScreen = explicitDelegationScreen.delegateWorkflow(prop.getProperty("uat.DelegateUsername"), WORKFLOW_CE);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_DELEGATE_SELECTED_WORKFLOW);
        inboxScreen.logout();

        //---------Login as Delegate user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CE, STATUS_OPEN);
        inboxScreen.tapOnForReviewAndActionSubTab();
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_DELEGATE_SELECTED_WORKFLOW);
    }

    @Test(groups = {TEST_GRP_EXPLICIT_DELEGATION_ALL, TEST_GRP_CE},
            dependsOnMethods = {"delegateSelectedCEWorkflowTest", "delegateCEWorkflowDetailViewTest", "swipeToDelegateCEWorkflowTest"})
    public void delegateAllCEWorkflowTest() {
        System.out.println("Method: delegateSelectedCEWorkflowTest()");
        List<String> workflowIDList;
        List<String> allWorkflowIDList;

        //--------------Explicit Delegation-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CE, STATUS_OPEN);
        inboxScreen.tapOnForReviewAndActionSubTab();
        workflowIDList = inboxScreen.getAllCNAWorkflowId();
        ExplicitDelegationScreen explicitDelegationScreen = inboxScreen.delegateAllWorkflow();
        inboxScreen = explicitDelegationScreen.delegateWorkflow(prop.getProperty("uat.DealerSupervisor"), WORKFLOW_CE);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_DELEGATE_ALL_WORKFLOW);
        inboxScreen.logout();

        //---------Login as Delegate user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.DealerSupervisor"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CE, STATUS_OPEN);
        inboxScreen.tapOnForReviewAndActionSubTab();
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_DELEGATE_ALL_WORKFLOW);
    }
}
