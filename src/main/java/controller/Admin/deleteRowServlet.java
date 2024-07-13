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
import java.util.ArrayList;
import java.util.List;

import static controller.Admin.showRowForm.*;

@WebServlet(value = "/deleteRow")
@SuppressWarnings("unchecked")
public class deleteRowServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String tableName = req.getParameter("tableName");
        String primaryKey = req.getParameter("primaryKey");

        switch (tableName) {
            case "utente" -> handleRemoveRowFromUtente(primaryKey, req, resp);
            case "prodotto" -> handleRemoveRowFromProdotto(primaryKey, req, resp);
            case "variante" -> handleRemoveRowFromVariante(primaryKey, req, resp);
            case "ordine" -> handleRemoveRowFromOrdine(primaryKey, req, resp);
            case "dettaglioOrdine" -> handleRemoveRowFromDettaglioOrdine(primaryKey, req, resp);
            case "gusto" -> handleRemoveRowFromGusto(primaryKey, req, resp);
            case "confezione" -> handleRemoveRowFromConfezione(primaryKey, req, resp);
        }
    }

    private void handleRemoveRowFromConfezione(String primaryKey, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ConfezioneDAO confezioneDAO = new ConfezioneDAO();
        if (primaryKey != null && !primaryKey.isBlank() && Integer.parseInt(primaryKey) > 0){
            int idConfezione = Integer.parseInt(primaryKey);
            confezioneDAO.doRemoveConfezione(idConfezione);
        }

        sendAllConfezioniResponse(confezioneDAO, resp);
    }

    protected static void sendAllConfezioniResponse(ConfezioneDAO confezioneDAO, HttpServletResponse resp) throws IOException {
        List<Confezione> confezioni = new ArrayList<>();
        confezioni = confezioneDAO.doRetrieveAll();

        JSONArray jsonArray = new JSONArray();
        for (Confezione confezione: confezioni){
            JSONObject jsonObject = confezioneHelper(confezione);
            jsonArray.add(jsonObject);
        }

        resp.setContentType("application/json");
        PrintWriter o = resp.getWriter();
        o.println(jsonArray);
        o.flush();
    }


    private void handleRemoveRowFromGusto(String primaryKey, HttpServletRequest request, HttpServletResponse response) throws IOException {
        int idGusto = Integer.parseInt(primaryKey);
        GustoDAO gustoDAO = new GustoDAO();
        gustoDAO.doRemoveGusto(idGusto);

        sendAllGustiResponse(gustoDAO, response);
    }

    protected void sendAllGustiResponse(GustoDAO gustoDAO, HttpServletResponse response) throws IOException {
        List<Gusto> gusti = gustoDAO.doRetrieveAll();

        JSONArray jsonArray = new JSONArray();
        for (Gusto g: gusti){
            JSONObject jsonObject = gustoHelper(g);
            jsonArray.add(jsonObject);
        }



        response.setContentType("application/json");
        PrintWriter o = response.getWriter();
        o.println(jsonArray);
        o.flush();
    }

    private void handleRemoveRowFromDettaglioOrdine(String primaryKey, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String[] primaryKeys = primaryKey.split(", ");
        DettaglioOrdineDAO dettaglioOrdineDAO = new DettaglioOrdineDAO();
        dettaglioOrdineDAO.doRemoveDettaglioOrdine(Integer.parseInt(primaryKeys[0]), Integer.parseInt(primaryKeys[2]));

        sendAllDettaglioOrdiniResponse(dettaglioOrdineDAO, response);
    }

    protected void sendAllDettaglioOrdiniResponse(DettaglioOrdineDAO dettaglioOrdineDAO, HttpServletResponse response) throws IOException {
        List<DettaglioOrdine> dettagliOrdini = new ArrayList<>();
        dettagliOrdini = dettaglioOrdineDAO.doRetrieveAll();

        JSONArray jsonArray = new JSONArray();
        for (DettaglioOrdine d: dettagliOrdini){
            JSONObject jsonObject = dettaglioOrdineHelper(d);
            jsonArray.add(jsonObject);
        }

        response.setContentType("application/json");
        PrintWriter o = response.getWriter();
        o.println(jsonArray);
        o.flush();
    }

    private void handleRemoveRowFromOrdine(String primaryKey, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        OrdineDao ordineDao = new OrdineDao();
        ordineDao.doDeleteOrder(Integer.parseInt(primaryKey));

        sendAllOrdiniResponse(ordineDao, resp);
    }

    protected void sendAllOrdiniResponse(OrdineDao ordineDao, HttpServletResponse response) throws IOException {
        List<Ordine> ordini = new ArrayList<>();
        ordini = ordineDao.doRetrieveAll();
        JSONArray jsonArray = new JSONArray();
        for (Ordine o: ordini){
            JSONObject ordineObject = jsonOrdineHelper(o);
            jsonArray.add(ordineObject);
        }

        response.setContentType("application/json");
        PrintWriter o = response.getWriter();
        o.println(jsonArray);
        o.flush();
    }


    private void handleRemoveRowFromVariante(String primaryKey, HttpServletRequest request, HttpServletResponse response) throws IOException {
        VarianteDAO varianteDAO = new VarianteDAO();
        Variante v = varianteDAO.doRetrieveVarianteByIdVariante(Integer.parseInt(primaryKey));
        if (v != null) varianteDAO.doRemoveVariante(Integer.parseInt(primaryKey));

        sendAllVariantiResponse(varianteDAO, response);
    }

    protected static void sendAllVariantiResponse(VarianteDAO varianteDAO, HttpServletResponse response) throws IOException {
        List<Variante> varianti = varianteDAO.doRetrieveAll();
        JSONArray jsonArray = new JSONArray();
        for (Variante v: varianti){
            JSONObject varianteObject = jsonVarianteHelper(v);
            jsonArray.add(varianteObject);
        }

        response.setContentType("application/json");
        PrintWriter o = response.getWriter();
        o.println(jsonArray);
        o.flush();
    }


    private void handleRemoveRowFromProdotto(String primaryKey, HttpServletRequest request, HttpServletResponse response) throws IOException {
        ProdottoDAO prodottoDAO = new ProdottoDAO();
        Prodotto p = prodottoDAO.doRetrieveById(primaryKey);
        if (p != null){
            prodottoDAO.removeProductFromIdProdotto(primaryKey);
        }

        sendAllProductsResponse(prodottoDAO, response);
    }


    protected void sendAllProductsResponse(ProdottoDAO prodottoDAO, HttpServletResponse response) throws IOException {
        List<Prodotto> prodotti = prodottoDAO.doRetrieveAll();
        JSONArray jsonArray = new JSONArray();
        for (Prodotto p: prodotti){
            jsonArray.add(jsonProductHelper(p));
        }

        response.setContentType("application/json");
        PrintWriter o = response.getWriter();
        o.println(jsonArray);
        o.flush();
    }



    private void handleRemoveRowFromUtente(String primaryKey, HttpServletRequest request, HttpServletResponse response) throws IOException {
        UtenteDAO utenteDAO = new UtenteDAO();
        Utente u = utenteDAO.doRetrieveByEmail(primaryKey);
        if (u != null) {
            utenteDAO.doRemoveUserByEmail(primaryKey);
        }

        sendAllUtentiResponse(utenteDAO, response);
    }

    protected static void sendAllUtentiResponse(UtenteDAO utenteDAO, HttpServletResponse response) throws IOException {
        List<Utente> utenti = utenteDAO.doRetrieveAll();

        JSONArray jsonArray = new JSONArray();
        for (Utente utente : utenti) {
            jsonArray.add(jsonHelperHere(utente));
        }

        response.setContentType("application/json");
        PrintWriter o = response.getWriter();
        o.println(jsonArray);
        o.flush();
    }

    protected static JSONObject jsonHelperHere(Utente x) {
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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
