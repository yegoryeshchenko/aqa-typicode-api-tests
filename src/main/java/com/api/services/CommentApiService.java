package com.api.services;

import com.api.entities.User;
import io.restassured.response.Response;
import net.serenitybdd.core.Serenity;

import static com.api.Endpoints.*;
import static com.api.SessionVariables.USER_OBJECT;
import static org.apache.http.HttpStatus.SC_OK;

public class CommentApiService extends AbstractService{

  public Response getCommentsForPost(int postId) {
    Response response = setUp()
        .get(COMMENTS, postId);
    response.then().statusCode(SC_OK);
    return response;
  }

}
