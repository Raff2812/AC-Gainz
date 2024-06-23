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
public class ShowTasteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Prodotto> filteredProducts = (List<Prodotto>) req.getSession().getAttribute("products");
        List<String> tastes = new ArrayList<>();

        for(Prodotto p: filteredProducts){
            if(!tastes.contains(p.getGusto()))
                tastes.add(p.getGusto());
        }

        JSONArray jsonArray = new JSONArray();
        for (String taste : tastes){
            jsonArray.add(taste);
        }

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
