function genericFilter() {
    console.log("Calling genericFilter");

    const price = document.getElementById("prices").value;
    const taste = document.getElementById("tastes").value;
    const calories = document.getElementById("calories").value;
    const sorting = document.getElementById("sorting").value;

    const params = new URLSearchParams();

    if (price) params.append("price", price);
    if (taste) params.append("taste", taste);
    if (calories) params.append("calories", calories);
    if (sorting) params.append("sorting", sorting);

    const urlServlet = `genericFilter?${params.toString()}`;

    console.log("Calling genericFilter with URL: " + urlServlet);

    const xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function () {
        if (xhttp.readyState === 4 && xhttp.status === 200) {
            const prodottiFiltrati = JSON.parse(xhttp.responseText);
            const group = document.querySelector(".group");
            group.innerHTML = "";

            prodottiFiltrati.forEach(prodottoFiltrato => {
                const img = document.createElement("img");
                const button = document.createElement("button");
                button.innerHTML = "Aggiungi al carrello"
                button.className = ""
                //img.src = `${prodottoFiltrato.immagine}`;
                //img.style.width = "100px";
                const div = document.createElement("div");
                div.innerHTML = `${prodottoFiltrato.id} ${prodottoFiltrato.nome} ${prodottoFiltrato.prezzo} ${prodottoFiltrato.gusto} ${prodottoFiltrato.calorie}`;
                div.appendChild(button);
                //div.appendChild(img);
                group.appendChild(div);
            });
        } else if (xhttp.readyState === 4) {
            console.error("Error in genericFilter: " + xhttp.status);
        }
    };

    xhttp.open("GET", urlServlet, true);
    xhttp.send();
}

document.getElementById("prices").addEventListener("change", function() {
    console.log(`Price changed: ${this.value}`);
    genericFilter();
});

document.getElementById("tastes").addEventListener("change", function() {
    console.log(`Taste changed: ${this.value}`);
    genericFilter();
});

document.getElementById("calories").addEventListener("change", function() {
    console.log(`Calories changed: ${this.value}`);
    genericFilter();
});

document.getElementById("sorting").addEventListener("change", function() {
    console.log(`Sorting changed: ${this.value}`);
    genericFilter();
});


function buildJSONString(product) {
    const jsonObject = {
        id: product.id,
        nome: escapeJSONString(product.nome),
        categoria: escapeJSONString(product.categoria),
        prezzo: product.prezzo,
        gusto: escapeJSONString(product.gusto)
    };
    return JSON.stringify(jsonObject);
}

// Funzione per gestire l'escape dei caratteri speciali in stringhe JSON
function escapeJSONString(value) {
    // Gestione degli escape per caratteri speciali come ", \, /, ecc.
    return value.replace(/[\\"]/g, '\\$&')
        .replace(/\u0000/g, '\\0');
    // Aggiungi altre regole di escape se necessario
}