package controller.utente;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@WebServlet(value = "/areaUtenteServlet")
public class AreaPersonaleServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //prendiamo dati utente
        HttpSession session = req.getSession();
        Utente utente = (Utente) session.getAttribute("Utente");

        if (utente != null) {
            //prendiamo tutti gli ordini e i relativi dati sul singolo ordine
            OrdineDao ordineDao = new OrdineDao();
            List<Ordine> ordini = ordineDao.doRetrieveByEmail(utente.getEmail());
            HashMap<Integer, List<DettaglioOrdine>> dettaglioOrdini = new HashMap<>();
            DettaglioOrdineDAO dettaglioOrdineDAO = new DettaglioOrdineDAO();

            for (Ordine ordine : ordini) {
                //per ogni ordine prendiamo il resoconto dalla sua descrizione in modo da tenere salvati anche
                //eventuali prodotti eliminati dal DB
                if (ordine.getDescrizione() != null && !ordine.getDescrizione().isEmpty()) {
                    List<DettaglioOrdine> dettagli = parseDescrizione(ordine.getDescrizione());
                    dettaglioOrdini.put(ordine.getIdOrdine(), dettagli);
                } else {
                    List<DettaglioOrdine> dettagli = dettaglioOrdineDAO.doRetrieveById(ordine.getIdOrdine());
                    dettaglioOrdini.put(ordine.getIdOrdine(), dettagli);
                }
            }

            req.setAttribute("ordini", ordini);
            req.setAttribute("dettaglioOrdini", dettaglioOrdini);
            req.getRequestDispatcher("WEB-INF/AreaUtente.jsp").forward(req, resp);
        }
    }


    //metodo usato per creare un oggetto dettaglioOrdine dalla descrizione dell'ordine
    private static List<DettaglioOrdine> parseDescrizione(String descrizione) {
        List<DettaglioOrdine> dettagli = new ArrayList<>();
        //suddividiamo i prodotti nella descrizione
        String[] prodotti = descrizione.split(";");

        for (String prodotto : prodotti) {
            //suddividiamo gli attributi del singolo prodotto
            String[] attributi = prodotto.trim().split("\\n");

            String nomeProdotto = "";
            String gusto = "";
            int pesoConfezione = 0;
            int quantita = 0;
            float prezzo = 0;

            //prendiamo i valori per gli attributi
            for (String attributo : attributi) {
                attributo = attributo.trim(); // Rimuove gli spazi iniziali e finali
                if (attributo.startsWith("Prodotto:")) {
                    nomeProdotto = attributo.replace("Prodotto:", "").trim();
                } else if (attributo.startsWith("Gusto:")) {
                    gusto = attributo.replace("Gusto:", "").trim();
                } else if (attributo.startsWith("Confezione:")) {
                    pesoConfezione = Integer.parseInt(attributo.replace("Confezione:", "").replace(" grammi", "").trim());
                } else if (attributo.startsWith("Quantità:")) {
                    quantita = Integer.parseInt(attributo.replace("Quantità:", "").trim());
                } else if (attributo.startsWith("Prezzo:")) {
                    prezzo = Float.parseFloat(attributo.replace("Prezzo:", "").replace(" €", "").trim());
                }
            }

            //Creiamo il dettaglioOrdine
            DettaglioOrdine dettaglio = new DettaglioOrdine();
            dettaglio.setNomeProdotto(nomeProdotto);
            dettaglio.setGusto(gusto);
            dettaglio.setPesoConfezione(pesoConfezione);
            dettaglio.setQuantita(quantita);
            dettaglio.setPrezzo(prezzo);

            dettagli.add(dettaglio);
        }

        return dettagli;
    }
}


