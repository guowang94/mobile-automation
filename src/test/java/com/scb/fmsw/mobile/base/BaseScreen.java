package com.scb.fmsw.mobile.base;

import com.scb.fmsw.mobile.WorkflowConstants;
import com.scb.fmsw.mobile.screen.InboxScreen;
import com.scb.fmsw.mobile.screen.LoginScreen;
import com.scb.fmsw.mobile.screen.delegation.DelegationScreen;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
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
    private String inboxSpecificButton = "//XCUIElementTypeStaticText[@name='$1']";
    private String formSwitchXpath = "//XCUIElementTypeStaticText[@name='$1']/ancestor::XCUIElementTypeCell/descendant::XCUIElementTypeSwitch";
    private String formTextViewXpath = "//XCUIElementTypeStaticText[@name='$1']/ancestor::XCUIElementTypeCell/descendant::XCUIElementTypeTextView";
    private String formTextFieldXpath = "//XCUIElementTypeStaticText[@name='$1']/ancestor::XCUIElementTypeCell/descendant::XCUIElementTypeTextField";
    private String formButtonXpath = "//XCUIElementTypeStaticText[@name='$1']/ancestor::XCUIElementTypeCell/descendant::XCUIElementTypeButton";
    private String pickerDoneButtonXpath = "//XCUIElementTypePickerWheel/ancestor::XCUIElementTypePicker/preceding-sibling::XCUIElementTypeButton[@name='Done']";
    private String pickerWheelXpath = "//XCUIElementTypePickerWheel";
    private String formDoneButtonXpath = "//XCUIElementTypeNavigationBar/XCUIElementTypeButton[2]";
    private String formContainer = "//XCUIElementTypeTable[@visible='true']";
    private String statusBar = "//XCUIElementTypeStatusBar";


    //accessibility id
    private String menuButton = "MenuIcon";
    private String sideNavHelp = "Help";
    private String logout = "Logout";
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
     * This method will enter Comments
     *
     * @param commentsLabel
     * @param comments
     */
    public void enterComments(String commentsLabel, String comments) {
        findElementByXpath(formTextViewXpath.replace("$1", commentsLabel), true).sendKeys(comments);
        iosDriver.hideKeyboard();
    }

    /**
     * This method will search for user
     *
     * @param searchFieldLabel
     * @param userID
     */
    public void searchForUser(String searchFieldLabel, String userID) {
        findElementByXpath(formTextFieldXpath.replace("$1", searchFieldLabel), true).sendKeys(userID.substring(0, 5));
        hasLoadingCompleted();
        try {
            scrollToElement(waitForElementById(userID, true)).click();
        } catch (Exception e) {
            screenshot(SCREENSHOT_MSG_NO_USER_FOUND);
            throw new RuntimeException(ERROR_MSG_NO_USER_FOUND.replace("$1", userID));
        }
    }

    /**
     * This method will search for group
     *
     * @param searchFieldLabel
     * @param groupName
     */
    public void searchForGroup(String searchFieldLabel, String groupName) {
        findElementByXpath(formTextFieldXpath.replace("$1", searchFieldLabel), true).sendKeys(groupName.substring(0, 5));
        hasLoadingCompleted();
        try {
            scrollToElement(waitForElementByName(groupName, true)).click();
        } catch (Exception e) {
            screenshot(SCREENSHOT_MSG_NO_GROUP_FOUND);
            throw new RuntimeException(ERROR_MSG_NO_GROUP_FOUND.replace("$1", groupName));
        }
    }

    /**
     * This method will select Picker Value
     *
     * @param pickerLabel
     * @param value
     */
    public void selectPickerValue(String pickerLabel, String value) {
        findElementByXpath(formButtonXpath.replace("$1", pickerLabel), true).click();
        findElementByXpath(pickerWheelXpath, true).sendKeys(value);
        findElementByXpath(pickerDoneButtonXpath, true).click();
    }

    /**
     * This method will toggle Switch
     *
     * @param switchLabel
     * @param isToggle
     */
    public void toggleSwitch(String switchLabel, boolean isToggle) {
        if (isToggle) {
            try {
                findElementByXpath(formSwitchXpath.replace("$1", switchLabel), true).click();
            } catch (Exception e) {
                //Do nothing
            }
        }
    }

    /**
     * This method will select Late Code value and enter Late Comments
     *
     * @param lateCode
     */
    public void selectLateCode(String lateCode) {
        if (lateCode != null) {
            selectPickerValue(FORM_LABEL_LATE_CODE, lateCode);
            enterComments(FORM_LABEL_LATE_COMMENTS, MSG_ENTER_LATE_COMMENT);
        }
    }

    /**
     * This method will select Late Response Code value and enter Late Response Comments
     *
     * @param lateResponseCode
     * @param workflowType
     */
    public void selectLateResponseCode(String lateResponseCode, String workflowType) {
        if (lateResponseCode != null) {
            selectPickerValue(FORM_LABEL_LATE_RESPONSE_CODE_COMPULSORY, lateResponseCode);
            if (WORKFLOW_VE.equals(workflowType)) {
                enterComments(FORM_LABEL_LATE_RESPONSE_COMMENTS, MSG_ENTER_LATE_COMMENT);
            } else {
                enterComments(FORM_LABEL_LATE_RESPONSE_COMMENTS_COMPULSORY, MSG_ENTER_LATE_COMMENT);
            }
        }
    }

    /**
     * This method will tap on Form's Done button
     */
    public void tapOnFormDoneButton() {
        findElementByXpath(formDoneButtonXpath, true).click();
    }

    /**
     * This method will check if Form Container is loaded
     *
     * @return boolean
     */
    public boolean hasFormContainerLoaded() {
        try {
            return findElementByXpath(formContainer, true).isDisplayed();
        } catch (Exception e) {
            throw new RuntimeException("Table Container not found");
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
        waitForElementById(logout, true).click();
        System.out.println("Logout of the Application\n");
        return new LoginScreen(iosDriver);
    }

    /**
     * This method will tap on Menu button
     */
    public void tapOnMenuButton() {
        hasLoadingCompleted();
        waitForElementById(menuButton, true).click();
    }

    /**
     * This method will tap on More Option button
     */
    public void tapOnMoreOptionButton() {
        hasLoadingCompleted();
        waitForElementById(moreOption, true).click();
    }

    /**
     * This method will tap on Help button in the side navigation
     */
    public void tapOnSideNavHelp() {
        waitForElementById(sideNavHelp, true).click();
    }

    /**
     * This method will navigate to specific inbox from side navigation
     *
     * @param inboxName name of the inbox
     * @return InboxScreen
     */
    public InboxScreen navigateToSpecificInboxFromSideNav(String inboxName) {
        waitForElementByXpath(inboxSpecificButton.replace("$1", inboxName), true).click();
        return new InboxScreen(iosDriver);
    }

    /**
     * This method will navigate to delegation screen from side navigation
     *
     * @return DelegationScreen
     */
    public DelegationScreen navigationToDelegationScreen() {
        hasLoadingCompleted();
        TouchAction action = new TouchAction(iosDriver);

        while (!waitForElementById(delegation, true).isDisplayed()) {
            Dimension size = iosDriver.manage().window().getSize();
            int startY = (int) (size.height * 0.8);
            int endY = (int) (size.height * 0.0);
            int startX = (int) (size.width / 2.2);
            //Logging purpose
//            System.out.println("Trying to swipe up from x:" + startX + " y:" + startY + ", to x:" + startX + " y:" + endY);
            action.press(PointOption.point(startX, startY)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2)))
                    .moveTo(PointOption.point(startX, endY)).release().perform();
            try {
                waitForElementUntilClickable(waitForElementById(delegation, true), 15, true);
            } catch (Exception e) {
                //Do nothing
            }
        }
        waitForElementById(delegation, true).click();
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
            Dimension size = iosDriver.manage().window().getSize();
            int startY = (int) (size.height * 0.7);
            int endY = (int) (size.height * 0.2);
            int startX = (int) (size.width / 2.2);
            //Logging purpose
