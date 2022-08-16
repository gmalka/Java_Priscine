package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class UsersRepositoryJdbcImpl implements UsersRepository {
    private static String QUERY_STATEMENT = "SELECT (SELECT COUNT(id) FROM chat.users) as sum, users.id as user_id, users.login as user_login, users.password as user_pas, chatroom.id as room_id, chatroom.name as room_name, chatroom.owner as room_owner, a1.id as room_user_id, a1.name as room_user_name, a1.owner as room_user_owner\n" +
            "    FROM chat.users\n" +
            "             LEFT JOIN chat.chatroom ON users.id = chatroom.owner\n" +
            "             LEFT JOIN chat.messages ON users.id = messages.author\n" +
            "             LEFT JOIN chat.chatroom a1 ON messages.room = a1.id\n" +
            "    ORDER BY users.id, chatroom.id";
    private Connection connection;

    public UsersRepositoryJdbcImpl(DataSource dataSource)
    {
        try {
            connection = dataSource.getConnection();
        }
        catch (Exception e)
        {
            System.err.println("Error: " + e.getMessage());
            System.exit(-1);
        }
    }

    @Override
    public List<User> findAll(int page, int size) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        HashMap<Long, User> list = new HashMap<>();
        try {
            preparedStatement = connection.prepareStatement(QUERY_STATEMENT);
            resultSet = preparedStatement.executeQuery();
            int pages = (page - 1) * size + 1, sum;
            long id = 1, prev_id = -1, current_id = 0;
            resultSet.next();

            sum = resultSet.getInt(1);
            if (pages > sum) {
                return null;
            }
            while (id < pages)
            {
                if (prev_id != (current_id = resultSet.getLong("user_id")))
                {
                    id++;
                    prev_id = current_id;
                }
                if (!resultSet.next())
                {
                    throw new SQLException();
                }
            }
            pages += size;

            while (id < pages) {
                Long user_id = resultSet.getLong(2);
                if (list.get(user_id) != null)
                {
                    Long chatroom_id = resultSet.getLong("room_id");
                    boolean finded = false;
                    for (Chatroom room : list.get(user_id).getCreated_rooms())
                    {
                        if (room.getId() == chatroom_id) {
                            finded = true;
                            break;
                        }
                    }
                    if (!finded) {
                        list.get(user_id).getCreated_rooms().add(createChatroom(resultSet, 1));
                    }
                    finded = false;
                    chatroom_id = resultSet.getLong("room_user_id");
                    for (Chatroom room : list.get(user_id).getSocializes_rooms())
                    {
                        if (room.getId() == chatroom_id) {
                            finded = true;
                            break;
                        }
                    }
                    if (!finded) {
                        list.get(user_id).getSocializes_rooms().add(createChatroom(resultSet, 2));
                    }
                }
                else {
                    list.put(user_id, getNewUser(resultSet));
                    id++;
                }
                if (!resultSet.next())
                {
                    break;
                }
            }
            return new ArrayList<>(list.values());
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                }
            }
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
        return null;
    }

    private Chatroom createChatroom(ResultSet rs, int action) {
        Chatroom chatroom = null;
        try {
            if (action == 1) {
                chatroom =  new Chatroom(rs.getLong("room_id"), rs.getString("room_name"), null, null);
            } else if (action == 2) {
                chatroom =  new Chatroom(rs.getLong("room_user_id"), rs.getString("room_user_name"), null, null);
            }
        } catch (SQLException e) {
            return null;
        }
        return chatroom;
    }

    private User getNewUser(ResultSet resultSet)
    {
        User user = null;
        try {
            List<Chatroom> owner = new ArrayList<>();
            List<Chatroom> socializer = new ArrayList<>();
            owner.add(createChatroom(resultSet, 1));
            socializer.add(createChatroom(resultSet, 2));
            user =  new User(resultSet.getLong("user_id"), resultSet.getString("user_login"), resultSet.getString("user_pas"), owner, socializer);
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
            System.exit(-1);
        }
        return user;
    }
}