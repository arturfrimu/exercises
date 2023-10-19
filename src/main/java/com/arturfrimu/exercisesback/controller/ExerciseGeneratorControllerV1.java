package com.arturfrimu.exercisesback.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@CrossOrigin("*")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/exercise-generator")
public class ExerciseGeneratorControllerV1 {

    private static final Random random = new Random();
    private static final Map<Long, Exercise> exercises = new HashMap<>();

    @GetMapping
    public ResponseEntity<ExerciseResponse> generateExercise(@RequestParam(name = "type") String type) {
        log.info("Ai intrat in generateExercise cu tip : " + type);
        long exerciseId = random.nextLong();
        int first = random.nextInt(20) + 1;
        int second = random.nextInt(20) + 1;

        if (ExerciseType.SUM.name().equalsIgnoreCase(type)) {
            String sum = first + " + " + second + " = ";
            int result = first + second;
            exercises.put(exerciseId, new Exercise(exerciseId, sum, result));
            return ResponseEntity.ok(new ExerciseResponse(exerciseId, sum));
        } else if (ExerciseType.DIFFERENCE.name().equalsIgnoreCase(type)) {
            String difference = first + " - " + second + " = ";
            int result = first - second;
            exercises.put(exerciseId, new Exercise(exerciseId, difference, result));
            return ResponseEntity.ok(new ExerciseResponse(exerciseId, difference));
        } else if (ExerciseType.MULTIPLICATION.name().equalsIgnoreCase(type)) {
            String multiplication = first + " * " + second + " = ";
            int result = first * second;
            exercises.put(exerciseId, new Exercise(exerciseId, multiplication, result));
            return ResponseEntity.ok(new ExerciseResponse(exerciseId, multiplication));
        } else if (ExerciseType.DIVISION.name().equalsIgnoreCase(type)) {
            String division = first + " / " + second + " = ";
            int result = first / second;
            exercises.put(exerciseId, new Exercise(exerciseId, division, result));
            return ResponseEntity.ok(new ExerciseResponse(exerciseId, division));
        } else {
           return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<Boolean> verify(@RequestBody VerifyRequest verifyRequest) {
        log.info("Ai intrat in generateExercise");
        Exercise exercise = exercises.get(verifyRequest.id());
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
}
