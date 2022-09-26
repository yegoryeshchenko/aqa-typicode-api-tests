package com.api_tests;

import com.api.entities.Post;
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
    public void emailsInTheCommentsShouldCorrespondTemplates() {
        userApiService.getUserByUsername(DELPHINE_USER_NAME);
        List<Post> posts = postApiService.getAllPostsForUser();
        commentApiService.verifyThatAllPostsForUserCorrespondsTheTemplate();
    }

}
