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
        HttpSession session = req.getSession();
        Utente utente = (Utente) session.getAttribute("Utente");

        if (utente != null) {
            OrdineDao ordineDao = new OrdineDao();
            List<Ordine> ordini = ordineDao.doRetrieveByEmail(utente.getEmail());
            HashMap<Integer, List<DettaglioOrdine>> dettaglioOrdini = new HashMap<>();
            DettaglioOrdineDAO dettaglioOrdineDAO = new DettaglioOrdineDAO();

            for (Ordine ordine : ordini) {
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

    private static List<DettaglioOrdine> parseDescrizione(String descrizione) {
        System.out.println(descrizione);
        List<DettaglioOrdine> dettagli = new ArrayList<>();
        String[] prodotti = descrizione.split(";");

        for (String prodotto : prodotti) {
            String[] attributi = prodotto.trim().split("\\n");

            String nomeProdotto = "";
            String gusto = "";
            int pesoConfezione = 0;
            int quantita = 0;
            float prezzo = 0;

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


