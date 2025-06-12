package org.minvas.dragonsofmugloar.game.logic.turn.rule;

import org.minvas.dragonsofmugloar.game.logic.turn.TurnAction;
import org.minvas.dragonsofmugloar.game.model.GameState;

public interface TurnRule {
    boolean isApplicable(GameState gameState);

    TurnAction apply(GameState gameState);
}
