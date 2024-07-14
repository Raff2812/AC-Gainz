<%@ page import="model.Prodotto" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: raffa
  Date: 11/07/2024
  Time: 16:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>

    <style>
        table {
            border-collapse: collapse;
            width: 100%;
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
            padding: 5px;
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

        /* Centro il div contenente la tabella */
        .tableContainer {
            width: 80%;
            margin: auto;
            padding-top: 50px;
        }
    </style>

</head>
<body>
<script src="JS/Tables.js"></script>
<a href="admin">Torna indietro</a>
<% List<Prodotto> prodotti = (List<Prodotto>) request.getAttribute("tableProdotto");
    if (prodotti != null){
%>
<div class="tableContainer">
    <table class="tableDB">
        <tr>
            <th>Id Prodotto</th>
            <th>Nome</th>
            <th>Descrizione</th>
            <th>Categoria</th>
            <th>Immagine</th>
            <th>Calorie</th>
            <th>Carboidrati</th>
            <th>Proteine</th>
            <th>Grassi</th>
            <th>Azione</th>
        </tr>
        <% for (Prodotto p : prodotti) { %>
        <tr>
            <td><%= p.getIdProdotto() %></td>
            <td><%= p.getNome() %></td>
            <td><%= p.getDescrizione() %></td>
            <td><%= p.getCategoria() %></td>
            <td><%= p.getImmagine() %></td>
            <td><%= p.getCalorie() %></td>
            <td><%= p.getCarboidrati() %></td>
            <td><%= p.getProteine() %></td>
            <td><%= p.getGrassi() %></td>
            <td class="center">
                <button class="button" onclick="editTableRow('prodotto', '<%=p.getIdProdotto()%>')">Modifica</button>
                <button class="button" onclick="deleteTableRow('prodotto', '<%=p.getIdProdotto()%>')">Elimina</button>
            </td>
        </tr>
        <% } %>
        <tr>
            <td colspan="10" class="center">
                <button class="add-button" onclick="addRow('prodotto')">+</button>
            </td>
        </tr>
    </table>
</div>
<% } %>
</body>
</html>
