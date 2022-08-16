package edu.school21.chat.models;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;

public class Program {
    private static final String BD_PATH = "jdbc:postgresql://localhost:5433/postgres";
    private static final String BD_USER_AND_PAS = "postgres";
    private static final String PATH_TO_SCHEMA = "../src/main/resources/schema.sql";
    private static final String PATH_TO_INSERT = "../src/main/resources/data.sql";

    public static void main(String[] args) {
        try{
            Class.forName("org.postgresql.Driver").getDeclaredConstructor().newInstance();
            Connection connection = DriverManager.getConnection(BD_PATH, BD_USER_AND_PAS, BD_USER_AND_PAS);
            if (connection == null)
            {
                System.err.println("Can't connect to database");
                System.exit(-1);
            }
            addToSQL(connection, PATH_TO_SCHEMA);
            addToSQL(connection, PATH_TO_INSERT);
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
