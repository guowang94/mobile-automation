package com.scb.fmsw.mobile.screen;

import com.scb.fmsw.mobile.base.BaseScreen;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

public class DashboardScreen extends BaseScreen {

	public DashboardScreen() {
		
	}

	public DashboardScreen(IOSDriver<IOSElement> testDriver) {
		iosDriver = testDriver;
	}
}
