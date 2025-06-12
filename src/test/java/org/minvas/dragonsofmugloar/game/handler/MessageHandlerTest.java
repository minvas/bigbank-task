package org.minvas.dragonsofmugloar.game.handler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.minvas.dragonsofmugloar.client.GameClient;
import org.minvas.dragonsofmugloar.client.dto.Message;
import org.minvas.dragonsofmugloar.client.dto.TaskResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.minvas.dragonsofmugloar.game.model.TaskDifficulty.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MessageHandlerTest {
    private static final String GAME_ID = "oSbEqq";

    private List<Message> expectedMessages;

    private GameClient gameClient;
    private MessageHandler messageHandler;

    private Message message1;
    private Message message2;
    private Message message3;
    private Message message4;
    private Message message5;
    private Message message6;

    private TaskResult expectedTaskResult;

    @BeforeEach
    public void setUp() {
        gameClient = mock(GameClient.class);
        messageHandler = new MessageHandler(gameClient);

        message1 = new Message("m1", "Steal cow", 1000, 1, SURE_THING.getProbabilityDescription());
        message2 = new Message("m2", "Climb a tree", 10, 7, GAMBLE.getProbabilityDescription());
        message3 = new Message("m3", "Help someone", 10, 7, SURE_THING.getProbabilityDescription());
        message4 = new Message("m4", "Help someone", 10, 7, PLAYING_WITH_FIRE.getProbabilityDescription());
        message5 = new Message("m5", "Climb a tree", 10, 6, GAMBLE.getProbabilityDescription());
        message6 = new Message("m6", "Climb a tree", 15, 6, GAMBLE.getProbabilityDescription());

        expectedMessages = List.of(message1, message2, message3, message4);

        expectedTaskResult = new TaskResult(true, 2, 50, 750, 8000, 5, "Task done");

        when(gameClient.fetchMessages(GAME_ID)).thenReturn(expectedMessages);
        when(gameClient.solveMessage(GAME_ID, "m1")).thenReturn(expectedTaskResult);
    }

    @Test
    void Refreshes_messages() {
        messageHandler.refreshMessages(GAME_ID);

        List<Message> messages = messageHandler.getMessagesSortedByPriority();
        assertEquals(expectedMessages.size(), messages.size());
        assertTrue(messages.containsAll(expectedMessages));
    }

    @Test
    void Solves_message() {
        messageHandler.refreshMessages(GAME_ID);

        TaskResult taskResult = messageHandler.solveMessage(GAME_ID, "m1");
        assertEquals(expectedTaskResult.success(), taskResult.success());
        assertEquals(expectedTaskResult.lives(), taskResult.lives());
        assertEquals(expectedTaskResult.gold(), taskResult.gold());
        assertEquals(expectedTaskResult.score(), taskResult.score());
        assertEquals(expectedTaskResult.highScore(), taskResult.highScore());
        assertEquals(expectedTaskResult.turn(), taskResult.turn());
        assertEquals(expectedTaskResult.message(), taskResult.message());
    }

    @Nested
    class Sorting_messages_by_priority {
        @Test
        void Gives_lowest_priority_if_associated_with_shady_business() {
            when(gameClient.fetchMessages(GAME_ID)).thenReturn(List.of(message1, message2, message3, message4, message5));
            messageHandler.refreshMessages(GAME_ID);

            List<Message> messages = messageHandler.getMessagesSortedByPriority();
            assertSame(message1, messages.getLast());
        }

        @Test
        void Gives_higher_priority_if_difficulty_level_is_low() {
            when(gameClient.fetchMessages(GAME_ID)).thenReturn(List.of(message1, message2, message3, message4, message5));
            messageHandler.refreshMessages(GAME_ID);

            List<Message> messages = messageHandler.getMessagesSortedByPriority();
            assertSame(message3, messages.getFirst());
        }

        @Test
        void Gives_higher_priority_if_difficulty_level_is_same_but_expires_sooner() {
            when(gameClient.fetchMessages(GAME_ID)).thenReturn(List.of(message1, message2, message5));

            messageHandler.refreshMessages(GAME_ID);
            List<Message> messages = messageHandler.getMessagesSortedByPriority();
            assertSame(message5, messages.getFirst());
        }

        @Test
        void Gives_higher_priority_if_difficulty_level_and_expiration_are_same_but_reward_is_bigger() {
            when(gameClient.fetchMessages(GAME_ID)).thenReturn(List.of(message1, message2, message5, message6));

            messageHandler.refreshMessages(GAME_ID);
            List<Message> messages = messageHandler.getMessagesSortedByPriority();
            assertSame(message6, messages.getFirst());
        }
    }
}
