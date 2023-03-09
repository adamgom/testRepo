package com.isa.jjdzr.rest.v1.api;

import com.isa.jjdzr.db.Db;
import com.isa.jjdzr.db.DbResponse;
import com.isa.jjdzr.model.Message;
import com.isa.jjdzr.rest.v1.dto.MessageListAddDTO;
import com.isa.jjdzr.rest.v1.response.DefaultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/message")
public class MessageApi {

    @Autowired
    private Db db;

    public MessageApi() {
    }

    @GetMapping("/getAll")
    public DefaultResponse getAllMessages() {
        List<Message> messages = db.getAllMessages();

        if (!messages.isEmpty()) {
            return new DefaultResponse("All messages", messages);
        } else {
            return new DefaultResponse("No messages found");
        }
    }

    @GetMapping("/getMessagesByNameByPathVariable/{name}")
    public DefaultResponse getMessageByNameByPathVariable(@PathVariable(value = "name") String name) {
        List<Message> messages = db.getMessagesByInnerWord(name);

        if (!messages.isEmpty()) {
            return new DefaultResponse("All messages found my name '" + name + "'", messages);
        } else {
            return new DefaultResponse("No messages found by name '" + name + "'");
        }
    }


    @GetMapping(value = "/getMessagesByNameByBody", consumes = {"text/html"})
    public DefaultResponse getMessageByNameByBody(@RequestBody String partialMessage) {
        List<Message> messages = db.getMessagesByInnerWord(partialMessage);

        if (!messages.isEmpty()) {
            return new DefaultResponse("All messages found my name '" + partialMessage + "'", messages);
        } else {
            return new DefaultResponse("No messages found by partial content '" + partialMessage + "'");
        }
    }

    @GetMapping("/getMessageById/{id}")
    public DefaultResponse getMessageById(@PathVariable(value = "id") String id) {
        int idParsed = parseId(id);
        if (idParsed == -1) {
            new DefaultResponse("ERROR: provided value '" + id + "' is not parsable number");
        }

        Optional<Message> message = db.getMessageById(idParsed);

        if (message.isPresent()) {
            return new DefaultResponse("Found by id '" + id + "'", List.of(message.get()));
        } else {
            return new DefaultResponse("No messages found by id '" + id + "'");
        }
    }

    @PostMapping("/addMessageByPathVariable/{content}")
    public DefaultResponse addMessageByPathVariable(@PathVariable(value = "content") String message) {
        DbResponse dbResponse = db.addMessage(message);

        if (dbResponse == DbResponse.ADDED) {
            return new DefaultResponse("Message '" + message + "' added");
        } else {
            throw new UnsupportedOperationException();
        }
    }


    @PostMapping(value = "/addMessagesByBody", consumes = {"application/json"})
    public DefaultResponse addMessagesByBody(@RequestBody MessageListAddDTO messageListAddDTO) {
        List<String> messagesToAdd = messageListAddDTO.getContentList();

        DbResponse dbResponse = db.addMessages(messagesToAdd);

        if (dbResponse == DbResponse.ADDED) {
            return new DefaultResponse("Several message in batch '" + messagesToAdd.size() + "' added");
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @PutMapping(value = {"/changeMessage/messageId/{id}/newContent/{content}", "/getByName2"})
    public DefaultResponse getElementsByNameWithJoinFetch(@PathVariable("id") String id, @PathVariable("content") String content) {
        int idParsed = parseId(id);
        if (idParsed == -1) {
            new DefaultResponse("ERROR: provided value '" + id + "' is not parsable number");
        }

        Optional<Message> oldMessage = db.getMessageById(idParsed);
        String oldMessageContent;
        if (oldMessage.isPresent()) {
            oldMessageContent = oldMessage.get().getMessage();
        } else {
            return new DefaultResponse("No messages found by id '" + id + "'");
        }

        DbResponse dbResponse = db.changeMessageById(idParsed, content);

        if (dbResponse == DbResponse.CHANGED) {
            String response = String.format("Message with id %s and with content '%s' replaced by new content '%s'",
                    id, oldMessageContent, content );
            return new DefaultResponse(response);
        } else {
            return new DefaultResponse("No messages found by id '" + id + "'");
        }
    }

    @DeleteMapping("/deleteMessageById/{id}")
    public DefaultResponse getExactElementByNameCaseSensitive(@PathVariable("id") String id) {
        int idParsed = parseId(id);
        if (idParsed == -1) {
            new DefaultResponse("ERROR: provided value '" + id + "' is not parsable number");
        }

        DbResponse dbResponse = db.deleteMessageById(idParsed);
        if (dbResponse == DbResponse.DELETED) {
            return new DefaultResponse("Message with id '" + id + "' deleted");
        } else if (dbResponse == DbResponse.NOT_FOUND){
            return new DefaultResponse("No messages found by id '" + id + "'");
        } else {
            throw new UnsupportedOperationException();
        }
    }

    private int parseId(String id) {
        try {
            return Integer.parseInt(id);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
