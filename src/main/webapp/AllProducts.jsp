<%@ page import="model.Prodotto" %>
<%@ page import="java.util.List" %>
<%@ page import="model.ProdottoDAO" %><%--
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
<%@ include file="Header.jsp"%>

<div class="product-list">
    <%
        ProdottoDAO prodottoDAO = new ProdottoDAO();
        List<Prodotto> allProducts = prodottoDAO.doRetrieveAll();

        for (Prodotto p : allProducts) {
    %>
    <div class="product-item">
        <h2><%= p.getNome() %></h2>
        <p><%= p.getCategoria() %></p>
        <p>Price: <%= p.getPrezzo() %> €</p>
    </div>
    <%
        }
    %>
</div>
<%@ include file="Footer.jsp"%>
</body>
</html>
