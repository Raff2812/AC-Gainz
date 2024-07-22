package controller.Filters;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Prodotto;
import model.ProdottoDAO;
import model.VarianteDAO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static controller.Filters.GenericFilterServlet.getJsonObject;


@WebServlet(value = "/searchBar")
public class SearchBarServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Prende dalla request il nome messo nella searchbar
        String name = req.getParameter("name");
        HttpSession session = req.getSession();

        synchronized (session) { //uso di synchronized per race conditions su session tramite ajax
            List<Prodotto> products = new ArrayList<>();
            VarianteDAO varianteDAO = new VarianteDAO();
            //prende la vecchia categoria(Se c'è) dalla sessione
            String categoria = (String) session.getAttribute("categoriaRecovery");
            System.out.println("categoriaSearch:" + categoria);
            ProdottoDAO prodottoDAO = new ProdottoDAO();

            if (name != null && !name.isEmpty()) {
                session.removeAttribute("categoria");  //rimuove la vecchia categoria per applicare i filtri

                try {
                    //prende la lista di tutti i prodotti in base al nome nella searchbar
                    products = prodottoDAO.filterProducts("", "", "", "", name);
                    session.setAttribute("searchBarName", name);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                //altrimenti mostra tutti i prodotti in base alla vecchia categoria gia presente
                session.removeAttribute("searchBarName");
                session.setAttribute("categoria", categoria);
                try {
                    products = prodottoDAO.filterProducts(categoria, "", "", "", "");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }


            // Save search results in originalProducts and products for further filtering
            session.setAttribute("filteredProducts", products);
            session.setAttribute("products", products);

            addToJson(products, session, req, resp);
        }
    }

    //metodo che trasforma i prodotti in JSONObject e aggiunge li aggiunge ad un JSONArray
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
