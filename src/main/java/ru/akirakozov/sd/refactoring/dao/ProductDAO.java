package ru.akirakozov.sd.refactoring.dao;

import ru.akirakozov.sd.refactoring.model.Product;

import java.util.List;

public interface ProductDAO {

    String C_NAME = "name";
    String C_PRICE = "price";
    String C_TABLE = "Product";


    void create(Product product);
    List<Product> getAll();
    Product getMaxProduct();
    Product getMinProduct();
    Integer getSum();
    Integer getCount();
}
