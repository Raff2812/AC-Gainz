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
    <title>Area utente</title>
    <style>
        .tab {
            float: left;
            border: 1px solid #ccc;
            background-color: #f1f1f1;
            width: fit-content;
        }

        /* Style the buttons inside the tab */
        .tab button {
            display: block;
            background-color: inherit;
            color: black;
            padding: 22px 16px;
            width: 100%;
            border: none;
            outline: none;
            text-align: left;
            cursor: pointer;
            transition: 0.3s;
            font-size: 17px;
        }

        /* Change background color of buttons on hover */
        .tab button:hover {
            background-color: #ddd;
        }

        /* Create an active/current "tab button" class */
        .tab button.active {
            background-color: #ccc;
        }

        /* Style the tab content */
        .tabcontent {
            display: block;
            float: left;
            padding: 0 12px;
            border: 1px solid #ccc;
            width: 70%;
            border-left: none;
            height: fit-content;
        }

        .contenitore{
            display: flex;
        }
    </style>
</head>
<body>
<%@include file="Header.jsp"%>
<div class="contenitore">
    <div class="tab">
        <button class="tablinks" onclick="openCity(event, 'areautente')" id="defaultOpen">Area Utente</button>
        <button class="tablinks" onclick="openCity(event, 'areamodifiche')">Area Modifiche</button>
        <button class="tablinks" onclick="openCity(event, 'areaordini')">Area Ordini</button>
    </div>

    <div id="areautente" class="tabcontent">
        <h3>Dettagli Utente</h3>
        <p>
            <%
                Utente x = (Utente) session.getAttribute("Utente");
                String email = x.getEmail();
                String password = x.getPassword();
                String indirizzo = x.getIndirizzo();
                String nome= x.getNome();
                String cognome= x.getCognome();
                String codiceFiscale= x.getCodiceFiscale();
                Date dataNascita= x.getDataNascita();
                String telefono= x.getTelefono();
            %>
            Email:<br><%=email%>
            <br>Password:<br><%=password%>
            <br>Indirizzo:<br><%=indirizzo%>
            <br>Nome:<br><%=nome%>
            <br>Cognome:<br><%=cognome%>
            <br>Codice fiscale:<br><%=codiceFiscale%>
            <br>Data di Nascita<br><%=dataNascita%>
            <br></br>Telefono:<br><%=telefono%>
        </p>
    </div>

    <div id="areamodifiche" class="tabcontent">
        <h3>Area Modifiche</h3>
        <p></p>
    </div>

    <div id="areaordini" class="tabcontent">
        <h3>Dettagli Ordini</h3>
        <p></p>
    </div>
</div>


<script>
    function openCity(evt, cityName) {
        var i, tabcontent, tablinks;
        tabcontent = document.getElementsByClassName("tabcontent");
        for (i = 0; i < tabcontent.length; i++) {
            tabcontent[i].style.display = "none";
        }
        tablinks = document.getElementsByClassName("tablinks");
        for (i = 0; i < tablinks.length; i++) {
            tablinks[i].className = tablinks[i].className.replace(" active", "");
        }
        document.getElementById(cityName).style.display = "block";
        evt.currentTarget.className += " active";
    }

    // Get the element with id="defaultOpen" and click on it
    document.getElementById("defaultOpen").click();
</script>

<form action="logOut" method="post">
    <button type="submit" name="LogOut" style="color: red" >Log Out</button>
</form>

<%@ include file="Footer.jsp"%>
</body>
</html>
