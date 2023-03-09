package com.isa.jjdzr.db;

import com.isa.jjdzr.model.Message;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryDbImpl implements Db {

    private List<Message> messages;

    public InMemoryDbImpl() {
        this.messages = new ArrayList<>();
    }

    public InMemoryDbImpl(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public List<Message> getAllMessages() {
        return Collections.unmodifiableList(messages);
    }

    @Override
    public List<Message> getMessagesByInnerWord(String innerWord) {
        return getMessagesContainingPhrase(innerWord);
    }

    @Override
    public Optional<Message> getMessageById(int id) {
        return getOneMessageById(id);
    }

    @Override
    public DbResponse addMessage(String message) {
        int nextId = getNextId();
        addOneMessage(nextId, message);

        return DbResponse.ADDED;
    }

    @Override
    public DbResponse addMessages(List<String> messages) {
        int nextId = getNextId();
        for (String message : messages) {
            addOneMessage(nextId, message);
            nextId++;
        }

        return DbResponse.ADDED;
    }

    @Override
    public DbResponse changeMessageById(int id, String newMessage) {
        Optional<Message> messageToChange = getOneMessageById(id);
        
        if (messageToChange.isPresent()) {
            messageToChange.get().setMessage(newMessage);
            return DbResponse.CHANGED;
        } else {
            return DbResponse.NOT_FOUND;
        }
    }

    @Override
    public DbResponse deleteMessageById(int id) {
        Optional<Message> oneMessage = getOneMessageById(id);

        if (oneMessage.isPresent()) {
            messages.remove(oneMessage.get());
            return DbResponse.DELETED;
        } else {
            return DbResponse.NOT_FOUND;
        }
    }

    private List<Message> getMessagesContainingPhrase(String phrase) {
        return messages.stream()
                .filter(message -> message.getMessage().contains(phrase))
                .collect(Collectors.toList());
    }

    private int getNextId() {
        OptionalInt lastId = messages.stream()
                .mapToInt(Message::getId)
                .max();

        if (lastId.isPresent()) {
            return lastId.getAsInt() + 1;
        } else {
            return 1;
        }
    }

    private void addOneMessage(int id, String messageText) {
        Message newMessage = new Message(id, messageText);
        messages.add(newMessage);
    }

    private Optional<Message> getOneMessageById(int id) {
        List<Message> innerList = messages.stream()
                .filter(message -> message.getId() == id)
                .collect(Collectors.toList());

        if (innerList.isEmpty()) {
            return Optional.empty();
        } else if (innerList.size() == 1) {
            return Optional.of(innerList.get(0));
        } else {
            throw new UnsupportedOperationException("Error in database, more than one message for provided id");
        }
    }
}
