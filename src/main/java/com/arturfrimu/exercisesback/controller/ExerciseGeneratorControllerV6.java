package com.arturfrimu.exercisesback.controller;

import com.arturfrimu.exercisesback.controller.request.VerifyRequest;
import com.arturfrimu.exercisesback.controller.response.ExerciseResponse;
import com.arturfrimu.exercisesback.controller.response.PercentageResponse;
import com.arturfrimu.exercisesback.service.ExerciseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@CrossOrigin(origins = "*", methods = {POST, GET, PUT, PATCH})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v6/exercise-generator")
public class ExerciseGeneratorControllerV6 {

    private final ExerciseService exerciseGenerationService;

    @GetMapping
    public ResponseEntity<ExerciseResponse> generateExercise(
            @RequestParam(name = "type") String type,
            @RequestParam(name = "position") String position,
            @RequestParam(name = "min") Integer min,
            @RequestParam(name = "max") Integer max) {
        ExerciseResponse exerciseResponse = exerciseGenerationService.generateExercise();

        return ResponseEntity.ok(exerciseResponse);
    }

    @PostMapping
    public ResponseEntity<Boolean> verify(@RequestBody VerifyRequest verifyRequest) {
        boolean isCorrect = exerciseGenerationService.verifyExercise(verifyRequest);

        return ResponseEntity.ok(isCorrect);
    }

    @GetMapping("/exercises")
    public ResponseEntity<List<ExerciseResponse>> getAll() {
        List<ExerciseResponse> allExercises = exerciseGenerationService.getAllExercises();
        return ResponseEntity.ok(allExercises);
    }

    @GetMapping("/exercises/{id}")
    public ResponseEntity<ExerciseResponse> getById(@PathVariable UUID id) {
        ExerciseResponse exercise = exerciseGenerationService.getExerciseById(id);
        return ResponseEntity.ok(new ExerciseResponse(exercise.id(), exercise.expression(), exercise.status()));
    }

    @GetMapping("/percentage")
    public ResponseEntity<PercentageResponse> getPercentage() {
        PercentageResponse percentageResponse = exerciseGenerationService.getPercentage();
        return ResponseEntity.ok(percentageResponse);
    }
}

