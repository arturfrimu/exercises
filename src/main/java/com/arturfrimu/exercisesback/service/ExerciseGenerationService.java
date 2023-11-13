package com.arturfrimu.exercisesback.service;

import com.arturfrimu.exercisesback.controller.exercise.Exercise;
import com.arturfrimu.exercisesback.controller.request.VerifyRequest;
import com.arturfrimu.exercisesback.controller.response.ExerciseResponse;
import com.arturfrimu.exercisesback.controller.response.PercentageResponse;
import com.arturfrimu.exercisesback.repository.ExerciseConfigurationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @see <a href="https://www.baeldung.com/java-interface-segregation">SOLID Interface Segregation</a>
 */
@Service // TODO: 05.11.2023 Delete from interface
public interface ExerciseGenerationService {

    // TODO: 05.11.2023 Prea multe metode in aceasta interfata.
    //  Grupeazale dupa logica si extragele creaza mai multe interfete mai mici
    //  Pentru fiecare metoda o interfata aparte
    //  Exemplu de denumiri de interfete:
    //  - SetExerciseConfigurationService
    //   - GenerateExerciseService
    //   - ListAllExercisesService
    //   - FindExerciseService
    //   - FindPercentageExerciseService

    void setConfiguration(ExerciseConfigurationRepository.ExerciseConfiguration newConfiguration);

    ExerciseResponse generateExercise();

    boolean verifyExercise(VerifyRequest verifyRequest);

    List<ExerciseResponse> getAllExercises();

    ExerciseResponse getExerciseById(UUID id);

    PercentageResponse getStatistic();

    void put(Map<UUID, Exercise> map);

    void clear();
}

