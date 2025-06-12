package org.minvas.dragonsofmugloar.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Message(
        @JsonProperty("adId") String adId,
        @JsonProperty("message") String message,
        @JsonProperty("reward") int reward,
        @JsonProperty("expiresIn") int expiresIn,
        @JsonProperty("probability") String probability
) {
}
