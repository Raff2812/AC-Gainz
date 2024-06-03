<%@ page import="model.Utente" %>
<%@ page import="java.util.Date" %><%--
  Created by IntelliJ IDEA.
  User: luigiauriemma
  Date: 15/05/24
  Time: 16:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="JS/AreaUtente.js"></script>
    <link rel="stylesheet" href="CSS/AreaUtenteCSS.css">
    <title>Area utente</title>
</head>
<body>
<%@include file="Header.jsp"%>
<div class="contenitore">
    <div class="tab">
        <button class="tablinks" onclick="opentab(event, 'areautente')">Area Utente</button>
        <button class="tablinks" onclick="opentab(event, 'areamodifiche')">Area Modifiche</button>
        <button class="tablinks" onclick="opentab(event, 'areaordini')">Area Ordini</button>
    </div>

    <div id="areautente" class="tabcontent">
        <h3>Dettagli Utente</h3>
        <p>
                <%
                String email = x.getEmail();
                String password = x.getPassword();
                String indirizzo = x.getIndirizzo();
                String nome= x.getNome();
                String cognome= x.getCognome();
                String codiceFiscale= x.getCodiceFiscale();
                Date dataNascita= x.getDataNascita();
                String telefono= x.getTelefono();
            %>
        <p id="areautente-tags">Email:</p><%=email%>
        <br><br><p id="areautente-tags">Password:</p><%=password%>
        <br><br><p id="areautente-tags">Indirizzo:</p><%=indirizzo%>
        <br><br><p id="areautente-tags">Nome:</p><%=nome%>
        <br><br><p id="areautente-tags">Cognome:</p><%=cognome%>
        <br><br><p id="areautente-tags">Codice fiscale:</p><%=codiceFiscale%>
        <br><br><p id="areautente-tags">Data di Nascita:</p><%=dataNascita%>
        <br><br><p id="areautente-tags">Telefono:</p><%=telefono%>
        </p>
    </div>

    <div id="areamodifiche" class="tabcontent">
        <h3>Area Modifiche</h3>
        <br><br>
        <button onclick="displaydivpass()">Modifica Password</button>
        <div id="passdiv" style="display: none">
            <form id="change-password-form">
                <label for="current-password">Password Attuale</label>
                <input type="password" id="current-password" name="current-password" required>

                <label for="new-password">Nuova Password</label>
                <input type="password" id="new-password" name="new-password" required>

                <label for="confirm-password">Conferma Nuova Password</label>
                <input type="password" id="confirm-password" name="confirm-password" required>

                <input type="submit" class="submit" value="Modifica Password">
                <div id="error-message-pass" class="error"></div>
            </form>
        </div>

        <br><br>
        <button onclick="displaydivind()">Modifica Indirizzo</button>
        <div id="inddiv" style="display: none">
            <form id="change-address-form">
                <label for="street">Via</label>
                <input type="text" id="street" name="street" required>

                <input type="submit" class="submit" value="Modifica Indirizzo">
                <div id="error-message-ind" class="error"></div>
            </form>
        </div>

        <br><br>
        <button onclick="displaydivnumtel()">Modifica Numero di Telefono</button>
        <div id="numteldiv" style="display: none">
            <form id="change-phone-form">
                <label for="new-phone">Nuovo Numero di Telefono</label>
                <input type="text" id="new-phone" name="new-phone" required>

                <input type="submit" class="submit" value="Modifica Numero di Telefono">
                <div id="error-message-numtel" class="error"></div>
            </form>
        </div>

        <br><br>
        <button onclick="displaydivcodfis()">Modifica Codice Fiscale</button>
        <div id="codfisdiv" style="display: none">
            <form id="change-codice-fiscale-form">
                <label for="new-codice-fiscale">Nuovo Codice Fiscale</label>
                <input type="text" id="new-codice-fiscale" name="new-codice-fiscale" required>

                <input type="submit" class="submit" value="Modifica Codice Fiscale">
                <div id="error-message-codfis" class="error"></div>
            </form>
        </div>

        <br><br>
        <button onclick="displaydivddn()">Modifica Data di Nascita</button>
        <div id="ddndiv" style="display: none">
            <form id="change-birthdate-form">
                <label for="new-birthdate">Nuova Data di Nascita</label>
                <input type="date" id="new-birthdate" name="new-birthdate" required>

                <input type="submit" class="submit" value="Modifica Data di Nascita">
                <div id="error-message-ddn" class="error"></div>
            </form>
        </div>

        <br><br>
        <button onclick="displaydivnome()">Modifica Nome</button>
        <div id="nomediv" style="display: none">
            <form id="change-name-form">
                <label for="new-name">Nuovo Nome</label>
                <input type="text" id="new-name" name="new-name" required>

                <input type="submit" class="submit" value="Modifica Nome">
                <div id="error-message-nome" class="error"></div>
            </form>
        </div>

        <br><br>
        <button onclick="displaydivcognome()">Modifica Cognome</button>
        <div id="cognomediv" style="display: none">
            <form id="change-surname-form">
                <label for="new-surname">Nuovo Cognome</label>
                <input type="text" id="new-surname" name="new-surname" required>

                <input type="submit" class="submit" value="Modifica Cognome">
                <div id="error-message-cognome" class="error"></div>
            </form>
        </div>
    </div>

    <div id="areaordini" class="tabcontent">
        <h3>Dettagli Ordini</h3>
        <p></p>
    </div>
</div>

<div class="logOut">
    <a href="logOut">LogOut</a>
</div>

<%@ include file="Footer.jsp"%>
</body>
</html>
