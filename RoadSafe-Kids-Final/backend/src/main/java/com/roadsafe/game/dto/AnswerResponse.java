package com.roadsafe.game.dto;

public record AnswerResponse(
    boolean correct,
    int points,
    int score,
    String message,
    String safetyGuidance
) {}
