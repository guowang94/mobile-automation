package com.scb.fmsw.mobile.test;

import com.scb.fmsw.mobile.base.BaseTest;
import com.scb.fmsw.mobile.screen.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class BookmarkTest extends BaseTest {

    //create test method for CNA, OMR, PNL, GT/GMR, IPV/FVA

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
        Assert.assertEquals(detailViewScreen.getBookmarkValue(), "Y",
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
        Assert.assertEquals(detailViewScreen.getBookmarkValue(), "Y",
                FAILED_MSG_FAILED_TO_BOOKMARK_WORKFLOW.replace("$1", workflowID));
    }

    @Test(groups = {TEST_GRP_BOOKMARK_SELECTED, TEST_GRP_CNA},
            dependsOnMethods = {"cnaSwipeBookmarkTest", "cnaBookmarkDetailViewTest"})
    public void cnaBookmarkSelectedTest() {
        System.out.println("Method: cnaBookmarkSelectedTest");
        List<String> workflowIDList;
        int workflowCount = 2;

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
        Assert.assertEquals(detailViewScreen.getBookmarkValue(), "Y",
                FAILED_MSG_FAILED_TO_BOOKMARK_SELECTED_WORKFLOW.replace("$1", workflowIDList.get(0)));

        inboxScreen = detailViewScreen.tapOnBackButton();
        detailViewScreen = inboxScreen.tapOnWorkflow(workflowIDList.get(1));
        Assert.assertEquals(detailViewScreen.getBookmarkValue(), "Y",
                FAILED_MSG_FAILED_TO_BOOKMARK_SELECTED_WORKFLOW.replace("$1", workflowIDList.get(1)));
    }

    @Test(groups = {TEST_GRP_BOOKMARK_ALL, TEST_GRP_CNA},
            dependsOnMethods = {"cnaBookmarkSelectedTest"})
    public void cnaBookmarkAllTest() {
        System.out.println("Method: cnaBookmarkAllTest");
        List<String> workflowIDList;

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername01"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CNA, STATUS_OVERDUE);
        inboxScreen.tapOnForAcknowledgementSubTab();
        workflowIDList = inboxScreen.getAllCNAWorkflowId();
        BookmarkScreen bookmarkScreen = inboxScreen.bookmarkAllWorkflow();
        inboxScreen = bookmarkScreen.bookmarkWorkflow(WORKFLOW_CNA, workflowIDList.size());

        for (String workflowID : workflowIDList) {
            InboxDetailViewScreen detailViewScreen = inboxScreen.tapOnWorkflow(workflowID);
            Assert.assertEquals(detailViewScreen.getBookmarkValue(), "Y",
                    FAILED_MSG_FAILED_TO_BOOKMARK_ALL_WORKFLOW.replace("$1", workflowID));
            inboxScreen = detailViewScreen.tapOnBackButton();
        }
    }
}
