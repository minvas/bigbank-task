package org.minvas.dragonsofmugloar.client;

import org.minvas.dragonsofmugloar.client.dto.*;

import java.util.List;

public interface GameClient {
    Game startGame();

    List<Message> fetchMessages(String gameId);

    TaskResult solveMessage(String gameId, String adId);

    List<ShopItem> fetchShopItems(String gameId);

    PurchaseResult buyItem(String gameId, String itemId);
}
