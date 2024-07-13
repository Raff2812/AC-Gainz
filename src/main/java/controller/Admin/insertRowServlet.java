package controller.Admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/insertRow")
@MultipartConfig
public class insertRowServlet extends HttpServlet {
    private static final String CARTELLA_UPLOAD = "Immagini";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nameTable = req.getParameter("nameTable");
        System.out.println(nameTable);

        if ("utente".equals(nameTable)) {
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            String nome = req.getParameter("nome");
            String cognome = req.getParameter("cognome");
            String codiceFiscale = req.getParameter("codiceFiscale");
            String dataDiNascita = req.getParameter("dataDiNascita");
            String indirizzo = req.getParameter("indirizzo");
            String telefono = req.getParameter("telefono");

            System.out.println(email);
            System.out.println("Password:" + password);
            System.out.println(nome);
            System.out.println(cognome);
            System.out.println(codiceFiscale);
            System.out.println(dataDiNascita);
            System.out.println(indirizzo);
            System.out.println(telefono);

            if (email != null && !email.isBlank() && password != null && nome != null && cognome != null && codiceFiscale != null && dataDiNascita != null && indirizzo != null && telefono != null) {
                System.out.println("OK insert");
                UtenteDAO utenteDAO = new UtenteDAO();
                Utente u = new Utente();
                u.setEmail(email);
                u.setPassword(password);
                u.setNome(nome);
                u.setCognome(cognome);
                u.setCodiceFiscale(codiceFiscale);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date ddn = dateFormat.parse(dataDiNascita);
                    u.setDataNascita(ddn);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                u.setIndirizzo(indirizzo);
                u.setTelefono(telefono);
                utenteDAO.doSave(u);

                req.getRequestDispatcher("showTable?tableName=" + nameTable).forward(req, resp);
            } else {
                System.out.println("Not ok insert");
            }
        } else if ("prodotto".equals(nameTable)) {
            String idProdotto = req.getParameter("idProdotto");
            String nome = req.getParameter("nome");
            String descrizione = req.getParameter("descrizione");
            String categoria = req.getParameter("categoria");
            Part filePart = req.getPart("immagine");
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            String destinazione = CARTELLA_UPLOAD + "/" + fileName; // Utilizza / come separatore
            Path pathDestinazione = Paths.get(getServletContext().getRealPath(destinazione));

            // se un file con quel nome esiste già, gli cambia nome
            for (int i = 2; Files.exists(pathDestinazione); i++) {
                destinazione = CARTELLA_UPLOAD + "/" + i + "_" + fileName; // Utilizza / come separatore
                pathDestinazione = Paths.get(getServletContext().getRealPath(destinazione));
            }

            InputStream fileInputStream = filePart.getInputStream();
            Files.createDirectories(pathDestinazione.getParent());
            Files.copy(fileInputStream, pathDestinazione);

            String calorie = req.getParameter("calorie");
            String carboidrati = req.getParameter("carboidrati");
            String proteine = req.getParameter("proteine");
            String grassi = req.getParameter("grassi");

            if (idProdotto != null && !idProdotto.isBlank() && nome != null && descrizione != null && categoria != null && calorie != null && carboidrati != null && proteine != null && grassi != null) {
                Prodotto p = new Prodotto();
                ProdottoDAO prodottoDAO = new ProdottoDAO();
                p.setIdProdotto(idProdotto);
                p.setNome(nome);
                p.setDescrizione(descrizione);
                p.setCategoria(categoria);
                p.setImmagine(destinazione);
                p.setCalorie(Integer.parseInt(calorie));
                p.setCarboidrati(Integer.parseInt(carboidrati));
                p.setProteine(Integer.parseInt(proteine));
                p.setGrassi(Integer.parseInt(grassi));

                prodottoDAO.doSave(p);

                req.getRequestDispatcher("showTable?tableName=" + nameTable).forward(req, resp);
            }
        }else if ("variante".equals(nameTable)){
            String idProdottoVariante = req.getParameter("idProdottoVariante");
            System.out.println(idProdottoVariante);
            String idGusto = req.getParameter("idGusto");
            System.out.println(idGusto);
            String idConfezione = req.getParameter("idConfezione");
            System.out.println(idConfezione);
            String prezzo = req.getParameter("prezzo");
            System.out.println(prezzo);
            String quantity = req.getParameter("quantity");
            System.out.println(quantity);
            String sconto = req.getParameter("sconto");
            System.out.println(sconto);
            String evidenza = req.getParameter("evidenza");
            System.out.println(evidenza);

            if (idProdottoVariante != null && idGusto != null && idConfezione != null && prezzo != null && quantity != null && sconto != null && evidenza != null){
                float price = Float.parseFloat(prezzo);
                int q = Integer.parseInt(quantity);
                int discount = Integer.parseInt(sconto);
                boolean evidence = Integer.parseInt(evidenza) == 1;

                if (price > 0 && q > 0 && discount > 0 && discount <= 100){
                    System.out.println("Ok parameters");
                    VarianteDAO varianteDAO = new VarianteDAO();
                    Variante v = new Variante();
                    v.setIdProdotto(idProdottoVariante);
                    v.setIdGusto(Integer.parseInt(idGusto));
                    v.setIdConfezione(Integer.parseInt(idConfezione));
                    v.setPrezzo(price);
                    v.setQuantita(q);
                    v.setSconto(discount);
                    v.setEvidenza(evidence);
                    varianteDAO.doSaveVariante(v);


                }
            }

            req.getRequestDispatcher("showTable?tableName=" + nameTable).forward(req, resp);

        } else if ("ordine".equals(nameTable)) {
            String emailUtente = req.getParameter("emailUtente");
            String stato = req.getParameter("stato");
            String totale = req.getParameter("totale");
            String descrizione = req.getParameter("descrizione");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date data = null;
            try {
                if (req.getParameter("data") != null && !req.getParameter("data").isBlank())
                         data = dateFormat.parse(req.getParameter("data"));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            if (emailUtente != null){
                Ordine ordine = new Ordine();
                if (totale != null && !totale.isBlank() && Float.parseFloat(totale) >= 0){
                    float price = Float.parseFloat(totale);
                    ordine.setTotale(price);
                }

                    ordine.setEmailUtente(emailUtente);
                    ordine.setStato(stato);
                    ordine.setDataOrdine(data);
                    ordine.setDescrizione(descrizione);

                    OrdineDao ordineDao = new OrdineDao();
                    ordineDao.doSave(ordine);
                }

            req.getRequestDispatcher("showTable?tableName=" + nameTable).forward(req, resp);
            }


        }
}

