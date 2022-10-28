package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.model.Product;
import ru.akirakozov.sd.refactoring.utils.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author akirakozov
 */
public class QueryServlet extends BaseServlet {
    public QueryServlet() throws SQLException {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        if ("max".equals(command)) {
            Product maxProduct = productDAO.getMaxProduct();
            response.getWriter().println(HtmlUtils.buildHtml(
                    HtmlUtils.productToHtml(maxProduct),
                    "<h1>Product with max price: </h1>"
            ));
        } else if ("min".equals(command)) {
            Product minProduct = productDAO.getMinProduct();
            response.getWriter().println(HtmlUtils.buildHtml(
                    HtmlUtils.productToHtml(minProduct),
                    "<h1>Product with min price: </h1>"
            ));
        } else if ("sum".equals(command)) {
            Integer sum = productDAO.getSum();
            response.getWriter().println(HtmlUtils.buildHtml(
                    sum != null ? sum + "\n" : "",
                    "Summary price: "
            ));
        } else if ("count".equals(command)) {
            Integer count = productDAO.getCount();
            response.getWriter().println(HtmlUtils.buildHtml(
                    count != null ? count + "\n" : "",
                    "Number of products: "
            ));
        } else {
            response.getWriter().println("Unknown command: " + command);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
