package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;

import java.util.Optional;

public interface MessagesRepository {
    void                save(Message message);
    void                update(Message message);
    Optional<Message>   findById(Long id);
}
