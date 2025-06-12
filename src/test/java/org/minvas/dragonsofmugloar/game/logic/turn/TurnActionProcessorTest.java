package org.minvas.dragonsofmugloar.game.logic.turn;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.minvas.dragonsofmugloar.client.GameClient;
import org.minvas.dragonsofmugloar.client.dto.Game;
import org.minvas.dragonsofmugloar.client.dto.PurchaseResult;
import org.minvas.dragonsofmugloar.client.dto.TaskResult;
import org.minvas.dragonsofmugloar.game.handler.GameStateHandler;
import org.minvas.dragonsofmugloar.game.handler.MessageHandler;
import org.minvas.dragonsofmugloar.game.handler.ShopHandler;

import static org.mockito.Mockito.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class TurnActionProcessorTest {

    PurchaseResult purchaseResult;
    TaskResult taskResult;

    TurnAction taskAction;
    TurnAction buyAction;

    private GameStateHandler gameStateHandler;

    private TurnActionProcessor turnActionProcessor;

    @BeforeEach
    public void setUp() {
        Game game = new Game("tsAsdv", 3, 0, 0, 0, 0, 0);
        taskAction = createTurnAction(TurnAction.Type.DO_TASK, "AbbSrr", null);
        buyAction = createTurnAction(TurnAction.Type.GO_SHOPPING, null, "hpot");

        purchaseResult = new PurchaseResult(true, 10, 5, 7, 3);
        taskResult = new TaskResult(true, 8, 50, 120, 5000, 5, "Task response message");

        GameClient gameClient = mock(GameClient.class);
        gameStateHandler = spy(new GameStateHandler(gameClient));
        MessageHandler messageHandler = mock(MessageHandler.class);
        ShopHandler shopHandler = mock(ShopHandler.class);

        when(messageHandler.solveMessage(game.gameId(), taskAction.getMessageId())).thenReturn(taskResult);
        when(shopHandler.buyItem(game.gameId(), buyAction.getItemId())).thenReturn(purchaseResult);
        when(gameClient.startGame()).thenReturn(game);

        turnActionProcessor = new TurnActionProcessor(messageHandler, shopHandler, gameStateHandler);

        gameStateHandler.initializeGame();
    }

    @Test
    void Process_task_action() {
        turnActionProcessor.processAction(taskAction);
        verify(gameStateHandler).updateGameState(taskResult);
    }

    @Test
    void Process_buy_action() {
        turnActionProcessor.processAction(buyAction);
        verify(gameStateHandler).updateGameState(purchaseResult);
    }

    private TurnAction createTurnAction(TurnAction.Type actionType, String messageId, String itemId) {
        TurnAction turnAction = new TurnAction();
        turnAction.setType(actionType);
        turnAction.setMessageId(messageId);
        turnAction.setItemId(itemId);
        return turnAction;
    }
}
