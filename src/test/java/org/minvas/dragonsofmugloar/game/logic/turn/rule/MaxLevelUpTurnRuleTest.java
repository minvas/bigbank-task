package org.minvas.dragonsofmugloar.game.logic.turn.rule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.minvas.dragonsofmugloar.client.GameClient;
import org.minvas.dragonsofmugloar.client.dto.ShopItem;
import org.minvas.dragonsofmugloar.game.handler.ShopHandler;
import org.minvas.dragonsofmugloar.game.logic.turn.TurnAction;
import org.minvas.dragonsofmugloar.game.model.GameState;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.minvas.dragonsofmugloar.game.logic.turn.TurnAction.Type.GO_SHOPPING;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class MaxLevelUpTurnRuleTest {
    private MaxLevelUpTurnRule maxLevelUpTurnRule;

    @BeforeEach
    public void setUp() {
        ShopItem shopItem1 = new ShopItem("bookOfTricks", "Book of Tricks", 100);
        ShopItem shopItem2 = new ShopItem("bookOfMegatricks", "Book of Megatricks", 300);

        GameClient gameClient = mock(GameClient.class);
        when(gameClient.fetchShopItems(any())).thenReturn(List.of(shopItem1, shopItem2));

        ShopHandler shopHandler = new ShopHandler(gameClient);
        shopHandler.refreshShopItems(any());

        maxLevelUpTurnRule = new MaxLevelUpTurnRule(shopHandler);
    }

    @Test
    void Rule_is_applicable_if_affordable_level_up_item_exists() {
        assertTrue(maxLevelUpTurnRule.isApplicable(new GameState.Builder().gold(100).build()));
    }

    @Test
    void Rule_is_not_applicable_if_affordable_level_up_item_does_not_exist() {
        assertFalse(maxLevelUpTurnRule.isApplicable(new GameState.Builder().gold(50).build()));
    }

    @Test
    void Applied_rule_produces_correct_turn_action() {
        TurnAction turnAction = maxLevelUpTurnRule.apply(new GameState.Builder().gold(300).build());
        assertSame(GO_SHOPPING, turnAction.getType());
        assertEquals("bookOfMegatricks", turnAction.getItemId());
        assertNull(turnAction.getMessageId());
    }
}
