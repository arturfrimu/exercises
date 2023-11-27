package com.arturfrimu.exercisesback.service;

import com.arturfrimu.exercisesback.DAO.ExerciseDAOImpl;
import com.arturfrimu.exercisesback.controller.exercise.Exercise;
import com.arturfrimu.exercisesback.controller.response.ExerciseResponse;
import com.arturfrimu.exercisesback.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class FindExerciseServiceImpl implements FindExerciseService {
    private final ExerciseDAOImpl exerciseDAO;

    /**
     * Cautam in baza exercitiul dupa Id si aruncam exceptie daca nu a fost gasit nimic <br>
     * Daca am gasit, mapam la ExerciseResponse si dam raspuns clientului
     */
    @Override
    public ExerciseResponse findById(UUID id) {
        Exercise exercise = exerciseDAO.getExercise(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nu a fost gasit nici un exercitiu cu id-ul: %s".formatted(id)));

        return new ExerciseResponse(exercise.id(), exercise.expression(), exercise.status());
    }
}
