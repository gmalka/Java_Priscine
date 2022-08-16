package edu.school21.repositories;

import edu.school21.models.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductsRepositoryJdbcImplTest {
    private Connection dbConnection;

    @BeforeEach
    public void resetDBAndGetConnection() throws Exception
    {
        dbConnection = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("/schema.sql")
                .addScript("/data.sql")
                .build().getConnection();
    }

    @Test
    public void testFindAll() throws SQLException {
        final ArrayList<Product> FIND_ALL = new ArrayList<Product>() {
            {
                add(new Product(0L, "Lenovo laptop MSCHSD-81273678", 555888L));
                add(new Product(1L, "HP Dominator NaGiBaToR", 15000L));
                add(new Product(2L, "MacBook Pro, 1gb RAM, 1GHZ, 13inches", 999999999L));
                add(new Product(3L, "Huawei TotallyNotACloneOfIphone", 2000L));
                add(new Product(4L, "SberPortal", 15999L));
                add(new Product(5L, "Another Yandex IoT Device", 20999L));
                add(new Product(6L, "Another Yandex IoT Device ", 10888L));
            }
            };
        Assertions.assertEquals(FIND_ALL, new ProductsRepositoryJdbcImpl(dbConnection).findAll());
    }

    @Test
    public void testFindByIdTrue() throws SQLException {
        final Product FINDER = new Product(4L, "SberPortal", 15999L);
        Assertions.assertEquals(FINDER,new ProductsRepositoryJdbcImpl(dbConnection).findById(4L).get());
    }

    @ParameterizedTest
    @ValueSource(longs = {-1, 99, 99, 15874, 41})
    public void testFindByIdFalse(Long number) throws SQLException {
        Assertions.assertFalse(new ProductsRepositoryJdbcImpl(dbConnection).findById(number).isPresent());
    }

    @ParameterizedTest
    @ValueSource(longs = {0, 1, 4, 6})
    public void testUpdate(Long id) throws SQLException {
        final Product FINDER = new Product(id, "HELLO THERE", 1445190L);
        ProductsRepositoryJdbcImpl repo = new ProductsRepositoryJdbcImpl(dbConnection);
        repo.update(FINDER);
        Assertions.assertEquals(FINDER, repo.findById(id).get());
    }

    @ParameterizedTest
    @ValueSource(longs = {7312, 1123, 44, 0})
    public void testSave(Long price) throws SQLException {
        final Product FINDER = new Product(7L, "HELLO THERE", price);
        ProductsRepositoryJdbcImpl repo = new ProductsRepositoryJdbcImpl(dbConnection);
        repo.save(FINDER);
        Assertions.assertEquals(FINDER, repo.findById(7L).get());
    }

    @ParameterizedTest
    @ValueSource(longs = {0, 1, 5})
    public void testDelete(Long id) throws Exception{
        ProductsRepositoryJdbcImpl repo = new ProductsRepositoryJdbcImpl(dbConnection);
        repo.delete(id);
        Assertions.assertFalse(repo.findById(id).isPresent());
    }
}
