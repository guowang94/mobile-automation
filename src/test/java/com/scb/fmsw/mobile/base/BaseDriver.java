package com.scb.fmsw.mobile.base;

import com.scb.fmsw.mobile.WorkflowConstants;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeMethod;

import java.net.URL;
import java.util.concurrent.TimeUnit;

public class BaseDriver implements WorkflowConstants {

    private DesiredCapabilities capabilities = new DesiredCapabilities();
    protected IOSDriver<IOSElement> iosDriver = null;

    /**
     * This method will return iosDriver
     *
     * @return IOSDriver<IOSElement>
     */
    public IOSDriver<IOSElement> getDriver() {
        return iosDriver;
    }

    @BeforeMethod(alwaysRun = true)
    public void initDriver() {
        try {
            capabilities.setCapability(CAPABILITY_NAME_DEVICE_NAME, CAPABILITY_VALUE_DEVICE_NAME);
            capabilities.setCapability(CAPABILITY_NAME_PLATFORM_NAME, CAPABILITY_VALUE_PLATFORM_NAME);
            capabilities.setCapability(CAPABILITY_NAME_PLATFORM_VERSION, CAPABILITY_VALUE_PLATFORM_VERSION);
            capabilities.setCapability(CAPABILITY_NAME_UDID, CAPABILITY_VALUE_UDID_IPHONE6);
            capabilities.setCapability(CAPABILITY_NAME_AUTOMATION_NAME, CAPABILITY_VALUE_AUTOMATION_NAME);
            capabilities.setCapability(CAPABILITY_NAME_LAUNCH_TIMEOUT, CAPABILITY_VALUE_LAUNCH_TIMEOUT);
//			capabilities.setCapability(CAPABILITY_NAME_APP, CAPABILITY_VALUE_APP_UAT);
            capabilities.setCapability(CAPABILITY_NAME_NEW_COMMAND_TIMEOUT, CAPABILITY_VALUE_NEW_COMMAND_TIMEOUT);
            capabilities.setCapability(CAPABILITY_NAME_BUNDLE_ID, CAPABILITY_VALUE_BUNDLE_ID);
            capabilities.setCapability(CAPABILITY_NAME_USE_NEW_WDA, CAPABILITY_VALUE_USE_NEW_WDA);

            iosDriver = new IOSDriver<>(new URL(DRIVER_URL), capabilities);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(ERROR_MSG_APPIUM_DRIVER_NOT_FOUND);
        }
        iosDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
    }
}
