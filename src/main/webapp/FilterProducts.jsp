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
        .filter select, button{
            appearance: none;
            min-width: 150px;
            padding: 12px 15px;
            border: 1px solid #d6d6d6;
            border-radius: 10px;

        }

        .content-group{
            width: 100%;
            display: flex;
            flex-wrap: wrap;
            align-items: center;
            justify-content: center;
        }

        .product-card{
            width: 20%;
            margin: 15px 15px 25px 15px;
            border-radius: 20px;
            box-shadow: 0 14px 14px black;
            transition: .3s;
            text-align: center;
        }

        .product-card:hover{
            transform: translate(0,-8px);
        }

        .product-info{
            align-content: center;
        }
        .product-info-name{
            padding: 5px 0;
            font-size: 18px;
            color: black;
            font-weight: bold;
        }
        .product-card img{
            padding-top: 5px;
            width: 40%;
            height: 40%;
        }

        .product-card button{
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

        .product-card button:hover{
            background-color: orangered;
            color: black;
        }


        @media only screen and (max-width: 1030px) {
            .filtersContainer{
                justify-content: space-around;
            }
        }
    </style>
    <script src="JS/FilterProductsStart.js"></script>
</head>
<body>
<%@include file="Header.jsp"%>



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
        if (session.getAttribute("productsByCriteria") != null) {
            products = (List<Prodotto>) session.getAttribute("productsByCriteria");
        } else {
            products = (List<Prodotto>) application.getAttribute("Products");
        }
        if (products != null) {
            for (Prodotto p : products) {
    %>
    <div class="product-card">
        <img src="<%=p.getImmagine()%>">
        <div class="product-info">
            <div class="product-info-name">
                <%= p.getNome() %>
            </div>
            <div class="product-info-price">
                <%= p.getPrezzo() %>
            </div>
            <div class="product-info-flavour">
                <%= p.getGusto() %>

            </div>
        </div>
        <button class="cartAdd">Aggiungi al Carrello</button>
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
