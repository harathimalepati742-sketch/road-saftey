package com.roadsafe.game.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AnswerRequest(
    @NotNull(message = "Player ID is required") Long playerId,
    @NotNull(message = "Scenario ID is required") Long scenarioId,
    @NotBlank(message = "Selected answer is required") String selectedAnswer,
    @NotNull(message = "Correct flag is required") Boolean correct
) {}
