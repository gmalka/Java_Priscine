package edu.school21.models;

import java.sql.SQLException;
import java.util.Objects;

public class Product {
    private Long    id;
    private String  name;
    private Long    price;

    public Product(Long id, String name, Long price) throws SQLException {
        if (id == null)
            throw new SQLException();
        this.id = id;
        this.name = name;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) && name.equals(product.name) && Objects.equals(price, product.price);
    }

    @Override
    public String toString() {
        return "\nProduct{" +
                "\nid=" + id +
                "\n, name='" + name + '\'' +
                "\n, price=" + price +
                "\n}";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) throws SQLException{
        if (id == null)
            throw new SQLException();
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
}
