package edu.school21.chat.program;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class Program {
    public static final String BD_PATH = "jdbc:postgresql://localhost:5433/postgres";
    public static final String BD_USER_AND_PAS = "postgres";
    public static final String PATH_TO_SCHEMA = "../src/main/resources/schema.sql";
    public static final String PATH_TO_INSERT = "../src/main/resources/data.sql";

    public static void main(String[] args) {
        try {
            HikariConfig cfg = new HikariConfig();
            cfg.setJdbcUrl(BD_PATH);
            cfg.setUsername(BD_USER_AND_PAS);
            cfg.setPassword(BD_USER_AND_PAS);
            HikariDataSource db = new HikariDataSource(cfg);

            Connection connection = db.getConnection();
            if (connection == null)
            {
                System.err.println("Can't connect to database");
                System.exit(-1);
            }
            addToSQL(connection, PATH_TO_SCHEMA);
            addToSQL(connection, PATH_TO_INSERT);
            MessagesRepositoryJdbcImpl t = new MessagesRepositoryJdbcImpl(db);
            User creator = new User(2L, "user", "user", new ArrayList(), new ArrayList());
            User author = creator;
            Chatroom room = new Chatroom(5L, "room", creator, new ArrayList());
            Message message = new Message(null, author, room, "Hello!", LocalDateTime.now());
            MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(db);
            try {
                messagesRepository.save(message);
                messagesRepository.save(message);
            }
            catch (Exception e)
            {
                System.out.println("Error :" + e.getMessage());
                System.exit(-1);
            }
            System.out.println(message);
            connection.close();
        }
        catch(Exception ex){
            ex.printStackTrace();
            System.err.println("Connection failed...");
            System.exit(-1);
        }
    }

    public static void addToSQL(Connection connection, String path)
    {
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            StringBuilder str = new StringBuilder();
            while (reader.ready())
            {
                str.append(reader.readLine());
            }
            for(String s : str.toString().split(";")) {
                connection.createStatement().execute(s);
            }
        }
        catch (Exception e)
        {
            System.err.println("Something wrong width this DB");
            System.exit(-1);
        }
    }
}
