package controller.homepage;

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
import java.util.ArrayList;
import java.util.List;

import static controller.Filters.GenericFilterServlet.getJsonObject;

@WebServlet(value = "/searchBar")
@SuppressWarnings("unchecked")
public class SearchBarServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        HttpSession session = req.getSession();

        List<Prodotto> products = new ArrayList<>();

        if (name != null && !name.isEmpty()) {
            ProdottoDAO prodottoDAO = new ProdottoDAO();
            products = prodottoDAO.doRetrieveByName(name);
        } else {
            products = (List<Prodotto>) session.getAttribute("filteredProducts");
        }

        // Save search results in originalProducts and products for further filtering
        session.setAttribute("filteredProducts", products);
        session.setAttribute("products", products);

        addToJson(products, session, req, resp);
    }

    private void addToJson(List<Prodotto> products, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        JSONArray jsonArray = new JSONArray();

        for (Prodotto p: products) {
            JSONObject jsonObject = getJsonObject(p);
            jsonArray.add(jsonObject);
        }

        response.setContentType("application/json");
        PrintWriter o = response.getWriter();
        o.println(jsonArray);
        o.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
