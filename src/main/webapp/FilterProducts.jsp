<%@ page import="model.Prodotto" %>
<%@ page import="java.util.List" %>
<%@ page import="org.json.simple.JSONObject" %>

<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Prodotti</title>
    <link rel="icon" type="image/x-icon" href="Immagini/favicon.ico">
    <style>
        .pageContainer {
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .filtersContainer {
            padding: 20px;
            width: 80%;
            display: flex;
            justify-content: space-between;
            flex-wrap: wrap;
            row-gap: 20px;
            background: #f6f6f6;
            border-radius: 10px;
            margin: 10px auto 50px;
        }
        .filter {
            display: flex;
            flex-direction: column;
            align-items: center;
        }
        .filter label {
            margin-bottom: 5px;
        }
        .filter select, button, #reset-button {
            appearance: none;
            min-width: 150px;
            padding: 12px 15px;
            border: 1px solid #d6d6d6;
            border-radius: 15px;
            transition: 0.3s;
        }
        #reset-button:hover {
            background-color: orangered;
            color: black;
        }
        .content-group {
            width: 100%;
            display: flex;
            flex-wrap: wrap;
            align-items: center;
            justify-content: center;
        }
        .product-card {
            width: 20%;
            margin: 15px 15px 25px 15px;
            border-radius: 20px;
            box-shadow: 0 14px 14px black;
            transition: .3s;
        }
        .product-card:hover {
            transform: translateY(-8px);
        }
        .product-info-price {
            float: right;
            color: orangered;
            text-transform: capitalize;
            font-size: 19px;
            text-decoration: line-through;
            margin-right: 5px;
        }
        .product-info-price-off {
            float: right;
            text-transform: capitalize;
            font-size: 19px;
            margin-right: 5px;
        }
        .product-info-flavour {
            float: left;
            text-transform: capitalize;
            font-size: 19px;
            margin-left: 5px;
        }
        .product-info-name {
            padding: 5px 0;
            font-size: 20px;
            color: black;
            text-align: center;
            font-weight: bold;
        }
        .product-image {
            position: relative;
            display: flex;
            align-items: center;
            justify-content: center;
            height: 200px; /* Adjust this height as necessary */
        }
        .product-image img {
            max-width: 100%;
            max-height: 100%;
            border-top-left-radius: 20px;
            border-top-right-radius: 20px;
        }
        .product-sconto {
            position: absolute;
            background-color: #ffffff;
            padding: 5px;
            border-radius: 5px;
            color: orangered;
            right: 10px;
            top: 10px;
            text-transform: capitalize;
        }
        .product-card button {
            text-align: center;
            font-size: 20px;
            color: white;
            background-color: rgba(0,0,0,0.86);
            width: 100%;
            padding: 15px;
            border: 0;
            cursor: pointer;
            border-bottom-left-radius: 20px;
            border-bottom-right-radius: 20px;
            transition: .4s;
        }
        .product-card button:hover {
            background-color: orangered;
            color: black;
        }
        @media only screen and (max-width: 1030px) {
            .filtersContainer {
                justify-content: space-around;
            }
        }
    </style>
</head>
<body>
<%@ include file="Header.jsp" %>

<div class="pageContainer">
    <div class="filtersContainer" id="filtersContainer">
        <div class="filter">
            <select id="sorting" name="sort">
                <option value="">Sort by:</option>
                <option value="sortDown">Prezzo: da alto a basso</option>
                <option value="sortUp">Prezzo: da basso ad alto</option>
                <option value="evidence">In evidenza</option>
            </select>
        </div>

        <div class="filter">
            <select id="prices" name="price">
                <option value="">Seleziona un range di prezzo</option>
                <option value="0-30">0-30</option>
                <option value="30-60">30-60</option>
                <option value="60-100">60-100</option>
            </select>
        </div>

        <div class="filter">
            <select id="tastes" name="taste">
                <option value="">Seleziona un gusto</option>
            </select>
        </div>

        <div class="filter">
            <select id="calories" name="calorie">
                <option value="">Seleziona un range di calorie</option>
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

<div id="gr" class="content-group">
    <%
        List<Prodotto> products = null;
        if (session.getAttribute("originalProducts") != null) {
            products = (List<Prodotto>) session.getAttribute("originalProducts");
        } else {
            products = (List<Prodotto>) application.getAttribute("Products");
        }

        if (products != null) {
            for (Prodotto p : products) {
    %>
    <div class="product-card">
        <div class="product-image">
            <% if (p.getSconto() > 0) { %>
            <span class="product-sconto"><%= p.getSconto() %>% di Sconto</span>
            <% } %>
            <img src="<%= p.getImmagine() %>" alt="<%= p.getNome() %>">
        </div>
        <div class="product-info">
            <h2 class="product-info-name"><%= p.getNome() %></h2>
            <% if (p.getSconto() > 0) {
                float prezzoscontato = (p.getPrezzo() - ((p.getPrezzo() * p.getSconto()) / 100));
                prezzoscontato = Math.round(prezzoscontato * 100.0f) / 100.0f;
            %>
            <span class="product-info-price-off"><%= prezzoscontato %></span>
            <span class="product-info-price"><%= p.getPrezzo() %></span>
            <% } else { %>
            <span class="product-info-price-off"><%= p.getPrezzo() %></span>
            <% } %>
            <span class="product-info-flavour"><%= p.getGusto() %></span>
        </div>
        <button class="cartAdd" data-product = '<%=p.getIdProdotto()%>'>Aggiungi al Carrello</button>
    </div>
    <%
            }
        }
    %>
</div>

<script src="JS/showTastes.js"></script>
<script src="JS/resetProducts.js"></script>

<%@ include file="Footer.jsp" %>
</body>
</html>
