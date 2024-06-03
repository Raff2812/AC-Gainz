package controller.Filters;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Prodotto;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(value = "/genericFilter")
public class GenericFilterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String filter = req.getParameter("filter");
        String value = req.getParameter("value");

        System.out.println("Filter: " + filter);
        System.out.println("Value: " + value);

        List<Prodotto> products = (List<Prodotto>) req.getSession().getAttribute("products");
        List<Prodotto> originalProducts = (List<Prodotto>) req.getSession().getAttribute("originalProducts");

        List<Prodotto> resultProducts = new ArrayList<>();

        if (value.isEmpty() || value.equals("-")) {
            // Se il valore è vuoto o "-", restituisci l'elenco originale dei prodotti
            resultProducts = new ArrayList<>(originalProducts);
        } else {
            // Altrimenti, applica i filtri
            for (Prodotto p : products) {
                if (filter.equals("price")) {
                    String[] priceRange = value.split("-");
                    int minPrice = Integer.parseInt(priceRange[0]);
                    int maxPrice = Integer.parseInt(priceRange[1]);
                    if (p.getPrezzo() >= minPrice && p.getPrezzo() <= maxPrice) {
                        resultProducts.add(p);
                    }
                } else if (filter.equals("taste") && p.getGusto().equals(value)) {
                    resultProducts.add(p);
                } else if (filter.equals("calories")) {
                    String[] caloriesRange = value.split("-");
                    int minCalories = Integer.parseInt(caloriesRange[0]);
                    int maxCalories = Integer.parseInt(caloriesRange[1]);
                    if (p.getCalorie() >= minCalories && p.getCalorie() <= maxCalories) {
                        resultProducts.add(p);
                    }
                }
            }
        }

        // Converti i prodotti filtrati in JSON
        resp.setContentType("application/json");
        PrintWriter o = resp.getWriter();
        JSONArray jsonArray = new JSONArray();
        for (Prodotto p : resultProducts) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", p.getIdProdotto());
            jsonObject.put("nome", p.getNome());
            jsonObject.put("categoria", p.getCategoria());
            jsonObject.put("calorie", p.getCalorie());
            jsonObject.put("prezzo", p.getPrezzo());
            jsonObject.put("gusto", p.getGusto());
            jsonArray.add(jsonObject);
        }

        // Aggiorna la lista dei prodotti nella sessione
        req.getSession().setAttribute("products", resultProducts);

        o.println(jsonArray);
        o.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
