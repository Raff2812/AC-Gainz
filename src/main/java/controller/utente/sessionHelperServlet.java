package controller.utente;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Prodotto;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(value = "/startSession")
public class sessionHelperServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        List<Prodotto> products = null;

        if (session.getAttribute("productsByCriteria") != null) {
            products = (List<Prodotto>) session.getAttribute("productsByCriteria");
        } else {
            products = (List<Prodotto>) getServletContext().getAttribute("Products");
        }
        if (products != null) {
            session.setAttribute("originalProducts", products);
            session.setAttribute("products", products);

            JSONArray jsonArray = new JSONArray();
            int i = 0;
            for (Prodotto p: products){
                System.out.println(p.getIdProdotto());
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id" + i, p.getIdProdotto());
                jsonArray.add(jsonObject);
                i++;
            }

            resp.setContentType("application/json");
            PrintWriter o = resp.getWriter();
            o.println(jsonArray);
            o.flush();
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
