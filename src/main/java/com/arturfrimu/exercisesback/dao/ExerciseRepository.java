package com.arturfrimu.exercisesback.dao;

import com.arturfrimu.exercisesback.entity.ExerciseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExerciseRepository extends JpaRepository<ExerciseEntity, UUID> {
}

