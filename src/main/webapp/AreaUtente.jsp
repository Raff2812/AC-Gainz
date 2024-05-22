<%@ page import="model.Utente" %><%--
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
</head>
<body>
<%
    Utente x = (Utente) session.getAttribute("Utente");
    String email = x.getEmail();
    String password = x.getPassword();
    String indirizzo = x.getIndirizzo();
%>



<p style="color: blue"> Ciao <%= email %> </p> <br>


</body>
</html>
