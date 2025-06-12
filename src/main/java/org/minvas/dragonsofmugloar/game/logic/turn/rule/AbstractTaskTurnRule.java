package org.minvas.dragonsofmugloar.game.logic.turn.rule;

import org.minvas.dragonsofmugloar.client.dto.Message;
import org.minvas.dragonsofmugloar.game.handler.MessageHandler;
import org.minvas.dragonsofmugloar.game.logic.turn.TurnAction;
import org.minvas.dragonsofmugloar.game.model.GameState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.minvas.dragonsofmugloar.game.logic.turn.TurnAction.Type.DO_TASK;

public abstract class AbstractTaskTurnRule implements TurnRule {
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private final MessageHandler messageHandler;

    public AbstractTaskTurnRule(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    @Override
    public TurnAction apply(GameState gameState) {
        Message message = pickMessage();

        logger.info("Chosen message: {}", message);

        TurnAction turnAction = new TurnAction();
        turnAction.setType(DO_TASK);
        turnAction.setMessageId(message.adId());
        return turnAction;
    }

    private Message pickMessage() {
        List<Message> messages = messageHandler.getMessagesSortedByPriority();
        return messages.getFirst();
    }
}
