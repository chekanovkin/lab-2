package com.example.lab1.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "msg")
public class Message {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String text;

    private String header;

    private String recipient;

    public Message(String recipient, String header, String text) {
        this.recipient = recipient;
        this.text = text;
        this.header = header;
    }

    public Message() {
    }
}
