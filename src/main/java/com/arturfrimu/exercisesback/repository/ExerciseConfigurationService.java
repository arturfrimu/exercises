package com.arturfrimu.exercisesback.repository;

import com.arturfrimu.exercisesback.repository.ExerciseConfigurationRepository.CurrentExerciseConfiguration;
import com.arturfrimu.exercisesback.service.RandomNumberGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExerciseConfigurationService {
    private final ExerciseConfigurationRepository exerciseConfigurationRepository;
    private final RandomNumberGenerator<Integer> numberGenerator;

    public CurrentExerciseConfiguration getCurrentExerciseConfiguration() {
        ExerciseConfigurationRepository.ExerciseConfiguration exerciseConfiguration = exerciseConfigurationRepository.getExerciseConfiguration();

        String type = exerciseConfiguration.type().get(numberGenerator.generate(0, exerciseConfiguration.type().size() - 1));
        String position = exerciseConfiguration.position().get(numberGenerator.generate(0, exerciseConfiguration.position().size() - 1));

        int min = exerciseConfiguration.range().min();
        int max = exerciseConfiguration.range().max();

        return new CurrentExerciseConfiguration(type, position, min, max);
    }

    public void setConfiguration(ExerciseConfigurationRepository.ExerciseConfiguration newConfiguration) {
        exerciseConfigurationRepository.setExerciseConfiguration(newConfiguration);
    }
}
