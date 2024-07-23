package controller.homepage;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Prodotto;
import model.ProdottoDAO;
import model.Variante;
import model.VarianteDAO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@WebServlet(value = "/showOptions")
public class ShowOptions extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idVariante = req.getParameter("idVariante");
        String action = req.getParameter("action");
        String idProdotto = req.getParameter("idProdotto");
        resp.setContentType("application/json");
        PrintWriter o = resp.getWriter();
        JSONArray jsonArray = new JSONArray();


        if (action != null && idVariante != null){
            if (action.equals("showFirst")){ //visualizza inzialmente la variante di costo minore, associando al gusto della variante di costo minore tutti i pesi associati
                VarianteDAO varianteDAO = new VarianteDAO();
                Variante v = varianteDAO.doRetrieveVarianteByIdVariante(Integer.parseInt(idVariante));

                if (v != null){
                    ProdottoDAO prodottoDAO = new ProdottoDAO();
                    Prodotto p = prodottoDAO.doRetrieveById(v.getIdProdotto());
                    if (p != null){
                        List<Variante> variantiAll = varianteDAO.doRetrieveVariantiByIdProdotto(p.getIdProdotto());

                        String gusto = v.getGusto(); //gusto della variante attuale di costo minimo

                        List<Integer> pesi = new ArrayList<>();

                        //prendo tutte le varianti del prodotto che sto aggiungendo al carrello che hanno il gusto della variante di costo inferiore
                        List<Variante> variantiByGusto = varianteDAO.doRetrieveVariantByCriteria(p.getIdProdotto(), "flavour", gusto);

                        for (Variante x: variantiByGusto){
                          pesi.add(x.getPesoConfezione());
                        }

                        JSONObject jsonObject1 = new JSONObject();
                        jsonObject1.put("nomeProdotto", p.getNome());
                        jsonObject1.put("idProdotto", p.getIdProdotto());
                        jsonObject1.put("cheapestFlavour", v.getGusto());
                        jsonObject1.put("cheapestWeight", v.getPesoConfezione());
                        jsonObject1.put("cheapestPrice", v.getPrezzo());
                        jsonObject1.put("cheapestDiscount", v.getSconto());
                        jsonArray.add(jsonObject1);

                        List<Integer> alreadySentWeights = new ArrayList<>();
                        for (Integer z: pesi){
                            if (z != v.getPesoConfezione() && !alreadySentWeights.contains(z)) {
                                alreadySentWeights.add(z);
                                JSONObject jsonObject2 = new JSONObject();
                                jsonObject2.put("cheapestWeightOptions", z);
                                jsonArray.add(jsonObject2);
                            }
                        }

                        List<String> alreadySentTastes = new ArrayList<>();
                        for (Variante y: variantiAll){
                            if (y.getIdVariante() != v.getIdVariante() && !y.getGusto().equals(v.getGusto()) && !alreadySentTastes.contains(y.getGusto())) {
                                alreadySentTastes.add(y.getGusto());
                                JSONObject jsonObject3 = new JSONObject();
                                jsonObject3.put("gusto", y.getGusto());
                                jsonArray.add(jsonObject3);
                            }
                        }

                        o.println(jsonArray);
                        o.flush();
                    }
                }
            }
        }
        //quando avviene un cambiamento nella select del gusto, restituisce tutti i pesi al gusto associati
        if (action != null && idProdotto != null && action.equals("updateOptions") && req.getParameter("flavour") != null){
            String flavour = req.getParameter("flavour");
            ProdottoDAO prodottoDAO = new ProdottoDAO();
            VarianteDAO varianteDAO = new VarianteDAO();
            Prodotto p = prodottoDAO.doRetrieveById(idProdotto);

            List<Integer> pesi = new ArrayList<>();
            List<Variante> varianti = varianteDAO.doRetrieveVariantByCriteria(p.getIdProdotto(), "flavour", flavour);
            for (Variante v: varianti)
                pesi.add(v.getPesoConfezione());

            pesi.sort(Comparator.comparingInt(o2 -> o2));


            for (Integer x: pesi) {
                System.out.println(x);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("peso", x);
                jsonArray.add(jsonObject);
            }

            o.println(jsonArray);
            o.flush();
        }
        //quando seleziono effettivamente un altro peso restituisce il prezzo della combinazione data dal gusto + il peso
        if (action != null && idProdotto != null && req.getParameter("flavour") != null && req.getParameter("weight") != null && action.equals("updatePrice")) {
            String flavour = req.getParameter("flavour");
            int weight = Integer.parseInt(req.getParameter("weight"));
            ProdottoDAO prodottoDAO = new ProdottoDAO();
            Prodotto x = prodottoDAO.doRetrieveById(idProdotto);

            if (x != null) {
                VarianteDAO varianteDAO = new VarianteDAO();
                Variante v = varianteDAO.doRetrieveVariantByFlavourAndWeight(idProdotto, flavour, weight).get(0);

                /*System.out.println(v.getIdVariante());*/
                JSONObject jsonResponse = new JSONObject();
                jsonResponse.put("prezzo", v.getPrezzo());
                jsonResponse.put("sconto", v.getSconto());

                o.println(jsonResponse);
                o.flush();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
