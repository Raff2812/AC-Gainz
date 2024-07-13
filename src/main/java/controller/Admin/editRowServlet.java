package controller.Admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import static controller.Admin.deleteRowServlet.sendAllUtentiResponse;

@WebServlet(value = "/editRow")
@MultipartConfig
public class editRowServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String tableName = req.getParameter("tableName");
        System.out.println("tableName: " + tableName);
        String primaryKey = req.getParameter("primaryKey");

        if ("utente".equals(tableName)) {
            String email = req.getParameter("email");
            String nome = req.getParameter("nome");
            String cognome = req.getParameter("cognome");
            String codiceFiscale = req.getParameter("codiceFiscale");
            String dataDiNascita = req.getParameter("dataDiNascita");
            String indirizzo = req.getParameter("indirizzo");
            String telefono = req.getParameter("telefono");

            System.out.println("email: " + email);
            System.out.println("nome: " + nome);
            System.out.println("cognome: " + cognome);
            System.out.println("codiceFiscale: " + codiceFiscale);
            System.out.println("dataDiNascita: " + dataDiNascita);
            System.out.println("indirizzo: " + indirizzo);
            System.out.println("telefono: " + telefono);

            if (email != null && !email.isBlank() && nome != null && cognome != null && codiceFiscale != null && dataDiNascita != null && indirizzo != null && telefono != null){
                System.out.println("OK edit");
                UtenteDAO utenteDAO = new UtenteDAO();
                Utente u = new Utente();
                u.setEmail(email);
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
                utenteDAO.doUpdateCustomer(u, primaryKey);

                /*sendAllUtentiResponse(utenteDAO, resp);*/
                req.getRequestDispatcher("showTable?tableName="+tableName).forward(req, resp);
            } else {
                System.out.println("Not ok edit");
            }
        }else if ("prodotto".equals(tableName)){
            String idProdotto = req.getParameter("idProdotto");
            System.out.println(idProdotto);
            String nome = req.getParameter("nome");
            System.out.println(nome);
            String descrizione = req.getParameter("descrizione");
            System.out.println(descrizione);
            String categoria = req.getParameter("categoria");
            System.out.println(categoria);
            String immagine = req.getParameter("immagine");
            System.out.println(immagine);
            String calorie = req.getParameter("calorie");
            System.out.println(calorie);
            String carboidrati = req.getParameter("carboidrati");
            System.out.println(carboidrati);
            String proteine = req.getParameter("proteine");
            System.out.println(proteine);
            String grassi = req.getParameter("grassi");
            System.out.println(grassi);

            if (idProdotto != null && !idProdotto.isBlank() && nome != null && descrizione != null && categoria != null && calorie != null && carboidrati != null && proteine != null && grassi != null){
                ProdottoDAO prodottoDAO = new ProdottoDAO();
                Prodotto p = prodottoDAO.doRetrieveById(idProdotto);

                    Prodotto x = new Prodotto();
                    x.setIdProdotto(idProdotto);
                    x.setNome(nome);
                    x.setDescrizione(descrizione);
                    x.setCategoria(categoria);
                    x.setImmagine(immagine);
                    x.setCalorie(Integer.parseInt(calorie));
                    x.setCarboidrati(Integer.parseInt(carboidrati));
                    x.setProteine(Integer.parseInt(proteine));
                    x.setGrassi(Integer.parseInt(grassi));

                    prodottoDAO.updateProduct(x, primaryKey);
                    req.getRequestDispatcher("showTable?tableName="+tableName).forward(req, resp);
            }else{
                System.out.println("Not ok edit Product");
            }
        }else if("variante".equals(tableName)){
            String idVariante = req.getParameter("idVariante");
            System.out.println(idVariante);
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


            if (idVariante != null && !idVariante.isBlank() && idProdottoVariante != null && idGusto != null && idConfezione != null && prezzo != null && quantity != null && sconto != null && evidenza != null) {
                VarianteDAO varianteDAO = new VarianteDAO();

                Variante v = new Variante();
                v.setIdVariante(Integer.parseInt(idVariante));
                v.setIdProdotto(idProdottoVariante);
                v.setIdGusto(Integer.parseInt(idGusto));
                v.setIdConfezione(Integer.parseInt(idConfezione));
                v.setPrezzo(Float.parseFloat(prezzo));
                v.setQuantita(Integer.parseInt(quantity));
                v.setSconto(Integer.parseInt(sconto));

                boolean evidence = Integer.parseInt(evidenza) == 1;
                v.setEvidenza(evidence);

                varianteDAO.updateVariante(v, Integer.parseInt(primaryKey));

                req.getRequestDispatcher("showTable?tableName="+tableName).forward(req, resp);

            }
        }else if ("ordine".equals(tableName)){
            String idOrdine = req.getParameter("idOrdine");
            String emailUtente = req.getParameter("emailUtente");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;
            try {
                date = dateFormat.parse(req.getParameter("data"));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            String stato = req.getParameter("stato");
            float totale = Float.parseFloat(req.getParameter("totale"));
            String descrizione = req.getParameter("descrizione");

            if (idOrdine != null && !idOrdine.isBlank() && emailUtente != null && date != null && stato != null && totale >=0 && descrizione != null){
                OrdineDao ordineDao = new OrdineDao();

                if (ordineDao.doRetrieveById(Integer.parseInt(primaryKey)) != null){
                    Ordine ordine = new Ordine();
                    ordine.setIdOrdine(Integer.parseInt(idOrdine));
                    ordine.setStato(stato);
                    ordine.setEmailUtente(emailUtente);
                    ordine.setTotale(totale);
                    ordine.setDataOrdine(date);
                    ordine.setDescrizione(descrizione);

                    ordineDao.doUpdateOrder(ordine, Integer.parseInt(primaryKey));
                }
            }
            req.getRequestDispatcher("showTable?tableName="+tableName).forward(req, resp);

        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }
}
