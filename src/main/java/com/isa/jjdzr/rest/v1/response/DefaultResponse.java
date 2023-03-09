package com.isa.jjdzr.rest.v1.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.isa.jjdzr.model.Message;

import java.util.List;

public class DefaultResponse {
    private String description;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Message> messages;

    public DefaultResponse(String description, List<Message> messages) {
        this.description = description;
        this.messages = messages;
    }

    public DefaultResponse(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public List<Message> getMessages() {
        return messages;
    }
}
