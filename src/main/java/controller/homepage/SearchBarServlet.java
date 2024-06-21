package controller.homepage;

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
import java.util.ArrayList;
import java.util.List;

@WebServlet(value = "/searchBar")
public class SearchBarServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        HttpSession session = req.getSession();


        if (name != null) {
            if (name.isEmpty()) {
                List<Prodotto> originalProducts = (List<Prodotto>) session.getAttribute("originalProducts");
                addToJson(originalProducts, session, req, resp);
            } else {
                ProdottoDAO prodottoDAO = new ProdottoDAO();
                List<Prodotto> products = new ArrayList<>();
                products = prodottoDAO.doRetrieveByName(name);
                addToJson(products, session, req, resp);
            }
        }


    }


    private void addToJson(List<Prodotto> products, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        JSONArray jsonArray = new JSONArray();

        for (Prodotto p: products){
            System.out.println(p.getIdProdotto());
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

        session.setAttribute("products", products);

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
