package controller.Filters;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Prodotto;
import model.ProdottoDAO;
import model.Variante;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/genericFilter")
public class GenericFilterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        String nameForm = req.getParameter("nameForm");
        if (nameForm != null){
            try {
                handleNameForm(nameForm, req, resp, session);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return;
        }



        String category = (String) session.getAttribute("categoria");
        String nameFilter = "";
        if(session.getAttribute("searchBarName") != null) nameFilter = (String) session.getAttribute("searchBarName");
        String weightFilter = req.getParameter("weight");
        String tasteFilter = req.getParameter("taste");
        String sortingFilter = req.getParameter("sorting");

        System.out.println("NameFilter:" + nameFilter);
        System.out.println("weightFilter:" + weightFilter);
        System.out.println("tasteFilter:" + tasteFilter);
        System.out.println("sortingFilter:" + sortingFilter);
        System.out.println("category:" + category);

        List<Prodotto> filteredProducts = new ArrayList<>();
        ProdottoDAO prodottoDAO = new ProdottoDAO();

        if (session.getAttribute("searchBarName") != null) nameFilter = (String) session.getAttribute("searchBarName");

        try {
            filteredProducts = prodottoDAO.filterProducts(category, sortingFilter, weightFilter, tasteFilter, nameFilter);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Aggiornare la lista di prodotti filtrati nella sessione
        session.setAttribute("filteredProducts", filteredProducts);

        // Inviare la risposta JSON al client
        sendJsonResponse(resp, filteredProducts);
    }

    private void handleNameForm(String nameForm, HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException, SQLException {
        List<Prodotto> products = new ArrayList<>();

        ProdottoDAO prodottoDAO = new ProdottoDAO();

        if (nameForm.isBlank()){
            products = prodottoDAO.doRetrieveAll();
            session.removeAttribute("categoria");
        }else {
            products = prodottoDAO.filterProducts("", "", "", "", nameForm);
        }
        request.setAttribute("originalProducts", products);
        session.setAttribute("searchBarName", nameForm);
        session.setAttribute("filteredProducts", products);

        request.getRequestDispatcher("FilterProducts.jsp").forward(request, response);
    }

    // Metodo per inviare la risposta JSON al client
    private void sendJsonResponse(HttpServletResponse resp, List<Prodotto> resultProducts) throws IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        JSONArray jsonArray = new JSONArray();

        for (Prodotto p : resultProducts) {
            JSONObject jsonObject = getJsonObject(p);
            jsonArray.add(jsonObject);
        }

        out.println(jsonArray);
        out.flush();
    }

    public static JSONObject getJsonObject(Prodotto p) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", p.getIdProdotto());
        jsonObject.put("nome", p.getNome());
        jsonObject.put("categoria", p.getCategoria());
        jsonObject.put("calorie", p.getCalorie());
        jsonObject.put("immagine", p.getImmagine());



        Variante variante = p.getVarianti().get(0);

        jsonObject.put("idVariante", variante.getIdVariante());

        if (variante.getSconto() > 0){
            jsonObject.put("sconto", variante.getSconto());
        }

        jsonObject.put("prezzo", variante.getPrezzo());

        jsonObject.put("gusto", variante.getGusto());

        jsonObject.put("peso", variante.getPesoConfezione());



        return jsonObject;
    }




    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
