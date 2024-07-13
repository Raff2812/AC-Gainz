<%--
  Created by IntelliJ IDEA.
  User: raffa
  Date: 16/06/2024
  Time: 17:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Area Admin</title>
    <link rel="icon" type="image/x-icon" href="Immagini/favicon.ico">
    <style>
        #listTables{
            display: flex;
            align-items: center;
            justify-content: center;
            flex-direction: column;
            min-height: 100%;
        }
        #listTables button{
            padding: 30px;
            width: 200px;
        }
    </style>
</head>
<body>
<%@include file="WEB-INF/Header.jsp"%>

<div id="listTables">
    <form action="showTable" method="get">
        <input type="hidden" name="tableName" value="utente">
        <button type="submit" id="utenteTableButton">Utenti</button>
    </form>

    <form action="showTable" method="get">
        <input type="hidden" name="tableName" value="prodotto">
        <button type="submit" id="prodottoTableButton">Prodotti</button>
    </form>

    <form action="showTable" method="get">
        <input type="hidden" name="tableName" value="variante">
        <button type="submit" id="variantiTableButton">Varianti</button>
    </form>

    <form action="showTable" method="get">
        <input type="hidden" name="tableName" value="ordine">
        <button type="submit" id="ordineTableButton">Ordini</button>
    </form>
</div>



<%@include file="WEB-INF/Footer.jsp"%>
</body>
</html>
