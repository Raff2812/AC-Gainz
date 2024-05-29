<%--
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
<div class="group"></div>

<% String filter = request.getParameter("category");%>

<script>
    var category = '<%=filter%>';
    console.log(category);
    function filterCategory(category){
        const xhttp = new XMLHttpRequest();

        xhttp.onreadystatechange = function (){
            if(xhttp.readyState === 4 && xhttp.status === 200){
                const prodotti = JSON.parse(xhttp.responseText);

                const group = document.querySelector(".group");
                group.innerHTML = '';

                prodotti.forEach(prodotto => {
                    const p = document.createElement("p");
                    p.innerText = prodotto.nome;
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
