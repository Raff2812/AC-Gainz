<%--
  Created by IntelliJ IDEA.
  User: luigiauriemma
  Date: 15/05/24
  Time: 10:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="CSS/HeaderCSS.css">
</head>
<body>
    <header>
        <div class="container">
            <div class="burger-menu" onclick="toggleMenu()">
                <div class="line"></div>
                <div class="line"></div>
                <div class="line"></div>
            </div>
            <div class="logo">AC-Gainz</div>
            <div class="search-bar">
                <input type="search" placeholder="Search">
            </div>
            <div class="carrello">
               <a href="">Carrello</a>
            </div>
            <div class="utente">
                <button onclick="myFunction()" class="dropbutton">Utente</button>
                <div id="myDropdown" class="dropdown-content">
                    <a href="#">Login</a>
                    <a href="#">Register</a>
                </div>
            </div>
        </div>

        <div class="lista" id="lista">
            <ul>
                <li>
                    <a href="">Tutto</a>
                </li>
                <li>
                    <a href="">Proteine</a>
                    <!--<button formaction="" name="">proteine</button>-->
                </li>
                <li>
                    <a href="">Farine</a>
                </li>
                <li>
                    <a href="">Barrette</a>
                </li>
                <li>
                    <a href="">Salse</a>
                </li>
            </ul>
        </div>
    </header>

    <script>
        /* quando viene cliccato il bottone, si apre e si toglie il contenuto del dropdown */
        function myFunction() {
            document.getElementById("myDropdown").classList.toggle("show");
        }

        // chiudi il dropdown se l'utente clicca all'esterno del riquadro
        window.onclick = function(event) {
            if (!event.target.matches('.dropbutton')) {
                var dropdowns = document.getElementsByClassName("dropdown-content");
                var i;
                for (i = 0; i < dropdowns.length; i++) {
                    var openDropdown = dropdowns[i];
                    if (openDropdown.classList.contains('show')) {
                        openDropdown.classList.remove('show');
                    }
                }
            }
        }



        //da modificare
        function toggleMenu() {
            var menu = document.getElementById("lista");
            menu.classList.toggle("showlista");
        }
    </script>
</body>
</html>
