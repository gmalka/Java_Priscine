package edu.school21.chat.models;

import java.sql.SQLException;
import java.util.List;

public class User {
    private long id;
    private String login;
    private String password;
    private List<Chatroom> created_rooms;
    private List<Chatroom> socializes_rooms;

    public User(long id, String login, String password, List<Chatroom> created_rooms, List<Chatroom> socializes_rooms) throws SQLException {
        if (login == null)
            throw new SQLException();
        this.id = id;
        this.login = login;
        this.password = password;
        this.created_rooms = created_rooms;
        this.socializes_rooms = socializes_rooms;
    }

    @Override
    public String toString() {
        return "author={\nid=" + id + ",\nlogin=\"" + login + "\",\npassword=\"" + password + "\",\ncreatedRooms=" + created_rooms + ",\nrooms=" + socializes_rooms + "\n},";
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
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
