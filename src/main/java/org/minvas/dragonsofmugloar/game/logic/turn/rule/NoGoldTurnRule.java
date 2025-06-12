package org.minvas.dragonsofmugloar.game.logic.turn.rule;

import org.minvas.dragonsofmugloar.game.handler.MessageHandler;
import org.minvas.dragonsofmugloar.game.model.GameState;
import org.springframework.stereotype.Component;

@Component("noGoldTurnRule")
public class NoGoldTurnRule extends AbstractTaskTurnRule {

    public NoGoldTurnRule(MessageHandler messageHandler) {
        super(messageHandler);
    }

    @Override
    public boolean isApplicable(GameState gameState) {
        return gameState.gold() <= 0;
    }
}
