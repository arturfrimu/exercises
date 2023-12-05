package com.arturfrimu.exercisesback.repository;

import com.arturfrimu.exercisesback.entity.ExerciseConfigurationEntity;
import com.arturfrimu.exercisesback.entity.PositionEntity;
import com.arturfrimu.exercisesback.entity.TypeEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ExerciseConfigurationImpl {
    private final ExerciseConfigurationRepository exerciseConfigurationRepository;
    public void saveExerciseConfiguration() {
        ExerciseConfigurationEntity exerciseConfiguration = new ExerciseConfigurationEntity(1, 5, 10);

        // Inițializează listele
        exerciseConfiguration.setPositions(new ArrayList<>());
        exerciseConfiguration.setTypes(new ArrayList<>());

        PositionEntity position1 = new PositionEntity("center", exerciseConfiguration);
        PositionEntity position2 = new PositionEntity("left", exerciseConfiguration);
        PositionEntity position3 = new PositionEntity("right", exerciseConfiguration);

        TypeEntity type1 = new TypeEntity("sum", exerciseConfiguration);
        TypeEntity type2 = new TypeEntity("difference", exerciseConfiguration);


        // Adaugă elemente în listă
        exerciseConfiguration.getPositions().add(position1);
        exerciseConfiguration.getPositions().add(position2);
        exerciseConfiguration.getPositions().add(position3);

        exerciseConfiguration.getTypes().add(type1);
        exerciseConfiguration.getTypes().add(type2);

        exerciseConfigurationRepository.save(exerciseConfiguration);
    }
//    public record ExerciseConfiguration(
//            List<String> type,
//            List<String> position,
//            Range range) {
//    }

    public record CurrentExerciseConfiguration(
            String type,
            String position,
            Integer min,
            Integer max) {
    }

//    public record Range(int min, int max) {
//    }
}
