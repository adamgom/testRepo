package com.isa.jjdzr.db;

import com.isa.jjdzr.model.Message;

import java.util.List;
import java.util.Optional;

public interface Db {
    List<Message> getAllMessages();

    List<Message> getMessagesByInnerWord(String innerWord);

    Optional<Message> getMessageById(int id);

    DbResponse addMessage(String messageText);

    DbResponse addMessages(List<String> messages);

    DbResponse changeMessageById(int id, String newMessageText);

    DbResponse deleteMessageById(int id);
}