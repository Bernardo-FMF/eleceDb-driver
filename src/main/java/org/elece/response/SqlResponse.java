package org.elece.response;

import java.util.ArrayList;
import java.util.List;

public class SqlResponse {
    private final List<String> messages;

    public SqlResponse() {
        this.messages = new ArrayList<>();
    }

    public void addMessage(String message) {
        this.messages.add(message);
    }

    public List<String> getMessages() {
        return messages;
    }
}
