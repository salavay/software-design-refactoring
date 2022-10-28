package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.model.Product;
import ru.akirakozov.sd.refactoring.utils.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends BaseServlet {


    public GetProductsServlet() throws SQLException {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        List<Product> products = productDAO.getAll();

        response.getWriter().println(HtmlUtils.buildHtml(
                products.stream().map(HtmlUtils::productToHtml)
                        .collect(Collectors.joining())
        ));

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
