package org.minvas.dragonsofmugloar.game.handler;

import org.junit.jupiter.api.*;
import org.minvas.dragonsofmugloar.client.GameClient;
import org.minvas.dragonsofmugloar.client.dto.PurchaseResult;
import org.minvas.dragonsofmugloar.client.dto.ShopItem;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.minvas.dragonsofmugloar.game.handler.ShopHandler.ITEM_HEALTH;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ShopHandlerTest {
    private static final String GAME_ID = "bGarrA";
    private static final int HEALTH_COST = 50;

    private List<ShopItem> expectedShopItems;
    private PurchaseResult expectedPurchaseResult;
    private ShopItem shopItem1;
    private ShopItem shopItem2;


    private ShopHandler shopHandler;
    private GameClient gameClient;

    @BeforeEach
    public void setUp() {
        gameClient = mock(GameClient.class);

        shopItem1 = new ShopItem(ITEM_HEALTH, "Health potion", HEALTH_COST);
        shopItem2 = new ShopItem("bom", "Book of Megatricks", 300);
        ShopItem shopItem3 = new ShopItem("bot", "Book of Tricks", 100);

        expectedShopItems = List.of(shopItem1, shopItem2, shopItem3);
        expectedPurchaseResult = new PurchaseResult(true, 10, 5, 1, 4);

        when(gameClient.fetchShopItems(GAME_ID)).thenReturn(expectedShopItems);
        when(gameClient.buyItem(GAME_ID, ITEM_HEALTH)).thenReturn(expectedPurchaseResult);

        shopHandler = new ShopHandler(gameClient);
    }

    @Test
    void Refreshes_shop_items() {
        shopHandler.refreshShopItems(GAME_ID);

        Collection<ShopItem> shopItems = shopHandler.getShopItems();
        assertEquals(expectedShopItems.size(), shopItems.size());
        assertTrue(shopItems.containsAll(expectedShopItems));
    }

    @Test
    void Buys_item() {
        shopHandler.refreshShopItems(GAME_ID);
        PurchaseResult purchaseResult = shopHandler.buyItem(GAME_ID, ITEM_HEALTH);

        assertEquals(expectedPurchaseResult.shoppingSuccess(), purchaseResult.shoppingSuccess());
        assertEquals(expectedPurchaseResult.gold(), purchaseResult.gold());
        assertEquals(expectedPurchaseResult.lives(), purchaseResult.lives());
        assertEquals(expectedPurchaseResult.level(), purchaseResult.level());
        assertEquals(expectedPurchaseResult.turn(), purchaseResult.turn());
    }

    @Nested
    class Find_health_item {
        @Test
        void Returns_non_empty_result_if_exists_and_enough_gold() {
            shopHandler.refreshShopItems(GAME_ID);

            Optional<ShopItem> result = shopHandler.findHealthItem(HEALTH_COST);
            assertTrue(result.isPresent());
            assertSame(shopItem1, result.get());
        }

        @Test
        void Returns_empty_result_if_does_not_exist() {
            when(gameClient.fetchShopItems(GAME_ID)).thenReturn(Collections.emptyList());
            shopHandler.refreshShopItems(GAME_ID);

            Optional<ShopItem> result = shopHandler.findHealthItem(HEALTH_COST);
            assertFalse(result.isPresent());
        }

        @Test
        void Returns_empty_result_if_exist_but_not_enough_gold() {
            shopHandler.refreshShopItems(GAME_ID);

            Optional<ShopItem> result = shopHandler.findHealthItem(HEALTH_COST - 1);
            assertFalse(result.isPresent());
        }
    }

    @Nested
    class Find_most_expensive_level_up_item {
        @Test
        void Returns_non_empty_result_if_exists_and_enough_gold() {
            shopHandler.refreshShopItems(GAME_ID);

            Optional<ShopItem> result = shopHandler.findMostExpensiveLevelUpItem(300);
            assertTrue(result.isPresent());
            assertSame(shopItem2, result.get());
        }

        @Test
        void Returns_empty_result_if_does_not_exist() {
            when(gameClient.fetchShopItems(GAME_ID)).thenReturn(Collections.emptyList());
            shopHandler.refreshShopItems(GAME_ID);

            Optional<ShopItem> result = shopHandler.findMostExpensiveLevelUpItem(50000);
            assertFalse(result.isPresent());
        }

        @Test
        void Returns_empty_result_if_exist_but_not_enough_gold() {
            shopHandler.refreshShopItems(GAME_ID);

            Optional<ShopItem> result = shopHandler.findMostExpensiveLevelUpItem(HEALTH_COST);
            assertFalse(result.isPresent());
        }
    }
}
