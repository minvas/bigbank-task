package org.minvas.dragonsofmugloar.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TaskResult(
        @JsonProperty("success") boolean success,
        @JsonProperty("lives") int lives,
        @JsonProperty("gold") int gold,
        @JsonProperty("score") int score,
        @JsonProperty("highScore") int highScore,
        @JsonProperty("turn") int turn,
        @JsonProperty("message") String message
) {
}
