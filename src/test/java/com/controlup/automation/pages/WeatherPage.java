package com.controlup.automation.pages;

import com.controlup.automation.components.WeatherWidget;
import com.controlup.automation.utils.Await;
import com.controlup.automation.utils.ElementsUtil;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class WeatherPage extends BasePage {
    @FindBy(css = "header.header.fixed-top li a[href='/weather/']")
    private WebElement weatherPageLink;

    @FindBy(css = "input#ctl00_butUnitC")
    private WebElement unitCelcius;

    @FindBy(css = "input#ctl00_butUnitF")
    private WebElement unitFahrenheit;

    @FindBy(css = "input#ctl00_Search1_txtSearch")
    private WebElement searchTextBar;

    @FindBy(css = "div.tt-dataset.tt-dataset-location div")
    private WebElement searchSuggestions;

    @FindBy(css = WeatherWidget.DEFAULT_SELECTOR)
    private WebElement weatherWidgetHolder;

    public WeatherWidget getWeatherWidget() {
        return new WeatherWidget(weatherWidgetHolder);
    }

    public WeatherPage(WebDriver driver) {
        super(driver);
    }

    @Step("Navigate to Weather page")
    public WeatherPage navigateToSelf() {
        navigateToHomePage();
        weatherPageLink.click();
        return this;
    }

    @Step("Switch to celsius mode")
    public WeatherPage switchToCelsiusMode() {
        unitCelcius.click();
        return this;
    }

    @Step("Switch to Fahrenheit mode")
    public WeatherPage switchToFahrenheitMode() {
        unitFahrenheit.click();
        return this;
    }

    @Step("Set search text = {0}")
    public WeatherPage setSearchText(String text) {
        searchTextBar.sendKeys(text);
        ElementsUtil.waitForVisible(searchSuggestions);
        if (searchSuggestions.isDisplayed()) { searchSuggestions.click(); }
        return this;
    }
}
