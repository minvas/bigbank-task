package org.minvas.dragonsofmugloar.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Game(
        @JsonProperty("gameId") String gameId,
        @JsonProperty("lives") int lives,
        @JsonProperty("gold") int gold,
        @JsonProperty("level") int level,
        @JsonProperty("score") int score,
        @JsonProperty("highScore") int highScore,
        @JsonProperty("turn") int turn
) {
}
