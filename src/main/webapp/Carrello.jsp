<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Carrello</title>
    <link rel="stylesheet" href="CSS/Cart.css">

    <link rel="icon" type="image/x-icon" href="Immagini/favicon.ico">

</head>
<body>
<%@include file="Header.jsp"%>

<script src="JS/updateCart.js"></script>

<div id="checkOutContainer">
    <div id="checkOutItem">
        <!-- Dynamic cart items will be appended here by updateCartJSP() -->
    </div>
    <div class="summary">
        <h2>Somma Totale</h2>
        <p id="subtotal">Subtotale: <span></span></p>
        <p id="totalOrder">Totale ordine: <span></span></p>
        <div class="buy">
            <button id="buyCart">Procedi all'acquisto</button>
        </div>
    </div>
</div>

<%@include file="Footer.jsp"%>
</body>
</html>
