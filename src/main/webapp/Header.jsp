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
        <div class="logo">
            <a href="index.jsp" >AC-Gainz</a>
        </div>
        <div class="search-bar">
            <input type="search" placeholder="Search..">
        </div>
        <div class="carrello">
            <a href="">Carrello</a>
        </div>
        <div class="utente">
            <button onclick="myFunction()" class="dropbutton">Utente</button>
            <div id="myDropdown" class="dropdown-content">
                <a href="Login.jsp">Login</a>
                <a href="Registrazione.jsp">Register</a>
            </div>
        </div>
    </div>



    <div class="lista" id="lista">
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

  <%--  <div class="lista" id="lista">
        <ul>
            <li>
                <a href="index.jsp">Tutto</a>
            </li>
            <li>
                <a href="javascript:void(0);" onclick="sendCategory('Proteine')">Proteine</a>
            </li>
            <li>
                <a href="javascript:void(0);" onclick="sendCategory('Farine')">Farine</a>
            </li>
            <li>
                <a href="javascript:void(0);" onclick="sendCategory('Barrette')">Barrette</a>
            </li>
            <li>
                <a href="javascript:void(0);" onclick="sendCategory('Salse')">Salse</a>
            </li>
        </ul>
    </div>
</header> --%>


<%-- <script>
    function sendCategory(category) {
        const xhr = new XMLHttpRequest();
        xhr.open("POST", "filter", true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                // Puoi gestire la risposta qui se necessario
                console.log(xhr.responseText);
            }
        };
        xhr.send(category += "");
    }
</script> --%>


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
