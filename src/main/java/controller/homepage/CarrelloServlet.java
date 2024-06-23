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
            case "quantity" ->
                    handleQuantityAction(req, session, prodottoDAO, out);
        }


    }

    private void handleAddAction(HttpServletRequest req, HttpSession session, ProdottoDAO prodottoDAO, PrintWriter out) throws IOException {
        String id = req.getParameter("id");
        System.out.println(id);
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

        if (cartItems != null && !cartItems.isEmpty()) {
            System.out.println("sturnz");
            writeCartItemsToResponse(cartItems, prodottoDAO, out);
        } else {
            JSONArray jsonArray = new JSONArray();
            out.println(jsonArray);
            out.flush();
            out.close();
        }
    }

    private void handleQuantityAction(HttpServletRequest request, HttpSession session, ProdottoDAO prodottoDAO, PrintWriter out) throws IOException{
        List<Carrello> cartItems = (List<Carrello>) session.getAttribute("cart");
        if (cartItems != null){
            String quantity = request.getParameter("quantity");
            String id = request.getParameter("id");
            if (quantity != null && !quantity.isBlank() && id != null){
                int q = Integer.parseInt(quantity);
                if (q <= 0){
                    handleRemoveAction(request, session, prodottoDAO, out);
                }else {
                    for (Carrello c: cartItems){
                        if (c.getIdProdotto().equals(id)){
                            c.setQuantita(q);
                            c.setPrezzo(prodottoDAO.doRetrieveById(c.getIdProdotto()).getPrezzo() * q);
                            break;
                        }
                    }
                    writeCartItemsToResponse(cartItems, prodottoDAO, out);
                }
            }
        }
    }

    private void writeCartItemsToResponse(List<Carrello> cartItems, ProdottoDAO prodottoDAO, PrintWriter out) throws IOException {
        JSONArray jsonArray = new JSONArray();
        float totalPrice = 0;


        for (Carrello item : cartItems) {
            Prodotto p = prodottoDAO.doRetrieveById(item.getIdProdotto());
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("id", item.getIdProdotto());

            jsonObject.put("nome", prodottoDAO.doRetrieveById(item.getIdProdotto()).getNome());

            jsonObject.put("imgSrc", p.getImmagine());
            jsonObject.put("flavour", p.getGusto());
            jsonObject.put("weight", p.getPeso());

            jsonObject.put("quantity", item.getQuantita());

            float itemPrice = item.getPrezzo();
            itemPrice = Math.round(itemPrice * 100.0f) / 100.0f;
            jsonObject.put("prezzo", itemPrice);

            totalPrice += item.getPrezzo();

            jsonArray.add(jsonObject);
        }

        totalPrice = Math.round(totalPrice * 100.0f) / 100.0f;

        JSONObject totalPriceObject = new JSONObject();
        totalPriceObject.put("totalPrice", totalPrice);
        jsonArray.add(totalPriceObject);

        out.println(jsonArray);
        out.flush();
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
