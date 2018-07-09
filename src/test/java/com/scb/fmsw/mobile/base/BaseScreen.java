package com.scb.fmsw.mobile.base;

import com.scb.fmsw.mobile.WorkflowConstants;
import com.scb.fmsw.mobile.screen.InboxScreen;
import com.scb.fmsw.mobile.screen.LoginScreen;
import com.scb.fmsw.mobile.screen.DelegationScreen;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class BaseScreen implements WorkflowConstants {

    protected IOSDriver<IOSElement> iosDriver = null;
    protected Properties prop;

    public BaseScreen() {
        prop = getProperties();
    }

    //xpath
    private String inboxSpecificButton = "//XCUIElementTypeStaticText[@name=\"$1\"]";

    //accessibility id
    private String menuButton = "MenuIcon";
    private String sideNavHelp = "Help";
    private String logout = "Logout";
    private String starhubText = "StarHub network";
    private String delegation = "Delegation";
    private String moreOption = "more option";

    /**
     * This method will return properties
     *
     * @return Properties
     */
    public Properties getProperties() {
        InputStream input;
        prop = new Properties();
        try {
            input = new FileInputStream("config.properties");
            prop.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prop;
    }

    /**
     * This method will take a screenshot and save in local machine
     *
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

    /**
     * This method will logout of the application
     *
     * @return LoginScreen
     */
    public LoginScreen logout() {
        hasLoadingCompleted();
        tapOnMenuButton();
        waitForElementById(logout).click();
        System.out.println("Logout of the Application\n");
        return new LoginScreen(iosDriver);
    }

    /**
     * This method will tap on Menu button
     */
    public void tapOnMenuButton() {
        hasLoadingCompleted();
        waitForElementById(menuButton).click();
    }

    /**
     * This method will tap on More Option button
     */
    public void tapOnMoreOptionButton() {
        hasLoadingCompleted();
        waitForElementById(moreOption).click();
    }

    /**
     * This method will tap on Help button in the side navigation
     */
    public void tapOnSideNavHelp() {
        waitForElementById(sideNavHelp).click();
    }

    /**
     * This method will navigate to specific inbox from side navigation
     *
     * @param inboxName name of the inbox
     * @return InboxScreen
     */
    public InboxScreen navigateToSpecificInboxFromSideNav(String inboxName) {
        waitForElementByXpath(inboxSpecificButton.replace("$1", inboxName)).click();
        return new InboxScreen(iosDriver);
    }

    /**
     * This method will navigate to delegation screen from side navigation
     *
     * @return DelegationScreen
     */
    public DelegationScreen navigationToDelegationScreen() {
        scrollToElement(waitForElementById(delegation)).click();
        System.out.println("Navigate to Delegation Screen");
        return new DelegationScreen(iosDriver);
    }

    /**
     * This method will scroll down until element is displayed
     *
     * @param element element
     */
    public WebElement scrollDownUntilElementIsDisplayed(WebElement element) {
        TouchAction action = new TouchAction(iosDriver);
        while (!element.isDisplayed()) {
            action.press(180, 580).moveTo(0, -400).release().perform();
            try {
				waitForElementUntilClickable(element, 15);
            } catch (Exception e) {
                //Do nothing
            }
        }
        return element;
    }

    /**
     * This method will scroll up until element is displayed
     *
     * @param element element
     * @return WebElement
     */
    public WebElement scrollUpUntilElementIsDisplayed(WebElement element) {
        TouchAction action = new TouchAction(iosDriver);
        while (!element.isDisplayed()) {
            action.press(180, 200).moveTo(0, 450).release().perform();
            try {
                waitForElementUntilClickable(element);
            } catch (Exception e) {
                //Do nothing
            }
        }
        return element;
    }

    public void scrollToTop() {
        waitForElementById(starhubText).click();
    }

    /**
     * This method will scroll to element
     *
     * @param element element
     * @return WebElement
     */
    public WebElement scrollToElement(WebElement element) {
        TouchAction action = new TouchAction(iosDriver);
        if (!element.isDisplayed()) {
            action.press(200,220).moveTo(element).release().perform();
        }
        return element;
    }

    /**
     * This method will wait for element to be located by xpath
     *
     * @param elementXpath xpath of element
     * @return WebElement
     */
    public WebElement waitForElementByXpath(String elementXpath) {
        try {
            return new WebDriverWait(iosDriver, 60).until(ExpectedConditions.presenceOfElementLocated(By.xpath(elementXpath)));
        } catch (Exception e) {
            throw new RuntimeException(ERROR_MSG_NO_ELEMENT_FOUND.replace("$1", elementXpath));
        }
    }

    /**
     * This method will wait for element to be located by id
     *
     * @param elementId id of element
     * @return WebElement
     */
    public WebElement waitForElementById(String elementId) {
        try {
            return new WebDriverWait(iosDriver, 60).until(ExpectedConditions.presenceOfElementLocated(By.id(elementId)));
        } catch (Exception e) {
            throw new RuntimeException(ERROR_MSG_NO_ELEMENT_FOUND.replace("$1", elementId));
        }
    }

    /**
     * This method will wait for element to be located by name
     *
     * @param elementName name of element
     * @return WebElement
     */
    public WebElement waitForElementByName(String elementName) {
        try {
            return new WebDriverWait(iosDriver, 60).until(ExpectedConditions.presenceOfElementLocated(By.name(elementName)));
        } catch (Exception e) {
            throw new RuntimeException(ERROR_MSG_NO_ELEMENT_FOUND.replace("$1", elementName));
        }
    }

    /**
     * This method will wait for element to be located by xpath with custom duration
     *
     * @param elementXpath xpath of element
     * @param duration     custom duration
     * @return WebElement
     */
    public WebElement waitForElementByXpath(String elementXpath, int duration) {
        try {
            return new WebDriverWait(iosDriver, duration).until(ExpectedConditions.presenceOfElementLocated(By.xpath(elementXpath)));
        } catch (Exception e) {
            throw new RuntimeException(ERROR_MSG_NO_ELEMENT_FOUND.replace("$1", elementXpath));
        }
    }

    /**
     * This method will wait for element to be located by id with custom duration
     *
     * @param elementId id of element
     * @param duration  custom duration
     * @return WebElement
     */
    public WebElement waitForElementById(String elementId, int duration) {
        try {
            return new WebDriverWait(iosDriver, duration).until(ExpectedConditions.presenceOfElementLocated(By.id(elementId)));
        } catch (Exception e) {
            throw new RuntimeException(ERROR_MSG_NO_ELEMENT_FOUND.replace("$1", elementId));
        }
    }

    /**
     * This method will wait for element to be located by name with custom duration
     *
     * @param elementName name of element
     * @param duration    custom duration
     * @return WebElement
     */
    public WebElement waitForElementByName(String elementName, int duration) {
        try {
            return new WebDriverWait(iosDriver, duration).until(ExpectedConditions.presenceOfElementLocated(By.name(elementName)));
        } catch (Exception e) {
            throw new RuntimeException(ERROR_MSG_NO_ELEMENT_FOUND.replace("$1", elementName));
        }
    }

    /**
     * This method will wait for element to be clickable by xpath
     *
     * @param elementXpath xpath of element
     * @return WebElement
     */
    public WebElement waitForElementByXpathUntilClickable(String elementXpath) {
        try {
            return new WebDriverWait(iosDriver, 60).until(ExpectedConditions.elementToBeClickable(By.xpath(elementXpath)));
        } catch (Exception e) {
            throw new RuntimeException(ERROR_MSG_NO_ELEMENT_FOUND.replace("$1", elementXpath));
        }
    }

    /**
     * This method will wait for element to be clickable by id
     *
     * @param elementId id of element
     * @return WebElement
     */
    public WebElement waitForElementByIdUntilClickable(String elementId) {
        try {
            return new WebDriverWait(iosDriver, 60).until(ExpectedConditions.elementToBeClickable(By.id(elementId)));
        } catch (Exception e) {
            throw new RuntimeException(ERROR_MSG_NO_ELEMENT_FOUND.replace("$1", elementId));
        }
    }

    /**
     * This method will wait for element to be clickable by name
     *
     * @param elementName name of element
     * @return WebElement
     */
    public WebElement waitForElementByNameUntilClickable(String elementName) {
        try {
            return new WebDriverWait(iosDriver, 60).until(ExpectedConditions.elementToBeClickable(By.name(elementName)));
        } catch (Exception e) {
            throw new RuntimeException(ERROR_MSG_NO_ELEMENT_FOUND.replace("$1", elementName));
        }
    }

    /**
     * This method will wait for element to be clickable
     *
     * @param element element
     * @return WebElement
     */
    public WebElement waitForElementUntilClickable(WebElement element) {
        try {
            return new WebDriverWait(iosDriver, 60).until(ExpectedConditions.elementToBeClickable(element));
        } catch (Exception e) {
            throw new RuntimeException(ERROR_MSG_NO_ELEMENT_FOUND.replace("$1", element.getText()));
        }
    }

    /**
     * This method will wait for element to be clickable by xpath with custom duration
     *
     * @param elementXpath xpath of element
     * @param duration     custom duration
     * @return WebElement
     */
    public WebElement waitForElementByXpathUntilClickable(String elementXpath, int duration) {
        try {
            return new WebDriverWait(iosDriver, duration).until(ExpectedConditions.elementToBeClickable(By.xpath(elementXpath)));
        } catch (Exception e) {
            throw new RuntimeException(ERROR_MSG_NO_ELEMENT_FOUND.replace("$1", elementXpath));
        }
    }

    /**
     * This method will wait for element to be clickable by id with custom duration
     *
     * @param elementId id of element
     * @param duration  custom duration
     * @return WebElement
     */
    public WebElement waitForElementByIdUntilClickable(String elementId, int duration) {
        try {
            return new WebDriverWait(iosDriver, duration).until(ExpectedConditions.elementToBeClickable(By.id(elementId)));
        } catch (Exception e) {
            throw new RuntimeException(ERROR_MSG_NO_ELEMENT_FOUND.replace("$1", elementId));
        }
    }

    /**
     * This method will wait for element to be clickable by name with custom duration
     *
     * @param elementName name of element
     * @param duration    custom duration
     * @return WebElement
     */
    public WebElement waitForElementByNameUntilClickable(String elementName, int duration) {
        try {
            return new WebDriverWait(iosDriver, duration).until(ExpectedConditions.elementToBeClickable(By.name(elementName)));
        } catch (Exception e) {
            throw new RuntimeException(ERROR_MSG_NO_ELEMENT_FOUND.replace("$1", elementName));
        }
    }

    /**
     * This method will wait for element to be clickable with custom duration
     *
     * @param element  element
     * @param duration custom duration
     * @return WebElement
     */
    public WebElement waitForElementUntilClickable(WebElement element, int duration) {
        try {
            return new WebDriverWait(iosDriver, duration).until(ExpectedConditions.elementToBeClickable(element));
        } catch (Exception e) {
            throw new RuntimeException(ERROR_MSG_NO_ELEMENT_FOUND.replace("$1", element.getText()));
        }
    }

    /**
     * This method will wait for all element to be located by xpath
     *
     * @param elementXpath xpath of element
     * @return List<WebElement>
     */
    public List<WebElement> waitForElementsByXpath(String elementXpath) {
        try {
            return new WebDriverWait(iosDriver, 60).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(elementXpath)));
        } catch (Exception e) {
            throw new RuntimeException(ERROR_MSG_NO_ELEMENT_FOUND.replace("$1", elementXpath));
        }
    }

    /**
     * This method will wait for all element to be located by id
     *
     * @param elementId id of element
     * @return List<WebElement>
     */
    public List<WebElement> waitForElementsById(String elementId) {
        try {
            return new WebDriverWait(iosDriver, 60).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id(elementId)));
        } catch (Exception e) {
            throw new RuntimeException(ERROR_MSG_NO_ELEMENT_FOUND.replace("$1", elementId));
        }
    }

    /**
     * This method will wait for all element to be located by name
     *
     * @param elementName name of element
     * @return List<WebElement>
     */
    public List<WebElement> waitForElementsByName(String elementName) {
        try {
            return new WebDriverWait(iosDriver, 60).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.name(elementName)));
        } catch (Exception e) {
            throw new RuntimeException(ERROR_MSG_NO_ELEMENT_FOUND.replace("$1", elementName));
        }
    }

    /**
     * This method will wait for all element to be located by xpath with custom duration
     *
     * @param elementXpath xpath of element
     * @param duration     custom duration
     * @return List<WebElement>
     */
    public List<WebElement> waitForElementsByXpath(String elementXpath, int duration) {
        try {
            return new WebDriverWait(iosDriver, duration).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(elementXpath)));
        } catch (Exception e) {
            throw new RuntimeException(ERROR_MSG_NO_ELEMENT_FOUND.replace("$1", elementXpath));
        }
    }

    /**
     * This method will wait for all element to be located by id with custom duration
     *
     * @param elementId id of element
     * @param duration  custom duration
     * @return List<WebElement>
     */
    public List<WebElement> waitForElementsById(String elementId, int duration) {
        try {
            return new WebDriverWait(iosDriver, duration).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id(elementId)));
        } catch (Exception e) {
            throw new RuntimeException(ERROR_MSG_NO_ELEMENT_FOUND.replace("$1", elementId));
        }
    }

    /**
     * This method will wait for all element to be located by name with custom duration
     *
     * @param elementName name of duration
     * @param duration    custom duration
     * @return List<WebElement>
     */
    public List<WebElement> waitForElementsByName(String elementName, int duration) {
        try {
            return new WebDriverWait(iosDriver, duration).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.name(elementName)));
        } catch (Exception e) {
            throw new RuntimeException(ERROR_MSG_NO_ELEMENT_FOUND.replace("$1", elementName));
        }
    }

    /**
     * This method will find element by xpath
     *
     * @param elementXpath xpath of element
     * @return WebElement
     */
    public WebElement findElementByXpath(String elementXpath) {
        try {
            return iosDriver.findElementByXPath(elementXpath);
        } catch (Exception e) {
            throw new RuntimeException(ERROR_MSG_NO_ELEMENT_FOUND.replace("$1", elementXpath));
        }
    }

    /**
     * This method will find element by accessibility id
     *
     * @param elementId id of element
     * @return WebElement
     */
    public WebElement findElementById(String elementId) {
        try {
            return iosDriver.findElementByAccessibilityId(elementId);
        } catch (Exception e) {
            throw new RuntimeException(ERROR_MSG_NO_ELEMENT_FOUND.replace("$1", elementId));
        }
    }

    /**
     * This method will find element by name
     *
     * @param elementName name of element
     * @return WebElement
     */
    public WebElement findElementByName(String elementName) {
        try {
            return iosDriver.findElementByName(elementName);
        } catch (Exception e) {
            throw new RuntimeException(ERROR_MSG_NO_ELEMENT_FOUND.replace("$1", elementName));
        }
    }

    /**
     * This method will check if Loading element is still displayed
     */
    protected void hasLoadingCompleted() {
        try {
            new WebDriverWait(iosDriver, 60).until(ExpectedConditions.invisibilityOf(iosDriver.findElementByName("Loading...")));
        } catch (Exception e) {
            // Do nothing
        }
    }

    /**
     * This method will tap on element but will swipe up if element is not visible
     *
     * @param element to be tapped
     */
    public void tapOnElement(WebElement element) {
        TouchAction action = new TouchAction(iosDriver);
        scrollDownUntilElementIsDisplayed(element);
        if (!element.getText().isEmpty()) {
            action.tap(element).perform();
        }
    }
}
