package com.scb.fmsw.mobile.test;

import com.scb.fmsw.mobile.WorkflowConstants;
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

    //Note: For manual workflow, in order to create delegation, the user has to be the acknowledger
    //create test method for CNA, OMR, PNL, GT/GMR, IPV/FVA, VE, CE

    //-------------------------------- MULTIPLE WORKFLOW TYPE ---------------------------------

    @Test(groups = {TEST_GRP_DEFAULT_DELEGATION, TEST_GRP_MULTIPLE_WORKFLOW, TEST_GRP_ACCEPT_DELEGATION})
    public void defaultMultipleWorkflowTypeDelegation() {
        System.out.println("Method: defaultMultipleWorkflowTypeDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        List<String> countryList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_CNA);
        workflowTypeList.add(WORKFLOW_PNL);
        countryList.add(DELEGATION_OPTION_ALL);
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationDefaultScreen delegationDefaultScreen = delegationOptionScreen.tapOnDefaultDelegation();
        DelegationPortfolioScreen delegationPortfolioScreen = delegationDefaultScreen.fillInDefaultForm(workflowTypeList,
                DELEGATION_TYPE_DESK_OR_COUNTRY, DELEGATION_OPTION_ALL, DELEGATION_OPTION_FX, DELEGATION_OPTION_FX_CASH,
                DELEGATION_OPTION_FX_ASA, countryList);
        DelegationDefaultCreationScreen delegationDefaultCreationScreen = workflowTypeList.size() == 1
                ? delegationPortfolioScreen.selectPortfolio(true, 0)
                : new DelegationDefaultCreationScreen(iosDriver);
        delegationScreen = delegationDefaultCreationScreen.createDefaultDelegation("2",
                prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment), FAILED_MSG_FAILED_TO_CREATE_DEFAULT_DELEGATION);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(1), comment), FAILED_MSG_FAILED_TO_CREATE_DEFAULT_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to accept the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        othersDelegationsScreen.acceptDelegation(othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegatorUsername"),
                DELEGATION_TYPE_DEFAULT, workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING));
        othersDelegationsScreen.acceptDelegation(othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegatorUsername"),
                DELEGATION_TYPE_DEFAULT, workflowTypeList.get(1), comment, DELEGATION_STATUS_PENDING));
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(othersDelegationsScreen
                        .getDelegationIndex(prop.getProperty("uat.DelegatorUsername"), DELEGATION_TYPE_DEFAULT,
                                workflowTypeList.get(0), comment, DELEGATION_STATUS_ACCEPTED), DELEGATION_STATUS_ACCEPTED),
                SCREENSHOT_MSG_FAILED_TO_ACCEPT_DELEGATION);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(othersDelegationsScreen
                        .getDelegationIndex(prop.getProperty("uat.DelegatorUsername"), DELEGATION_TYPE_DEFAULT,
                                workflowTypeList.get(1), comment, DELEGATION_STATUS_ACCEPTED), DELEGATION_STATUS_ACCEPTED),
                SCREENSHOT_MSG_FAILED_TO_ACCEPT_DELEGATION);
        System.out.println("Complete");
    }

    //-------------------------------- CNA ---------------------------------

    @Test(groups = {TEST_GRP_DEFAULT_DELEGATION, TEST_GRP_CNA, TEST_GRP_ACCEPT_DELEGATION})
    public void cnaAcceptDefaultDeskOrCountryDelegation() {
        System.out.println("Method: cnaAcceptDefaultDeskOrCountryDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        List<String> countryList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_CNA);
        countryList.add(DELEGATION_OPTION_ALL);
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationDefaultScreen delegationDefaultScreen = delegationOptionScreen.tapOnDefaultDelegation();
        DelegationPortfolioScreen delegationPortfolioScreen = delegationDefaultScreen.fillInDefaultForm(workflowTypeList,
                DELEGATION_TYPE_DESK_OR_COUNTRY, DELEGATION_OPTION_ALL, DELEGATION_OPTION_FX, DELEGATION_OPTION_FX_CASH,
                DELEGATION_OPTION_FX_ASA, countryList);
        DelegationDefaultCreationScreen delegationDefaultCreationScreen = delegationPortfolioScreen
                .selectPortfolio(true, 0);
        delegationScreen = delegationDefaultCreationScreen.createDefaultDelegation("1",
                prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"),
                DELEGATION_TYPE_DEFAULT, workflowTypeList.get(0), comment), FAILED_MSG_FAILED_TO_CREATE_DEFAULT_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to accept the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegatorUsername"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.acceptDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_ACCEPTED),
                SCREENSHOT_MSG_FAILED_TO_ACCEPT_DELEGATION);
        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_DEFAULT_DELEGATION, TEST_GRP_CNA, TEST_GRP_ACCEPT_DELEGATION})
    public void cnaAcceptDefaultPortfolioDelegation() {
        System.out.println("Method: cnaAcceptDefaultPortfolioDelegation");
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
        DelegationDefaultScreen delegationDefaultScreen = delegationOptionScreen.tapOnDefaultDelegation();
        DelegationPortfolioScreen delegationPortfolioScreen = delegationDefaultScreen.fillInDefaultForm(workflowTypeList,
                DELEGATION_TYPE_PORTFOLIO, DELEGATION_OPTION_ALL, DELEGATION_OPTION_FX, DELEGATION_OPTION_FX_CASH,
                DELEGATION_OPTION_FX_ASA, countryList);
        DelegationDefaultCreationScreen delegationDefaultCreationScreen = delegationPortfolioScreen
                .selectPortfolio(false, count);
        delegationScreen = delegationDefaultCreationScreen.createDefaultDelegation("1",
                prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment), FAILED_MSG_FAILED_TO_CREATE_DEFAULT_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to accept the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegatorUsername"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.acceptDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_ACCEPTED),
                SCREENSHOT_MSG_FAILED_TO_ACCEPT_DELEGATION);
        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_USERS_DELEGATION, TEST_GRP_CNA, TEST_GRP_ACCEPT_DELEGATION})
    public void cnaAcceptUserDelegation() {
        System.out.println("Method: cnaAcceptUserDelegation");
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
        DelegationUsersScreen delegationUsersScreen = delegationOptionScreen.tapOnUsersDelegation();
        DelegationUsersCreationScreen delegationUsersCreationScreen = delegationUsersScreen
                .fillInUsersForm(workflowTypeList, fromDate, toDate, prop.getProperty("uat.DelegateUsername"));
        delegationScreen = delegationUsersCreationScreen.createUsersDelegation(comment);
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
        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_DEFAULT_DELEGATION, TEST_GRP_CNA, TEST_GRP_REJECT_DELEGATION})
    public void cnaRejectDefaultDeskOrCountryDelegation() {
        System.out.println("Method: cnaRejectDefaultDeskOrCountryDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        List<String> countryList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_CNA);
        countryList.add(DELEGATION_OPTION_ALL);
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationDefaultScreen delegationDefaultScreen = delegationOptionScreen.tapOnDefaultDelegation();
        DelegationPortfolioScreen delegationPortfolioScreen = delegationDefaultScreen.fillInDefaultForm(workflowTypeList,
                DELEGATION_TYPE_DESK_OR_COUNTRY, DELEGATION_OPTION_ALL, DELEGATION_OPTION_FX, DELEGATION_OPTION_FX_CASH,
                DELEGATION_OPTION_FX_ASA, countryList);
        DelegationDefaultCreationScreen delegationDefaultCreationScreen = delegationPortfolioScreen
                .selectPortfolio(true, 0);
        delegationScreen = delegationDefaultCreationScreen.createDefaultDelegation("1",
                prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"),
                DELEGATION_TYPE_DEFAULT, workflowTypeList.get(0), comment), FAILED_MSG_FAILED_TO_CREATE_DEFAULT_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to Reject the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegatorUsername"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.rejectDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_REJECTED),
                SCREENSHOT_MSG_FAILED_TO_REJECT_DELEGATION);
        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_DEFAULT_DELEGATION, TEST_GRP_CNA, TEST_GRP_REJECT_DELEGATION})
    public void cnaRejectDefaultPortfolioDelegation() {
        System.out.println("Method: cnaRejectDefaultPortfolioDelegation");
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
        DelegationDefaultScreen delegationDefaultScreen = delegationOptionScreen.tapOnDefaultDelegation();
        DelegationPortfolioScreen delegationPortfolioScreen = delegationDefaultScreen.fillInDefaultForm(workflowTypeList,
                DELEGATION_TYPE_PORTFOLIO, DELEGATION_OPTION_ALL, DELEGATION_OPTION_FX, DELEGATION_OPTION_FX_CASH,
                DELEGATION_OPTION_FX_ASA, countryList);
        DelegationDefaultCreationScreen delegationDefaultCreationScreen = delegationPortfolioScreen
                .selectPortfolio(false, count);
        delegationScreen = delegationDefaultCreationScreen.createDefaultDelegation("1",
                prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment), FAILED_MSG_FAILED_TO_CREATE_DEFAULT_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to Reject the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegatorUsername"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.rejectDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_REJECTED),
                SCREENSHOT_MSG_FAILED_TO_REJECT_DELEGATION);
        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_USERS_DELEGATION, TEST_GRP_CNA, TEST_GRP_REJECT_DELEGATION})
    public void cnaRejectUserDelegation() {
        System.out.println("Method: cnaRejectUserDelegation");
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
        DelegationUsersScreen delegationUsersScreen = delegationOptionScreen.tapOnUsersDelegation();
        DelegationUsersCreationScreen delegationUsersCreationScreen = delegationUsersScreen
                .fillInUsersForm(workflowTypeList, fromDate, toDate, prop.getProperty("uat.DelegateUsername"));
        delegationScreen = delegationUsersCreationScreen.createUsersDelegation(comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"),
                DELEGATION_TYPE_USER, workflowTypeList.get(0), comment), FAILED_MSG_FAILED_TO_CREATE_USERS_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to Reject the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegatorUsername"), DELEGATION_TYPE_USER,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.rejectDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_REJECTED),
                SCREENSHOT_MSG_FAILED_TO_REJECT_DELEGATION);
        System.out.println("Complete");
    }

    //-------------------------------- OMR ---------------------------------

    @Test(groups = {TEST_GRP_DEFAULT_DELEGATION, TEST_GRP_OMR, TEST_GRP_ACCEPT_DELEGATION})
    public void omrAcceptDefaultOMRDeskOrCountryDelegation() {
        System.out.println("Method: omrAcceptDefaultOMRDeskOrCountryDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        List<String> countryList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_OMR);
        countryList.add(DELEGATION_OPTION_ALL);
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationDefaultScreen delegationDefaultScreen = delegationOptionScreen.tapOnDefaultDelegation();
        DelegationPortfolioScreen delegationPortfolioScreen = delegationDefaultScreen.fillInDefaultForm(workflowTypeList,
                DELEGATION_TYPE_DESK_OR_COUNTRY, DELEGATION_OPTION_ALL, DELEGATION_OPTION_FX, DELEGATION_OPTION_FX_CASH,
                DELEGATION_OPTION_FX_ASA, countryList);
        DelegationDefaultCreationScreen delegationDefaultCreationScreen = delegationPortfolioScreen
                .selectPortfolio(true, 0);
        delegationScreen = delegationDefaultCreationScreen.createDefaultDelegation("1",
                prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment), FAILED_MSG_FAILED_TO_CREATE_DEFAULT_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to accept the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegatorUsername"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.acceptDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_ACCEPTED),
                SCREENSHOT_MSG_FAILED_TO_ACCEPT_DELEGATION);
        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_DEFAULT_DELEGATION, TEST_GRP_OMR, TEST_GRP_ACCEPT_DELEGATION})
    public void omrAcceptDefaultPortfolioDelegation() {
        System.out.println("Method: omrAcceptDefaultPortfolioDelegation");
        int count = 3;
        List<String> workflowTypeList = new ArrayList<>();
        List<String> countryList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_OMR);
        countryList.add(DELEGATION_OPTION_ALL);
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationDefaultScreen delegationDefaultScreen = delegationOptionScreen.tapOnDefaultDelegation();
        DelegationPortfolioScreen delegationPortfolioScreen = delegationDefaultScreen.fillInDefaultForm(workflowTypeList,
                DELEGATION_TYPE_PORTFOLIO, DELEGATION_OPTION_ALL, DELEGATION_OPTION_FX, DELEGATION_OPTION_FX_CASH,
                DELEGATION_OPTION_FX_ASA, countryList);
        DelegationDefaultCreationScreen delegationDefaultCreationScreen = delegationPortfolioScreen
                .selectPortfolio(false, count);
        delegationScreen = delegationDefaultCreationScreen.createDefaultDelegation("1",
                prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment), FAILED_MSG_FAILED_TO_CREATE_DEFAULT_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to accept the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegatorUsername"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.acceptDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_ACCEPTED),
                SCREENSHOT_MSG_FAILED_TO_ACCEPT_DELEGATION);
        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_USERS_DELEGATION, TEST_GRP_OMR, TEST_GRP_ACCEPT_DELEGATION})
    public void omrAcceptUserDelegation() {
        System.out.println("Method: omrAcceptUserDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_OMR);
        //The duration of the delegation cannot be for more than 1 year
        DateFormat dateFormat = new SimpleDateFormat("dd, MMMM, yyyy");
        String fromDate = dateFormat.format(new Date());
        String toDate = dateFormat.format(new Date());
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationUsersScreen delegationUsersScreen = delegationOptionScreen.tapOnUsersDelegation();
        DelegationUsersCreationScreen delegationUsersCreationScreen = delegationUsersScreen
                .fillInUsersForm(workflowTypeList, fromDate, toDate, prop.getProperty("uat.DelegateUsername"));
        delegationScreen = delegationUsersCreationScreen.createUsersDelegation(comment);
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
        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_DEFAULT_DELEGATION, TEST_GRP_OMR, TEST_GRP_REJECT_DELEGATION})
    public void omrRejectDefaultOMRDeskOrCountryDelegation() {
        System.out.println("Method: omrRejectDefaultOMRDeskOrCountryDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        List<String> countryList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_OMR);
        countryList.add(DELEGATION_OPTION_ALL);
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationDefaultScreen delegationDefaultScreen = delegationOptionScreen.tapOnDefaultDelegation();
        DelegationPortfolioScreen delegationPortfolioScreen = delegationDefaultScreen.fillInDefaultForm(workflowTypeList,
                DELEGATION_TYPE_DESK_OR_COUNTRY, DELEGATION_OPTION_ALL, DELEGATION_OPTION_FX, DELEGATION_OPTION_FX_CASH,
                DELEGATION_OPTION_FX_ASA, countryList);
        DelegationDefaultCreationScreen delegationDefaultCreationScreen = delegationPortfolioScreen
                .selectPortfolio(true, 0);
        delegationScreen = delegationDefaultCreationScreen.createDefaultDelegation("1",
                prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment), FAILED_MSG_FAILED_TO_CREATE_DEFAULT_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to reject the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegatorUsername"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.rejectDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_REJECTED),
                SCREENSHOT_MSG_FAILED_TO_REJECT_DELEGATION);
        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_DEFAULT_DELEGATION, TEST_GRP_OMR, TEST_GRP_REJECT_DELEGATION})
    public void omrRejectDefaultPortfolioDelegation() {
        System.out.println("Method: omrRejectDefaultPortfolioDelegation");
        int count = 3;
        List<String> workflowTypeList = new ArrayList<>();
        List<String> countryList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_OMR);
        countryList.add(DELEGATION_OPTION_ALL);
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationDefaultScreen delegationDefaultScreen = delegationOptionScreen.tapOnDefaultDelegation();
        DelegationPortfolioScreen delegationPortfolioScreen = delegationDefaultScreen.fillInDefaultForm(workflowTypeList,
                DELEGATION_TYPE_PORTFOLIO, DELEGATION_OPTION_ALL, DELEGATION_OPTION_FX, DELEGATION_OPTION_FX_CASH,
                DELEGATION_OPTION_FX_ASA, countryList);
        DelegationDefaultCreationScreen delegationDefaultCreationScreen = delegationPortfolioScreen
                .selectPortfolio(false, count);
        delegationScreen = delegationDefaultCreationScreen.createDefaultDelegation("1",
                prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment), FAILED_MSG_FAILED_TO_CREATE_DEFAULT_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to reject the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegatorUsername"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.rejectDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_REJECTED),
                SCREENSHOT_MSG_FAILED_TO_REJECT_DELEGATION);
        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_USERS_DELEGATION, TEST_GRP_OMR, TEST_GRP_REJECT_DELEGATION})
    public void omrRejectUserDelegation() {
        System.out.println("Method: omrRejectUserDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_OMR);
        //The duration of the delegation cannot be for more than 1 year
        DateFormat dateFormat = new SimpleDateFormat("dd, MMMM, yyyy");
        String fromDate = dateFormat.format(new Date());
        String toDate = dateFormat.format(new Date());
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationUsersScreen delegationUsersScreen = delegationOptionScreen.tapOnUsersDelegation();
        DelegationUsersCreationScreen delegationUsersCreationScreen = delegationUsersScreen
                .fillInUsersForm(workflowTypeList, fromDate, toDate, prop.getProperty("uat.DelegateUsername"));
        delegationScreen = delegationUsersCreationScreen.createUsersDelegation(comment);
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
        System.out.println("Complete");
    }

    //-------------------------------- PNL ---------------------------------

    @Test(groups = {TEST_GRP_DEFAULT_DELEGATION, TEST_GRP_PNL, TEST_GRP_ACCEPT_DELEGATION})
    public void pnlAcceptDefaultDeskOrCountryDelegation() {
        System.out.println("Method: pnlAcceptDefaultDeskOrCountryDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        List<String> countryList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_PNL);
        countryList.add(DELEGATION_OPTION_ALL);
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationDefaultScreen delegationDefaultScreen = delegationOptionScreen.tapOnDefaultDelegation();
        DelegationPortfolioScreen delegationPortfolioScreen = delegationDefaultScreen.fillInDefaultForm(workflowTypeList,
                DELEGATION_TYPE_DESK_OR_COUNTRY, DELEGATION_OPTION_ALL, DELEGATION_OPTION_FX, DELEGATION_OPTION_FX_CASH,
                DELEGATION_OPTION_FX_ASA, countryList);
        DelegationDefaultCreationScreen delegationDefaultCreationScreen = delegationPortfolioScreen
                .selectPortfolio(true, 0);
        delegationScreen = delegationDefaultCreationScreen.createDefaultDelegation("1",
                prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"),
                DELEGATION_TYPE_DEFAULT, workflowTypeList.get(0), comment), FAILED_MSG_FAILED_TO_CREATE_DEFAULT_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to accept the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegatorUsername"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.acceptDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_ACCEPTED),
                SCREENSHOT_MSG_FAILED_TO_ACCEPT_DELEGATION);
        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_DEFAULT_DELEGATION, TEST_GRP_PNL, TEST_GRP_ACCEPT_DELEGATION})
    public void pnlAcceptDefaultPortfolioDelegation() {
        System.out.println("Method: pnlAcceptDefaultPortfolioDelegation");
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
        DelegationDefaultScreen delegationDefaultScreen = delegationOptionScreen.tapOnDefaultDelegation();
        DelegationPortfolioScreen delegationPortfolioScreen = delegationDefaultScreen.fillInDefaultForm(workflowTypeList,
                DELEGATION_TYPE_REPORT_LABEL, DELEGATION_OPTION_ALL, DELEGATION_OPTION_FX, DELEGATION_OPTION_FX_CASH,
                DELEGATION_OPTION_FX_ASA, countryList);
        DelegationDefaultCreationScreen delegationDefaultCreationScreen = delegationPortfolioScreen
                .selectPortfolio(false, count);
        delegationScreen = delegationDefaultCreationScreen.createDefaultDelegation("1",
                prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment), FAILED_MSG_FAILED_TO_CREATE_DEFAULT_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to accept the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegatorUsername"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.acceptDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_ACCEPTED),
                SCREENSHOT_MSG_FAILED_TO_ACCEPT_DELEGATION);
        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_USERS_DELEGATION, TEST_GRP_PNL, TEST_GRP_ACCEPT_DELEGATION})
    public void pnlAcceptUserDelegation() {
        System.out.println("Method: pnlAcceptUserDelegation");
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
        DelegationUsersScreen delegationUsersScreen = delegationOptionScreen.tapOnUsersDelegation();
        DelegationUsersCreationScreen delegationUsersCreationScreen = delegationUsersScreen
                .fillInUsersForm(workflowTypeList, fromDate, toDate, prop.getProperty("uat.DelegateUsername"));
        delegationScreen = delegationUsersCreationScreen.createUsersDelegation(comment);
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
        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_DEFAULT_DELEGATION, TEST_GRP_PNL, TEST_GRP_REJECT_DELEGATION})
    public void pnlRejectDefaultDeskOrCountryDelegation() {
        System.out.println("Method: pnlRejectDefaultDeskOrCountryDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        List<String> countryList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_PNL);
        countryList.add(DELEGATION_OPTION_ALL);
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationDefaultScreen delegationDefaultScreen = delegationOptionScreen.tapOnDefaultDelegation();
        DelegationPortfolioScreen delegationPortfolioScreen = delegationDefaultScreen.fillInDefaultForm(workflowTypeList,
                DELEGATION_TYPE_DESK_OR_COUNTRY, DELEGATION_OPTION_ALL, DELEGATION_OPTION_FX, DELEGATION_OPTION_FX_CASH,
                DELEGATION_OPTION_FX_ASA, countryList);
        DelegationDefaultCreationScreen delegationDefaultCreationScreen = delegationPortfolioScreen
                .selectPortfolio(true, 0);
        delegationScreen = delegationDefaultCreationScreen.createDefaultDelegation("1",
                prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"),
                DELEGATION_TYPE_DEFAULT, workflowTypeList.get(0), comment), FAILED_MSG_FAILED_TO_CREATE_DEFAULT_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to reject the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegatorUsername"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.rejectDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_REJECTED),
                SCREENSHOT_MSG_FAILED_TO_REJECT_DELEGATION);
        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_DEFAULT_DELEGATION, TEST_GRP_PNL, TEST_GRP_REJECT_DELEGATION})
    public void pnlRejectDefaultPortfolioDelegation() {
        System.out.println("Method: pnlRejectDefaultPortfolioDelegation");
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
        DelegationDefaultScreen delegationDefaultScreen = delegationOptionScreen.tapOnDefaultDelegation();
        DelegationPortfolioScreen delegationPortfolioScreen = delegationDefaultScreen.fillInDefaultForm(workflowTypeList,
                DELEGATION_TYPE_REPORT_LABEL, DELEGATION_OPTION_ALL, DELEGATION_OPTION_FX, DELEGATION_OPTION_FX_CASH,
                DELEGATION_OPTION_FX_ASA, countryList);
        DelegationDefaultCreationScreen delegationDefaultCreationScreen = delegationPortfolioScreen
                .selectPortfolio(false, count);
        delegationScreen = delegationDefaultCreationScreen.createDefaultDelegation("1",
                prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment), FAILED_MSG_FAILED_TO_CREATE_DEFAULT_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to reject the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegatorUsername"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.rejectDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_REJECTED),
                SCREENSHOT_MSG_FAILED_TO_REJECT_DELEGATION);
        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_USERS_DELEGATION, TEST_GRP_PNL, TEST_GRP_REJECT_DELEGATION})
    public void pnlRejectUserDelegation() {
        System.out.println("Method: pnlRejectUserDelegation");
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
        DelegationUsersScreen delegationUsersScreen = delegationOptionScreen.tapOnUsersDelegation();
        DelegationUsersCreationScreen delegationUsersCreationScreen = delegationUsersScreen
                .fillInUsersForm(workflowTypeList, fromDate, toDate, prop.getProperty("uat.DelegateUsername"));
        delegationScreen = delegationUsersCreationScreen.createUsersDelegation(comment);
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
        System.out.println("Complete");
    }

    //-------------------------------- GT/GMR ---------------------------------

    @Test(groups = {TEST_GRP_DEFAULT_DELEGATION, TEST_GRP_GT_GMR, TEST_GRP_ACCEPT_DELEGATION})
    public void gmrAcceptDefaultDeskOrCountryDelegation() {
        System.out.println("Method: gmrAcceptDefaultDeskOrCountryDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        List<String> countryList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_GMR);
        countryList.add(DELEGATION_COUNTRY_JAPAN);
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationDefaultScreen delegationDefaultScreen = delegationOptionScreen.tapOnDefaultDelegation();
        DelegationPortfolioScreen delegationPortfolioScreen = delegationDefaultScreen.fillInDefaultForm(workflowTypeList,
                DELEGATION_TYPE_DESK_OR_COUNTRY, DELEGATION_OPTION_ALL, DELEGATION_OPTION_CMS, DELEGATION_OPTION_ABS_STRUCTURED,
                DELEGATION_OPTION_ABS_STRUCTERED_MTM, countryList);
        DelegationDefaultCreationScreen delegationDefaultCreationScreen = delegationPortfolioScreen
                .selectPortfolio(true, 0);
        delegationScreen = delegationDefaultCreationScreen.createDefaultDelegation("1",
                prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"),
                DELEGATION_TYPE_DEFAULT, workflowTypeList.get(0), comment), FAILED_MSG_FAILED_TO_CREATE_DEFAULT_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to accept the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.FOUsername02"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.acceptDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_ACCEPTED),
                SCREENSHOT_MSG_FAILED_TO_ACCEPT_DELEGATION);
        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_DEFAULT_DELEGATION, TEST_GRP_GT_GMR, TEST_GRP_ACCEPT_DELEGATION})
    public void gmrAcceptDefaultPortfolioDelegation() {
        System.out.println("Method: gmrAcceptDefaultPortfolioDelegation");
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
        DelegationDefaultScreen delegationDefaultScreen = delegationOptionScreen.tapOnDefaultDelegation();
        DelegationPortfolioScreen delegationPortfolioScreen = delegationDefaultScreen.fillInDefaultForm(workflowTypeList,
                DELEGATION_TYPE_REPORT_LABEL, DELEGATION_OPTION_ALL, DELEGATION_OPTION_CMS, DELEGATION_OPTION_ABS_STRUCTURED,
                DELEGATION_OPTION_ABS_STRUCTERED_MTM, countryList);
        DelegationDefaultCreationScreen delegationDefaultCreationScreen = delegationPortfolioScreen
                .selectPortfolio(false, count);
        delegationScreen = delegationDefaultCreationScreen.createDefaultDelegation("1",
                prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"),
                DELEGATION_TYPE_DEFAULT, workflowTypeList.get(0), comment), FAILED_MSG_FAILED_TO_CREATE_DEFAULT_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to accept the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.FOUsername02"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.acceptDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_ACCEPTED),
                SCREENSHOT_MSG_FAILED_TO_ACCEPT_DELEGATION);
        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_USERS_DELEGATION, TEST_GRP_GT_GMR, TEST_GRP_ACCEPT_DELEGATION})
    public void gmrAcceptUserDelegation() {
        System.out.println("Method: gmrAcceptUserDelegation");
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
        DelegationUsersScreen delegationUsersScreen = delegationOptionScreen.tapOnUsersDelegation();
        DelegationUsersCreationScreen delegationUsersCreationScreen = delegationUsersScreen
                .fillInUsersForm(workflowTypeList, fromDate, toDate, prop.getProperty("uat.DelegateUsername"));
        delegationScreen = delegationUsersCreationScreen.createUsersDelegation(comment);
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
        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_DEFAULT_DELEGATION, TEST_GRP_GT_GMR, TEST_GRP_REJECT_DELEGATION})
    public void gmrRejectDefaultDeskOrCountryDelegation() {
        System.out.println("Method: gmrRejectDefaultDeskOrCountryDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        List<String> countryList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_GMR);
        countryList.add(DELEGATION_COUNTRY_JAPAN);
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationDefaultScreen delegationDefaultScreen = delegationOptionScreen.tapOnDefaultDelegation();
        DelegationPortfolioScreen delegationPortfolioScreen = delegationDefaultScreen.fillInDefaultForm(workflowTypeList,
                DELEGATION_TYPE_DESK_OR_COUNTRY, DELEGATION_OPTION_ALL, DELEGATION_OPTION_CMS, DELEGATION_OPTION_ABS_STRUCTURED,
                DELEGATION_OPTION_ABS_STRUCTERED_MTM, countryList);
        DelegationDefaultCreationScreen delegationDefaultCreationScreen = delegationPortfolioScreen
                .selectPortfolio(true, 0);
        delegationScreen = delegationDefaultCreationScreen.createDefaultDelegation("1",
                prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"),
                DELEGATION_TYPE_DEFAULT, workflowTypeList.get(0), comment), FAILED_MSG_FAILED_TO_CREATE_DEFAULT_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to reject the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.FOUsername02"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.rejectDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_REJECTED),
                SCREENSHOT_MSG_FAILED_TO_REJECT_DELEGATION);
        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_DEFAULT_DELEGATION, TEST_GRP_GT_GMR, TEST_GRP_REJECT_DELEGATION})
    public void gmrRejectDefaultPortfolioDelegation() {
        System.out.println("Method: gmrRejectDefaultPortfolioDelegation");
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
        DelegationDefaultScreen delegationDefaultScreen = delegationOptionScreen.tapOnDefaultDelegation();
        DelegationPortfolioScreen delegationPortfolioScreen = delegationDefaultScreen.fillInDefaultForm(workflowTypeList,
                DELEGATION_TYPE_REPORT_LABEL, DELEGATION_OPTION_ALL, DELEGATION_OPTION_CMS, DELEGATION_OPTION_ABS_STRUCTURED,
                DELEGATION_OPTION_ABS_STRUCTERED_MTM, countryList);
        DelegationDefaultCreationScreen delegationDefaultCreationScreen = delegationPortfolioScreen
                .selectPortfolio(false, count);
        delegationScreen = delegationDefaultCreationScreen.createDefaultDelegation("1",
                prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"),
                DELEGATION_TYPE_DEFAULT, workflowTypeList.get(0), comment), FAILED_MSG_FAILED_TO_CREATE_DEFAULT_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to reject the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.FOUsername02"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.rejectDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_REJECTED),
                SCREENSHOT_MSG_FAILED_TO_REJECT_DELEGATION);
        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_USERS_DELEGATION, TEST_GRP_GT_GMR, TEST_GRP_REJECT_DELEGATION})
    public void gmrRejectUserDelegation() {
        System.out.println("Method: gmrRejectUserDelegation");
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
        DelegationUsersScreen delegationUsersScreen = delegationOptionScreen.tapOnUsersDelegation();
        DelegationUsersCreationScreen delegationUsersCreationScreen = delegationUsersScreen
                .fillInUsersForm(workflowTypeList, fromDate, toDate, prop.getProperty("uat.DelegateUsername"));
        delegationScreen = delegationUsersCreationScreen.createUsersDelegation(comment);
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
        System.out.println("Complete");
    }


    //-------------------------------- IPV/FVA ---------------------------------

    @Test(groups = {TEST_GRP_DEFAULT_DELEGATION, TEST_GRP_IPV_FVA, TEST_GRP_ACCEPT_DELEGATION})
    public void fvaAcceptDefaultDeskOrCountryDelegation() {
        System.out.println("Method: fvaAcceptDefaultDeskOrCountryDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        List<String> countryList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_FVA);
        countryList.add(DELEGATION_OPTION_ALL);
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationDefaultScreen delegationDefaultScreen = delegationOptionScreen.tapOnDefaultDelegation();
        DelegationPortfolioScreen delegationPortfolioScreen = delegationDefaultScreen.fillInDefaultForm(workflowTypeList,
                DELEGATION_TYPE_DESK_OR_COUNTRY, DELEGATION_OPTION_ALL, DELEGATION_OPTION_FX, DELEGATION_OPTION_FX_CASH,
                DELEGATION_OPTION_FX_ASA, countryList);
        DelegationDefaultCreationScreen delegationDefaultCreationScreen = delegationPortfolioScreen
                .selectPortfolio(true, 0);
        delegationScreen = delegationDefaultCreationScreen.createDefaultDelegation("1",
                prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"),
                DELEGATION_TYPE_DEFAULT, workflowTypeList.get(0), comment), FAILED_MSG_FAILED_TO_CREATE_DEFAULT_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to accept the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.FOUsername02"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.acceptDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_ACCEPTED),
                SCREENSHOT_MSG_FAILED_TO_ACCEPT_DELEGATION);
        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_DEFAULT_DELEGATION, TEST_GRP_IPV_FVA, TEST_GRP_ACCEPT_DELEGATION})
    public void fvaAcceptDefaultPortfolioDelegation() {
        System.out.println("Method: fvaAcceptDefaultPortfolioDelegation");
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
        DelegationDefaultScreen delegationDefaultScreen = delegationOptionScreen.tapOnDefaultDelegation();
        DelegationPortfolioScreen delegationPortfolioScreen = delegationDefaultScreen.fillInDefaultForm(workflowTypeList,
                DELEGATION_TYPE_REPORT_LABEL, DELEGATION_OPTION_ALL, DELEGATION_OPTION_FX, DELEGATION_OPTION_FX_CASH,
                DELEGATION_OPTION_FX_ASA, countryList);
        DelegationDefaultCreationScreen delegationDefaultCreationScreen = delegationPortfolioScreen
                .selectPortfolio(false, count);
        delegationScreen = delegationDefaultCreationScreen.createDefaultDelegation("1",
                prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment), FAILED_MSG_FAILED_TO_CREATE_DEFAULT_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to accept the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.FOUsername02"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.acceptDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_ACCEPTED),
                SCREENSHOT_MSG_FAILED_TO_ACCEPT_DELEGATION);
        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_USERS_DELEGATION, TEST_GRP_IPV_FVA, TEST_GRP_ACCEPT_DELEGATION})
    public void fvaAcceptUserDelegation() {
        System.out.println("Method: cnaAcceptUserDelegation");
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
        DelegationUsersScreen delegationUsersScreen = delegationOptionScreen.tapOnUsersDelegation();
        DelegationUsersCreationScreen delegationUsersCreationScreen = delegationUsersScreen
                .fillInUsersForm(workflowTypeList, fromDate, toDate, prop.getProperty("uat.DelegateUsername"));
        delegationScreen = delegationUsersCreationScreen.createUsersDelegation(comment);
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
        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_DEFAULT_DELEGATION, TEST_GRP_IPV_FVA, TEST_GRP_REJECT_DELEGATION})
    public void fvaRejectDefaultDeskOrCountryDelegation() {
        System.out.println("Method: fvaRejectDefaultDeskOrCountryDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        List<String> countryList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_FVA);
        countryList.add(DELEGATION_OPTION_ALL);
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationDefaultScreen delegationDefaultScreen = delegationOptionScreen.tapOnDefaultDelegation();
        DelegationPortfolioScreen delegationPortfolioScreen = delegationDefaultScreen.fillInDefaultForm(workflowTypeList,
                DELEGATION_TYPE_DESK_OR_COUNTRY, DELEGATION_OPTION_ALL, DELEGATION_OPTION_FX, DELEGATION_OPTION_FX_CASH,
                DELEGATION_OPTION_FX_ASA, countryList);
        DelegationDefaultCreationScreen delegationDefaultCreationScreen = delegationPortfolioScreen
                .selectPortfolio(true, 0);
        delegationScreen = delegationDefaultCreationScreen.createDefaultDelegation("1",
                prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"),
                DELEGATION_TYPE_DEFAULT, workflowTypeList.get(0), comment), FAILED_MSG_FAILED_TO_CREATE_DEFAULT_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to accept the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.FOUsername02"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.rejectDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_REJECTED),
                SCREENSHOT_MSG_FAILED_TO_REJECT_DELEGATION);
        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_DEFAULT_DELEGATION, TEST_GRP_IPV_FVA, TEST_GRP_REJECT_DELEGATION})
    public void fvaRejectDefaultPortfolioDelegation() {
        System.out.println("Method: fvaRejectDefaultPortfolioDelegation");
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
        DelegationDefaultScreen delegationDefaultScreen = delegationOptionScreen.tapOnDefaultDelegation();
        DelegationPortfolioScreen delegationPortfolioScreen = delegationDefaultScreen.fillInDefaultForm(workflowTypeList,
                DELEGATION_TYPE_REPORT_LABEL, DELEGATION_OPTION_ALL, DELEGATION_OPTION_FX, DELEGATION_OPTION_FX_CASH,
                DELEGATION_OPTION_FX_ASA, countryList);
        DelegationDefaultCreationScreen delegationDefaultCreationScreen = delegationPortfolioScreen
                .selectPortfolio(false, count);
        delegationScreen = delegationDefaultCreationScreen.createDefaultDelegation("1",
                prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment), FAILED_MSG_FAILED_TO_CREATE_DEFAULT_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to accept the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.FOUsername02"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.rejectDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_REJECTED),
                SCREENSHOT_MSG_FAILED_TO_REJECT_DELEGATION);
        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_USERS_DELEGATION, TEST_GRP_IPV_FVA, TEST_GRP_REJECT_DELEGATION})
    public void fvaRejectUserDelegation() {
        System.out.println("Method: fvaRejectUserDelegation");
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
        DelegationUsersScreen delegationUsersScreen = delegationOptionScreen.tapOnUsersDelegation();
        DelegationUsersCreationScreen delegationUsersCreationScreen = delegationUsersScreen
                .fillInUsersForm(workflowTypeList, fromDate, toDate, prop.getProperty("uat.DelegateUsername"));
        delegationScreen = delegationUsersCreationScreen.createUsersDelegation(comment);
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
        System.out.println("Complete");
    }

    //-------------------------------- CE ---------------------------------

    @Test(groups = {TEST_GRP_DEFAULT_DELEGATION, TEST_GRP_CE, TEST_GRP_ACCEPT_DELEGATION})
    public void ceAcceptDefaultDelegation() {
        System.out.println("Method: ceAcceptDefaultDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_CE);
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationDefaultScreen delegationDefaultScreen = delegationOptionScreen.tapOnDefaultDelegation();
        DelegationDefaultCreationScreen delegationDefaultCreationScreen = delegationDefaultScreen
                .fillInDefaultCEForm(workflowTypeList, DELEGATION_TYPE_CE_VE_USER);
        delegationScreen = delegationDefaultCreationScreen
                .createDefaultDelegation("1", prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"),
                DELEGATION_TYPE_DEFAULT, workflowTypeList.get(0), comment),
                FAILED_MSG_FAILED_TO_CREATE_DEFAULT_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to accept the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegatorUsername"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.acceptDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_ACCEPTED),
                SCREENSHOT_MSG_FAILED_TO_ACCEPT_DELEGATION);
        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_USERS_DELEGATION, TEST_GRP_CE, TEST_GRP_ACCEPT_DELEGATION})
    public void ceAcceptUserDelegation() {
        System.out.println("Method: ceAcceptUserDelegation");
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
        DelegationUsersScreen delegationUsersScreen = delegationOptionScreen.tapOnUsersDelegation();
        DelegationUsersCreationScreen delegationUsersCreationScreen = delegationUsersScreen
                .fillInUsersForm(workflowTypeList, fromDate, toDate, prop.getProperty("uat.DelegateUsername"));
        delegationScreen = delegationUsersCreationScreen.createUsersDelegation(comment);
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
        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_DEFAULT_DELEGATION, TEST_GRP_CE, TEST_GRP_REJECT_DELEGATION})
    public void ceRejectDefaultDelegation() {
        System.out.println("Method: ceRejectDefaultDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_CE);
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationDefaultScreen delegationDefaultScreen = delegationOptionScreen.tapOnDefaultDelegation();
        DelegationDefaultCreationScreen delegationDefaultCreationScreen = delegationDefaultScreen
                .fillInDefaultCEForm(workflowTypeList, DELEGATION_TYPE_CE_VE_USER);
        delegationScreen = delegationDefaultCreationScreen
                .createDefaultDelegation("1", prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"),
                DELEGATION_TYPE_DEFAULT, workflowTypeList.get(0), comment),
                FAILED_MSG_FAILED_TO_CREATE_DEFAULT_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to reject the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegatorUsername"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.rejectDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_REJECTED),
                SCREENSHOT_MSG_FAILED_TO_REJECT_DELEGATION);
        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_USERS_DELEGATION, TEST_GRP_CE, TEST_GRP_REJECT_DELEGATION})
    public void ceRejectUserDelegation() {
        System.out.println("Method: ceRejectUserDelegation");
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
        DelegationUsersScreen delegationUsersScreen = delegationOptionScreen.tapOnUsersDelegation();
        DelegationUsersCreationScreen delegationUsersCreationScreen = delegationUsersScreen
                .fillInUsersForm(workflowTypeList, fromDate, toDate, prop.getProperty("uat.DelegateUsername"));
        delegationScreen = delegationUsersCreationScreen.createUsersDelegation(comment);
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
        System.out.println("Complete");
    }

    //-------------------------------- VE ---------------------------------

    @Test(groups = {TEST_GRP_DEFAULT_DELEGATION, TEST_GRP_VE, TEST_GRP_ACCEPT_DELEGATION})
    public void veAcceptDefaultDelegation() {
        System.out.println("Method: veAcceptDefaultDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_VE);
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationDefaultScreen delegationDefaultScreen = delegationOptionScreen.tapOnDefaultDelegation();
        DelegationDefaultCreationScreen delegationDefaultCreationScreen = delegationDefaultScreen
                .fillInDefaultCEForm(workflowTypeList, DELEGATION_TYPE_CE_VE_USER);
        delegationScreen = delegationDefaultCreationScreen
                .createDefaultDelegation("1", prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"),
                DELEGATION_TYPE_DEFAULT, workflowTypeList.get(0), comment),
                FAILED_MSG_FAILED_TO_CREATE_DEFAULT_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to accept the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegatorUsername"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.acceptDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_ACCEPTED),
                SCREENSHOT_MSG_FAILED_TO_ACCEPT_DELEGATION);
        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_USERS_DELEGATION, TEST_GRP_VE, TEST_GRP_ACCEPT_DELEGATION})
    public void veAcceptUserDelegation() {
        System.out.println("Method: veAcceptUserDelegation");
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
        DelegationUsersScreen delegationUsersScreen = delegationOptionScreen.tapOnUsersDelegation();
        DelegationUsersCreationScreen delegationUsersCreationScreen = delegationUsersScreen
                .fillInUsersForm(workflowTypeList, fromDate, toDate, prop.getProperty("uat.DelegateUsername"));
        delegationScreen = delegationUsersCreationScreen.createUsersDelegation(comment);
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
        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_DEFAULT_DELEGATION, TEST_GRP_VE, TEST_GRP_REJECT_DELEGATION})
    public void veRejectDefaultDelegation() {
        System.out.println("Method: veRejectDefaultDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_VE);
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationDefaultScreen delegationDefaultScreen = delegationOptionScreen.tapOnDefaultDelegation();
        DelegationDefaultCreationScreen delegationDefaultCreationScreen = delegationDefaultScreen
                .fillInDefaultCEForm(workflowTypeList, DELEGATION_TYPE_CE_VE_USER);
        delegationScreen = delegationDefaultCreationScreen
                .createDefaultDelegation("1", prop.getProperty("uat.DelegateUsername"), comment);
        Assert.assertTrue(delegationScreen.verifyDelegation(prop.getProperty("uat.DelegateUsername"),
                DELEGATION_TYPE_DEFAULT, workflowTypeList.get(0), comment),
                FAILED_MSG_FAILED_TO_CREATE_DEFAULT_DELEGATION);
        delegationScreen.logout();

        //-----------------------login as the Delegatee to reject the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegatorUsername"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.rejectDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_REJECTED),
                SCREENSHOT_MSG_FAILED_TO_REJECT_DELEGATION);
        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_USERS_DELEGATION, TEST_GRP_VE, TEST_GRP_REJECT_DELEGATION},
            dependsOnMethods = {"veRejectDefaultDelegation"})
    public void veRejectUserDelegation() {
        System.out.println("Method: veRejectUserDelegation");
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
        DelegationUsersScreen delegationUsersScreen = delegationOptionScreen.tapOnUsersDelegation();
        DelegationUsersCreationScreen delegationUsersCreationScreen = delegationUsersScreen
                .fillInUsersForm(workflowTypeList, fromDate, toDate, prop.getProperty("uat.DelegateUsername"));
        delegationScreen = delegationUsersCreationScreen.createUsersDelegation(comment);
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
        System.out.println("Complete");
    }
}
