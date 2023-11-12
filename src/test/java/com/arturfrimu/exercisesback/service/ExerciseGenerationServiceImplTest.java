package com.arturfrimu.exercisesback.service;

import com.arturfrimu.exercisesback.controller.exercise.Exercise;
import com.arturfrimu.exercisesback.controller.response.PercentageResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

import static com.arturfrimu.exercisesback.controller.enumeration.Status.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ExerciseGenerationServiceImplTest {

    @Autowired
    private ExerciseGenerationServiceImpl exerciseGenerationService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        exerciseGenerationService.clear();
    }

    @MethodSource("getPercentageArgumentsProvider")
    @ParameterizedTest
    void getPercentage(final Map<UUID, Exercise> map, PercentageResponse percentageResponse) {
        exerciseGenerationService.put(map);

        PercentageResponse percentage = exerciseGenerationService.getPercentage();

        assertThat(percentage).isNotNull().isEqualTo(percentageResponse);
    }

    public static Stream<Arguments> getPercentageArgumentsProvider() {
        return Stream.of(
                Arguments.of(new HashMap<>(), new PercentageResponse("0", "0", "0", 0, 0, 0)),
                Arguments.of(Map.of(UUID.randomUUID(), new Exercise(UUID.randomUUID(), "", "", UNSOLVED)), new PercentageResponse("0", "0", "100", 0, 0, 1)),
                Arguments.of(Map.of(UUID.randomUUID(), new Exercise(UUID.randomUUID(), "", "", CORRECT)), new PercentageResponse("100", "0", "0", 1, 0, 1)),
                Arguments.of(Map.of(UUID.randomUUID(), new Exercise(UUID.randomUUID(), "", "", ERROR)), new PercentageResponse("0", "100", "0", 0, 1, 1)),
                Arguments.of(Map.of(
                        UUID.randomUUID(), new Exercise(UUID.randomUUID(), "", "", CORRECT),
                        UUID.randomUUID(), new Exercise(UUID.randomUUID(), "", "", CORRECT),
                        UUID.randomUUID(), new Exercise(UUID.randomUUID(), "", "", UNSOLVED),
                        UUID.randomUUID(), new Exercise(UUID.randomUUID(), "", "", ERROR)
                ), new PercentageResponse("50", "25", "25", 2, 1, 3)),
                Arguments.of(Map.of(
                        UUID.randomUUID(), new Exercise(UUID.randomUUID(), "", "", ERROR),
                        UUID.randomUUID(), new Exercise(UUID.randomUUID(), "", "", ERROR),
                        UUID.randomUUID(), new Exercise(UUID.randomUUID(), "", "", UNSOLVED),
                        UUID.randomUUID(), new Exercise(UUID.randomUUID(), "", "", CORRECT)
                ), new PercentageResponse("25", "50", "25", 1, 2, 3)),
                Arguments.of(Map.of(
                        UUID.randomUUID(), new Exercise(UUID.randomUUID(), "", "", UNSOLVED),
                        UUID.randomUUID(), new Exercise(UUID.randomUUID(), "", "", UNSOLVED),
                        UUID.randomUUID(), new Exercise(UUID.randomUUID(), "", "", ERROR),
                        UUID.randomUUID(), new Exercise(UUID.randomUUID(), "", "", CORRECT)
                ), new PercentageResponse("25", "25", "50", 1, 1, 3))
        );
    }
}