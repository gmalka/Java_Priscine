package edu.school21.chat.models;

import java.util.List;

public class User {
    private int login;
    private String password;
    private List<Chatroom> created_rooms;
    private List<Chatroom> socializes_rooms;

    public User(int login, String password, List<Chatroom> created_rooms, List<Chatroom> socializes_rooms) {
        this.login = login;
        this.password = password;
        this.created_rooms = created_rooms;
        this.socializes_rooms = socializes_rooms;
    }

    public int getLogin() {
        return login;
    }

    public void setLogin(int login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Chatroom> getCreated_rooms() {
        return created_rooms;
    }

    public void setCreated_rooms(List<Chatroom> created_rooms) {
        this.created_rooms = created_rooms;
    }

    public List<Chatroom> getSocializes_rooms() {
        return socializes_rooms;
    }

    public void setSocializes_rooms(List<Chatroom> socializes_rooms) {
        this.socializes_rooms = socializes_rooms;
    }
}
