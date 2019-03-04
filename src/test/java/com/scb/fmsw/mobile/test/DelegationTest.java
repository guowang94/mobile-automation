package com.scb.fmsw.mobile.test;

import com.scb.fmsw.mobile.base.BaseTest;
import com.scb.fmsw.mobile.screen.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DelegationTest extends BaseTest {

    //Note: For manual workflow, in order to create report label level delegation, the user has to be the acknowledger
    //create test method for CNA, TRR, PNL, GT/GMR, IPV/FVA, VE, CE

    //-------------------------------- CNA ---------------------------------

    @Test(groups = {TEST_GRP_AUTO_OUT_OF_OFFICE_DELEGATION, TEST_GRP_CNA, TEST_GRP_ACCEPT_DELEGATION})
    public void cnaAcceptAndDeleteAndDeleteAutoOutOfOfficeDeskOrCountryDelegation() {
        System.out.println("Method: cnaAcceptAndDeleteAndDeleteAutoOutOfOfficeDeskOrCountryDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        List<String> countryList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_CNA);
        countryList.add(DELEGATION_OPTION_ALL);
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());
        int index;

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationAutoOutOfOfficeScreen delegationAutoOutOfOfficeScreen = delegationOptionScreen.tapOnAutoOutOfOfficeDelegation();
        DelegationPortfolioScreen delegationPortfolioScreen = delegationAutoOutOfOfficeScreen.fillInDefaultForm(workflowTypeList,
                DELEGATION_TYPE_DESK_OR_COUNTRY, DELEGATION_OPTION_ALL, DELEGATION_OPTION_FX, DELEGATION_OPTION_FX_CASH,
                DELEGATION_OPTION_FX_ASA, countryList, DELEGATION_OPTION_Y);
        DelegationDefaultCreationScreen delegationDefaultCreationScreen = delegationPortfolioScreen
                .selectPortfolio(true, 0);
        delegationScreen = delegationDefaultCreationScreen.createDefaultDelegation("1",
                prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"),
                DELEGATION_TYPE_AUTO_OUT_OF_OFFICE, workflowTypeList.get(0), comment), FAILED_MSG_FAILED_TO_CREATE_DEFAULT_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to accept the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegatorUsername"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.acceptDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_ACCEPTED),
                SCREENSHOT_MSG_FAILED_TO_ACCEPT_DELEGATION);
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_ACCEPTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_AUTO_OUT_OF_OFFICE_DELEGATION, TEST_GRP_CNA, TEST_GRP_ACCEPT_DELEGATION})
    public void cnaAcceptAndDeleteAndDeleteAutoOutOfOfficePortfolioDelegation() {
        System.out.println("Method: cnaAcceptAndDeleteAndDeleteAutoOutOfOfficePortfolioDelegation");
        int count = 3;
        List<String> workflowTypeList = new ArrayList<>();
        List<String> countryList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_CNA);
        countryList.add(DELEGATION_OPTION_ALL);
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationAutoOutOfOfficeScreen delegationAutoOutOfOfficeScreen = delegationOptionScreen.tapOnAutoOutOfOfficeDelegation();
        DelegationPortfolioScreen delegationPortfolioScreen = delegationAutoOutOfOfficeScreen.fillInDefaultForm(workflowTypeList,
                DELEGATION_TYPE_PORTFOLIO, DELEGATION_OPTION_ALL, DELEGATION_OPTION_FX, DELEGATION_OPTION_FX_CASH,
                DELEGATION_OPTION_FX_ASA, countryList, DELEGATION_OPTION_Y);
        DelegationDefaultCreationScreen delegationDefaultCreationScreen = delegationPortfolioScreen
                .selectPortfolio(false, count);
        delegationScreen = delegationDefaultCreationScreen
                .createDefaultDelegation("1", prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment), FAILED_MSG_FAILED_TO_CREATE_DEFAULT_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to accept the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegatorUsername"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.acceptDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_ACCEPTED),
                SCREENSHOT_MSG_FAILED_TO_ACCEPT_DELEGATION);
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_ACCEPTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_PERIOD_DELEGATION, TEST_GRP_CNA, TEST_GRP_ACCEPT_DELEGATION})
    public void cnaAcceptAndDeleteAndDeleteUserDelegation() {
        System.out.println("Method: cnaAcceptAndDeleteAndDeleteUserDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_CNA);
        //The duration of the delegation cannot be for more than 1 year
        DateFormat dateFormat = new SimpleDateFormat("dd, MMMM, yyyy");
        String fromDate = dateFormat.format(new Date());
        String toDate = dateFormat.format(new Date());
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationPeriodScreen delegationPeriodScreen = delegationOptionScreen.tapOnPeriodDelegation();
        DelegationUsersCreationScreen delegationUsersCreationScreen = delegationPeriodScreen.fillInUsersForm(workflowTypeList);
        delegationScreen = delegationUsersCreationScreen
                .createUsersDelegation(fromDate, toDate, prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"),
                DELEGATION_TYPE_USER, workflowTypeList.get(0), comment), FAILED_MSG_FAILED_TO_CREATE_USERS_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to accept the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegatorUsername"), DELEGATION_TYPE_USER,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.acceptDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_ACCEPTED),
                SCREENSHOT_MSG_FAILED_TO_ACCEPT_DELEGATION);
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_USER,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_ACCEPTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_AUTO_OUT_OF_OFFICE_DELEGATION, TEST_GRP_CNA, TEST_GRP_REJECT_DELEGATION})
    public void cnaRejectAndDeleteAutoOutOfOfficeDeskOrCountryDelegation() {
        System.out.println("Method: cnaRejectAndDeleteAutoOutOfOfficeDeskOrCountryDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        List<String> countryList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_CNA);
        countryList.add(DELEGATION_OPTION_ALL);
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationAutoOutOfOfficeScreen delegationAutoOutOfOfficeScreen = delegationOptionScreen.tapOnAutoOutOfOfficeDelegation();
        DelegationPortfolioScreen delegationPortfolioScreen = delegationAutoOutOfOfficeScreen.fillInDefaultForm(workflowTypeList,
                DELEGATION_TYPE_DESK_OR_COUNTRY, DELEGATION_OPTION_ALL, DELEGATION_OPTION_FX, DELEGATION_OPTION_FX_CASH,
                DELEGATION_OPTION_FX_ASA, countryList, DELEGATION_OPTION_Y);
        DelegationDefaultCreationScreen delegationDefaultCreationScreen = delegationPortfolioScreen
                .selectPortfolio(true, 0);
        delegationScreen = delegationDefaultCreationScreen.createDefaultDelegation("1",
                prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"),
                DELEGATION_TYPE_AUTO_OUT_OF_OFFICE, workflowTypeList.get(0), comment), FAILED_MSG_FAILED_TO_CREATE_DEFAULT_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to RejectAndDelete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegatorUsername"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.rejectDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_REJECTED),
                SCREENSHOT_MSG_FAILED_TO_REJECT_DELEGATION);
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_REJECTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_AUTO_OUT_OF_OFFICE_DELEGATION, TEST_GRP_CNA, TEST_GRP_REJECT_DELEGATION})
    public void cnaRejectAndDeleteAutoOutOfOfficePortfolioDelegation() {
        System.out.println("Method: cnaRejectAndDeleteAutoOutOfOfficePortfolioDelegation");
        int count = 3;
        List<String> workflowTypeList = new ArrayList<>();
        List<String> countryList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_CNA);
        countryList.add(DELEGATION_OPTION_ALL);
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationAutoOutOfOfficeScreen delegationAutoOutOfOfficeScreen = delegationOptionScreen.tapOnAutoOutOfOfficeDelegation();
        DelegationPortfolioScreen delegationPortfolioScreen = delegationAutoOutOfOfficeScreen.fillInDefaultForm(workflowTypeList,
                DELEGATION_TYPE_PORTFOLIO, DELEGATION_OPTION_ALL, DELEGATION_OPTION_FX, DELEGATION_OPTION_FX_CASH,
                DELEGATION_OPTION_FX_ASA, countryList, DELEGATION_OPTION_Y);
        DelegationDefaultCreationScreen delegationDefaultCreationScreen = delegationPortfolioScreen
                .selectPortfolio(false, count);
        delegationScreen = delegationDefaultCreationScreen.createDefaultDelegation("1",
                prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment), FAILED_MSG_FAILED_TO_CREATE_DEFAULT_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to RejectAndDelete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegatorUsername"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.rejectDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_REJECTED),
                SCREENSHOT_MSG_FAILED_TO_REJECT_DELEGATION);
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_REJECTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_PERIOD_DELEGATION, TEST_GRP_CNA, TEST_GRP_REJECT_DELEGATION})
    public void cnaRejectAndDeleteUserDelegation() {
        System.out.println("Method: cnaRejectAndDeleteUserDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_CNA);
        //The duration of the delegation cannot be for more than 1 year
        DateFormat dateFormat = new SimpleDateFormat("dd, MMMM, yyyy");
        String fromDate = dateFormat.format(new Date());
        String toDate = dateFormat.format(new Date());
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationPeriodScreen delegationPeriodScreen = delegationOptionScreen.tapOnPeriodDelegation();
        DelegationUsersCreationScreen delegationUsersCreationScreen = delegationPeriodScreen
                .fillInUsersForm(workflowTypeList);
        delegationScreen = delegationUsersCreationScreen.createUsersDelegation(fromDate, toDate, prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"),
                DELEGATION_TYPE_USER, workflowTypeList.get(0), comment), FAILED_MSG_FAILED_TO_CREATE_USERS_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to RejectAndDelete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegatorUsername"), DELEGATION_TYPE_USER,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.rejectDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_REJECTED),
                SCREENSHOT_MSG_FAILED_TO_REJECT_DELEGATION);
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_USER,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_REJECTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    //-------------------------------- TRR ---------------------------------

    @Test(groups = {TEST_GRP_AUTO_OUT_OF_OFFICE_DELEGATION, TEST_GRP_TRR, TEST_GRP_ACCEPT_DELEGATION})
    public void trrAcceptAndDeleteAndDeleteAutoOutOfOfficeTRRDeskOrCountryDelegation() {
        System.out.println("Method: trrAcceptAndDeleteAndDeleteAutoOutOfOfficeTRRDeskOrCountryDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        List<String> countryList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_TRR);
        countryList.add(DELEGATION_OPTION_ALL);
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationAutoOutOfOfficeScreen delegationAutoOutOfOfficeScreen = delegationOptionScreen.tapOnAutoOutOfOfficeDelegation();
        DelegationPortfolioScreen delegationPortfolioScreen = delegationAutoOutOfOfficeScreen.fillInDefaultForm(workflowTypeList,
                DELEGATION_TYPE_DESK_OR_COUNTRY, DELEGATION_OPTION_ALL, DELEGATION_OPTION_FX, DELEGATION_OPTION_FX_CASH,
                DELEGATION_OPTION_FX_ASA, countryList, DELEGATION_OPTION_Y);
        DelegationDefaultCreationScreen delegationDefaultCreationScreen = delegationPortfolioScreen
                .selectPortfolio(true, 0);
        delegationScreen = delegationDefaultCreationScreen.createDefaultDelegation("1",
                prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment), FAILED_MSG_FAILED_TO_CREATE_DEFAULT_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to accept the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegatorUsername"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.acceptDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_ACCEPTED),
                SCREENSHOT_MSG_FAILED_TO_ACCEPT_DELEGATION);
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_ACCEPTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_AUTO_OUT_OF_OFFICE_DELEGATION, TEST_GRP_TRR, TEST_GRP_ACCEPT_DELEGATION})
    public void trrAcceptAndDeleteAndDeleteAutoOutOfOfficePortfolioDelegation() {
        System.out.println("Method: trrAcceptAndDeleteAndDeleteAutoOutOfOfficePortfolioDelegation");
        int count = 3;
        List<String> workflowTypeList = new ArrayList<>();
        List<String> countryList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_TRR);
        countryList.add(DELEGATION_OPTION_ALL);
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationAutoOutOfOfficeScreen delegationAutoOutOfOfficeScreen = delegationOptionScreen.tapOnAutoOutOfOfficeDelegation();
        DelegationPortfolioScreen delegationPortfolioScreen = delegationAutoOutOfOfficeScreen.fillInDefaultForm(workflowTypeList,
                DELEGATION_TYPE_PORTFOLIO, DELEGATION_OPTION_ALL, DELEGATION_OPTION_FX, DELEGATION_OPTION_FX_CASH,
                DELEGATION_OPTION_FX_ASA, countryList, DELEGATION_OPTION_Y);
        DelegationDefaultCreationScreen delegationDefaultCreationScreen = delegationPortfolioScreen
                .selectPortfolio(false, count);
        delegationScreen = delegationDefaultCreationScreen.createDefaultDelegation("1",
                prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment), FAILED_MSG_FAILED_TO_CREATE_DEFAULT_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to accept the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegatorUsername"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.acceptDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_ACCEPTED),
                SCREENSHOT_MSG_FAILED_TO_ACCEPT_DELEGATION);
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_ACCEPTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_PERIOD_DELEGATION, TEST_GRP_TRR, TEST_GRP_ACCEPT_DELEGATION})
    public void trrAcceptAndDeleteAndDeleteUserDelegation() {
        System.out.println("Method: trrAcceptAndDeleteAndDeleteUserDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_TRR);
        //The duration of the delegation cannot be for more than 1 year
        DateFormat dateFormat = new SimpleDateFormat("dd, MMMM, yyyy");
        String fromDate = dateFormat.format(new Date());
        String toDate = dateFormat.format(new Date());
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationPeriodScreen delegationPeriodScreen = delegationOptionScreen.tapOnPeriodDelegation();
        DelegationUsersCreationScreen delegationUsersCreationScreen = delegationPeriodScreen
                .fillInUsersForm(workflowTypeList);
        delegationScreen = delegationUsersCreationScreen.createUsersDelegation(fromDate, toDate, prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"),
                DELEGATION_TYPE_USER, workflowTypeList.get(0), comment), FAILED_MSG_FAILED_TO_CREATE_USERS_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to accept the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegatorUsername"), DELEGATION_TYPE_USER,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.acceptDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_ACCEPTED),
                SCREENSHOT_MSG_FAILED_TO_ACCEPT_DELEGATION);
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_USER,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_ACCEPTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_AUTO_OUT_OF_OFFICE_DELEGATION, TEST_GRP_TRR, TEST_GRP_REJECT_DELEGATION})
    public void trrRejectAndDeleteAutoOutOfOfficeTRRDeskOrCountryDelegation() {
        System.out.println("Method: trrRejectAndDeleteAutoOutOfOfficeTRRDeskOrCountryDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        List<String> countryList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_TRR);
        countryList.add(DELEGATION_OPTION_ALL);
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationAutoOutOfOfficeScreen delegationAutoOutOfOfficeScreen = delegationOptionScreen.tapOnAutoOutOfOfficeDelegation();
        DelegationPortfolioScreen delegationPortfolioScreen = delegationAutoOutOfOfficeScreen.fillInDefaultForm(workflowTypeList,
                DELEGATION_TYPE_DESK_OR_COUNTRY, DELEGATION_OPTION_ALL, DELEGATION_OPTION_FX, DELEGATION_OPTION_FX_CASH,
                DELEGATION_OPTION_FX_ASA, countryList, DELEGATION_OPTION_Y);
        DelegationDefaultCreationScreen delegationDefaultCreationScreen = delegationPortfolioScreen
                .selectPortfolio(true, 0);
        delegationScreen = delegationDefaultCreationScreen.createDefaultDelegation("1",
                prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment), FAILED_MSG_FAILED_TO_CREATE_DEFAULT_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to reject the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegatorUsername"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.rejectDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_REJECTED),
                SCREENSHOT_MSG_FAILED_TO_REJECT_DELEGATION);
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_REJECTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_AUTO_OUT_OF_OFFICE_DELEGATION, TEST_GRP_TRR, TEST_GRP_REJECT_DELEGATION})
    public void trrRejectAndDeleteAutoOutOfOfficePortfolioDelegation() {
        System.out.println("Method: trrRejectAndDeleteAutoOutOfOfficePortfolioDelegation");
        int count = 3;
        List<String> workflowTypeList = new ArrayList<>();
        List<String> countryList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_TRR);
        countryList.add(DELEGATION_OPTION_ALL);
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationAutoOutOfOfficeScreen delegationAutoOutOfOfficeScreen = delegationOptionScreen.tapOnAutoOutOfOfficeDelegation();
        DelegationPortfolioScreen delegationPortfolioScreen = delegationAutoOutOfOfficeScreen.fillInDefaultForm(workflowTypeList,
                DELEGATION_TYPE_PORTFOLIO, DELEGATION_OPTION_ALL, DELEGATION_OPTION_FX, DELEGATION_OPTION_FX_CASH,
                DELEGATION_OPTION_FX_ASA, countryList, DELEGATION_OPTION_Y);
        DelegationDefaultCreationScreen delegationDefaultCreationScreen = delegationPortfolioScreen
                .selectPortfolio(false, count);
        delegationScreen = delegationDefaultCreationScreen.createDefaultDelegation("1",
                prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment), FAILED_MSG_FAILED_TO_CREATE_DEFAULT_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to reject the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegatorUsername"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.rejectDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_REJECTED),
                SCREENSHOT_MSG_FAILED_TO_REJECT_DELEGATION);
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_REJECTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_PERIOD_DELEGATION, TEST_GRP_TRR, TEST_GRP_REJECT_DELEGATION})
    public void trrRejectAndDeleteUserDelegation() {
        System.out.println("Method: trrRejectAndDeleteUserDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_TRR);
        //The duration of the delegation cannot be for more than 1 year
        DateFormat dateFormat = new SimpleDateFormat("dd, MMMM, yyyy");
        String fromDate = dateFormat.format(new Date());
        String toDate = dateFormat.format(new Date());
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationPeriodScreen delegationPeriodScreen = delegationOptionScreen.tapOnPeriodDelegation();
        DelegationUsersCreationScreen delegationUsersCreationScreen = delegationPeriodScreen
                .fillInUsersForm(workflowTypeList);
        delegationScreen = delegationUsersCreationScreen.createUsersDelegation(fromDate, toDate, prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"),
                DELEGATION_TYPE_USER, workflowTypeList.get(0), comment), FAILED_MSG_FAILED_TO_CREATE_USERS_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to reject the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegatorUsername"), DELEGATION_TYPE_USER,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.rejectDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_REJECTED),
                SCREENSHOT_MSG_FAILED_TO_REJECT_DELEGATION);
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_USER,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_REJECTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    //-------------------------------- PNL ---------------------------------

    @Test(groups = {TEST_GRP_AUTO_OUT_OF_OFFICE_DELEGATION, TEST_GRP_PNL, TEST_GRP_ACCEPT_DELEGATION})
    public void pnlAcceptAndDeleteAndDeleteAutoOutOfOfficeDeskOrCountryDelegation() {
        System.out.println("Method: pnlAcceptAndDeleteAutoOutOfOfficeDeskOrCountryDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        List<String> countryList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_PNL);
        countryList.add(DELEGATION_OPTION_ALL);
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationAutoOutOfOfficeScreen delegationAutoOutOfOfficeScreen = delegationOptionScreen.tapOnAutoOutOfOfficeDelegation();
        DelegationPortfolioScreen delegationPortfolioScreen = delegationAutoOutOfOfficeScreen.fillInDefaultForm(workflowTypeList,
                DELEGATION_TYPE_DESK_OR_COUNTRY, DELEGATION_OPTION_ALL, DELEGATION_OPTION_FX, DELEGATION_OPTION_FX_CASH,
                DELEGATION_OPTION_FX_ASA, countryList, DELEGATION_OPTION_Y);
        DelegationDefaultCreationScreen delegationDefaultCreationScreen = delegationPortfolioScreen
                .selectPortfolio(true, 0);
        delegationScreen = delegationDefaultCreationScreen.createDefaultDelegation("1",
                prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"),
                DELEGATION_TYPE_AUTO_OUT_OF_OFFICE, workflowTypeList.get(0), comment), FAILED_MSG_FAILED_TO_CREATE_DEFAULT_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to accept the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegatorUsername"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.acceptDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_ACCEPTED),
                SCREENSHOT_MSG_FAILED_TO_ACCEPT_DELEGATION);
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_ACCEPTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_AUTO_OUT_OF_OFFICE_DELEGATION, TEST_GRP_PNL, TEST_GRP_ACCEPT_DELEGATION})
    public void pnlAcceptAndDeleteAutoOutOfOfficeReportLabelDelegation() {
        System.out.println("Method: pnlAcceptAndDeleteAutoOutOfOfficeReportLabelDelegation");
        int count = 3;
        List<String> workflowTypeList = new ArrayList<>();
        List<String> countryList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_PNL);
        countryList.add(DELEGATION_OPTION_ALL);
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationAutoOutOfOfficeScreen delegationAutoOutOfOfficeScreen = delegationOptionScreen.tapOnAutoOutOfOfficeDelegation();
        DelegationPortfolioScreen delegationPortfolioScreen = delegationAutoOutOfOfficeScreen.fillInDefaultForm(workflowTypeList,
                DELEGATION_TYPE_REPORT_LABEL, DELEGATION_OPTION_ALL, DELEGATION_OPTION_FX, DELEGATION_OPTION_FX_CASH,
                DELEGATION_OPTION_FX_ASA, countryList, DELEGATION_OPTION_Y);
        DelegationDefaultCreationScreen delegationDefaultCreationScreen = delegationPortfolioScreen
                .selectPortfolio(false, count);
        delegationScreen = delegationDefaultCreationScreen.createDefaultDelegation("1",
                prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment), FAILED_MSG_FAILED_TO_CREATE_DEFAULT_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to accept the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegatorUsername"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.acceptDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_ACCEPTED),
                SCREENSHOT_MSG_FAILED_TO_ACCEPT_DELEGATION);
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_ACCEPTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_PERIOD_DELEGATION, TEST_GRP_PNL, TEST_GRP_ACCEPT_DELEGATION})
    public void pnlAcceptAndDeleteUserDelegation() {
        System.out.println("Method: pnlAcceptAndDeleteUserDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_PNL);
        //The duration of the delegation cannot be for more than 1 year
        DateFormat dateFormat = new SimpleDateFormat("dd, MMMM, yyyy");
        String fromDate = dateFormat.format(new Date());
        String toDate = dateFormat.format(new Date());
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationPeriodScreen delegationPeriodScreen = delegationOptionScreen.tapOnPeriodDelegation();
        DelegationUsersCreationScreen delegationUsersCreationScreen = delegationPeriodScreen
                .fillInUsersForm(workflowTypeList);
        delegationScreen = delegationUsersCreationScreen.createUsersDelegation(fromDate, toDate, prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"),
                DELEGATION_TYPE_USER, workflowTypeList.get(0), comment), FAILED_MSG_FAILED_TO_CREATE_USERS_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to accept the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegatorUsername"), DELEGATION_TYPE_USER,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.acceptDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_ACCEPTED),
                SCREENSHOT_MSG_FAILED_TO_ACCEPT_DELEGATION);
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_USER,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_ACCEPTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_AUTO_OUT_OF_OFFICE_DELEGATION, TEST_GRP_PNL, TEST_GRP_REJECT_DELEGATION})
    public void pnlRejectAndDeleteAutoOutOfOfficeDeskOrCountryDelegation() {
        System.out.println("Method: pnlRejectAndDeleteAutoOutOfOfficeDeskOrCountryDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        List<String> countryList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_PNL);
        countryList.add(DELEGATION_OPTION_ALL);
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationAutoOutOfOfficeScreen delegationAutoOutOfOfficeScreen = delegationOptionScreen.tapOnAutoOutOfOfficeDelegation();
        DelegationPortfolioScreen delegationPortfolioScreen = delegationAutoOutOfOfficeScreen.fillInDefaultForm(workflowTypeList,
                DELEGATION_TYPE_DESK_OR_COUNTRY, DELEGATION_OPTION_ALL, DELEGATION_OPTION_FX, DELEGATION_OPTION_FX_CASH,
                DELEGATION_OPTION_FX_ASA, countryList, DELEGATION_OPTION_Y);
        DelegationDefaultCreationScreen delegationDefaultCreationScreen = delegationPortfolioScreen
                .selectPortfolio(true, 0);
        delegationScreen = delegationDefaultCreationScreen.createDefaultDelegation("1",
                prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"),
                DELEGATION_TYPE_AUTO_OUT_OF_OFFICE, workflowTypeList.get(0), comment), FAILED_MSG_FAILED_TO_CREATE_DEFAULT_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to reject the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegatorUsername"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.rejectDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_REJECTED),
                SCREENSHOT_MSG_FAILED_TO_REJECT_DELEGATION);
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_REJECTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_AUTO_OUT_OF_OFFICE_DELEGATION, TEST_GRP_PNL, TEST_GRP_REJECT_DELEGATION})
    public void pnlRejectAndDeleteAutoOutOfOfficeReportLabelDelegation() {
        System.out.println("Method: pnlRejectAndDeleteAutoOutOfOfficeReportLabelDelegation");
        int count = 3;
        List<String> workflowTypeList = new ArrayList<>();
        List<String> countryList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_PNL);
        countryList.add(DELEGATION_OPTION_ALL);
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationAutoOutOfOfficeScreen delegationAutoOutOfOfficeScreen = delegationOptionScreen.tapOnAutoOutOfOfficeDelegation();
        DelegationPortfolioScreen delegationPortfolioScreen = delegationAutoOutOfOfficeScreen.fillInDefaultForm(workflowTypeList,
                DELEGATION_TYPE_REPORT_LABEL, DELEGATION_OPTION_ALL, DELEGATION_OPTION_FX, DELEGATION_OPTION_FX_CASH,
                DELEGATION_OPTION_FX_ASA, countryList, DELEGATION_OPTION_Y);
        DelegationDefaultCreationScreen delegationDefaultCreationScreen = delegationPortfolioScreen
                .selectPortfolio(false, count);
        delegationScreen = delegationDefaultCreationScreen.createDefaultDelegation("1",
                prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment), FAILED_MSG_FAILED_TO_CREATE_DEFAULT_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to reject the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegatorUsername"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.rejectDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_REJECTED),
                SCREENSHOT_MSG_FAILED_TO_REJECT_DELEGATION);
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_REJECTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_PERIOD_DELEGATION, TEST_GRP_PNL, TEST_GRP_REJECT_DELEGATION})
    public void pnlRejectAndDeleteUserDelegation() {
        System.out.println("Method: pnlRejectAndDeleteUserDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_PNL);
        //The duration of the delegation cannot be for more than 1 year
        DateFormat dateFormat = new SimpleDateFormat("dd, MMMM, yyyy");
        String fromDate = dateFormat.format(new Date());
        String toDate = dateFormat.format(new Date());
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationPeriodScreen delegationPeriodScreen = delegationOptionScreen.tapOnPeriodDelegation();
        DelegationUsersCreationScreen delegationUsersCreationScreen = delegationPeriodScreen
                .fillInUsersForm(workflowTypeList);
        delegationScreen = delegationUsersCreationScreen.createUsersDelegation(fromDate, toDate, prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"),
                DELEGATION_TYPE_USER, workflowTypeList.get(0), comment), FAILED_MSG_FAILED_TO_CREATE_USERS_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to reject the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegatorUsername"), DELEGATION_TYPE_USER,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.rejectDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_REJECTED),
                SCREENSHOT_MSG_FAILED_TO_REJECT_DELEGATION);
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_USER,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_REJECTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    //-------------------------------- GT/GMR ---------------------------------

    @Test(groups = {TEST_GRP_AUTO_OUT_OF_OFFICE_DELEGATION, TEST_GRP_GT_GMR, TEST_GRP_ACCEPT_DELEGATION})
    public void gmrAcceptAndDeleteAutoOutOfOfficeDeskOrCountryDelegation() {
        System.out.println("Method: gmrAcceptAndDeleteAutoOutOfOfficeDeskOrCountryDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        List<String> countryList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_GMR);
        countryList.add(DELEGATION_COUNTRY_JAPAN);
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationAutoOutOfOfficeScreen delegationAutoOutOfOfficeScreen = delegationOptionScreen.tapOnAutoOutOfOfficeDelegation();
        DelegationPortfolioScreen delegationPortfolioScreen = delegationAutoOutOfOfficeScreen.fillInDefaultForm(workflowTypeList,
                DELEGATION_TYPE_DESK_OR_COUNTRY, DELEGATION_OPTION_ALL, DELEGATION_OPTION_CMS, DELEGATION_OPTION_ABS_STRUCTURED,
                DELEGATION_OPTION_ABS_STRUCTERED_MTM, countryList, null);
        DelegationDefaultCreationScreen delegationDefaultCreationScreen = delegationPortfolioScreen
                .selectPortfolio(true, 0);
        delegationScreen = delegationDefaultCreationScreen.createDefaultDelegation("1",
                prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"),
                DELEGATION_TYPE_AUTO_OUT_OF_OFFICE, workflowTypeList.get(0), comment), FAILED_MSG_FAILED_TO_CREATE_DEFAULT_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to accept the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.FOUsername02"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.acceptDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_ACCEPTED),
                SCREENSHOT_MSG_FAILED_TO_ACCEPT_DELEGATION);
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_ACCEPTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_AUTO_OUT_OF_OFFICE_DELEGATION, TEST_GRP_GT_GMR, TEST_GRP_ACCEPT_DELEGATION})
    public void gmrAcceptAndDeleteAutoOutOfOfficeReportLabelDelegation() {
        System.out.println("Method: gmrAcceptAndDeleteAutoOutOfOfficeReportLabelDelegation");
        int count = 1;
        List<String> workflowTypeList = new ArrayList<>();
        List<String> countryList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_GMR);
        countryList.add(DELEGATION_COUNTRY_JAPAN);
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationAutoOutOfOfficeScreen delegationAutoOutOfOfficeScreen = delegationOptionScreen.tapOnAutoOutOfOfficeDelegation();
        DelegationPortfolioScreen delegationPortfolioScreen = delegationAutoOutOfOfficeScreen.fillInDefaultForm(workflowTypeList,
                DELEGATION_TYPE_REPORT_LABEL, DELEGATION_OPTION_ALL, DELEGATION_OPTION_CMS, DELEGATION_OPTION_ABS_STRUCTURED,
                DELEGATION_OPTION_ABS_STRUCTERED_MTM, countryList, null);
        DelegationDefaultCreationScreen delegationDefaultCreationScreen = delegationPortfolioScreen
                .selectPortfolio(false, count);
        delegationScreen = delegationDefaultCreationScreen.createDefaultDelegation("1",
                prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"),
                DELEGATION_TYPE_AUTO_OUT_OF_OFFICE, workflowTypeList.get(0), comment), FAILED_MSG_FAILED_TO_CREATE_DEFAULT_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to accept the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.FOUsername02"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.acceptDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_ACCEPTED),
                SCREENSHOT_MSG_FAILED_TO_ACCEPT_DELEGATION);
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_ACCEPTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_PERIOD_DELEGATION, TEST_GRP_GT_GMR, TEST_GRP_ACCEPT_DELEGATION})
    public void gmrAcceptAndDeleteUserDelegation() {
        System.out.println("Method: gmrAcceptAndDeleteUserDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_GMR);
        //The duration of the delegation cannot be for more than 1 year
        DateFormat dateFormat = new SimpleDateFormat("dd, MMMM, yyyy");
        String fromDate = dateFormat.format(new Date());
        String toDate = dateFormat.format(new Date());
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationPeriodScreen delegationPeriodScreen = delegationOptionScreen.tapOnPeriodDelegation();
        DelegationUsersCreationScreen delegationUsersCreationScreen = delegationPeriodScreen
                .fillInUsersForm(workflowTypeList);
        delegationScreen = delegationUsersCreationScreen.createUsersDelegation(fromDate, toDate, prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"),
                DELEGATION_TYPE_USER, workflowTypeList.get(0), comment), FAILED_MSG_FAILED_TO_CREATE_USERS_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to accept the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.FOUsername02"), DELEGATION_TYPE_USER,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.acceptDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_ACCEPTED),
                SCREENSHOT_MSG_FAILED_TO_ACCEPT_DELEGATION);
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_USER,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_ACCEPTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_AUTO_OUT_OF_OFFICE_DELEGATION, TEST_GRP_GT_GMR, TEST_GRP_REJECT_DELEGATION})
    public void gmrRejectAndDeleteAutoOutOfOfficeDeskOrCountryDelegation() {
        System.out.println("Method: gmrRejectAndDeleteAutoOutOfOfficeDeskOrCountryDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        List<String> countryList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_GMR);
        countryList.add(DELEGATION_COUNTRY_JAPAN);
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationAutoOutOfOfficeScreen delegationAutoOutOfOfficeScreen = delegationOptionScreen.tapOnAutoOutOfOfficeDelegation();
        DelegationPortfolioScreen delegationPortfolioScreen = delegationAutoOutOfOfficeScreen.fillInDefaultForm(workflowTypeList,
                DELEGATION_TYPE_DESK_OR_COUNTRY, DELEGATION_OPTION_ALL, DELEGATION_OPTION_CMS, DELEGATION_OPTION_ABS_STRUCTURED,
                DELEGATION_OPTION_ABS_STRUCTERED_MTM, countryList, null);
        DelegationDefaultCreationScreen delegationDefaultCreationScreen = delegationPortfolioScreen
                .selectPortfolio(true, 0);
        delegationScreen = delegationDefaultCreationScreen.createDefaultDelegation("1",
                prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"),
                DELEGATION_TYPE_AUTO_OUT_OF_OFFICE, workflowTypeList.get(0), comment), FAILED_MSG_FAILED_TO_CREATE_DEFAULT_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to reject the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.FOUsername02"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.rejectDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_REJECTED),
                SCREENSHOT_MSG_FAILED_TO_REJECT_DELEGATION);
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_REJECTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_AUTO_OUT_OF_OFFICE_DELEGATION, TEST_GRP_GT_GMR, TEST_GRP_REJECT_DELEGATION})
    public void gmrRejectAndDeleteAutoOutOfOfficeReportLabelDelegation() {
        System.out.println("Method: gmrRejectAndDeleteAutoOutOfOfficeReportLabelDelegation");
        int count = 1;
        List<String> workflowTypeList = new ArrayList<>();
        List<String> countryList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_GMR);
        countryList.add(DELEGATION_COUNTRY_JAPAN);
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationAutoOutOfOfficeScreen delegationAutoOutOfOfficeScreen = delegationOptionScreen.tapOnAutoOutOfOfficeDelegation();
        DelegationPortfolioScreen delegationPortfolioScreen = delegationAutoOutOfOfficeScreen.fillInDefaultForm(workflowTypeList,
                DELEGATION_TYPE_REPORT_LABEL, DELEGATION_OPTION_ALL, DELEGATION_OPTION_CMS, DELEGATION_OPTION_ABS_STRUCTURED,
                DELEGATION_OPTION_ABS_STRUCTERED_MTM, countryList, null);
        DelegationDefaultCreationScreen delegationDefaultCreationScreen = delegationPortfolioScreen
                .selectPortfolio(false, count);
        delegationScreen = delegationDefaultCreationScreen.createDefaultDelegation("1",
                prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"),
                DELEGATION_TYPE_AUTO_OUT_OF_OFFICE, workflowTypeList.get(0), comment), FAILED_MSG_FAILED_TO_CREATE_DEFAULT_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to reject the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.FOUsername02"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.rejectDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_REJECTED),
                SCREENSHOT_MSG_FAILED_TO_REJECT_DELEGATION);
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_REJECTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_PERIOD_DELEGATION, TEST_GRP_GT_GMR, TEST_GRP_REJECT_DELEGATION})
    public void gmrRejectAndDeleteUserDelegation() {
        System.out.println("Method: gmrRejectAndDeleteUserDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_GMR);
        //The duration of the delegation cannot be for more than 1 year
        DateFormat dateFormat = new SimpleDateFormat("dd, MMMM, yyyy");
        String fromDate = dateFormat.format(new Date());
        String toDate = dateFormat.format(new Date());
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationPeriodScreen delegationPeriodScreen = delegationOptionScreen.tapOnPeriodDelegation();
        DelegationUsersCreationScreen delegationUsersCreationScreen = delegationPeriodScreen
                .fillInUsersForm(workflowTypeList);
        delegationScreen = delegationUsersCreationScreen.createUsersDelegation(fromDate, toDate, prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"),
                DELEGATION_TYPE_USER, workflowTypeList.get(0), comment), FAILED_MSG_FAILED_TO_CREATE_USERS_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to reject the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.FOUsername02"), DELEGATION_TYPE_USER,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.rejectDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_REJECTED),
                SCREENSHOT_MSG_FAILED_TO_REJECT_DELEGATION);
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_USER,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_REJECTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }


    //-------------------------------- IPV/FVA ---------------------------------

    @Test(groups = {TEST_GRP_AUTO_OUT_OF_OFFICE_DELEGATION, TEST_GRP_IPV_FVA, TEST_GRP_ACCEPT_DELEGATION})
    public void fvaAcceptAndDeleteAutoOutOfOfficeDeskOrCountryDelegation() {
        System.out.println("Method: fvaAcceptAndDeleteAutoOutOfOfficeDeskOrCountryDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        List<String> countryList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_FVA);
        countryList.add(DELEGATION_OPTION_ALL);
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationAutoOutOfOfficeScreen delegationAutoOutOfOfficeScreen = delegationOptionScreen.tapOnAutoOutOfOfficeDelegation();
        DelegationPortfolioScreen delegationPortfolioScreen = delegationAutoOutOfOfficeScreen.fillInDefaultForm(workflowTypeList,
                DELEGATION_TYPE_DESK_OR_COUNTRY, DELEGATION_OPTION_ALL, DELEGATION_OPTION_FX, DELEGATION_OPTION_FX_CASH,
                DELEGATION_OPTION_FX_ASA, countryList, null);
        DelegationDefaultCreationScreen delegationDefaultCreationScreen = delegationPortfolioScreen
                .selectPortfolio(true, 0);
        delegationScreen = delegationDefaultCreationScreen.createDefaultDelegation("1",
                prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"),
                DELEGATION_TYPE_AUTO_OUT_OF_OFFICE, workflowTypeList.get(0), comment), FAILED_MSG_FAILED_TO_CREATE_DEFAULT_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to accept the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.FOUsername02"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.acceptDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_ACCEPTED),
                SCREENSHOT_MSG_FAILED_TO_ACCEPT_DELEGATION);
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_ACCEPTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_AUTO_OUT_OF_OFFICE_DELEGATION, TEST_GRP_IPV_FVA, TEST_GRP_ACCEPT_DELEGATION})
    public void fvaAcceptAndDeleteAutoOutOfOfficeReportLabelDelegation() {
        System.out.println("Method: fvaAcceptAndDeleteAutoOutOfOfficeReportLabelDelegation");
        int count = 1;
        List<String> workflowTypeList = new ArrayList<>();
        List<String> countryList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_FVA);
        countryList.add(DELEGATION_COUNTRY_KOREA);
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationAutoOutOfOfficeScreen delegationAutoOutOfOfficeScreen = delegationOptionScreen.tapOnAutoOutOfOfficeDelegation();
        DelegationPortfolioScreen delegationPortfolioScreen = delegationAutoOutOfOfficeScreen.fillInDefaultForm(workflowTypeList,
                DELEGATION_TYPE_REPORT_LABEL, DELEGATION_OPTION_ALL, DELEGATION_OPTION_FX, DELEGATION_OPTION_FX_CASH,
                DELEGATION_OPTION_FX_ASA, countryList, null);
        DelegationDefaultCreationScreen delegationDefaultCreationScreen = delegationPortfolioScreen
                .selectPortfolio(false, count);
        delegationScreen = delegationDefaultCreationScreen.createDefaultDelegation("1",
                prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment), FAILED_MSG_FAILED_TO_CREATE_DEFAULT_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to accept the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.FOUsername02"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.acceptDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_ACCEPTED),
                SCREENSHOT_MSG_FAILED_TO_ACCEPT_DELEGATION);
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_ACCEPTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_PERIOD_DELEGATION, TEST_GRP_IPV_FVA, TEST_GRP_ACCEPT_DELEGATION})
    public void fvaAcceptAndDeleteUserDelegation() {
        System.out.println("Method: cnaAcceptAndDeleteUserDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_FVA);
        //The duration of the delegation cannot be for more than 1 year
        DateFormat dateFormat = new SimpleDateFormat("dd, MMMM, yyyy");
        String fromDate = dateFormat.format(new Date());
        String toDate = dateFormat.format(new Date());
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationPeriodScreen delegationPeriodScreen = delegationOptionScreen.tapOnPeriodDelegation();
        DelegationUsersCreationScreen delegationUsersCreationScreen = delegationPeriodScreen
                .fillInUsersForm(workflowTypeList);
        delegationScreen = delegationUsersCreationScreen.createUsersDelegation(fromDate, toDate, prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"),
                DELEGATION_TYPE_USER, workflowTypeList.get(0), comment), FAILED_MSG_FAILED_TO_CREATE_USERS_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to accept the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.FOUsername02"), DELEGATION_TYPE_USER,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.acceptDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_ACCEPTED),
                SCREENSHOT_MSG_FAILED_TO_ACCEPT_DELEGATION);
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_USER,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_ACCEPTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_AUTO_OUT_OF_OFFICE_DELEGATION, TEST_GRP_IPV_FVA, TEST_GRP_REJECT_DELEGATION})
    public void fvaRejectAndDeleteAutoOutOfOfficeDeskOrCountryDelegation() {
        System.out.println("Method: fvaRejectAndDeleteAutoOutOfOfficeDeskOrCountryDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        List<String> countryList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_FVA);
        countryList.add(DELEGATION_OPTION_ALL);
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationAutoOutOfOfficeScreen delegationAutoOutOfOfficeScreen = delegationOptionScreen.tapOnAutoOutOfOfficeDelegation();
        DelegationPortfolioScreen delegationPortfolioScreen = delegationAutoOutOfOfficeScreen.fillInDefaultForm(workflowTypeList,
                DELEGATION_TYPE_DESK_OR_COUNTRY, DELEGATION_OPTION_ALL, DELEGATION_OPTION_FX, DELEGATION_OPTION_FX_CASH,
                DELEGATION_OPTION_FX_ASA, countryList, null);
        DelegationDefaultCreationScreen delegationDefaultCreationScreen = delegationPortfolioScreen
                .selectPortfolio(true, 0);
        delegationScreen = delegationDefaultCreationScreen.createDefaultDelegation("1",
                prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"),
                DELEGATION_TYPE_AUTO_OUT_OF_OFFICE, workflowTypeList.get(0), comment), FAILED_MSG_FAILED_TO_CREATE_DEFAULT_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to accept the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.FOUsername02"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.rejectDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_REJECTED),
                SCREENSHOT_MSG_FAILED_TO_REJECT_DELEGATION);
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_REJECTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_AUTO_OUT_OF_OFFICE_DELEGATION, TEST_GRP_IPV_FVA, TEST_GRP_REJECT_DELEGATION})
    public void fvaRejectAndDeleteAutoOutOfOfficeReportLabelDelegation() {
        System.out.println("Method: fvaRejectAndDeleteAutoOutOfOfficeReportLabelDelegation");
        int count = 1;
        List<String> workflowTypeList = new ArrayList<>();
        List<String> countryList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_FVA);
        countryList.add(DELEGATION_COUNTRY_KOREA);
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationAutoOutOfOfficeScreen delegationAutoOutOfOfficeScreen = delegationOptionScreen.tapOnAutoOutOfOfficeDelegation();
        DelegationPortfolioScreen delegationPortfolioScreen = delegationAutoOutOfOfficeScreen.fillInDefaultForm(workflowTypeList,
                DELEGATION_TYPE_REPORT_LABEL, DELEGATION_OPTION_ALL, DELEGATION_OPTION_FX, DELEGATION_OPTION_FX_CASH,
                DELEGATION_OPTION_FX_ASA, countryList, null);
        DelegationDefaultCreationScreen delegationDefaultCreationScreen = delegationPortfolioScreen
                .selectPortfolio(false, count);
        delegationScreen = delegationDefaultCreationScreen.createDefaultDelegation("1",
                prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment), FAILED_MSG_FAILED_TO_CREATE_DEFAULT_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to accept the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.FOUsername02"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.rejectDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_REJECTED),
                SCREENSHOT_MSG_FAILED_TO_REJECT_DELEGATION);
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_REJECTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_PERIOD_DELEGATION, TEST_GRP_IPV_FVA, TEST_GRP_REJECT_DELEGATION})
    public void fvaRejectAndDeleteUserDelegation() {
        System.out.println("Method: fvaRejectAndDeleteUserDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_FVA);
        //The duration of the delegation cannot be for more than 1 year
        DateFormat dateFormat = new SimpleDateFormat("dd, MMMM, yyyy");
        String fromDate = dateFormat.format(new Date());
        String toDate = dateFormat.format(new Date());
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationPeriodScreen delegationPeriodScreen = delegationOptionScreen.tapOnPeriodDelegation();
        DelegationUsersCreationScreen delegationUsersCreationScreen = delegationPeriodScreen
                .fillInUsersForm(workflowTypeList);
        delegationScreen = delegationUsersCreationScreen.createUsersDelegation(fromDate, toDate, prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"),
                DELEGATION_TYPE_USER, workflowTypeList.get(0), comment), FAILED_MSG_FAILED_TO_CREATE_USERS_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to accept the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.FOUsername02"), DELEGATION_TYPE_USER,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.rejectDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_REJECTED),
                SCREENSHOT_MSG_FAILED_TO_REJECT_DELEGATION);
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_USER,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_REJECTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    //-------------------------------- CE ---------------------------------

    @Test(groups = {TEST_GRP_AUTO_OUT_OF_OFFICE_DELEGATION, TEST_GRP_CE, TEST_GRP_ACCEPT_DELEGATION})
    public void ceAcceptAndDeleteAutoOutOfOfficeDelegation() {
        System.out.println("Method: ceAcceptAndDeleteAutoOutOfOfficeDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_CE);
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationAutoOutOfOfficeScreen delegationAutoOutOfOfficeScreen = delegationOptionScreen.tapOnAutoOutOfOfficeDelegation();
        DelegationDefaultCreationScreen delegationDefaultCreationScreen = delegationAutoOutOfOfficeScreen
                .fillInDefaultForm(workflowTypeList, DELEGATION_TYPE_USER);
        delegationScreen = delegationDefaultCreationScreen
                .createDefaultDelegation("1", prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"),
                DELEGATION_TYPE_AUTO_OUT_OF_OFFICE, workflowTypeList.get(0), comment),
                FAILED_MSG_FAILED_TO_CREATE_DEFAULT_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to accept the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegatorUsername"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.acceptDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_ACCEPTED),
                SCREENSHOT_MSG_FAILED_TO_ACCEPT_DELEGATION);
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_ACCEPTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_PERIOD_DELEGATION, TEST_GRP_CE, TEST_GRP_ACCEPT_DELEGATION})
    public void ceAcceptAndDeleteUserDelegation() {
        System.out.println("Method: ceAcceptAndDeleteUserDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_CE);
        //The duration of the delegation cannot be for more than 1 year
        DateFormat dateFormat = new SimpleDateFormat("dd, MMMM, yyyy");
        String fromDate = dateFormat.format(new Date());
        String toDate = dateFormat.format(new Date());
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationPeriodScreen delegationPeriodScreen = delegationOptionScreen.tapOnPeriodDelegation();
        DelegationUsersCreationScreen delegationUsersCreationScreen = delegationPeriodScreen.fillInUsersForm(workflowTypeList);
        delegationScreen = delegationUsersCreationScreen.createUsersDelegation(fromDate, toDate, prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"),
                DELEGATION_TYPE_USER, workflowTypeList.get(0), comment), FAILED_MSG_FAILED_TO_CREATE_USERS_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to accept the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegatorUsername"), DELEGATION_TYPE_USER,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.acceptDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_ACCEPTED),
                SCREENSHOT_MSG_FAILED_TO_ACCEPT_DELEGATION);
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_USER,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_ACCEPTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_AUTO_OUT_OF_OFFICE_DELEGATION, TEST_GRP_CE, TEST_GRP_REJECT_DELEGATION})
    public void ceRejectAndDeleteAutoOutOfOfficeDelegation() {
        System.out.println("Method: ceRejectAndDeleteAutoOutOfOfficeDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_CE);
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationAutoOutOfOfficeScreen delegationAutoOutOfOfficeScreen = delegationOptionScreen.tapOnAutoOutOfOfficeDelegation();
        DelegationDefaultCreationScreen delegationDefaultCreationScreen = delegationAutoOutOfOfficeScreen
                .fillInDefaultForm(workflowTypeList, DELEGATION_TYPE_USER);
        delegationScreen = delegationDefaultCreationScreen
                .createDefaultDelegation("1", prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"),
                DELEGATION_TYPE_AUTO_OUT_OF_OFFICE, workflowTypeList.get(0), comment),
                FAILED_MSG_FAILED_TO_CREATE_DEFAULT_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to reject the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegatorUsername"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.rejectDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_REJECTED),
                SCREENSHOT_MSG_FAILED_TO_REJECT_DELEGATION);
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_REJECTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_PERIOD_DELEGATION, TEST_GRP_CE, TEST_GRP_REJECT_DELEGATION})
    public void ceRejectAndDeleteUserDelegation() {
        System.out.println("Method: ceRejectAndDeleteUserDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_CE);
        //The duration of the delegation cannot be for more than 1 year
        DateFormat dateFormat = new SimpleDateFormat("dd, MMMM, yyyy");
        String fromDate = dateFormat.format(new Date());
        String toDate = dateFormat.format(new Date());
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationPeriodScreen delegationPeriodScreen = delegationOptionScreen.tapOnPeriodDelegation();
        DelegationUsersCreationScreen delegationUsersCreationScreen = delegationPeriodScreen
                .fillInUsersForm(workflowTypeList);
        delegationScreen = delegationUsersCreationScreen.createUsersDelegation(fromDate, toDate, prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"),
                DELEGATION_TYPE_USER, workflowTypeList.get(0), comment), FAILED_MSG_FAILED_TO_CREATE_USERS_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to reject the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegatorUsername"), DELEGATION_TYPE_USER,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.rejectDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_REJECTED),
                SCREENSHOT_MSG_FAILED_TO_REJECT_DELEGATION);
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_USER,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_REJECTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    //-------------------------------- VE ---------------------------------

    @Test(groups = {TEST_GRP_AUTO_OUT_OF_OFFICE_DELEGATION, TEST_GRP_VE, TEST_GRP_ACCEPT_DELEGATION})
    public void veAcceptAndDeleteAutoOutOfOfficeDelegation() {
        System.out.println("Method: veAcceptAndDeleteAutoOutOfOfficeDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_VE);
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationAutoOutOfOfficeScreen delegationAutoOutOfOfficeScreen = delegationOptionScreen.tapOnAutoOutOfOfficeDelegation();
        DelegationDefaultCreationScreen delegationDefaultCreationScreen = delegationAutoOutOfOfficeScreen
                .fillInDefaultForm(workflowTypeList, DELEGATION_TYPE_USER);
        delegationScreen = delegationDefaultCreationScreen
                .createDefaultDelegation("1", prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"),
                DELEGATION_TYPE_AUTO_OUT_OF_OFFICE, workflowTypeList.get(0), comment),
                FAILED_MSG_FAILED_TO_CREATE_DEFAULT_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to accept the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegatorUsername"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.acceptDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_ACCEPTED),
                SCREENSHOT_MSG_FAILED_TO_ACCEPT_DELEGATION);
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_ACCEPTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_PERIOD_DELEGATION, TEST_GRP_VE, TEST_GRP_ACCEPT_DELEGATION})
    public void veAcceptAndDeleteUserDelegation() {
        System.out.println("Method: veAcceptAndDeleteUserDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_VE);
        //The duration of the delegation cannot be for more than 1 year
        DateFormat dateFormat = new SimpleDateFormat("dd, MMMM, yyyy");
        String fromDate = dateFormat.format(new Date());
        String toDate = dateFormat.format(new Date());
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationPeriodScreen delegationPeriodScreen = delegationOptionScreen.tapOnPeriodDelegation();
        DelegationUsersCreationScreen delegationUsersCreationScreen = delegationPeriodScreen
                .fillInUsersForm(workflowTypeList);
        delegationScreen = delegationUsersCreationScreen.createUsersDelegation(fromDate, toDate, prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"),
                DELEGATION_TYPE_USER, workflowTypeList.get(0), comment), FAILED_MSG_FAILED_TO_CREATE_USERS_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to accept the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegatorUsername"), DELEGATION_TYPE_USER,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.acceptDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_ACCEPTED),
                SCREENSHOT_MSG_FAILED_TO_ACCEPT_DELEGATION);
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_USER,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_ACCEPTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_AUTO_OUT_OF_OFFICE_DELEGATION, TEST_GRP_VE, TEST_GRP_REJECT_DELEGATION})
    public void veRejectAndDeleteAutoOutOfOfficeDelegation() {
        System.out.println("Method: veRejectAndDeleteAutoOutOfOfficeDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_VE);
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationAutoOutOfOfficeScreen delegationAutoOutOfOfficeScreen = delegationOptionScreen.tapOnAutoOutOfOfficeDelegation();
        DelegationDefaultCreationScreen delegationDefaultCreationScreen = delegationAutoOutOfOfficeScreen
                .fillInDefaultForm(workflowTypeList, DELEGATION_TYPE_USER);
        delegationScreen = delegationDefaultCreationScreen
                .createDefaultDelegation("1", prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"),
                DELEGATION_TYPE_AUTO_OUT_OF_OFFICE, workflowTypeList.get(0), comment),
                FAILED_MSG_FAILED_TO_CREATE_DEFAULT_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to reject the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegatorUsername"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.rejectDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_REJECTED),
                SCREENSHOT_MSG_FAILED_TO_REJECT_DELEGATION);
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_REJECTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_PERIOD_DELEGATION, TEST_GRP_VE, TEST_GRP_REJECT_DELEGATION})
    public void veRejectAndDeleteUserDelegation() {
        System.out.println("Method: veRejectAndDeleteUserDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_VE);
        //The duration of the delegation cannot be for more than 1 year
        DateFormat dateFormat = new SimpleDateFormat("dd, MMMM, yyyy");
        String fromDate = dateFormat.format(new Date());
        String toDate = dateFormat.format(new Date());
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationPeriodScreen delegationPeriodScreen = delegationOptionScreen.tapOnPeriodDelegation();
        DelegationUsersCreationScreen delegationUsersCreationScreen = delegationPeriodScreen
                .fillInUsersForm(workflowTypeList);
        delegationScreen = delegationUsersCreationScreen.createUsersDelegation(fromDate, toDate, prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"),
                DELEGATION_TYPE_USER, workflowTypeList.get(0), comment), FAILED_MSG_FAILED_TO_CREATE_USERS_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to reject the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegatorUsername"), DELEGATION_TYPE_USER,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.rejectDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_REJECTED),
                SCREENSHOT_MSG_FAILED_TO_REJECT_DELEGATION);
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_USER,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_REJECTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    //-------------------------------- AFO ---------------------------------

    @Test(groups = {TEST_GRP_AUTO_OUT_OF_OFFICE_DELEGATION, TEST_GRP_AFO, TEST_GRP_ACCEPT_DELEGATION})
    public void afoAcceptAndDeleteAutoOutOfOfficeDelegation() {
        System.out.println("Method: afoAcceptAndDeleteAutoOutOfOfficeDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_AFO);
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationAutoOutOfOfficeScreen delegationAutoOutOfOfficeScreen = delegationOptionScreen.tapOnAutoOutOfOfficeDelegation();
        DelegationDefaultCreationScreen delegationDefaultCreationScreen = delegationAutoOutOfOfficeScreen
                .fillInDefaultForm(workflowTypeList, DELEGATION_TYPE_USER);
        delegationScreen = delegationDefaultCreationScreen
                .createDefaultDelegation("1", prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"),
                DELEGATION_TYPE_AUTO_OUT_OF_OFFICE, workflowTypeList.get(0), comment),
                FAILED_MSG_FAILED_TO_CREATE_DEFAULT_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to accept the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegatorUsername"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.acceptDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_ACCEPTED),
                SCREENSHOT_MSG_FAILED_TO_ACCEPT_DELEGATION);
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_ACCEPTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_PERIOD_DELEGATION, TEST_GRP_AFO, TEST_GRP_ACCEPT_DELEGATION})
    public void afoAcceptAndDeleteUserDelegation() {
        System.out.println("Method: afoAcceptAndDeleteUserDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_AFO);
        //The duration of the delegation cannot be for more than 1 year
        DateFormat dateFormat = new SimpleDateFormat("dd, MMMM, yyyy");
        String fromDate = dateFormat.format(new Date());
        String toDate = dateFormat.format(new Date());
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationPeriodScreen delegationPeriodScreen = delegationOptionScreen.tapOnPeriodDelegation();
        DelegationUsersCreationScreen delegationUsersCreationScreen = delegationPeriodScreen
                .fillInUsersForm(workflowTypeList);
        delegationScreen = delegationUsersCreationScreen.createUsersDelegation(fromDate, toDate, prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"),
                DELEGATION_TYPE_USER, workflowTypeList.get(0), comment), FAILED_MSG_FAILED_TO_CREATE_USERS_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to accept the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegatorUsername"), DELEGATION_TYPE_USER,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.acceptDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_ACCEPTED),
                SCREENSHOT_MSG_FAILED_TO_ACCEPT_DELEGATION);
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_USER,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_ACCEPTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_AUTO_OUT_OF_OFFICE_DELEGATION, TEST_GRP_AFO, TEST_GRP_REJECT_DELEGATION})
    public void afoRejectAndDeleteAutoOutOfOfficeDelegation() {
        System.out.println("Method: afoRejectAndDeleteAutoOutOfOfficeDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_AFO);
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationAutoOutOfOfficeScreen delegationAutoOutOfOfficeScreen = delegationOptionScreen.tapOnAutoOutOfOfficeDelegation();
        DelegationDefaultCreationScreen delegationDefaultCreationScreen = delegationAutoOutOfOfficeScreen
                .fillInDefaultForm(workflowTypeList, DELEGATION_TYPE_USER);
        delegationScreen = delegationDefaultCreationScreen
                .createDefaultDelegation("1", prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"),
                DELEGATION_TYPE_AUTO_OUT_OF_OFFICE, workflowTypeList.get(0), comment),
                FAILED_MSG_FAILED_TO_CREATE_DEFAULT_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to reject the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegatorUsername"),
                DELEGATION_TYPE_AUTO_OUT_OF_OFFICE, workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.rejectDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_REJECTED),
                SCREENSHOT_MSG_FAILED_TO_REJECT_DELEGATION);
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_REJECTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_PERIOD_DELEGATION, TEST_GRP_AFO, TEST_GRP_REJECT_DELEGATION})
    public void afoRejectAndDeleteUserDelegation() {
        System.out.println("Method: afoRejectAndDeleteUserDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_AFO);
        //The duration of the delegation cannot be for more than 1 year
        DateFormat dateFormat = new SimpleDateFormat("dd, MMMM, yyyy");
        String fromDate = dateFormat.format(new Date());
        String toDate = dateFormat.format(new Date());
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationPeriodScreen delegationPeriodScreen = delegationOptionScreen.tapOnPeriodDelegation();
        DelegationUsersCreationScreen delegationUsersCreationScreen = delegationPeriodScreen.fillInUsersForm(workflowTypeList);
        delegationScreen = delegationUsersCreationScreen.createUsersDelegation(fromDate, toDate, prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"),
                DELEGATION_TYPE_USER, workflowTypeList.get(0), comment), FAILED_MSG_FAILED_TO_CREATE_USERS_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to reject the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegatorUsername"), DELEGATION_TYPE_USER,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.rejectDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_REJECTED),
                SCREENSHOT_MSG_FAILED_TO_REJECT_DELEGATION);
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_USER,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_REJECTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    //-------------------------------- BEX ---------------------------------

    @Test(groups = {TEST_GRP_AUTO_OUT_OF_OFFICE_DELEGATION, TEST_GRP_BEX, TEST_GRP_ACCEPT_DELEGATION})
    public void bexAcceptAndDeleteAutoOutOfOfficeDelegation() {
        System.out.println("Method: bexAcceptAndDeleteAutoOutOfOfficeDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_BEX);
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationAutoOutOfOfficeScreen delegationAutoOutOfOfficeScreen = delegationOptionScreen.tapOnAutoOutOfOfficeDelegation();
        DelegationDefaultCreationScreen delegationDefaultCreationScreen = delegationAutoOutOfOfficeScreen
                .fillInDefaultForm(workflowTypeList, DELEGATION_TYPE_USER);
        delegationScreen = delegationDefaultCreationScreen
                .createDefaultDelegation("1", prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"),
                DELEGATION_TYPE_AUTO_OUT_OF_OFFICE, workflowTypeList.get(0), comment),
                FAILED_MSG_FAILED_TO_CREATE_DEFAULT_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to accept the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegatorUsername"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.acceptDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_ACCEPTED),
                SCREENSHOT_MSG_FAILED_TO_ACCEPT_DELEGATION);
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_ACCEPTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_PERIOD_DELEGATION, TEST_GRP_BEX, TEST_GRP_ACCEPT_DELEGATION})
    public void bexAcceptAndDeleteUserDelegation() {
        System.out.println("Method: bexAcceptAndDeleteUserDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_BEX);
        //The duration of the delegation cannot be for more than 1 year
        DateFormat dateFormat = new SimpleDateFormat("dd, MMMM, yyyy");
        String fromDate = dateFormat.format(new Date());
        String toDate = dateFormat.format(new Date());
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationPeriodScreen delegationPeriodScreen = delegationOptionScreen.tapOnPeriodDelegation();
        DelegationUsersCreationScreen delegationUsersCreationScreen = delegationPeriodScreen
                .fillInUsersForm(workflowTypeList);
        delegationScreen = delegationUsersCreationScreen.createUsersDelegation(fromDate, toDate, prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"),
                DELEGATION_TYPE_USER, workflowTypeList.get(0), comment), FAILED_MSG_FAILED_TO_CREATE_USERS_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to accept the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegatorUsername"), DELEGATION_TYPE_USER,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.acceptDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_ACCEPTED),
                SCREENSHOT_MSG_FAILED_TO_ACCEPT_DELEGATION);
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_USER,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_ACCEPTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_AUTO_OUT_OF_OFFICE_DELEGATION, TEST_GRP_BEX, TEST_GRP_REJECT_DELEGATION})
    public void bexRejectAndDeleteAutoOutOfOfficeDelegation() {
        System.out.println("Method: bexRejectAndDeleteAutoOutOfOfficeDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_BEX);
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationAutoOutOfOfficeScreen delegationAutoOutOfOfficeScreen = delegationOptionScreen.tapOnAutoOutOfOfficeDelegation();
        DelegationDefaultCreationScreen delegationDefaultCreationScreen = delegationAutoOutOfOfficeScreen
                .fillInDefaultForm(workflowTypeList, DELEGATION_TYPE_USER);
        delegationScreen = delegationDefaultCreationScreen
                .createDefaultDelegation("1", prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"),
                DELEGATION_TYPE_AUTO_OUT_OF_OFFICE, workflowTypeList.get(0), comment),
                FAILED_MSG_FAILED_TO_CREATE_DEFAULT_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to reject the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegatorUsername"),
                DELEGATION_TYPE_AUTO_OUT_OF_OFFICE, workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.rejectDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_REJECTED),
                SCREENSHOT_MSG_FAILED_TO_REJECT_DELEGATION);
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_REJECTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_PERIOD_DELEGATION, TEST_GRP_BEX, TEST_GRP_REJECT_DELEGATION})
    public void bexRejectAndDeleteUserDelegation() {
        System.out.println("Method: bexRejectAndDeleteUserDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_BEX);
        //The duration of the delegation cannot be for more than 1 year
        DateFormat dateFormat = new SimpleDateFormat("dd, MMMM, yyyy");
        String fromDate = dateFormat.format(new Date());
        String toDate = dateFormat.format(new Date());
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationPeriodScreen delegationPeriodScreen = delegationOptionScreen.tapOnPeriodDelegation();
        DelegationUsersCreationScreen delegationUsersCreationScreen = delegationPeriodScreen.fillInUsersForm(workflowTypeList);
        delegationScreen = delegationUsersCreationScreen.createUsersDelegation(fromDate, toDate, prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"),
                DELEGATION_TYPE_USER, workflowTypeList.get(0), comment), FAILED_MSG_FAILED_TO_CREATE_USERS_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to reject the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegatorUsername"), DELEGATION_TYPE_USER,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.rejectDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_REJECTED),
                SCREENSHOT_MSG_FAILED_TO_REJECT_DELEGATION);
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_USER,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_REJECTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    //-------------------------------- MT ---------------------------------

    @Test(groups = {TEST_GRP_AUTO_OUT_OF_OFFICE_DELEGATION, TEST_GRP_MT, TEST_GRP_ACCEPT_DELEGATION})
    public void mtAcceptAndDeleteAutoOutOfOfficeDelegation() {
        System.out.println("Method: mtAcceptAndDeleteAutoOutOfOfficeDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_MT);
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationAutoOutOfOfficeScreen delegationAutoOutOfOfficeScreen = delegationOptionScreen.tapOnAutoOutOfOfficeDelegation();
        DelegationDefaultCreationScreen delegationDefaultCreationScreen = delegationAutoOutOfOfficeScreen
                .fillInDefaultForm(workflowTypeList, DELEGATION_TYPE_USER);
        delegationScreen = delegationDefaultCreationScreen
                .createDefaultDelegation("1", prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"),
                DELEGATION_TYPE_AUTO_OUT_OF_OFFICE, workflowTypeList.get(0), comment),
                FAILED_MSG_FAILED_TO_CREATE_DEFAULT_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to accept the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegatorUsername"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.acceptDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_ACCEPTED),
                SCREENSHOT_MSG_FAILED_TO_ACCEPT_DELEGATION);
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_ACCEPTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_PERIOD_DELEGATION, TEST_GRP_MT, TEST_GRP_ACCEPT_DELEGATION})
    public void mtAcceptAndDeleteUserDelegation() {
        System.out.println("Method: mtAcceptAndDeleteUserDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_MT);
        //The duration of the delegation cannot be for more than 1 year
        DateFormat dateFormat = new SimpleDateFormat("dd, MMMM, yyyy");
        String fromDate = dateFormat.format(new Date());
        String toDate = dateFormat.format(new Date());
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationPeriodScreen delegationPeriodScreen = delegationOptionScreen.tapOnPeriodDelegation();
        DelegationUsersCreationScreen delegationUsersCreationScreen = delegationPeriodScreen
                .fillInUsersForm(workflowTypeList);
        delegationScreen = delegationUsersCreationScreen.createUsersDelegation(fromDate, toDate, prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"),
                DELEGATION_TYPE_USER, workflowTypeList.get(0), comment), FAILED_MSG_FAILED_TO_CREATE_USERS_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to accept the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegatorUsername"), DELEGATION_TYPE_USER,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.acceptDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_ACCEPTED),
                SCREENSHOT_MSG_FAILED_TO_ACCEPT_DELEGATION);
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_USER,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_ACCEPTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_AUTO_OUT_OF_OFFICE_DELEGATION, TEST_GRP_MT, TEST_GRP_REJECT_DELEGATION})
    public void mtRejectAndDeleteAutoOutOfOfficeDelegation() {
        System.out.println("Method: mtRejectAndDeleteAutoOutOfOfficeDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_MT);
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationAutoOutOfOfficeScreen delegationAutoOutOfOfficeScreen = delegationOptionScreen.tapOnAutoOutOfOfficeDelegation();
        DelegationDefaultCreationScreen delegationDefaultCreationScreen = delegationAutoOutOfOfficeScreen
                .fillInDefaultForm(workflowTypeList, DELEGATION_TYPE_USER);
        delegationScreen = delegationDefaultCreationScreen
                .createDefaultDelegation("1", prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"),
                DELEGATION_TYPE_AUTO_OUT_OF_OFFICE, workflowTypeList.get(0), comment),
                FAILED_MSG_FAILED_TO_CREATE_DEFAULT_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to reject the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegatorUsername"),
                DELEGATION_TYPE_AUTO_OUT_OF_OFFICE, workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.rejectDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_REJECTED),
                SCREENSHOT_MSG_FAILED_TO_REJECT_DELEGATION);
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_REJECTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_PERIOD_DELEGATION, TEST_GRP_MT, TEST_GRP_REJECT_DELEGATION})
    public void mtRejectAndDeleteUserDelegation() {
        System.out.println("Method: mtRejectAndDeleteUserDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_MT);
        //The duration of the delegation cannot be for more than 1 year
        DateFormat dateFormat = new SimpleDateFormat("dd, MMMM, yyyy");
        String fromDate = dateFormat.format(new Date());
        String toDate = dateFormat.format(new Date());
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationPeriodScreen delegationPeriodScreen = delegationOptionScreen.tapOnPeriodDelegation();
        DelegationUsersCreationScreen delegationUsersCreationScreen = delegationPeriodScreen.fillInUsersForm(workflowTypeList);
        delegationScreen = delegationUsersCreationScreen.createUsersDelegation(fromDate, toDate, prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"),
                DELEGATION_TYPE_USER, workflowTypeList.get(0), comment), FAILED_MSG_FAILED_TO_CREATE_USERS_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to reject the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegatorUsername"), DELEGATION_TYPE_USER,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.rejectDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_REJECTED),
                SCREENSHOT_MSG_FAILED_TO_REJECT_DELEGATION);
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_USER,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_REJECTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    //-------------------------------- BRS ---------------------------------

    @Test(groups = {TEST_GRP_AUTO_OUT_OF_OFFICE_DELEGATION, TEST_GRP_BRS, TEST_GRP_ACCEPT_DELEGATION})
    public void brsAcceptAndDeleteAutoOutOfOfficeDelegation() {
        System.out.println("Method: brsAcceptAndDeleteAutoOutOfOfficeDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_BRS);
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationAutoOutOfOfficeScreen delegationAutoOutOfOfficeScreen = delegationOptionScreen.tapOnAutoOutOfOfficeDelegation();
        DelegationDefaultCreationScreen delegationDefaultCreationScreen = delegationAutoOutOfOfficeScreen
                .fillInDefaultForm(workflowTypeList, DELEGATION_TYPE_USER);
        delegationScreen = delegationDefaultCreationScreen
                .createDefaultDelegation("1", prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"),
                DELEGATION_TYPE_AUTO_OUT_OF_OFFICE, workflowTypeList.get(0), comment),
                FAILED_MSG_FAILED_TO_CREATE_DEFAULT_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to accept the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegatorUsername"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.acceptDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_ACCEPTED),
                SCREENSHOT_MSG_FAILED_TO_ACCEPT_DELEGATION);
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_ACCEPTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_PERIOD_DELEGATION, TEST_GRP_BRS, TEST_GRP_ACCEPT_DELEGATION})
    public void brsAcceptAndDeleteUserDelegation() {
        System.out.println("Method: brsAcceptAndDeleteUserDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_BRS);
        //The duration of the delegation cannot be for more than 1 year
        DateFormat dateFormat = new SimpleDateFormat("dd, MMMM, yyyy");
        String fromDate = dateFormat.format(new Date());
        String toDate = dateFormat.format(new Date());
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationPeriodScreen delegationPeriodScreen = delegationOptionScreen.tapOnPeriodDelegation();
        DelegationUsersCreationScreen delegationUsersCreationScreen = delegationPeriodScreen
                .fillInUsersForm(workflowTypeList);
        delegationScreen = delegationUsersCreationScreen.createUsersDelegation(fromDate, toDate, prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"),
                DELEGATION_TYPE_USER, workflowTypeList.get(0), comment), FAILED_MSG_FAILED_TO_CREATE_USERS_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to accept the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegatorUsername"), DELEGATION_TYPE_USER,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.acceptDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_ACCEPTED),
                SCREENSHOT_MSG_FAILED_TO_ACCEPT_DELEGATION);
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_USER,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_ACCEPTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_AUTO_OUT_OF_OFFICE_DELEGATION, TEST_GRP_BRS, TEST_GRP_REJECT_DELEGATION})
    public void brsRejectAndDeleteAutoOutOfOfficeDelegation() {
        System.out.println("Method: brsRejectAndDeleteAutoOutOfOfficeDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_BRS);
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationAutoOutOfOfficeScreen delegationAutoOutOfOfficeScreen = delegationOptionScreen.tapOnAutoOutOfOfficeDelegation();
        DelegationDefaultCreationScreen delegationDefaultCreationScreen = delegationAutoOutOfOfficeScreen
                .fillInDefaultForm(workflowTypeList, DELEGATION_TYPE_USER);
        delegationScreen = delegationDefaultCreationScreen
                .createDefaultDelegation("1", prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"),
                DELEGATION_TYPE_AUTO_OUT_OF_OFFICE, workflowTypeList.get(0), comment),
                FAILED_MSG_FAILED_TO_CREATE_DEFAULT_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to reject the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegatorUsername"),
                DELEGATION_TYPE_AUTO_OUT_OF_OFFICE, workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.rejectDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_REJECTED),
                SCREENSHOT_MSG_FAILED_TO_REJECT_DELEGATION);
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_AUTO_OUT_OF_OFFICE,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_REJECTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_PERIOD_DELEGATION, TEST_GRP_BRS, TEST_GRP_REJECT_DELEGATION})
    public void brsRejectAndDeleteUserDelegation() {
        System.out.println("Method: brsRejectAndDeleteUserDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_BRS);
        //The duration of the delegation cannot be for more than 1 year
        DateFormat dateFormat = new SimpleDateFormat("dd, MMMM, yyyy");
        String fromDate = dateFormat.format(new Date());
        String toDate = dateFormat.format(new Date());
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationPeriodScreen delegationPeriodScreen = delegationOptionScreen.tapOnPeriodDelegation();
        DelegationUsersCreationScreen delegationUsersCreationScreen = delegationPeriodScreen.fillInUsersForm(workflowTypeList);
        delegationScreen = delegationUsersCreationScreen.createUsersDelegation(fromDate, toDate, prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"),
                DELEGATION_TYPE_USER, workflowTypeList.get(0), comment), FAILED_MSG_FAILED_TO_CREATE_USERS_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to reject the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegatorUsername"), DELEGATION_TYPE_USER,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.rejectDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_REJECTED),
                SCREENSHOT_MSG_FAILED_TO_REJECT_DELEGATION);
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_USER,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_REJECTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }
}
