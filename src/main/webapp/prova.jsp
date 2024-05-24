<%@ page import="model.Prodotto" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: raffa
  Date: 23/05/2024
  Time: 10:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    List<Prodotto> products = (List<Prodotto>) request.getAttribute("productsByCategoria");

    for(Prodotto p: products){
%>
<%=p.getIdProdotto()%> <%= p.getNome()%> <%= p.getCategoria()%> <%= p.getPrezzo()%> <br>

<%
    }
%>

</body>
</html>
