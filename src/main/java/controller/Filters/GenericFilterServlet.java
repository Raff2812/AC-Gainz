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
import java.util.Comparator;
import java.util.List;

@WebServlet(value = "/genericFilter")
public class GenericFilterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String priceFilter = req.getParameter("price");
        String caloriesFilter = req.getParameter("calories");
        String tasteFilter = req.getParameter("taste");
        String sortingFilter = req.getParameter("sorting");

        System.out.println("Price Filter: " + priceFilter);
        System.out.println("Calories Filter: " + caloriesFilter);
        System.out.println("Taste Filter: " + tasteFilter);
        System.out.println("Sorting Filter: " + sortingFilter);

        List<Prodotto> products = (List<Prodotto>) req.getSession().getAttribute("products");
        List<Prodotto> originalProducts = (List<Prodotto>) req.getSession().getAttribute("originalProducts");

        List<Prodotto> resultProducts = new ArrayList<>(originalProducts);

        if (priceFilter == null && caloriesFilter == null && tasteFilter == null && sortingFilter == null) {
            resultProducts = new ArrayList<>(originalProducts); //potenzialmente inutile
        } else {
            if (priceFilter != null) {
                resultProducts = filterByPrice(resultProducts, priceFilter); //aggiorni resultProducts
            }
            if (caloriesFilter != null) {
                resultProducts = filterByCalories(resultProducts, caloriesFilter);  //aggiorni resultProducts
            }
            if (tasteFilter != null) {
                resultProducts = filterByTaste(resultProducts, tasteFilter);  //aggiorni resultProducts
            }
            if (sortingFilter != null) {
                resultProducts = resultFromSorting(resultProducts, sortingFilter);  //aggiorni resultProducts
            }
        }

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
            jsonObject.put("immagine", p.getImmagine());
            jsonArray.add(jsonObject);
        }

        req.getSession().setAttribute("products", resultProducts);

        o.println(jsonArray);
        o.flush();
    }



    private List<Prodotto> filterByPrice(List<Prodotto> products, String value) {
        List<Prodotto> resultProducts = new ArrayList<>();
        String[] priceRange = value.split("-");
        int minPrice = Integer.parseInt(priceRange[0]);
        int maxPrice = Integer.parseInt(priceRange[1]);
        for (Prodotto p : products) {
            if (p.getPrezzo() >= minPrice && p.getPrezzo() <= maxPrice) {
                resultProducts.add(p);
            }
        }
        return resultProducts;
    }

    private List<Prodotto> filterByCalories(List<Prodotto> products, String value) {
        List<Prodotto> resultProducts = new ArrayList<>();
        String[] caloriesRange = value.split("-");
        int minCalories = Integer.parseInt(caloriesRange[0]);
        int maxCalories = Integer.parseInt(caloriesRange[1]);
        for (Prodotto p : products) {
            if (p.getCalorie() >= minCalories && p.getCalorie() <= maxCalories) {
                resultProducts.add(p);
            }
        }
        return resultProducts;
    }

    private List<Prodotto> filterByTaste(List<Prodotto> products, String value) {
        List<Prodotto> resultProducts = new ArrayList<>();
        for (Prodotto p : products) {
            if (p.getGusto().equals(value)) {
                resultProducts.add(p);
            }
        }
        return resultProducts;
    }

    private List<Prodotto> resultFromSorting(List<Prodotto> products, String value) {
        if (value.equals("sortUp")) {
            products.sort(Comparator.comparingDouble(Prodotto::getPrezzo));
        } else if (value.equals("sortDown")) {
            products.sort((o1, o2) -> Float.compare(o2.getPrezzo(), o1.getPrezzo()));
        } else if(value.equals("evidence")){
            List<Prodotto> evidenceProducts = new ArrayList<>();

            for(Prodotto p: products){
                if(p.isEvidenza())
                    evidenceProducts.add(p);
            }

            System.out.println(evidenceProducts);

            return evidenceProducts;
        }
        return products;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
