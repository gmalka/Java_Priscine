package edu.school21.chat.program;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.Message;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Scanner;

public class Program {
    public static final String BD_PATH = "jdbc:postgresql://localhost:5433/postgres";
    public static final String BD_USER_AND_PAS = "postgres";
    public static final String PATH_TO_SCHEMA = "../src/main/resources/schema.sql";
    public static final String PATH_TO_INSERT = "../src/main/resources/data.sql";

    public static void main(String[] args) {
        try {
            System.out.println(System.getProperty("user.dir"));
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
            Scanner scanner = new Scanner(System.in);
            while (!scanner.hasNextInt())
            {
                scanner.next();
            }
            Optional<Message> o = t.findById(scanner.nextLong());
            scanner.close();
            connection.close();
            Message m = o.get();
            System.out.println("id = " + m.getId());
            System.out.println(m.getAuthor());
            System.out.println(m.getRoom());
            System.out.println("Data = " + m.getData().format(DateTimeFormatter.ofPattern("MM/MM/yy HH:mm")));
            System.out.println("Message = " + m.getText());
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
