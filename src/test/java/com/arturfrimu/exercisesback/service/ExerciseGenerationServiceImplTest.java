package com.arturfrimu.exercisesback.service;

import com.arturfrimu.exercisesback.dao.ExerciseDAOImpl;
import com.arturfrimu.exercisesback.dao.ExerciseRepository;
import com.arturfrimu.exercisesback.controller.exercise.Exercise;
import com.arturfrimu.exercisesback.controller.response.PercentageResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.stream.Stream;

import static com.arturfrimu.exercisesback.enumeration.Status.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ExerciseGenerationServiceImplTest {
    @Autowired
    private ExerciseRepository exerciseRepository;
    @Autowired
    private ExerciseDAOImpl exerciseDAO;
    @Autowired
    private FindPercentageExerciseService findPercentageExerciseService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        exerciseRepository.deleteAll();
    }

    @MethodSource("getPercentageArgumentsProvider")
    @ParameterizedTest
    void getPercentage(final List<Exercise> exercises, PercentageResponse percentageResponse) {
        exerciseDAO.saveAll(exercises);

        PercentageResponse percentage = findPercentageExerciseService.find();

        assertThat(percentage).isNotNull().isEqualTo(percentageResponse);
    }

    public static Stream<Arguments> getPercentageArgumentsProvider() {
        return Stream.of(
                Arguments.of(new ArrayList<>(), new PercentageResponse("0", "0", "0")),
                Arguments.of(List.of(new Exercise(UUID.randomUUID(), "", "", UNSOLVED)), new PercentageResponse("0", "0", "100")),
                Arguments.of(List.of(new Exercise(UUID.randomUUID(), "", "", CORRECT)), new PercentageResponse("100", "0", "0")),
                Arguments.of(List.of(new Exercise(UUID.randomUUID(), "", "", ERROR)), new PercentageResponse("0", "100", "0")),
                Arguments.of(List.of(
                        new Exercise(UUID.randomUUID(), "", "", CORRECT),
                        new Exercise(UUID.randomUUID(), "", "", CORRECT),
                        new Exercise(UUID.randomUUID(), "", "", UNSOLVED),
                        new Exercise(UUID.randomUUID(), "", "", ERROR)
                ), new PercentageResponse("50", "25", "25")),
                Arguments.of(List.of(
                        new Exercise(UUID.randomUUID(), "", "", ERROR),
                        new Exercise(UUID.randomUUID(), "", "", ERROR),
                        new Exercise(UUID.randomUUID(), "", "", UNSOLVED),
                        new Exercise(UUID.randomUUID(), "", "", CORRECT)
                ), new PercentageResponse("25", "50", "25")),
                Arguments.of(List.of(
                        new Exercise(UUID.randomUUID(), "", "", UNSOLVED),
                        new Exercise(UUID.randomUUID(), "", "", UNSOLVED),
                        new Exercise(UUID.randomUUID(), "", "", ERROR),
                        new Exercise(UUID.randomUUID(), "", "", CORRECT)
                ), new PercentageResponse("25", "25", "50"))
        );
    }
}