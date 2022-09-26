package com.api.services;

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

    private PostApiService postApiService = new PostApiService();

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
    public void verifyThatAllPostsForUserCorrespondsTheTemplate() {
        List<Post> posts = postApiService.getAllPostsForUser();
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

}
