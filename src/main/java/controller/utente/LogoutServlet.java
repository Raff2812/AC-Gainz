package controller.utente;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Carrello;
import model.CarrelloDAO;
import model.Utente;

import java.io.IOException;
import java.util.List;

@WebServlet(value = "/logOut")
public class LogoutServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Utente x = (Utente) session.getAttribute("Utente");
        //Save cart into DB before logging out
        CarrelloDAO carrelloDAO = new CarrelloDAO();
        List<Carrello> cart = (List<Carrello>) session.getAttribute("cart");

        if (cart.isEmpty()){
            carrelloDAO.doRemoveCartByUser(x.getEmail());
            System.out.println("Carrello vuoto");
        } else{
            for (Carrello c : cart) {
            c.setEmailUtente(x.getEmail());
            carrelloDAO.doSave(c);
            }
        }




        session.removeAttribute("Utente");
        req.getSession().invalidate();

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("index.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
