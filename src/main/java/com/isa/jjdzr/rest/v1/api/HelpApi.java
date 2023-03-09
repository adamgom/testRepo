package com.isa.jjdzr.rest.v1.api;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class HelpApi {

    @GetMapping("/help")
    public List<String> getAllMessages() {
        return List.of(
         "GET    - localhost:8080/message/getAll",
         "Get    - localhost:8080/message/getMessagesByNameByPathVariable/{name}",
         "Get    - localhost:8080/message/getMessagesByNameByBody, pure String",
         "Get    - localhost:8080/message/getMessageById/{id}",
         "Post   - localhost:8080/message/addMessageByPathVariable/{content}",
         "Post   - localhost:8080/message/addMessagesByBody, JSON list, list name \"contentList\"",
         "Put    - localhost:8080/message/changeMessage/messageId/{id}/newContent/{content}",
         "Delete - localhost:8080/message/deleteMessageById/{id}");
    }
}
