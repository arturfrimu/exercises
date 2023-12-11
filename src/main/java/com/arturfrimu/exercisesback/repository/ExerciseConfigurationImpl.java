package com.arturfrimu.exercisesback.repository;

import com.arturfrimu.exercisesback.entity.ExerciseConfigurationEntity;
import com.arturfrimu.exercisesback.entity.PositionEntity;
import com.arturfrimu.exercisesback.entity.TypeEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@RequiredArgsConstructor
@Repository
public class ExerciseConfigurationImpl {
    private final ExerciseConfigurationRepository exerciseConfigurationRepository;

    public void setExerciseConfiguration(ExerciseConfiguration exerciseConfiguration) {
        if (exerciseConfiguration.range().min() <= 0 || exerciseConfiguration.range().max() <= 0) {
            throw new IllegalArgumentException("min și max trebuie sa fie mai mare ca 0");
        }
        ExerciseConfigurationEntity exerciseConfigurationEntity = new ExerciseConfigurationEntity();
        List<TypeEntity> typeEntities = exerciseConfiguration.type().stream()
                .map(type -> new TypeEntity(type, exerciseConfigurationEntity))
                .collect(Collectors.toList());
        exerciseConfigurationEntity.setTypes(typeEntities);
        List<PositionEntity> positionEntities = exerciseConfiguration.position().stream()
                .map(position -> new PositionEntity(position, exerciseConfigurationEntity))
                .collect(Collectors.toList());
        exerciseConfigurationEntity.setPositions(positionEntities);

        exerciseConfigurationEntity.setMin(exerciseConfiguration.range().min());
        exerciseConfigurationEntity.setMax(exerciseConfiguration.range().max());
        exerciseConfigurationRepository.save(exerciseConfigurationEntity);
    }


    public ExerciseConfiguration getExerciseConfiguration() {
        List<ExerciseConfigurationEntity> exercises = exerciseConfigurationRepository.findAll();

        return exercises.stream()
                .findFirst()
                .map(exercise -> new ExerciseConfiguration(
                        exercise.getTypes().stream().map(TypeEntity::getType).collect(Collectors.toList()),
                        exercise.getPositions().stream().map(PositionEntity::getPosition).collect(Collectors.toList()),
                        new Range(exercise.getMin(), exercise.getMax())
                ))
                .orElse(null);
    }


    public record ExerciseConfiguration(
            List<String> type,
            List<String> position,
            Range range) {
    }

    public record CurrentExerciseConfiguration(
            String type,
            String position,
            Integer min,
            Integer max
    ) {
    }

    public record Range(int min, int max) {
    }
}