package com.roadsafe.game.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreatePlayerRequest(
    @NotBlank(message = "Player name is required")
    @Size(min = 2, max = 40, message = "Player name must be 2-40 characters")
    String name
) {}
