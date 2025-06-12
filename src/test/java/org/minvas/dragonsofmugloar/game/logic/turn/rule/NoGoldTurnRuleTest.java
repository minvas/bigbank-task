package org.minvas.dragonsofmugloar.game.logic.turn.rule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.minvas.dragonsofmugloar.client.GameClient;
import org.minvas.dragonsofmugloar.client.dto.Message;
import org.minvas.dragonsofmugloar.game.handler.MessageHandler;
import org.minvas.dragonsofmugloar.game.logic.turn.TurnAction;
import org.minvas.dragonsofmugloar.game.model.GameState;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.minvas.dragonsofmugloar.game.logic.turn.TurnAction.Type.DO_TASK;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class NoGoldTurnRuleTest {

    private NoGoldTurnRule noGoldTurnRule;

    @BeforeEach
    public void setUp() {
        Message message = new Message("aCddBe", "Test message", 10, 7, "Gamble");


        GameClient gameClient = mock(GameClient.class);
        when(gameClient.fetchMessages(any())).thenReturn(List.of(message));

        MessageHandler messageHandler = new MessageHandler(gameClient);
        messageHandler.refreshMessages("seEvad");

        noGoldTurnRule = new NoGoldTurnRule(messageHandler);
    }

    @Test
    void Rule_is_applicable_if_gold_amount_is_not_positive() {
        GameState gameState = new GameState.Builder().gold(0).build();
        assertTrue(noGoldTurnRule.isApplicable(gameState));
    }

    @Test
    void Rule_is_not_applicable_if_gold_amount_is_positive() {
        GameState gameState = new GameState.Builder().gold(1).build();
        assertFalse(noGoldTurnRule.isApplicable(gameState));
    }

    @Test
    void Applied_rule_produces_correct_turn_action() {
        TurnAction turnAction = noGoldTurnRule.apply(new GameState.Builder().build());
        assertSame(DO_TASK, turnAction.getType());
        assertEquals("aCddBe", turnAction.getMessageId());
        assertNull(turnAction.getItemId());
    }
}
