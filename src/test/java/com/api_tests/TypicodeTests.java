package com.api_tests;

import com.api.entities.Post;
import com.api.services.TypicodeSteps;
import io.restassured.RestAssured;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

@ExtendWith(SerenityJUnit5Extension.class)
public class TypicodeTests {

    private TypicodeSteps steps = new TypicodeSteps();

    @Test
    public void emailsInTheCommentsShouldCorrespondTemplates() {
        steps.getUserByUsername("Delphine");
        List<Post> posts = steps.getAllPostsForUser();
        steps.verifyThatAllPostsForUserCorrespondsTheTemplate();
    }

}
