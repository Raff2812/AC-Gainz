package controller.homepage;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(value = "/cartServlet")
public class CarrelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        HttpSession session = req.getSession();

        resp.setContentType("application/json");
        PrintWriter o = resp.getWriter();

        JSONArray jsonArray = new JSONArray();
        if (action.equals("add")) {
            String id = req.getParameter("id");
            String nome = req.getParameter("nome");
            String categoria = req.getParameter("categoria");
            float prezzo = Float.parseFloat(req.getParameter("prezzo"));
            String gusto = req.getParameter("gusto");

            System.out.println(id + " " + nome + " " + categoria + " " + prezzo + " " +gusto);

            List<Carrello> cartItems = (List<Carrello>) session.getAttribute("cart");

            Utente logged = (Utente) session.getAttribute("Utente");
            if(cartItems == null){
                cartItems = new ArrayList<>();
            }

                boolean b = false;
                for (Carrello c: cartItems){
                    if(c.getIdProdotto().equals(id)){
                        c.setQuantita(c.getQuantita() + 1);
                        c.setPrezzo(c.getPrezzo() + prezzo);
                        b = true;
                    }
                }

                if(!b) cartItems.add(new Carrello("user@gmail.com",  id, 1, prezzo));



                for(Carrello c: cartItems){
                    System.out.println(c.getIdProdotto());
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", c.getIdProdotto());
                    jsonObject.put("quantity", c.getQuantita());
                    jsonObject.put("prezzo", c.getPrezzo());
                    jsonArray.add(jsonObject);
                }

                session.setAttribute("cart", cartItems);

                o.println(jsonArray);
                o.flush();
            }
        if (action.equals("remove")){
            String idToRemove = req.getParameter("id");

            List<Carrello> cartItems = (List<Carrello>) session.getAttribute("cart");

            for(Carrello c: cartItems){
                if (c.getIdProdotto().equals(idToRemove)){
                    cartItems.remove(c);
                    break;
                }
            }

            for(Carrello c: cartItems){
                System.out.println(c.getIdProdotto());
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", c.getIdProdotto());
                jsonObject.put("quantity", c.getQuantita());
                jsonObject.put("prezzo", c.getPrezzo());
                jsonArray.add(jsonObject);
            }

            session.setAttribute("cart", cartItems);

            o.println(jsonArray);
            o.flush();
        }
        if(action.equals("show")){
            System.out.println("Showing");
            List<Carrello> cart = (List<Carrello>) session.getAttribute("cart");

            if(!cart.isEmpty()){
                System.out.println("Ho roba");
                for (Carrello c: cart){
                    System.out.println(c.getIdProdotto() + " in the cart");
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", c.getIdProdotto());
                    jsonObject.put("quantity", c.getQuantita());
                    jsonObject.put("prezzo", c.getPrezzo());
                    jsonArray.add(jsonObject);
                }

                o.println(jsonArray);
                o.flush();
            }
        }
    }



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
