<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="model.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Prodotti</title>
    <link rel="stylesheet" href="CSS/ProductCard.css">
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
        .centered-div {
            display: none;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            width: 400px;
            margin: 0 auto;
            padding: 20px;
            background: white;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            position: fixed;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            box-sizing: border-box;
        }

        /* Styling the name */
        .nome-div-options {
            font-size: 24px;
            font-weight: bold;
            margin-bottom: 10px;
        }

        /* Styling the prices */
        .price-span {
            display: flex;
            align-items: baseline;
            gap: 10px;
        }

        .current-price {
            color: red;
            font-size: 22px;
            margin: 0;
        }

        .original-price {
            color: #666;
            text-decoration: line-through;
            font-size: 16px;
        }

        /* Styling the select element */
        .flavour-container,
        .weight-container {
            width: 100%;
            margin-bottom: 20px;
        }

        .flavour-select,
        .weight-select {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 1em;
        }

        /* Styling quantity select */
        .quantity-select {
            width: 100%;
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 1em;
        }

        /* Styling add to cart button */
        .add-to-cart-button {
            padding: 10px 20px;
            background: #007bff;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 1em;
            margin-bottom: 20px;
        }

        /* Styling form button */
        .product-info-name-redirect {
            padding: 10px 20px;
            background: #f1f1f1;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 1em;
        }
        /* Styling form button */
        .product-info-name-redirect:hover{
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
<%@ include file="WEB-INF/Header.jsp" %>

<div class="pageContainer">
    <div class="filtersContainer" id="filtersContainer">
        <div class="filter">
            <select id="sorting" name="sort" onchange="genericFilter()">
                <option value="">Sort by:</option>
                <option value="PriceDesc">Prezzo: da alto a basso</option>
                <option value="PriceAsc">Prezzo: da basso ad alto</option>
                <option value="CaloriesDesc">Calorie: da alto a basso</option>
                <option value="CaloriesAsc">Calorie: da basso ad alto</option>
                <option value="evidence">In evidenza</option>
            </select>
        </div>

        <%-- <div class="filter">
            <select id="prices" name="price" onchange="genericFilter()">
                <option value="">Seleziona un range di prezzo</option>
                <option value="0-30">0-30</option>
                <option value="30-60">30-60</option>
                <option value="60-100">60-100</option>
            </select>
        </div> --%>
        <div class="filter">
            <select id="weights" name="weights" onchange="genericFilter()">
                <option value="">Seleziona un peso</option>
                <option value="50">50 grammi</option>
                <option value="100">100 grammi</option>
                <option value="200">200 grammi</option>
                <option value="500">500 grammi</option>
                <option value="1000">1 kg</option>
            </select>
        </div>


        <div class="filter">
            <select id="tastes" name="taste" onclick="showTastes()" onchange="genericFilter()">
                <option value="">Seleziona un gusto</option>
            </select>
        </div>

        <%-- <div class="filter">
            <select id="calories" name="calorie" onchange="genericFilter()">
                <option value="">Seleziona un range di calorie</option>
                <option value="0-100">0-100</option>
                <option value="100-200">100-200</option>
                <option value="200-300">200-300</option>
                <option value="300-400">300-400</option>
            </select>
        </div> --%>


        <button id="reset-button" onclick="resetProducts()">Reset</button>
    </div>
</div>

<script defer src="JS/genericFilter.js"></script>
<script defer src="JS/showTastes.js"></script>
<script src="JS/productOptions.js"></script>





<div id="gr" class="content-group">
    <%
        List<Prodotto> products = null;
        if (request.getAttribute("originalProducts") != null) {
            products = (List<Prodotto>) request.getAttribute("originalProducts");
        } else {
            products = (List<Prodotto>) application.getAttribute("Products");
        }

        if (products != null) {
            for (Prodotto p : products) {
                Variante variante = p.getVarianti().get(0);
    %>
    <div class="product-card">
        <div class="product-image">
            <% if (variante.getSconto() > 0) { %>
            <span class="product-sconto"><%= variante.getSconto() %>% di Sconto</span>
            <% } %>
            <img src="<%= p.getImmagine() %>" alt="<%= p.getNome() %>">
        </div>
        <div class="product-info">
            <h2 class="product-info-name">
                <form action="ProductInfo" method="post">
                    <input type="hidden" name="primaryKey" value="<%=p.getIdProdotto()%>">
                    <button type="submit" class="product-info-name-redirect"><%= p.getNome() %></button>
                </form>
            </h2>
            <% if (variante.getSconto() > 0) {
                float prezzoscontato = (variante.getPrezzo() - ((variante.getPrezzo() * variante.getSconto()) / 100));
                prezzoscontato = Math.round(prezzoscontato * 100.0f) / 100.0f;
            %>
            <span class="product-info-flavour"><%= variante.getGusto() %></span>
            <span class="product-info-price-off"><%= prezzoscontato %> €</span>
            <span class="product-info-price"><%= variante.getPrezzo() %> €</span>
            <% } else { %>
            <span class="product-info-flavour"><%=variante.getGusto()%></span>
            <span class="product-info-price-off"><%= variante.getPrezzo() %> €</span>
            <% } %>
        </div>
            <button class="cartAdd"  onclick="optionsVarianti('<%=variante.getIdVariante()%>')">Aggiungi al Carrello</button>
    </div>
    <%
            }
        }
    %>
</div>

<div class="centered-div">
    <span>
        <h2 class="nome-div-options"></h2>
    </span>
    <span class="price-span">
        <h3 class="current-price"></h3>
    </span>

    <div class="flavour-container">
        <label>Flavour</label>
        <select class="flavour-select"></select>
    </div>
    <div class="weight-container">
        <label>Size</label>
        <select class="weight-select"></select>
    </div>
    <select class="quantity-select">
        <% for(int i = 1; i < 10; i++){ %>
        <option value="<%=i%>"><%=i%></option>
        <%
            }
        %>
    </select>

    <button class="add-to-cart-button">Aggiungi al carrello</button>
</div>



<%@ include file="WEB-INF/Footer.jsp" %>
</body>
</html>
