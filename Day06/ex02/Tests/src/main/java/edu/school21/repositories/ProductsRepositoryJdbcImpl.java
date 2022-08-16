package edu.school21.repositories;

import edu.school21.models.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImpl implements ProductsRepository {
    private Connection connection;

    public ProductsRepositoryJdbcImpl(Connection ds) {
        connection = ds;
    }

    @Override
    public List<Product> findAll() throws SQLException {
        final String query = "SELECT * FROM PRODUCTS ORDER BY ID";
        List<Product> list = new ArrayList<>();
        ResultSet rs = null;
        PreparedStatement statement = connection.prepareStatement(query);
        rs = statement.executeQuery();
        while (rs.next()) {
            list.add(new Product(rs.getLong("ID"),
                    rs.getString("NAME"),
                    rs.getLong("PRICE")));
        }
        return list;
    }

    @Override
    public Optional<Product> findById(Long id) {
        final String query = "SELECT * FROM PRODUCTS WHERE ID = " + id;
        Product product = null;
        ResultSet rs = null;
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            rs = statement.executeQuery();
            rs.next();
            product = new Product(rs.getLong("ID"),
                    rs.getString("NAME"),
                    rs.getLong("PRICE"));
        } catch (SQLException e) {
            return Optional.empty();
        }
        return Optional.of(product);
    }

    @Override
    public void update(Product product) throws SQLException {
        final String QUERY_TEMPLATE = "UPDATE products SET NAME = ?, PRICE = ? WHERE ID = " + product.getId();
        PreparedStatement statement = connection.prepareStatement(QUERY_TEMPLATE);
        statement.setString(1, product.getName());
        statement.setLong(2, product.getPrice());
        statement.execute();
    }

    @Override
    public void save(Product product) throws SQLException {
        final String QUERY_TEMPLATE = "INSERT INTO products (NAME, PRICE) VALUES (?, ?)";
        PreparedStatement query = connection.prepareStatement(QUERY_TEMPLATE);
        query.setString(1, product.getName());
        query.setLong(2, product.getPrice());
        query.execute();
    }

    @Override
    public void delete(Long id) throws SQLException {
        final String DELETED_QUERY = "DELETE FROM products WHERE ID = " + id;
        connection.prepareStatement(DELETED_QUERY).execute();
    }
}
