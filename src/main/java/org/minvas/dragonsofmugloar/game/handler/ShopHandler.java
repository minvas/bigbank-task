package org.minvas.dragonsofmugloar.game.handler;

import org.minvas.dragonsofmugloar.client.GameClient;
import org.minvas.dragonsofmugloar.client.dto.PurchaseResult;
import org.minvas.dragonsofmugloar.client.dto.ShopItem;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toMap;

@Component
public class ShopHandler {
    protected static final String ITEM_HEALTH = "hpot";
    private Map<String, ShopItem> shopItemsMappedByItemId;

    private final GameClient gameClient;

    public ShopHandler(GameClient gameClient) {
        this.gameClient = gameClient;
    }

    public void refreshShopItems(String gameId) {
        List<ShopItem> shopItems = gameClient.fetchShopItems(gameId);
        shopItemsMappedByItemId = shopItems.stream().collect(toMap(ShopItem::id, shopItem -> shopItem));
    }

    public PurchaseResult buyItem(String gameId, String itemId) {
        return gameClient.buyItem(gameId, itemId);
    }

    public Optional<ShopItem> findHealthItem(int availableGold) {
        ShopItem shopItem = shopItemsMappedByItemId.get(ITEM_HEALTH);
        if (shopItem != null && shopItem.cost() <= availableGold) {
            return Optional.of(shopItem);
        }
        return Optional.empty();
    }

    public Optional<ShopItem> findMostExpensiveLevelUpItem(int availableGold) {
        ShopItem result = null;
        for (ShopItem shopItem : getShopItems()) {
            if (ITEM_HEALTH.equals(shopItem.id())) {
                continue;
            }

            if (shopItem.cost() <= availableGold) {
                if (result == null) {
                    result = shopItem;
                } else {
                    result = result.cost() < shopItem.cost() ? shopItem : result;
                }
            }
        }
        return result == null ? Optional.empty() : Optional.of(result);
    }

    protected Collection<ShopItem> getShopItems() {
        return shopItemsMappedByItemId.values();
    }
}
