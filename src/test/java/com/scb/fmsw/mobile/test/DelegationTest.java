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
    //create test method for CNA, OMR, PNL, GT/GMR, IPV/FVA

    //-------------------------------- MULTIPLE WORKFLOW TYPE ---------------------------------

    @Test(groups = {TEST_GRP_DEFAULT_DELEGATION , TEST_GRP_MULTIPLE_WORKFLOW})
    public void defaultMultipleWorkflowTypeDelegation() {
        System.out.println("Method: defaultMultipleWorkflowTypeDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        List<String> countryList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_CNA);
        workflowTypeList.add(WORKFLOW_PNL);
        countryList.add(DELEGATION_OPTION_ALL);
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegationUsername"));
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
        othersDelegationsScreen.acceptDelegation(othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegationUsername"),
                DELEGATION_TYPE_DEFAULT, workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING));
        othersDelegationsScreen.acceptDelegation(othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegationUsername"),
                DELEGATION_TYPE_DEFAULT, workflowTypeList.get(1), comment, DELEGATION_STATUS_PENDING));
         Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(othersDelegationsScreen
                         .getDelegationIndex(prop.getProperty("uat.DelegationUsername"), DELEGATION_TYPE_DEFAULT,
                 workflowTypeList.get(0), comment, DELEGATION_STATUS_ACCEPTED), DELEGATION_STATUS_ACCEPTED),
                SCREENSHOT_MSG_FAILED_TO_ACCEPT_DELEGATION);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(othersDelegationsScreen
                        .getDelegationIndex(prop.getProperty("uat.DelegationUsername"), DELEGATION_TYPE_DEFAULT,
                                workflowTypeList.get(1), comment, DELEGATION_STATUS_ACCEPTED), DELEGATION_STATUS_ACCEPTED),
                SCREENSHOT_MSG_FAILED_TO_ACCEPT_DELEGATION);
        System.out.println("Complete");
    }

    //-------------------------------- CNA ---------------------------------

    @Test(groups = {TEST_GRP_DEFAULT_DELEGATION , TEST_GRP_CNA})
    public void cnaDefaultDeskOrCountryDelegation() {
        System.out.println("Method: cnaDefaultDeskOrCountryDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        List<String> countryList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_CNA);
        countryList.add(DELEGATION_OPTION_ALL);
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegationUsername"));
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
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegationUsername"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.acceptDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_ACCEPTED),
                SCREENSHOT_MSG_FAILED_TO_ACCEPT_DELEGATION);
        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_DEFAULT_DELEGATION , TEST_GRP_CNA})
    public void cnaDefaultPortfolioDelegation() {
        System.out.println("Method: cnaDefaultPortfolioDelegation");
        int count = 3;
        List<String> workflowTypeList = new ArrayList<>();
        List<String> countryList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_CNA);
        countryList.add(DELEGATION_OPTION_ALL);
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegationUsername"));
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
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegationUsername"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.acceptDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_ACCEPTED),
                SCREENSHOT_MSG_FAILED_TO_ACCEPT_DELEGATION);
        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_USERS_DELEGATION, TEST_GRP_CNA})
    public void cnaUserDelegation() {
        System.out.println("Method: cnaUserDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_CNA);
        //The duration of the delegation cannot be for more than 1 year
        DateFormat dateFormat = new SimpleDateFormat("dd, MMMM, yyyy");
        String fromDate = dateFormat.format(new Date());
        String toDate = dateFormat.format(new Date());
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegationUsername"));
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
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegationUsername"), DELEGATION_TYPE_USER,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.acceptDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_ACCEPTED),
                SCREENSHOT_MSG_FAILED_TO_ACCEPT_DELEGATION);
        System.out.println("Complete");
    }

    //-------------------------------- OMR ---------------------------------

    @Test(groups = {TEST_GRP_DEFAULT_DELEGATION , TEST_GRP_OMR})
    public void omrDefaultOMRDeskOrCountryDelegation() {
        System.out.println("Method: omrDefaultOMRDeskOrCountryDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        List<String> countryList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_OMR);
        countryList.add(DELEGATION_OPTION_ALL);
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegationUsername"));
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
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegationUsername"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.acceptDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_ACCEPTED),
                SCREENSHOT_MSG_FAILED_TO_ACCEPT_DELEGATION);
        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_DEFAULT_DELEGATION , TEST_GRP_OMR})
    public void omrDefaultPortfolioDelegation() {
        System.out.println("Method: omrDefaultPortfolioDelegation");
        int count = 3;
        List<String> workflowTypeList = new ArrayList<>();
        List<String> countryList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_OMR);
        countryList.add(DELEGATION_OPTION_ALL);
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegationUsername"));
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
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegationUsername"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.acceptDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_ACCEPTED),
                SCREENSHOT_MSG_FAILED_TO_ACCEPT_DELEGATION);
        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_USERS_DELEGATION, TEST_GRP_OMR})
    public void omrUserDelegation() {
        System.out.println("Method: omrUserDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_OMR);
        //The duration of the delegation cannot be for more than 1 year
        DateFormat dateFormat = new SimpleDateFormat("dd, MMMM, yyyy");
        String fromDate = dateFormat.format(new Date());
        String toDate = dateFormat.format(new Date());
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegationUsername"));
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
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegationUsername"), DELEGATION_TYPE_USER,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.acceptDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_ACCEPTED),
                SCREENSHOT_MSG_FAILED_TO_ACCEPT_DELEGATION);
        System.out.println("Complete");
    }

    //-------------------------------- PNL ---------------------------------

    @Test(groups = {TEST_GRP_DEFAULT_DELEGATION , TEST_GRP_PNL})
    public void pnlDefaultDeskOrCountryDelegation() {
        System.out.println("Method: pnlDefaultDeskOrCountryDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        List<String> countryList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_PNL);
        countryList.add(DELEGATION_OPTION_ALL);
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegationUsername"));
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
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegationUsername"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.acceptDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_ACCEPTED),
                SCREENSHOT_MSG_FAILED_TO_ACCEPT_DELEGATION);
        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_DEFAULT_DELEGATION , TEST_GRP_PNL})
    public void pnlDefaultPortfolioDelegation() {
        System.out.println("Method: pnlDefaultPortfolioDelegation");
        int count = 3;
        List<String> workflowTypeList = new ArrayList<>();
        List<String> countryList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_PNL);
        countryList.add(DELEGATION_OPTION_ALL);
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegationUsername"));
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
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegationUsername"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.acceptDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_ACCEPTED),
                SCREENSHOT_MSG_FAILED_TO_ACCEPT_DELEGATION);
        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_USERS_DELEGATION, TEST_GRP_PNL})
    public void pnlUserDelegation() {
        System.out.println("Method: pnlUserDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_PNL);
        //The duration of the delegation cannot be for more than 1 year
        DateFormat dateFormat = new SimpleDateFormat("dd, MMMM, yyyy");
        String fromDate = dateFormat.format(new Date());
        String toDate = dateFormat.format(new Date());
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegationUsername"));
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
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegationUsername"), DELEGATION_TYPE_USER,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.acceptDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_ACCEPTED),
                SCREENSHOT_MSG_FAILED_TO_ACCEPT_DELEGATION);
        System.out.println("Complete");
    }

    //-------------------------------- CE ---------------------------------

    @Test(groups = {TEST_GRP_DEFAULT_DELEGATION , TEST_GRP_CE})
    public void ceDefaultDelegation() {
        System.out.println("Method: ceDefaultDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_CE);
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegationUsername"));
        overviewScreen.tapOnMenuButton();
        DelegationScreen delegationScreen = overviewScreen.navigationToDelegationScreen();
        DelegationOptionScreen delegationOptionScreen = delegationScreen.tapOnCreateDelegation();
        DelegationDefaultScreen delegationDefaultScreen = delegationOptionScreen.tapOnDefaultDelegation();
        DelegationDefaultCreationScreen delegationDefaultCreationScreen = delegationDefaultScreen
                .fillInDefaultCEForm(workflowTypeList, DELEGATION_TYPE_CE_USER);
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
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegationUsername"), DELEGATION_TYPE_DEFAULT,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.acceptDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_ACCEPTED),
                SCREENSHOT_MSG_FAILED_TO_ACCEPT_DELEGATION);
        System.out.println("Complete");
    }

    @Test(groups = {TEST_GRP_USERS_DELEGATION, TEST_GRP_CE})
    public void ceUserDelegation() {
        System.out.println("Method: ceUserDelegation");
        List<String> workflowTypeList = new ArrayList<>();
        workflowTypeList.add(WORKFLOW_CE);
        //The duration of the delegation cannot be for more than 1 year
        DateFormat dateFormat = new SimpleDateFormat("dd, MMMM, yyyy");
        String fromDate = dateFormat.format(new Date());
        String toDate = dateFormat.format(new Date());
        String comment = MSG_ENTER_COMMENT + ". " + String.valueOf(new Date());

        OverviewScreen overviewScreen = login(prop.getProperty("uat.DelegationUsername"));
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
        int index = othersDelegationsScreen.getDelegationIndex(prop.getProperty("uat.DelegationUsername"), DELEGATION_TYPE_USER,
                workflowTypeList.get(0), comment, DELEGATION_STATUS_PENDING);
        othersDelegationsScreen.acceptDelegation(index);
        Assert.assertTrue(othersDelegationsScreen.verifyDelegationStatus(index, DELEGATION_STATUS_ACCEPTED),
                SCREENSHOT_MSG_FAILED_TO_ACCEPT_DELEGATION);
        System.out.println("Complete");
    }
}
