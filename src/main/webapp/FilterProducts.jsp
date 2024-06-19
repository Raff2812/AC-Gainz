<%@ page import="model.Prodotto" %>
<%@ page import="java.util.List" %>
<%@ page import="org.json.simple.JSONObject" %>

<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Prodotti</title>
    <style>
        .pageContainer {
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .filtersContainer {
            display: flex;
            gap: 20px;
            padding: 20px;
            background-color: transparent;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            justify-content: center;
            align-items: center;
        }
        .filter {
            display: flex;
            flex-direction: column;
            align-items: center;
        }
        .filter label {
            margin-bottom: 5px;
        }
        .filter select {
            padding: 5px;
        }
        .buttonFilter {
            padding: 5px;
            text-decoration: none;
        }
    </style>
</head>
<body>

<%@include file="Header.jsp"%>

<%-- <script src="JS/Cart.js"></script> --%>

<div class="pageContainer">
    <div class="filtersContainer" id="filtersContainer">
        <div class="sort">
            <label for="sorting">Ordina Per</label>
            <select id="sorting" name="sort">
                <option value="">-</option>
                <option value="sortDown">Prezzo: da alto a basso</option>
                <option value="sortUp">Prezzo: da basso ad alto</option>
                <option value="evidence">In evidenza</option>
            </select>
        </div>

        <div class="filter">
            <label for="prices">Prezzo</label>
            <select id="prices" name="price">
                <option value="">Seleziona un range</option>
                <option value="0-30">0-30</option>
                <option value="30-60">30-60</option>
                <option value="60-100">60-100</option>
            </select>
        </div>

        <div class="filter">
            <label for="tastes">Gusti</label>
            <select id="tastes" name="taste"></select>
        </div>

        <div class="filter">
            <label for="calories">Calorie</label>
            <select id="calories" name="calorie">
                <option value="">Seleziona un range</option>
                <option value="0-100">0-100</option>
                <option value="100-200">100-200</option>
                <option value="200-300">200-300</option>
                <option value="300-400">300-400</option>
            </select>
        </div>

        <button id="reset-button">Reset</button>
    </div>
</div>

<script src="JS/genericFilter.js"></script>

<div id="gr" class="group">
    <%
        List<Prodotto> products = null;
        if (request.getAttribute("productsByCriteria") != null) {
            products = (List<Prodotto>) request.getAttribute("productsByCriteria");
        } else {
            products = (List<Prodotto>) application.getAttribute("Products");
        }
        if (products != null) {
            session.setAttribute("originalProducts", products);
            session.setAttribute("products", products);
            for (Prodotto p : products) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", p.getIdProdotto());
                String jsonString = jsonObject.toJSONString();
    %>
    <div class="px">
        <%= p.getIdProdotto() %>
        <%= p.getNome() %>
        <%= p.getCategoria() %>
        <%= p.getPrezzo() %>
        <%= p.getGusto() %>
        <button class="cartAdd" data-product='<%= jsonString %>'>Aggiungi al Carrello</button>
    </div>
    <%
            }
        }
    %>
</div>

<script src="JS/showTastes.js"></script>
<script src="JS/resetProducts.js"></script>

<%@include file="Footer.jsp"%>

</body>
</html>
