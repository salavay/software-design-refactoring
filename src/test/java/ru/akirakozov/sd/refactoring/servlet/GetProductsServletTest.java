package ru.akirakozov.sd.refactoring.servlet;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.akirakozov.sd.refactoring.BaseTestCase;
import ru.akirakozov.sd.refactoring.model.Product;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetProductsServletTest extends BaseTestCase {

    GetProductsServlet getProductsServlet;

    @Before
    public void before() throws SQLException {
        getProductsServlet = new GetProductsServlet();
    }

    @Test
    public void empty() throws IOException {
        List<Product> productsToTest = new ArrayList<>();

        testForProducts(productsToTest);
    }

    @Test
    public void twoProducts() throws IOException {
        List<Product> productsToTest = new ArrayList<>();
        productsToTest.add(new Product("product1", 1));
        productsToTest.add(new Product("product2", 2));

        testForProducts(productsToTest);
    }

    private void testForProducts(List<Product> productsToTest) throws IOException {
        for (Product product : productsToTest) {
            putToDb(product.getName(), product.getPrice());
        }

        getProductsServlet.doGet(request, response);


        stringWriter.flush();
        String result = stringWriter.toString();
        Assert.assertEquals(buildHtmlForGet(productsToTest), result);
    }

    private String buildHtmlForGet(List<Product> products) {
        return buildHtml(buildHtmlForProducts(products));
    }
}