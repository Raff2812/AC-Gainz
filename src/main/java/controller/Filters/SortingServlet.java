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

@WebServlet(value = "/sortingBy")
public class SortingServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sorting = req.getParameter("sorting");

        List<Prodotto> products = (List<Prodotto>) req.getSession().getAttribute("products");

        List<Prodotto> copyProducts = new ArrayList<>(products);


        JSONArray jsonArray = new JSONArray();
        resp.setContentType("application/json");
        PrintWriter o = resp.getWriter();

        if(sorting.equals("sortUp")) {
            copyProducts.sort(new Comparator<Prodotto>() {
                @Override
                public int compare(Prodotto o1, Prodotto o2) {
                    return Float.compare(o1.getPrezzo(), o2.getPrezzo());
                }
            });
        }else if(sorting.equals("sortDown")){
            copyProducts.sort(new Comparator<Prodotto>() {
                @Override
                public int compare(Prodotto o1, Prodotto o2) {
                    return Float.compare(o2.getPrezzo(), o1.getPrezzo());
                }
            });
        }

        /*List<Prodotto> resultProducts;
        if (sorting.isEmpty()) {
            resultProducts = (List<Prodotto>) req.getSession().getAttribute("originalProducts");
        } else {
            resultProducts = copyProducts;
        }*/


        List<Prodotto> resultProducts = copyProducts;


        for(Prodotto p: resultProducts){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", p.getIdProdotto());
                jsonObject.put("nome", p.getNome());
                jsonObject.put("categoria", p.getCategoria());
                jsonObject.put("calorie", p.getCalorie());
                jsonObject.put("prezzo", p.getPrezzo());
                jsonObject.put("gusto", p.getGusto());
                jsonArray.add(jsonObject);
        }


            o.println(jsonArray);
            o.flush();

    }



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
