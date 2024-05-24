<%@ page import="model.Prodotto" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: luigiauriemma
  Date: 15/05/24
  Time: 16:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%-- <%@ include file="WEB-INF/results/Registrazione.jsp"%> --%>
<a href="Registrazione.jsp">Registrazione</a><br>
<a href="Login.jsp">Login</a><br>
<form action="prova" method="post">
    <!--<input type="text" name="attributo" placeholder="inserisci attributo">
    <input type="text" name="valore" placeholder="inserisci valore">-->

    <select name="attributo">
        <option value="calorie">Calorie</option>
        <option value="quantità">Quantità</option>
        <option value="prezzo">Prezzo</option>
        <option value="peso">Peso</option>
    </select>
    <select name="valore">
        <option value="0 - 100">0 - 100</option>
        <option value="100 - 200">100 - 200</option>
        <option value="200 - 300">200 - 300</option>
        <option value="300 - 400">300 - 400</option>
    </select>



    <input type="submit" value="GO">
</form>
<%
    List<Prodotto> prodottoList = (List<Prodotto>) application.getAttribute("Products");
    for(Prodotto p: prodottoList){
%>
<%=p.getIdProdotto()%> <%= p.getNome()%> <%= p.getCategoria()%> <%= p.getPrezzo()%> <br>
<%
    }
%>
</body>
</html>
