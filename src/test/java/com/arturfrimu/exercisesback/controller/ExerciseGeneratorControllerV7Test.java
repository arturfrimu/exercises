package com.arturfrimu.exercisesback.controller;

import com.arturfrimu.exercisesback.common.BaseRestTemplate;
import com.arturfrimu.exercisesback.controller.response.ExerciseResponse;
import com.arturfrimu.exercisesback.repository.ExerciseConfigurationRepository;
import com.arturfrimu.exercisesback.repository.ExerciseConfigurationRepository.ExerciseConfiguration;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static com.arturfrimu.exercisesback.enumeration.Status.UNSOLVED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.RequestEntity.get;
import static org.springframework.http.RequestEntity.post;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
class ExerciseGeneratorControllerV7Test {

    @LocalServerPort
    private int PORT;

    @Autowired
    private BaseRestTemplate restTemplate;

    @ParameterizedTest
    @MethodSource("generateExerciseArgumentsProvider")
    void generateExercise(final ExerciseConfiguration configuration, Predicate<String> predicate, final String simbol) {
        var response = restTemplate.exchange(post("http://localhost:%d/api/v7/exercise-generator/config".formatted(PORT)).body(configuration), new ParameterizedTypeReference<Void>() {
        });
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();

        for (int i = 0; i < 10; i++) {
            var response2 = restTemplate.exchange(get("http://localhost:%d/api/v7/exercise-generator".formatted(PORT)).build(), EXERCISE);
            assertThat(response2.getStatusCode().is2xxSuccessful()).isTrue();
            ExerciseResponse generatedExercice = response2.getBody();
            assertThat(generatedExercice).isNotNull();

            Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?"); // Regular expression for numbers

            String expression = generatedExercice.expression();

            Optional<Double> numberOutOfRange = pattern.matcher(expression).results()
                    .map(matchResult -> Double.parseDouble(matchResult.group()))
                    .filter(nr -> nr < configuration.range().min() || nr > configuration.range().max())
                    .findFirst();

            assertThat(numberOutOfRange).isEmpty();

            assertThat(expression).contains(simbol);
            assertThat(predicate.test(expression)).isTrue();

            assertThat(generatedExercice.status()).isEqualTo(UNSOLVED);
        }
    }

    public static Stream<Arguments> generateExerciseArgumentsProvider() {
        Predicate<String> left = (expression) -> expression.startsWith("?");
        Predicate<String> center = (expression) -> expression.contains("?") && !expression.startsWith("?") && !expression.endsWith("?");
        Predicate<String> right = (expression) -> expression.endsWith("?");

        return Stream.of(
                Arguments.of(new ExerciseConfiguration(List.of("sum"), List.of("left"), new ExerciseConfigurationRepository.Range(1, 5)), left, "+"),
                Arguments.of(new ExerciseConfiguration(List.of("sum"), List.of("center"), new ExerciseConfigurationRepository.Range(5, 10)), center, "+"),
                Arguments.of(new ExerciseConfiguration(List.of("sum"), List.of("right"), new ExerciseConfigurationRepository.Range(2, 8)), right, "+"),
                Arguments.of(new ExerciseConfiguration(List.of("difference"), List.of("left"), new ExerciseConfigurationRepository.Range(3, 12)), left, "-"),
                Arguments.of(new ExerciseConfiguration(List.of("difference"), List.of("center"), new ExerciseConfigurationRepository.Range(1, 2)), center, "-"),
                Arguments.of(new ExerciseConfiguration(List.of("difference"), List.of("right"), new ExerciseConfigurationRepository.Range(2, 4)), right, "-"),
                Arguments.of(new ExerciseConfiguration(List.of("multiplication"), List.of("right"), new ExerciseConfigurationRepository.Range(1, 4)), right, "*"),
                Arguments.of(new ExerciseConfiguration(List.of("division"), List.of("right"), new ExerciseConfigurationRepository.Range(3, 5)), right, "/"),
                Arguments.of(new ExerciseConfiguration(List.of("division"), List.of("right"), new ExerciseConfigurationRepository.Range(5, 7)), right, "/")
        );
    }

    // @formatter:off
    static final ParameterizedTypeReference<ExerciseResponse> EXERCISE = new ParameterizedTypeReference<>() {
    };
}