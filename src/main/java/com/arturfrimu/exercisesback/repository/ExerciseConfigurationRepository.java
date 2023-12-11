package com.arturfrimu.exercisesback.repository;

import com.arturfrimu.exercisesback.entity.ExerciseConfigurationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ExerciseConfigurationRepository extends JpaRepository<ExerciseConfigurationEntity, Integer> {
}
