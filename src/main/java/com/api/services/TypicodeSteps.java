package com.api.services;

import com.api.entities.Comment;
import com.api.entities.Post;
import com.api.entities.User;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Step;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.Arrays;
import java.util.List;

import static com.api.SessionVariables.USER_OBJECT;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TypicodeSteps {

    UserApiService userApiService = new UserApiService();
    PostApiService postApiService = new PostApiService();
    CommentApiService commentApiService = new CommentApiService();

    @Step("search for user with name {0}")
    public void getUserByUsername(String username) {
        List<User> users = Arrays.asList(userApiService.getUsers().as(User[].class));
        User user = getUserIdByUsername(users, username);
        Serenity.setSessionVariable(USER_OBJECT).to(user);
    }

    private User getUserIdByUsername(List<User> users, String username) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username)).findFirst().get();
    }

    @Step("get all posts for user")
    public List<Post> getAllPostsForUser() {
        return Arrays.asList(postApiService.getPostsForUser().as(Post[].class));
    }

    private List<Comment> getCommentsForUserPosts(int postId) {
        return Arrays.asList(commentApiService.getCommentsForPost(postId).as(Comment[].class));
    }

    @Step("all emails in the posts should correspond the email template")
    public void verifyThatAllPostsForUserCorrespondsTheTemplate() {
        List<Post> posts = getAllPostsForUser();
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
