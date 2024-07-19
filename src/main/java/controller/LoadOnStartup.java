package controller;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import model.Prodotto;
import model.ProdottoDAO;

import java.util.List;

@WebServlet(value = "/loadOnStartUp", loadOnStartup = 1)
public class LoadOnStartup extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        ProdottoDAO prodottoDAO = new ProdottoDAO();

        List<Prodotto> prodottoList = prodottoDAO.doRetrieveAll();


        // Save products in application context
        getServletContext().setAttribute("Products", prodottoList);

    }


}
