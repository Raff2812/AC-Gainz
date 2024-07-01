<%@ page import="model.Prodotto" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Random" %><%--
  Created by IntelliJ IDEA.
  User: luigiauriemma
  Date: 27/06/24
  Time: 15:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Product Page</title>
  <style>
    .container-flex{
      display: flex;
      justify-content: center;
      flex-wrap: wrap;
    }
    .info-right{
      width: 50%;
      height: auto;
      margin: 0 0 0 30px;
    }
    .info-right-name{
      text-transform: capitalize;
      font-weight: 500;
      font-size: 30px;
      color: black;
      margin-top: 10px;
    }

    .product-info{
      background-color: #f5f5f5;
      margin-top: 10px;
      border-radius: 20px;
      padding: 15px 20px;
      width: 80%;

    }
    .product-info-risparmio{
      color: orangered;
      font-size: 15px;
      text-transform: capitalize;
    }
    .product-info-costoattuale{
      font-size: 20px;
      color: black;
      font-weight: bold;
    }
    .product-info-costooriginale{
      color: rgb(128,128,128);
      font-size: 17px;
    }
    .product-info-gusto, .product-info-peso, .product-info-quantita:not(:last-child){
      margin-top: 20px;
      margin-right: 5px;
      color: black;
      font-size: 20px;
      font-weight: 400;
    }
    .product-info-gusto #tastes, .product-info-peso #weights, .product-info-quantita #quantitynumber{
      border-radius: 5px;
      background-color: white;
      color: black;
      border: 1px solid lightgray;
      padding: 6px;
    }

    #cartAdd{
      margin-top: 50px;
      padding: 10px;
      color: white;
      background-color: orangered;
      font-size: 20px;
      font-weight: 350;
      transition: .3s;
      border: none;
      border-radius: 15px;
    }
    #cartAdd:hover{
      color: black;
      transform: translate(0,-1px);
      cursor: pointer;
    }



    .product-suggests{
      background-color: #f5f5f5;
      width: 90%;
      border-radius: 20px;
      margin-top: 5px;
    }
    .suggests-product-container{
      display: flex;
      flex-wrap: wrap;
      height: auto;
    }
    .suggests-product-card{
      margin: 10px 15px 10px 15px;
      border-radius: 20px;
      transition: .3s;
    }
    .suggests-product-image{
      position: relative;
      text-align: center;
    }
    .suggests-product-image img{
      max-width: 100px;
      max-height: 150px;
      border-top-left-radius: 20px;
      border-top-right-radius: 20px;
    }
    .suggests-product-sconto{
      position: absolute;
      font-size: 13px;
      background-color: #ffffff;
      padding: 5px;
      border-radius: 5px;
      color: orangered;
      right: 10px;
      top: 10px;
      text-transform: capitalize;
    }
    .suggests-cartAdd{
      position: absolute;
      bottom: 10px;
      left: 50%;
      transform: translateX(-50%);
      padding: 8px;
      width: 80%;
      text-transform: capitalize;
      border: none;
      outline: none;
      background-color: orangered;
      border-radius: 10px;
      transition: 0.4s;
      cursor: pointer;
      opacity: 0;
      color: white;
    }
    .suggests-product-card:hover .suggests-cartAdd{
      opacity: 1;
    }
    .suggests-cartAdd:hover{
      color: black;
    }
    .suggests-product-info{
      width: 100%;
      padding-top: 5px;
    }
    .suggests-product-info-name-redirect{
      text-transform: capitalize;
      margin-bottom: 4px;
      background-color: #f5f5f5;
      font-weight: 450;
      font-size: 17px;
      border: none;
      outline: none;
      cursor: pointer;
      color: black;
    }
    .suggests-product-info-costoattuale{
      color: black;
      font-size: 18px;
      font-weight: 400;
    }
    .suggests-product-info-costooriginale{
      color: orangered;
      text-decoration: line-through;
      font-size: 16px;
      font-weight: 400;
    }

    .suggests-product-info-flavour, .suggests-product-info-weight{
      font-size: 16px;
      font-weight: 400;
      text-transform: capitalize;
    }


    .info-left{
      width: 40%;
      height: 100%;
    }
    .info-left img{
      width: 100%;
      height: 100%;
      border-radius: 20px;
    }


    .product-description{
      margin-top: 20px;
      margin-left: 20px;
    }
    .product-description-accordion {
      background-color: #eee;
      color: #444;
      cursor: pointer;
      padding: 18px;
      width: 70%;
      border-radius: 20px;
      margin-bottom: 5px;
      border: none;
      text-align: left;
      outline: none;
      font-size: 15px;
      transition: 0.4s;
    }

    .product-description-accordion:last-child{
      margin-bottom: 25px;
    }

    .active, .product-description-accordion:hover {
      background-color: #ccc;
    }

    .product-description-panel {
      padding: 0 18px;
      margin-bottom: 15px;
      display: none;
      background-color: white;
      overflow: hidden;
    }

    table{
      width: 50%;
      border: 1px solid lightgray;
    }
    td{
      text-align: left;
      padding: 16px;
    }
    tr:nth-child(even)
    {
      background-color: #f5f5f5;
    }


  </style>
