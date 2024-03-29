package com.arturfrimu.exercisesback.controller.response;

import com.arturfrimu.exercisesback.enumeration.Status;
import java.util.UUID;

public record ExerciseResponse(UUID id, String expression, Status status) {
}
