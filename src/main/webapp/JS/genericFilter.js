function genericFilter(filter, value) {
    console.log(`Calling genericFilter with filter: ${filter}, value: ${value}`);

    const xhttp = new XMLHttpRequest();
    const urlServlet = `genericFilter?filter=${encodeURIComponent(filter)}&value=${encodeURIComponent(value)}`;

    console.log("Calling genericFilter with URL: " + urlServlet);

    xhttp.onreadystatechange = function () {
        if (xhttp.readyState === 4 && xhttp.status === 200) {
            const prodottiFiltrati = JSON.parse(xhttp.responseText);
            const group = document.querySelector(".group");
            group.innerHTML = "";

            prodottiFiltrati.forEach(prodottoFiltrato => {
                const p = document.createElement("p");
                p.innerHTML = `${prodottoFiltrato.id} ${prodottoFiltrato.nome} ${prodottoFiltrato.prezzo} ${prodottoFiltrato.gusto} ${prodottoFiltrato.calorie}`;
                group.appendChild(p);
            });
        } else if (xhttp.readyState === 4) {
            console.error("Error in genericFilter: " + xhttp.status);
        }
    };

    xhttp.open("GET", urlServlet, true);
    xhttp.send();
}


document.getElementById("prices").addEventListener("change", function() {
    const value = this.value;
    console.log(`Price changed: ${value}`);
        genericFilter('price', value);

});

document.getElementById("tastes").addEventListener("change", function() {
    const value = this.value;
    console.log(`Taste changed: ${value}`);
        genericFilter('taste', value);

});

document.getElementById("calories").addEventListener("change", function() {
    const value = this.value;
    console.log(`Calories changed: ${value}`);
        genericFilter('calories', value);

});
