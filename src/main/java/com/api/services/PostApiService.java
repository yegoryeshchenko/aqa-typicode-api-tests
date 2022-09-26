package com.api.services;

import com.api.entities.Post;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Step;

import java.util.Arrays;
import java.util.List;

import static com.api.Endpoints.POSTS;
import static org.apache.http.HttpStatus.SC_OK;

public class PostApiService extends AbstractService {

    @Step("get all posts for user by {0} id")
    public List<Post> getAllPostsForUser(int userId) {
        Response response = setUp()
                .params("userId", userId)
                .get(POSTS);
        response.then().statusCode(SC_OK);
        return Arrays.asList(response.as(Post[].class));
    }

}
