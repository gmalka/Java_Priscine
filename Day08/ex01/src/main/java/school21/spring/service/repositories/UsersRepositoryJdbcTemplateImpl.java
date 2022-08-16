package school21.spring.service.repositories;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import school21.spring.service.models.User;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static java.sql.Types.BIGINT;
import static java.sql.Types.VARCHAR;

public class UsersRepositoryJdbcTemplateImpl implements UsersRepository{
    private JdbcTemplate jdbcTemplate;

    public UsersRepositoryJdbcTemplateImpl(DataSource dataSource)
    {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<User> findById(Long id) {
        User user =  jdbcTemplate
                .query("SELECT * FROM userTable WHERE identifier=?",
                new Object[]{id},
                new int[]{BIGINT},
                new BeanPropertyRowMapper<>(User.class))
                .stream().findAny().orElse(null);
        if (user != null)
            return Optional.of(user);
        else
            return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query("SELECT * FROM userTable", new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public void save(User entity) {
        jdbcTemplate.update("INSERT INTO userTable (email) VALUES (?)", entity.getEmail());
    }

    @Override
    public void update(User entity) {
        jdbcTemplate.update("UPDATE userTable SET email=? WHERE identifier=?", entity.getEmail(), entity.getIdentifier());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM userTable WHERE identifier=?", id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jdbcTemplate.query("SELECT FROM userTable WHERE email=?", new Object[] {email}, new int[] {VARCHAR}, new BeanPropertyRowMapper<>(User.class)).stream().findAny();
    }
}
