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
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet("/editServlet")
public class ModificaDatiUtenteServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String field = request.getParameter("field");

        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("Utente");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("areaUtenteServlet");
        UtenteDAO utenteDAO = new UtenteDAO();

        switch (field) {
            case "password" ->
                handlePasswordChange(request, response, utente, requestDispatcher, utenteDAO);
            case "address" ->
                handleAddressChange(request, response, utente, requestDispatcher, utenteDAO);
            case "phone" ->
                handlePhoneChange(request, response, utente, requestDispatcher, utenteDAO);
            case "codice-fiscale" ->
                handleCodiceFiscaleChange(request, response, utente, requestDispatcher, utenteDAO);
            case "data-di-nascita" ->
                handleDataNascitaChange(request, response, utente, requestDispatcher, utenteDAO);
            case "nome" ->
                handleNomeChange(request, response, utente, requestDispatcher, utenteDAO);
            case "cognome" ->
                handleCognomeChange(request, response, utente, requestDispatcher, utenteDAO);
            default ->{
                request.setAttribute("error", "Invalid field parameter");
                requestDispatcher.forward(request, response);
            }
        }
    }

    private void handlePasswordChange(HttpServletRequest request, HttpServletResponse response, Utente utente,
                                      RequestDispatcher requestDispatcher, UtenteDAO utenteDAO)
            throws ServletException, IOException {
        String currentPassword = request.getParameter("current-password");
        String newPassword = request.getParameter("new-password");
        String confirmPassword = request.getParameter("confirm-password");

        if (currentPassword == null || newPassword == null || confirmPassword == null) {
            request.setAttribute("messageType", "error");
            request.setAttribute("message", "Missing parameters");
            request.setAttribute("field", "password");
            requestDispatcher.forward(request, response);
            return;
        }

        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\w\\s]).{8,}$";
        Pattern passwordRegex = Pattern.compile(passwordPattern);

        Matcher newPasswordMatcher = passwordRegex.matcher(newPassword);
        if (!newPasswordMatcher.matches()) {
            request.setAttribute("messageType", "error");
            request.setAttribute("message", "Pattern non rispettato");
            request.setAttribute("field", "password");
            requestDispatcher.forward(request, response);
            return;
        }

        Matcher confirmPasswordMatcher = passwordRegex.matcher(confirmPassword);
        if (!confirmPasswordMatcher.matches()) {
            request.setAttribute("messageType", "error");
            request.setAttribute("message", "Pattern non rispettato");
            request.setAttribute("field", "password");
            requestDispatcher.forward(request, response);
            return;
        }

        String hashedCurrentPassword = sha1(currentPassword);

        System.out.println("Hashed current password: " + hashedCurrentPassword);
        System.out.println("Stored password: " + utente.getPassword());

        if (!hashedCurrentPassword.equals(utente.getPassword())) {
            request.setAttribute("messageType", "error");
            request.setAttribute("message", "Password attuale non corretta");
            request.setAttribute("field", "password");
            requestDispatcher.forward(request, response);
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("messageType", "error");
            request.setAttribute("message", "Le password non corrispondono");
            request.setAttribute("field", "password");
            requestDispatcher.forward(request, response);
            return;
        }


        utente.setPassword(newPassword);
        utente.hashPassword();

        System.out.println(utente.getPassword());

        request.getSession().setAttribute("Utente", utente);

        utenteDAO.doUpdateCustomerGeneric(utente, "password", utente.getPassword());
        System.out.println("Password Cambiata con: " + utente.getPassword());
        request.setAttribute("messageType", "success");
        request.setAttribute("message", "Password modificata con successo");
        request.setAttribute("field", "password");
        requestDispatcher.forward(request, response);
    }

    private void handleAddressChange(HttpServletRequest request, HttpServletResponse response, Utente utente,
                                     RequestDispatcher requestDispatcher, UtenteDAO utenteDAO)
            throws ServletException, IOException {
        String indirizzo = request.getParameter("street");

        if (indirizzo == null) {
            request.setAttribute("messageType", "error");
            request.setAttribute("message", "Missing parameters");
            request.setAttribute("field", "address");
            requestDispatcher.forward(request, response);
            return;
        }

        utente.setIndirizzo(indirizzo);
        request.getSession().setAttribute("Utente", utente);
        utenteDAO.doUpdateCustomerGeneric(utente, "indirizzo", indirizzo);

        request.setAttribute("messageType", "success");
        request.setAttribute("message", "Indirizzo modificato con successo");
        request.setAttribute("field", "address");
        requestDispatcher.forward(request, response);
    }

    private void handlePhoneChange(HttpServletRequest request, HttpServletResponse response, Utente utente,
                                   RequestDispatcher requestDispatcher, UtenteDAO utenteDAO)
            throws ServletException, IOException {
        String telefono = request.getParameter("new-phone");

        if (telefono == null) {
            request.setAttribute("messageType", "error");
            request.setAttribute("message", "Missing parameters");
            request.setAttribute("field", "address");
            requestDispatcher.forward(request, response);
            return;
        }

        String phonePattern = "^3[0-9]{8,9}$";
        Pattern phoneRegex = Pattern.compile(phonePattern);

        Matcher phoneMatcher = phoneRegex.matcher(telefono);
        if (!phoneMatcher.matches()) {
            request.setAttribute("messageType", "error");
            request.setAttribute("message", "Pattern non rispettato");
            request.setAttribute("field", "phone");
            requestDispatcher.forward(request, response);
            return;
        }

        utente.setTelefono(telefono);
        request.getSession().setAttribute("Utente", utente);
        utenteDAO.doUpdateCustomerGeneric(utente, "numero_di_cellulare", telefono);

        request.setAttribute("messageType", "success");
        request.setAttribute("message", "Numero di telefono modificato con successo");
        request.setAttribute("field", "phone");
        requestDispatcher.forward(request, response);
    }

    private void handleCodiceFiscaleChange(HttpServletRequest request, HttpServletResponse response, Utente utente,
                                           RequestDispatcher requestDispatcher, UtenteDAO utenteDAO)
            throws ServletException, IOException {
        String codFiscale = request.getParameter("new-codice-fiscale");

        if (codFiscale == null) {
            request.setAttribute("messageType", "error");
            request.setAttribute("message", "Missing parameters");
            request.setAttribute("field", "codice-fiscale");
            requestDispatcher.forward(request, response);
            return;
        }

        String codFiscalePattern = "^[A-Z]{6}\\d{2}[A-Z]\\d{2}[A-Z]\\d{3}[A-Z]$";
        Pattern codFiscaleRegex = Pattern.compile(codFiscalePattern);
        Matcher codFiscaleMatcher = codFiscaleRegex.matcher(codFiscale);

        if (!codFiscaleMatcher.matches()) {
            request.setAttribute("messageType", "error");
            request.setAttribute("message", "Pattern non rispettato");
            request.setAttribute("field", "codice-fiscale");
            requestDispatcher.forward(request, response);
            return;
        }

        utente.setCodiceFiscale(codFiscale);
        request.getSession().setAttribute("Utente", utente);
        utenteDAO.doUpdateCustomerGeneric(utente, "codice_fiscale", codFiscale);

        request.setAttribute("messageType", "success");
        request.setAttribute("message", "Codice fiscale modificato con successo");
        request.setAttribute("field", "codice-fiscale");
        requestDispatcher.forward(request, response);
    }

    private void handleDataNascitaChange(HttpServletRequest request, HttpServletResponse response, Utente utente,
                                         RequestDispatcher requestDispatcher, UtenteDAO utenteDAO)
            throws ServletException, IOException {
        String ddn = request.getParameter("new-birthdate");

        if (ddn == null) {
            request.setAttribute("messageType", "error");
            request.setAttribute("message", "Missing parameters");
            request.setAttribute("field", "data-di-nascita");
            requestDispatcher.forward(request, response);
            return;
        }

        String[] fieldsDate = ddn.split("-");
        int flag = Integer.parseInt(fieldsDate[0]);

        if (flag < 1900) {
            request.setAttribute("messageType", "error");
            request.setAttribute("message", "Anno minimo non rispettato");
            request.setAttribute("field", "data-di-nascita");
            requestDispatcher.forward(request, response);
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dateformatted = sdf.parse(ddn);
            utente.setDataNascita(dateformatted);
            request.getSession().setAttribute("Utente", utente);
            utenteDAO.doUpdateCustomerGeneric(utente, "data_di_nascita", ddn);

            request.setAttribute("messageType", "success");
            request.setAttribute("message", "Data di nascita modificata con successo");
            request.setAttribute("field", "data-di-nascita");
            requestDispatcher.forward(request, response);
        } catch (ParseException e) {
            throw new ServletException("Errore nel parsing della data di nascita", e);
        }
    }

    private void handleNomeChange(HttpServletRequest request, HttpServletResponse response, Utente utente,
                                  RequestDispatcher requestDispatcher, UtenteDAO utenteDAO)
            throws ServletException, IOException {
        String nome = request.getParameter("new-name");

        if (nome == null) {
            request.setAttribute("messageType", "error");
            request.setAttribute("message", "Missing parameters");
            request.setAttribute("field", "nome");
            requestDispatcher.forward(request, response);
            return;
        }

        utente.setNome(nome);
        request.getSession().setAttribute("Utente", utente);
        utenteDAO.doUpdateCustomerGeneric(utente, "nome", nome);

        request.setAttribute("messageType", "success");
        request.setAttribute("message", "Nome modificato con successo");
        request.setAttribute("field", "nome");
        requestDispatcher.forward(request, response);
    }

    private void handleCognomeChange(HttpServletRequest request, HttpServletResponse response, Utente utente,
                                     RequestDispatcher requestDispatcher, UtenteDAO utenteDAO)
            throws ServletException, IOException {
        String cognome = request.getParameter("new-surname");

        if (cognome == null) {
            request.setAttribute("messageType", "error");
            request.setAttribute("message", "Missing parameters");
            request.setAttribute("field", "cognome");
            requestDispatcher.forward(request, response);
            return;
        }

        utente.setCognome(cognome);
        request.getSession().setAttribute("Utente", utente);
        utenteDAO.doUpdateCustomerGeneric(utente, "cognome", cognome);

        request.setAttribute("messageType", "success");
        request.setAttribute("message", "Cognome modificato con successo");
        request.setAttribute("field", "cognome");
        requestDispatcher.forward(request, response);
    }

    public String sha1(String pass) {
        try {
            var digest =
                    MessageDigest.getInstance("SHA-1");
            digest.reset();
            digest.update(pass.getBytes(StandardCharsets.UTF_8));
            pass = String.format("%040x", new
                    BigInteger(1, digest.digest()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        return pass;
    }
}
