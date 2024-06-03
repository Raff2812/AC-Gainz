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
import java.util.List;

@WebServlet(value = "/resetProducts")
public class resetProductsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Prodotto> originalProducts = (List<Prodotto>) req.getSession().getAttribute("originalProducts");

        req.getSession().removeAttribute("products");
        req.getSession().setAttribute("products", originalProducts);


        JSONArray jsonArray = new JSONArray();

        for(Prodotto p: originalProducts){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", p.getIdProdotto());
            jsonObject.put("nome", p.getNome());
            jsonObject.put("categoria", p.getCategoria());
            jsonObject.put("calorie", p.getCalorie());
            jsonObject.put("prezzo", p.getPrezzo());
            jsonObject.put("gusto", p.getGusto());
            jsonArray.add(jsonObject);
        }

        resp.setContentType("application/json");
        PrintWriter o = resp.getWriter();

        o.println(jsonArray);
        o.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
