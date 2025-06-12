package org.minvas.dragonsofmugloar.game.handler;

import org.minvas.dragonsofmugloar.client.GameClient;
import org.minvas.dragonsofmugloar.client.dto.Message;
import org.minvas.dragonsofmugloar.client.dto.TaskResult;
import org.minvas.dragonsofmugloar.game.model.TaskDifficulty;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class MessageHandler {
    private static final Pattern[] SHADY_BUSINESS_PATTERNS = {Pattern.compile("\\bsteal\\b", Pattern.CASE_INSENSITIVE)};

    private List<Message> messages;

    private final GameClient gameClient;

    public MessageHandler(GameClient gameClient) {
        this.gameClient = gameClient;
    }

    public void refreshMessages(String gameId) {
        this.messages = gameClient.fetchMessages(gameId);
    }

    public TaskResult solveMessage(String gameId, String messageId) {
        return gameClient.solveMessage(gameId, messageId);
    }

    public List<Message> getMessagesSortedByPriority() {
        List<Message> result = new ArrayList<>(messages);
        result.sort(
                Comparator
                        .comparing(MessageHandler::isShadyBusiness)
                        .thenComparing(MessageHandler::resolveDifficultyLevel)
                        .thenComparing(Message::expiresIn)
                        .thenComparing(Message::reward, Comparator.reverseOrder())
        );
        return result;
    }

    private static int resolveDifficultyLevel(Message message) {
        return TaskDifficulty.fromProbabilityDescription(message.probability()).getLevel();
    }

    private static boolean isShadyBusiness(Message message) {
        if (message.message() != null) {
            for (Pattern pattern : SHADY_BUSINESS_PATTERNS) {
                Matcher matcher = pattern.matcher(message.message());
                if (matcher.find()) {
                    return true;
                }
            }
        }
        return false;
    }
}
