package com.api.services;

import com.api.entities.Comment;
import com.api.entities.Post;
import com.api.utils.ApiUtils;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Step;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.Arrays;
import java.util.List;

import static com.api.Constants.VALID_EMAIL_ADDRESS_REGEX;
import static com.api.Endpoints.COMMENTS;
import static com.api.Endpoints.COMMENTS_FOR_POSTS;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CommentApiService extends AbstractService {

    ApiUtils utils = new ApiUtils();

    public Response getCommentsForPost(int postId) {
        Response response = setUp()
                .get(COMMENTS_FOR_POSTS, postId);
        response.then().statusCode(SC_OK);
        return response;
    }

    private List<Comment> getCommentsForUserPosts(int postId) {
        return Arrays.asList(getCommentsForPost(postId).as(Comment[].class));
    }

    // validate email with EmailValidator
    @Step("all comments for user's posts should be valid")
    public void verifyThatAllUserEmailsInCommentsForUserPostsShouldBeValid(List<Post> posts) {
        EmailValidator validator = EmailValidator.getInstance();
        for (Post post : posts) {
            List<Comment> comments = getCommentsForUserPosts(post.getId());
            for (Comment comment : comments) {
                assertThat(validator.isValid(comment.getEmail()))
                        .as("Email format is wrong!")
                        .isTrue();
            }
        }
    }

    // validate email with regexp
    @Step("all emails in user's comments should correspond the template")
    public void verifyAllEmailsInUserCommentsCorrespondTheTemplate(List<Post> posts) {
        for (Post post : posts) {
            List<Comment> comments = getCommentsForUserPosts(post.getId());
            for (Comment comment : comments) {
                assertThat(utils.validateEmail(VALID_EMAIL_ADDRESS_REGEX, comment.getEmail()))
                        .as("Email format is wrong!")
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

    @Step("get all existing comments")
    public List<Comment> getAllComments() {
        return Arrays.asList(setUp().get(COMMENTS).as(Comment[].class));
    }

    @Step("number of comments should not exceed maximum")
    public void verifyNumberOfCommentsDoesNotExceedMaximum(int max) {
        assertThat(getAllComments().size())
                .isLessThanOrEqualTo(max);
    }

}
