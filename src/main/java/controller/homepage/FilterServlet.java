package controller.homepage;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Prodotto;
import model.ProdottoDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(value = "/filter")
public class FilterServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       String filter = req.getParameter("category");
        ProdottoDAO prodottoDAO = new ProdottoDAO();

        List<Prodotto> productsByCriteria = new ArrayList<>();
        productsByCriteria = prodottoDAO.doRetrieveByCriteria("categoria", filter);
        req.setAttribute("productsByCriteria", productsByCriteria);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("Filter.jsp");
        requestDispatcher.forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
}}
