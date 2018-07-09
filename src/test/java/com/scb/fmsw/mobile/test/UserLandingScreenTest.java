package com.scb.fmsw.mobile.test;

import com.scb.fmsw.mobile.base.BaseTest;
import com.scb.fmsw.mobile.screen.LandingScreen;
import com.scb.fmsw.mobile.screen.LoginScreen;
import com.scb.fmsw.mobile.screen.OverviewScreen;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class UserLandingScreenTest extends BaseTest {

    String[] allUserID = {"1403400", "1413445", "1415362", "1453406", "1549537", "1517948", "1011665", "1200563", "1535505", "1562523", "1332508", "1018993", "1508378", "1547319", "1570044", "1520425", "1568445", "1276823", "1471919", "1521467", "1561148", "1185488", "1560755", "1321250", "1458778", "1344793", "1355840", "1001179", "1002184", "1399585", "1431837", "1227618", "1418832", "1001409"};
    String[] test = {"1227618"};

    @Test
    public void userLandingScreenTest() throws InterruptedException{
        for (int i = 0; i < allUserID.length; i++) {
            LoginScreen loginScreen = new LoginScreen(iosDriver);
            LandingScreen landingScreen;
            OverviewScreen overviewScreen;
            landingScreen = loginScreen.login(allUserID[i]);
            try {
                iosDriver.findElementById("cross");
                System.out.println("Login attempt failed for " + allUserID[i]);
                screenshot("Login attempt failed for " + allUserID[i]);
                loginScreen.clearUsername();
                System.out.println();
            } catch (Exception e) {
                Thread.sleep(3000);
                hasLoadingCompleted();
                System.out.println("Login attempt for " + allUserID[i]);
                screenshot("Login attempt for " + allUserID[i]);
                overviewScreen = landingScreen.overviewScreenNavigation(allUserID[i]);
                overviewScreen.logout();
            }
        }
    }

    @Test
    public void userLandingScreenThroughBSTest() throws InterruptedException{
        for (int i = 0; i < allUserID.length; i++) {
            LoginScreen loginScreen = new LoginScreen(iosDriver);
            LandingScreen landingScreen;
            OverviewScreen overviewScreen;
            landingScreen = loginScreen.login(allUserID[i]);
            try {
                overviewScreen = landingScreen.overviewScreenNavigation(allUserID[i]);
                hasLoadingCompleted();
                System.out.println("Login attempt for " + allUserID[i]);
                overviewScreen.logout();
            } catch (Exception e) {
                Thread.sleep(3000);
                System.out.println("Login attempt failed for " + allUserID[i]);
                screenshot("Login attempt failed for " + allUserID[i]);
                System.out.println();
            }
        }
    }

    /**
     * This method will take a screenshot and save in local machine
     * @param filename name of the screenshot
     */
    protected void screenshot(String filename) {
        File scrFile = iosDriver.getScreenshotAs(OutputType.FILE);
        System.out.println("Screenshot completed");
        filename = (new Date() + " " + filename).replace(" ", "_").replace(":", "_").replace("/", "_");
        String userDir = System.getProperty("user.dir");
        try {
            File testScreenShot = new File(userDir + File.separator + "test-output" + File.separator + "screenshots" + File.separator + filename + ".png");
            // Copy the file to screenshot folder
            FileUtils.copyFile(scrFile, testScreenShot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void hasLoadingCompleted() {
        try {
            new WebDriverWait(iosDriver, 30).until(ExpectedConditions.invisibilityOf(iosDriver.findElementByName("Loading...")));
        } catch (Exception e) {
            // Do nothing
        }
    }
}
