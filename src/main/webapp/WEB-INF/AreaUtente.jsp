<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="model.*" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="JS/AreaUtente.js"></script>
    <link rel="stylesheet" href="CSS/AreaUtenteCSS.css">
    <title>Area utente</title>
    <link rel="icon" type="image/x-icon" href="Immagini/favicon.ico">
 <%--   <%
        boolean isLoggedArea = (session.getAttribute("Utente") != null);
    %>
    <script>
        const isLoggedNow = <%=isLoggedArea%>;
        if (isLoggedNow === false)
            window.location.href = "index.jsp";
    </script>
    --%>
</head>
<body>

<%@ include file="Header.jsp" %>

<script src="JS/togglePassword.js"></script>

<div class="contenitore">
    <div class="tab">
        <button class="tablinks" onclick="opentab(event, 'areautente')">Area Utente</button>
        <button class="tablinks" onclick="opentab(event, 'areamodifiche')">Area Modifiche</button>
        <button class="tablinks" onclick="opentab(event, 'areaordini')">Area Ordini</button>
    </div>


    <div id="areautente" class="tabcontent">
        <h3>Dettagli Utente</h3>
        <%
            if (utente != null) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                String formattedDate = simpleDateFormat.format(utente.getDataNascita());
                boolean powers = utente.getPoteri();
                if (powers) {
        %>
        <a href="admin" id="areaAdmin">Area Admin</a>
        <%
            }
        %>
        <div class="logOut">
            <a href="logOut">LogOut</a>
        </div>

        <p id="areautente-tags">Email:</p><%= utente.getEmail() %>
        <br><br><p id="areautente-tags">Password:</p><%= utente.getPassword() %>
        <br><br><p id="areautente-tags">Indirizzo:</p><%= utente.getIndirizzo() %>
        <br><br><p id="areautente-tags">Nome:</p><%= utente.getNome() %>
        <br><br><p id="areautente-tags">Cognome:</p><%= utente.getCognome() %>
        <br><br><p id="areautente-tags">Codice fiscale:</p><%= utente.getCodiceFiscale() %>
        <br><br><p id="areautente-tags">Data di Nascita:</p><%= formattedDate %>
        <br><br><p id="areautente-tags">Telefono:</p><%= utente.getTelefono() %>
        <%
            }
        %>
    </div>

    <div id="areamodifiche" class="tabcontent">
        <h3>Area Modifiche</h3>
        <br><br>
        <button onclick="displayAll('passdiv')">Modifica Password</button>
        <div id="passdiv" class="form-container" style="display: none">
            <div id="error-message-pass" class="error"></div>
            <div id="success-message-pass" class="success"></div>
            <form id="change-password-form" action="editServlet" method="post">
                <input type="hidden" name="field" value="password">
                <label for="current-password">Password Attuale</label>
                <input type="password" id="current-password" name="current-password" required>
                <img src="Immagini/hide.png" alt="hide png" onclick="togglePassword('current-password')">
                <label for="new-password">Nuova Password</label>
                <input type="password" id="new-password" name="new-password" required pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[^\w\s]).{8,}$">
                <img src="Immagini/hide.png" alt="hide png" onclick="togglePassword('new-password')">
                <label for="confirm-password">Conferma Nuova Password</label>
                <input type="password" id="confirm-password" name="confirm-password" required pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[^\w\s]).{8,}$">
                <img src="Immagini/hide.png" alt="hide png" onclick="togglePassword('confirm-password')">
                <input type="submit" class="submit" value="Modifica Password">
            </form>
        </div>

        <br><br>
        <button onclick="displayAll('inddiv')">Modifica Indirizzo</button>
        <div id="inddiv" class="form-container" style="display: none">
            <div id="error-message-ind" class="error"></div>
            <div id="success-message-ind" class="success"></div>
            <form id="change-address-form" action="editServlet" method="post">
                <input type="hidden" name="field" value="address">
                <label for="street">Via</label>
                <input type="text" id="street" name="street" required>
                <input type="submit" class="submit" value="Modifica Indirizzo">
            </form>
        </div>

        <br><br>
        <button onclick="displayAll('numteldiv')">Modifica Numero di Telefono</button>
        <div id="numteldiv" class="form-container" style="display: none">
            <div id="error-message-numtel" class="error"></div>
            <div id="success-message-numtel" class="success"></div>
            <form id="change-phone-form" action="editServlet" method="post">
                <input type="hidden" name="field" value="phone">
                <label for="new-phone">Nuovo Numero di Telefono</label>
                <input type="text" id="new-phone" name="new-phone" required pattern="^3[0-9]{8,9}$">
                <input type="submit" class="submit" value="Modifica Numero di Telefono">
            </form>
        </div>

        <br><br>
        <button onclick="displayAll('codfisdiv')">Modifica Codice Fiscale</button>
        <div id="codfisdiv" class="form-container" style="display: none">
            <div id="error-message-codfis" class="error"></div>
            <div id="success-message-codfis" class="success"></div>
            <form id="change-codice-fiscale-form" action="editServlet" method="post">
                <input type="hidden" name="field" value="codice-fiscale">
                <label for="new-codice-fiscale">Nuovo Codice Fiscale</label>
                <input type="text" id="new-codice-fiscale" name="new-codice-fiscale" required pattern="^[A-Z]{6}\\d{2}[A-Z]\\d{2}[A-Z]\\d{3}[A-Z]$">
                <input type="submit" class="submit" value="Modifica Codice Fiscale">
            </form>
        </div>

        <br><br>
        <button onclick="displayAll('ddndiv')">Modifica Data di Nascita</button>
        <div id="ddndiv" class="form-container" style="display: none">
            <div id="error-message-ddn" class="error"></div>
            <div id="success-message-ddn" class="success"></div>
            <form id="change-birthdate-form" action="editServlet" method="post">
                <input type="hidden" name="field" value="data-di-nascita">
                <label for="new-birthdate">Nuova Data di Nascita</label>
                <input type="date" id="new-birthdate" name="new-birthdate" required min="1900-01-01">
                <input type="submit" class="submit" value="Modifica Data di Nascita">
            </form>
        </div>

        <br><br>
        <button onclick="displayAll('nomediv')">Modifica Nome</button>
        <div id="nomediv" class="form-container" style="display: none">
            <div id="error-message-nome" class="error"></div>
            <div id="success-message-nome" class="success"></div>
            <form id="change-name-form" action="editServlet" method="post">
                <input type="hidden" name="field" value="nome">
                <label for="new-name">Nuovo Nome</label>
                <input type="text" id="new-name" name="new-name" required>
                <input type="submit" class="submit" value="Modifica Nome">
            </form>
        </div>

        <br><br>
        <button onclick="displayAll('cognomediv')">Modifica Cognome</button>
        <div id="cognomediv" class="form-container" style="display: none">
            <div id="error-message-cognome" class="error"></div>
            <div id="success-message-cognome" class="success"></div>
            <form id="change-surname-form" action="editServlet" method="post">
                <input type="hidden" name="field" value="cognome">
                <label for="new-surname">Nuovo Cognome</label>
                <input type="text" id="new-surname" name="new-surname" required>
                <input type="submit" class="submit" value="Modifica Cognome">
            </form>
        </div>
    </div>

    <div id="areaordini" class="tabcontent">
        <h3>Dettagli Ordini</h3>
        <%
            if (utente != null) {
                List<Ordine> ordini = (List<Ordine>) request.getAttribute("ordini");
                if (!ordini.isEmpty()) {%>
        <table>
            <tr>
                <th>Id Ordine</th>
                <th>Data Ordine</th>
                <th>Stato</th>
                <th>Totale</th>
            </tr>
        <%for (Ordine order : ordini) {%>
            <tr>
                <td><%= order.getIdOrdine()%></td> <td><%= order.getDataOrdine()%></td> <td><%= order.getStato()%></td> <td><%=order.getTotale()%>€</td>
            </tr>
        <%}%>
        </table>
        <%}
            }%>
    </div>
