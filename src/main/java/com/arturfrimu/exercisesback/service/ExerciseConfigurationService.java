package com.arturfrimu.exercisesback.service;

import com.arturfrimu.exercisesback.entity.ExerciseConfigurationEntity;
import com.arturfrimu.exercisesback.repository.ExerciseConfigurationRepository;
import com.arturfrimu.exercisesback.repository.ExerciseConfigurationImpl.CurrentExerciseConfiguration;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExerciseConfigurationService {
    private final ExerciseConfigurationRepository exerciseConfigurationRepository;
    private final RandomNumberGenerator<Integer> numberGenerator;

    public CurrentExerciseConfiguration getCurrentExerciseConfiguration() {
        ExerciseConfigurationEntity exerciseConfiguration = exerciseConfigurationRepository.findById(1)
                .orElseThrow(() -> new RuntimeException("Exercise configuration not found"));

        String type = exerciseConfiguration.getTypes().get(numberGenerator.generate(0, exerciseConfiguration.getTypes().size() - 1)).getType();
        String position = exerciseConfiguration.getPositions().get(numberGenerator.generate(0, exerciseConfiguration.getPositions().size() - 1)).getPosition();

        int min = exerciseConfiguration.getMin();
        int max = exerciseConfiguration.getMax();

        return new CurrentExerciseConfiguration(type, position, min, max);
    }

    public void setConfiguration(ExerciseConfigurationEntity newConfiguration) {
        exerciseConfigurationRepository.save(newConfiguration);
    }

//    public CurrentExerciseConfiguration getCurrentExerciseConfiguration() {
//        ExerciseConfigurationImpl.ExerciseConfiguration exerciseConfiguration = exerciseConfiguration.getExerciseConfiguration();
//
//        String type = exerciseConfiguration.type().get(numberGenerator.generate(0, exerciseConfiguration.type().size() - 1));
//        String position = exerciseConfiguration.position().get(numberGenerator.generate(0, exerciseConfiguration.position().size() - 1));
//
//        int min = exerciseConfiguration.range().min();
//        int max = exerciseConfiguration.range().max();
//
//        return new CurrentExerciseConfiguration(type, position, min, max);
//    }
//
//    public void setConfiguration(ExerciseConfigurationRepository.ExerciseConfiguration newConfiguration) {
//        exerciseConfigurationRepository.setExerciseConfiguration(newConfiguration);
//    }
}
