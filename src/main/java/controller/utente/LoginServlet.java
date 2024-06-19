package controller.utente;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Carrello;
import model.CarrelloDAO;
import model.Utente;
import model.UtenteDAO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(value = "/login")
public class LoginServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        // Prendo i parametri dal form
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UtenteDAO utenteDAO = new UtenteDAO();

        String emailPattern = "^[\\w.%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,8}$";
        Pattern emailRegex = Pattern.compile(emailPattern);
        Matcher emailMatcher = emailRegex.matcher(email);

        if (!emailMatcher.matches()) {

            response.sendRedirect("Login.jsp?err=patternMail");
            return;
        }

        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\w\\s]).{8,}$";
        Pattern passwordRegex = Pattern.compile(passwordPattern);
        Matcher passwordMatcher = passwordRegex.matcher(password);

        if (!passwordMatcher.matches()) {

            response.sendRedirect("Login.jsp?err=patternPassword");
            return;
        }

        // Se nel mio db non c'è alcuna corrispondenza con l'email passata dal form, ricarico la pagina del login
        // e faccio uscire un messaggio di errore
        if (utenteDAO.doRetrieveByEmail(email) == null) {
            response.sendRedirect("Login.jsp?err=UtenteNonRegistrato");
            return;
        }

        Utente x = null;
        try {
            x = utenteDAO.doRetrieveByEmailAndPassword(email, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // In questo caso, se arrivo qui significa che l'email è presente nel db ma ciò che è sbagliata è la password
        // per cui faccio ciò che ho fatto prima, con un messaggio di errore evidentemente diverso
        if (x == null) {
            response.sendRedirect("Login.jsp?err=PasswordSbagliata");
            return;
        }

        // Se arrivo qui, ho messo i dati giusti nel form

        // Mi prendo la sessione
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(1800); // Set session timeout

        // Inserisco nella sessione l'oggetto contenente l'utente vero e proprio
        session.setAttribute("Utente", x);


        CarrelloDAO carrelloDAO = new CarrelloDAO();
        List<Carrello> cart = carrelloDAO.doRetrieveCartItemsByUser(x.getEmail());
        if (!cart.isEmpty()) {
            session.setAttribute("cart", cart);
        }
        // Redirect to the index page
        response.sendRedirect("index.jsp");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
