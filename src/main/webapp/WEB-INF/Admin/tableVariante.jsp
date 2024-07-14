<%@ page import="model.Variante" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: raffa
  Date: 11/07/2024
  Time: 16:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>

    <style>
        table {
            border-collapse: collapse;
            width: 80%;
            margin: auto;
        }

        th, td {
            text-align: center;
            padding: 8px;
        }

        tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        th {
            background-color: #04AA6D;
            color: white;
        }
        th a{
            text-decoration: none;
        }

        .center {
            text-align: center;
        }

        .button {
            padding: 5px 10px;
            background-color: #04AA6D;
            color: white;
            border: none;
            cursor: pointer;
            text-align: center;
        }

        .button:hover {
            background-color: #45a049;
        }

        .add-button {
            padding: 5px 10px;
            background-color: #4CAF50;
            color: white;
            border: none;
            cursor: pointer;
            text-align: center;
        }

        .add-button:hover {
            background-color: #45a049;
        }

        /* Centro il form verticalmente e orizzontalmente */
        .divForm {
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            padding: 20px;
            background-color: #f9f9f9;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            border-radius: 10px;
            width: 300px;
        }

        /* Stile del form */
        .formUpdate {
            display: flex;
            flex-direction: column;
            gap: 15px;
        }
        .formUpdate label {
            margin-bottom: -10px;
            font-weight: bold;
        }
        /* Stile degli input */
        .formUpdate input {
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 16px;
        }

        /* Stile del pulsante */
        .formUpdate button {
            padding: 10px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        /* Stile del pulsante al passaggio del mouse */
        .formUpdate button:hover {
            background-color: #0056b3;
        }


    </style>

</head>
<body>
<script src="JS/Tables.js"></script>
<a href="admin">Torna indietro</a>
    <% List<Variante> varianti = (List<Variante>) request.getAttribute("tableVariante");
    if (varianti != null){
%>

<table class="tableDB">
    <tr>
        <th>Id Variante</th>
        <th><a href="showTable?tableName=prodotto">Id Prodotto Variante</a></th>
        <th><a href="showTable?tableName=gusto">Id Gusto</a></th>
        <th><a href="showTable?tableName=confezione">Id Confezione</a></th>
        <th>Prezzo</th>
        <th>Quantità</th>
        <th>Sconto</th>
        <th>Evidenza</th>
        <th>Azione</th>
    </tr>
    <% for (Variante v: varianti) {
        int evidence = 0;
        if (v.isEvidenza()) evidence = 1;
    %>
    <tr>
        <td><%= v.getIdVariante() %></td>
        <td><%= v.getIdProdotto() %></td>
        <td><%= v.getIdGusto() %></td>
        <td><%= v.getIdConfezione() %></td>
        <td><%= v.getPrezzo() %></td>
        <td><%= v.getQuantita() %></td>
        <td><%= v.getSconto() %></td>
        <td><%= evidence %></td>
        <td class="center">
            <button class="button" onclick="editTableRow('variante', '<%=v.getIdVariante()%>')">Modifica</button>
            <button class="button" onclick="deleteTableRow('variante', '<%=v.getIdVariante()%>')">Elimina</button>
        </td>

    </tr>
    <% } %>
    <tr>
        <td colspan="8" class="center">
            <button class="add-button" onclick="addRow('variante')">+</button>
        </td>
    </tr>
</table>
    <% } %>
