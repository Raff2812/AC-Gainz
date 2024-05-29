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
    <title>AC-Gainz HomePage</title>
</head>
<body>
 <%@ include file="Header.jsp"%>






 <%-- se sei arrivato qui leggendo a partire dai commenti di HomePage, leggi i commenti di Header.jsp
<form action="prova" method="post">
    <select name="attributo">
        <option value="calorie">Calorie</option>
        <option value="quantita">Quantità</option>
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
</form--%>

 <%--
 <%
    List<Prodotto> prodottoList = (List<Prodotto>) application.getAttribute("Products");
    for(Prodotto p: prodottoList){
%>
 <p><%=p.getIdProdotto()%> <%= p.getNome()%> <%= p.getCategoria()%> <%= p.getPrezzo()%> </p>
<%
    }
%>

--%>
 <div class="group"></div>

 <p> Home Page</p>


 <%@ include file="Footer.jsp"%>
</body>
</html>
