package com.arturfrimu.exercisesback.service;

import com.arturfrimu.exercisesback.repository.ExerciseConfigurationRepository;

public interface SetExerciseConfigurationService {
    void setConfiguration(ExerciseConfigurationRepository.ExerciseConfiguration newConfiguration);
}
