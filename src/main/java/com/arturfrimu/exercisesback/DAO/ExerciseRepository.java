package com.arturfrimu.exercisesback.DAO;

import com.arturfrimu.exercisesback.Entity.ExerciseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExerciseRepository extends JpaRepository<ExerciseEntity, UUID> {
}

