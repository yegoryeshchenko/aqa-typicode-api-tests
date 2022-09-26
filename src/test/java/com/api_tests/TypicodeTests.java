package com.api_tests;

import com.api.entities.Post;
import com.api.entities.User;
import com.api.services.CommentApiService;
import com.api.services.PostApiService;
import com.api.services.UserApiService;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static com.api.Constants.DELPHINE_USER_NAME;

@ExtendWith(SerenityJUnit5Extension.class)
public class TypicodeTests {

    private final UserApiService userApiService = new UserApiService();
    private final PostApiService postApiService = new PostApiService();
    private final CommentApiService commentApiService = new CommentApiService();

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
