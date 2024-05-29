<%@ page import="model.Utente" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="CSS/HeaderCSS.css">

    <% Utente x = (Utente) session.getAttribute("Utente");
        boolean isLogged = (x != null); %>


    <script>
        window.onload = function() {
            const isLogged = <%= isLogged %>;
            if (isLogged) {
                document.getElementById("utente").innerHTML = `
                <form action="AreaUtente.jsp" method="post">
                    <input type="hidden" name="areaPersonale" value="areaPersonale">
                    <button type="submit" class="header-custom_button">Area Personale</button>
                </form>
            `;
            }
            console.log(isLogged);
            // Dispatch a custom event to signal that the header script has finished executing
            document.dispatchEvent(new Event('headerScriptLoaded'));
        };
    </script>


</head>
<body>


<header>
    <div class="header-container">
        <div class="header-burger-menu" onclick="toggleMenu()">
            <div class="header-line"></div>
            <div class="header-line"></div>
            <div class="header-line"></div>
        </div>
        <div class="header-logo">
            <a href="index.jsp" >AC-Gainz</a>
        </div>
        <div class="header-search-bar">
            <input type="search" placeholder="Search..">
        </div>
        <div class="header-carrello">
            <a href="">Carrello</a>
        </div>
        <div class="header-utente" id="utente">
            <button onclick="myFunction()" class="header-dropbutton">Utente</button>
            <div id="myDropdown" class="header-dropdown-content">
                <a href="Login.jsp">Login</a>
                <a href="Registrazione.jsp">Register</a>
            </div>
        </div>
    </div>



    <div class="header-lista" id="lista">
        <ul>
            <li>
                <a href="FilterProducts.jsp?category=tutto">Tutto</a>
            </li>
            <li>
                <a href="FilterProducts.jsp?category=proteine">Proteine</a>
            </li>
            <li>
                <a href="FilterProducts.jsp?category=creatina">Creatina</a>
            </li>
            <li>
                <a href="FilterProducts.jsp?category=salse">Salse</a>
            </li>
        </ul>

    </div>


</header>

    <script>
        /* quando viene cliccato il bottone, si apre e si toglie il contenuto del dropdown */
        function myFunction() {
            document.getElementById("myDropdown").classList.toggle("header-show");
        }
        // chiudi il dropdown se l'utente clicca all'esterno del riquadro
        window.onclick = function(event) {
            if (!event.target.matches('.header-dropbutton')) {
                var dropdowns = document.getElementsByClassName("header-dropdown-content");
                var i;
                for (i = 0; i < dropdowns.length; i++) {
                    var openDropdown = dropdowns[i];
                    if (openDropdown.classList.contains('header-show')) {
                        openDropdown.classList.remove('header-show');
                    }
                }
            }
        }
        function toggleMenu() {
            var menu = document.getElementById("lista");
            menu.classList.toggle("header-showlista");
        }
    </script>



<%-- Script che prende dalla session l'utente loggato, e se tale utente non è undefined
         cambia nell'header l'elemento con id = 'utente' e lo sostituisce con un bottone che reinderizza ora all'Area Personale
         dell'utente--%>





</body>
</html>
