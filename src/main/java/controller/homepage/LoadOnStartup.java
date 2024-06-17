package controller.homepage;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import model.Carrello;
import model.Prodotto;
import model.ProdottoDAO;


import java.util.Calendar;
import java.util.List;

@WebServlet(value = "/loadOnStartUp", loadOnStartup = 0)
public class LoadOnStartup extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {

        super.init(config);


        ProdottoDAO dao = new ProdottoDAO();
        List<Prodotto> prodottoList = dao.doRetrieveAll();
        getServletContext().setAttribute("Products", prodottoList);

    }
}
