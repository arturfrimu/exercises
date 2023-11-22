package com.arturfrimu.exercisesback.service;

import com.arturfrimu.exercisesback.DAO.ExerciseDAOInterface;
import com.arturfrimu.exercisesback.controller.enumeration.Status;
import com.arturfrimu.exercisesback.controller.exercise.Exercise;
import com.arturfrimu.exercisesback.controller.response.PercentageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.arturfrimu.exercisesback.controller.enumeration.Status.CORRECT;
import static com.arturfrimu.exercisesback.controller.enumeration.Status.ERROR;
import static java.math.MathContext.DECIMAL32;

@RequiredArgsConstructor
@Service
public class FindPercentageExerciseServiceImpl implements FindPercentageExerciseService {
    private final ExerciseDAOInterface exerciseDAO;

    @Override
    public PercentageResponse find() {
        int exercisesSize = exerciseDAO.size();

        if (exercisesSize == 0) {
            return new PercentageResponse("0", "0", "0");
        }

        Map<Status, List<Exercise>> percentage = exerciseDAO.getAllExercises()
                .stream()
                .collect(Collectors.groupingBy(
                        Exercise::status,
                        Collectors.mapping(
                                exercise -> exercise,
                                Collectors.toList())));

        BigDecimal totalPercentage = BigDecimal.valueOf(100);
        BigDecimal correctPercentage = BigDecimal.ZERO;
        BigDecimal errorPercentage = BigDecimal.ZERO;

        if (percentage.containsKey(CORRECT)) {
            int totalCorrectExercises = percentage.get(CORRECT).size();
            correctPercentage = BigDecimal.valueOf(totalCorrectExercises)
                    .multiply(totalPercentage)
                    .divide(BigDecimal.valueOf(exercisesSize), DECIMAL32);
        }

        if (percentage.containsKey(ERROR)) {
            int totalErrorExercises = percentage.get(ERROR).size();
            errorPercentage = BigDecimal.valueOf(totalErrorExercises)
                    .multiply(totalPercentage)
                    .divide(BigDecimal.valueOf(exercisesSize), DECIMAL32);
        }

        String correctExercisesPercent = correctPercentage.toString();
        String errorExercisesPercent = errorPercentage.toString();
        String unsolvedPercentage = totalPercentage.subtract(correctPercentage).subtract(errorPercentage).toString();

        return new PercentageResponse(correctExercisesPercent, errorExercisesPercent, unsolvedPercentage);
    }
}
