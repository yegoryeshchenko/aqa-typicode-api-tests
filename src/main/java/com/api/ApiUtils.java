package com.api;

import io.restassured.response.Response;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class ApiUtils {

    public void responseShouldFollowJsonSchemaInTheFile(Response response, String fileName) {
        response.then().body(matchesJsonSchemaInClasspath(fileName));
    }

}
