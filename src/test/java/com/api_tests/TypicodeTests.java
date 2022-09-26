package com.api_tests;

import com.api.entities.Post;
import com.api.entities.User;
import com.api.services.CommentApiService;
import com.api.services.PostApiService;
import com.api.services.UserApiService;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.api.Constants.DELPHINE_USER_NAME;

public class TypicodeTests {

    private final UserApiService userApiService = new UserApiService();
    private final PostApiService postApiService = new PostApiService();
    private final CommentApiService commentApiService = new CommentApiService();

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test
    public void emailsInTheCommentsShouldBeValid() {
        User user = userApiService.getUserByUsername(DELPHINE_USER_NAME);

        List<Post> posts = postApiService.getAllPostsForUser(user.getId());

        commentApiService.verifyThatAllUserEmailsInCommentsForUserPostsShouldBeValid(posts);
    }

    @Test
    public void emailsInTheCommentsShouldCorrespondTemplates() {
        User user = userApiService.getUserByUsername(DELPHINE_USER_NAME);

        List<Post> posts = postApiService.getAllPostsForUser(user.getId());

        commentApiService.verifyAllEmailsInUserCommentsCorrespondTheTemplate(posts);
    }

    @Test
    public void commentsResponseShouldFollowTheJSonSchema() {
        User user = userApiService.getUserByUsername(DELPHINE_USER_NAME);

        List<Post> posts = postApiService.getAllPostsForUser(user.getId());

        commentApiService
                .commentsResponseShouldFollowTheJsonSchema(posts, "json_objects/comments_response_schema.json");
    }

    @Test
    public void numberOfCommentsShouldNotExceedMaximum() {
        commentApiService.verifyNumberOfCommentsDoesNotExceedMaximum(500);
    }

}
