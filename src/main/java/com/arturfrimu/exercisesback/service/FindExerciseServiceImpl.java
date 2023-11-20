package com.arturfrimu.exercisesback.service;

import com.arturfrimu.exercisesback.DAO.ExerciseDAOInterface;
import com.arturfrimu.exercisesback.controller.exercise.Exercise;
import com.arturfrimu.exercisesback.controller.response.ExerciseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class FindExerciseServiceImpl implements FindExerciseService {
    private final ExerciseDAOInterface exerciseDAO;

    @Override
    public ExerciseResponse getExerciseById(UUID id) {
        Exercise exercise = exerciseDAO.getExercise(id);
        return new ExerciseResponse(exercise.id(), exercise.expression(), exercise.status());
    }
}
