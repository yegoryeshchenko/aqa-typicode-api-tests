package com.api.services;

import com.api.entities.User;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

import static com.api.Endpoints.USERS;
import static org.apache.http.HttpStatus.SC_OK;

@Slf4j
public class UserApiService extends AbstractService {

    public Response getUsers() {
        log.info("get users");
        Response response = setUp().get(USERS);
        response.then().statusCode(SC_OK);
        return response;
    }

    @Step("search for user with name {0}")
    public User getUserByUsername(String username) {
        log.info("search for user with name {}", username);
        List<User> users = Arrays.asList(getUsers().as(User[].class));
        return getUserByUsername(users, username);
    }

    private User getUserByUsername(List<User> users, String username) {
        log.info("get user by {} username", username);
        return users.stream()
                .filter(user -> user.getUsername().equals(username)).findFirst().get();
    }

}
