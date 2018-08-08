package com.scb.fmsw.mobile.base;

import com.scb.fmsw.mobile.screen.LandingScreen;
import com.scb.fmsw.mobile.screen.LoginScreen;
import com.scb.fmsw.mobile.screen.OverviewScreen;
import io.appium.java_client.TouchAction;
import org.testng.annotations.AfterMethod;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class BaseTest extends BaseDriver implements Runnable {

	protected Properties prop;

	public BaseTest() {
		prop = getProperties();
		iosDriver = this.getDriver();
//		startThread();
	}

	/**
	 * This method will create 1 thread
	 */
	protected void startThread() {
		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		System.out.println("Separate thread " + Thread.currentThread().getId() + " started running");
		while (!Thread.currentThread().isInterrupted()) {
			try {
				Thread.sleep(35000); // sleep for 35 sec
//				Thread.sleep(240000); // sleep for 4min
				new TouchAction(iosDriver).tap(180, 40).perform();
//				System.out.println("Separate thread " + Thread.currentThread().getId() + ": tap on screen to prevent it from sleeping");
			} catch (Exception e) {
				Thread.currentThread().interrupt();
				System.out.println(Thread.currentThread().getId());
			}
		}
		if (Thread.currentThread().isInterrupted()) {
			startThread();
		}
	}

	/**
	 * This method will return properties
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

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		iosDriver.quit();
	}

	/**
	 * This method will consolidate the method from login to navigation to overview screen
	 *
	 * @param userID username
	 * @return OverviewScreen
	 */
	protected OverviewScreen login(String userID) {
		LoginScreen loginScreen = new LoginScreen(iosDriver);
		LandingScreen landingScreen = loginScreen.login(userID);
		return landingScreen.overviewScreenNavigation(userID);
	}

	/**
	 * This method will compare 2 String list
	 *
	 * @param primaryList used as the base
	 * @param secondaryList used to compared against the primaryList
	 * @return boolean
	 */
	protected boolean compareLists(List<String> primaryList, List<String> secondaryList) {
		Collections.sort(primaryList);
		Collections.sort(secondaryList);
		System.out.println("Comparing list");
        System.out.println("Primary List: " + primaryList.size());
        for (String el : primaryList) {
            System.out.println(el);
        }
        System.out.println("Secondary List: " + secondaryList.size());
        for (String el : secondaryList) {
            System.out.println(el);
        }
		return primaryList.containsAll(secondaryList);
	}

	protected void printList(List<String> list) {
		for (String el : list) {
			System.out.println(el);
		}
	}
}