</head>
<body>
<%@ include file="Header.jsp"%>


  <%
    Prodotto p=null;
    if(request.getAttribute("prodotto")!=null){
      p= (Prodotto) request.getAttribute("prodotto");
    }
  %>
  <div class="container-flex">
    <div class="info-left">
      <img src="<%=p.getImmagine()%>">
    </div>

    <div class="info-right">
      <div class="info-right-name"><%=p.getNome()%></div>
      <div class="product-info">
        <% if (p.getSconto() > 0) {
          float prezzoscontato = (p.getPrezzo() - ((p.getPrezzo() * p.getSconto()) / 100));
          prezzoscontato = Math.round(prezzoscontato * 100.0f) / 100.0f;
        %>
        <div class="product-info-risparmio">Risparmia
          <%
            float costorisparmio=0;
            costorisparmio=(p.getPrezzo()*p.getSconto())/100;
          %>
            <%=costorisparmio%>€
        </div>
        <div>
          <span class="product-info-costoattuale"><%=prezzoscontato%>€</span>
          <span class="product-info-costooriginale">Era <%=p.getPrezzo()%>€</span>
        </div>
          <%}
          else {%>
          <span class="product-info-costoattuale"><%=p.getPrezzo()%>€</span>
          <% } %>
         <div class="product-info-gusto">
          Gusto:
          <select id="tastes" name="taste">
            <option value=""><%=p.getGusto()%></option>
          </select>
        </div>
        <div class="product-info-peso">
          Peso:
          <select id="weights" name="weight">
            <option value=""><%=p.getPeso()%></option>
          </select>
        </div>
        <div class="product-info-quantita">
          <select id="quantitynumber" name="quantity">
            <option value="1">1</option>
            <option value="2">2</option>
            <option value="3">3</option>
            <option value="4">4</option>
            <option value="5">5</option>
            <option value="6">6</option>
            <option value="7">7</option>
            <option value="8">8</option>
            <option value="9">9</option>
            <option value="10">10</option>
          </select>
          <button id="cartAdd">Aggiungi al Carrello</button>
        </div>
      </div>
      <div class="info-right-name">
        Potrebbe Interessarti anche:
      </div>
      <div class="product-suggests">
        <div class="suggests-product-container">
          <%
            List<Prodotto> suggeriti = null;
            if (request.getAttribute("suggeriti") != null) {
              suggeriti = (List<Prodotto>) request.getAttribute("suggeriti");
            }
            if (suggeriti != null) {
              for (int i=0;i<3;i++) {
                Random random=new Random();
                Prodotto s=suggeriti.get(random.nextInt(0,suggeriti.size()-1));
          %>
          <div class="suggests-product-card">
            <div class="suggests-product-image">
              <% if (s.getSconto() > 0) { %>
              <span class="suggests-product-sconto"><%= s.getSconto() %>% di Sconto</span>
              <% } %>

              <img src="<%= s.getImmagine() %>" alt="<%= s.getNome() %>">
              <button class="suggests-cartAdd">Aggiungi al Carrello</button>
            </div>
            <div class="suggests-product-info">
                <form action="Product" method="post">
                  <input type="hidden" name="primarykey" value="<%=s.getIdProdotto()%>">
                  <input type="hidden" name="category" value="<%=s.getCategoria()%>">
                  <button class="suggests-product-info-name-redirect"><%= s.getNome() %></button>
                </form>
              <% if (s.getSconto() > 0) {
                float prezzoscontato = (s.getPrezzo() - ((s.getPrezzo() * s.getSconto()) / 100));
                prezzoscontato = Math.round(prezzoscontato * 100.0f) / 100.0f;
              %>
              <span class="suggests-product-info-costoattuale"><%=prezzoscontato%>€</span>
              <span class="suggests-product-info-costooriginale"><%=s.getPrezzo()%>€</span>
              <% } else { %>
              <span class="suggests-product-info-costoattuale"><%=s.getPrezzo()%>€</span>
              <% } %>
              <div class="suggests-product-info-flavour"><%= s.getGusto()%></div>
              <div class="suggests-product-info-weight"><%=s.getPeso()%>gr.</div>
            </div>
          </div>
          <%
              }
            }
          %>
        </div>
      </div>
    </div>
  </div>
  <div class="product-description">
    <button class="product-description-accordion">Descrizione</button>
    <div class="product-description-panel">
      <p><%=p.getDescrizione()%></p>
    </div>

    <button class="product-description-accordion">Valori Nutrizionali</button>
    <div class="product-description-panel">
      <table>
        <p>Per 100gr.</p>
        <tr>
          <td>Energia kcal </td>
          <td><%=p.getCalorie()%>kcal</td>
        </tr>
        <tr>
          <td>Grassi</td>
          <td><%=p.getGrassi()%>g</td>
        </tr>
        <tr>
          <td>Carboidrati</td>
          <td><%=p.getCarboidrati()%>g</td>
        </tr>
        <tr>
          <td>Proteine</td>
          <td><%=p.getProteine()%>g</td>
        </tr>
      </table>
    </div>
  </div>

<script>
  var acc = document.getElementsByClassName("product-description-accordion");
  var i;

  for (i = 0; i < acc.length; i++) {
    acc[i].addEventListener("click", function() {
      this.classList.toggle("active");
      var panel = this.nextElementSibling;
      if (panel.style.display === "block") {
        panel.style.display = "none";
      } else {
        panel.style.display = "block";
      }
    });
  }
</script>
<%@ include file="Footer.jsp" %>
</body>
</html>