<%@ page import="model.ProdottoDAO" %>
<%@ page import="model.Prodotto" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: raffa
  Date: 28/05/2024
  Time: 18:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%@include file="Header.jsp"%>


<div class="group">
    <%
        ProdottoDAO prodottoDAO = new ProdottoDAO();
        List<Prodotto> allProducts = prodottoDAO.doRetrieveAllSeasoning();

        for (Prodotto p : allProducts) {
    %>
    <h2><%= p.getNome() %></h2>
    <p><%= p.getCategoria() %></p>
    <p>Price: <%= p.getPrezzo() %> €</p>
    <%
        }
    %>



</div>

<script src="JS/showCondimenti.js"></script>
<script src="JS/filterCategory.js"></script>

<%@include file="Footer.jsp"%>
</body>
</html>
