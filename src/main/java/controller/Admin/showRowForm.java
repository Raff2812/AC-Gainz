package controller.Admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

@WebServlet(value = "/showRowForm")
@SuppressWarnings("unchecked")
public class showRowForm extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String tableName = req.getParameter("tableName");


        JSONArray jsonArray = new JSONArray();
        resp.setContentType("application/json");
        PrintWriter o = resp.getWriter();

        if (tableName != null) {
            switch (tableName) {
                case "utente" -> showUtenteRowTable(req, jsonArray);
                case "prodotto" -> showProdottoRowTable(req, jsonArray);
                case "variante" -> showVarianteRowTable(req, jsonArray);
                case "ordine" -> showOrdineRowTable(req, jsonArray);
            }
        }

        o.println(jsonArray.toJSONString());
        o.flush();
    }

    private void showOrdineRowTable(HttpServletRequest request, JSONArray jsonArray){
        String action = request.getParameter("action");
        if (action != null && !action.isBlank() && action.equals("edit")){
            OrdineDao ordineDao = new OrdineDao();
            Ordine o = new Ordine();
            String primaryKey = request.getParameter("primaryKey");
            o = ordineDao.doRetrieveById(Integer.parseInt(primaryKey));

            if (o != null){
                System.out.println("Ok order");
                JSONObject jsonObject = jsonOrdineHelper(o);
                jsonArray.add(jsonObject);
                System.out.println(jsonArray.toJSONString());
            }
        }
    }

    protected static JSONObject jsonOrdineHelper(Ordine o){
        JSONObject ordineObject = new JSONObject();
        ordineObject.put("idOrdine", o.getIdOrdine());
        ordineObject.put("emailUtente", o.getEmailUtente());
        ordineObject.put("stato", o.getStato());

        if (o.getDataOrdine()!= null) {
            ordineObject.put("data", new SimpleDateFormat("yyyy-MM-dd").format(o.getDataOrdine()));
        } else {
            ordineObject.put("data", "");
        }
        ordineObject.put("totale", o.getTotale());
        ordineObject.put("descrizione", o.getDescrizione());

        return ordineObject;
    }

    private void showVarianteRowTable(HttpServletRequest request, JSONArray jsonArray){
        String action = request.getParameter("action");
        if (action != null && !action.isBlank() && action.equals("edit")){
            VarianteDAO varianteDAO = new VarianteDAO();
            Variante v = new Variante();
            String primaryKey = request.getParameter("primaryKey");
            v = varianteDAO.doRetrieveVarianteByIdVariante(Integer.parseInt(primaryKey));

            if (v != null){
                JSONObject varianteObject = jsonVarianteHelper(v);
                jsonArray.add(varianteObject);
            }

        }
    }

    protected static JSONObject jsonVarianteHelper(Variante v){
        JSONObject varianteObject = new JSONObject();
        varianteObject.put("idVariante", v.getIdVariante());
        varianteObject.put("idProdottoVariante", v.getIdProdotto());
        varianteObject.put("idGusto", v.getIdGusto());
        varianteObject.put("idConfezione", v.getIdConfezione());
        varianteObject.put("prezzo", v.getPrezzo());
        varianteObject.put("quantity", v.getQuantita());
        varianteObject.put("sconto", v.getSconto());


        int evidenzaNumber = 0;
        if (v.isEvidenza()) evidenzaNumber = 1;
        varianteObject.put("evidenza", evidenzaNumber);

        return varianteObject;
    }

    private void showProdottoRowTable(HttpServletRequest req, JSONArray jsonArray) {
        String action = req.getParameter("action");
        if (action != null && !action.isBlank() && action.equals("edit")){
            ProdottoDAO prodottoDAO = new ProdottoDAO();
            String primaryKey = req.getParameter("primaryKey");

            if (primaryKey != null && !primaryKey.isBlank()){
                Prodotto p = prodottoDAO.doRetrieveById(primaryKey);

                if (p != null){
                    JSONObject productObject = jsonProductHelper(p);
                    jsonArray.add(productObject);
                }
            }
        }
    }

    protected static JSONObject jsonProductHelper(Prodotto p){
        JSONObject productObject = new JSONObject();
        productObject.put("idProdotto", p.getIdProdotto());
        productObject.put("nome", p.getNome());
        productObject.put("descrizione", p.getDescrizione());
        productObject.put("categoria", p.getCategoria());
        productObject.put("immagine", p.getImmagine());
        productObject.put("calorie", p.getCalorie());
        productObject.put("carboidrati", p.getCarboidrati());
        productObject.put("proteine", p.getProteine());
        productObject.put("grassi", p.getGrassi());

        return productObject;
    }

    protected static JSONObject jsonHelper(Utente x) {
        JSONObject userObject = new JSONObject();
        userObject.put("email", x.getEmail());
        userObject.put("nome", x.getNome());
        userObject.put("cognome", x.getCognome());
        userObject.put("codiceFiscale", x.getCodiceFiscale());
        if (x.getDataNascita() != null) {
            userObject.put("dataDiNascita", new SimpleDateFormat("yyyy-MM-dd").format(x.getDataNascita()));
        } else {
            userObject.put("dataDiNascita", "");
        }
        userObject.put("indirizzo", x.getIndirizzo());
        userObject.put("telefono", x.getTelefono());
        return userObject;
    }
    private void showUtenteRowTable(HttpServletRequest request, JSONArray jsonArray) {

        String action = request.getParameter("action");
        if (action != null && !action.isBlank()){
            if (action.equals("edit")) {
                UtenteDAO utenteDAO = new UtenteDAO();
                String primaryKey = request.getParameter("primaryKey");

                if (primaryKey != null && !primaryKey.isBlank()) {
                    Utente x = utenteDAO.doRetrieveByEmail(primaryKey);

                    if (x != null) {
                        JSONObject userObject = jsonHelper(x);
                        jsonArray.add(userObject);
                    }
                }
            }
        }

    }



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
