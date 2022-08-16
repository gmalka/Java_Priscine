package school21.spring.service.repositories;

import school21.spring.service.models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class UsersRepositoryJdbcImpl implements UsersRepository {
    private Connection connection;

    public UsersRepositoryJdbcImpl(DataSource dataSource) throws SQLException {
        connection = dataSource.getConnection();
    }

    @Override
    public Optional<User> findById(Long id) {
        try {
            ResultSet set = connection.createStatement().executeQuery("SELECT * FROM userTable");
            while (set.next())
            {
                if (set.getLong("identifier") == id)
                {
                    return Optional.of(new User(set.getLong("identifier"), set.getString("email")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        List<User> list = new ArrayList<>();

        try {
            ResultSet set = connection.createStatement().executeQuery("SELECT * FROM userTable");
            while (set.next())
            {
                list.add(new User(set.getLong("identifier"), set.getString("email")));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(User entity) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("INSERT INTO userTable (email) VALUES (?)");
            statement.setString(1, entity.getEmail());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(User entity) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE userTable SET email=" + entity.getEmail() + " WHERE identifier=" + entity.getIdentifier());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        try {
            connection.createStatement().execute("DELETE FROM userTable WHERE identifier = " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try {
            ResultSet set = connection.createStatement()
                    .executeQuery("SELECT * FROM userTable WHERE email=" + email);

            if (set.next()) {
                User user = new User();

                user.setIdentifier(set.getLong("identifier"));
                user.setEmail(set.getString("email"));

                return Optional.of(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }
}
