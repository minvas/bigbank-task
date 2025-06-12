package org.minvas.dragonsofmugloar.game.logic.turn.rule;

import org.minvas.dragonsofmugloar.client.dto.ShopItem;
import org.minvas.dragonsofmugloar.game.handler.ShopHandler;
import org.minvas.dragonsofmugloar.game.logic.turn.TurnAction;
import org.minvas.dragonsofmugloar.game.model.GameState;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("maxLevelUpTurnRule")
public class MaxLevelUpTurnRule implements TurnRule {

    private final ShopHandler shopHandler;

    public MaxLevelUpTurnRule(ShopHandler shopHandler) {
        this.shopHandler = shopHandler;
    }

    @Override
    public boolean isApplicable(GameState gameState) {
        return getMostExpensiveItemForLevelUp(gameState) != null;
    }

    @Override
    public TurnAction apply(GameState gameState) {
        TurnAction turnAction = new TurnAction();
        turnAction.setType(TurnAction.Type.GO_SHOPPING);
        turnAction.setItemId(getMostExpensiveItemForLevelUp(gameState).id());
        return turnAction;
    }

    private ShopItem getMostExpensiveItemForLevelUp(GameState gameState) {
        Optional<ShopItem> result = shopHandler.findMostExpensiveLevelUpItem(gameState.gold());
        return result.orElse(null);
    }
}
