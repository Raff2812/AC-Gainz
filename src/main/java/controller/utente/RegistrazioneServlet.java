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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@WebServlet("/register")
public class RegistrazioneServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String email = request.getParameter("email");
        UtenteDAO utenteDAO = new UtenteDAO();
        if(utenteDAO.doRetrieveByEmail(email) != null){
            // Se l'utente esiste già, reindirizza alla pagina di registrazione con un messaggio di errore
            request.setAttribute("errorMessage", "Email già registrata.");
            request.getRequestDispatcher("/WEB-INF/results/Registrazione.jsp").forward(request, response);
            return;
        }

        String password = request.getParameter("password");
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String codiceFiscale = request.getParameter("codiceFiscale");


        String dateString = request.getParameter("dataDiNascita");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dataDiNascita = null;
        try {
            dataDiNascita = formatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String indirizzo = request.getParameter("indirizzo");
        String numCellulare = request.getParameter("numCellulare");
        int poteri = Integer.parseInt(request.getParameter("poteri"));

        Utente x = new Utente(email, password, nome, cognome, codiceFiscale, dataDiNascita, indirizzo, numCellulare, poteri);
        utenteDAO.doSave(x);

        HttpSession session = request.getSession();
        session.setAttribute("Utente", x);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("AreaUtente.jsp");
        requestDispatcher.forward(request, response);

    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
