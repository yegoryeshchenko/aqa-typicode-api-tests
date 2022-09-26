package com.api.services;

import com.api.entities.User;
import io.restassured.response.Response;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Step;

import java.util.Arrays;
import java.util.List;

import static com.api.Endpoints.USERS;
import static com.api.SessionVariables.USER_OBJECT;
import static org.apache.http.HttpStatus.SC_OK;

public class UserApiService extends AbstractService {
    public Response getUsers() {
        Response response = setUp().get(USERS);
        response.then().statusCode(SC_OK);
        return response;
    }

    @Step("search for user with name {0}")
    public void getUserByUsername(String username) {
        List<User> users = Arrays.asList(getUsers().as(User[].class));
        User user = getUserIdByUsername(users, username);
        Serenity.setSessionVariable(USER_OBJECT).to(user);
    }

    private User getUserIdByUsername(List<User> users, String username) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username)).findFirst().get();
    }

}
