package controller.homepage;

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

@WebServlet(value = "/ProductInfo")
public class ProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String primaryKey = req.getParameter("primaryKey");
        System.out.println(primaryKey);
        if(primaryKey != null) {
                ProdottoDAO prodottoDAO = new ProdottoDAO();
                Prodotto prodotto = prodottoDAO.doRetrieveById(primaryKey);
                if(prodotto != null)
                {
                    ProdottoDAO suggeritiDAO = new ProdottoDAO();
                    //sezione dei suggeriti
                    String category = prodotto.getCategoria();
                    List<Prodotto> suggeriti = suggeritiDAO.doRetrieveByCriteria("categoria",category);
                    req.setAttribute("suggeriti",suggeriti);
                    req.setAttribute("prodotto",prodotto);
                    req.getRequestDispatcher("Product.jsp").forward(req,resp);
                }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
