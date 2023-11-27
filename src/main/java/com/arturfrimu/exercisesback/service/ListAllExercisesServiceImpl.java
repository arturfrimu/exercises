package com.arturfrimu.exercisesback.service;

import com.arturfrimu.exercisesback.DAO.ExerciseDAOImpl;
import com.arturfrimu.exercisesback.controller.exercise.Exercise;
import com.arturfrimu.exercisesback.controller.response.ExerciseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ListAllExercisesServiceImpl implements ListAllExercisesService {
    private final ExerciseDAOImpl exerciseDAO;

    @Override
    public List<ExerciseResponse> getAll() {
        List<Exercise> allExercises = exerciseDAO.getAllExercises();
        return allExercises.stream()
                .map(exercise -> new ExerciseResponse(exercise.id(), exercise.expression(), exercise.status()))
                .toList();
    }
}
