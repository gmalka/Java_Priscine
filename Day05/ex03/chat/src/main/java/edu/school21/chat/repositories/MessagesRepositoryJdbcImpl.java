package edu.school21.chat.repositories;

import edu.school21.chat.Exception.NotSavedSubEntityException;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {
    private Connection connection;
    private final String QUERY_TEMPLATE = "INSERT INTO chat.messages (author, room, text, time) VALUES (?, ?, ?, ?) RETURNING *";
    private final String UPDATE_STATEMENT = "UPDATE chat.messages SET author = ?, room = ?, text = ?, time = ? WHERE id = ?";
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

    public void save(Message message)
    {
        try {
            OutUser(message.getAuthor().getId());
            OutChatroom(message.getRoom().getId());
            PreparedStatement query = connection.prepareStatement(QUERY_TEMPLATE);
            query.setLong(1, message.getAuthor().getId());
            query.setLong(2, message.getRoom().getId());
            query.setString(3, message.getText());
            try {
                query.setTimestamp(4, Timestamp.valueOf(message.getData()));
            }
            catch (Exception ignored)
            {
                query.setTimestamp(4, null);
            }
            ResultSet resultSet = query.executeQuery();
            resultSet.next();
            message.setId(resultSet.getLong(1));
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
            Message message = new Message(rs.getLong(1), OutUser(rs.getInt(2)), OutChatroom(rs.getInt(3)), rs.getString(4), time);
            return Optional.of(message);
        }
        catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage());
            System.exit(-1);
        }
        return Optional.empty();
    }

    public void    update(Message message)
    {
        try
        {
            PreparedStatement statement = connection.prepareStatement(UPDATE_STATEMENT);
            statement.setLong(1, message.getAuthor().getId());
            statement.setLong(2, message.getRoom().getId());
            statement.setString(3, message.getText());
            try {
                statement.setTimestamp(4, Timestamp.valueOf(message.getData()));
            }
            catch (Exception e) {
                statement.setTimestamp(4, null);
            }
            statement.setLong(5, message.getId());
            statement.execute();
        }
        catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage());
            System.exit(-1);
        }
    }

    private User OutUser(long id) throws NotSavedSubEntityException
    {
        try {
            PreparedStatement pst = connection.prepareStatement(SQL_STATEMENT_USER + id);
            ResultSet rs = pst.executeQuery();
            rs.next();
            return new User(rs.getInt(1), rs.getString(2), rs.getString(3), null, null);
        }
        catch (Exception e)
        {
            throw new NotSavedSubEntityException();
        }
    }

    private Chatroom OutChatroom(long id) throws NotSavedSubEntityException
    {
        try {
            PreparedStatement pst = connection.prepareStatement(SQL_STATEMENT_CHATROOM + id);
            ResultSet rs = pst.executeQuery();
            rs.next();
            return new Chatroom(rs.getInt(1), rs.getString(2), OutUser(rs.getInt(3)), null);
        }
        catch (Exception e)
        {
            throw new NotSavedSubEntityException();
        }
    }
}
