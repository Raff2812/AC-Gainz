package controller.homepage;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;

import model.*;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
