package com.arturfrimu.exercisesback.service;

import com.arturfrimu.exercisesback.repository.ExerciseConfigurationImpl;
import com.arturfrimu.exercisesback.repository.ExerciseConfigurationImpl.ExerciseConfiguration;
import com.arturfrimu.exercisesback.repository.ExerciseConfigurationImpl.CurrentExerciseConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExerciseConfigurationService {
    private final ExerciseConfigurationImpl exerciseConfigurationImpl;
    private final RandomNumberGenerator<Integer> numberGenerator;
    public CurrentExerciseConfiguration getCurrentExerciseConfiguration() {

       ExerciseConfiguration exerciseConfiguration = exerciseConfigurationImpl.getExerciseConfiguration();

        String type = exerciseConfiguration.type().get(numberGenerator.generate(0, exerciseConfiguration.type().size() - 1));
        String position = exerciseConfiguration.position().get(numberGenerator.generate(0, exerciseConfiguration.position().size() - 1));

        int min = exerciseConfiguration.range().min();
        int max = exerciseConfiguration.range().max();

        return new CurrentExerciseConfiguration(type, position, min, max);
    }

    public void setConfiguration(ExerciseConfigurationImpl.ExerciseConfiguration newConfiguration) {
        exerciseConfigurationImpl.setExerciseConfiguration(newConfiguration);
    }
}