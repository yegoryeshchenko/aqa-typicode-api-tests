package com.api.utils;

import io.restassured.response.Response;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class ApiUtils {

    public void responseShouldFollowJsonSchemaInTheFile(Response response, String fileName) {
        response.then().body(matchesJsonSchemaInClasspath(fileName));
    }

    public boolean validateEmail(Pattern regexp, String emailStr) {
        Matcher matcher = regexp.matcher(emailStr);
        return matcher.find();
    }

}
