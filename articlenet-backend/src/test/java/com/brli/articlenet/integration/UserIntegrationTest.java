package com.brli.articlenet.integration;

import com.brli.articlenet.constants.ValidationConstants;
import com.brli.articlenet.dto.RegisterDTO;
import com.brli.articlenet.model.Result;
import com.brli.articlenet.utils.StringUtil;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.brli.articlenet.constants.ValidationConstants.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class UserIntegrationTest {
    @Autowired
    private WebTestClient webTestClient;

    @Test
    void canRegisterUser() {
        // random generation of username and password
        String username = StringUtil.randomUsername(MIN_USERNAME_LENGTH, MAX_USERNAME_LENGTH);
        String password = StringUtil.randomPassword(MIN_PASSWORD_LENGTH, MAX_PASSWORD_LENGTH);

        // create a registerDTO
        RegisterDTO registerDTO = new RegisterDTO(username, password);

        // send post request to register API
        ParameterizedTypeReference<Result<String>> responseType =
                new ParameterizedTypeReference<>() {
                };

        webTestClient.post()
                .uri("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(registerDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody(responseType)
                .value(result -> {
                    assertEquals(0, result.getCode());
                    assertEquals("success", result.getMessage());
                    assertNull(result.getData());
                });
    }
}
