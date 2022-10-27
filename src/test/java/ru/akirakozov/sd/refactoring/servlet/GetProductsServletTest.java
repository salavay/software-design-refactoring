package ru.akirakozov.sd.refactoring.servlet;

import javafx.util.Pair;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.akirakozov.sd.refactoring.BaseTestCase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetProductsServletTest extends BaseTestCase {

    GetProductsServlet getProductsServlet;

    @Before
    public void before() {
        getProductsServlet = new GetProductsServlet();
    }

    @Test
    public void empty() throws IOException {
        List<Pair<String, Integer>> productsToTest = new ArrayList<>();

        testForProducts(productsToTest);
    }

    @Test
    public void twoProducts() throws IOException {
        List<Pair<String, Integer>> productsToTest = new ArrayList<>();
        productsToTest.add(new Pair<>("product1", 1));
        productsToTest.add(new Pair<>("product2", 2));

        testForProducts(productsToTest);
    }

    private void testForProducts(List<Pair<String, Integer>> productsToTest) throws IOException {
        for (Pair<String, Integer> product : productsToTest) {
            putToDb(product.getKey(), product.getValue());
        }

        getProductsServlet.doGet(request, response);


        stringWriter.flush();
        String result = stringWriter.toString();
        Assert.assertEquals(buildHtmlForGet(productsToTest), result);
    }

    private String buildHtmlForGet(List<Pair<String, Integer>> products) {
        return buildHtml(buildHtmlForProducts(products));
    }
}