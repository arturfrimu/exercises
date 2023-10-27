package com.arturfrimu.exercisesback.controller;

import com.arturfrimu.exercisesback.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import static com.arturfrimu.exercisesback.controller.ExerciseGeneratorControllerV4.ExerciseSumIncognitePosition.*;
import static com.arturfrimu.exercisesback.controller.ExerciseGeneratorControllerV4.ExerciseType.*;

@CrossOrigin("*")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v4/exercise-generator")
public class ExerciseGeneratorControllerV4 {

    private static final Random random = new Random();
    private static final Map<Long, Exercise> exercises = new HashMap<>();

    @GetMapping
    public ResponseEntity<ExerciseResponse> generateExercise(
            @RequestParam(name = "type") String type,
            @RequestParam(name = "position") String position,
            @RequestParam(name = "min") Integer min,
            @RequestParam(name = "max") Integer max
    ) {
        log.info("Ai intrat in generateExercise cu tip : {} si pozitia : {}", type, position);
        long exerciseId = random.nextLong();
        int first = random.nextInt((max - min) + 1) + min;
        int second = random.nextInt((max - min) + 1) + min;

        int temp = 0;

        if (SUM.name().equalsIgnoreCase(type)) {
            if (RIGHT.name().equalsIgnoreCase(position)) {

                if (first > second) {
                    temp = first;
                    first = second;
                    second = temp;
                }

                String sum = first + " + " + second + " = ?";
                int result = first + second;
                exercises.put(exerciseId, new Exercise(exerciseId, sum, result));
                return ResponseEntity.ok(new ExerciseResponse(exerciseId, sum));
            } else if (CENTER.name().equalsIgnoreCase(position)) {

                if (first > second) {
                    temp = first;
                    first = second;
                    second = temp;
                }

                String sum = first + " + ? = " + second;
                int result = second - first;
                exercises.put(exerciseId, new Exercise(exerciseId, sum, result));
                return ResponseEntity.ok(new ExerciseResponse(exerciseId, sum));
            } else if (LEFT.name().equalsIgnoreCase(position)) {

                if (first > second) {
                    temp = first;
                    first = second;
                    second = temp;
                }

                String sum = "? + " + first + " = " + second;
                int result = second - first;
                exercises.put(exerciseId, new Exercise(exerciseId, sum, result));
                return ResponseEntity.ok(new ExerciseResponse(exerciseId, sum));
            } else {
                return ResponseEntity.badRequest().build();
            }
        } else if (DIFFERENCE.name().equalsIgnoreCase(type)) {

            if (second > first) {
                temp = first;
                first = second;
                second = temp;
            }

            String difference = first + " - " + second + " = ?";
            int result = first - second;
            exercises.put(exerciseId, new Exercise(exerciseId, difference, result));
            return ResponseEntity.ok(new ExerciseResponse(exerciseId, difference));
        } else if (MULTIPLICATION.name().equalsIgnoreCase(type)) {
            String multiplication = first + " * " + second + " = ?";
            int result = first * second;
            exercises.put(exerciseId, new Exercise(exerciseId, multiplication, result));
            return ResponseEntity.ok(new ExerciseResponse(exerciseId, multiplication));
        } else if (DIVISION.name().equalsIgnoreCase(type)) {
            String division = first + " / " + second + " = ?";
            int result = first / second;
            exercises.put(exerciseId, new Exercise(exerciseId, division, result));
            return ResponseEntity.ok(new ExerciseResponse(exerciseId, division));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<Boolean> verify(@RequestBody VerifyRequest verifyRequest) {
        log.info("Ai intrat in generateExercise cu id : {}", verifyRequest.id);
        Exercise exercise = exercises.get(verifyRequest.id());

        if (Objects.isNull(exercise)) {
            throw new ResourceNotFoundException("Exercise not found with id: %d".formatted(verifyRequest.id));
        }

        boolean isCorrect = exercise.verify(verifyRequest.userInput);
        return ResponseEntity.ok(isCorrect);
    }

    // @formatter:off
    public record ExerciseResponse(Long id, String expression) {}
    public record Exercise(Long id, String expression, Integer result) {
        private boolean verify(final Integer requestResult) {
            return this.result.equals(requestResult);
        }
    }
    public record VerifyRequest(Long id, Integer userInput) {}

    public enum ExerciseType {
        SUM, DIFFERENCE, MULTIPLICATION, DIVISION;
    }

    public enum ExerciseSumIncognitePosition {
        LEFT, CENTER, RIGHT;
    }
}
