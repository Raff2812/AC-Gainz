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
                    resultProducts = filterByName(nameFilter);
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


        if (p.getSconto() > 0)
            jsonObject.put("sconto", p.getSconto());

        jsonObject.put("prezzo", p.getPrezzo());



        jsonObject.put("gusto", p.getGusto());
        jsonObject.put("immagine", p.getImmagine());
        return jsonObject;
    }


    private void handleFormName(HttpServletRequest request, HttpServletResponse response, HttpSession session, String nameForm) throws ServletException, IOException {
        List<Prodotto> originalProducts = (List<Prodotto>) getServletContext().getAttribute("Products");

        List<Prodotto> resultProducts = new ArrayList<>(originalProducts);

        resultProducts = filterByName(nameForm);

       /* session.setAttribute("productsByCriteria", resultProducts);*/
        session.setAttribute("originalProducts", resultProducts);

        request.getRequestDispatcher("FilterProducts.jsp").forward(request, response);
        return;
    }

    private List<Prodotto> filterByName(String value) {
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
            float price = p.getPrezzo();
            if (p.getSconto() > 0){
                price = p.getPrezzo() - (p.getPrezzo() *  p.getSconto() / 100);
                /*price = Math.round(price * 100.0f) / 100.0f;*/
            }

            if (price >= minPrice && price <= maxPrice) {
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
        switch (value) {
            case "sortUp" -> products.sort((o1, o2) -> {
                float price1 = o1.getPrezzo();
                float price2 = o2.getPrezzo();

                if (o1.getSconto() > 0)
                    price1 = Math.round(price1 * (1 - ((float) o1.getSconto() /100)) * 100.0f) / 100.0f;

                if (o2.getSconto() > 0)
                    price2 = Math.round(price2 * (1 - ((float) o2.getSconto() /100)) * 100.0f) / 100.0f;


                return Float.compare(price1, price2);
            });
            case "sortDown" -> products.sort((o1, o2) -> {
                float price1 = o1.getPrezzo();
                float price2 = o2.getPrezzo();

                if (o1.getSconto() > 0)
                    price1 = Math.round(price1 * (1 - ((float) o1.getSconto() /100)) * 100.0f) / 100.0f;

                if (o2.getSconto() > 0)
                    price2 = Math.round(price2 * (1 - ((float) o2.getSconto() /100)) * 100.0f) / 100.0f;


                return Float.compare(price2, price1);
            });
            case "evidence" -> {
                List<Prodotto> evidenceProducts = new ArrayList<>();
                for (Prodotto p : products) {
                    if (p.isEvidenza()) {
                        evidenceProducts.add(p);
                    }
                }
                System.out.println("Evidence products: " + evidenceProducts);
                return evidenceProducts;
            }
        }

        return products;
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
