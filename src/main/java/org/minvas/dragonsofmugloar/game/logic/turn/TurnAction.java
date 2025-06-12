package org.minvas.dragonsofmugloar.game.logic.turn;

public class TurnAction {
    public enum Type {
        DO_TASK,
        GO_SHOPPING
    }

    private Type type;
    private String itemId;
    private String messageId;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
