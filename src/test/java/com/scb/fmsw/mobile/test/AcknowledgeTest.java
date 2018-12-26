package com.scb.fmsw.mobile.test;

import com.scb.fmsw.mobile.base.BaseTest;
import com.scb.fmsw.mobile.screen.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class AcknowledgeTest extends BaseTest {

    //create test method for CNA, OMR, PNL, GT/GMR, IPV/FVA, CE, VE

    //-------------------------------- CNA ---------------------------------

    /*@Test(groups = {TEST_GRP_ACKNOWLEDGE, TEST_GRP_CNA})*/
    public void acknowledgeCNAWorkflowDetailViewTest() {
        System.out.println("Method: acknowledgeCNAWorkflowDetailViewTest()");
        String workflowID;

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CNA, STATUS_OPEN);
        inboxScreen.navigateToBucket(BUCKET_TO_REVIEW);
        inboxScreen.tapOnForAcknowledgementSubTab();
        workflowID = inboxScreen.getFirstCNAWorkflowId();
        InboxDetailViewScreen detailViewScreen = inboxScreen.tapOnWorkflow(workflowID, true);
        AcknowledgeScreen acknowledgeScreen = detailViewScreen.tapOnAcknowledgeButton();
        inboxScreen = acknowledgeScreen.acknowledgeWorkflow(null, WORKFLOW_CNA, 1);
        inboxScreen.navigateToBucket(BUCKET_CLOSED);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_CLOSED),
                FAILED_MSG_FAILED_TO_ACKNOWLEDGE_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_ACKNOWLEDGED, WORKFLOW_CNA, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        System.out.println("Complete!");
    }

    /*@Test(groups = {TEST_GRP_ACKNOWLEDGE, TEST_GRP_CNA})*/
    public void swipeToAcknowledgeCNAWorkflowTest() {
        System.out.println("Method: swipeToAcknowledgeCNAWorkflowTest()");
        String workflowID;

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CNA, STATUS_OPEN);
        inboxScreen.navigateToBucket(BUCKET_TO_REVIEW);
        inboxScreen.tapOnForAcknowledgementSubTab();
        workflowID = inboxScreen.getFirstCNAWorkflowId();
        AcknowledgeScreen acknowledgeScreen = inboxScreen.swipeRightAndTapOnAcknowledge(workflowID, WORKFLOW_CNA);
        inboxScreen = acknowledgeScreen.acknowledgeWorkflow(null, WORKFLOW_CNA, 1);
        inboxScreen.navigateToBucket(BUCKET_CLOSED);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_CLOSED),
                FAILED_MSG_FAILED_TO_ACKNOWLEDGE_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_ACKNOWLEDGED, WORKFLOW_CNA, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        System.out.println("Complete!");
    }

    /*@Test(groups = {TEST_GRP_ACKNOWLEDGE_SELECTED, TEST_GRP_CNA})*/
    public void acknowledgeSelectedCNAWorkflowTest() {
        System.out.println("Method: acknowledgeSelectedCNAWorkflowTest()");
        int workflowCount = 1;
        List<String> workflowIDList;
        List<String> workflowIDInClosedBucket;

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CNA, STATUS_OPEN);
        inboxScreen.navigateToBucket(BUCKET_TO_REVIEW);
        inboxScreen.tapOnForAcknowledgementSubTab();
        SelectMultipleWorkflowScreen selectMultipleWorkflowScreen = inboxScreen
                .navigateToSelectMultipleWorkflowScreen(workflowCount, BUCKET_TO_REVIEW, MORE_OPTION_ACKNOWLEDGE_SELECTED);
        workflowIDList = selectMultipleWorkflowScreen.selectNumberOfCNAWorkflow(workflowCount);
        AcknowledgeScreen acknowledgeScreen = selectMultipleWorkflowScreen
                .tapOnAcknowledgeSelectedScreenDoneButton();
        inboxScreen = acknowledgeScreen.acknowledgeWorkflow(null, WORKFLOW_CNA, workflowCount);
        inboxScreen.navigateToBucket(BUCKET_CLOSED);
        workflowIDInClosedBucket = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(workflowIDInClosedBucket, workflowIDList),
                FAILED_MSG_FAILED_TO_ACKNOWLEDGE_SELECTED_WORKFLOW);
        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_ACKNOWLEDGE_ALL, TEST_GRP_CNA}/*,
            dependsOnMethods = {"acknowledgeSelectedCNAWorkflowTest", "swipeToAcknowledgeCNAWorkflowTest", "acknowledgeCNAWorkflowDetailViewTest"}*/)
    public void acknowledgeAllCNAWorkflowTest() {
        System.out.println("Method: acknowledgeAllCNAWorkflowTest()");
        List<String> workflowIDList;
        List<String> workflowIDInClosedBucket;

//        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CNA, STATUS_OPEN);
        inboxScreen.navigateToBucket(BUCKET_TO_REVIEW);
        inboxScreen.tapOnForAcknowledgementSubTab();
        workflowIDList = inboxScreen.getAllCNAWorkflowId();
        AcknowledgeScreen acknowledgeScreen = inboxScreen
                .acknowledgeAllWorkflow(MORE_OPTION_ACKNOWLEDGE_ALL);
        inboxScreen = acknowledgeScreen.acknowledgeWorkflow(null, WORKFLOW_CNA, workflowIDList.size());
        inboxScreen.navigateToBucket(BUCKET_CLOSED);
        workflowIDInClosedBucket = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(workflowIDInClosedBucket, workflowIDList),
                FAILED_MSG_FAILED_TO_ACKNOWLEDGE_ALL_WORKFLOW);
        System.out.println("Complete!");
    }

    //-------------------------------- OMR ---------------------------------

    @Test(groups = {TEST_GRP_ACKNOWLEDGE, TEST_GRP_OMR})
    public void acknowledgeOMRWorkflowDetailViewTest() {
        System.out.println("Method: acknowledgeOMRWorkflowDetailViewTest()");
        String workflowID;

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_OMR, STATUS_OPEN);
        inboxScreen.navigateToBucket(BUCKET_TO_DO);
        inboxScreen.tapOnForAcknowledgementSubTab();
        workflowID = inboxScreen.getFirstWorkflowId();
        InboxDetailViewScreen detailViewScreen = inboxScreen.tapOnWorkflow(workflowID, true);
        AcknowledgeScreen acknowledgeScreen = detailViewScreen.tapOnAcknowledgeButton();
        inboxScreen = acknowledgeScreen.acknowledgeOMRWorkflow(LATE_CODE_DEADLINE_MISSED,
                ACKNOWLEDGEMENT_CODE_HRR_DEAL, WORKFLOW_OMR, 1);
        inboxScreen.navigateToBucket(BUCKET_CLOSED);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_CLOSED),
                FAILED_MSG_FAILED_TO_ACKNOWLEDGE_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_ACKNOWLEDGED, WORKFLOW_OMR, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_ACKNOWLEDGE, TEST_GRP_OMR})
    public void swipeToAcknowledgeOMRWorkflowTest() {
        System.out.println("Method: swipeToAcknowledgeOMRWorkflowTest()");
        String workflowID;

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_OMR, STATUS_OPEN);
        inboxScreen.navigateToBucket(BUCKET_TO_DO);
        inboxScreen.tapOnForAcknowledgementSubTab();
        workflowID = inboxScreen.getFirstCNAWorkflowId();
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

    @Test(groups = {TEST_GRP_ACKNOWLEDGE_SELECTED, TEST_GRP_OMR})
    public void acknowledgeSelectedOMRWorkflowTest() {
        System.out.println("Method: acknowledgeSelectedOMRWorkflowTest()");
        int workflowCount = 1;
        List<String> workflowIDList;
        List<String> workflowIDInClosedBucket;

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_OMR, STATUS_OPEN);
        inboxScreen.tapOnForAcknowledgementSubTab();
        SelectMultipleWorkflowScreen selectMultipleWorkflowScreen = inboxScreen
                .navigateToSelectMultipleWorkflowScreen(workflowCount, BUCKET_TO_DO, MORE_OPTION_ACKNOWLEDGE_SELECTED);
        workflowIDList = selectMultipleWorkflowScreen.selectNumberOfCNAWorkflow(workflowCount);
        AcknowledgeScreen acknowledgeScreen = selectMultipleWorkflowScreen
                .tapOnAcknowledgeSelectedScreenDoneButton();
        inboxScreen = acknowledgeScreen.acknowledgeOMRWorkflow(null,
                ACKNOWLEDGEMENT_CODE_HRR_DEAL, WORKFLOW_OMR, workflowCount);
        inboxScreen.navigateToBucket(BUCKET_CLOSED);
        workflowIDInClosedBucket = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(workflowIDInClosedBucket, workflowIDList),
                FAILED_MSG_FAILED_TO_ACKNOWLEDGE_SELECTED_WORKFLOW);
        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_ACKNOWLEDGE_ALL, TEST_GRP_OMR},
            dependsOnMethods = {"acknowledgeSelectedOMRWorkflowTest", "acknowledgeOMRWorkflowDetailViewTest", "swipeToAcknowledgeOMRWorkflowTest"})
    public void acknowledgeAllOMRWorkflowTest() {
        System.out.println("Method: acknowledgeAllOMRWorkflowTest()");
        List<String> workflowIDList;
        List<String> workflowIDInClosedBucket;

        OverviewScreen overviewScreen = login(prop.getProperty("uat.OMRUsername"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_OMR, STATUS_OPEN);
        inboxScreen.navigateToBucket(BUCKET_TO_DO);
        inboxScreen.tapOnForAcknowledgementSubTab();
        workflowIDList = inboxScreen.getAllCNAWorkflowId();
        AcknowledgeScreen acknowledgeScreen = inboxScreen.acknowledgeAllWorkflow(MORE_OPTION_ACKNOWLEDGE_ALL);
        inboxScreen = acknowledgeScreen.acknowledgeOMRWorkflow(null,
                ACKNOWLEDGEMENT_CODE_HRR_DEAL, WORKFLOW_OMR, workflowIDList.size());
        inboxScreen.navigateToBucket(BUCKET_CLOSED);
        workflowIDInClosedBucket = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(workflowIDInClosedBucket, workflowIDList),
                FAILED_MSG_FAILED_TO_ACKNOWLEDGE_ALL_WORKFLOW);
        System.out.println("Complete!");
    }

    //-------------------------------- CE ---------------------------------

    //--------- MTCR USER -----------

    @Test(groups = {TEST_GRP_ACKNOWLEDGE, TEST_GRP_CE})
    public void reviewAndAssessCEWorkflowDetailViewTest() {
        System.out.println("Method: reviewAndAssessCEWorkflowDetailViewTest()");

        //------------------Login as MTCR user to Review and Assess---------------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.MTCRUsername"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CE, STATUS_OPEN);
        inboxScreen.tapOnForReviewSubTab();
        String workflowID = inboxScreen.getFirstCNAWorkflowId();
        InboxDetailViewScreen inboxDetailViewScreen = inboxScreen.tapOnWorkflow(workflowID, true);
        AcknowledgeScreen acknowledgeScreen = inboxDetailViewScreen.tapOnAcknowledgeButton();
        inboxScreen = acknowledgeScreen.reviewAndAssessWorkflow(CE_LATE_CODE_OTHERS, CE_SEVERITY_HIGH,
                CE_POTENTIAL_LOSS_YES, WORKFLOW_CE, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_REVIEW_AND_ASSESS_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_FO_REVIEW, WORKFLOW_CE, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_ACKNOWLEDGE, TEST_GRP_CE})
    public void swipeToReviewAndAssessCEWorkflowTest() {
        System.out.println("Method: swipeToReviewAndAssessCEWorkflowTest()");

        //------------------Login as MTCR user to Review and Assess---------------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.MTCRUsername"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CE, STATUS_OPEN);
        inboxScreen.tapOnForReviewSubTab();
        String workflowID = inboxScreen.getFirstCNAWorkflowId();
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

    @Test(groups = {TEST_GRP_ACKNOWLEDGE_SELECTED, TEST_GRP_CE})
    public void reviewAndAssessSelectedCEWorkflowTest() {
        System.out.println("Method: reviewAndAssessSelectedCEWorkflowTest()");
        int workflowCount = 1;

        //------------------Login as MTCR user to Review and Assess---------------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.MTCRUsername"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CE, STATUS_OPEN);
        inboxScreen.tapOnForReviewSubTab();
        SelectMultipleWorkflowScreen selectMultipleWorkflowScreen = inboxScreen
                .navigateToSelectMultipleWorkflowScreen(workflowCount, BUCKET_TO_DO,
                        MORE_OPTION_REVIEW_AND_ASSESS_SELECTED);
        List<String> workflowIDList = selectMultipleWorkflowScreen.selectNumberOfCNAWorkflow(workflowCount);
        AcknowledgeScreen acknowledgeScreen = selectMultipleWorkflowScreen.tapOnAcknowledgeSelectedScreenDoneButton();
        inboxScreen = acknowledgeScreen.reviewAndAssessWorkflow(null, CE_SEVERITY_HIGH,
                CE_POTENTIAL_LOSS_YES, WORKFLOW_CE, workflowCount);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        List<String> workflowIDInBucket = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(workflowIDInBucket, workflowIDList),
                FAILED_MSG_FAILED_TO_ACKNOWLEDGE_SELECTED_WORKFLOW);
        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_ACKNOWLEDGE_ALL, TEST_GRP_CE},
            dependsOnMethods = {"reviewAndAssessSelectedCEWorkflowTest", "reviewAndAssessCEWorkflowDetailViewTest", "swipeToReviewAndAssessCEWorkflowTest"})
    public void reviewAndAssessAllCEWorkflowTest() {
        System.out.println("Method: reviewAndAssessAllCEWorkflowTest()");

        //------------------Login as MTCR user to Review and Assess---------------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.MTCRUsername"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CE, STATUS_OPEN);
        inboxScreen.tapOnForReviewSubTab();
        List<String> workflowIDList = inboxScreen.getAllCNAWorkflowId();
        AcknowledgeScreen acknowledgeScreen = inboxScreen.acknowledgeAllWorkflow(MORE_OPTION_REVIEW_AND_ASSESS_ALL);
        inboxScreen = acknowledgeScreen.reviewAndAssessWorkflow(null, CE_SEVERITY_HIGH,
                CE_POTENTIAL_LOSS_YES, WORKFLOW_CE, workflowIDList.size());
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        List<String> workflowIDInBucket = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(workflowIDInBucket, workflowIDList),
                FAILED_MSG_FAILED_TO_ACKNOWLEDGE_SELECTED_WORKFLOW);
        System.out.println("Complete!");
    }

    //------ DEALER SUPERVISOR -------

    @Test(groups = {TEST_GRP_ACKNOWLEDGE, TEST_GRP_CE},
            dependsOnMethods = {"reviewAndAssessAllCEWorkflowTest"})
    public void reviewAndApproveCEWorkflowDetailViewTest() {
        System.out.println("Method: reviewAndApproveCEWorkflowDetailViewTest()");

        //------------------Login as Dealer's LM to Review and Approve---------------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DealerSupervisor"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CE, STATUS_OPEN);
        inboxScreen.tapOnForReviewSubTab();
        String workflowID = inboxScreen.getFirstCNAWorkflowId();
        InboxDetailViewScreen inboxDetailViewScreen = inboxScreen.tapOnWorkflow(workflowID, true);
        AcknowledgeScreen acknowledgeScreen = inboxDetailViewScreen.tapOnAcknowledgeButton();
        inboxScreen = acknowledgeScreen.reviewAndApproveWorkflow(CE_LATE_CODE_OTHERS, CE_DISCIPLINARY_ACTION_DISMISSAL,
                WORKFLOW_CE, 1);
        inboxScreen.navigateToBucket(BUCKET_CLOSED);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_CLOSED),
                FAILED_MSG_FAILED_TO_REVIEW_AND_APPROVE_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(
                WORKFLOW_STATUS_REVIEWED_AND_DISCIPLINARY_ACTION_TAKEN, WORKFLOW_CE, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_ACKNOWLEDGE, TEST_GRP_CE},
            dependsOnMethods = {"reviewAndAssessAllCEWorkflowTest"})
    public void swipeToReviewAndApproveCEWorkflowTest() {
        System.out.println("Method: swipeToReviewAndApproveCEWorkflowTest()");

        //------------------Login as Dealer's LM to Review and Approve---------------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DealerSupervisor"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CE, STATUS_OPEN);
        inboxScreen.tapOnForReviewSubTab();
        String workflowID = inboxScreen.getFirstCNAWorkflowId();
        AcknowledgeScreen acknowledgeScreen = inboxScreen.swipeRightAndTapOnAcknowledge(workflowID, WORKFLOW_CE);
        inboxScreen = acknowledgeScreen.reviewAndApproveWorkflow(null, CE_DISCIPLINARY_ACTION_DISMISSAL,
                WORKFLOW_CE, 1);
        inboxScreen.navigateToBucket(BUCKET_CLOSED);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_CLOSED),
                FAILED_MSG_FAILED_TO_REVIEW_AND_APPROVE_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(
                WORKFLOW_STATUS_REVIEWED_AND_DISCIPLINARY_ACTION_TAKEN, WORKFLOW_CE, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_ACKNOWLEDGE_SELECTED, TEST_GRP_CE},
            dependsOnMethods = {"reviewAndAssessAllCEWorkflowTest"})
    public void reviewAndApproveSelectedCEWorkflowTest() {
        System.out.println("Method: reviewAndApproveSelectedCEWorkflowTest()");
        int workflowCount = 1;

        //------------------Login as Dealer's LM to Review and Approve---------------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DealerSupervisor"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CE, STATUS_OPEN);
        inboxScreen.tapOnForReviewSubTab();
        SelectMultipleWorkflowScreen selectMultipleWorkflowScreen = inboxScreen
                .navigateToSelectMultipleWorkflowScreen(workflowCount, BUCKET_TO_DO,
                        MORE_OPTION_REVIEW_AND_APPROVE_SELECTED);
        List<String> workflowIDList = selectMultipleWorkflowScreen.selectNumberOfCNAWorkflow(workflowCount);
        AcknowledgeScreen acknowledgeScreen = selectMultipleWorkflowScreen
                .tapOnAcknowledgeSelectedScreenDoneButton();
        inboxScreen = acknowledgeScreen.reviewAndApproveWorkflow(null, CE_DISCIPLINARY_ACTION_DISMISSAL,
                WORKFLOW_CE, workflowCount);
        inboxScreen.navigateToBucket(BUCKET_CLOSED);
        List<String> workflowIDInBucket = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(workflowIDInBucket, workflowIDList),
                FAILED_MSG_FAILED_TO_ACKNOWLEDGE_SELECTED_WORKFLOW);
        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_ACKNOWLEDGE_ALL, TEST_GRP_CE},
            dependsOnMethods = {"reviewAndApproveSelectedCEWorkflowTest", "swipeToReviewAndApproveCEWorkflowTest", "reviewAndApproveCEWorkflowDetailViewTest"})
    public void reviewAndApproveAllCEWorkflowTest() {
        System.out.println("Method: reviewAndApproveAllCEWorkflowTest()");

        //------------------Login as Dealer's LM to Review and Approve---------------------

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DealerSupervisor"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CE, STATUS_OPEN);
        inboxScreen.tapOnForReviewSubTab();
        List<String> workflowIDList = inboxScreen.getAllCNAWorkflowId();
        AcknowledgeScreen acknowledgeScreen = inboxScreen.acknowledgeAllWorkflow(MORE_OPTION_REVIEW_AND_APPROVE_ALL);
        inboxScreen = acknowledgeScreen.reviewAndApproveWorkflow(null, CE_DISCIPLINARY_ACTION_DISMISSAL,
                WORKFLOW_CE, workflowIDList.size());
        inboxScreen.navigateToBucket(BUCKET_CLOSED);
        List<String> workflowIDInBucket = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(workflowIDInBucket, workflowIDList),
                FAILED_MSG_FAILED_TO_ACKNOWLEDGE_SELECTED_WORKFLOW);
        System.out.println("Complete!");
    }

    //-------------------------------- IPV/FVA ---------------------------------

    @Test(groups = {TEST_GRP_ACKNOWLEDGE, TEST_GRP_IPV_FVA})
    public void acknowledgeFVAWorkflowDetailViewTest() {
        System.out.println("Method: acknowledgeFVAWorkflowDetailViewTest()");
        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_FVA, STATUS_OPEN);
        inboxScreen.tapOnForAcknowledgementSubTab();
        String workflowID = inboxScreen.getFirstCNAWorkflowId();
        InboxDetailViewScreen detailViewScreen = inboxScreen.tapOnWorkflow(workflowID, true);
        AcknowledgeScreen acknowledgeScreen = detailViewScreen.tapOnAcknowledgeButton();
        inboxScreen = acknowledgeScreen.acknowledgeWorkflow(null, WORKFLOW_FVA, 1);
        inboxScreen.navigateToBucket(BUCKET_CLOSED);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_CLOSED),
                FAILED_MSG_FAILED_TO_ACKNOWLEDGE_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_ACKNOWLEDGED, WORKFLOW_FVA, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_ACKNOWLEDGE, TEST_GRP_IPV_FVA})
    public void swipeToAcknowledgeFVAWorkflowTest() {
        System.out.println("Method: swipeToAcknowledgeFVAWorkflowTest()");
        String workflowID;

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_FVA, STATUS_OPEN);
        inboxScreen.tapOnForAcknowledgementSubTab();
        workflowID = inboxScreen.getFirstCNAWorkflowId();
        AcknowledgeScreen acknowledgeScreen = inboxScreen.swipeRightAndTapOnAcknowledge(workflowID, WORKFLOW_FVA);
        inboxScreen = acknowledgeScreen.acknowledgeWorkflow(null, WORKFLOW_FVA, 1);
        inboxScreen.navigateToBucket(BUCKET_CLOSED);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_CLOSED),
                FAILED_MSG_FAILED_TO_ACKNOWLEDGE_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_ACKNOWLEDGED, WORKFLOW_FVA, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_ACKNOWLEDGE_SELECTED, TEST_GRP_IPV_FVA})
    public void acknowledgeSelectedFVAWorkflowTest() {
        System.out.println("Method: acknowledgeSelectedFVAWorkflowTest()");
        int workflowCount = 1;
        List<String> workflowIDList;
        List<String> workflowIDInClosedBucket;

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_FVA, STATUS_OPEN);
        inboxScreen.navigateToBucket(BUCKET_TO_DO);
        inboxScreen.tapOnForAcknowledgementSubTab();
        SelectMultipleWorkflowScreen selectMultipleWorkflowScreen = inboxScreen
                .navigateToSelectMultipleWorkflowScreen(workflowCount, BUCKET_TO_DO, MORE_OPTION_ACKNOWLEDGE_SELECTED);
        workflowIDList = selectMultipleWorkflowScreen.selectNumberOfCNAWorkflow(workflowCount);
        AcknowledgeScreen acknowledgeScreen = selectMultipleWorkflowScreen
                .tapOnAcknowledgeSelectedScreenDoneButton();
        inboxScreen = acknowledgeScreen.acknowledgeWorkflow(null, WORKFLOW_FVA, workflowCount);
        inboxScreen.navigateToBucket(BUCKET_CLOSED);
        workflowIDInClosedBucket = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(workflowIDInClosedBucket, workflowIDList),
                FAILED_MSG_FAILED_TO_ACKNOWLEDGE_SELECTED_WORKFLOW);
        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_ACKNOWLEDGE_ALL, TEST_GRP_IPV_FVA},
            dependsOnMethods = {"acknowledgeSelectedFVAWorkflowTest", "swipeToAcknowledgeFVAWorkflowTest", "acknowledgeFVAWorkflowDetailViewTest"})
    public void acknowledgeAllFVAWorkflowTest() {
        System.out.println("Method: acknowledgeAllFVAWorkflowTest()");
        List<String> workflowIDList;
        List<String> workflowIDInClosedBucket;

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_FVA, STATUS_OPEN);
        inboxScreen.navigateToBucket(BUCKET_TO_DO);
        inboxScreen.tapOnForAcknowledgementSubTab();
        workflowIDList = inboxScreen.getAllCNAWorkflowId();
        AcknowledgeScreen acknowledgeScreen = inboxScreen.acknowledgeAllWorkflow(MORE_OPTION_ACKNOWLEDGE_ALL);
        inboxScreen = acknowledgeScreen.acknowledgeWorkflow(null, WORKFLOW_FVA, workflowIDList.size());
        inboxScreen.navigateToBucket(BUCKET_CLOSED);
        workflowIDInClosedBucket = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(workflowIDInClosedBucket, workflowIDList),
                FAILED_MSG_FAILED_TO_ACKNOWLEDGE_ALL_WORKFLOW);
        System.out.println("Complete!");
    }

    //-------------------------------- PNL ---------------------------------

    @Test(groups = {TEST_GRP_ACKNOWLEDGE, TEST_GRP_PNL})
    public void reviewAndAcceptPNLWorkflowDetailViewTest() {
        System.out.println("Method: reviewAndAcceptPNLWorkflowDetailViewTest()");

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_PNL, STATUS_OPEN);
        inboxScreen.tapOnForReviewAndAcceptanceSubTab();
        String workflowID = inboxScreen.getFirstCNAWorkflowId();
        InboxDetailViewScreen detailViewScreen = inboxScreen.tapOnWorkflow(workflowID, true);
        AcknowledgeScreen acknowledgeScreen = detailViewScreen.tapOnAcknowledgeButton();
        inboxScreen = acknowledgeScreen.acknowledgeWorkflow(null, WORKFLOW_PNL, 1);
        inboxScreen.navigateToBucket(BUCKET_CLOSED);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_CLOSED),
                FAILED_MSG_FAILED_TO_ACKNOWLEDGE_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_REVIEWED_AND_ACCEPTED, WORKFLOW_PNL, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_ACKNOWLEDGE, TEST_GRP_PNL})
    public void swipeToReviewAndAcceptPNLWorkflowTest() {
        System.out.println("Method: swipeToReviewAndAcceptPNLWorkflowTest()");

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_PNL, STATUS_OPEN);
        inboxScreen.tapOnForReviewAndAcceptanceSubTab();
        String workflowID = inboxScreen.getFirstCNAWorkflowId();
        AcknowledgeScreen acknowledgeScreen = inboxScreen.swipeRightAndTapOnAcknowledge(workflowID, WORKFLOW_PNL);
        inboxScreen = acknowledgeScreen.acknowledgeWorkflow(null, WORKFLOW_PNL, 1);
        inboxScreen.navigateToBucket(BUCKET_CLOSED);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_CLOSED),
                FAILED_MSG_FAILED_TO_ACKNOWLEDGE_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_REVIEWED_AND_ACCEPTED, WORKFLOW_PNL, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_ACKNOWLEDGE_SELECTED, TEST_GRP_PNL})
    public void reviewAndAcceptSelectedPNLWorkflowTest() {
        System.out.println("Method: reviewAndAcceptSelectedPNLWorkflowTest()");
        int workflowCount = 1;
        List<String> workflowIDList;
        List<String> workflowIDInClosedBucket;

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_PNL, STATUS_OPEN);
        inboxScreen.tapOnForReviewAndAcceptanceSubTab();
        SelectMultipleWorkflowScreen selectMultipleWorkflowScreen = inboxScreen
                .navigateToSelectMultipleWorkflowScreen(workflowCount, BUCKET_TO_DO,
                        MORE_OPTION_REVIEW_AND_ACCEPT_SELECTED);
        workflowIDList = selectMultipleWorkflowScreen.selectNumberOfCNAWorkflow(workflowCount);
        AcknowledgeScreen acknowledgeScreen = selectMultipleWorkflowScreen
                .tapOnAcknowledgeSelectedScreenDoneButton();
        inboxScreen = acknowledgeScreen.acknowledgeWorkflow(null, WORKFLOW_PNL, workflowCount);
        inboxScreen.navigateToBucket(BUCKET_CLOSED);
        workflowIDInClosedBucket = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(workflowIDInClosedBucket, workflowIDList),
                FAILED_MSG_FAILED_TO_ACKNOWLEDGE_SELECTED_WORKFLOW);
        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_ACKNOWLEDGE_ALL, TEST_GRP_PNL},
            dependsOnMethods = {"reviewAndAcceptSelectedPNLWorkflowTest", "swipeToReviewAndAcceptPNLWorkflowTest", "reviewAndAcceptPNLWorkflowDetailViewTest"})
    public void reviewAndAcceptAllPNLWorkflowTest() {
        System.out.println("Method: reviewAndAcceptAllPNLWorkflowTest()");
        List<String> workflowIDList;
        List<String> workflowIDInClosedBucket;

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_PNL, STATUS_OPEN);
        inboxScreen.tapOnForReviewAndAcceptanceSubTab();
        workflowIDList = inboxScreen.getAllCNAWorkflowId();
        AcknowledgeScreen acknowledgeScreen = inboxScreen
                .acknowledgeAllWorkflow(MORE_OPTION_REVIEW_AND_ACCEPT_ALL);
        inboxScreen = acknowledgeScreen.acknowledgeWorkflow(null, WORKFLOW_PNL,
                workflowIDList.size());
        inboxScreen.navigateToBucket(BUCKET_CLOSED);
        workflowIDInClosedBucket = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(workflowIDInClosedBucket, workflowIDList),
                FAILED_MSG_FAILED_TO_ACKNOWLEDGE_ALL_WORKFLOW);
        System.out.println("Complete!");
    }

    //-------------------------------- GT/GMR ---------------------------------

    @Test(groups = {TEST_GRP_ACKNOWLEDGE, TEST_GRP_GT_GMR})
    public void approveGMRWorkflowDetailViewTest() {
        System.out.println("Method: approveGMRWorkflowDetailViewTest(), Concurrent Workflow");
        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_GMR, STATUS_OPEN);
        inboxScreen.tapOnForApprovalSubTab();
        String workflowID = inboxScreen.getFirstWorkflowId();
        String subWorkflowID = inboxScreen.getSubWorkflowId(workflowID);
        InboxDetailViewScreen detailViewScreen = inboxScreen.tapOnWorkflow(subWorkflowID, true);
        AcknowledgeScreen acknowledgeScreen = detailViewScreen.tapOnAcknowledgeButton();
        inboxScreen = acknowledgeScreen.acknowledgeWorkflow(null, WORKFLOW_GMR, 1);
        inboxScreen.navigateToBucket(BUCKET_CLOSED);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(subWorkflowID, 1, BUCKET_CLOSED),
                FAILED_MSG_FAILED_TO_ACKNOWLEDGE_WORKFLOW.replace("$1", subWorkflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_APPROVED, WORKFLOW_GMR, subWorkflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", subWorkflowID));
        inboxScreen.logout();

        overviewScreen = login(prop.getProperty("uat.FOUsername01"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_GMR, STATUS_OPEN);
        inboxScreen.tapOnForApprovalSubTab();
        subWorkflowID = inboxScreen.getSubWorkflowId(workflowID);
        detailViewScreen = inboxScreen.tapOnWorkflow(subWorkflowID, true);
        acknowledgeScreen = detailViewScreen.tapOnAcknowledgeButton();
        inboxScreen = acknowledgeScreen.acknowledgeWorkflow(null, WORKFLOW_GMR, 1);
        inboxScreen.navigateToBucket(BUCKET_CLOSED);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(subWorkflowID, 1, BUCKET_CLOSED),
                FAILED_MSG_FAILED_TO_ACKNOWLEDGE_WORKFLOW.replace("$1", subWorkflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_APPROVED, WORKFLOW_GMR, subWorkflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", subWorkflowID));
        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_ACKNOWLEDGE, TEST_GRP_GT_GMR})
    public void swipeToApproveGMRWorkflowDetailViewTest() {
        System.out.println("Method: swipeToApproveGMRWorkflowDetailViewTest(), Concurrent Workflow");
        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_GMR, STATUS_OPEN);
        inboxScreen.tapOnForApprovalSubTab();
        String workflowID = inboxScreen.getFirstWorkflowId();
        String subWorkflowID = inboxScreen.getSubWorkflowId(workflowID);
        AcknowledgeScreen acknowledgeScreen = inboxScreen.swipeRightAndTapOnAcknowledge(workflowID, WORKFLOW_GMR);
        inboxScreen = acknowledgeScreen.acknowledgeWorkflow(null, WORKFLOW_GMR, 1);
        inboxScreen.navigateToBucket(BUCKET_CLOSED);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(subWorkflowID, 1, BUCKET_CLOSED),
                FAILED_MSG_FAILED_TO_ACKNOWLEDGE_WORKFLOW.replace("$1", subWorkflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_APPROVED, WORKFLOW_GMR, subWorkflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", subWorkflowID));
        inboxScreen.logout();

        overviewScreen = login(prop.getProperty("uat.FOUsername01"));
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_GMR, STATUS_OPEN);
        inboxScreen.tapOnForApprovalSubTab();
        subWorkflowID = inboxScreen.getSubWorkflowId(workflowID);
        acknowledgeScreen = inboxScreen.swipeRightAndTapOnAcknowledge(workflowID, WORKFLOW_GMR);
        inboxScreen = acknowledgeScreen.acknowledgeWorkflow(null, WORKFLOW_GMR, 1);
        inboxScreen.navigateToBucket(BUCKET_CLOSED);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(subWorkflowID, 1, BUCKET_CLOSED),
                FAILED_MSG_FAILED_TO_ACKNOWLEDGE_WORKFLOW.replace("$1", subWorkflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_APPROVED, WORKFLOW_GMR, subWorkflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", subWorkflowID));
        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_ACKNOWLEDGE_SELECTED, TEST_GRP_GT_GMR})
    public void approveSelectedGMRWorkflowTest() {
        System.out.println("Method: approveSelectedGMRWorkflowTest()");
        int workflowCount = 1;
        List<String> workflowIDList;
        List<String> workflowIDInClosedBucket;

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_GMR, STATUS_OPEN);
        inboxScreen.tapOnForApprovalSubTab();
        SelectMultipleWorkflowScreen selectMultipleWorkflowScreen = inboxScreen
                .navigateToSelectMultipleWorkflowScreen(workflowCount, BUCKET_TO_DO,
                        MORE_OPTION_APPROVE_SELECTED);
        workflowIDList = selectMultipleWorkflowScreen.selectNumberOfCNAWorkflow(workflowCount);
        AcknowledgeScreen acknowledgeScreen = selectMultipleWorkflowScreen
                .tapOnAcknowledgeSelectedScreenDoneButton();
        inboxScreen = acknowledgeScreen.acknowledgeWorkflow(null, WORKFLOW_GMR, workflowCount);
        inboxScreen.navigateToBucket(BUCKET_CLOSED);
        workflowIDInClosedBucket = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(workflowIDInClosedBucket, workflowIDList),
                FAILED_MSG_FAILED_TO_ACKNOWLEDGE_SELECTED_WORKFLOW);
        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_ACKNOWLEDGE_ALL, TEST_GRP_GT_GMR},
            dependsOnMethods = {"approveSelectedGMRWorkflowTest", "approveGMRWorkflowDetailViewTest", "swipeToApproveGMRWorkflowDetailViewTest"})
    public void approveAllGMRWorkflowTest() {
        System.out.println("Method: approveAllGMRWorkflowTest()");
        List<String> workflowIDList;
        List<String> workflowIDInClosedBucket;

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_GMR, STATUS_OPEN);
        inboxScreen.tapOnForReviewAndAcceptanceSubTab();
        workflowIDList = inboxScreen.getAllCNAWorkflowId();
        AcknowledgeScreen acknowledgeScreen = inboxScreen
                .acknowledgeAllWorkflow(MORE_OPTION_APPROVE_ALL);
        inboxScreen = acknowledgeScreen.acknowledgeWorkflow(null, WORKFLOW_GMR,
                workflowIDList.size());
        inboxScreen.navigateToBucket(BUCKET_CLOSED);
        workflowIDInClosedBucket = inboxScreen.getAllCNAWorkflowId();
        Assert.assertTrue(compareLists(workflowIDInClosedBucket, workflowIDList),
                FAILED_MSG_FAILED_TO_ACKNOWLEDGE_ALL_WORKFLOW);
        System.out.println("Complete!");
    }

    //-------------------------------- VE ---------------------------------

    @Test(groups = {TEST_GRP_ACKNOWLEDGE, TEST_GRP_VE})
    public void reviewAndActionWorkflowDetailViewTest() {
        System.out.println("Method: reviewAndActionWorkflowDetailViewTest()");
        String workflowID;

        OverviewScreen overviewScreen = login(prop.getProperty("uat.VEVDOUsername"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_VE, STATUS_OPEN);
        inboxScreen.tapOnForReviewAndActionSubTab();
        workflowID = inboxScreen.getFirstWorkflowId();
        InboxDetailViewScreen detailViewScreen = inboxScreen.tapOnWorkflow(workflowID, true);
        AcknowledgeScreen acknowledgeScreen = detailViewScreen.tapOnAcknowledgeButton();
        inboxScreen = acknowledgeScreen.reviewAndActionWorkflow(VE_LATE_CODE_INVESTIGATION_WITH_HR,
                VE_DISCIPLINARY_ACTION_COACHING_OR_COUNSELING, WORKFLOW_VE, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_REVIEW_AND_ACTION_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_COMPLIANCE_ACTION_UT, WORKFLOW_VE, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));

        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_ACKNOWLEDGE, TEST_GRP_VE})
    public void swipeToReviewAndActionWorkflowTest() {
        System.out.println("Method: swipeToReviewAndActionWorkflowTest()");
        String workflowID;

        OverviewScreen overviewScreen = login(prop.getProperty("uat.VEVDOUsername"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_VE, STATUS_OPEN);
        inboxScreen.tapOnForReviewAndActionSubTab();
        workflowID = inboxScreen.getFirstWorkflowId();
        AcknowledgeScreen acknowledgeScreen = inboxScreen.swipeRightAndTapOnAcknowledge(workflowID, WORKFLOW_VE);
        inboxScreen = acknowledgeScreen.reviewAndActionWorkflow(null, VE_DISCIPLINARY_ACTION_COACHING_OR_COUNSELING, WORKFLOW_VE, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_REVIEW_AND_ACTION_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_COMPLIANCE_ACTION_UT, WORKFLOW_VE, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));

        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_ACKNOWLEDGE_SELECTED, TEST_GRP_VE})
    public void reviewAndActionSelectedWorkflowDetailViewTest() {
        System.out.println("Method: reviewAndActionSelectedWorkflowDetailViewTest()");
        List<String> workflowIDList;
        int workflowCount = 1;

        OverviewScreen overviewScreen = login(prop.getProperty("uat.VEVDOUsername"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_VE, STATUS_OPEN);
        inboxScreen.tapOnForReviewAndActionSubTab();
        SelectMultipleWorkflowScreen selectMultipleWorkflowScreen = inboxScreen.navigateToSelectMultipleWorkflowScreen(workflowCount, BUCKET_TO_DO, MORE_OPTION_REVIEW_AND_ACTION_SELECTED);
        workflowIDList = selectMultipleWorkflowScreen.selectNumberOfWorkflow(workflowCount);
        AcknowledgeScreen acknowledgeScreen = selectMultipleWorkflowScreen.tapOnAcknowledgeSelectedScreenDoneButton();
        inboxScreen = acknowledgeScreen.reviewAndActionWorkflow(null, VE_DISCIPLINARY_ACTION_COACHING_OR_COUNSELING, WORKFLOW_VE, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowIDList.get(0), 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_REVIEW_AND_ACTION_WORKFLOW.replace("$1", workflowIDList.get(0)));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_COMPLIANCE_ACTION_UT, WORKFLOW_VE, workflowIDList.get(0)),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowIDList.get(0)));

        System.out.println("Complete!");
    }

    //-------------------------------- TM ---------------------------------

    @Test(groups = {TEST_GRP_ACKNOWLEDGE, TEST_GRP_TM})
    public void approveTMWorkflowDetailViewTest() {
        System.out.println("Method: approveTMWorkflowDetailViewTest()");
        String workflowID;
        String workflowStatus;
        String nextUserID = null;

        //Need to provide the Supervisor ID
        OverviewScreen overviewScreen = login("1460477");
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_TM, STATUS_OPEN);
        inboxScreen.tapOnForApprovalSubTab();
        //Need to provide the Workflow ID
        workflowID = "TMN0000257388";
        InboxDetailViewScreen detailViewScreen = inboxScreen.tapOnWorkflow(workflowID, false);

        if (detailViewScreen != null) {
            workflowStatus = detailViewScreen.getWorkflowStatusValue();

            if (WORKFLOW_STATUS_PENDING_SUPERVISOR_APPROVAL.equals(workflowStatus)) {

                AcknowledgeScreen acknowledgeScreen = detailViewScreen.tapOnAcknowledgeButton();
                inboxScreen = acknowledgeScreen.acknowledgeWorkflow(null, WORKFLOW_TM, 1);
                inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
                Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                        FAILED_MSG_FAILED_TO_APPROVE_WORKFLOW.replace("$1", workflowID));
                Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(
                        WORKFLOW_STATUS_PENDING_TRADER_APPROVAL, WORKFLOW_TM, workflowID),
                        FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));

                detailViewScreen = inboxScreen.navigateToDetailView(workflowID);
                nextUserID = detailViewScreen.getCurrActorValue();
                inboxScreen = detailViewScreen.tapOnBackButton();
                inboxScreen.logout();
            }
        } else {
            inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
            detailViewScreen = inboxScreen.tapOnWorkflow(workflowID, true);
            workflowStatus = detailViewScreen.getWorkflowStatusValue();

            if (WORKFLOW_STATUS_PENDING_TRADER_APPROVAL.equals(workflowStatus)) {
                nextUserID = detailViewScreen.getCurrActorValue();
                inboxScreen = detailViewScreen.tapOnBackButton();
                inboxScreen.logout();
            }
        }

        //----------- Login as Trader --------------

        overviewScreen = login(nextUserID);
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_TM, STATUS_OPEN);
        inboxScreen.tapOnForApprovalSubTab();
        detailViewScreen = inboxScreen.tapOnWorkflow(workflowID, true);
        AcknowledgeScreen acknowledgeScreen = detailViewScreen.tapOnAcknowledgeButton();
        inboxScreen = acknowledgeScreen.acknowledgeWorkflow(null, WORKFLOW_TM, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_APPROVE_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(
                WORKFLOW_STATUS_PENDING_FM_HEAD_APPROVAL, WORKFLOW_TM, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));

        detailViewScreen = inboxScreen.navigateToDetailView(workflowID);
        nextUserID = detailViewScreen.getCurrActorValue();
        inboxScreen = detailViewScreen.tapOnBackButton();
        inboxScreen.logout();

        //----------- Login as FM Head ------------

        overviewScreen = login(nextUserID);
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_TM, STATUS_OPEN);
        inboxScreen.tapOnForApprovalSubTab();
        detailViewScreen = inboxScreen.tapOnWorkflow(workflowID, true);
        acknowledgeScreen = detailViewScreen.tapOnAcknowledgeButton();
        inboxScreen = acknowledgeScreen.acknowledgeWorkflow(null, WORKFLOW_TM, 1);
        inboxScreen.navigateToBucket(BUCKET_CLOSED);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_CLOSED),
                FAILED_MSG_FAILED_TO_APPROVE_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(
                WORKFLOW_STATUS_APPROVED, WORKFLOW_TM, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));


        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_ACKNOWLEDGE, TEST_GRP_TM})
    public void swipeToApproveTMWorkflowDetailViewTest() {
        System.out.println("Method: swipeToApproveTMWorkflowDetailViewTest()");
        String workflowID;
        String workflowStatus;
        String nextUserID = null;

        //Need to provide the Supervisor ID
        OverviewScreen overviewScreen = login("1460477");
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_TM, STATUS_OPEN);
        inboxScreen.tapOnForApprovalSubTab();
        //Need to provide the Workflow ID
        workflowID = "TMN0000257500";
        InboxDetailViewScreen detailViewScreen = inboxScreen.tapOnWorkflow(workflowID, false);

        if (detailViewScreen != null) {
            workflowStatus = detailViewScreen.getWorkflowStatusValue();

            if (WORKFLOW_STATUS_PENDING_SUPERVISOR_APPROVAL.equals(workflowStatus)) {

                inboxScreen = detailViewScreen.tapOnBackButton();
                AcknowledgeScreen acknowledgeScreen = inboxScreen.swipeRightAndTapOnAcknowledge(workflowID, WORKFLOW_TM);
                inboxScreen = acknowledgeScreen.acknowledgeWorkflow(null, WORKFLOW_TM, 1);
                inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
                Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                        FAILED_MSG_FAILED_TO_APPROVE_WORKFLOW.replace("$1", workflowID));
                Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(
                        WORKFLOW_STATUS_PENDING_TRADER_APPROVAL, WORKFLOW_TM, workflowID),
                        FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));

                detailViewScreen = inboxScreen.navigateToDetailView(workflowID);
                nextUserID = detailViewScreen.getCurrActorValue();
                inboxScreen = detailViewScreen.tapOnBackButton();
                inboxScreen.logout();
            }
        } else {
            inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
            detailViewScreen = inboxScreen.tapOnWorkflow(workflowID, true);
            workflowStatus = detailViewScreen.getWorkflowStatusValue();

            if (WORKFLOW_STATUS_PENDING_TRADER_APPROVAL.equals(workflowStatus)) {
                nextUserID = detailViewScreen.getCurrActorValue();
                inboxScreen = detailViewScreen.tapOnBackButton();
                inboxScreen.logout();
            }
        }

        //----------- Login as Trader --------------

        overviewScreen = login(nextUserID);
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_TM, STATUS_OPEN);
        inboxScreen.tapOnForApprovalSubTab();
        AcknowledgeScreen acknowledgeScreen = inboxScreen.swipeRightAndTapOnAcknowledge(workflowID, WORKFLOW_TM);
        inboxScreen = acknowledgeScreen.acknowledgeWorkflow(null, WORKFLOW_TM, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_APPROVE_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(
                WORKFLOW_STATUS_PENDING_FM_HEAD_APPROVAL, WORKFLOW_TM, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));

        detailViewScreen = inboxScreen.navigateToDetailView(workflowID);
        nextUserID = detailViewScreen.getCurrActorValue();
        inboxScreen = detailViewScreen.tapOnBackButton();
        inboxScreen.logout();

        //----------- Login as FM Head ------------

        overviewScreen = login(nextUserID);
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_TM, STATUS_OPEN);
        inboxScreen.tapOnForApprovalSubTab();
        acknowledgeScreen = inboxScreen.swipeRightAndTapOnAcknowledge(workflowID, WORKFLOW_TM);
        inboxScreen = acknowledgeScreen.acknowledgeWorkflow(null, WORKFLOW_TM, 1);
        inboxScreen.navigateToBucket(BUCKET_CLOSED);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_CLOSED),
                FAILED_MSG_FAILED_TO_APPROVE_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(
                WORKFLOW_STATUS_APPROVED, WORKFLOW_TM, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));


        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_ACKNOWLEDGE, TEST_GRP_TM, TEST_GRP_ACKNOWLEDGE_SELECTED})
    public void approveSelectedTMWorkflowDetailViewTest() {
        System.out.println("Method: approveSelectedTMWorkflowDetailViewTest()");
        String workflowID;
        String workflowStatus;
        String nextUserID = null;
        int workflowCount = 1;

        //Need to provide the Supervisor ID
        OverviewScreen overviewScreen = login("1460477");
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_TM, STATUS_OPEN);
        inboxScreen.tapOnForApprovalSubTab();
        //Need to provide the Workflow ID
        workflowID = "TMN0000257510";
        InboxDetailViewScreen detailViewScreen = inboxScreen.tapOnWorkflow(workflowID, false);

        if (detailViewScreen != null) {
            workflowStatus = detailViewScreen.getWorkflowStatusValue();

            if (WORKFLOW_STATUS_PENDING_SUPERVISOR_APPROVAL.equals(workflowStatus)) {

                inboxScreen = detailViewScreen.tapOnBackButton();
                SelectMultipleWorkflowScreen selectMultipleWorkflowScreen = inboxScreen
                        .navigateToSelectMultipleWorkflowScreen(workflowCount, BUCKET_TO_DO, MORE_OPTION_APPROVE_SELECTED);
                selectMultipleWorkflowScreen.selectNumberOfCNAWorkflow(workflowID);
                AcknowledgeScreen acknowledgeScreen = selectMultipleWorkflowScreen.tapOnAcknowledgeSelectedScreenDoneButton();
                inboxScreen = acknowledgeScreen.acknowledgeWorkflow(null, WORKFLOW_TM, 1);
                inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
                Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                        FAILED_MSG_FAILED_TO_APPROVE_WORKFLOW.replace("$1", workflowID));
                Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(
                        WORKFLOW_STATUS_PENDING_TRADER_APPROVAL, WORKFLOW_TM, workflowID),
                        FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));

                detailViewScreen = inboxScreen.navigateToDetailView(workflowID);
                nextUserID = detailViewScreen.getCurrActorValue();
                inboxScreen = detailViewScreen.tapOnBackButton();
                inboxScreen.logout();
            }
        } else {
            inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
            detailViewScreen = inboxScreen.tapOnWorkflow(workflowID, true);
            workflowStatus = detailViewScreen.getWorkflowStatusValue();

            if (WORKFLOW_STATUS_PENDING_TRADER_APPROVAL.equals(workflowStatus)) {
                nextUserID = detailViewScreen.getCurrActorValue();
                inboxScreen = detailViewScreen.tapOnBackButton();
                inboxScreen.logout();
            }
        }

        //----------- Login as Trader --------------

        overviewScreen = login(nextUserID);
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_TM, STATUS_OPEN);
        inboxScreen.tapOnForApprovalSubTab();
        SelectMultipleWorkflowScreen selectMultipleWorkflowScreen = inboxScreen
                .navigateToSelectMultipleWorkflowScreen(workflowCount, BUCKET_TO_DO, MORE_OPTION_APPROVE_SELECTED);
        selectMultipleWorkflowScreen.selectNumberOfCNAWorkflow(workflowID);
        AcknowledgeScreen acknowledgeScreen = selectMultipleWorkflowScreen.tapOnAcknowledgeSelectedScreenDoneButton();
        inboxScreen = acknowledgeScreen.acknowledgeWorkflow(null, WORKFLOW_TM, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_APPROVE_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(
                WORKFLOW_STATUS_PENDING_FM_HEAD_APPROVAL, WORKFLOW_TM, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));

        detailViewScreen = inboxScreen.navigateToDetailView(workflowID);
        nextUserID = detailViewScreen.getCurrActorValue();
        inboxScreen = detailViewScreen.tapOnBackButton();
        inboxScreen.logout();

        //----------- Login as FM Head ------------

        overviewScreen = login(nextUserID);
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_TM, STATUS_OPEN);
        inboxScreen.tapOnForApprovalSubTab();
        selectMultipleWorkflowScreen = inboxScreen
                .navigateToSelectMultipleWorkflowScreen(workflowCount, BUCKET_TO_DO, MORE_OPTION_APPROVE_SELECTED);
        selectMultipleWorkflowScreen.selectNumberOfCNAWorkflow(workflowID);
        acknowledgeScreen = selectMultipleWorkflowScreen.tapOnAcknowledgeSelectedScreenDoneButton();
        inboxScreen = acknowledgeScreen.acknowledgeWorkflow(null, WORKFLOW_TM, 1);
        inboxScreen.navigateToBucket(BUCKET_CLOSED);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_CLOSED),
                FAILED_MSG_FAILED_TO_APPROVE_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(
                WORKFLOW_STATUS_APPROVED, WORKFLOW_TM, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));


        System.out.println("Complete!");
    }

    @Test(groups = {TEST_GRP_ACKNOWLEDGE, TEST_GRP_TM, TEST_GRP_ACKNOWLEDGE_ALL},
            dependsOnMethods = {"approveTMWorkflowDetailViewTest", "swipeToApproveTMWorkflowDetailViewTest",
                    "approveSelectedTMWorkflowDetailViewTest"})
    public void approveAllTMWorkflowDetailViewTest() {
        System.out.println("Method: approveAllTMWorkflowDetailViewTest()");
        List<String> workflowID = new ArrayList<>();
        String workflowStatus;
        String nextUserID = null;

        //Need to provide the Supervisor ID
        OverviewScreen overviewScreen = login("1460477");
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_TM, STATUS_OPEN);
        inboxScreen.tapOnForApprovalSubTab();
        //Need to provide the Workflow ID
        workflowID.add("TMN0000257513");
        InboxDetailViewScreen detailViewScreen = inboxScreen.tapOnWorkflow(workflowID.get(0), false);

        if (detailViewScreen != null) {
            workflowStatus = detailViewScreen.getWorkflowStatusValue();

            if (WORKFLOW_STATUS_PENDING_SUPERVISOR_APPROVAL.equals(workflowStatus)) {

                inboxScreen = detailViewScreen.tapOnBackButton();
                AcknowledgeScreen acknowledgeScreen = inboxScreen.acknowledgeAllWorkflow(MORE_OPTION_APPROVE_ALL);
                inboxScreen = acknowledgeScreen.acknowledgeWorkflow(null, WORKFLOW_TM, 1);
                inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
                Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID.get(0), 1, BUCKET_IN_PROGRESS),
                        FAILED_MSG_FAILED_TO_APPROVE_WORKFLOW.replace("$1", workflowID.get(0)));
                Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(
                        WORKFLOW_STATUS_PENDING_TRADER_APPROVAL, WORKFLOW_TM, workflowID.get(0)),
                        FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID.get(0)));

                detailViewScreen = inboxScreen.navigateToDetailView(workflowID.get(0));
                nextUserID = detailViewScreen.getCurrActorValue();
                inboxScreen = detailViewScreen.tapOnBackButton();
                inboxScreen.logout();
            }
        } else {
            inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
            detailViewScreen = inboxScreen.tapOnWorkflow(workflowID.get(0), true);
            workflowStatus = detailViewScreen.getWorkflowStatusValue();

            if (WORKFLOW_STATUS_PENDING_TRADER_APPROVAL.equals(workflowStatus)) {
                nextUserID = detailViewScreen.getCurrActorValue();
                inboxScreen = detailViewScreen.tapOnBackButton();
                inboxScreen.logout();
            }
        }

        //----------- Login as Trader --------------

        overviewScreen = login(nextUserID);
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_TM, STATUS_OPEN);
        inboxScreen.tapOnForApprovalSubTab();
        AcknowledgeScreen acknowledgeScreen = inboxScreen.acknowledgeAllWorkflow(MORE_OPTION_APPROVE_ALL);
        inboxScreen = acknowledgeScreen.acknowledgeWorkflow(null, WORKFLOW_TM, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID.get(0), 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_APPROVE_WORKFLOW.replace("$1", workflowID.get(0)));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(
                WORKFLOW_STATUS_PENDING_FM_HEAD_APPROVAL, WORKFLOW_TM, workflowID.get(0)),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID.get(0)));

        detailViewScreen = inboxScreen.navigateToDetailView(workflowID.get(0));
        nextUserID = detailViewScreen.getCurrActorValue();
        inboxScreen = detailViewScreen.tapOnBackButton();
        inboxScreen.logout();

        //----------- Login as FM Head ------------

        overviewScreen = login(nextUserID);
        inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_TM, STATUS_OPEN);
        inboxScreen.tapOnForApprovalSubTab();
        acknowledgeScreen = inboxScreen.acknowledgeAllWorkflow(MORE_OPTION_APPROVE_ALL);
        inboxScreen = acknowledgeScreen.acknowledgeWorkflow(null, WORKFLOW_TM, 1);
        inboxScreen.navigateToBucket(BUCKET_CLOSED);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID.get(0), 1, BUCKET_CLOSED),
                FAILED_MSG_FAILED_TO_APPROVE_WORKFLOW.replace("$1", workflowID.get(0)));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(
                WORKFLOW_STATUS_APPROVED, WORKFLOW_TM, workflowID.get(0)),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID.get(0)));


        System.out.println("Complete!");
    }
}
