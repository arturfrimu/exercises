package com.arturfrimu.exercisesback.controller;

import com.arturfrimu.exercisesback.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.arturfrimu.exercisesback.controller.ExerciseGeneratorControllerV6.Status.*;
import static java.math.MathContext.DECIMAL32;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@CrossOrigin(origins = "*", methods = {POST, GET, PUT, PATCH})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v6/exercise-generator")
public class ExerciseGeneratorControllerV6 {

    private final RandomNumberGenerator<Integer> randomIntGenerator;

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
            exerciseId = UUID.randomUUID();
        }

        int temp;

        if (ExerciseType.SUM.name().equalsIgnoreCase(type)) {
            if (ExerciseSumPosition.RIGHT.name().equalsIgnoreCase(position)) {

                String sum = first + " + " + second + " = ?";
                String result = String.valueOf(first + second);
                exercises.put(exerciseId, new Exercise(exerciseId, sum, result, UNSOLVED));
                log.info("Exercise {} {} {}", exerciseId, sum, result);
                log.info("exercises {}", exercises);
                return ResponseEntity.ok(new ExerciseResponse(exerciseId, sum, UNSOLVED));

            } else if (ExerciseSumPosition.CENTER.name().equalsIgnoreCase(position)) {

                if (first > second) {
                    temp = first;
                    first = second;
                    second = temp;
                }

                String sum = first + " + ? = " + second;
                String result = String.valueOf(second - first);
                exercises.put(exerciseId, new Exercise(exerciseId, sum, result, UNSOLVED));
                log.info("Exercise {} {} {}", exerciseId, sum, result);
                log.info("exercises {}", exercises);
                return ResponseEntity.ok(new ExerciseResponse(exerciseId, sum, UNSOLVED));

            } else if (ExerciseSumPosition.LEFT.name().equalsIgnoreCase(position)) {

                if (first > second) {
                    temp = first;
                    first = second;
                    second = temp;
                }

                String sum = "? + " + first + " = " + second;
                String result = String.valueOf(second - first);
                exercises.put(exerciseId, new Exercise(exerciseId, sum, result, UNSOLVED));
                log.info("Exercise {} {} {}", exerciseId, sum, result);
                log.info("exercises {}", exercises);
                return ResponseEntity.ok(new ExerciseResponse(exerciseId, sum, UNSOLVED));

            } else {
                return ResponseEntity.badRequest().build();
            }

        } else if (ExerciseType.DIFFERENCE.name().equalsIgnoreCase(type)) {
            if (ExerciseSumPosition.RIGHT.name().equalsIgnoreCase(position)) {

                if (second > first) {
                    temp = first;
                    first = second;
                    second = temp;
                }

                String difference = first + " - " + second + " = ?";
                String result = String.valueOf(first - second);
                exercises.put(exerciseId, new Exercise(exerciseId, difference, result, UNSOLVED));
                log.info("Exercise {} {} {}", exerciseId, difference, result);
                log.info("exercises {}", exercises);
                return ResponseEntity.ok(new ExerciseResponse(exerciseId, difference, UNSOLVED));

            } else if (ExerciseSumPosition.CENTER.name().equalsIgnoreCase(position)) {

                if (second > first) {
                    temp = first;
                    first = second;
                    second = temp;
                }

                String difference = first + " - ? = " + second;
                String result = String.valueOf(first - second);
                exercises.put(exerciseId, new Exercise(exerciseId, difference, result, UNSOLVED));
                log.info("Exercise {} {} {}", exerciseId, difference, result);
                log.info("exercises {}", exercises);
                return ResponseEntity.ok(new ExerciseResponse(exerciseId, difference, UNSOLVED));

            } else if (ExerciseSumPosition.LEFT.name().equalsIgnoreCase(position)) {

                String difference = "? - " + first + " = " + second;
                String result = String.valueOf(first + second);
                exercises.put(exerciseId, new Exercise(exerciseId, difference, result, UNSOLVED));
                log.info("Exercise {} {} {}", exerciseId, difference, result);
                log.info("exercises {}", exercises);
                return ResponseEntity.ok(new ExerciseResponse(exerciseId, difference, UNSOLVED));

            } else {
                return ResponseEntity.badRequest().build();
            }
        } else if (ExerciseType.MULTIPLICATION.name().equalsIgnoreCase(type)) {

            String multiplication = first + " * " + second + " = ?";
            String result = String.valueOf(first * second);
            exercises.put(exerciseId, new Exercise(exerciseId, multiplication, result, UNSOLVED));
            log.info("Exercise {} {} {}", exerciseId, multiplication, result);
            log.info("exercises {}", exercises);
            return ResponseEntity.ok(new ExerciseResponse(exerciseId, multiplication, UNSOLVED));

        } else if (ExerciseType.DIVISION.name().equalsIgnoreCase(type)) {

            if (first < second) {
                temp = first;
                first = second;
                second = temp;
            }

            String division = first + " / " + second + " = ?";
            String result = String.valueOf(first / second);
            exercises.put(exerciseId, new Exercise(exerciseId, division, result, UNSOLVED));
            log.info("Exercise {} {} {}", exerciseId, division, result);
            log.info("exercises {}", exercises);
            return ResponseEntity.ok(new ExerciseResponse(exerciseId, division, UNSOLVED));

        } else if (ExerciseType.COMPARISON.name().equalsIgnoreCase(type)) {

            if (ExerciseComparison.ONE.name().equalsIgnoreCase(position)) {
                String comparison = first + " ? " + second;

                String result = "=";

                if (first > second) {
                    result = ">";
                } else if (first < second) {
                    result = "<";
                }

                exercises.put(exerciseId, new Exercise(exerciseId, comparison, result, UNSOLVED));
                log.info("Exercise {} {} {}", exerciseId, comparison, result);
                log.info("exercises {}", exercises);
                return ResponseEntity.ok(new ExerciseResponse(exerciseId, comparison, UNSOLVED));

            } else if (ExerciseComparison.TWO.name().equalsIgnoreCase(position)) {

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

                String combinedResult = result1 + " | "  + result2;

                exercises.put(exerciseId, new Exercise(exerciseId, comparison, combinedResult, UNSOLVED));
                log.info("Exercise {} {} {}", exerciseId, comparison, combinedResult);
                log.info("exercises {}", exercises);
                return ResponseEntity.ok(new ExerciseResponse(exerciseId, comparison, UNSOLVED));
            } else {
                return ResponseEntity.badRequest().build();
            }
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

        if (isCorrect) {
            Exercise correctExercise = exercise.markAs(Status.CORRECT);
            exercises.put(correctExercise.id, correctExercise);
        } else {
            Exercise errorExercise = exercise.markAs(ERROR);
            exercises.put(errorExercise.id, errorExercise);
        }

        return ResponseEntity.ok(isCorrect);
    }

    @GetMapping("/exercises")
    public ResponseEntity<List<ExerciseResponse>> getAll() {
        List<ExerciseResponse> allExercises = exercises.values().stream()
                .map(exercise -> new ExerciseResponse(exercise.id, exercise.expression, exercise.status))
                .toList();
        return ResponseEntity.ok(allExercises);
    }

    @GetMapping("/exercises/{id}")
    public ResponseEntity<ExerciseResponse> getById(@PathVariable UUID id) {
        Exercise exercise = exercises.get(id);
        return ResponseEntity.ok(new ExerciseResponse(exercise.id, exercise.expression, exercise.status));
    }

    @GetMapping("/percentage")
    public ResponseEntity<PercentageResponse> getPercentage() {
        int exercisesSize = exercises.size();

        if (exercisesSize == 0) {
            return ResponseEntity.ok(new PercentageResponse("0", "0", "0"));
        }

        Map<Status, List<Exercise>> percentage = exercises.values()
                .stream()
                .collect(Collectors.groupingBy(
                        Exercise::status,
                        Collectors.mapping(
                                exercise -> exercise,
                                Collectors.toList()
                        )
                ));

        BigDecimal totalPercentage = BigDecimal.valueOf(100);

        int totalCorrectExercises = percentage.get(CORRECT).size();
        int totalErrorExercises = percentage.get(ERROR).size();

        BigDecimal correctPercentage = BigDecimal.valueOf(totalCorrectExercises)
                .multiply(totalPercentage)
                .divide(BigDecimal.valueOf(exercisesSize), DECIMAL32);

        BigDecimal errorPercentage = BigDecimal.valueOf(totalErrorExercises)
                .multiply(totalPercentage)
                .divide(BigDecimal.valueOf(exercisesSize), DECIMAL32);

        String correctExercisesPercent = correctPercentage.toString();
        String errorExercisesPercent = errorPercentage.toString();
        String unsolvedPercentage = totalPercentage.subtract(correctPercentage).subtract(errorPercentage).toString();

        return ResponseEntity.ok(new PercentageResponse(correctExercisesPercent, errorExercisesPercent, unsolvedPercentage));
    }

    // @formatter:off
    public record PercentageResponse(String success, String error,  String unsolved) {}
    public record ExerciseResponse(UUID id, String expression, Status status) {}
    public record Exercise(UUID id, String expression, String result, Status status) {
        private boolean verify(final String requestResult) {
            return this.result.equals(requestResult);
        }

        public Exercise markAs(Status status) {
            return new Exercise(id ,expression, result, status);
        }
    }
    public record VerifyRequest(UUID id, String userInput) {}

    public enum ExerciseType {
        SUM, DIFFERENCE, MULTIPLICATION, DIVISION, COMPARISON
    }

    public enum ExerciseSumPosition {
        LEFT, CENTER, RIGHT;
    }

    public enum ExerciseComparison {
        ONE, TWO;
    }
    
    public enum Status { 
        UNSOLVED, CORRECT, ERROR 
    }
}
