package org.minvas.dragonsofmugloar.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ShopItem(
        @JsonProperty("id") String id,
        @JsonProperty("name") String name,
        @JsonProperty("cost") int cost
) {
}
