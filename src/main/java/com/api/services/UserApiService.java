package com.api.services;

import io.restassured.response.Response;

import static com.api.Endpoints.USERS;
import static org.apache.http.HttpStatus.SC_OK;

public class UserApiService extends AbstractService{
    public Response getUsers() {
        Response response = setUp().get(USERS);
        response.then().statusCode(SC_OK);
        return response;
    }

}
