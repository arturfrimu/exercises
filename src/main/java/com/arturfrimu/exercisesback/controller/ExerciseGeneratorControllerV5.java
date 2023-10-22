package com.arturfrimu.exercisesback.controller;

import com.arturfrimu.exercisesback.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@CrossOrigin(origins = "*", methods = {POST, GET, PUT, PATCH})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v5/exercise-generator")
public class ExerciseGeneratorControllerV5 {

    private final RandomNumberGenerator<Integer> randomIntGenerator;
    private final RandomNumberGenerator<Long> randomLongGenerator;

    private static final Map<UUID, Exercise> exercises = new HashMap<>();

    @GetMapping
    public ResponseEntity<ExerciseResponse> generateExercise(
            @RequestParam(name = "type") String type,
            @RequestParam(name = "position") String position,
            @RequestParam(name = "min") Integer min,
            @RequestParam(name = "max") Integer max
    ) {
        log.info("Ai intrat in generateExercise cu tip : {} si pozitia : {}", type, position);

        int first = randomIntGenerator.generate(min, max);
        int second = randomIntGenerator.generate(min, max);

        UUID exerciseId = UUID.randomUUID();

        while (exercises.containsKey(exerciseId)) {
            exerciseId =  UUID.randomUUID();
        }

        int temp;

        if (ExerciseType.SUM.name().equalsIgnoreCase(type)) {
            if (ExerciseSumIncognitePosition.RIGHT.name().equalsIgnoreCase(position)) {

                String sum = first + " + " + second + " = ?";
                int result = first + second;
                exercises.put(exerciseId, new Exercise(exerciseId, sum, result));
                log.info("Exercise {} {} {}", exerciseId, sum, result);
                log.info("exercises {}", exercises);
                return ResponseEntity.ok(new ExerciseResponse(exerciseId, sum));

            } else if (ExerciseSumIncognitePosition.CENTER.name().equalsIgnoreCase(position)) {

                if (first > second) {
                    temp = first;
                    first = second;
                    second = temp;
                }

                String sum = first + " + ? = " + second;
                int result = second - first;
                exercises.put(exerciseId, new Exercise(exerciseId, sum, result));
                log.info("Exercise {} {} {}", exerciseId, sum, result);
                log.info("exercises {}", exercises);
                return ResponseEntity.ok(new ExerciseResponse(exerciseId, sum));

            } else if (ExerciseSumIncognitePosition.LEFT.name().equalsIgnoreCase(position)) {

                if (first > second) {
                    temp = first;
                    first = second;
                    second = temp;
                }

                String sum = "? + " + first + " = " + second;
                int result = second - first;
                exercises.put(exerciseId, new Exercise(exerciseId, sum, result));
                log.info("Exercise {} {} {}", exerciseId, sum, result);
                log.info("exercises {}", exercises);
                return ResponseEntity.ok(new ExerciseResponse(exerciseId, sum));

            } else {
                return ResponseEntity.badRequest().build();
            }

        } else if (ExerciseType.DIFFERENCE.name().equalsIgnoreCase(type)) {
            if (ExerciseSumIncognitePosition.RIGHT.name().equalsIgnoreCase(position)) {

                if (second > first) {
                    temp = first;
                    first = second;
                    second = temp;
                }

                String difference = first + " - " + second + " = ?";
                int result = first - second;
                exercises.put(exerciseId, new Exercise(exerciseId, difference, result));
                log.info("Exercise {} {} {}", exerciseId, difference, result);
                log.info("exercises {}", exercises);
                return ResponseEntity.ok(new ExerciseResponse(exerciseId, difference));

            } else if (ExerciseSumIncognitePosition.CENTER.name().equalsIgnoreCase(position)) {

                if (second > first) {
                    temp = first;
                    first = second;
                    second = temp;
                }

                String difference = first + " - ? = " + second;
                int result = first - second;
                exercises.put(exerciseId, new Exercise(exerciseId, difference, result));
                log.info("Exercise {} {} {}", exerciseId, difference, result);
                log.info("exercises {}", exercises);
                return ResponseEntity.ok(new ExerciseResponse(exerciseId, difference));

            } else if (ExerciseSumIncognitePosition.LEFT.name().equalsIgnoreCase(position)) {

                String difference = "? - " + first + " = " + second;
                int result = first + second;
                exercises.put(exerciseId, new Exercise(exerciseId, difference, result));
                log.info("Exercise {} {} {}", exerciseId, difference, result);
                log.info("exercises {}", exercises);
                return ResponseEntity.ok(new ExerciseResponse(exerciseId, difference));

            } else {
                return ResponseEntity.badRequest().build();
            }
        } else if (ExerciseType.MULTIPLICATION.name().equalsIgnoreCase(type)) {

            String multiplication = first + " * " + second + " = ?";
            int result = first * second;
            exercises.put(exerciseId, new Exercise(exerciseId, multiplication, result));
            log.info("Exercise {} {} {}", exerciseId, multiplication, result);
            log.info("exercises {}", exercises);
            return ResponseEntity.ok(new ExerciseResponse(exerciseId, multiplication));

        } else if (ExerciseType.DIVISION.name().equalsIgnoreCase(type)) {

            if (first < second) {
                temp = first;
                first = second;
                second = temp;
            }

            String division = first + " / " + second + " = ?";
            int result = first / second;
            exercises.put(exerciseId, new Exercise(exerciseId, division, result));
            log.info("Exercise {} {} {}", exerciseId, division, result);
            log.info("exercises {}", exercises);
            return ResponseEntity.ok(new ExerciseResponse(exerciseId, division));

        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<Boolean> verify(@RequestBody VerifyRequest verifyRequest) {
        log.info("verifyRequest {}", verifyRequest);
        log.info("exercises {}", exercises);

        Exercise exercise = exercises.get(verifyRequest.id());

        if (Objects.isNull(exercise)) {
            throw new ResourceNotFoundException("Exercise not found with id: %s".formatted(verifyRequest.id));
        }

        boolean isCorrect = exercise.verify(verifyRequest.userInput);
        return ResponseEntity.ok(isCorrect);
    }

    // @formatter:off
    public record ExerciseResponse(UUID id, String expression) {}
    public record Exercise(UUID id, String expression, Integer result) {
        private boolean verify(final Integer requestResult) {
            return this.result.equals(requestResult);
        }
    }
    public record VerifyRequest(UUID id, Integer userInput) {}

    public enum ExerciseType {
        SUM, DIFFERENCE, MULTIPLICATION, DIVISION;
    }

    public enum ExerciseSumIncognitePosition {
        LEFT, CENTER, RIGHT;
    }
}
