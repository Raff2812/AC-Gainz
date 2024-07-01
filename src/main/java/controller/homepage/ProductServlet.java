package controller.homepage;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Prodotto;
import model.ProdottoDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(value = "/Product")
public class ProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String primarykey=req.getParameter("primarykey");
        String category=req.getParameter("category");
        if(primarykey!=null)
        {
            try {
                ProdottoDAO prodottoDAO=new ProdottoDAO();
                Prodotto prodotto=prodottoDAO.doRetrieveById(primarykey);
                if(prodotto!=null)
                {
                    ProdottoDAO suggeritiDAO=new ProdottoDAO();
                    List<Prodotto> suggeriti=suggeritiDAO.doRetrieveByCriteria("categoria",category);
                    req.setAttribute("suggeriti",suggeriti);
                    req.setAttribute("prodotto",prodotto);
                    req.getRequestDispatcher("Product.jsp").forward(req,resp);
                }
                else
                {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
                }

            }catch (Exception e)
            {
                throw new ServletException(e);
            }
        }
        else
        {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Invalid Product Id");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
