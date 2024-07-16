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
import java.util.Date;



//controllo dei field del form
@WebServlet("/register")
public class RegistrazioneServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
      super.doGet(request, response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String email = request.getParameter("email");
        UtenteDAO utenteDAO = new UtenteDAO();
        if(utenteDAO.doRetrieveByEmail(email) != null){
            // Se l'utente esiste già, reindirizza alla pagina di registrazione con un messaggio di errore
            request.setAttribute("errorMessage", "Email già registrata.");
            request.getRequestDispatcher("/WEB-INF/results/Registrazione.jsp").forward(request, response);
            return;
        }


        //Controllo parametri form da fare --
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

        Utente x = new Utente();
        x.setEmail(email);
        x.setPassword(password);
        x.hashPassword();
        x.setCodiceFiscale(codiceFiscale);
        x.setNome(nome);
        x.setCognome(cognome);
        x.setIndirizzo(indirizzo);
        x.setTelefono(numCellulare);
        x.setDataNascita(dataDiNascita);

        utenteDAO.doSave(x);


        //Stessa cosa fatta su LoginServlet
        HttpSession session = request.getSession();
        session.setAttribute("Utente", x);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp");
        requestDispatcher.forward(request, response);
    }
}
