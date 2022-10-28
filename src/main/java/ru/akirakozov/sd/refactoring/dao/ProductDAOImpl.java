package ru.akirakozov.sd.refactoring.dao;

import ru.akirakozov.sd.refactoring.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {

    private final Connection connection;

    public ProductDAOImpl() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:test.db");
    }

    private void update(String query) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute(query);
        }
    }

    @Override
    public void create(Product product) {
        try {
            update(String.format("INSERT INTO PRODUCT (%s, %s) VALUES (\"%s\", %d)",
                    C_NAME, C_PRICE,
                    product.getName(), product.getPrice()));
        } catch (SQLException e) {
            System.err.println("Failed to create product: " + product);
        }
    }

    @Override
    public List<Product> getAll() {
        try {
            return selectRows(String.format("SELECT * FROM %s", C_TABLE),
                    rs -> new Product(rs.getString(C_NAME), rs.getInt(C_PRICE)));
        } catch (SQLException e) {
            System.err.println("Failed to load all products");
            return Collections.emptyList();
        }
    }

    @Override
    public Product getMaxProduct() {
        try {
            return selectRow(String.format(
                            "SELECT * FROM %s ORDER BY %s DESC LIMIT 1",
                            C_TABLE, C_PRICE),
                    rs -> new Product(rs.getString(C_NAME), rs.getInt(C_PRICE)));
        } catch (SQLException e) {
            System.err.println("Failed to get max products");
            return null;
        }
    }

    @Override
    public Product getMinProduct() {
        try {
            return selectRow(String.format(
                            "SELECT * FROM %s ORDER BY %s LIMIT 1",
                            C_TABLE, C_PRICE),
                    rs -> new Product(rs.getString(C_NAME), rs.getInt(C_PRICE)));
        } catch (SQLException e) {
            System.err.println("Failed to get min products");
            return null;
        }
    }

    @Override
    public Integer getSum() {
        try {
            return selectRow(String.format(
                            "SELECT SUM(%s) as %s FROM %s",
                            C_PRICE, C_PRICE, C_TABLE),
                    rs -> rs.getInt(C_PRICE));
        } catch (SQLException e) {
            System.err.println("Failed to get sum of products");
            return null;
        }
    }

    @Override
    public Integer getCount() {
        try {
            return selectRow(String.format(
                            "SELECT COUNT(%s) as %s FROM %s",
                            C_PRICE, C_PRICE, C_TABLE),
                    rs -> rs.getInt(C_PRICE));
        } catch (SQLException e) {
            System.err.println("Failed to count products");
            return null;
        }
    }

    private <T> T selectRow(String query, RowGetter<T> rg) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            try (ResultSet rs = statement.executeQuery(query)) {
                if (rs != null && rs.next()) {
                    return rg.apply(rs);
                } else {
                    throw new SQLException();
                }
            }
        }
    }

    private <T> List<T> selectRows(String query, RowGetter<T> rg) throws SQLException {
        List<T> result = new ArrayList<>();
        try(Statement statement = connection.createStatement()) {
            try (ResultSet rs = statement.executeQuery(query)) {
                while (rs != null && rs.next()) {
                    result.add(rg.apply(rs));
                }
            }
        }
        return result;
    }
}
