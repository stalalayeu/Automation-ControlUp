package com.controlup.automation.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {
    
    public HomePage(WebDriver driver) {
        super(driver);
    }
    
    @Step("Navigate to Home page")
    public HomePage navigateToSelf() {
        return super.navigateToHomePage();
    }

}
