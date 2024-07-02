package controller.Filters;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Prodotto;
import model.ProdottoDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(value = "/categories")
public class CategoriesServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String filter = req.getParameter("category");
        ProdottoDAO prodottoDAO = new ProdottoDAO();

        HttpSession session = req.getSession();

        List<Prodotto> productsByCriteria = new ArrayList<>();

        if(filter.equals("tutto")){
            productsByCriteria = prodottoDAO.doRetrieveAll();
        }else {
            productsByCriteria = prodottoDAO.doRetrieveByCriteria("categoria", filter);
        }



        //rimuovo per mantenere coerenza con i gusti
        session.removeAttribute("products");


        req.setAttribute("originalProducts", productsByCriteria);

        //setto nella session per usare i filtri in ajax
        session.setAttribute("filteredProducts", productsByCriteria);


        RequestDispatcher requestDispatcher = req.getRequestDispatcher("FilterProducts.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }}
