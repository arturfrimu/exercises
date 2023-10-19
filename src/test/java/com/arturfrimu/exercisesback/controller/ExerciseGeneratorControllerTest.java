package com.arturfrimu.exercisesback.controller;

import com.arturfrimu.exercisesback.common.BaseRestTemplate;
import com.arturfrimu.exercisesback.controller.ExerciseGeneratorControllerV1.ExerciseResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.RequestEntity.get;
import static org.springframework.http.RequestEntity.post;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class ExerciseGeneratorControllerTest {

    @LocalServerPort
    private int PORT;
    private final String BASE_URL = "http://localhost:%d/api/v1/exercise-generator";

    @Autowired
    private BaseRestTemplate restTemplate;

    @Test
    void generateExercise() {
        var response = restTemplate.exchange(get(BASE_URL.formatted(PORT) + "?type=sum").build(), EXERCISE);

        assertThat(response).isNotNull();
        ExerciseResponse responseBody = response.getBody();
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.expression()).contains("+");
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();

        ResponseEntity<Boolean> exchange = restTemplate.exchange(
                post(BASE_URL.formatted(PORT))
                        .body(new ExerciseGeneratorControllerV1.VerifyRequest(responseBody.id(), 6)), VERIFY);

        assertThat(exchange).isNotNull();
        assertThat(exchange.getBody()).isNotNull();
        assertThat(exchange.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(exchange.getBody()).isTrue();
    }

    // @formatter:off
    static final ParameterizedTypeReference<ExerciseResponse> EXERCISE = new ParameterizedTypeReference<>() {};
    static final ParameterizedTypeReference<Boolean> VERIFY = new ParameterizedTypeReference<>() {};
}