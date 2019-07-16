package com.example.mymessenger.models;

import com.example.mymessenger.tools.MessageType;

import java.io.Serializable;


public class Message implements Serializable {
    private final MessageType type;
    private final String data;
    private static final long serialVersionUID = 6529685098267757690L;

    public Message(MessageType type, String data) {
        this.data = data;
        this.type = type;
    }

    public String getData() {
        return data;
    }


    public MessageType getType() {
        return type;
    }

}
