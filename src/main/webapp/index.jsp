<%@ page import="java.util.List, java.util.HashMap" %>
<%@ page import="model.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="WEB-INF/Header.jsp"%>
<html>
<head>
    <title>AC-Gainz HomePage</title>
    <link rel="icon" type="image/x-icon" href="Immagini/favicon.ico">
    <link rel="stylesheet" href="CSS/ProductCard.css">
    <style>
        .page-container {
            margin: 40px;
        }
        .img-container {
            position: relative;
            width: 90%;
            margin: 0 auto;
        }
        .main-img, .secondary-img {
            width: 100%;
            border-radius: 20px;
        }
        .text-overlay {
            position: absolute;
            top: 27%;
            left: 27%;
            transform: translate(-50%, -50%);
            color: white;
            font-size: 35px;
            padding: 5px;
            font-weight: 550;
            text-align: left;
            max-width: 50%;
            border-radius: 20px;
            backdrop-filter: blur(40px);
            box-sizing: border-box;
        }
        .prodotti-phrase {
            font-size: 30px;
            color: black;
            font-weight: 450;
            margin: 10px 0 5px 0;
        }
        .prodotti-list {
            width: 100%;
            display: flex;
            flex-wrap: wrap;
            align-items: center;
            justify-content: center;
            gap: 15px;
        }
    </style>
</head>

<body>
<script src="JS/productOptions.js"></script>

<div class="page-container">
    <div class="img-container">
        <img class="main-img" src="./Immagini/discount-immagine-homepage.png" alt="immagine sconto">
        <div class="text-overlay">Carburante per L'attività Fisica, dai uno occhio ai nostri prodotti scontati</div>
        <div class="prodotti-phrase">In Sconto: </div>
    </div>
    <div class="prodotti-list">
        <%
            List<Prodotto> products = (List<Prodotto>) application.getAttribute("Products");

            if (products != null) {
                for (Prodotto product : products) {
                    Variante variante = product.getVarianti().get(0);
                    if (variante.getSconto() > 0) {
                        float prezzoScontato = Math.round((variante.getPrezzo() - ((variante.getPrezzo() * variante.getSconto()) / 100)) * 100.0f) / 100.0f;
        %>
        <div class="product-card">
            <div class="product-image">
                <span class="product-sconto"><%= variante.getSconto() %>% di Sconto</span>
                <img src="<%= product.getImmagine() %>" alt="<%= product.getNome() %>">
            </div>
            <div class="product-info">
                <h2 class="product-info-name">
                    <form action="ProductInfo" method="post">
                        <input type="hidden" name="primaryKey" value="<%= product.getIdProdotto() %>">
                        <input type="hidden" name="category" value="<%= product.getCategoria() %>">
                        <button class="product-info-name-redirect"><%= product.getNome() %></button>
                    </form>
                </h2>
                <span class="product-info-flavour"><%= variante.getGusto() %></span>
                <span class="product-info-price-off"><%= prezzoScontato %>€</span>
                <span class="product-info-price"><%= variante.getPrezzo() %>€</span>

            </div>
            <button class="cartAdd" onclick="optionsVarianti('<%= variante.getIdVariante() %>')">Aggiungi al Carrello</button>
        </div>
        <%
                    }
                }
            }
        %>
    </div>

    <div class="img-container">
        <img class="secondary-img" src="./Immagini/seconda-immagine-homepage.jpg" alt="immagine prodotto">
        <div class="text-overlay">Scopri i Nostri Prodotti In Evidenza</div>
        <div class="prodotti-phrase">In Evidenza: </div>
    </div>
    <div class="prodotti-list">
        <%
            if (products != null) {
                for (Prodotto product : products) {
                    Variante variante = product.getVarianti().get(0);
                    if (variante.isEvidenza()) {
                        float prezzoScontato = Math.round((variante.getPrezzo() - ((variante.getPrezzo() * variante.getSconto()) / 100)) * 100.0f) / 100.0f;
        %>
        <div class="product-card">
            <div class="product-image">
                <span class="product-sconto"><%= variante.getSconto() %>% di Sconto</span>
                <img src="<%= product.getImmagine() %>" alt="<%= product.getNome() %>">
            </div>
            <div class="product-info">
                <h2 class="product-info-name">
                    <form action="ProductInfo" method="post">
                        <input type="hidden" name="primaryKey" value="<%= product.getIdProdotto() %>">
                        <input type="hidden" name="category" value="<%= product.getCategoria() %>">
                        <button class="product-info-name-redirect"><%= product.getNome() %></button>
                    </form>
                </h2>
                <span class="product-info-flavour"><%= variante.getGusto() %></span>
                <span class="product-info-price-off"><%= prezzoScontato %>€</span>
                <span class="product-info-price"><%= variante.getPrezzo() %>€</span>
            </div>
            <button class="cartAdd" onclick="optionsVarianti('<%= variante.getIdVariante() %>')">Aggiungi al Carrello</button>
        </div>
        <%
                    }
                }
            }
        %>
    </div>
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
        <% for(int i = 1; i <= 10; i++){ %>
        <option value="<%=i%>"><%=i%></option>
        <%
            }
        %>
    </select>

    <button class="add-to-cart-button">Aggiungi al carrello</button>
</div>

<%@ include file="WEB-INF/Footer.jsp"%>
</body>
</html>
