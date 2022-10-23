package com.controlup.automation.uitest;

import com.controlup.automation.pages.HomePage;

import com.controlup.automation.pages.WeatherPage;
import com.controlup.automation.restapi.BaseApiTest;
import io.qameta.allure.*;
import org.assertj.core.data.Percentage;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class ExpressTest extends BaseUiTest {

    @Feature("WEATHER UI")
    @Story("Current Temperature")
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "API and UI temperature comparison")
    public void testSimpleNavigation() {
        float tempAPI = given().
                headers(BaseApiTest.RestWeather.HEADERS).
                queryParams("q", "20852").
            when().get(BaseApiTest.RestWeather.URL).
            then().extract().
                body().jsonPath().getFloat("current.temp_c");

     	HomePage homePage = new HomePage(driver);
     	float tempUI = homePage
                .navigateToPage(WeatherPage.class)
                .switchToCelsiusMode()
                .setSearchText("20852")
                .getWeatherWidget()
                .getTemperatureFloat();

        assertThat(tempUI).isCloseTo(tempAPI, Percentage.withPercentage(10));
    } 
    
}
