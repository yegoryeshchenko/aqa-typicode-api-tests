package com.api.services;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.rest.SerenityRest;

public abstract class AbstractService {

    public RequestSpecification setUp() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
        return SerenityRest.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON);
    }

}
