package com.arturfrimu.exercisesback.controller;

import com.arturfrimu.exercisesback.controller.request.VerifyRequest;
import com.arturfrimu.exercisesback.controller.response.ExerciseResponse;
import com.arturfrimu.exercisesback.controller.response.PercentageResponse;
import com.arturfrimu.exercisesback.repository.ExerciseConfigurationRepository.ExerciseConfiguration;
import com.arturfrimu.exercisesback.service.ExerciseGenerationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@CrossOrigin(origins = "*", methods = {POST, GET, PUT, PATCH})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v7/exercise-generator")
public class ExerciseGeneratorControllerV7 {

    private final ExerciseGenerationService exerciseGenerationService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/config")
    public ResponseEntity<?> config(@RequestBody ExerciseConfiguration configuration) {
        exerciseGenerationService.setConfiguration(configuration);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN') and hasRole('USER')")
    @GetMapping
    public ResponseEntity<ExerciseResponse> generateExercise() {
        ExerciseResponse exerciseResponse = exerciseGenerationService.generateExercise();

        return ResponseEntity.ok(exerciseResponse);
    }

    @PreAuthorize("hasRole('ADMIN') and hasRole('USER')")
    @PostMapping
    public ResponseEntity<Boolean> verify(@RequestBody VerifyRequest verifyRequest) {
        boolean isCorrect = exerciseGenerationService.verifyExercise(verifyRequest);

        return ResponseEntity.ok(isCorrect);
    }

    @PreAuthorize("hasRole('ADMIN') and hasRole('USER')")
    @GetMapping("/exercises")
    public ResponseEntity<List<ExerciseResponse>> getAll() {
        List<ExerciseResponse> allExercises = exerciseGenerationService.getAllExercises();
        return ResponseEntity.ok(allExercises);
    }

    @PreAuthorize("hasRole('ADMIN') and hasRole('USER')")
    @GetMapping("/exercises/{id}")
    public ResponseEntity<ExerciseResponse> getById(@PathVariable UUID id) {
        ExerciseResponse exercise = exerciseGenerationService.getExerciseById(id);
        return ResponseEntity.ok(new ExerciseResponse(exercise.id(), exercise.expression(), exercise.status()));
    }

    @PreAuthorize("hasRole('ADMIN') and hasRole('USER')")
    @GetMapping("/percentage")
    public ResponseEntity<PercentageResponse> getPercentage() {
        PercentageResponse percentageResponse = exerciseGenerationService.getPercentage();
        return ResponseEntity.ok(percentageResponse);
    }
}

