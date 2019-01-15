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

    //Note: For manual workflow, in order to create delegation, the user has to be the acknowledger
    //create test method for CNA, TRR, PNL, GT/GMR, IPV/FVA, VE, CE

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
    public void cnaAcceptAndDeleteAndDeleteDefaultDeskOrCountryDelegation() {
        System.out.println("Method: cnaAcceptAndDeleteAndDeleteDefaultDeskOrCountryDelegation");
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
        index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegatorUsername"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.acceptDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_ACCEPTED),
                SCREENSHOT_MSG_FAILED_TO_ACCEPT_DELEGATION);
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_ACCEPTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_DEFAULT_DELEGATION, TEST_GRP_CNA, TEST_GRP_ACCEPT_DELEGATION})
    public void cnaAcceptAndDeleteAndDeleteDefaultPortfolioDelegation() {
        System.out.println("Method: cnaAcceptAndDeleteAndDeleteDefaultPortfolioDelegation");
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
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_ACCEPTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_USERS_DELEGATION, TEST_GRP_CNA, TEST_GRP_ACCEPT_DELEGATION})
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

    @Test(groups = {TEST_GRP_DEFAULT_DELEGATION, TEST_GRP_CNA, TEST_GRP_REJECT_DELEGATION})
    public void cnaRejectAndDeleteDefaultDeskOrCountryDelegation() {
        System.out.println("Method: cnaRejectAndDeleteDefaultDeskOrCountryDelegation");
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

        //-----------------------login as the Delegatee to RejectAndDelete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegatorUsername"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.rejectDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_REJECTED),
                SCREENSHOT_MSG_FAILED_TO_REJECT_DELEGATION);
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_REJECTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_DEFAULT_DELEGATION, TEST_GRP_CNA, TEST_GRP_REJECT_DELEGATION})
    public void cnaRejectAndDeleteDefaultPortfolioDelegation() {
        System.out.println("Method: cnaRejectAndDeleteDefaultPortfolioDelegation");
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

        //-----------------------login as the Delegatee to RejectAndDelete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegateUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        OthersDelegationsScreen othersDelegationsScreen = delegationScreen.navigateToOthersDelegation();
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegatorUsername"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.rejectDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_REJECTED),
                SCREENSHOT_MSG_FAILED_TO_REJECT_DELEGATION);
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_REJECTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_USERS_DELEGATION, TEST_GRP_CNA, TEST_GRP_REJECT_DELEGATION})
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
        DelegationUsersScreen delegationUsersScreen = delegationOptionScreen.tapOnUsersDelegation();
        DelegationUsersCreationScreen delegationUsersCreationScreen = delegationUsersScreen
                .fillInUsersForm(workflowTypeList, fromDate, toDate, prop.getProperty("uat.DelegateUsername"));
        delegationScreen = delegationUsersCreationScreen.createUsersDelegation(comment);
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

    @Test(groups = {TEST_GRP_DEFAULT_DELEGATION, TEST_GRP_TRR, TEST_GRP_ACCEPT_DELEGATION})
    public void TRRAcceptAndDeleteAndDeleteDefaultTRRDeskOrCountryDelegation() {
        System.out.println("Method: trrAcceptAndDeleteAndDeleteDefaultTRRDeskOrCountryDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        List<String> countryList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_TRR);
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
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_ACCEPTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_DEFAULT_DELEGATION, TEST_GRP_TRR, TEST_GRP_ACCEPT_DELEGATION})
    public void trrAcceptAndDeleteAndDeleteDefaultPortfolioDelegation() {
        System.out.println("Method: trrAcceptAndDeleteAndDeleteDefaultPortfolioDelegation");
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
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_ACCEPTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_USERS_DELEGATION, TEST_GRP_TRR, TEST_GRP_ACCEPT_DELEGATION})
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

    @Test(groups = {TEST_GRP_DEFAULT_DELEGATION, TEST_GRP_TRR, TEST_GRP_REJECT_DELEGATION})
    public void trrRejectAndDeleteDefaultTRRDeskOrCountryDelegation() {
        System.out.println("Method: trrRejectAndDeleteDefaultTRRDeskOrCountryDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        List<String> countryList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_TRR);
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
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_REJECTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_DEFAULT_DELEGATION, TEST_GRP_TRR, TEST_GRP_REJECT_DELEGATION})
    public void trrRejectAndDeleteDefaultPortfolioDelegation() {
        System.out.println("Method: trrRejectAndDeleteDefaultPortfolioDelegation");
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
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_REJECTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_USERS_DELEGATION, TEST_GRP_TRR, TEST_GRP_REJECT_DELEGATION})
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

    @Test(groups = {TEST_GRP_DEFAULT_DELEGATION, TEST_GRP_PNL, TEST_GRP_ACCEPT_DELEGATION})
    public void pnlAcceptAndDeleteAndDeleteDefaultDeskOrCountryDelegation() {
        System.out.println("Method: pnlAcceptAndDeleteDefaultDeskOrCountryDelegation");
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
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_ACCEPTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_DEFAULT_DELEGATION, TEST_GRP_PNL, TEST_GRP_ACCEPT_DELEGATION})
    public void pnlAcceptAndDeleteDefaultPortfolioDelegation() {
        System.out.println("Method: pnlAcceptAndDeleteDefaultPortfolioDelegation");
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
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_ACCEPTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_USERS_DELEGATION, TEST_GRP_PNL, TEST_GRP_ACCEPT_DELEGATION})
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

    @Test(groups = {TEST_GRP_DEFAULT_DELEGATION, TEST_GRP_PNL, TEST_GRP_REJECT_DELEGATION})
    public void pnlRejectAndDeleteDefaultDeskOrCountryDelegation() {
        System.out.println("Method: pnlRejectAndDeleteDefaultDeskOrCountryDelegation");
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
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_REJECTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_DEFAULT_DELEGATION, TEST_GRP_PNL, TEST_GRP_REJECT_DELEGATION})
    public void pnlRejectAndDeleteDefaultPortfolioDelegation() {
        System.out.println("Method: pnlRejectAndDeleteDefaultPortfolioDelegation");
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
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_REJECTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_USERS_DELEGATION, TEST_GRP_PNL, TEST_GRP_REJECT_DELEGATION})
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

    @Test(groups = {TEST_GRP_DEFAULT_DELEGATION, TEST_GRP_GT_GMR, TEST_GRP_ACCEPT_DELEGATION})
    public void gmrAcceptAndDeleteDefaultDeskOrCountryDelegation() {
        System.out.println("Method: gmrAcceptAndDeleteDefaultDeskOrCountryDelegation");
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
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_ACCEPTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_DEFAULT_DELEGATION, TEST_GRP_GT_GMR, TEST_GRP_ACCEPT_DELEGATION})
    public void gmrAcceptAndDeleteDefaultPortfolioDelegation() {
        System.out.println("Method: gmrAcceptAndDeleteDefaultPortfolioDelegation");
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
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_ACCEPTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_USERS_DELEGATION, TEST_GRP_GT_GMR, TEST_GRP_ACCEPT_DELEGATION})
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

    @Test(groups = {TEST_GRP_DEFAULT_DELEGATION, TEST_GRP_GT_GMR, TEST_GRP_REJECT_DELEGATION})
    public void gmrRejectAndDeleteDefaultDeskOrCountryDelegation() {
        System.out.println("Method: gmrRejectAndDeleteDefaultDeskOrCountryDelegation");
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
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_REJECTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_DEFAULT_DELEGATION, TEST_GRP_GT_GMR, TEST_GRP_REJECT_DELEGATION})
    public void gmrRejectAndDeleteDefaultPortfolioDelegation() {
        System.out.println("Method: gmrRejectAndDeleteDefaultPortfolioDelegation");
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
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_REJECTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_USERS_DELEGATION, TEST_GRP_GT_GMR, TEST_GRP_REJECT_DELEGATION})
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

    @Test(groups = {TEST_GRP_DEFAULT_DELEGATION, TEST_GRP_IPV_FVA, TEST_GRP_ACCEPT_DELEGATION})
    public void fvaAcceptAndDeleteDefaultDeskOrCountryDelegation() {
        System.out.println("Method: fvaAcceptAndDeleteDefaultDeskOrCountryDelegation");
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
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_ACCEPTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_DEFAULT_DELEGATION, TEST_GRP_IPV_FVA, TEST_GRP_ACCEPT_DELEGATION})
    public void fvaAcceptAndDeleteDefaultPortfolioDelegation() {
        System.out.println("Method: fvaAcceptAndDeleteDefaultPortfolioDelegation");
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
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_ACCEPTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_USERS_DELEGATION, TEST_GRP_IPV_FVA, TEST_GRP_ACCEPT_DELEGATION})
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

    @Test(groups = {TEST_GRP_DEFAULT_DELEGATION, TEST_GRP_IPV_FVA, TEST_GRP_REJECT_DELEGATION})
    public void fvaRejectAndDeleteDefaultDeskOrCountryDelegation() {
        System.out.println("Method: fvaRejectAndDeleteDefaultDeskOrCountryDelegation");
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
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_REJECTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_DEFAULT_DELEGATION, TEST_GRP_IPV_FVA, TEST_GRP_REJECT_DELEGATION})
    public void fvaRejectAndDeleteDefaultPortfolioDelegation() {
        System.out.println("Method: fvaRejectAndDeleteDefaultPortfolioDelegation");
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
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.FOUsername02"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_REJECTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_USERS_DELEGATION, TEST_GRP_IPV_FVA, TEST_GRP_REJECT_DELEGATION})
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

    @Test(groups = {TEST_GRP_DEFAULT_DELEGATION, TEST_GRP_CE, TEST_GRP_ACCEPT_DELEGATION})
    public void ceAcceptAndDeleteDefaultDelegation() {
        System.out.println("Method: ceAcceptAndDeleteDefaultDelegation");
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
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_ACCEPTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_USERS_DELEGATION, TEST_GRP_CE, TEST_GRP_ACCEPT_DELEGATION})
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

    @Test(groups = {TEST_GRP_DEFAULT_DELEGATION, TEST_GRP_CE, TEST_GRP_REJECT_DELEGATION})
    public void ceRejectAndDeleteDefaultDelegation() {
        System.out.println("Method: ceRejectAndDeleteDefaultDelegation");
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
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_REJECTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_USERS_DELEGATION, TEST_GRP_CE, TEST_GRP_REJECT_DELEGATION})
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

    @Test(groups = {TEST_GRP_DEFAULT_DELEGATION, TEST_GRP_VE, TEST_GRP_ACCEPT_DELEGATION})
    public void veAcceptAndDeleteDefaultDelegation() {
        System.out.println("Method: veAcceptAndDeleteDefaultDelegation");
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
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_ACCEPTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_USERS_DELEGATION, TEST_GRP_VE, TEST_GRP_ACCEPT_DELEGATION})
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

    @Test(groups = {TEST_GRP_DEFAULT_DELEGATION, TEST_GRP_VE, TEST_GRP_REJECT_DELEGATION})
    public void veRejectAndDeleteDefaultDelegation() {
        System.out.println("Method: veRejectAndDeleteDefaultDelegation");
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
        othersDelegationsScreen.logout();

        //-----------------------login as the Delegator to delete the delegation------------------

        overviewScreen = login(prop.getProperty("uat.DelegatorUsername"));
        overviewScreen.tapOnMenuButton();
        delegationScreen = overviewScreen.navigationToDelegationScreen();
        index = delegationScreen.getDelegationIndex(prop.getProperty("uat.DelegateUsername"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_REJECTED);
        delegationScreen.deleteDelegation(index);

        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_USERS_DELEGATION, TEST_GRP_VE, TEST_GRP_REJECT_DELEGATION},
            dependsOnMethods = {"veRejectAndDeleteDefaultDelegation"})
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
