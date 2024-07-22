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
import java.util.List;

import static controller.Admin.showRowForm.*;

@WebServlet(value = "/deleteRow")
@SuppressWarnings("unchecked")
public class deleteRowServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //prendiamo dalla Request i parametri che ci servono (Ovvero nome della tabella e chiave primaria)
        String tableName = req.getParameter("tableName");
        String primaryKey = req.getParameter("primaryKey");
        Utente utente = (Utente) req.getSession().getAttribute("Utente"); // usato per controllare se admin cancella il suo stesso profilo

        boolean isTheSame = false;

        //Controlliamo quale delle tabelle è quella scelta dall'admin
        JSONArray jsonArray = null;
        boolean success = switch (tableName) {
            case "utente" -> handleRemoveRowFromUtente(primaryKey, utente);
            case "prodotto" -> handleRemoveRowFromProdotto(primaryKey);
            case "variante" -> handleRemoveRowFromVariante(primaryKey);
            case "ordine" -> handleRemoveRowFromOrdine(primaryKey);
            case "dettaglioOrdine" -> handleRemoveRowFromDettaglioOrdine(primaryKey);
            case "gusto" -> handleRemoveRowFromGusto(primaryKey);
            case "confezione" -> handleRemoveRowFromConfezione(primaryKey);
            default -> false;
        };

        //se abbiamo un successo e la tabella che è l'utente controlliamo se sta eliminando se stesso
        if (success && tableName.equals("utente")) {
            isTheSame = checkIfAdminDeletingSelf(primaryKey, utente);
        }

        //se è lo stesso invalida la sessione e reindirizza all'homepage altrimenti prendi la tupla e mandala
        if (isTheSame) {
            req.getSession(false).invalidate();
            resp.sendRedirect("index.jsp");
            return; // Interrompe l'esecuzione
        } else {
            if (success) {
                jsonArray = getJsonArrayForTable(tableName);
            }

            if (jsonArray != null) {
                sendJsonResponse(jsonArray, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid table name or primary key.");
            }
        }
    }


    //controlla se l'admin sta cancellando se stesso dal database
    private boolean checkIfAdminDeletingSelf(String primaryKey, Utente utente) {
        System.out.println("Checking if admin is deleting self");  // Log per debug
        return primaryKey != null && !primaryKey.isBlank() && utente.getEmail().equals(primaryKey);
    }



    //Rimuove una row dalla tabella confezione(Utilizzando metodi del DAO)
    private boolean handleRemoveRowFromConfezione(String primaryKey) {
        ConfezioneDAO confezioneDAO = new ConfezioneDAO();
        if (isValidPrimaryKey(primaryKey)) {
            int idConfezione = Integer.parseInt(primaryKey);
            confezioneDAO.doRemoveConfezione(idConfezione);
            return true;
        }
        return false;
    }

    //Rimuove una row dalla tabella Gusto(Utilizzando metodi del DAO)
    private boolean handleRemoveRowFromGusto(String primaryKey) {
        if (isValidPrimaryKey(primaryKey)) {
            int idGusto = Integer.parseInt(primaryKey);
            GustoDAO gustoDAO = new GustoDAO();
            gustoDAO.doRemoveGusto(idGusto);
            return true;
        }
        return false;
    }

    //Rimuove una row dalla tabella Dettaglio Ordine(Dettaglio ordine ha due chiavi primarie)(Utilizzando metodi del DAO)
    private boolean handleRemoveRowFromDettaglioOrdine(String primaryKey) {
        if (primaryKey != null && !primaryKey.isBlank()) {
            String[] primaryKeys = primaryKey.split(", ");
            DettaglioOrdineDAO dettaglioOrdineDAO = new DettaglioOrdineDAO();
            dettaglioOrdineDAO.doRemoveDettaglioOrdine(Integer.parseInt(primaryKeys[0]), Integer.parseInt(primaryKeys[2]));
            return true;
        }
        return false;
    }


    //Rimuove una row dalla tabella Ordine(Utilizzando metodi del DAO)
    private boolean handleRemoveRowFromOrdine(String primaryKey) {
        if (isValidPrimaryKey(primaryKey)) {
            OrdineDao ordineDao = new OrdineDao();
            ordineDao.doDeleteOrder(Integer.parseInt(primaryKey));
            return true;
        }
        return false;
    }


    //Rimuove una row dalla tabella Variante(Utilizzando metodi del DAO)
    private boolean handleRemoveRowFromVariante(String primaryKey) {
        if (isValidPrimaryKey(primaryKey)) {
            VarianteDAO varianteDAO = new VarianteDAO();
            Variante v = varianteDAO.doRetrieveVarianteByIdVariante(Integer.parseInt(primaryKey));
            if (v != null) {
                varianteDAO.doRemoveVariante(Integer.parseInt(primaryKey));
            }
            return true;
        }
        return false;
    }


    //Rimuove una row dalla tabella Prodotto(Utilizzando metodi del DAO)
    private boolean handleRemoveRowFromProdotto(String primaryKey) {
        if (primaryKey != null && !primaryKey.isBlank()) {
            ProdottoDAO prodottoDAO = new ProdottoDAO();
            Prodotto p = prodottoDAO.doRetrieveById(primaryKey);
            if (p != null) {
                prodottoDAO.removeProductFromIdProdotto(primaryKey);
            }
            return true;
        }
        return false;
    }


    //Rimuove una row dalla tabella Utente(Utilizzando metodi del DAO)
    private boolean handleRemoveRowFromUtente(String primaryKey, Utente utente) throws IOException {
        if (primaryKey != null && !primaryKey.isBlank()) {
            UtenteDAO utenteDAO = new UtenteDAO();
            Utente u = utenteDAO.doRetrieveByEmail(primaryKey);
            if (u != null) {
                utenteDAO.doRemoveUserByEmail(u.getEmail());
                return true;
            }
        }
        return false;
    }


    //In base alla tabella scelta viene creato un array Json con tutte le row della tabella
    private JSONArray getJsonArrayForTable(String tableName) {
        switch (tableName) {
            case "utente":
                return getAllUtentiJsonArray(new UtenteDAO());
            case "prodotto":
                return getAllProdottiJsonArray(new ProdottoDAO());
            case "variante":
                return getAllVariantiJsonArray(new VarianteDAO());
            case "ordine":
                return getAllOrdiniJsonArray(new OrdineDao());
            case "dettaglioOrdine":
                return getAllDettagliOrdiniJsonArray(new DettaglioOrdineDAO());
            case "gusto":
                return getAllGustiJsonArray(new GustoDAO());
            case "confezione":
                return getAllConfezioniJsonArray(new ConfezioneDAO());
            default:
                return null;
        }
    }

    //Prende tutte le Row della tabella scelta dal DB tramite metodo DAO e li inserisce in un JSONArray
    private static JSONArray getAllUtentiJsonArray(UtenteDAO utenteDAO) {
        List<Utente> utenti = utenteDAO.doRetrieveAll();
        JSONArray jsonArray = new JSONArray();
        for (Utente utente : utenti) {
            jsonArray.add(jsonHelperHere(utente));
        }
        return jsonArray;
    }


    //Prende tutte le Row della tabella scelta dal DB tramite metodo DAO e li inserisce in un JSONArray
    private static JSONArray getAllProdottiJsonArray(ProdottoDAO prodottoDAO) {
        List<Prodotto> prodotti = prodottoDAO.doRetrieveAll();
        JSONArray jsonArray = new JSONArray();
        for (Prodotto prodotto : prodotti) {
            jsonArray.add(jsonProductHelper(prodotto));
        }
        return jsonArray;
    }


    //Prende tutte le Row della tabella scelta dal DB tramite metodo DAO e li inserisce in un JSONArray
    private static JSONArray getAllVariantiJsonArray(VarianteDAO varianteDAO) {
        List<Variante> varianti = varianteDAO.doRetrieveAll();
        JSONArray jsonArray = new JSONArray();
        for (Variante variante : varianti) {
            jsonArray.add(jsonVarianteHelper(variante));
        }
        return jsonArray;
    }


    //Prende tutte le Row della tabella scelta dal DB tramite metodo DAO e li inserisce in un JSONArray
    private static JSONArray getAllOrdiniJsonArray(OrdineDao ordineDao) {
        List<Ordine> ordini = ordineDao.doRetrieveAll();
        JSONArray jsonArray = new JSONArray();
        for (Ordine ordine : ordini) {
            jsonArray.add(jsonOrdineHelper(ordine));
        }
        return jsonArray;
    }


    //Prende tutte le Row della tabella scelta dal DB tramite metodo DAO e li inserisce in un JSONArray
    private static JSONArray getAllDettagliOrdiniJsonArray(DettaglioOrdineDAO dettaglioOrdineDAO) {
        List<DettaglioOrdine> dettagliOrdini = dettaglioOrdineDAO.doRetrieveAll();
        JSONArray jsonArray = new JSONArray();
        for (DettaglioOrdine dettaglioOrdine : dettagliOrdini) {
            jsonArray.add(dettaglioOrdineHelper(dettaglioOrdine));
        }
        return jsonArray;
    }


    //Prende tutte le Row della tabella scelta dal DB tramite metodo DAO e li inserisce in un JSONArray
    private static JSONArray getAllGustiJsonArray(GustoDAO gustoDAO) {
        List<Gusto> gusti = gustoDAO.doRetrieveAll();
        JSONArray jsonArray = new JSONArray();
        for (Gusto gusto : gusti) {
            jsonArray.add(gustoHelper(gusto));
        }
        return jsonArray;
    }


    //Prende tutte le Row della tabella scelta dal DB tramite metodo DAO e li inserisce in un JSONArray
    private static JSONArray getAllConfezioniJsonArray(ConfezioneDAO confezioneDAO) {
        List<Confezione> confezioni = confezioneDAO.doRetrieveAll();
        JSONArray jsonArray = new JSONArray();
        for (Confezione confezione : confezioni) {
            jsonArray.add(confezioneHelper(confezione));
        }
        return jsonArray;
    }

    //Invia la risposta JSon
    private static void sendJsonResponse(JSONArray jsonArray, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter o = response.getWriter();
        o.println(jsonArray);
        o.flush();
    }

    //Controlla se la chiave primaria è valida
    private boolean isValidPrimaryKey(String primaryKey) {
        return primaryKey != null && !primaryKey.isBlank() && Integer.parseInt(primaryKey) > 0;
    }



    //Crea un oggetto JSON con i valori degli attributi presi dalla tabella nel DB
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
