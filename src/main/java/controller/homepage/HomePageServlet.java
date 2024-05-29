package controller.homepage;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Prodotto;
import model.Utente;

import java.io.IOException;
import java.util.List;

@WebServlet(value = "/homePage")
public class HomePageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        //Mi prendo l'utente passato da LoginServlet
        Utente x = (Utente) session.getAttribute("Utente");


        //Controllo necessario perchè senza, altrimenti, una volta loggati nel sito
        //se si cliccasse sul filtro del range presente in "basso" è come se mi facesse uscire (da migliorare)
        /*List<Prodotto> productsByCriteria = (List<Prodotto>) req.getAttribute("productsByCriteria");
        if (productsByCriteria != null){
            RequestDispatcher requestDispatcherFilter = req.getRequestDispatcher("AllProducts.jsp");
            requestDispatcherFilter.forward(req, resp);
        }*/


        //Se non clicco sul filtro, richiamo semplicemente index.jsp
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("index.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
