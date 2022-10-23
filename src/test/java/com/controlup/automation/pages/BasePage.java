package com.controlup.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract class representation of a Page in the UI. Page object pattern
 */
public abstract class BasePage {
	protected Logger logger;
	protected WebDriver driver;

	private static final By HOME_LINK_SELECTOR = By.cssSelector("header a.navbar-brand");

	/**
	 * Constructor injecting the WebDriver interface
	 * @param driver
	 */
	public BasePage(WebDriver driver) {
		this.driver = driver;
		this.logger = LoggerFactory.getLogger(this.getClass());

		PageFactory.initElements(driver, this);
	}

	/**
	 * Navigate page to self 
	 * @return
	 */
	public abstract <P extends BasePage> P navigateToSelf();

	/**
	 * Navigate to the Home page via top menu link
	 * @return home page instance
	 */
	public HomePage navigateToHomePage(){
		driver.findElement(HOME_LINK_SELECTOR).click();
	    return new HomePage(driver);
	}

	/**
	 * Navigate to the page via top menu
	 * @return
	 */
	public <P extends BasePage> P navigateToPage(Class<P> clazz){
		try {
			return clazz.getDeclaredConstructor(WebDriver.class)
					.newInstance(driver)
					.navigateToSelf();
		} catch (Exception e){
			throw new RuntimeException("Couldn't navigate to Page: " + clazz.getName(), e);
		}
	}
}
