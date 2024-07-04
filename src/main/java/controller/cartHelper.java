package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Carrello;
import model.Prodotto;
import model.ProdottoDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@WebServlet(value = "/cart")
@SuppressWarnings("unchecked")
public class cartHelper extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Carrello> cartItems = (List<Carrello>) req.getSession().getAttribute("cart");
        ProdottoDAO prodottoDAO = new ProdottoDAO();
        List<Prodotto> productsCart = new ArrayList<>();

        if (cartItems != null && !cartItems.isEmpty()) {
            for (Carrello c : cartItems) {
                Prodotto p = prodottoDAO.doRetrieveById(c.getIdProdotto());
                productsCart.add(p);
            }
        }

        req.setAttribute("productsCart", productsCart);
        req.getRequestDispatcher("Carrello.jsp").forward(req, resp);
    }

}

