<%@ page import="model.Prodotto" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: luigiauriemma
  Date: 15/05/24
  Time: 16:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>AC-Gainz HomePage</title>
    <link rel="icon" type="image/x-icon" href="Immagini/favicon.ico">
    <link rel="stylesheet" href="CSS/ProductCard.css">
    <style>
        .page-container {
            margin: 40px;
        }

        .img-principale-container, .img-secondaria-container {
            position: relative;
            width: 90%;
            margin: 0 auto;
        }

        .discount-img, .img-secondaria {
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
            font-weight: 550;
            text-align: left;
            max-width: 50%;
            box-sizing: border-box;
        }

        .prodotti-phrase {
            font-size: 30px;
            color: black;
            font-weight: 450;
            margin: 10px 0 5px 0;
        }
        .prodotti-scontati, .prodotti-in-evidenza{
            width: 100%;
            display: flex;
            flex-wrap: wrap;
            align-items: center;
            justify-content: center;
        }
    </style>
</head>
<body>
<%@ include file="WEB-INF/Header.jsp"%>
<div class="page-container">
    <div class="img-principale-container">
        <img class="discount-img" src="./Immagini/discount-immagine-homepage.png" alt="immagine sconto">
        <div class="text-overlay">Carburante per L'attività Fisica, fino al <!--mettere il max di sconto --> di sconto</div>
        <div class="prodotti-phrase">In Sconto: </div>
    </div>

    <div class="prodotti-scontati">
            <%
                List<Prodotto> products = null;
                if (application.getAttribute("Products") != null) {
                    products = (List<Prodotto>) application.getAttribute("Products");
                }
                if (products != null) {
                    for (Prodotto p : products) {
                        if (p.getSconto()>0) {%>
            <div class="product-card">
                <div class="product-image">
                    <span class="product-sconto"><%= p.getSconto() %>% di Sconto</span>
                    <img src="<%= p.getImmagine() %>" alt="<%= p.getNome() %>">
                </div>
                <div class="product-info">
                    <h2 class="product-info-name">
                        <form action="Product" method="post">
                            <input type="hidden" name="primarykey" value="<%=p.getIdProdotto()%>">
                            <input type="hidden" name="category" value="<%=p.getCategoria()%>">
                            <button class="product-info-name-redirect"><%= p.getNome() %></button>
                        </form>
                    </h2>
                    <%
                        float prezzoscontato = (p.getPrezzo() - ((p.getPrezzo() * p.getSconto()) / 100));
                        prezzoscontato = Math.round(prezzoscontato * 100.0f) / 100.0f;
                    %>
                    <span class="product-info-price-off"><%= prezzoscontato %></span>
                    <span class="product-info-price"><%= p.getPrezzo() %></span>
                    <span class="product-info-flavour"><%= p.getGusto() %></span>
                </div>
                <button class="cartAdd"  onclick="addCart('<%=p.getIdProdotto()%>')">Aggiungi al Carrello</button>
            </div>
            <%
                        }
                    }
                }
            %>
        </div>

    <div class="img-secondaria-container">
        <img class="img-secondaria" src="./Immagini/seconda-immagine-homepage.jpg" alt="immagine prodotto">
        <div class="text-overlay">Scopri i Nostri Prodotti In Evidenza</div>
        <div class="prodotti-phrase">In Evidenza: </div>
    </div>

    <div class="prodotti-in-evidenza">
        <%
            List<Prodotto> products2 = null;
            if (application.getAttribute("Products") != null) {
                products2 = (List<Prodotto>) application.getAttribute("Products");
            }
            if (products2 != null) {
                for (Prodotto p : products2) {
                    if (p.isEvidenza()) {%>
        <div class="product-card">
            <div class="product-image">
                <span class="product-sconto"><%= p.getSconto() %>% di Sconto</span>
                <img src="<%= p.getImmagine() %>" alt="<%= p.getNome() %>">
            </div>
            <div class="product-info">
                <h2 class="product-info-name">
                    <form action="Product" method="post">
                        <input type="hidden" name="primarykey" value="<%=p.getIdProdotto()%>">
                        <input type="hidden" name="category" value="<%=p.getCategoria()%>">
                        <button class="product-info-name-redirect"><%= p.getNome() %></button>
                    </form>
                </h2>
                <%
                    float prezzoscontato = (p.getPrezzo() - ((p.getPrezzo() * p.getSconto()) / 100));
                    prezzoscontato = Math.round(prezzoscontato * 100.0f) / 100.0f;
                %>
                <span class="product-info-price-off"><%= prezzoscontato %></span>
                <span class="product-info-price"><%= p.getPrezzo() %></span>
                <span class="product-info-flavour"><%= p.getGusto() %></span>
            </div>
            <button class="cartAdd"  onclick="addCart('<%=p.getIdProdotto()%>')">Aggiungi al Carrello</button>
        </div>
        <%
                    }
                }
            }
        %>    </div>
</div>

<%@ include file="WEB-INF/Footer.jsp"%>
</body>
</html>