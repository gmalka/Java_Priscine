package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {
    private Connection connection;
    private final String SQL_STATEMENT_MESSAGE = "SELECT * FROM chat.messages WHERE id = ";
    private final String SQL_STATEMENT_USER = "SELECT * FROM chat.users WHERE id = ";
    private final String SQL_STATEMENT_CHATROOM ="SELECT * FROM chat.chatroom WHERE id = ";

    public MessagesRepositoryJdbcImpl(DataSource con)
    {
        try {
            connection = con.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
    @Override
    public Optional<Message> findById(Long id) {
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            pst = connection.prepareStatement(SQL_STATEMENT_MESSAGE + id);
            rs = pst.executeQuery();
            rs.next();

            LocalDateTime time;
            try {
                time = rs.getTimestamp(5).toLocalDateTime();
            }
            catch (Exception ignored)
            {
                time = null;
            }
            Message message = new Message(rs.getInt(1), OutUser(rs.getInt(2)), OutChatroom(rs.getInt(3)), rs.getString(4), time);
            return Optional.of(message);
        }
        catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage());
            System.exit(-1);
        }
        return Optional.empty();
    }

    private User OutUser(int id)
    {
        try {
            PreparedStatement pst = connection.prepareStatement(SQL_STATEMENT_USER + id);
            ResultSet rs = pst.executeQuery();
            rs.next();
            return new User(rs.getInt(1), rs.getString(2), rs.getString(3), null, null);
        }
        catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage());
            System.exit(-1);
        }
        return null;
    }

    private Chatroom OutChatroom(int id)
    {
        try {
            PreparedStatement pst = connection.prepareStatement(SQL_STATEMENT_CHATROOM + id);
            ResultSet rs = pst.executeQuery();
            rs.next();
            return new Chatroom(rs.getInt(1), rs.getString(2), rs.getInt(3), null);
        }
        catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage());
            System.exit(-1);
        }
        return null;
    }
}
