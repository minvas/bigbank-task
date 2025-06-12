package org.minvas.dragonsofmugloar.game.logic.turn;

import org.minvas.dragonsofmugloar.client.dto.PurchaseResult;
import org.minvas.dragonsofmugloar.client.dto.TaskResult;
import org.minvas.dragonsofmugloar.game.handler.GameStateHandler;
import org.minvas.dragonsofmugloar.game.handler.MessageHandler;
import org.minvas.dragonsofmugloar.game.handler.ShopHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TurnActionProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(TurnActionProcessor.class);


    private final MessageHandler messageHandler;
    private final ShopHandler shopHandler;
    private final GameStateHandler gameStateHandler;

    public TurnActionProcessor(MessageHandler messageHandler,
                               ShopHandler shopHandler,
                               GameStateHandler gameStateHandler) {
        this.messageHandler = messageHandler;
        this.shopHandler = shopHandler;
        this.gameStateHandler = gameStateHandler;
    }

    public void processAction(TurnAction turnAction) {
        switch (turnAction.getType()) {
            case DO_TASK -> doTask(turnAction);
            case GO_SHOPPING -> goShopping(turnAction);
        }
    }

    private void doTask(TurnAction turnAction) {
        String gameId = gameStateHandler.getState().id();
        TaskResult taskResult = messageHandler.solveMessage(gameId, turnAction.getMessageId());
        gameStateHandler.updateGameState(taskResult);
        LOGGER.info("Updated game state based on task result: {}", taskResult);
    }

    private void goShopping(TurnAction turnAction) {
        String gameId = gameStateHandler.getState().id();
        PurchaseResult purchaseResult = shopHandler.buyItem(gameId, turnAction.getItemId());
        gameStateHandler.updateGameState(purchaseResult);
        LOGGER.info("Updated game state based on purchase result: {}", purchaseResult);
    }
}
