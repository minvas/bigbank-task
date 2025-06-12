package org.minvas.dragonsofmugloar.game.logic.turn;


import org.junit.jupiter.api.*;
import org.minvas.dragonsofmugloar.game.logic.turn.rule.TurnRule;
import org.minvas.dragonsofmugloar.game.model.GameState;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class TurnRuleEngineTest {
    GameState gameState;
    TurnRule turnRule1;
    TurnRule turnRule2;
    TurnRule turnRule3;

    private TurnRuleEngine turnRuleEngine;

    @BeforeEach
    public void setUp() {
        gameState = mock(GameState.class);

        turnRule1 = spy(new FakeTurnRule(false));
        turnRule2 = spy(new FakeTurnRule(true));
        turnRule3 = spy(new FakeTurnRule(true));

        turnRuleEngine = new TurnRuleEngine(List.of(turnRule1, turnRule2, turnRule3));
    }

    @Nested
    class Determine_turn_action {
        @Test
        void Applies_only_first_applicable_rule_from_the_list() {
            turnRuleEngine.determineTurnAction(gameState);

            verify(turnRule1, times(1)).isApplicable(gameState);
            verify(turnRule2, times(1)).isApplicable(gameState);
            verify(turnRule2, times(1)).apply(gameState);
            verifyNoInteractions(turnRule3);
        }

        @Test
        void Throws_exception_none_of_the_rules_can_be_applied() {
            turnRuleEngine = new TurnRuleEngine(Collections.emptyList());
            IllegalStateException exception = assertThrows(IllegalStateException.class, () -> turnRuleEngine.determineTurnAction(gameState));
            assertEquals("None of the rules can be applied", exception.getMessage());
        }
    }

    private static class FakeTurnRule implements TurnRule {

        boolean applicable;

        public FakeTurnRule(boolean applicable) {
            this.applicable = applicable;
        }

        @Override
        public boolean isApplicable(GameState gameState) {
            return applicable;
        }

        @Override
        public TurnAction apply(GameState gameState) {
            return new TurnAction();
        }
    }
}
