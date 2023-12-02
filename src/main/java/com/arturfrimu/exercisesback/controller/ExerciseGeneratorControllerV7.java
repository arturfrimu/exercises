package com.arturfrimu.exercisesback.controller;

import com.arturfrimu.exercisesback.controller.request.VerifyRequest;
import com.arturfrimu.exercisesback.controller.response.ExerciseResponse;
import com.arturfrimu.exercisesback.controller.response.PercentageResponse;
import com.arturfrimu.exercisesback.repository.ExerciseConfigurationRepository.ExerciseConfiguration;
import com.arturfrimu.exercisesback.service.ExerciseConfigurationService;
import com.arturfrimu.exercisesback.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.web.bind.annotation.RequestMethod.*;

//@CrossOrigin(origins = "*", methods = {POST, GET, PUT, PATCH})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v7/exercise-generator")
public class ExerciseGeneratorControllerV7 {
    private final GenerateExerciseService generateExerciseService;
    private final VerifyExerciseService verifyExerciseService;
    private final ListAllExercisesService listAllExercisesService;
    private final FindExerciseService findExerciseService;
    private final FindPercentageExerciseService findPercentageExerciseService;
    private final ExerciseConfigurationService exerciseConfigurationService;

    @PostMapping("/config")
    public ResponseEntity<?> config(@RequestBody ExerciseConfiguration configuration) {
        exerciseConfigurationService.setConfiguration(configuration);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<ExerciseResponse> generateExercise() {
        ExerciseResponse exerciseResponse = generateExerciseService.generate();

        return ResponseEntity.ok(exerciseResponse);
    }

    @PostMapping
    public ResponseEntity<Boolean> verify(@RequestBody VerifyRequest verifyRequest) {
        boolean isCorrect = verifyExerciseService.verify(verifyRequest);

        return ResponseEntity.ok(isCorrect);
    }

    @GetMapping("/exercises")
    public ResponseEntity<List<ExerciseResponse>> getAll() {
        List<ExerciseResponse> allExercises = listAllExercisesService.getAll();
        return ResponseEntity.ok(allExercises);
    }

    @GetMapping("/exercises/{id}")
    public ResponseEntity<ExerciseResponse> getById(@PathVariable UUID id) {
        ExerciseResponse exercise = findExerciseService.findById(id);
        return ResponseEntity.ok(new ExerciseResponse(exercise.id(), exercise.expression(), exercise.status()));
    }

    @GetMapping("/percentage")
    public ResponseEntity<PercentageResponse> getPercentage() {
        PercentageResponse percentageResponse = findPercentageExerciseService.find();
        return ResponseEntity.ok(percentageResponse);
    }
}

