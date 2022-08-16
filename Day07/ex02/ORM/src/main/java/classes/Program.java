package classes;

import com.zaxxer.hikari.HikariDataSource;
import models.User;

import java.sql.SQLException;

public class Program {
    public static final String DB_URL = "jdbc:postgresql://localhost:5433/postgres";
    public static final String DB_USER = "postgres";

    public static void main(String[] args) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(DB_URL);
        dataSource.setUsername(DB_USER);
        dataSource.setPassword(DB_USER);


        try {
            OrmManager ormManager = new OrmManager(dataSource);
            User user = new User("bob", "fisher", 100);
            ormManager.save(user);
            user.setId(1L);
            user.setFirstName("Tom");
            user.setLastName("Hardy");
            user.setAge(35);
            ormManager.update(user);
            User user1 = ormManager.findById(1L, User.class);
            System.out.println(user1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
