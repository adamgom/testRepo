package com.isa.jjdzr.rest.v1.dto;

import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public class MessageListAddDTO {
    private List<String> contentList;

    public MessageListAddDTO(List<String> contentList) {
            this.contentList = contentList;
    }

    public MessageListAddDTO() {
    }

    public List<String> getContentList() {
        return contentList;
    }

    public void setContentList(List<String> contentList) {
        this.contentList = contentList;
    }
}
