<%@ page import="model.DettaglioOrdine" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Ordine" %><%--
  Created by IntelliJ IDEA.
  User: raffa
  Date: 01/07/2024
  Time: 10:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ordine</title>
</head>
<body>
<%@include file="WEB-INF/Header.jsp"%>
<p>Ordine confermato</p> <br>
<p>Dettagli ordine:</p> <br>
<%
    Ordine ordine = (Ordine) request.getAttribute("order");
    List<DettaglioOrdine> dettaglioOrdine = (List<DettaglioOrdine>) request.getAttribute("orderDetails");
    if (dettaglioOrdine != null && !dettaglioOrdine.isEmpty()){
        for (DettaglioOrdine item: dettaglioOrdine){
%>
    <p><%=item.getIdProdotto()%>  <%=item.getQuantita()%> <%=item.getPrezzo()%></p>
<%
        }
    }
%>
<p>Totale ordine: <%=ordine.getTotale()%></p>


<%@include file="WEB-INF/Footer.jsp"%>
</body>
</html>
