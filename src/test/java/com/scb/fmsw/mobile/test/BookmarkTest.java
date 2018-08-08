package com.scb.fmsw.mobile.test;

import com.scb.fmsw.mobile.base.BaseTest;
import com.scb.fmsw.mobile.screen.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class BookmarkTest extends BaseTest {

    //create test method for CNA, OMR, PNL, GT/GMR, IPV/FVA, VE, CE

    //-------------------------------- CNA ---------------------------------

    @Test(groups = {TEST_GRP_BOOKMARK, TEST_GRP_CNA})
    public void cnaBookmarkDetailViewTest() {
        System.out.println("Method: cnaBookmarkDetailViewTest");
        String workflowID;

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername01"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CNA, STATUS_OPEN);
        inboxScreen.navigateToBucket(BUCKET_TO_REVIEW);
        inboxScreen.tapOnForAcknowledgementSubTab();
        workflowID = inboxScreen.getFirstCNAWorkflowId();
        InboxDetailViewScreen detailViewScreen = inboxScreen.tapOnWorkflow(workflowID);
        BookmarkScreen bookmarkScreen = detailViewScreen.tapOnBookmarkButton();
        inboxScreen = bookmarkScreen.bookmarkWorkflow(WORKFLOW_CNA, 1);
        detailViewScreen = inboxScreen.tapOnWorkflow(workflowID);
        Assert.assertEquals(detailViewScreen.getBookmarkValue(WORKFLOW_CNA), "Y",
                FAILED_MSG_FAILED_TO_BOOKMARK_WORKFLOW.replace("$1", workflowID));
    }

    @Test(groups = {TEST_GRP_BOOKMARK, TEST_GRP_CNA})
    public void cnaSwipeBookmarkTest() {
        System.out.println("Method: cnaSwipeBookmarkTest");
        String workflowID;

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername01"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CNA, STATUS_OPEN);
        inboxScreen.navigateToBucket(BUCKET_TO_REVIEW);
        inboxScreen.tapOnForAcknowledgementSubTab();
        workflowID = inboxScreen.getFirstCNAWorkflowId();
        BookmarkScreen bookmarkScreen = inboxScreen.swipeLeftAndTapOnBookmark(workflowID);
        inboxScreen = bookmarkScreen.bookmarkWorkflow(WORKFLOW_CNA, 1);
        InboxDetailViewScreen detailViewScreen = inboxScreen.tapOnWorkflow(workflowID);
        Assert.assertEquals(detailViewScreen.getBookmarkValue(WORKFLOW_CNA), "Y",
                FAILED_MSG_FAILED_TO_BOOKMARK_WORKFLOW.replace("$1", workflowID));
    }

    @Test(groups = {TEST_GRP_BOOKMARK_SELECTED, TEST_GRP_CNA},
            dependsOnMethods = {"cnaSwipeBookmarkTest", "cnaBookmarkDetailViewTest"})
    public void cnaBookmarkSelectedTest() {
        System.out.println("Method: cnaBookmarkSelectedTest");
        List<String> workflowIDList;
        int workflowCount = 1;

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername01"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CNA, STATUS_OPEN);
        inboxScreen.navigateToBucket(BUCKET_TO_REVIEW);
        inboxScreen.tapOnForAcknowledgementSubTab();
        SelectMultipleWorkflowScreen selectMultipleWorkflowScreen = inboxScreen
                .navigateToSelectMultipleWorkflowScreen(workflowCount, BUCKET_TO_REVIEW,
                        MORE_OPTION_BOOKMARK_SELECTED);
        workflowIDList = selectMultipleWorkflowScreen.selectNumberOfCNAWorkflow(workflowCount);
        BookmarkScreen bookmarkScreen = selectMultipleWorkflowScreen.tapOnBookmarkSelectedScreenDoneButton();
        inboxScreen = bookmarkScreen.bookmarkWorkflow(WORKFLOW_CNA, workflowCount);

        InboxDetailViewScreen detailViewScreen = inboxScreen.tapOnWorkflow(workflowIDList.get(0));
        Assert.assertEquals(detailViewScreen.getBookmarkValue(WORKFLOW_CNA), "Y",
                FAILED_MSG_FAILED_TO_BOOKMARK_SELECTED_WORKFLOW.replace("$1", workflowIDList.get(0)));
    }

    @Test(groups = {TEST_GRP_BOOKMARK_ALL, TEST_GRP_CNA},
            dependsOnMethods = {"cnaBookmarkSelectedTest"})
    public void cnaBookmarkAllTest() {
        System.out.println("Method: cnaBookmarkAllTest");
        List<String> workflowIDList;

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername01"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CNA, STATUS_OPEN);
        inboxScreen.navigateToBucket(BUCKET_TO_DO);
        inboxScreen.tapOnForAcknowledgementSubTab();
        workflowIDList = inboxScreen.getAllCNAWorkflowId();
        BookmarkScreen bookmarkScreen = inboxScreen.bookmarkAllWorkflow();
        inboxScreen = bookmarkScreen.bookmarkWorkflow(WORKFLOW_CNA, workflowIDList.size());

        for (String workflowID : workflowIDList) {
            InboxDetailViewScreen detailViewScreen = inboxScreen.tapOnWorkflow(workflowID);
            Assert.assertEquals(detailViewScreen.getBookmarkValue(WORKFLOW_CNA), "Y",
                    FAILED_MSG_FAILED_TO_BOOKMARK_ALL_WORKFLOW.replace("$1", workflowID));
            inboxScreen = detailViewScreen.tapOnBackButton();
        }
    }

    //-------------------------------- OMR ---------------------------------

    @Test(groups = {TEST_GRP_BOOKMARK, TEST_GRP_OMR})
    public void omrBookmarkDetailViewTest() {
        System.out.println("Method: omrBookmarkDetailViewTest");
        String workflowID;

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_OMR, STATUS_OVERDUE);
        inboxScreen.tapOnForAcknowledgementSubTab();
        workflowID = inboxScreen.getFirstCNAWorkflowId();
        InboxDetailViewScreen detailViewScreen = inboxScreen.tapOnWorkflow(workflowID);
        BookmarkScreen bookmarkScreen = detailViewScreen.tapOnBookmarkButton();
        inboxScreen = bookmarkScreen.bookmarkWorkflow(WORKFLOW_OMR, 1);
        detailViewScreen = inboxScreen.tapOnWorkflow(workflowID);
        Assert.assertEquals(detailViewScreen.getBookmarkValue(WORKFLOW_OMR), "Y",
                FAILED_MSG_FAILED_TO_BOOKMARK_WORKFLOW.replace("$1", workflowID));
    }

    @Test(groups = {TEST_GRP_BOOKMARK, TEST_GRP_OMR})
    public void omrSwipeBookmarkTest() {
        System.out.println("Method: omrSwipeBookmarkTest");
        String workflowID;

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_OMR, STATUS_OVERDUE);
        inboxScreen.tapOnForAcknowledgementSubTab();
        workflowID = inboxScreen.getFirstCNAWorkflowId();
        BookmarkScreen bookmarkScreen = inboxScreen.swipeLeftAndTapOnBookmark(workflowID);
        inboxScreen = bookmarkScreen.bookmarkWorkflow(WORKFLOW_OMR, 1);
        InboxDetailViewScreen detailViewScreen = inboxScreen.tapOnWorkflow(workflowID);
        Assert.assertEquals(detailViewScreen.getBookmarkValue(WORKFLOW_OMR), "Y",
                FAILED_MSG_FAILED_TO_BOOKMARK_WORKFLOW.replace("$1", workflowID));
    }

    @Test(groups = {TEST_GRP_BOOKMARK_SELECTED, TEST_GRP_OMR}/*,
            dependsOnMethods = {"omrSwipeBookmarkTest", "omrBookmarkDetailViewTest"}*/)
    public void omrBookmarkSelectedTest() {
        System.out.println("Method: omrBookmarkSelectedTest");
        List<String> workflowIDList;
        int workflowCount = 1;

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_OMR, STATUS_OVERDUE);
        inboxScreen.tapOnForAcknowledgementSubTab();
        SelectMultipleWorkflowScreen selectMultipleWorkflowScreen = inboxScreen
                .navigateToSelectMultipleWorkflowScreen(workflowCount, BUCKET_TO_DO,
                        MORE_OPTION_BOOKMARK_SELECTED);
        workflowIDList = selectMultipleWorkflowScreen.selectNumberOfCNAWorkflow(workflowCount);
        BookmarkScreen bookmarkScreen = selectMultipleWorkflowScreen.tapOnBookmarkSelectedScreenDoneButton();
        inboxScreen = bookmarkScreen.bookmarkWorkflow(WORKFLOW_OMR, workflowCount);

        InboxDetailViewScreen detailViewScreen = inboxScreen.tapOnWorkflow(workflowIDList.get(0));
        Assert.assertEquals(detailViewScreen.getBookmarkValue(WORKFLOW_OMR), "Y",
                FAILED_MSG_FAILED_TO_BOOKMARK_SELECTED_WORKFLOW.replace("$1", workflowIDList.get(0)));
    }

    @Test(groups = {TEST_GRP_BOOKMARK_ALL, TEST_GRP_OMR},
            dependsOnMethods = {"omrBookmarkSelectedTest"})
    public void omrBookmarkAllTest() {
        System.out.println("Method: omrBookmarkAllTest");
        List<String> workflowIDList;

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_OMR, STATUS_OVERDUE);
        inboxScreen.navigateToBucket(BUCKET_TO_DO);
        inboxScreen.tapOnForAcknowledgementSubTab();
        workflowIDList = inboxScreen.getAllCNAWorkflowId();
        BookmarkScreen bookmarkScreen = inboxScreen.bookmarkAllWorkflow();
        inboxScreen = bookmarkScreen.bookmarkWorkflow(WORKFLOW_OMR, workflowIDList.size());

        for (String workflowID : workflowIDList) {
            InboxDetailViewScreen detailViewScreen = inboxScreen.tapOnWorkflow(workflowID);
            Assert.assertEquals(detailViewScreen.getBookmarkValue(WORKFLOW_OMR), "Y",
                    FAILED_MSG_FAILED_TO_BOOKMARK_ALL_WORKFLOW.replace("$1", workflowID));
            inboxScreen = detailViewScreen.tapOnBackButton();
        }
    }

    //-------------------------------- PNL ---------------------------------

    @Test(groups = {TEST_GRP_BOOKMARK, TEST_GRP_PNL})
    public void pnlBookmarkDetailViewTest() {
        System.out.println("Method: pnlBookmarkDetailViewTest");
        String workflowID;

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername01"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_PNL, STATUS_OVERDUE);
        inboxScreen.tapOnForReviewAndAcceptanceSubTab();
        workflowID = inboxScreen.getFirstCNAWorkflowId();
        InboxDetailViewScreen detailViewScreen = inboxScreen.tapOnWorkflow(workflowID);
        BookmarkScreen bookmarkScreen = detailViewScreen.tapOnBookmarkButton();
        inboxScreen = bookmarkScreen.bookmarkWorkflow(WORKFLOW_PNL, 1);
        detailViewScreen = inboxScreen.tapOnWorkflow(workflowID);
        Assert.assertEquals(detailViewScreen.getBookmarkValue(WORKFLOW_PNL), "Y",
                FAILED_MSG_FAILED_TO_BOOKMARK_WORKFLOW.replace("$1", workflowID));
    }

    @Test(groups = {TEST_GRP_BOOKMARK, TEST_GRP_PNL})
    public void pnlSwipeBookmarkTest() {
        System.out.println("Method: pnlSwipeBookmarkTest");
        String workflowID;

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername01"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_PNL, STATUS_OVERDUE);
        inboxScreen.tapOnForReviewAndAcceptanceSubTab();
        workflowID = inboxScreen.getFirstCNAWorkflowId();
        BookmarkScreen bookmarkScreen = inboxScreen.swipeLeftAndTapOnBookmark(workflowID);
        inboxScreen = bookmarkScreen.bookmarkWorkflow(WORKFLOW_PNL, 1);
        InboxDetailViewScreen detailViewScreen = inboxScreen.tapOnWorkflow(workflowID);
        Assert.assertEquals(detailViewScreen.getBookmarkValue(WORKFLOW_PNL), "Y",
                FAILED_MSG_FAILED_TO_BOOKMARK_WORKFLOW.replace("$1", workflowID));
    }

    @Test(groups = {TEST_GRP_BOOKMARK_SELECTED, TEST_GRP_PNL},
            dependsOnMethods = {"pnlSwipeBookmarkTest", "pnlBookmarkDetailViewTest"})
    public void pnlBookmarkSelectedTest() {
        System.out.println("Method: pnlBookmarkSelectedTest");
        List<String> workflowIDList;
        int workflowCount = 1;

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername01"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_PNL, STATUS_OVERDUE);
        inboxScreen.tapOnForReviewAndAcceptanceSubTab();
        SelectMultipleWorkflowScreen selectMultipleWorkflowScreen = inboxScreen
                .navigateToSelectMultipleWorkflowScreen(workflowCount, BUCKET_TO_DO,
                        MORE_OPTION_BOOKMARK_SELECTED);
        workflowIDList = selectMultipleWorkflowScreen.selectNumberOfCNAWorkflow(workflowCount);
        BookmarkScreen bookmarkScreen = selectMultipleWorkflowScreen.tapOnBookmarkSelectedScreenDoneButton();
        inboxScreen = bookmarkScreen.bookmarkWorkflow(WORKFLOW_PNL, workflowCount);

        InboxDetailViewScreen detailViewScreen = inboxScreen.tapOnWorkflow(workflowIDList.get(0));
        Assert.assertEquals(detailViewScreen.getBookmarkValue(WORKFLOW_PNL), "Y",
                FAILED_MSG_FAILED_TO_BOOKMARK_SELECTED_WORKFLOW.replace("$1", workflowIDList.get(0)));
    }

    @Test(groups = {TEST_GRP_BOOKMARK_ALL, TEST_GRP_PNL},
            dependsOnMethods = {"pnlBookmarkSelectedTest"})
    public void pnlBookmarkAllTest() {
        System.out.println("Method: pnlBookmarkAllTest");
        List<String> workflowIDList;

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername01"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_PNL, STATUS_OVERDUE);
        inboxScreen.navigateToBucket(BUCKET_TO_DO);
        inboxScreen.tapOnForReviewAndAcceptanceSubTab();
        workflowIDList = inboxScreen.getAllCNAWorkflowId();
        BookmarkScreen bookmarkScreen = inboxScreen.bookmarkAllWorkflow();
        inboxScreen = bookmarkScreen.bookmarkWorkflow(WORKFLOW_PNL, workflowIDList.size());

        for (String workflowID : workflowIDList) {
            InboxDetailViewScreen detailViewScreen = inboxScreen.tapOnWorkflow(workflowID);
            Assert.assertEquals(detailViewScreen.getBookmarkValue(WORKFLOW_PNL), "Y",
                    FAILED_MSG_FAILED_TO_BOOKMARK_ALL_WORKFLOW.replace("$1", workflowID));
            inboxScreen = detailViewScreen.tapOnBackButton();
        }
    }

    //-------------------------------- GT/GMR ---------------------------------

    @Test(groups = {TEST_GRP_BOOKMARK, TEST_GRP_GT_GMR})
    public void gmrBookmarkDetailViewTest() {
        System.out.println("Method: gmrBookmarkDetailViewTest");
        String workflowID;

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername01"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_GMR, STATUS_OVERDUE);
        inboxScreen.tapOnForApprovalSubTab();
        workflowID = inboxScreen.getFirstCNAWorkflowId();
        InboxDetailViewScreen detailViewScreen = inboxScreen.tapOnWorkflow(workflowID);
        BookmarkScreen bookmarkScreen = detailViewScreen.tapOnBookmarkButton();
        inboxScreen = bookmarkScreen.bookmarkWorkflow(WORKFLOW_GMR, 1);
        detailViewScreen = inboxScreen.tapOnWorkflow(workflowID);
        Assert.assertEquals(detailViewScreen.getBookmarkValue(WORKFLOW_GMR), "Y",
                FAILED_MSG_FAILED_TO_BOOKMARK_WORKFLOW.replace("$1", workflowID));
    }

    @Test(groups = {TEST_GRP_BOOKMARK, TEST_GRP_GT_GMR})
    public void gmrSwipeBookmarkTest() {
        System.out.println("Method: gmrSwipeBookmarkTest");
        String workflowID;

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername01"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_GMR, STATUS_OVERDUE);
        inboxScreen.tapOnForApprovalSubTab();
        workflowID = inboxScreen.getFirstCNAWorkflowId();
        BookmarkScreen bookmarkScreen = inboxScreen.swipeLeftAndTapOnBookmark(workflowID);
        inboxScreen = bookmarkScreen.bookmarkWorkflow(WORKFLOW_GMR, 1);
        InboxDetailViewScreen detailViewScreen = inboxScreen.tapOnWorkflow(workflowID);
        Assert.assertEquals(detailViewScreen.getBookmarkValue(WORKFLOW_GMR), "Y",
                FAILED_MSG_FAILED_TO_BOOKMARK_WORKFLOW.replace("$1", workflowID));
    }

    @Test(groups = {TEST_GRP_BOOKMARK_SELECTED, TEST_GRP_GT_GMR}/*,
            dependsOnMethods = {"gmrSwipeBookmarkTest", "gmrBookmarkDetailViewTest"}*/)
    public void gmrBookmarkSelectedTest() {
        System.out.println("Method: gmrBookmarkSelectedTest");
        List<String> workflowIDList;
        int workflowCount = 1;

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername01"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_GMR, STATUS_OVERDUE);
        inboxScreen.tapOnForApprovalSubTab();
        SelectMultipleWorkflowScreen selectMultipleWorkflowScreen = inboxScreen
                .navigateToSelectMultipleWorkflowScreen(workflowCount, BUCKET_TO_DO,
                        MORE_OPTION_BOOKMARK_SELECTED);
        workflowIDList = selectMultipleWorkflowScreen.selectNumberOfCNAWorkflow(workflowCount);
        BookmarkScreen bookmarkScreen = selectMultipleWorkflowScreen.tapOnBookmarkSelectedScreenDoneButton();
        inboxScreen = bookmarkScreen.bookmarkWorkflow(WORKFLOW_GMR, workflowCount);

        InboxDetailViewScreen detailViewScreen = inboxScreen.tapOnWorkflow(workflowIDList.get(0));
        Assert.assertEquals(detailViewScreen.getBookmarkValue(WORKFLOW_GMR), "Y",
                FAILED_MSG_FAILED_TO_BOOKMARK_SELECTED_WORKFLOW.replace("$1", workflowIDList.get(0)));
    }

    @Test(groups = {TEST_GRP_BOOKMARK_ALL, TEST_GRP_GT_GMR},
            dependsOnMethods = {"gmrBookmarkSelectedTest", "gmrSwipeBookmarkTest", "gmrBookmarkDetailViewTest"})
    public void gmrBookmarkAllTest() {
        System.out.println("Method: gmrBookmarkAllTest");
        List<String> workflowIDList;

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername01"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_GMR, STATUS_OVERDUE);
        inboxScreen.tapOnForApprovalSubTab();
        workflowIDList = inboxScreen.getAllCNAWorkflowId();
        BookmarkScreen bookmarkScreen = inboxScreen.bookmarkAllWorkflow();
        inboxScreen = bookmarkScreen.bookmarkWorkflow(WORKFLOW_GMR, workflowIDList.size());

        for (String workflowID : workflowIDList) {
            InboxDetailViewScreen detailViewScreen = inboxScreen.tapOnWorkflow(workflowID);
            Assert.assertEquals(detailViewScreen.getBookmarkValue(WORKFLOW_GMR), "Y",
                    FAILED_MSG_FAILED_TO_BOOKMARK_ALL_WORKFLOW.replace("$1", workflowID));
            inboxScreen = detailViewScreen.tapOnBackButton();
        }
    }

    //-------------------------------- IPV/FVA ---------------------------------

    @Test(groups = {TEST_GRP_BOOKMARK, TEST_GRP_IPV_FVA})
    public void fvaBookmarkDetailViewTest() {
        System.out.println("Method: fvaBookmarkDetailViewTest");
        String workflowID;

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername01"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_FVA, STATUS_OVERDUE);
        inboxScreen.tapOnForAcknowledgementSubTab();
        workflowID = inboxScreen.getFirstCNAWorkflowId();
        InboxDetailViewScreen detailViewScreen = inboxScreen.tapOnWorkflow(workflowID);
        BookmarkScreen bookmarkScreen = detailViewScreen.tapOnBookmarkButton();
        inboxScreen = bookmarkScreen.bookmarkWorkflow(WORKFLOW_FVA, 1);
        detailViewScreen = inboxScreen.tapOnWorkflow(workflowID);
        Assert.assertEquals(detailViewScreen.getBookmarkValue(WORKFLOW_FVA), "Y",
                FAILED_MSG_FAILED_TO_BOOKMARK_WORKFLOW.replace("$1", workflowID));
    }

    @Test(groups = {TEST_GRP_BOOKMARK, TEST_GRP_IPV_FVA})
    public void fvaSwipeBookmarkTest() {
        System.out.println("Method: fvaSwipeBookmarkTest");
        String workflowID;

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername01"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_FVA, STATUS_OVERDUE);
        inboxScreen.tapOnForAcknowledgementSubTab();
        workflowID = inboxScreen.getFirstCNAWorkflowId();
        BookmarkScreen bookmarkScreen = inboxScreen.swipeLeftAndTapOnBookmark(workflowID);
        inboxScreen = bookmarkScreen.bookmarkWorkflow(WORKFLOW_FVA, 1);
        InboxDetailViewScreen detailViewScreen = inboxScreen.tapOnWorkflow(workflowID);
        Assert.assertEquals(detailViewScreen.getBookmarkValue(WORKFLOW_FVA), "Y",
                FAILED_MSG_FAILED_TO_BOOKMARK_WORKFLOW.replace("$1", workflowID));
    }

    @Test(groups = {TEST_GRP_BOOKMARK_SELECTED, TEST_GRP_IPV_FVA}/*,
            dependsOnMethods = {"fvaSwipeBookmarkTest", "fvaBookmarkDetailViewTest"}*/)
    public void fvaBookmarkSelectedTest() {
        System.out.println("Method: fvaBookmarkSelectedTest");
        List<String> workflowIDList;
        int workflowCount = 1;

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername01"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_FVA, STATUS_OVERDUE);
        inboxScreen.tapOnForAcknowledgementSubTab();
        SelectMultipleWorkflowScreen selectMultipleWorkflowScreen = inboxScreen
                .navigateToSelectMultipleWorkflowScreen(workflowCount, BUCKET_TO_DO,
                        MORE_OPTION_BOOKMARK_SELECTED);
        workflowIDList = selectMultipleWorkflowScreen.selectNumberOfCNAWorkflow(workflowCount);
        BookmarkScreen bookmarkScreen = selectMultipleWorkflowScreen.tapOnBookmarkSelectedScreenDoneButton();
        inboxScreen = bookmarkScreen.bookmarkWorkflow(WORKFLOW_FVA, workflowCount);

        InboxDetailViewScreen detailViewScreen = inboxScreen.tapOnWorkflow(workflowIDList.get(0));
        Assert.assertEquals(detailViewScreen.getBookmarkValue(WORKFLOW_FVA), "Y",
                FAILED_MSG_FAILED_TO_BOOKMARK_SELECTED_WORKFLOW.replace("$1", workflowIDList.get(0)));
    }

    @Test(groups = {TEST_GRP_BOOKMARK_ALL, TEST_GRP_IPV_FVA},
            dependsOnMethods = {"fvaBookmarkSelectedTest", "fvaSwipeBookmarkTest", "fvaBookmarkDetailViewTest"})
    public void fvaBookmarkAllTest() {
        System.out.println("Method: fvaBookmarkAllTest");
        List<String> workflowIDList;

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername01"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_FVA, STATUS_OVERDUE);
        inboxScreen.tapOnForAcknowledgementSubTab();
        workflowIDList = inboxScreen.getAllCNAWorkflowId();
        BookmarkScreen bookmarkScreen = inboxScreen.bookmarkAllWorkflow();
        inboxScreen = bookmarkScreen.bookmarkWorkflow(WORKFLOW_FVA, workflowIDList.size());

        for (String workflowID : workflowIDList) {
            InboxDetailViewScreen detailViewScreen = inboxScreen.tapOnWorkflow(workflowID);
            Assert.assertEquals(detailViewScreen.getBookmarkValue(WORKFLOW_FVA), "Y",
                    FAILED_MSG_FAILED_TO_BOOKMARK_ALL_WORKFLOW.replace("$1", workflowID));
            inboxScreen = detailViewScreen.tapOnBackButton();
        }
    }

    //-------------------------------- VE ---------------------------------

    @Test(groups = {TEST_GRP_BOOKMARK, TEST_GRP_VE})
    public void veBookmarkDetailViewTest() {
        System.out.println("Method: veBookmarkDetailViewTest");
        String workflowID;

        OverviewScreen overviewScreen = login(prop.getProperty("uat.VEVDOUsername"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_VE, STATUS_OPEN);
        inboxScreen.tapOnForReviewAndActionSubTab();
        workflowID = inboxScreen.getFirstWorkflowId();
        InboxDetailViewScreen detailViewScreen = inboxScreen.tapOnWorkflow(workflowID);
        BookmarkScreen bookmarkScreen = detailViewScreen.tapOnBookmarkButton();
        inboxScreen = bookmarkScreen.bookmarkWorkflow(WORKFLOW_VE, 1);
        detailViewScreen = inboxScreen.tapOnWorkflow(workflowID);
        Assert.assertEquals(detailViewScreen.getBookmarkValue(WORKFLOW_VE), "Y",
                FAILED_MSG_FAILED_TO_BOOKMARK_WORKFLOW.replace("$1", workflowID));
    }

    @Test(groups = {TEST_GRP_BOOKMARK, TEST_GRP_VE})
    public void veSwipeBookmarkTest() {
        System.out.println("Method: veSwipeBookmarkTest");
        String workflowID;

        OverviewScreen overviewScreen = login(prop.getProperty("uat.VEVDOUsername"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_VE, STATUS_OPEN);
        inboxScreen.tapOnForReviewAndActionSubTab();
        workflowID = inboxScreen.getFirstWorkflowId();
        BookmarkScreen bookmarkScreen = inboxScreen.swipeLeftAndTapOnBookmark(workflowID);
        inboxScreen = bookmarkScreen.bookmarkWorkflow(WORKFLOW_VE, 1);
        InboxDetailViewScreen detailViewScreen = inboxScreen.tapOnWorkflow(workflowID);
        Assert.assertEquals(detailViewScreen.getBookmarkValue(WORKFLOW_VE), "Y",
                FAILED_MSG_FAILED_TO_BOOKMARK_WORKFLOW.replace("$1", workflowID));
    }

    @Test(groups = {TEST_GRP_BOOKMARK_SELECTED, TEST_GRP_VE}/*,
            dependsOnMethods = {"veSwipeBookmarkTest", "veBookmarkDetailViewTest"}*/)
    public void veBookmarkSelectedTest() {
        System.out.println("Method: veBookmarkSelectedTest");
        List<String> workflowIDList;
        int workflowCount = 1;

        OverviewScreen overviewScreen = login(prop.getProperty("uat.VEVDOUsername"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_VE, STATUS_OPEN);
        inboxScreen.tapOnForReviewAndActionSubTab();
        SelectMultipleWorkflowScreen selectMultipleWorkflowScreen = inboxScreen
                .navigateToSelectMultipleWorkflowScreen(workflowCount, BUCKET_TO_DO,
                        MORE_OPTION_BOOKMARK_SELECTED);
        workflowIDList = selectMultipleWorkflowScreen.selectNumberOfWorkflow(workflowCount);
        BookmarkScreen bookmarkScreen = selectMultipleWorkflowScreen.tapOnBookmarkSelectedScreenDoneButton();
        inboxScreen = bookmarkScreen.bookmarkWorkflow(WORKFLOW_VE, workflowCount);

        InboxDetailViewScreen detailViewScreen = inboxScreen.tapOnWorkflow(workflowIDList.get(0));
        Assert.assertEquals(detailViewScreen.getBookmarkValue(WORKFLOW_VE), "Y",
                FAILED_MSG_FAILED_TO_BOOKMARK_SELECTED_WORKFLOW.replace("$1", workflowIDList.get(0)));
    }

    //-------------------------------- CE ---------------------------------

    @Test(groups = {TEST_GRP_BOOKMARK, TEST_GRP_CE})
    public void ceBookmarkDetailViewDealerTest() {
        System.out.println("Method: ceBookmarkDetailViewDealerTest");
        String workflowID;

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DealerSupervisor"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CE, STATUS_OPEN);
        inboxScreen.tapOnForReviewSubTab();
        workflowID = inboxScreen.getFirstCNAWorkflowId();
        InboxDetailViewScreen detailViewScreen = inboxScreen.tapOnWorkflow(workflowID);
        BookmarkScreen bookmarkScreen = detailViewScreen.tapOnBookmarkButton();
        inboxScreen = bookmarkScreen.bookmarkWorkflow(WORKFLOW_CE, 1);
        detailViewScreen = inboxScreen.tapOnWorkflow(workflowID);
        Assert.assertEquals(detailViewScreen.getBookmarkValue(WORKFLOW_CE), "Y",
                FAILED_MSG_FAILED_TO_BOOKMARK_WORKFLOW.replace("$1", workflowID));
    }

    @Test(groups = {TEST_GRP_BOOKMARK, TEST_GRP_CE})
    public void ceSwipeBookmarkDealerTest() {
        System.out.println("Method: ceSwipeBookmarkDealerTest");
        String workflowID;

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DealerSupervisor"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CE, STATUS_OPEN);
        inboxScreen.tapOnForReviewSubTab();
        workflowID = inboxScreen.getFirstCNAWorkflowId();
        BookmarkScreen bookmarkScreen = inboxScreen.swipeLeftAndTapOnBookmark(workflowID);
        inboxScreen = bookmarkScreen.bookmarkWorkflow(WORKFLOW_CE, 1);
        InboxDetailViewScreen detailViewScreen = inboxScreen.tapOnWorkflow(workflowID);
        Assert.assertEquals(detailViewScreen.getBookmarkValue(WORKFLOW_CE), "Y",
                FAILED_MSG_FAILED_TO_BOOKMARK_WORKFLOW.replace("$1", workflowID));
    }

    @Test(groups = {TEST_GRP_BOOKMARK_SELECTED, TEST_GRP_CE},
            dependsOnMethods = {"ceSwipeBookmarkDealerTest", "ceBookmarkDetailViewDealerTest"})
    public void ceBookmarkSelectedDealerTest() {
        System.out.println("Method: ceBookmarkSelectedDealerTest");
        List<String> workflowIDList;
        int workflowCount = 1;

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DealerSupervisor"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CE, STATUS_OPEN);
        inboxScreen.tapOnForReviewSubTab();
        SelectMultipleWorkflowScreen selectMultipleWorkflowScreen = inboxScreen
                .navigateToSelectMultipleWorkflowScreen(workflowCount, BUCKET_TO_DO,
                        MORE_OPTION_BOOKMARK_SELECTED);
        workflowIDList = selectMultipleWorkflowScreen.selectNumberOfCNAWorkflow(workflowCount);
        BookmarkScreen bookmarkScreen = selectMultipleWorkflowScreen.tapOnBookmarkSelectedScreenDoneButton();
        inboxScreen = bookmarkScreen.bookmarkWorkflow(WORKFLOW_CE, workflowCount);

        InboxDetailViewScreen detailViewScreen = inboxScreen.tapOnWorkflow(workflowIDList.get(0));
        Assert.assertEquals(detailViewScreen.getBookmarkValue(WORKFLOW_CE), "Y",
                FAILED_MSG_FAILED_TO_BOOKMARK_SELECTED_WORKFLOW.replace("$1", workflowIDList.get(0)));
    }

    @Test(groups = {TEST_GRP_BOOKMARK_ALL, TEST_GRP_CE}/*,
            dependsOnMethods = {"ceBookmarkSelectedDealerTest"}*/)
    public void ceBookmarkAllDealerTest() {
        System.out.println("Method: ceBookmarkAllDealerTest");
        List<String> workflowIDList;

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DealerSupervisor"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CE, STATUS_OPEN);
        inboxScreen.tapOnForReviewSubTab();
        workflowIDList = inboxScreen.getAllCNAWorkflowId();
        BookmarkScreen bookmarkScreen = inboxScreen.bookmarkAllWorkflow();
        inboxScreen = bookmarkScreen.bookmarkWorkflow(WORKFLOW_CE, workflowIDList.size());

        for (String workflowID : workflowIDList) {
            InboxDetailViewScreen detailViewScreen = inboxScreen.tapOnWorkflow(workflowID);
            Assert.assertEquals(detailViewScreen.getBookmarkValue(WORKFLOW_CE), "Y",
                    FAILED_MSG_FAILED_TO_BOOKMARK_ALL_WORKFLOW.replace("$1", workflowID));
            inboxScreen = detailViewScreen.tapOnBackButton();
        }
    }
}
