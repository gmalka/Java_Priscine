package edu.school21;

import edu.school21.models.Product;
import edu.school21.repositories.ProductsRepositoryJdbcImpl;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Optional;

public class Program {
    public static void main(String[] args) {
        DataSource dbConnection = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();
        try {
            ProductsRepositoryJdbcImpl l = new ProductsRepositoryJdbcImpl(dbConnection.getConnection());
            Optional<Product> e = l.findById(1L);
            System.out.println(e);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
