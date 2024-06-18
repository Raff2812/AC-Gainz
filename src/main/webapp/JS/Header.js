window.onload = function() {
    const isLogged = Logged;
    if (isLogged) {
        document.getElementById("utente").innerHTML = `
                <form action="AreaUtente.jsp" method="post">
                    <input type="hidden" name="areaPersonale" value="areaPersonale">
                    <button type="submit" class="header-custom_button">Area Personale</button>
                </form>
            `;
    }
    console.log(isLogged);
}







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


/*function toggleCart(){
    const listCart = document.getElementById("listCart");
    listCart.classList.toggle("hidden")
}*/
