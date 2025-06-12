package org.minvas.dragonsofmugloar.game.logic.turn.rule;

import org.minvas.dragonsofmugloar.game.handler.MessageHandler;
import org.minvas.dragonsofmugloar.game.model.GameState;
import org.springframework.stereotype.Component;

@Component("defaultTurnRule")
public class DefaultTurnRule extends AbstractTaskTurnRule {

    public DefaultTurnRule(MessageHandler messageHandler) {
        super(messageHandler);
    }

    @Override
    public boolean isApplicable(GameState gameState) {
        return true;
    }
}
