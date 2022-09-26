package com.api.services;

import com.api.ApiUtils;
import com.api.entities.Comment;
import com.api.entities.Post;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Step;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.Arrays;
import java.util.List;

import static com.api.Endpoints.COMMENTS;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CommentApiService extends AbstractService {

    ApiUtils utils = new ApiUtils();

    public Response getCommentsForPost(int postId) {
        Response response = setUp()
                .get(COMMENTS, postId);
        response.then().statusCode(SC_OK);
        return response;
    }

    private List<Comment> getCommentsForUserPosts(int postId) {
        return Arrays.asList(getCommentsForPost(postId).as(Comment[].class));
    }

    @Step("all emails in the posts should correspond the email template")
    public void verifyThatAllPostsForUserCorrespondsTheEmailTemplate(List<Post> posts) {
        EmailValidator validator = EmailValidator.getInstance();
        for (Post post : posts) {
            List<Comment> comments = getCommentsForUserPosts(post.getId());
            for (Comment comment : comments) {
                assertThat(validator.isValid(comment.getEmail()))
                        .as("Email template is wrong!")
                        .isTrue();
            }
        }
    }

    @Step("comments response should follow the json schema from {1} file")
    public void commentsResponseShouldFollowTheJsonSchema(List<Post> posts, String fileName) {
        for (Post post : posts) {
            Response response = getCommentsForPost(post.getId());
            utils.responseShouldFollowJsonSchemaInTheFile(response, fileName);
        }
    }

}
