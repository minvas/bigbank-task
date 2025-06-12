package org.minvas.dragonsofmugloar.client;

import org.minvas.dragonsofmugloar.client.dto.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
class GameClientImpl implements GameClient {

    private final RestClient restClient;

    public GameClientImpl(RestClient.Builder restClientBuilder,
                          @Value("${app.client.base-url}") String baseUrl) {
        this.restClient = restClientBuilder.baseUrl(baseUrl).build();
    }

    @Override
    public Game startGame() {
        return restClient.post()
                .uri("/game/start")
                .retrieve()
                .body(Game.class);
    }

    @Override
    public List<Message> fetchMessages(String gameId) {
        return restClient.get()
                .uri("/{gameId}/messages", gameId)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }

    @Override
    public TaskResult solveMessage(String gameId, String adId) {
        return restClient.post()
                .uri("/{gameId}/solve/{adId}", gameId, adId)
                .retrieve()
                .body(TaskResult.class);
    }

    @Override
    public List<ShopItem> fetchShopItems(String gameId) {
        return restClient.get()
                .uri("/{gameId}/shop", gameId)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }

    @Override
    public PurchaseResult buyItem(String gameId, String itemId) {
        return restClient.post()
                .uri("/{gameId}/shop/buy/{itemId}", gameId, itemId)
                .retrieve()
                .body(PurchaseResult.class);
    }
}
