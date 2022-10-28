package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.dao.ProductDAO;
import ru.akirakozov.sd.refactoring.dao.ProductDAOImpl;

import javax.servlet.http.HttpServlet;
import java.sql.SQLException;

public abstract class BaseServlet extends HttpServlet {

    protected final ProductDAO productDAO;

    public BaseServlet() throws SQLException {
        this.productDAO = new ProductDAOImpl();
    }

}
