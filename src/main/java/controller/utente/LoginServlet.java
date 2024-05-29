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

        //Prendo i parametri dal form
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UtenteDAO utenteDAO = new UtenteDAO();


        //Se nel mio db non c'è alcuna corrispondenza con l'email passata dal form, ricarico la pagina del login
        // e faccio uscire un messaggio di errore
        if(utenteDAO.doRetrieveByEmail(email) == null){
            request.setAttribute("ErrorEmail", "Utente non registrato");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("Login.jsp");
            requestDispatcher.forward(request, response);
        }


        Utente x = null;
        try {
            x = utenteDAO.doRetrieveByEmailAndPassword(email, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //In questo caso, se arrivo qui significa che l'email è presente nel db ma ciò che è sbagliata è la password
        //per cui faccio ciò che ho fatto prima, con un messaggio di errore evidentemente diverso
        if(x == null){
            HttpSession session = request.getSession();
            session.setAttribute("ErrorPassword", "Password Sbagliata");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("Login.jsp");
            requestDispatcher.forward(request, response);
        }

        //se arrivo qui, ho messo i dati giusti nel form

        //mi prendo la sessione
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(1800);  //non so se serve

        //inserisco nella sessione l'oggetto contenente l'utente vero e proprio
        session.setAttribute("Utente", x);

        //richiamo la servlet HomePageServlet che mi gestirà queste informazioni
        /*RequestDispatcher requestDispatcher = request.getRequestDispatcher("homePage");
        requestDispatcher.forward(request, response);*/

        response.sendRedirect("index.jsp");

    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
