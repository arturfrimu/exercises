package com.arturfrimu.exercisesback.controller.exercise;

import com.arturfrimu.exercisesback.controller.enumeration.Status;

import java.util.UUID;

public record Exercise(UUID id, String expression, String result, Status status) {
    public boolean verify(final String requestResult) {
        return this.result.equals(requestResult);
    }

    public Exercise markAs(Status status) {
        return new Exercise(id, expression, result, status);
    }
}
