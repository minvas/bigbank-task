package org.minvas.dragonsofmugloar.game.logic.turn.rule;

import org.minvas.dragonsofmugloar.client.dto.ShopItem;
import org.minvas.dragonsofmugloar.game.handler.ShopHandler;
import org.minvas.dragonsofmugloar.game.logic.turn.TurnAction;
import org.minvas.dragonsofmugloar.game.model.GameState;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("buyHealthTurnRule")
public class BuyHealthTurnRule implements TurnRule {
    protected static final int MIN_PREFERRED_LIVES = 4;

    private final ShopHandler shopHandler;

    public BuyHealthTurnRule(ShopHandler shopHandler) {
        this.shopHandler = shopHandler;
    }

    @Override
    public boolean isApplicable(GameState gameState) {
        ShopItem shopItem = null;
        if (gameState.lives() < MIN_PREFERRED_LIVES) {
            shopItem = findAffordableHealthItem(gameState);
        }
        return shopItem != null;
    }

    @Override
    public TurnAction apply(GameState gameState) {
        TurnAction turnAction = new TurnAction();
        turnAction.setType(TurnAction.Type.GO_SHOPPING);
        turnAction.setItemId(findAffordableHealthItem(gameState).id());
        return turnAction;
    }

    private ShopItem findAffordableHealthItem(GameState gameState) {
        Optional<ShopItem> searchResult = shopHandler.findHealthItem(gameState.gold());
        return searchResult.orElse(null);
    }
}
