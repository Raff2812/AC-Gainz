package controller.utente;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Ordine;
import model.OrdineDao;
import model.Utente;

import java.io.IOException;
import java.util.List;

@WebServlet(value = "/areaUtenteServlet")
public class AreaPersonaleServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Utente utente = null;
        utente = (Utente) session.getAttribute("Utente");

        if (utente != null){
            OrdineDao ordineDao = new OrdineDao();
            List<Ordine> ordini = ordineDao.doRetrieveByEmail(utente.getEmail());

            req.setAttribute("ordini", ordini);
            req.getRequestDispatcher("WEB-INF/AreaUtente.jsp").forward(req, resp);
        }


    }
}
