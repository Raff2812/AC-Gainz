package controller.Filters;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Prodotto;
import model.ProdottoDAO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@WebServlet(value = "/genericFilter")
public class GenericFilterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        String nameForm = req.getParameter("nameForm"); //searchBar non ajax
        if (nameForm != null && !nameForm.isBlank()){
           handleFormName(req, resp, session, nameForm);
        }



        String nameFilter = req.getParameter("name"); // SearchBar ajax
        System.out.println(nameFilter + "searched");

        String priceFilter = req.getParameter("price");
        String caloriesFilter = req.getParameter("calories");
        String tasteFilter = req.getParameter("taste");
        String sortingFilter = req.getParameter("sorting");

        System.out.println("Price Filter: " + priceFilter);
        System.out.println("Calories Filter: " + caloriesFilter);
        System.out.println("Taste Filter: " + tasteFilter);
        System.out.println("Sorting Filter: " + sortingFilter);


        synchronized (session) {

            List<Prodotto> originalProducts = (List<Prodotto>) session.getAttribute("originalProducts");
            if (originalProducts == null) originalProducts = new ArrayList<>();


            List<Prodotto> resultProducts = new ArrayList<>(originalProducts);

            if (priceFilter == null && caloriesFilter == null && tasteFilter == null && sortingFilter == null && nameFilter == null) {
                resultProducts = new ArrayList<>(originalProducts);
            } else {
                if (priceFilter != null) {
                    resultProducts = filterByPrice(resultProducts, priceFilter);
                }
                if (caloriesFilter != null) {
                    resultProducts = filterByCalories(resultProducts, caloriesFilter);
                }
                if (tasteFilter != null) {
                    resultProducts = filterByTaste(resultProducts, tasteFilter);
                }
                if (sortingFilter != null) {
                    resultProducts = resultFromSorting(resultProducts, sortingFilter);
                }
                if (nameFilter != null) {
                    resultProducts = filterByName(resultProducts, nameFilter);
                }
            }

            resp.setContentType("application/json");
            PrintWriter o = resp.getWriter();

            JSONArray jsonArray = new JSONArray();

            for (Prodotto p : resultProducts) {
                JSONObject jsonObject = getJsonObject(p);
                jsonArray.add(jsonObject);
            }

            session.setAttribute("products", resultProducts);

            o.println(jsonArray);
            o.flush();
        }
    }

    public static JSONObject getJsonObject(Prodotto p) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", p.getIdProdotto());
        jsonObject.put("nome", p.getNome());
        jsonObject.put("categoria", p.getCategoria());
        jsonObject.put("calorie", p.getCalorie());


        if (p.getSconto() > 0){
            float price = p.getPrezzo() - (p.getPrezzo() * ((float) p.getSconto() / 100));
            jsonObject.put("sconto", p.getSconto());
            jsonObject.put("prezzo", price);
        }else {
            jsonObject.put("prezzo", p.getPrezzo());
        }


        jsonObject.put("gusto", p.getGusto());
        jsonObject.put("immagine", p.getImmagine());
        return jsonObject;
    }


    private void handleFormName(HttpServletRequest request, HttpServletResponse response, HttpSession session, String nameForm) throws ServletException, IOException {
        List<Prodotto> originalProducts = (List<Prodotto>) getServletContext().getAttribute("Products");

        List<Prodotto> resultProducts = new ArrayList<>(originalProducts);

        resultProducts = filterByName(resultProducts, nameForm);

        session.setAttribute("productsByCriteria", resultProducts);

        request.getRequestDispatcher("FilterProducts.jsp").forward(request, response);
        return;
    }

    private List<Prodotto> filterByName(List<Prodotto> prodottos, String value) {
        List<Prodotto> resultProducts = new ArrayList<>();
        ProdottoDAO prodottoDAO = new ProdottoDAO();
        resultProducts = prodottoDAO.doRetrieveByName(value);
        return resultProducts;
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
            products.sort((Prodotto o1, Prodotto o2) -> {
                float price1 = o1.getPrezzo();
                float price2 = o2.getPrezzo();

                if (o1.getSconto() > 0) price1 = price1 - (price1 * o1.getSconto() / 100);
                if (o2.getSconto() > 0) price2 = price2 - (price2 * o2.getSconto() / 100);

                return Float.compare(price1, price2);
            });
        }else if (value.equals("sortDown")) {
            products.sort((Prodotto o1, Prodotto o2) -> {
                float price1 = o1.getPrezzo();
                float price2 = o2.getPrezzo();

                if (o1.getSconto() > 0) price1 = price1 - (price1 * o1.getSconto() / 100);
                if (o2.getSconto() > 0) price2 = price2 - (price2 * o2.getSconto() / 100);

                return Float.compare(price2, price1);
            });
        } else if (value.equals("evidence")) {
            List<Prodotto> evidenceProducts = new ArrayList<>();
            for (Prodotto p : products) {
                if (p.isEvidenza()) {
                    evidenceProducts.add(p);
                }
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
