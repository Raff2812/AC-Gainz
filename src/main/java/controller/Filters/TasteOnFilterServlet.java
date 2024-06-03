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

@WebServlet(value = "/filterTaste")
public class TasteOnFilterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String taste = req.getParameter("taste");
        List<Prodotto> products = (List<Prodotto>) req.getSession().getAttribute("products");
        resp.setContentType("application/json");
        PrintWriter o = resp.getWriter();

        if(taste.equals("")){
            JSONArray jsonArray = new JSONArray();
            for (Prodotto p: products){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", p.getIdProdotto());
                jsonObject.put("nome", p.getNome());
                jsonObject.put("gusto", p.getGusto());
                jsonObject.put("prezzo", p.getPrezzo());
                jsonObject.put("calorie", p.getCalorie());
                jsonArray.add(jsonObject);
            }

            o.println(jsonArray);

            o.flush();
        }
            else{
                List<Prodotto> productsFiltered = new ArrayList<>();

                for(Prodotto p: products){
                    if(p.getGusto().equals(taste))
                        productsFiltered.add(p);
                }

                JSONArray jsonArray = new JSONArray();
                for(Prodotto p: productsFiltered){
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", p.getIdProdotto());
                    jsonObject.put("nome", p.getNome());
                    jsonObject.put("gusto", p.getGusto());
                    jsonObject.put("prezzo", p.getPrezzo());
                    jsonObject.put("calorie", p.getCalorie());
                    jsonArray.add(jsonObject);
                }

                o.println(jsonArray);

                o.flush();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
