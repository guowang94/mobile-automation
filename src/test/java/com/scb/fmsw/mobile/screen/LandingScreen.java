package com.scb.fmsw.mobile.screen;

import com.scb.fmsw.mobile.base.BaseScreen;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LandingScreen extends BaseScreen {

    private PageObjects landingScreen;

    public LandingScreen(IOSDriver<IOSElement> testDriver) {
        iosDriver = testDriver;
        landingScreen = new PageObjects();
        PageFactory.initElements(iosDriver, landingScreen);
    }

    /**
     * This method will navigate to Overview screen
     *
     * @param userID Business Dashboard user will login as this user
     * @return OverviewScreen
     */
    public OverviewScreen overviewScreenNavigation(String userID) {
        hasLoadingCompleted();
        try {
            if ("Y".equals(prop.getProperty("isBusinessDashboardUser"))) {
                BusinessProcessScreen businessProcessScreen = businessProcessScreenNavigation();
                if (businessProcessScreen != null) {
                    businessProcessScreen.searchForUser(userID);
                }
            }
            landingScreen.workflowButton.click();
            System.out.println("Navigate to Overview Screen");
        } catch (Exception e) {
            System.out.println("Single Role User");
        }
        return new OverviewScreen(iosDriver);
    }

    /**
     * This method will navigate to Business Process screen
     */
    private BusinessProcessScreen businessProcessScreenNavigation() {
        try {
            landingScreen.businessProcessButton.click();
            System.out.println("Navigate to Business Support Screen");
            return new BusinessProcessScreen(iosDriver);
        } catch (Exception e) {
            System.out.println("User does not have Business Dashboard Support View");
        }
        return null;
    }

    /**
     * This method will navigate to Dashboard screen
     *
     * @return DashboardScreen
     */
    public DashboardScreen dashboardScreenNavigation() {
        hasLoadingCompleted();
        try {
            landingScreen.supervisoryDashboardButton.click();
            System.out.println("Navigate to Dashboard Screen");

        } catch (Exception e) {
            System.out.println("Single Role User");
        }
        return new DashboardScreen(iosDriver);
    }

    private class PageObjects {
        @FindBy(id = "Dashboard")
        WebElement supervisoryDashboardButton;

        @FindBy(id = "Workflow")
        WebElement workflowButton;

        @FindBy(id = "Dashboard Viewer")
        WebElement businessProcessButton;
    }

}
