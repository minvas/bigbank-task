package org.minvas.dragonsofmugloar.game.logic.turn.rule;

import org.junit.jupiter.api.*;
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
public class BuyHealthTurnRuleTest {
    private static final int HEALTH_COST = 50;

    private BuyHealthTurnRule buyHealthTurnRule;

    @BeforeEach
    public void setUp() {
        ShopItem shopItem1 = new ShopItem("hpot", "Health potion", HEALTH_COST);

        GameClient gameClient = mock(GameClient.class);
        when(gameClient.fetchShopItems(any())).thenReturn(List.of(shopItem1));

        ShopHandler shopHandler = new ShopHandler(gameClient);
        shopHandler.refreshShopItems(any());

        buyHealthTurnRule = new BuyHealthTurnRule(shopHandler);
    }

    @Test
    void Rule_is_applicable_if_lives_below_min_limit_and_affordable_health_item_exists() {
        GameState gameState = new GameState.Builder()
                .lives(BuyHealthTurnRule.MIN_PREFERRED_LIVES - 1)
                .gold(HEALTH_COST)
                .build();
        assertTrue(buyHealthTurnRule.isApplicable(gameState));
    }

    @Nested
    class Rule_is_not_applicable_if {
        @Test
        void Min_lives_limit_not_reached() {
            GameState gameState = new GameState.Builder()
                    .lives(BuyHealthTurnRule.MIN_PREFERRED_LIVES)
                    .gold(HEALTH_COST)
                    .build();
            assertFalse(buyHealthTurnRule.isApplicable(gameState));
        }

        @Test
        void Affordable_health_item_does_not_exist() {
            int livesBellowLimit = BuyHealthTurnRule.MIN_PREFERRED_LIVES - 1;
            int goldBelowHealthItemCost = HEALTH_COST - 1;
            GameState gameState = new GameState.Builder()
                    .lives(livesBellowLimit)
                    .gold(goldBelowHealthItemCost)
                    .build();
            assertFalse(buyHealthTurnRule.isApplicable(gameState));
        }
    }

    @Test
    void Applied_rule_produces_correct_turn_action() {
        TurnAction turnAction = buyHealthTurnRule.apply(new GameState.Builder().gold(HEALTH_COST).build());
        assertSame(GO_SHOPPING, turnAction.getType());
        assertEquals("hpot", turnAction.getItemId());
        assertNull(turnAction.getMessageId());
    }
}
