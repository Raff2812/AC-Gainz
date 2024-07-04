<%@ page import="java.util.List" %>
<%@ page import="model.Prodotto" %>
<%@ page import="model.Carrello" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Carrello</title>
    <link rel="stylesheet" href="CSS/Cart.css">
    <link rel="icon" type="image/x-icon" href="Immagini/favicon.ico">
</head>
<body>
<%@ include file="WEB-INF/Header.jsp" %>

<script src="JS/Carrello.js"></script>

<div id="checkOutContainer">
    <div id="checkOutItem">
        <%
            List<Carrello> cartItems = (List<Carrello>) session.getAttribute("cart");
            List<Prodotto> productsCart = (List<Prodotto>) request.getAttribute("productsCart");

            float total = 0;
            if (cartItems != null && productsCart != null && cartItems.size() == productsCart.size()){
                for (int i = 0; i < cartItems.size(); i++) {
                    Carrello cartItem = cartItems.get(i);
                    Prodotto p = productsCart.get(i);
                    total += cartItem.getPrezzo();
        %>
        <div class="product">
            <img src="<%= p.getImmagine() %>" alt="<%= p.getNome() %>">
            <div class="product-info">
                <h3><%= p.getNome() %></h3>
                <p><%= p.getGusto() %></p>
                <p><%= p.getPeso() %> grammi</p>
            </div>
            <div class="quantity-div">
                <input class="inputQuantity" type="number" value="<%= cartItem.getQuantita() %>">
                <button data-product = '<%=p.getIdProdotto()%>' class="modifyQuantity">Modifica</button>
            </div>
            <div class="price-div">
                <p><%= Math.round(cartItem.getPrezzo() * 100.0f) / 100.0f %></p>
            </div>
            <div class="rmv-div">
                <button class="rmvButton" onclick="removeItem('<%= p.getIdProdotto() %>')">Rimuovi Elemento</button>
            </div>
        </div>
            <%
                    }
                }
            %>
        <!-- Dynamic cart items will be appended here by updateCartJSP() -->
    </div>


    <div class="summary">
        <h2>Somma Totale</h2>
        <p id="subtotal">Subtotale: <span> <%=total%> </span></p>
        <p id="totalOrder">Totale ordine: <span> <%=total%> </span></p>
        <div class="buy">
            <button id="buyCart">Procedi all'acquisto</button>
        </div>
    </div>
</div>

<%@ include file="WEB-INF/Footer.jsp" %>
</body>
</html>
