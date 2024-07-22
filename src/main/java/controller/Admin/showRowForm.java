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
        // Recupera il nome della tabella e la chiave primaria dai parametri della richiesta
        String tableName = req.getParameter("tableName");
        String primaryKey = req.getParameter("primaryKey");

        // Crea un JSONArray per raccogliere le righe della tabella in formato JSON
        JSONArray jsonArray = new JSONArray();
        resp.setContentType("application/json");

        System.out.println(tableName + " ShowRowForm");
        // Controlla se i parametri tableName e primaryKey sono validi
        if (tableName != null && !tableName.isBlank() && primaryKey != null && !primaryKey.isBlank()) {
            // Determina quale tabella mostrare
            switch (tableName) {
                case "utente" -> showUtenteRowTable(primaryKey, jsonArray);
                case "prodotto" -> showProdottoRowTable(primaryKey, jsonArray);
                case "variante" -> showVarianteRowTable(primaryKey, jsonArray);
                case "ordine" -> showOrdineRowTable(primaryKey, jsonArray);
                case "dettaglioOrdine" -> showDettaglioOrdineRowTable(primaryKey, jsonArray);
                case "gusto" -> showGustoRowTable(primaryKey, jsonArray);
                case "confezione" -> showConfezioneRowTable(primaryKey, jsonArray);
                default -> throw new ServletException("Tabella non valida.");
            }
        }

        // Invia il JSON di risposta
        try (PrintWriter out = resp.getWriter()) {
            out.println(jsonArray.toJSONString());
            out.flush();
        }
    }


    //Prende la Confezione dal DB tramite la primaryKey e la aggiunge ad un JSONARRAY
    private void showConfezioneRowTable(String primaryKey, JSONArray jsonArray) {
        ConfezioneDAO confezioneDAO = new ConfezioneDAO();
        int idConfezione = Integer.parseInt(primaryKey);
        Confezione confezione = confezioneDAO.doRetrieveById(idConfezione);
        if (confezione != null) {
            jsonArray.add(confezioneHelper(confezione));
        }
    }

    //Prende il gusto dal DB tramite la primaryKey e la aggiunge ad un JSONARRAY
    private void showGustoRowTable(String primaryKey, JSONArray jsonArray) {
        GustoDAO gustoDAO = new GustoDAO();
        int idGusto = Integer.parseInt(primaryKey);
        Gusto gusto = gustoDAO.doRetrieveById(idGusto);
        if (gusto != null) {
            jsonArray.add(gustoHelper(gusto));
        }
    }


    //Prende il dettaglio ordine dal DB tramite la primaryKey e la aggiunge ad un JSONARRAY
    private void showDettaglioOrdineRowTable(String primaryKey, JSONArray jsonArray) {
        DettaglioOrdineDAO dettaglioOrdineDAO = new DettaglioOrdineDAO();
        String[] keys = primaryKey.split(", ");
        if (keys.length == 3) {
            int idOrdine = Integer.parseInt(keys[0]);
            int idVariante = Integer.parseInt(keys[2]);
            DettaglioOrdine dettaglioOrdine = dettaglioOrdineDAO.doRetrieveByIdOrderAndIdVariant(idOrdine, idVariante);
            if (dettaglioOrdine != null) {
                jsonArray.add(dettaglioOrdineHelper(dettaglioOrdine));
            }
        }
    }


    //Prende l'ordine dal DB tramite la primaryKey e la aggiunge ad un JSONARRAY

    private void showOrdineRowTable(String primaryKey, JSONArray jsonArray) {
        OrdineDao ordineDao = new OrdineDao();
        Ordine ordine = ordineDao.doRetrieveById(Integer.parseInt(primaryKey));
        if (ordine != null) {
            System.out.println(ordine.getIdOrdine() + " OOOOKKK");
            jsonArray.add(jsonOrdineHelper(ordine));
        }
    }


    //Prende la variante dal DB tramite la primaryKey e la aggiunge ad un JSONARRAY
    private void showVarianteRowTable(String primaryKey, JSONArray jsonArray) {
        VarianteDAO varianteDAO = new VarianteDAO();
        Variante variante = varianteDAO.doRetrieveVarianteByIdVariante(Integer.parseInt(primaryKey));
        if (variante != null) {
            jsonArray.add(jsonVarianteHelper(variante));
        }
    }


    //Prende il prodotto dal DB tramite la primaryKey e la aggiunge ad un JSONARRAY

    private void showProdottoRowTable(String primaryKey, JSONArray jsonArray) {
        ProdottoDAO prodottoDAO = new ProdottoDAO();
        Prodotto prodotto = prodottoDAO.doRetrieveById(primaryKey);
        if (prodotto != null) {
            jsonArray.add(jsonProductHelper(prodotto));
        }
    }


    //Prende l'utente dal DB tramite la primaryKey e la aggiunge ad un JSONARRAY
    private void showUtenteRowTable(String primaryKey, JSONArray jsonArray) {
        UtenteDAO utenteDAO = new UtenteDAO();
        Utente utente = utenteDAO.doRetrieveByEmail(primaryKey);
        if (utente != null) {
            jsonArray.add(jsonUtenteHelper(utente));
        }
    }


    //Crea un oggetto JSON
    protected static JSONObject confezioneHelper(Confezione confezione) {
        JSONObject confezioneObject = new JSONObject();
        confezioneObject.put("idConfezione", confezione.getIdConfezione());
        confezioneObject.put("pesoConfezione", confezione.getPeso());
        return confezioneObject;
    }

    //Crea un oggetto JSON
    protected static JSONObject gustoHelper(Gusto gusto) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("idGusto", gusto.getIdGusto());
        jsonObject.put("nomeGusto", gusto.getNomeGusto());
        return jsonObject;
    }

    //Crea un oggetto JSON
    protected static JSONObject dettaglioOrdineHelper(DettaglioOrdine dettaglioOrdine) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("idOrdine", dettaglioOrdine.getIdOrdine());
        jsonObject.put("idProdotto", dettaglioOrdine.getIdProdotto());
        jsonObject.put("idVariante", dettaglioOrdine.getIdVariante());
        jsonObject.put("quantity", dettaglioOrdine.getQuantita());
        jsonObject.put("prezzo", dettaglioOrdine.getPrezzo());
        return jsonObject;
    }

    //Crea un oggetto JSON
    protected static JSONObject jsonOrdineHelper(Ordine ordine) {
        JSONObject ordineObject = new JSONObject();
        ordineObject.put("idOrdine", ordine.getIdOrdine());
        ordineObject.put("emailUtente", ordine.getEmailUtente());
        ordineObject.put("stato", ordine.getStato());
        ordineObject.put("data", ordine.getDataOrdine() != null ? new SimpleDateFormat("yyyy-MM-dd").format(ordine.getDataOrdine()) : "");
        ordineObject.put("totale", ordine.getTotale());
        ordineObject.put("descrizione", ordine.getDescrizione());
        return ordineObject;
    }

    //Crea un oggetto JSON
    protected static JSONObject jsonVarianteHelper(Variante variante) {
        JSONObject varianteObject = new JSONObject();
        varianteObject.put("idVariante", variante.getIdVariante());
        varianteObject.put("idProdottoVariante", variante.getIdProdotto());
        varianteObject.put("idGusto", variante.getIdGusto());
        varianteObject.put("idConfezione", variante.getIdConfezione());
        varianteObject.put("prezzo", variante.getPrezzo());
        varianteObject.put("quantity", variante.getQuantita());
        varianteObject.put("sconto", variante.getSconto());
        varianteObject.put("evidenza", variante.isEvidenza() ? 1 : 0);
        return varianteObject;
    }

    //Crea un oggetto JSON
    protected static JSONObject jsonProductHelper(Prodotto prodotto) {
        JSONObject productObject = new JSONObject();
        productObject.put("idProdotto", prodotto.getIdProdotto());
        productObject.put("nome", prodotto.getNome());
        productObject.put("descrizione", prodotto.getDescrizione());
        productObject.put("categoria", prodotto.getCategoria());
        productObject.put("immagine", prodotto.getImmagine());
        productObject.put("calorie", prodotto.getCalorie());
        productObject.put("carboidrati", prodotto.getCarboidrati());
        productObject.put("proteine", prodotto.getProteine());
        productObject.put("grassi", prodotto.getGrassi());
        return productObject;
    }

    //Crea un oggetto JSON
    protected static JSONObject jsonUtenteHelper(Utente utente) {
        JSONObject userObject = new JSONObject();
        userObject.put("email", utente.getEmail());
        userObject.put("password", utente.getPassword());
        userObject.put("nome", utente.getNome());
        userObject.put("cognome", utente.getCognome());
        userObject.put("codiceFiscale", utente.getCodiceFiscale());
        userObject.put("dataDiNascita", utente.getDataNascita() != null ? new SimpleDateFormat("yyyy-MM-dd").format(utente.getDataNascita()) : "");
        userObject.put("indirizzo", utente.getIndirizzo());
        userObject.put("telefono", utente.getTelefono());
        return userObject;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
