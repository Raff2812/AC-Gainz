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

@WebServlet(value = "/prova")
public class provaProducts extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProdottoDAO p = new ProdottoDAO();
        String attributo = request.getParameter("attributo");
        String valore = request.getParameter("valore");

        List<Prodotto> products = new ArrayList<>();

        if (valore.contains(" - ")){
            String[] fromAndTo = valore.split(" - ");

            products = p.doRetrieveByCriteriaRange(attributo, Integer.parseInt(fromAndTo[0]), Integer.parseInt(fromAndTo[1]));
        }
        else products = p.doRetrieveByCriteria(attributo, valore);


        request.setAttribute("productsByCriteria", products);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("homePage");
        requestDispatcher.forward(request, response);



    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
