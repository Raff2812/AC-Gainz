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
            <div class="logo">AC-Gainz</div>
            <div class="search-bar">
                <input type="search" placeholder="Search">
            </div>
            <div class="carrello">carrello </div>
            <div class="utente">
                <button onclick="myFunction()" class="dropbutton">Utente</button>
                <div id="myDropdown" class="dropdown-content">
                    <a href="#">Login</a>
                    <a href="#">Register</a>
                </div>
            </div>
        </div>

        <div class="lista">
            <ul>
                <li>
                    <a href="">Tutto</a>
                </li>
                <li>
                    <a href="">Proteine</a>
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
    </script>
</body>
</html>
