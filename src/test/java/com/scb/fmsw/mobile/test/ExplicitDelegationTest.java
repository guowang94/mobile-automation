package com.scb.fmsw.mobile.test;

import com.scb.fmsw.mobile.base.BaseTest;
import com.scb.fmsw.mobile.screen.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class ExplicitDelegationTest extends BaseTest {

    //create test method for CNA, OMR, PNL, GT/GMR, IPV/FVA

    //-------------------------------- CNA ---------------------------------

    //todo maybe need to check for workflow status, if domt meed to check for workflow status then i need to change the method to ignore null value
    @Test(groups = {TEST_GRP_EXPLICIT_DELEGATION, TEST_GRP_CNA})
    public void delegateCNAWorkflowDetailViewTest() {
        System.out.println("Method: delegateCNAWorkflowDetailViewTest()");
        String workflowID;

        //--------------Explicit Delegation-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CNA, STATUS_OVERDUE);
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
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CNA, STATUS_OVERDUE);
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
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CNA, STATUS_OVERDUE);
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
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CNA, STATUS_OVERDUE);
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

        OverviewScreen overviewScreen = login(prop.getProperty("uat.OMRUsername"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_OMR, STATUS_OPEN);
        inboxScreen.navigateToBucket(BUCKET_TO_DO);
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
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_OMR, STATUS_OPEN);
        inboxScreen.navigateToBucket(BUCKET_TO_REVIEW);
        inboxScreen.tapOnForAcknowledgementSubTab();
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_TO_REVIEW),
                FAILED_MSG_FAILED_TO_DELEGATE_WORKFLOW.replace("$1", workflowID));
    }

    @Test(groups = {TEST_GRP_EXPLICIT_DELEGATION, TEST_GRP_OMR})
    public void swipeToDelegateOMRWorkflowTest() {
        System.out.println("Method: swipeToDelegateOMRWorkflowTest()");
        String workflowID;

        //--------------Explicit Delegation-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.OMRUsername"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_OMR, STATUS_OPEN);
        inboxScreen.navigateToBucket(BUCKET_TO_DO);
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
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_OMR, STATUS_OPEN);
        inboxScreen.navigateToBucket(BUCKET_TO_REVIEW);
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

        OverviewScreen overviewScreen = login(prop.getProperty("uat.OMRUsername"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_OMR, STATUS_OPEN);
        inboxScreen.navigateToBucket(BUCKET_TO_DO);
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
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_OMR, STATUS_OPEN);
        inboxScreen.navigateToBucket(BUCKET_TO_REVIEW);
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
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_OMR, STATUS_OPEN);
        inboxScreen.navigateToBucket(BUCKET_TO_DO);
        inboxScreen.tapOnForAcknowledgementSubTab();
        workflowIDList = inboxScreen.getAllCNAWorkflowId();
        ExplicitDelegationScreen explicitDelegationScreen = inboxScreen.delegateAllWorkflow();
        inboxScreen = explicitDelegationScreen.delegateWorkflow(prop.getProperty("uat.OMRUsername"), WORKFLOW_OMR);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_DELEGATE_ALL_WORKFLOW);
        inboxScreen.logout();

        //---------Login as Delegate user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.OMRUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_OMR, STATUS_OPEN);
        inboxScreen.navigateToBucket(BUCKET_TO_REVIEW);
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

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegationUsername"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_PNL, STATUS_OPEN);
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
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_PNL, STATUS_OPEN);
        inboxScreen.tapOnForReviewAndAcceptanceSubTab();
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_TO_DO),
                FAILED_MSG_FAILED_TO_DELEGATE_WORKFLOW.replace("$1", workflowID));
    }

    @Test(groups = {TEST_GRP_EXPLICIT_DELEGATION, TEST_GRP_PNL})
    public void swipeToDelegatePNLWorkflowTest() {
        System.out.println("Method: swipeToDelegatePNLWorkflowTest()");
        String workflowID;

        //--------------Explicit Delegation-------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegationUsername"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_PNL, STATUS_OPEN);
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
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_PNL, STATUS_OPEN);
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

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegationUsername"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_PNL, STATUS_OPEN);
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
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_PNL, STATUS_OPEN);
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
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_PNL, STATUS_OPEN);
        inboxScreen.tapOnForReviewAndAcceptanceSubTab();
        workflowIDList = inboxScreen.getAllCNAWorkflowId();
        ExplicitDelegationScreen explicitDelegationScreen = inboxScreen.delegateAllWorkflow();
        inboxScreen = explicitDelegationScreen.delegateWorkflow(prop.getProperty("uat.DelegationUsername"), WORKFLOW_PNL);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_DELEGATE_ALL_WORKFLOW);
        inboxScreen.logout();

        //---------Login as Delegate user to check if workflow is there-------

        overviewScreen = login(prop.getProperty("uat.DelegationUsername"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_PNL, STATUS_OPEN);
        inboxScreen.tapOnForReviewAndAcceptanceSubTab();
        allWorkflowIDList = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(allWorkflowIDList, workflowIDList), FAILED_MSG_FAILED_TO_DELEGATE_ALL_WORKFLOW);
    }
}
