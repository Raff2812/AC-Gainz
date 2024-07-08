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
@SuppressWarnings("unchecked")
public class CarrelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");


        ProdottoDAO prodottoDAO = new ProdottoDAO();
        HttpSession session = req.getSession();

        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();


        switch (action) {
            case "show" ->
                    handleShowAction(session, prodottoDAO, out);
            case "addVariant" ->
                handleAddVariantAction(req, session, prodottoDAO,  out);
            case "removeVariant" ->
                handleRemoveVariantAction(req, session, prodottoDAO, out);
            case "quantityVariant" ->
                handleQuantityVariantAction(req, session, prodottoDAO, out);
        }


    }


    private void handleShowAction(HttpSession session, ProdottoDAO prodottoDAO, PrintWriter out) throws IOException {
        List<Carrello> cartItems = (List<Carrello>) session.getAttribute("cart");

        if (cartItems != null && !cartItems.isEmpty()) {
            writeCartItemsToResponse(cartItems, prodottoDAO, out);
        } else {
            JSONArray jsonArray = new JSONArray();
            out.println(jsonArray);
            out.flush();
            out.close();
        }
    }


    public void handleQuantityVariantAction(HttpServletRequest request,  HttpSession session, ProdottoDAO prodottoDAO, PrintWriter out) throws IOException, ServletException {
        String idProdotto = request.getParameter("id");
        String gusto = request.getParameter("gusto");
        int pesoConfezione = Integer.parseInt(request.getParameter("pesoConfezione"));

        Prodotto p = prodottoDAO.doRetrieveById(idProdotto);
        if (p != null){
            VarianteDAO varianteDAO = new VarianteDAO();
            Variante v = varianteDAO.doRetrieveVariantByFlavourAndWeight(p.getIdProdotto(), gusto, pesoConfezione).get(0);
            List<Carrello> cartItems = (List<Carrello>) session.getAttribute("cart");
            if (v != null && cartItems != null){
                String quantity = request.getParameter("quantity");
                if (quantity != null && !quantity.isBlank() && Integer.parseInt(quantity) < v.getQuantita()){
                    int q = Integer.parseInt(quantity);
                    if (q < 0) {
                        handleRemoveVariantAction(request, session, prodottoDAO, out);
                    }
                    else {
                        float price = v.getPrezzo();
                        if (v.getSconto() > 0){
                            price = price * (1 - (float) v.getSconto() / 100);
                            price = Math.round(price * 100.0f) / 100.0f;
                        }

                        price *= q;

                        for (Carrello c: cartItems){
                            if (c.getIdVariante() == v.getIdVariante()){
                                c.setQuantita(q);
                                c.setPrezzo(price);
                                break;
                            }
                        }
                        writeCartItemsToResponse(cartItems, prodottoDAO, out);
                    }
                }
            }
        }
    }




    public void handleRemoveVariantAction(HttpServletRequest request, HttpSession session, ProdottoDAO prodottoDAO, PrintWriter out) throws IOException {
        String idToRemove = request.getParameter("id");
        String gusto = request.getParameter("gusto");
        int pesoConfezione = Integer.parseInt(request.getParameter("pesoConfezione"));

        Prodotto p = prodottoDAO.doRetrieveById(idToRemove);

        if (p != null){
            VarianteDAO varianteDAO = new VarianteDAO();
            Variante v = varianteDAO.doRetrieveVariantByFlavourAndWeight(p.getIdProdotto(), gusto, pesoConfezione).get(0);
            if (v != null){
                List<Carrello> cartItems = (List<Carrello>) session.getAttribute("cart");


                if (cartItems != null) {
                    cartItems.removeIf(item -> item.getIdVariante()  == v.getIdVariante());
                    session.setAttribute("cart", cartItems);

                    writeCartItemsToResponse(cartItems, prodottoDAO, out);
                }
            }
        }
    }





    private void handleAddVariantAction(HttpServletRequest request, HttpSession session, ProdottoDAO prodottoDAO, PrintWriter out) throws IOException {
        String id = request.getParameter("id");
        Prodotto p = prodottoDAO.doRetrieveById(id);
        if (p != null) {
            int quantity = 1;

            if (request.getParameter("quantity") != null) {
                int x = Integer.parseInt(request.getParameter("quantity"));
                if (x > 0) quantity = x;
            }

            String gusto = request.getParameter("gusto");
            String pesoConfezione = request.getParameter("pesoConfezione");

            VarianteDAO varianteDAO = new VarianteDAO();

            Variante v = varianteDAO.doRetrieveVariantByFlavourAndWeight(id, gusto, Integer.parseInt(pesoConfezione)).get(0);

            List<Carrello> cartItems = (List<Carrello>) session.getAttribute("cart");
            if (cartItems == null) cartItems = new ArrayList<>();

            if (v != null) {
                float price = v.getPrezzo();

                if (v.getSconto() > 0) {
                    price = price * (1 - (float) v.getSconto() / 100);
                    price = Math.round(price * 100.0f) / 100.0f;
                }

                boolean itemExists = false;
                if (!cartItems.isEmpty()) {
                    for (Carrello item : cartItems) {
                        if (item.getIdVariante() == v.getIdVariante()) {
                            int newQuantity = item.getQuantita() + quantity;
                            if (newQuantity <= v.getQuantita()) {
                                item.setQuantita(newQuantity);
                                item.setPrezzo(item.getPrezzo() + (price * quantity));
                            }
                            itemExists = true;
                            break;
                        }
                    }
                }

                if (!itemExists && quantity <= v.getQuantita()) {
                    Carrello c = new Carrello();
                    c.setIdProdotto(id);
                    c.setIdVariante(v.getIdVariante());
                    c.setNomeProdotto(p.getNome());
                    c.setQuantita(quantity);
                    c.setPrezzo(price * quantity);
                    c.setGusto(gusto);
                    c.setPesoConfezione(Integer.parseInt(pesoConfezione));
                    c.setImmagineProdotto(p.getImmagine());
                    cartItems.add(c);
                }

                session.setAttribute("cart", cartItems);
                writeCartItemsToResponse(cartItems, prodottoDAO, out);
            }
        }
    }










    private void writeCartItemsToResponse(List<Carrello> cartItems, ProdottoDAO prodottoDAO, PrintWriter out) throws IOException{
        JSONArray jsonArray = new JSONArray();
        float totalPrice = 0;

        for (Carrello item: cartItems){
            Prodotto p = prodottoDAO.doRetrieveById(item.getIdProdotto());
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("idProdotto", item.getIdProdotto());
            jsonObject.put("idVariante", item.getIdVariante());
            jsonObject.put("nomeProdotto", p.getNome());
            jsonObject.put("imgSrc", p.getImmagine());
            jsonObject.put("flavour", item.getGusto());
            jsonObject.put("weight", item.getPesoConfezione());

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
