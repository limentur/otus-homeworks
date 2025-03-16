package ru.otus.listener.homework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import ru.otus.listener.Listener;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;

public class HistoryListener implements Listener, HistoryReader {
    private final Map<Long, Message> history = new HashMap<>();
    @Override
    public void onUpdated(Message msg) {
        var msgCloneBuilder = msg.toBuilder();
        if (msg.getField13() != null) {
            var newObjectForMessage = new ObjectForMessage();
            if (msg.getField13().getData() != null) {
                newObjectForMessage.setData(new ArrayList<>(msg.getField13().getData()));
            }
            msgCloneBuilder.field13(newObjectForMessage);
        }
        history.put(msg.getId(), msgCloneBuilder.build());
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return Optional.of(history.get(id));
    }
}
