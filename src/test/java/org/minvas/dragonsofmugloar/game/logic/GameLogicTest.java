package org.minvas.dragonsofmugloar.game.logic;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.minvas.dragonsofmugloar.client.GameClient;
import org.minvas.dragonsofmugloar.client.dto.Game;
import org.minvas.dragonsofmugloar.client.dto.Message;
import org.minvas.dragonsofmugloar.client.dto.ShopItem;
import org.minvas.dragonsofmugloar.game.handler.GameStateHandler;
import org.minvas.dragonsofmugloar.game.handler.MessageHandler;
import org.minvas.dragonsofmugloar.game.handler.ShopHandler;
import org.minvas.dragonsofmugloar.game.logic.turn.TurnActionProcessor;
import org.minvas.dragonsofmugloar.game.logic.turn.TurnRuleEngine;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class GameLogicTest {
    private static final String GAME_ID = "gbSfSa";

    private TurnActionProcessor turnActionProcessor;
    private GameStateHandler gameStateHandler;
    private MessageHandler messageHandler;
    private ShopHandler shopHandler;

    private GameLogic gameLogic;

    @BeforeEach
    public void setUp() {
        Game startedGame = new Game(GAME_ID, 3, 0, 0, 0, 0, 0);
        List<ShopItem> shopItems = List.of(new ShopItem("hpot", "Health potion", 50));

        Message messageA = new Message("messageA", "Help someone", 10, 7, "Gamble");
        List<Message> availableMessages = List.of(messageA);

        GameClient gameClient = mock(GameClient.class);
        gameStateHandler = spy(new GameStateHandler(gameClient));
        turnActionProcessor = mock(TurnActionProcessor.class);
        shopHandler = mock(ShopHandler.class);
        messageHandler = mock(MessageHandler.class);
        TurnRuleEngine turnRuleEngine = mock(TurnRuleEngine.class);


        when(gameClient.startGame()).thenReturn(startedGame);
        when(gameClient.fetchShopItems(GAME_ID)).thenReturn(shopItems);
        when(gameClient.fetchMessages(GAME_ID)).thenReturn(availableMessages);

        gameLogic = new GameLogic(gameStateHandler, shopHandler, messageHandler, turnRuleEngine, turnActionProcessor);
    }

    @Test
    void Verify_game_state_initialized_on_start() {
        gameLogic.startGame();

        verify(gameStateHandler, times(1)).initializeGame();
        verify(shopHandler, times(1)).refreshShopItems(GAME_ID);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void Verify_game_ended_returns_proper_result(boolean finished) {
        gameLogic.startGame();

        when(gameStateHandler.isFinished()).thenReturn(finished);

        assertEquals(finished, gameLogic.isEnded());
    }

    @Nested
    class On_each_turn {
        @Test
        void Messages_are_refreshed() {
            gameLogic.startGame();
            gameLogic.takeTurn();

            verify(messageHandler, times(1)).refreshMessages(GAME_ID);
        }

        @Test
        void Action_is_processed() {
            gameLogic.startGame();
            gameLogic.takeTurn();

            verify(turnActionProcessor, times(1)).processAction(any());
        }
    }
}
