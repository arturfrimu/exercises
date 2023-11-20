package com.arturfrimu.exercisesback.service;

import com.arturfrimu.exercisesback.DAO.ExerciseDAOInterface;
import com.arturfrimu.exercisesback.controller.exercise.Exercise;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ExerciseManagementServiceImpl implements ExerciseManagementService {
    private final ExerciseDAOInterface exerciseDAO;

    @Override
    public void put(Map<UUID, Exercise> map) {
        exerciseDAO.putAll(map);
    }

    @Override
    public void clear() {
        exerciseDAO.clear();
    }
}
