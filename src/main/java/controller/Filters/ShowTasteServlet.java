package controller.Filters;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Prodotto;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(value = "/showTastes")
@SuppressWarnings("unchecked")
public class ShowTasteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Prodotto> originalProducts = (List<Prodotto>) req.getSession().getAttribute("filteredProducts");
        List<String> tastes = new ArrayList<>();

        // Raccogliere tutti i gusti unici
        for (Prodotto p : originalProducts) {
            if (!tastes.contains(p.getGusto())) {
                tastes.add(p.getGusto());
            }
        }

        // Ottenere la lista aggiornata dei prodotti filtrati dalla sessione
        List<Prodotto> filteredProducts = (List<Prodotto>) req.getSession().getAttribute("products");
        if (filteredProducts == null) {
            filteredProducts = new ArrayList<>(originalProducts);
        }

        // Contare le occorrenze di ogni gusto nei prodotti filtrati
        List<String> indexTastes = new ArrayList<>();
        for (String taste : tastes) {
            int counter = 0;
            for (Prodotto p : filteredProducts) {
                if (p.getGusto().equals(taste)) {
                    counter++;
                }
            }
            String tasteWithCount = taste + " " + "(" + counter + ")";
            indexTastes.add(tasteWithCount);
        }

        // Creare il JSONArray per la risposta
        JSONArray jsonArray = new JSONArray();
        for (String taste : indexTastes) {
            jsonArray.add(taste);
        }

        // Impostare il tipo di contenuto e inviare la risposta
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.println(jsonArray);
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