//            System.out.println("Trying to swipe up from x:" + startX + " y:" + startY + ", to x:" + startX + " y:" + endY);
            action.press(PointOption.point(startX, startY)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2)))
                    .moveTo(PointOption.point(startX, endY)).release().perform();
            try {
                waitForElementUntilClickable(element, 15, true);
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
            Dimension size = iosDriver.manage().window().getSize();
            int startY = (int) (size.height * 0.3);
            int endY = (int) (size.height * 0.9);
            int startX = (int) (size.width / 2.2);
            //Logging purpose
//            System.out.println("Trying to swipe from x:" + startX + " y:" + startY + ", to x:" + startX + " y:" + endY);
            action.press(PointOption.point(startX, startY)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2)))
                    .moveTo(PointOption.point(startX, endY)).release().perform();
            try {
                waitForElementUntilClickable(element, true);
            } catch (Exception e) {
                //Do nothing
            }
        }
        return element;
    }

    public void scrollToTop() {
        waitForElementByXpath(statusBar, true).click();
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
            Dimension size = iosDriver.manage().window().getSize();
            int startY = (int) (size.height * 0.2);
            int startX = (int) (size.width / 2.2);
            //Logging purpose
//            System.out.println("Trying to swipe from x:" + startX + " y:" + startY + ", to x:" + element.getLocation().getX() + " y:" + element.getLocation().getY());
            action.press(PointOption.point(startX, startY)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2)))
                    .moveTo(PointOption.point(element.getLocation().getX(), element.getLocation().getY())).release().perform();
        }
        return element;
    }

    /**
     * This method will wait for element to be located by xpath
     *
     * @param elementXpath xpath of element
     * @param isException
     * @return WebElement
     */
    public WebElement waitForElementByXpath(String elementXpath, boolean isException) {
        try {
            return new WebDriverWait(iosDriver, 60).until(ExpectedConditions.presenceOfElementLocated(By.xpath(elementXpath)));
        } catch (Exception e) {
            if (isException)
                throw new RuntimeException(ERROR_MSG_NO_ELEMENT_FOUND.replace("$1", elementXpath));
            else
                return null;
        }
    }

    /**
     * This method will wait for element to be located by id
     *
     * @param elementId   id of element
     * @param isException
     * @return WebElement
     */
    public WebElement waitForElementById(String elementId, boolean isException) {
        try {
            return new WebDriverWait(iosDriver, 60).until(ExpectedConditions.presenceOfElementLocated(By.id(elementId)));
        } catch (Exception e) {
            if (isException)
                throw new RuntimeException(ERROR_MSG_NO_ELEMENT_FOUND.replace("$1", elementId));
            else
                return null;
        }
    }

    /**
     * This method will wait for element to be located by name
     *
     * @param elementName name of element
     * @param isException
     * @return WebElement
     */
    public WebElement waitForElementByName(String elementName, boolean isException) {
        try {
            return new WebDriverWait(iosDriver, 60).until(ExpectedConditions.presenceOfElementLocated(By.name(elementName)));
        } catch (Exception e) {
            if (isException)
                throw new RuntimeException(ERROR_MSG_NO_ELEMENT_FOUND.replace("$1", elementName));
            else
                return null;
        }
    }

    /**
     * This method will wait for element to be located by xpath with custom duration
     *
     * @param elementXpath xpath of element
     * @param duration     custom duration
     * @param isException
     * @return WebElement
     */
    public WebElement waitForElementByXpath(String elementXpath, int duration, boolean isException) {
        try {
            return new WebDriverWait(iosDriver, duration).until(ExpectedConditions.presenceOfElementLocated(By.xpath(elementXpath)));
        } catch (Exception e) {
            if (isException)
                throw new RuntimeException(ERROR_MSG_NO_ELEMENT_FOUND.replace("$1", elementXpath));
            else
                return null;
        }
    }

    /**
     * This method will wait for element to be located by id with custom duration
     *
     * @param elementId   id of element
     * @param duration    custom duration
     * @param isException
     * @return WebElement
     */
    public WebElement waitForElementById(String elementId, int duration, boolean isException) {
        try {
            return new WebDriverWait(iosDriver, duration).until(ExpectedConditions.presenceOfElementLocated(By.id(elementId)));
        } catch (Exception e) {
            if (isException)
                throw new RuntimeException(ERROR_MSG_NO_ELEMENT_FOUND.replace("$1", elementId));
            else
                return null;
        }
    }

    /**
     * This method will wait for element to be located by name with custom duration
     *
     * @param elementName name of element
     * @param duration    custom duration
     * @param isException
     * @return WebElement
     */
    public WebElement waitForElementByName(String elementName, int duration, boolean isException) {
        try {
            return new WebDriverWait(iosDriver, duration).until(ExpectedConditions.presenceOfElementLocated(By.name(elementName)));
        } catch (Exception e) {
            if (isException)
                throw new RuntimeException(ERROR_MSG_NO_ELEMENT_FOUND.replace("$1", elementName));
            else
                return null;
        }
    }

    /**
     * This method will wait for element to be clickable by xpath
     *
     * @param elementXpath xpath of element
     * @param isException
     * @return WebElement
     */
    public WebElement waitForElementByXpathUntilClickable(String elementXpath, boolean isException) {
        try {
            return new WebDriverWait(iosDriver, 60).until(ExpectedConditions.elementToBeClickable(By.xpath(elementXpath)));
        } catch (Exception e) {
            if (isException)
                throw new RuntimeException(ERROR_MSG_NO_ELEMENT_FOUND.replace("$1", elementXpath));
            else
                return null;
        }
    }

    /**
     * This method will wait for element to be clickable by id
     *
     * @param elementId   id of element
     * @param isException
     * @return WebElement
     */
    public WebElement waitForElementByIdUntilClickable(String elementId, boolean isException) {
        try {
            return new WebDriverWait(iosDriver, 60).until(ExpectedConditions.elementToBeClickable(By.id(elementId)));
        } catch (Exception e) {
            if (isException)
                throw new RuntimeException(ERROR_MSG_NO_ELEMENT_FOUND.replace("$1", elementId));
            else
                return null;
        }
    }

    /**
     * This method will wait for element to be clickable by name
     *
     * @param elementName name of element
     * @param isException
     * @return WebElement
     */
    public WebElement waitForElementByNameUntilClickable(String elementName, boolean isException) {
        try {
            return new WebDriverWait(iosDriver, 60).until(ExpectedConditions.elementToBeClickable(By.name(elementName)));
        } catch (Exception e) {
            if (isException)
                throw new RuntimeException(ERROR_MSG_NO_ELEMENT_FOUND.replace("$1", elementName));
            else
                return null;
        }
    }

    /**
     * This method will wait for element to be clickable
     *
     * @param element     element
     * @param isException
     * @return WebElement
     */
    public WebElement waitForElementUntilClickable(WebElement element, boolean isException) {
        try {
            return new WebDriverWait(iosDriver, 60).until(ExpectedConditions.elementToBeClickable(element));
        } catch (Exception e) {
            if (isException)
                throw new RuntimeException(ERROR_MSG_NO_ELEMENT_FOUND.replace("$1", element.getText()));
            else
                return null;
        }
    }

    /**
     * This method will wait for element to be clickable by xpath with custom duration
     *
     * @param elementXpath xpath of element
     * @param duration     custom duration
     * @param isException
     * @return WebElement
     */
    public WebElement waitForElementByXpathUntilClickable(String elementXpath, int duration, boolean isException) {
        try {
            return new WebDriverWait(iosDriver, duration).until(ExpectedConditions.elementToBeClickable(By.xpath(elementXpath)));
        } catch (Exception e) {
            if (isException)
                throw new RuntimeException(ERROR_MSG_NO_ELEMENT_FOUND.replace("$1", elementXpath));
            else
                return null;
        }
    }

    /**
     * This method will wait for element to be clickable by id with custom duration
     *
     * @param elementId   id of element
     * @param duration    custom duration
     * @param isException
     * @return WebElement
     */
    public WebElement waitForElementByIdUntilClickable(String elementId, int duration, boolean isException) {
        try {
            return new WebDriverWait(iosDriver, duration).until(ExpectedConditions.elementToBeClickable(By.id(elementId)));
        } catch (Exception e) {
            if (isException)
                throw new RuntimeException(ERROR_MSG_NO_ELEMENT_FOUND.replace("$1", elementId));
            else
                return null;
        }
    }

    /**
     * This method will wait for element to be clickable by name with custom duration
     *
     * @param elementName name of element
     * @param duration    custom duration
     * @param isException
     * @return WebElement
     */
    public WebElement waitForElementByNameUntilClickable(String elementName, int duration, boolean isException) {
        try {
            return new WebDriverWait(iosDriver, duration).until(ExpectedConditions.elementToBeClickable(By.name(elementName)));
        } catch (Exception e) {
            if (isException)
                throw new RuntimeException(ERROR_MSG_NO_ELEMENT_FOUND.replace("$1", elementName));
            else
                return null;
        }
    }

    /**
     * This method will wait for element to be clickable with custom duration
     *
     * @param element     element
     * @param duration    custom duration
     * @param isException
     * @return WebElement
     */
    public WebElement waitForElementUntilClickable(WebElement element, int duration, boolean isException) {
        try {
            return new WebDriverWait(iosDriver, duration).until(ExpectedConditions.elementToBeClickable(element));
        } catch (Exception e) {
            if (isException)
                throw new RuntimeException(ERROR_MSG_NO_ELEMENT_FOUND.replace("$1", element.getText()));
            else
                return null;
        }
    }

    /**
     * This method will wait for all element to be located by xpath
     *
     * @param elementXpath xpath of element
     * @param isException
     * @return List<WebElement>
     */
    public List<WebElement> waitForElementsByXpath(String elementXpath, boolean isException) {
        try {
            return new WebDriverWait(iosDriver, 60).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(elementXpath)));
        } catch (Exception e) {
            if (isException)
                throw new RuntimeException(ERROR_MSG_NO_ELEMENT_FOUND.replace("$1", elementXpath));
            else
                return null;
        }
    }

    /**
     * This method will wait for all element to be located by id
     *
     * @param elementId   id of element
     * @param isException
     * @return List<WebElement>
     */
    public List<WebElement> waitForElementsById(String elementId, boolean isException) {
        try {
            return new WebDriverWait(iosDriver, 60).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id(elementId)));
        } catch (Exception e) {
            if (isException)
                throw new RuntimeException(ERROR_MSG_NO_ELEMENT_FOUND.replace("$1", elementId));
            else
                return null;
        }
    }

    /**
     * This method will wait for all element to be located by name
     *
     * @param elementName name of element
     * @param isException
     * @return List<WebElement>
     */
    public List<WebElement> waitForElementsByName(String elementName, boolean isException) {
        try {
            return new WebDriverWait(iosDriver, 60).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.name(elementName)));
        } catch (Exception e) {
            if (isException)
                throw new RuntimeException(ERROR_MSG_NO_ELEMENT_FOUND.replace("$1", elementName));
            else
                return null;
        }
    }

    /**
     * This method will wait for all element to be located by xpath with custom duration
     *
     * @param elementXpath xpath of element
     * @param duration     custom duration
     * @param isException
     * @return List<WebElement>
     */
    public List<WebElement> waitForElementsByXpath(String elementXpath, int duration, boolean isException) {
        try {
            return new WebDriverWait(iosDriver, duration).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(elementXpath)));
        } catch (Exception e) {
            if (isException)
                throw new RuntimeException(ERROR_MSG_NO_ELEMENT_FOUND.replace("$1", elementXpath));
            else
                return null;
        }
    }

    /**
     * This method will wait for all element to be located by id with custom duration
     *
     * @param elementId   id of element
     * @param duration    custom duration
     * @param isException
     * @return List<WebElement>
     */
    public List<WebElement> waitForElementsById(String elementId, int duration, boolean isException) {
        try {
            return new WebDriverWait(iosDriver, duration).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id(elementId)));
        } catch (Exception e) {
            if (isException)
                throw new RuntimeException(ERROR_MSG_NO_ELEMENT_FOUND.replace("$1", elementId));
            else
                return null;
        }
    }

    /**
     * This method will wait for all element to be located by name with custom duration
     *
     * @param elementName name of duration
     * @param duration    custom duration
     * @param isException
     * @return List<WebElement>
     */
    public List<WebElement> waitForElementsByName(String elementName, int duration, boolean isException) {
        try {
            return new WebDriverWait(iosDriver, duration).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.name(elementName)));
        } catch (Exception e) {
            if (isException)
                throw new RuntimeException(ERROR_MSG_NO_ELEMENT_FOUND.replace("$1", elementName));
            else
                return null;
        }
    }

    /**
     * This method will find element by xpath
     *
     * @param elementXpath xpath of element
     * @param isException
     * @return WebElement
     */
    public WebElement findElementByXpath(String elementXpath, boolean isException) {
        try {
            return iosDriver.findElementByXPath(elementXpath);
        } catch (Exception e) {
            if (isException)
                throw new RuntimeException(ERROR_MSG_NO_ELEMENT_FOUND.replace("$1", elementXpath));
            else
                return null;
        }
    }

    /**
     * This method will find element by accessibility id
     *
     * @param elementId   id of element
     * @param isException
     * @return WebElement
     */
    public WebElement findElementById(String elementId, boolean isException) {
        try {
            return iosDriver.findElementByAccessibilityId(elementId);
        } catch (Exception e) {
            if (isException)
                throw new RuntimeException(ERROR_MSG_NO_ELEMENT_FOUND.replace("$1", elementId));
            else
                return null;
        }
    }

    /**
     * This method will find element by name
     *
     * @param elementName name of element
     * @param isException
     * @return WebElement
     */
    public WebElement findElementByName(String elementName, boolean isException) {
        try {
            return iosDriver.findElementByName(elementName);
        } catch (Exception e) {
            if (isException)
                throw new RuntimeException(ERROR_MSG_NO_ELEMENT_FOUND.replace("$1", elementName));
            else
                return null;
        }
    }

    /**
     * This method will check if Loading element is still displayed
     */
    protected void hasLoadingCompleted() {
        try {
            new WebDriverWait(iosDriver, 60).until(ExpectedConditions.invisibilityOfElementLocated(By.id("Loading")));
        } catch (Exception e) {
            // Do nothing
        }
    }

    /**
     * This method will tap on element but will swipe up if element is not visible
     *
     * @param element to be tapped
     */
    protected void tapOnElement(WebElement element) {
        TouchAction action = new TouchAction(iosDriver);
        scrollDownUntilElementIsDisplayed(element);
        if (!element.getText().isEmpty()) {
            action.tap(PointOption.point(element.getLocation().getX(), element.getLocation().getY())).perform();
        }
    }

    /**
     * This method will get the x axis of the element
     *
     * @param element
     * @return int
     */
    protected int getElementLocationX(WebElement element) {
        return element.getLocation().getX() + element.getRect().getWidth() / 2;
    }

    /**
     * This method will get the y axis of the element
     *
     * @param element
     * @return int
     */
    protected int getElementLocationY(WebElement element) {
        return element.getLocation().getY() + element.getRect().getHeight() / 2;
    }
}
