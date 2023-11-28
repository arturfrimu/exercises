package com.arturfrimu.exercisesback.service;

import com.arturfrimu.exercisesback.dao.ExerciseDAOImpl;
import com.arturfrimu.exercisesback.controller.exercise.Exercise;
import com.arturfrimu.exercisesback.controller.request.VerifyRequest;
import com.arturfrimu.exercisesback.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.arturfrimu.exercisesback.enumeration.Status.CORRECT;
import static com.arturfrimu.exercisesback.enumeration.Status.ERROR;

@Service
@RequiredArgsConstructor
public class VerifyExerciseServiceImpl implements VerifyExerciseService {
    private final ExerciseDAOImpl exerciseDAO;

    /**
     * Verificam mai intai daca exercitiul pe care vrem sa-l verificam exista in baza <br>
     * Daca nu este aruncam exceptie ca exercitiul nu a fost gasit <br>
     * Daca este facem verificarea <br>
     * Daca este corect il marcam ca corect si il salvam in baza <br>
     * Daca este gresit il marcam ca gresit si il salvam in baza <br>
     * Returnam clientului true daca este corect si false daca este gresit <br>
     */
    @Override
    public boolean verify(VerifyRequest verifyRequest) {
        Exercise exercise = exerciseDAO.getExercise(verifyRequest.id())
                .orElseThrow(() -> new ResourceNotFoundException("Exercise not found with id: %s".formatted(verifyRequest.id())));

        boolean isCorrect = exercise.verify(verifyRequest.userInput());

        if (isCorrect) {
            Exercise correctExercise = exercise.markAs(CORRECT);
            exerciseDAO.putExercise(correctExercise.id(), correctExercise);
        } else {
            Exercise errorExercise = exercise.markAs(ERROR);
            exerciseDAO.putExercise(errorExercise.id(), errorExercise);
        }
        return isCorrect;
    }
}
