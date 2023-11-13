package com.arturfrimu.exercisesback.controller;

import com.arturfrimu.exercisesback.controller.request.VerifyRequest;
import com.arturfrimu.exercisesback.controller.response.ExerciseResponse;
import com.arturfrimu.exercisesback.controller.response.PercentageResponse;
import com.arturfrimu.exercisesback.repository.ExerciseConfigurationRepository.ExerciseConfiguration;
import com.arturfrimu.exercisesback.service.ExerciseGenerationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/config")
    public ResponseEntity<?> config(@RequestBody ExerciseConfiguration configuration) {
        exerciseGenerationService.setConfiguration(configuration);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<ExerciseResponse> generateExercise() {
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

    @GetMapping("/statistic")
    public ResponseEntity<PercentageResponse> getStatistic() {
        PercentageResponse percentage = exerciseGenerationService.getStatistic();
        return ResponseEntity.ok(percentage);
    }
}

