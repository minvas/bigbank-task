package org.minvas.dragonsofmugloar.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PurchaseResult(
        @JsonProperty("shoppingSuccess") boolean shoppingSuccess,
        @JsonProperty("gold") int gold,
        @JsonProperty("lives") int lives,
        @JsonProperty("level") int level,
        @JsonProperty("turn") int turn
) {
}
