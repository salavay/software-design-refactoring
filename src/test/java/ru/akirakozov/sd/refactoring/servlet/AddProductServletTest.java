package ru.akirakozov.sd.refactoring.servlet;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ru.akirakozov.sd.refactoring.BaseTestCase;
import ru.akirakozov.sd.refactoring.model.Product;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class AddProductServletTest extends BaseTestCase {

    AddProductServlet addProductServlet;

    @Before
    public void before() throws SQLException {
        addProductServlet = new AddProductServlet();
    }

    @Test
    public void addOneProduct() throws IOException {
        List<Product> productsToTest = new ArrayList<>();
        productsToTest.add(new Product("product1", 1));
        for (Product product : productsToTest) {
            doAddRequest(product.getName(), product.getPrice());
        }

        List<Product> response = collectDbOrderedByPrice();

        Assert.assertEquals(productsToTest, response);
    }

    @Test
    public void addTwoProduct() throws IOException {
        List<Product> productsToTest = new ArrayList<>();
        productsToTest.add(new Product("product1", 1));
        productsToTest.add(new Product("product2", 2));
        for (Product product : productsToTest) {
            doAddRequest(product.getName(), product.getPrice());
        }

        List<Product> response = collectDbOrderedByPrice();

        Assert.assertEquals(productsToTest, response);
    }

    private void doAddRequest(String product, int price) throws IOException {
        initReqAndRes();
        Mockito.doReturn(product)
                .when(request).getParameter("name");
        Mockito.doReturn(Integer.toString(price))
                .when(request).getParameter("price");
        addProductServlet.doGet(request, response);

    }

}