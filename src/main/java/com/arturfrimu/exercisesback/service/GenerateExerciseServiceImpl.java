package com.arturfrimu.exercisesback.service;
import com.arturfrimu.exercisesback.dao.ExerciseDAOImpl;
import com.arturfrimu.exercisesback.enumeration.ExerciseComparison;
import com.arturfrimu.exercisesback.enumeration.ExerciseSumPosition;
import com.arturfrimu.exercisesback.enumeration.ExerciseType;
import com.arturfrimu.exercisesback.controller.exercise.Exercise;
import com.arturfrimu.exercisesback.controller.response.ExerciseResponse;
import com.arturfrimu.exercisesback.repository.ExerciseConfigurationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.arturfrimu.exercisesback.enumeration.Status.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenerateExerciseServiceImpl implements GenerateExerciseService {
    private final ExerciseDAOImpl exerciseDAO;
    private final RandomNumberGenerator<Integer> randomIntGenerator;
    private final ExerciseConfigurationService exerciseConfigurationService;

    @Override
    public ExerciseResponse generate() {
        ExerciseConfigurationRepository.CurrentExerciseConfiguration currentExerciseConfiguration =
                exerciseConfigurationService.getCurrentExerciseConfiguration();

        int min = currentExerciseConfiguration.min();
        int max = currentExerciseConfiguration.max();

        if (min > max) throw new RuntimeException("Min nu poate fi mai mare ca max");

        int first = randomIntGenerator.generate(min, max);
        int second = randomIntGenerator.generate(min, max);

        UUID exerciseId = UUID.randomUUID();

        while (exerciseDAO.getExercise(exerciseId).isPresent()) { // folosim isPresent de la optional in loc de != null
            exerciseId = UUID.randomUUID();
        }

        int temp;

        if (ExerciseType.SUM.name().equalsIgnoreCase(currentExerciseConfiguration.type())) {
            if (ExerciseSumPosition.RIGHT.name().equalsIgnoreCase(currentExerciseConfiguration.position())) {

                String sum = first + " + " + second + " = ?";
                String result = String.valueOf(first + second);
                exerciseDAO.save(new Exercise(exerciseId, sum, result, UNSOLVED));
                return new ExerciseResponse(exerciseId, sum, UNSOLVED);

            } else if (ExerciseSumPosition.CENTER.name().equalsIgnoreCase(currentExerciseConfiguration.position())) {

                if (first > second) {
                    temp = first;
                    first = second;
                    second = temp;
                }

                String sum = first + " + ? = " + second;
                String result = String.valueOf(second - first);
                exerciseDAO.save(new Exercise(exerciseId, sum, result, UNSOLVED));
                return new ExerciseResponse(exerciseId, sum, UNSOLVED);

            } else if (ExerciseSumPosition.LEFT.name().equalsIgnoreCase(currentExerciseConfiguration.position())) {

                if (first > second) {
                    temp = first;
                    first = second;
                    second = temp;
                }

                String sum = "? + " + first + " = " + second;
                String result = String.valueOf(second - first);
                exerciseDAO.save(new Exercise(exerciseId, sum, result, UNSOLVED));
                return new ExerciseResponse(exerciseId, sum, UNSOLVED);

            } else {
                throw new RuntimeException("Poziția specificată nu este recunoscută: " + currentExerciseConfiguration.position());

            }

        } else if (ExerciseType.DIFFERENCE.name().equalsIgnoreCase(currentExerciseConfiguration.type())) {
            if (ExerciseSumPosition.RIGHT.name().equalsIgnoreCase(currentExerciseConfiguration.position())) {

                if (second > first) {
                    temp = first;
                    first = second;
                    second = temp;
                }

                String difference = first + " - " + second + " = ?";
                String result = String.valueOf(first - second);
                exerciseDAO.save(new Exercise(exerciseId, difference, result, UNSOLVED));
                return new ExerciseResponse(exerciseId, difference, UNSOLVED);

            } else if (ExerciseSumPosition.CENTER.name().equalsIgnoreCase(currentExerciseConfiguration.position())) {

                if (second > first) {
                    temp = first;
                    first = second;
                    second = temp;
                }

                String difference = first + " - ? = " + second;
                String result = String.valueOf(first - second);
                exerciseDAO.save(new Exercise(exerciseId, difference, result, UNSOLVED));
                return new ExerciseResponse(exerciseId, difference, UNSOLVED);

            } else if (ExerciseSumPosition.LEFT.name().equalsIgnoreCase(currentExerciseConfiguration.position())) {

                String difference = "? - " + first + " = " + second;
                String result = String.valueOf(first + second);
                exerciseDAO.save(new Exercise(exerciseId, difference, result, UNSOLVED));
                return new ExerciseResponse(exerciseId, difference, UNSOLVED);

            } else {
                throw new RuntimeException("Poziția specificată nu este recunoscută: " + currentExerciseConfiguration.position());

            }
        } else if (ExerciseType.MULTIPLICATION.name().equalsIgnoreCase(currentExerciseConfiguration.type())) {

            String multiplication = first + " * " + second + " = ?";
            String result = String.valueOf(first * second);
            exerciseDAO.save(new Exercise(exerciseId, multiplication, result, UNSOLVED));
            return new ExerciseResponse(exerciseId, multiplication, UNSOLVED);

        } else if (ExerciseType.DIVISION.name().equalsIgnoreCase(currentExerciseConfiguration.type())) {

            if (first < second) {
                temp = first;
                first = second;
                second = temp;
            }

            String division = first + " / " + second + " = ?";
            String result = String.valueOf(first / second);
            exerciseDAO.save(new Exercise(exerciseId, division, result, UNSOLVED));
            return new ExerciseResponse(exerciseId, division, UNSOLVED);

        } else if (ExerciseType.COMPARISON.name().equalsIgnoreCase(currentExerciseConfiguration.type())) {

            if (ExerciseComparison.ONE.name().equalsIgnoreCase(currentExerciseConfiguration.position())) {
                String comparison = first + " ? " + second;

                String result = "=";

                if (first > second) {
                    result = ">";
                } else if (first < second) {
                    result = "<";
                }

                exerciseDAO.save(new Exercise(exerciseId, comparison, result, UNSOLVED));
                return new ExerciseResponse(exerciseId, comparison, UNSOLVED);

            } else if (ExerciseComparison.TWO.name().equalsIgnoreCase(currentExerciseConfiguration.position())) {

                int third = randomIntGenerator.generate(min, max);
                String comparison = first + " ? " + second + " ? " + third;

                String result1 = "=";
                String result2 = "=";

                if (first > second) {
                    result1 = ">";
                } else if (first < second) {
                    result1 = "<";
                }

                if (second > third) {
                    result2 = ">";
                } else if (second < third) {
                    result2 = "<";
                }

                String combinedResult = result1 + "|" + result2;

                exerciseDAO.save(new Exercise(exerciseId, comparison, combinedResult, UNSOLVED));
                return new ExerciseResponse(exerciseId, comparison, UNSOLVED);
            } else {
                throw new RuntimeException("Poziția specificată nu este recunoscută: " + currentExerciseConfiguration.position());

            }
        } else {
            throw new RuntimeException("Poziția specificată nu este recunoscută: " + currentExerciseConfiguration.position());

        }
    }
}
