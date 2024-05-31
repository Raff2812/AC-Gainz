<%@ page import="java.util.List" %>
<%@ page import="model.Prodotto" %>
<%@ page import="model.ProdottoDAO" %><%--
  Created by IntelliJ IDEA.
  User: raffa
  Date: 29/05/2024
  Time: 18:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%@include file="Header.jsp"%>

<div id="filters" class="filters">
    <div id="firstSelect">
        <label>
            <select name="attributo" onchange="showSelect(this.value.toLowerCase())">
                <option value="">-</option>
                <option value="prezzo">Prezzo</option>
                <option value="calorie">Calorie</option>
                <option value="gusto">Gusto</option>
                <option value="sconto">Sconto</option>
            </select>
        </label>
    </div>
    <div id="secondSelect">

    </div>

</div>


<script>
    function showSelect(filter){
        if(filter === 'prezzo' || filter === 'calorie'){
            const secondSelect = document.getElementById("secondSelect");
            secondSelect.innerHTML = "";

            const label1 = document.createElement("label")
            const label2 = document.createElement("label");

            const select1 = document.createElement("select");
            const select2 = document.createElement("select");

            const optionDefault = document.createElement("option");
            optionDefault.value = "";
            optionDefault.textContent = "-";
            select1.appendChild(optionDefault);
            select2.appendChild(optionDefault);

            label1.appendChild(select1);
            label2.appendChild(select2);

            secondSelect.appendChild(label1);
            secondSelect.appendChild(label2);
        }
    }
</script>



<div id="gr" class="group">
<%
    if(request.getAttribute("productsByCriteria") != null) {
        List<Prodotto> productsByCriteria = (List<Prodotto>) request.getAttribute("productsByCriteria");
        for (Prodotto p: productsByCriteria){
%>
    <p><%=p.getNome()%> <%=p.getCategoria()%> <%=p.getGusto()%> <%=p.getPrezzo()%></p>
    <%
            }
    }
    else{
        List<Prodotto> allProducts = (List<Prodotto>) application.getAttribute("Products");
        for (Prodotto p: allProducts){
    %>
    <p><%=p.getNome()%> <%=p.getCategoria()%> <%=p.getGusto()%> <%=p.getPrezzo()%></p>
    <%
        }
        }
    %>
</div>



    <%--
<%
    ProdottoDAO prodottoDAO = new ProdottoDAO();
    List<Prodotto> allProducts = prodottoDAO.doRetrieveAll();

    for(Prodotto p: allProducts){
%>

    <p><%=p.getNome()%>  <%=p.getPrezzo()%>  <%=p.getCategoria()%></p>

<%
    }
%> --%>




<script>
    function filterCategory(category){
        const xhttp = new XMLHttpRequest();



        xhttp.onreadystatechange = function (){
            if(xhttp.readyState === 4 && xhttp.status === 200){
                const prodotti = JSON.parse(xhttp.responseText);

                const group = document.querySelector(".group");
                group.innerHTML = '';


                prodotti.forEach(prodotto => {
                    const p = document.createElement("p");
                    p.innerText = prodotto.nome + " " + prodotto.prezzo;
                    group.appendChild(p);
                })
            }
        }

        xhttp.open("GET", "filter?category="+category, true);
        xhttp.send();
    }

    document.addEventListener('headerScriptLoaded', () => {
        filterCategory(category);
        console.log("filterCategory script executed");
    });

</script>



<%@include file="Footer.jsp"%>
</body>
</html>
