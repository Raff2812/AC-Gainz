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


    fetch(urlServlet)
        .then(response => {
            if (!response.ok) throw new Error();

            return response.text();
        })
        .then(responseText => {
            updateView(responseText);
        })
        .catch(error => {
            console.log(error);
        })

    console.log("Calling genericFilter with URL: " + urlServlet);
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

document.addEventListener("DOMContentLoaded", function() {
    const queryString = window.location.search;
    const urlSearchParams = new URLSearchParams(queryString);

    if (urlSearchParams.has("name")) {
        filterByName(urlSearchParams.get("name"));
    }
});

function filterByName(name) {
    const urlSearch = new URLSearchParams();
    urlSearch.append("name", name);

    fetch(`genericFilter?${urlSearch.toString()}`)
        .then(response => {
            if (!response.ok) throw new Error();
            return response.text();
        })
        .then(responseText => {
            updateView(responseText);
        })
        .catch(error => {
            console.error(error);
        });
}

function updateView(response) {
    const group = document.querySelector(".group");
    group.innerHTML = "";

    const products = JSON.parse(response);

    products.forEach(prodottoFiltrato => {
        const button = document.createElement("button");
        button.innerHTML = "Aggiungi al carrello";
        button.className= "cartAdd";

        const product = JSON.stringify(prodottoFiltrato);

        button.setAttribute("data-product", product);

        const div = document.createElement("div");
        div.innerHTML = `${prodottoFiltrato.id} ${prodottoFiltrato.nome} ${prodottoFiltrato.prezzo} ${prodottoFiltrato.gusto} ${prodottoFiltrato.calorie}`;
        div.appendChild(button);
        group.appendChild(div);
    });


        // Aggiunta di eventi click per i pulsanti "Aggiungi al carrello"
      document.querySelectorAll(".cartAdd").forEach(button => {
            button.addEventListener("click", function () {
                const product = JSON.parse(this.getAttribute("data-product"));
                addCart(product);
            });
        });

}
