<%@ page import="model.Utente" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="CSS/HeaderCSS.css">
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
                <form action="index.jsp" method="post">
                    <input type="hidden" name="category" value="Tutto">
                    <button type="submit">Tutto</button>
                </form>
            </li>
            <li>
                <form action="filter" method="post">
                    <input type="hidden" name="category" value="proteine">
                    <button type="submit">Proteine</button>
                </form>
            </li>
            <li>
                <form action="filter" method="post">
                    <input type="hidden" name="category" value="farine">
                    <button type="submit">Farine</button>
                </form>
            </li>
            <li>
                <form action="filter" method="post">
                    <input type="hidden" name="category" value="barrette">
                    <button type="submit">Barrette</button>
                </form>
            </li>
            <li>
                <form action="filter" method="post">
                    <input type="hidden" name="category" value="salse">
                    <button type="submit">Salse</button>
                </form>
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
    <script>
        function isLogged() {
            const userLoggedIn = <%= session.getAttribute("Utente") != null %>;
            if (userLoggedIn) {
                document.getElementById("utente").innerHTML = `
                    <form action="AreaUtente.jsp" method="post">
                        <input type="hidden" name="areaPersonale" value="areaPersonale">
                        <button type="submit" class="header-custom_button">Area Personale</button>  <%-- qui mi definisco una classe custom_button per personalizzare il buttone
                                                                                                      vedere in Header.cc --%>
                    </form>
                `;
            }
        }
        window.onload = isLogged;
    </script>
</body>
</html>
