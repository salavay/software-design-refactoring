package ru.akirakozov.sd.refactoring;

import org.junit.*;
import org.mockito.Mockito;
import ru.akirakozov.sd.refactoring.model.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BaseTestCase {
    protected static Connection connection;
    private final static String DATASOURCE = "jdbc:sqlite:test.db";
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected StringWriter stringWriter;

    @BeforeClass
    public static void setUp() {
        try {
            connection = DriverManager.getConnection(DATASOURCE);
        } catch (SQLException e) {
            Assert.fail("Failed to create DB");
        }
        execute("CREATE TABLE IF NOT EXISTS PRODUCT" +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " NAME           TEXT    NOT NULL, " +
                " PRICE          INT     NOT NULL)");
        clearDb();
    }

    @AfterClass
    public static void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            Assert.fail("Failed to stop DB");
        }
    }

    @Before
    public void beforeBase() {
        clearDb();
        initReqAndRes();
    }

    protected void initReqAndRes() {
        stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
        try {
            Mockito.doReturn(printWriter).when(response).getWriter();
        } catch (IOException e) {
            Assert.fail("Can't init request and response");
        }
    }

    @After
    public void checkResponseStructure() {
        Mockito.verify(response, Mockito.times(1))
                .setStatus(HttpServletResponse.SC_OK);
        Mockito.verify(response, Mockito.times(1))
                .setContentType("text/html");
    }

    protected static void putToDb(String name, int price) {
        execute(String.format("INSERT INTO PRODUCT (NAME, PRICE) VALUES (\"%s\", %d)", name, price));
    }

    protected static void clearDb() {
        execute("delete from PRODUCT where 1=1");
    }

    protected static void execute(String query) {
        try (Statement statement = connection.createStatement()) {
            statement.execute(query);
        } catch (SQLException e) {
            Assert.fail("Failed to execute query: " + query + "\n" + e.getMessage());
        }
    }

    protected List<Product> collectDbOrderedByPrice() {
        List<Product> result = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            try (ResultSet rs = statement.executeQuery("SELECT * FROM PRODUCT ORDER BY PRICE")) {
                while (rs != null && rs.next()) {
                    String name = rs.getString("name");
                    int price = rs.getInt("price");
                    result.add(new Product(name, price));
                }
            }
        } catch (Exception e) {
            Assert.fail();
        }
        return result;
    }

    protected String buildHtmlForProducts(List<Product> products) {
        StringBuilder sb = new StringBuilder();
        for (Product product : products) {
            sb.append(String.format("%s\t%d</br>\n",
                    product.getName(),
                    product.getPrice()));
        }
        return sb.toString();
    }

    protected String buildHtml(String body) {
        return "<html><body>\n" +
                body +
                "</body></html>\n";
    }
}
