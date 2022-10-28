package ru.akirakozov.sd.refactoring.servlet;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ru.akirakozov.sd.refactoring.BaseTestCase;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class QueryServletTest extends BaseTestCase {

    QueryServlet queryServlet;
    private static final Map<String, String> headers = new HashMap<>();
    static {
        headers.put("max", "<h1>Product with max price: </h1>\n");
        headers.put("min", "<h1>Product with min price: </h1>\n");
        headers.put("sum", "Summary price: \n");
        headers.put("count", "Number of products: \n");
    }

    @Before
    public void before() throws SQLException {
        queryServlet = new QueryServlet();
    }

    @Test
    public void unknownCommand() throws IOException {
        queryServlet.doGet(request, response);

        Assert.assertEquals("Unknown command: null\n",
                stringWriter.toString());
    }

    @Test
    public void emptyMax() throws IOException {
        Mockito.doReturn("max").when(request)
                .getParameter("command");
        queryServlet.doGet(request, response);

        Assert.assertEquals(htmlBodyForQuery("max", ""),
                stringWriter.toString());
    }

    @Test
    public void simpleMax() throws IOException {
        putToDb("product1", 1);
        putToDb("product2", 2);
        Mockito.doReturn("max").when(request)
                .getParameter("command");
        queryServlet.doGet(request, response);

        Assert.assertEquals(htmlBodyForQuery("max",
                        "product2\t2</br>\n"),
                stringWriter.toString());
    }

    @Test
    public void emptyMin() throws IOException {
        Mockito.doReturn("min").when(request)
                .getParameter("command");
        queryServlet.doGet(request, response);

        Assert.assertEquals(htmlBodyForQuery("min", ""),
                stringWriter.toString());
    }

    @Test
    public void simpleMin() throws IOException {
        putToDb("product1", 1);
        putToDb("product2", 2);
        Mockito.doReturn("min").when(request)
                .getParameter("command");
        queryServlet.doGet(request, response);

        Assert.assertEquals(htmlBodyForQuery("min",
                        "product1\t1</br>\n"),
                stringWriter.toString());
    }

    @Test
    public void emptySum() throws IOException {
        Mockito.doReturn("sum").when(request)
                .getParameter("command");
        queryServlet.doGet(request, response);

        Assert.assertEquals(htmlBodyForQuery("sum", "0\n"),
                stringWriter.toString());
    }

    @Test
    public void simpleSum() throws IOException {
        putToDb("product1", 1);
        putToDb("product2", 2);
        Mockito.doReturn("sum").when(request)
                .getParameter("command");
        queryServlet.doGet(request, response);

        Assert.assertEquals(htmlBodyForQuery("sum",
                        "3\n"),
                stringWriter.toString());
    }

    @Test
    public void emptyCount() throws IOException {
        Mockito.doReturn("count").when(request)
                .getParameter("command");
        queryServlet.doGet(request, response);

        Assert.assertEquals(htmlBodyForQuery("count", "0\n"),
                stringWriter.toString());
    }

    @Test
    public void simpleCount() throws IOException {
        putToDb("product1", 1);
        putToDb("product2", 2);
        Mockito.doReturn("count").when(request)
                .getParameter("command");
        queryServlet.doGet(request, response);

        Assert.assertEquals(htmlBodyForQuery("count",
                        "2\n"),
                stringWriter.toString());
    }



    public String htmlBodyForQuery(String command, String body) {
        String header = headers.getOrDefault(command, null);
        if (header == null) {
            Assert.fail("Uknown command");
        }
        String preparedBody = header + body;
        return buildHtml(preparedBody);
    }


}