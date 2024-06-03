package controller.Filters;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Prodotto;
import model.ProdottoDAO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(value = "/filter")
public class FilterServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String filter = req.getParameter("category");
        ProdottoDAO prodottoDAO = new ProdottoDAO();

        List<Prodotto> productsByCriteria = new ArrayList<>();

        if(filter.equals("tutto")){
            productsByCriteria = prodottoDAO.doRetrieveAll();
        } else
        {productsByCriteria = prodottoDAO.doRetrieveByCriteria("categoria", filter);}

        req.setAttribute("productsByCriteria", productsByCriteria);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("FilterProducts.jsp");
        requestDispatcher.forward(req, resp);

        /*JSONArray jsonArray = new JSONArray();

        for (Prodotto p : productsByCriteria){
            System.out.println(p.getNome());
        }


        for(Prodotto p: productsByCriteria){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", p.getIdProdotto());
            jsonObject.put("nome", p.getNome());
            jsonObject.put("prezzo", p.getPrezzo());
            jsonArray.add(jsonObject);
        }

        System.out.println("JSON Array: " + jsonArray);

        resp.setContentType("application/json");
        PrintWriter o = resp.getWriter();
        o.println(jsonArray);
        o.flush();*/
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }}
