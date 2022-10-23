package com.controlup.automation.restapi;

import org.testng.annotations.Test;

import io.qameta.allure.*;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import static org.hamcrest.Matchers.*;

public class RestApiTest extends BaseApiTest {

    @Feature("API")
    @Story("API JSON")
    @Severity(SeverityLevel.BLOCKER)
    @Test(groups = {"api"})
    public void validateWeatherSchema() {
    	given().headers(RestWeather.HEADERS).
                queryParams("q", "20852").
    	        filter(new RestFilter(this.getClass().getName() + ".validateCorrectWeatherResponse")).
        when().get(RestWeather.URL).
        then().
            statusCode(200).
        	body(matchesJsonSchemaInClasspath("rest/weather-schema.json")).
            body("location.name", equalTo("Rockville"));
    }

}
