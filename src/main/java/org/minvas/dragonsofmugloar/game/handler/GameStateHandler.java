package org.minvas.dragonsofmugloar.game.handler;

import org.minvas.dragonsofmugloar.client.GameClient;
import org.minvas.dragonsofmugloar.client.dto.Game;
import org.minvas.dragonsofmugloar.client.dto.PurchaseResult;
import org.minvas.dragonsofmugloar.client.dto.TaskResult;
import org.minvas.dragonsofmugloar.game.model.GameState;
import org.springframework.stereotype.Component;

@Component
public class GameStateHandler {
    private GameState gameState;

    private final GameClient gameClient;

    public GameStateHandler(GameClient gameClient) {
        this.gameClient = gameClient;
    }

    public GameState initializeGame() {
        Game game = gameClient.startGame();
        this.gameState = GameState.Builder.from(game).build();
        return gameState;
    }

    public GameState getState() {
        return gameState;
    }

    public boolean isFinished() {
        return gameState.lives() <= 0;
    }

    public GameState updateGameState(PurchaseResult purchaseResult) {
        gameState = GameState.Builder.from(gameState)
                .lives(purchaseResult.lives())
                .gold(purchaseResult.gold())
                .turn(purchaseResult.turn())
                .level(purchaseResult.level())
                .build();
        return gameState;
    }

    public GameState updateGameState(TaskResult taskResult) {
        gameState = GameState.Builder.from(gameState)
                .lives(taskResult.lives())
                .gold(taskResult.gold())
                .turn(taskResult.turn())
                .score(taskResult.score())
                .build();
        return gameState;
    }
}
