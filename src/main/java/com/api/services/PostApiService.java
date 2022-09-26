package com.api.services;

import com.api.entities.User;
import io.restassured.response.Response;
import net.serenitybdd.core.Serenity;

import static com.api.Endpoints.POSTS;
import static com.api.SessionVariables.USER_OBJECT;
import static org.apache.http.HttpStatus.SC_OK;

public class PostApiService extends AbstractService{

    public Response getPostsForUser() {
        User user = Serenity.sessionVariableCalled(USER_OBJECT);
        Response response = setUp()
                .params("userId", user.getId())
                .get(POSTS);
        response.then().statusCode(SC_OK);
        return response;
    }

}
