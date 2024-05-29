<%@ page import="model.ProdottoDAO" %>
<%@ page import="model.Prodotto" %>
<%@ page import="java.util.List" %>
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
    List<Prodotto> allProducts = prodottoDAO.doRetrieveAllSupplements();

    for (Prodotto p : allProducts) {
%>
    <h2><%= p.getNome() %></h2>
    <p><%= p.getCategoria() %></p>
    <p>Price: <%= p.getPrezzo() %> €</p>
<%
    }
%>






</div>


<script src="JS/showIntegratori.js"></script>
<script src="JS/filterCategory.js"></script>






<%@include file="Footer.jsp"%>
</body>
</html>
