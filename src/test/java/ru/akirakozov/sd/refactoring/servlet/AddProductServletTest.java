package ru.akirakozov.sd.refactoring.servlet;

import javafx.util.Pair;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ru.akirakozov.sd.refactoring.BaseTestCase;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class AddProductServletTest extends BaseTestCase {

    AddProductServlet addProductServlet;

    @Before
    public void before() {
        addProductServlet = new AddProductServlet();
    }

    @Test
    public void addOneProduct() throws IOException {
        List<Pair<String, Integer>> productsToTest = new ArrayList<>();
        productsToTest.add(new Pair<>("product1", 1));
        for (Pair<String, Integer> product : productsToTest) {
            doAddRequest(product.getKey(), product.getValue());
        }

        List<Pair<String, Integer>> response = collectDbOrderedByPrice();

        Assert.assertEquals(productsToTest, response);
    }

    @Test
    public void addTwoProduct() throws IOException {
        List<Pair<String, Integer>> productsToTest = new ArrayList<>();
        productsToTest.add(new Pair<>("product1", 1));
        productsToTest.add(new Pair<>("product2", 2));
        for (Pair<String, Integer> product : productsToTest) {
            doAddRequest(product.getKey(), product.getValue());
        }

        List<Pair<String, Integer>> response = collectDbOrderedByPrice();

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