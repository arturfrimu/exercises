package com.arturfrimu.exercisesback.controller;

import com.arturfrimu.exercisesback.common.BaseRestTemplate;
import com.arturfrimu.exercisesback.controller.ExerciseGeneratorControllerV6.ExerciseResponse;
import com.arturfrimu.exercisesback.controller.ExerciseGeneratorControllerV6.VerifyRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Stream;

import static com.arturfrimu.exercisesback.controller.ExerciseGeneratorControllerV6.Status.CORRECT;
import static com.arturfrimu.exercisesback.controller.ExerciseGeneratorControllerV6.Status.UNSOLVED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.RequestEntity.get;
import static org.springframework.http.RequestEntity.post;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class ExerciseGeneratorControllerV6Test {

    @LocalServerPort
    private int PORT;

    @Autowired
    private BaseRestTemplate restTemplate;

    @MockBean
    private RandomNumberGenerator<Integer> randomNumberGeneratorMock;

    @MethodSource("generateExerciseArgumentsProvider")
    @ParameterizedTest
    void generateExercise(final String type, final String position, final int firstNumberMock, final int secondNumberMock, final String expected, final int verify) {
        String BASE_URL = "http://localhost:%d/api/v6/exercise-generator?type=%s&position=%s&min=%d&max=%d";

        when(randomNumberGeneratorMock.generate(anyInt(), anyInt())).thenReturn(firstNumberMock).thenReturn(secondNumberMock);

        int min = 1;
        int max = 5;
        var response = restTemplate.exchange(get(BASE_URL.formatted(PORT, type, position, min, max)).build(), EXERCISE);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        ExerciseResponse generatedExercice = response.getBody();
        assertThat(generatedExercice).isNotNull();
        assertThat(generatedExercice.expression()).isEqualTo(expected);
        assertThat(generatedExercice.status()).isEqualTo(UNSOLVED);


        String BASE_URL_VERIFY = "http://localhost:%d/api/v6/exercise-generator";
        ResponseEntity<Boolean> exchange = restTemplate.exchange(
                post(BASE_URL_VERIFY.formatted(PORT))
                        .body(new VerifyRequest(generatedExercice.id(), verify)), VERIFY);

        assertThat(exchange).isNotNull();
        assertThat(exchange.getBody()).isNotNull();
        assertThat(exchange.getStatusCode().is2xxSuccessful()).isTrue();

        assertThat(exchange.getBody()).isTrue();

        ResponseEntity<ExerciseResponse> exerciseById = restTemplate.exchange(get("http://localhost:%d/api/v6/exercise-generator/exercises/%s".formatted(PORT, generatedExercice.id())).build(), EXERCISE);

        assertThat(exerciseById.getBody()).isNotNull();
        assertThat(exerciseById.getBody().status()).isEqualTo(CORRECT);

        ResponseEntity<List<ExerciseResponse>> allExercises = restTemplate.exchange(get("http://localhost:%d/api/v6/exercise-generator/exercises".formatted(PORT)).build(), EXERCISE_LIST);
        assertThat(allExercises.getBody()).isNotNull();
        allExercises.getBody().forEach(System.out::println);
    }

    @Test
    void getAllExercises() {
        String BASE_URL = "http://localhost:%d/api/v6/exercise-generator?type=%s&position=%s&min=%d&max=%d";

        for (int i = 0; i < 10; i++) {
            Integer first = new RandomNumberGenerator.RandomIntGenerator().generate(1, 10);
            Integer second = new RandomNumberGenerator.RandomIntGenerator().generate(1, 10);
            when(randomNumberGeneratorMock.generate(anyInt(), anyInt())).thenReturn(first).thenReturn(second);
            ResponseEntity<ExerciseResponse> generatedExercice = restTemplate.exchange(get(BASE_URL.formatted(PORT, "sum", "left", 1, 10)).build(), EXERCISE);
        }

        ResponseEntity<List<ExerciseResponse>> allExercises = restTemplate.exchange(get("http://localhost:%d/api/v6/exercise-generator/exercises".formatted(PORT)).build(), EXERCISE_LIST);
        assertThat(allExercises.getBody()).isNotNull();
        assertThat(allExercises.getBody()).hasSize(10);
        allExercises.getBody().forEach(System.out::println);
    }

    private static Stream<Arguments> generateExerciseArgumentsProvider() {
        return Stream.of(
                Arguments.arguments("sum", "right", 1, 2, "1 + 2 = ?", 3), // no swipe
                Arguments.arguments("sum", "right", 2, 1, "2 + 1 = ?", 3), // no swipe
                Arguments.arguments("sum", "center", 1, 2, "1 + ? = 2", 1),
                Arguments.arguments("sum", "center", 2, 1, "1 + ? = 2", 1),
                Arguments.arguments("sum", "left", 1, 2, "? + 1 = 2", 1),
                Arguments.arguments("sum", "left", 2, 1, "? + 1 = 2", 1),

                Arguments.arguments("difference", "right", 3, 2, "3 - 2 = ?", 1),
                Arguments.arguments("difference", "right", 2, 3, "3 - 2 = ?", 1),
                Arguments.arguments("difference", "center", 2, 1, "2 - ? = 1", 1),
                Arguments.arguments("difference", "center", 1, 2, "2 - ? = 1", 1),
                Arguments.arguments("difference", "left", 2, 1, "? - 2 = 1", 3), // no swipe
                Arguments.arguments("difference", "left", 1, 2, "? - 1 = 2", 3),  // no swipe

                Arguments.arguments("multiplication", "NOT SUPERTED", 2, 3, "2 * 3 = ?", 6), // no swipe
                Arguments.arguments("multiplication", "NOT SUPERTED", 3, 2, "3 * 2 = ?", 6), // no swipe

                Arguments.arguments("division", "NOT SUPERTED", 4, 2, "4 / 2 = ?", 2), // no swipe
                Arguments.arguments("division", "NOT SUPERTED", 2, 4, "4 / 2 = ?", 2),

                Arguments.arguments("comparison", "NOT SUPERTED", 0, 0, "0 ? 0", 0),
                Arguments.arguments("comparison", "NOT SUPERTED", 1, 0, "1 ? 0", 1),
                Arguments.arguments("comparison", "NOT SUPERTED", 0, 1, "0 ? 1", -1)
        );
    }

    // @formatter:off
    static final ParameterizedTypeReference<ExerciseResponse> EXERCISE = new ParameterizedTypeReference<>() {};
    static final ParameterizedTypeReference<List<ExerciseResponse>> EXERCISE_LIST = new ParameterizedTypeReference<>() {};
    static final ParameterizedTypeReference<Boolean> VERIFY = new ParameterizedTypeReference<>() {};
}