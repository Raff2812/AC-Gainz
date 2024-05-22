package controller.utente;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Utente;
import model.UtenteDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(value = "/login")
public class LoginServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UtenteDAO utenteDAO = new UtenteDAO();

        if(utenteDAO.doRetrieveByEmail(email) == null){
            request.setAttribute("Error", "Utente non registrato");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("Login.jsp");
            requestDispatcher.forward(request, response);
        }


        Utente x = null;
        try {
            x = utenteDAO.doRetrieveByEmailAndPassword(email, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(x == null){
            request.setAttribute("Error", "Password Sbagliata");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("Login.jsp");
            requestDispatcher.forward(request, response);
        }

        HttpSession session = request.getSession();
        session.setAttribute("Utente", x);
        session.setAttribute("Logged", true);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("AreaUtente.jsp");
        requestDispatcher.forward(request, response);

    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
