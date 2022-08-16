package edu.school21.chat.models;

import javax.swing.text.AttributeSet;
import java.time.LocalDateTime;

public class Message{
    private Long id;
    private User author;
    private Chatroom room;
    private String text;
    private LocalDateTime data;

    public Message(Long id, User author, Chatroom room, String text, LocalDateTime data) {
        this.id = id;
        this.author = author;
        this.room = room;
        this.text = text;
        this.data = data;
    }

    @Override
    public String toString() {
        return "Message{" + "\n" +
                "id=" + id + "\n" +
                author + "\n" +
                room + "\n" +
                ", text='" + text + '\'' + "\n" +
                ", data=" + data + "\n" +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Chatroom getRoom() {
        return room;
    }

    public void setRoom(Chatroom room) {
        this.room = room;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }
}
