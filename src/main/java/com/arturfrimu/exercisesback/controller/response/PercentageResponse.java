package com.arturfrimu.exercisesback.controller.response;

public record PercentageResponse(String success, String error, String unsolved, int totalCorrectExercises,
                                 int totalErrorExercises, int totalExercises) {
}
