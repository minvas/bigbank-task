package org.minvas.dragonsofmugloar.game.logic;

import org.minvas.dragonsofmugloar.game.handler.GameStateHandler;
import org.minvas.dragonsofmugloar.game.handler.MessageHandler;
import org.minvas.dragonsofmugloar.game.handler.ShopHandler;
import org.minvas.dragonsofmugloar.game.logic.turn.TurnAction;
import org.minvas.dragonsofmugloar.game.logic.turn.TurnActionProcessor;
import org.minvas.dragonsofmugloar.game.logic.turn.TurnRuleEngine;
import org.minvas.dragonsofmugloar.game.model.GameState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class GameLogic {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameLogic.class);

    private final GameStateHandler gameStateHandler;
    private final ShopHandler shopHandler;
    private final MessageHandler messageHandler;
    private final TurnRuleEngine turnRuleEngine;
    private final TurnActionProcessor turnActionProcessor;

    public GameLogic(GameStateHandler gameStateHandler,
                     ShopHandler shopHandler,
                     MessageHandler messageHandler,
                     TurnRuleEngine turnRuleEngine,
                     TurnActionProcessor turnActionProcessor) {
        this.gameStateHandler = gameStateHandler;
        this.shopHandler = shopHandler;
        this.messageHandler = messageHandler;
        this.turnRuleEngine = turnRuleEngine;
        this.turnActionProcessor = turnActionProcessor;
    }

    public void startGame() {
        GameState gameState = gameStateHandler.initializeGame();
        shopHandler.refreshShopItems(gameState.id());

        LOGGER.info("Game initialized [gameState={}]", getGameState());
    }

    public void takeTurn() {
        refreshMessages();
        processTurnAction();
        LOGGER.info("Game state after a turn: {}", getGameState());
    }

    public boolean isEnded() {
        return gameStateHandler.isFinished();
    }

    private void processTurnAction() {
        TurnAction turnAction = turnRuleEngine.determineTurnAction(getGameState());
        turnActionProcessor.processAction(turnAction);
    }

    private void refreshMessages() {
        messageHandler.refreshMessages(getGameState().id());
    }

    private GameState getGameState() {
        return gameStateHandler.getState();
    }
}
