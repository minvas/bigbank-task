package org.minvas.dragonsofmugloar.game.handler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.minvas.dragonsofmugloar.client.GameClient;
import org.minvas.dragonsofmugloar.client.dto.Game;
import org.minvas.dragonsofmugloar.client.dto.PurchaseResult;
import org.minvas.dragonsofmugloar.client.dto.TaskResult;
import org.minvas.dragonsofmugloar.game.model.GameState;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class GameStateHandlerTest {

    private GameClient gameClient;
    private GameStateHandler gameStateHandler;

    @BeforeEach
    public void setUp() {
        gameClient = mock(GameClient.class);
        gameStateHandler = new GameStateHandler(gameClient);
    }

    @Test
    void Game_state_gets_initialized() {
        Game game = new Game("AsbbaS", 1, 10, 5, 500, 5700, 1);
        when(gameClient.startGame()).thenReturn(game);

        GameState gameState = gameStateHandler.initializeGame();

        assertMatch(game, gameState);
    }

    @Test
    void Game_is_finished_if_no_lives_left() {
        Game game = new Game("AsbbaS", 0, 10, 5, 500, 5700, 1);
        when(gameClient.startGame()).thenReturn(game);

        gameStateHandler.initializeGame();

        assertTrue(gameStateHandler.isFinished());
    }

    @Test
    void Game_is_not_finished_if_lives_number_is_positive() {
        Game game = new Game("AsbbaS", 1, 10, 5, 500, 5700, 1);
        when(gameClient.startGame()).thenReturn(game);

        gameStateHandler.initializeGame();

        assertFalse(gameStateHandler.isFinished());
    }

    @Test
    void Game_state_is_updated_based_on_purchase_result() {
        Game game = new Game("AsbbaS", 1, 10, 5, 500, 5700, 1);
        when(gameClient.startGame()).thenReturn(game);

        gameStateHandler.initializeGame();

        PurchaseResult purchaseResult = new PurchaseResult(true, 1, 2, 6, 2);
        GameState gameState = gameStateHandler.updateGameState(purchaseResult);

        assertEquals(game.gameId(), gameState.id());
        assertEquals(game.score(), gameState.score());
        assertEquals(purchaseResult.lives(), gameState.lives());
        assertEquals(purchaseResult.gold(), gameState.gold());
        assertEquals(purchaseResult.level(), gameState.level());
        assertEquals(purchaseResult.turn(), gameState.turn());
    }

    @Test
    void Game_state_is_updated_based_on_task_result() {
        Game game = new Game("AsbbaS", 2, 10, 5, 500, 5700, 1);
        when(gameClient.startGame()).thenReturn(game);

        gameStateHandler.initializeGame();

        TaskResult taskResult = new TaskResult(true, 2, 20, 550, 5700, 2, "Task message");
        GameState gameState = gameStateHandler.updateGameState(taskResult);

        assertEquals(game.gameId(), gameState.id());
        assertEquals(game.level(), gameState.level());
        assertEquals(taskResult.score(), gameState.score());
        assertEquals(taskResult.lives(), gameState.lives());
        assertEquals(taskResult.gold(), gameState.gold());
        assertEquals(taskResult.turn(), gameState.turn());
    }

    @Test
    void Returns_game_state() {
        Game game = new Game("AsbbaS", 1, 10, 5, 500, 5700, 1);
        when(gameClient.startGame()).thenReturn(game);

        gameStateHandler.initializeGame();

        assertMatch(game, gameStateHandler.getState());
    }

    private void assertMatch(Game game, GameState gameState) {
        assertEquals(game.gameId(), gameState.id());
        assertEquals(game.lives(), gameState.lives());
        assertEquals(game.gold(), gameState.gold());
        assertEquals(game.level(), gameState.level());
        assertEquals(game.score(), gameState.score());
        assertEquals(game.turn(), gameState.turn());
    }
}