</div>

<%
    String errorOld = (String) request.getAttribute("errorOld");
    String errorMatching = (String) request.getAttribute("errorMatching");
    String successPsw = (String) request.getAttribute("successPsw");
    String successAdd = (String) request.getAttribute("successAdd");
    String successTel = (String) request.getAttribute("successTel");
    String successCf = (String) request.getAttribute("successCf");
    String successDdn = (String) request.getAttribute("successDdn");
    String successNa = (String) request.getAttribute("successNa");
    String successSur = (String) request.getAttribute("successSur");
    String errorMessage = (String) request.getAttribute("error");

    boolean errorOLD = errorOld != null;
    boolean errorMATCHING = errorMatching != null;
    boolean success = successPsw != null || successAdd != null || successTel != null || successCf != null || successDdn != null || successNa != null || successSur != null;
    boolean error = errorMessage != null;
%>

<script>
    const displayMessage = (elementId, message, type) => {
        const element = document.getElementById(elementId);
        element.style.display = "block";
        element.innerHTML = message;
        element.className = type;
    };

    <% if (errorOLD) { %>
    displayMessage("error-message-pass", "Password sbagliata", "error");
    <% } else if (errorMATCHING) { %>
    displayMessage("error-message-pass", "Le due password nuove non corrispondono", "error");
    <% } else if (error) { %>
    displayMessage("error-message-pass", "<%= errorMessage %>", "error");
    <% } %>

    <% if (successPsw != null) { %>
    displayMessage("success-message-pass", "<%= successPsw %>", "success");
    <% } else if (successAdd != null) { %>
    displayMessage("success-message-ind", "<%= successAdd %>", "success");
    <% } else if (successTel != null) { %>
    displayMessage("success-message-numtel", "<%= successTel %>", "success");
    <% } else if (successCf != null) { %>
    displayMessage("success-message-codfis", "<%= successCf %>", "success");
    <% } else if (successDdn != null) { %>
    displayMessage("success-message-ddn", "<%= successDdn %>", "success");
    <% } else if (successNa != null) { %>
    displayMessage("success-message-nome", "<%= successNa %>", "success");
    <% } else if (successSur != null) { %>
    displayMessage("success-message-cognome", "<%= successSur %>", "success");
    <% } %>
</script>

<%@ include file="Footer.jsp" %>
</body>
</html>
