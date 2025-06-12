package org.minvas.dragonsofmugloar.game.logic.turn;

import org.minvas.dragonsofmugloar.game.logic.turn.rule.TurnRule;
import org.minvas.dragonsofmugloar.game.model.GameState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class TurnRuleEngine {
    private static final Logger LOGGER = LoggerFactory.getLogger(TurnRuleEngine.class);

    private final List<TurnRule> turnRules;

    public TurnRuleEngine(List<TurnRule> turnRules) {
        this.turnRules = turnRules;
    }

    public TurnAction determineTurnAction(GameState gameState) {
        for (TurnRule turnRule : turnRules) {
            if (turnRule.isApplicable(gameState)) {
                LOGGER.info("Applying turn rule: {}", turnRule.getClass().getSimpleName());
                return turnRule.apply(gameState);
            }
        }

        throw new IllegalStateException("None of the rules can be applied");
    }
}
