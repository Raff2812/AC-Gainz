package controller.homepage;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(value = "/orderServlet")
@SuppressWarnings("unchecked")

public class OrdineServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        List<Carrello> cart = (List<Carrello>) session.getAttribute("cart");

        if (!cart.isEmpty() && session.getAttribute("Utente") != null){
            Utente x = (Utente) session.getAttribute("Utente");
            float orderTotal = 0;
            for (Carrello cartItem: cart) orderTotal += cartItem.getPrezzo();



            Ordine ordine = new Ordine();
            ordine.setEmailUtente(x.getEmail());


            OrdineDao ordineDao = new OrdineDao();
            ordineDao.doSave(ordine);



            int id_order = ordineDao.getLastInsertedId();

            List<DettaglioOrdine> dettaglioOrdine = new ArrayList<>();
            for (Carrello cartItem: cart){
                DettaglioOrdine dettaglioOrdineItem = new DettaglioOrdine();
                dettaglioOrdineItem.setIdOrdine(id_order);
                dettaglioOrdineItem.setIdVariante(cartItem.getIdVariante());
                dettaglioOrdineItem.setIdProdotto(cartItem.getIdProdotto());
                dettaglioOrdineItem.setQuantita(cartItem.getQuantita());
                dettaglioOrdine.add(dettaglioOrdineItem);
            }

            System.out.println(dettaglioOrdine.size());

            session.removeAttribute("cart");
            CarrelloDAO carrelloDAO = new CarrelloDAO();
            carrelloDAO.doRemoveCartByUser(x.getEmail());

            DettaglioOrdineDAO dettaglioOrdineDAO = new DettaglioOrdineDAO();
            for (DettaglioOrdine dettaglioOrdineItem : dettaglioOrdine)
                dettaglioOrdineDAO.doSave(dettaglioOrdineItem);



            Ordine ordine1 = ordineDao.doRetrieveById(ordineDao.getLastInsertedId());


            List<DettaglioOrdine> dettaglioOrdini1 = new ArrayList<>();
            dettaglioOrdini1 = dettaglioOrdineDAO.doRetrieveById(ordine1.getIdOrdine());
            System.out.println(dettaglioOrdini1.size());
            req.setAttribute("order", ordine1);
            req.setAttribute("orderDetails", dettaglioOrdini1);

            req.getRequestDispatcher("WEB-INF/Ordine.jsp").forward(req, resp);
        }
    }
}
