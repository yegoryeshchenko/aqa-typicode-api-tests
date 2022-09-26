package com.api.services;

import com.api.entities.Comment;
import com.api.entities.Post;
import com.api.utils.ApiUtils;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.Arrays;
import java.util.List;

import static com.api.Constants.VALID_EMAIL_ADDRESS_REGEX;
import static com.api.Endpoints.COMMENTS;
import static com.api.Endpoints.COMMENTS_FOR_POSTS;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

@Slf4j
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
    @Step("all emails in comments for user's posts should be valid")
    public void verifyThatAllUserEmailsInCommentsForUserPostsShouldBeValid(List<Post> posts) {
        log.info("all emails in comments for user's posts should be valid");
        EmailValidator validator = EmailValidator.getInstance();
        for (Post post : posts) {
            List<Comment> comments = getCommentsForUserPosts(post.getId());
            for (Comment comment : comments) {
                assertThat("Email format is wrong!", validator.isValid(comment.getEmail()), is(true));
            }
        }
    }

    // validate email with regexp
    @Step("all emails in user's comments should correspond the template")
    public void verifyAllEmailsInUserCommentsCorrespondTheTemplate(List<Post> posts) {
        log.info("all emails in user's comments should correspond the template in the posts");
        for (Post post : posts) {
            List<Comment> comments = getCommentsForUserPosts(post.getId());
            for (Comment comment : comments) {
                assertThat("Email format is wrong!",
                        utils.validateEmail(VALID_EMAIL_ADDRESS_REGEX, comment.getEmail()), is(true));
            }
        }
    }

    @Step("comments response should follow the json schema from {1} file")
    public void commentsResponseShouldFollowTheJsonSchema(List<Post> posts, String fileName) {
        log.info("comments response should follow the json schema from file");
        for (Post post : posts) {
            Response response = getCommentsForPost(post.getId());
            utils.responseShouldFollowJsonSchemaInTheFile(response, fileName);
        }
    }

    @Step("get all existing comments")
    public List<Comment> getAllComments() {
        log.info("get all comments");
        return Arrays.asList(setUp().get(COMMENTS).as(Comment[].class));
    }

    @Step("number of comments should not exceed maximum")
    public void verifyNumberOfCommentsDoesNotExceedMaximum(int max) {
        log.info("number of comments should not exceed maximum");
        assertThat(getAllComments().size(), lessThanOrEqualTo(max));
    }

}
