package controller.homepage;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Prodotto;
import model.ProdottoDAO;
import model.Variante;
import model.VarianteDAO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
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
        VarianteDAO varianteDAO = new VarianteDAO();

        if (name != null && !name.isEmpty()) {
            session.removeAttribute("categoria");  //per applicare i filtri
            ProdottoDAO prodottoDAO = new ProdottoDAO();
            try {
                products = prodottoDAO.filterProducts("", "", "", "", name);
                session.setAttribute("searchBarName", name);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
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
