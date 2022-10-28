package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.model.Product;

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
            response.getWriter().println("<html><body>");
            response.getWriter().println("<h1>Product with max price: </h1>");

            Product maxProduct = productDAO.getMaxProduct();
            if (maxProduct != null) {
                response.getWriter().println(maxProduct.getName() + "\t" + maxProduct.getPrice() + "</br>");
            }
            response.getWriter().println("</body></html>");

        } else if ("min".equals(command)) {
            response.getWriter().println("<html><body>");
            response.getWriter().println("<h1>Product with min price: </h1>");

            Product minProduct = productDAO.getMinProduct();
            if (minProduct != null) {
                response.getWriter().println(minProduct.getName() + "\t" + minProduct.getPrice() + "</br>");
            }
            response.getWriter().println("</body></html>");

        } else if ("sum".equals(command)) {
            response.getWriter().println("<html><body>");
            response.getWriter().println("Summary price: ");

            Integer sum = productDAO.getSum();
            if (sum != null) {
                response.getWriter().println(sum);
            }

            response.getWriter().println("</body></html>");
        } else if ("count".equals(command)) {
            response.getWriter().println("<html><body>");
            response.getWriter().println("Number of products: ");

            Integer count = productDAO.getCount();
            if (count != null) {
                response.getWriter().println(count);
            }

            response.getWriter().println("</body></html>");
        } else {
            response.getWriter().println("Unknown command: " + command);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
