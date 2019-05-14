package com.scb.fmsw.mobile.test;

import com.scb.fmsw.mobile.base.BaseTest;
import com.scb.fmsw.mobile.screen.AcknowledgeScreen;
import com.scb.fmsw.mobile.screen.InboxDetailViewScreen;
import com.scb.fmsw.mobile.screen.InboxScreen;
import com.scb.fmsw.mobile.screen.OverviewScreen;
import com.scb.fmsw.mobile.screen.delegation.DelegationAutoOutOfOfficeScreen;
import com.scb.fmsw.mobile.screen.delegation.DelegationOptionScreen;
import com.scb.fmsw.mobile.screen.delegation.DelegationScreen;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Testing extends BaseTest {

    @Test(groups = "TEST01")
    public void Test() {
        String workflowID = "VE0000239166";
        OverviewScreen overviewScreen = login(prop.getProperty("uat.VEVDOUsername"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_VE, STATUS_OPEN);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_REVIEW_AND_ACTION_WORKFLOW.replace("$1", workflowID));
        Assert.assertTrue(inboxScreen.verifyDetailsPostActionPerformed(WORKFLOW_STATUS_PENDING_COMPLIANCE_ACTION_UT, WORKFLOW_VE, workflowID),
                FAILED_MSG_FAILED_TO_MATCH_COMMENTS_OR_WORKFLOW_STATUS.replace("$1", workflowID));
    }

    @Test(groups = "TEST01")
    public void Test01() throws InterruptedException {
        List<String> workflowTypeList = new ArrayList<>();
        List<String> countryList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_CNA);
        countryList.add("ALL");
        String comment = MSG_ENTER_COMMENT + " " + String.valueOf(new Date());


        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername02"));

        Thread.sleep(5000);
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationAutoOutOfOfficeScreen delegationAutoOutOfOfficeScreen = delegationOptionScreen.tapOnAutoOutOfOfficeDelegation();

//        delegationAutoOutOfOfficeScreen.selectType("Portfolio");
//        delegationAutoOutOfOfficeScreen.selectBusinessGroup("FXRC");
//        delegationAutoOutOfOfficeScreen.selectBusiness("FX");
//        delegationAutoOutOfOfficeScreen.selectSubBusiness("FX Cash");
//		delegationAutoOutOfOfficeScreen.selectDesk("FX ASA");

        delegationAutoOutOfOfficeScreen.selectType("Desk/Country");
//        delegationAutoOutOfOfficeScreen.selectType("Portfolio");
        delegationAutoOutOfOfficeScreen.selectBusinessGroup("ALL");
//        delegationAutoOutOfOfficeScreen.selectBusinessGroup("FXRC");
        delegationAutoOutOfOfficeScreen.selectBusiness("ALL");
//        delegationAutoOutOfOfficeScreen.selectBusiness("FX");
        delegationAutoOutOfOfficeScreen.selectSubBusiness("ALL");
//        delegationAutoOutOfOfficeScreen.selectSubBusiness("FX Cash");
        delegationAutoOutOfOfficeScreen.selectDesk("ALL");
//        delegationAutoOutOfOfficeScreen.selectDesk("FX ASA");
        Thread.sleep(20000);
    }


    @Test(groups = "TEST01")
    public void Test02() throws InterruptedException {
        OverviewScreen overviewScreen = login(prop.getProperty("sit.MTCRUsername"));
        InboxScreen inboxScreen = overviewScreen.tapOnWorkflowCount(WORKFLOW_CE, STATUS_OPEN);
        String workflowID = inboxScreen.getFirstCNAWorkflowId();
        InboxDetailViewScreen inboxDetailViewScreen = inboxScreen.tapOnWorkflow(workflowID, true);
        AcknowledgeScreen acknowledgeScreen = inboxDetailViewScreen.tapOnAcknowledgeButton();
        inboxScreen = acknowledgeScreen.reviewAndAssessWorkflow(null, "High",
                "Yes", WORKFLOW_CE, 1);
        inboxScreen.navigateToBucket(BUCKET_IN_PROGRESS);
        Assert.assertTrue(inboxScreen.verifyWorkflowInBucket(workflowID, 1, BUCKET_IN_PROGRESS),
                FAILED_MSG_FAILED_TO_REVIEW_AND_ASSESS_WORKFLOW.replace("$1", workflowID));


//        reviewAndAssessScreen.selectSeverityValue("High");
//        reviewAndAssessScreen.selectPotentialLossValue("No");
//        reviewAndAssessScreen.enterIssueFlag("test");
//        reviewAndAssessScreen.enterRiskAssessmentComment("test");
//        reviewAndAssessScreen.enterExplainationComment("test");
//        reviewAndAssessScreen.enterControlBreakdownComment("test");
//        reviewAndAssessScreen.enterGroupRemark("test");

//        reviewAndAssessScreen.selectLateCode(CE_LATE_CODE_INVESTIGATION_WITH_FO);
//        reviewAndAssessScreen.enterLateComment();




        Thread.sleep(5000);
        Thread.sleep(50000);




    }

    @Test(groups = "TEST")
    public void test03() {
        OverviewScreen overviewScreen = login(prop.getProperty("sit.MTCRUsername"));
    }

    @Test(groups = "TEST")
    public void test04() {
        OverviewScreen overviewScreen = login(prop.getProperty("sit.MTCRUsername"));
    }
}
