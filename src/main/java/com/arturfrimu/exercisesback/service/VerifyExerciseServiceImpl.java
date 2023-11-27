package com.arturfrimu.exercisesback.service;
import com.arturfrimu.exercisesback.DAO.ExerciseDAOImpl;
import com.arturfrimu.exercisesback.controller.exercise.Exercise;
import com.arturfrimu.exercisesback.controller.request.VerifyRequest;
import com.arturfrimu.exercisesback.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.arturfrimu.exercisesback.controller.enumeration.Status.CORRECT;
import static com.arturfrimu.exercisesback.controller.enumeration.Status.ERROR;

@Service
@RequiredArgsConstructor
public class VerifyExerciseServiceImpl implements VerifyExerciseService {
    private final ExerciseDAOImpl exerciseDAO;

    @Override
    public boolean verify(VerifyRequest verifyRequest) {
        Exercise exercise = exerciseDAO.getExercise(verifyRequest.id());

        if (Objects.isNull(exercise)) {
            throw new ResourceNotFoundException("Exercise not found with id: %s".formatted(verifyRequest.id()));
        }

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
