package controller.homepage;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(value = "/cartServlet")
public class CarrelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        ProdottoDAO prodottoDAO = new ProdottoDAO();
        HttpSession session = req.getSession();

        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        switch (action) {
            case "add" ->
                handleAddAction(req, session, prodottoDAO, out);
            case "remove" ->
                handleRemoveAction(req, session, prodottoDAO, out);
            case "show" ->
                handleShowAction(session, prodottoDAO, out);
        }
    }

    private void handleAddAction(HttpServletRequest req, HttpSession session, ProdottoDAO prodottoDAO, PrintWriter out) throws IOException {
        String id = req.getParameter("id");
        Prodotto prodotto = prodottoDAO.doRetrieveById(id);
        List<Carrello> cartItems = (List<Carrello>) session.getAttribute("cart");

        if (cartItems == null) {
            cartItems = new ArrayList<>();
        }

        boolean itemExists = false;
        for (Carrello item : cartItems) {
            if (item.getIdProdotto().equals(id)) {
                item.setQuantita(item.getQuantita() + 1);
                item.setPrezzo(item.getPrezzo() + prodotto.getPrezzo());
                itemExists = true;
                break;
            }
        }

        if (!itemExists) {
            cartItems.add(new Carrello("user@gmail.com", id, prodotto.getNome(), 1, prodotto.getPrezzo()));
        }

        session.setAttribute("cart", cartItems);
        writeCartItemsToResponse(cartItems, prodottoDAO, out);
    }

    private void handleRemoveAction(HttpServletRequest req, HttpSession session, ProdottoDAO prodottoDAO, PrintWriter out) throws IOException {
        String idToRemove = req.getParameter("id");
        List<Carrello> cartItems = (List<Carrello>) session.getAttribute("cart");

        if (cartItems != null) {
            cartItems.removeIf(item -> item.getIdProdotto().equals(idToRemove));
            session.setAttribute("cart", cartItems);
        }

        writeCartItemsToResponse(cartItems, prodottoDAO, out);
    }

    private void handleShowAction(HttpSession session, ProdottoDAO prodottoDAO, PrintWriter out) throws IOException {
        List<Carrello> cartItems = (List<Carrello>) session.getAttribute("cart");

        if (cartItems != null) {
            writeCartItemsToResponse(cartItems, prodottoDAO, out);
        } else {
            out.println("[]");
            out.flush();
        }
    }

    private void writeCartItemsToResponse(List<Carrello> cartItems, ProdottoDAO prodottoDAO, PrintWriter out) throws IOException {
        JSONArray jsonArray = new JSONArray();
        float totalPrice = 0;

        for (Carrello item : cartItems) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", item.getIdProdotto());
            jsonObject.put("nome", prodottoDAO.doRetrieveById(item.getIdProdotto()).getNome());
            jsonObject.put("quantity", item.getQuantita());
            jsonObject.put("prezzo", item.getPrezzo());
            totalPrice += item.getPrezzo();
            jsonArray.add(jsonObject);
        }

        totalPrice = Math.round(totalPrice * 100.0f) / 100.0f;

        JSONObject totalPriceObject = new JSONObject();
        totalPriceObject.put("totalPrice", totalPrice);
        jsonArray.add(totalPriceObject);

        out.println(jsonArray);
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
