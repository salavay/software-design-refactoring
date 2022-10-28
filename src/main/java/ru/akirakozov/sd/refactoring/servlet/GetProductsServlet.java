package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.model.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends BaseServlet {


    public GetProductsServlet() throws SQLException {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().println("<html><body>");

        List<Product> products = productDAO.getAll();
        for (Product product : products) {
            response.getWriter().println(product.getName() + "\t" + product.getPrice() + "</br>");
        }

        response.getWriter().println("</body></html>");

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
