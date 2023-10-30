package com.arturfrimu.exercisesback.controller.request;

import java.util.UUID;

public record VerifyRequest(UUID id, String userInput) {
}
