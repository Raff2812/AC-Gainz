function resetProducts() {

    const xhttp = new XMLHttpRequest();
    const urlServlet = `resetProducts`;

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

