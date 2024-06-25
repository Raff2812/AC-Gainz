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
        console.log("calling Filter by Name")
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
    const group = document.querySelector("#gr");
    group.innerHTML = "";

    const products = JSON.parse(response);

    products.forEach(prodottoFiltrato => {
        const divProductCard = document.createElement("div");
        divProductCard.className = "product-card";

        const img = document.createElement("img");
        img.src = prodottoFiltrato.immagine;
        img.alt = prodottoFiltrato.nome;

        divProductCard.appendChild(img);

        const divProductInfoName = document.createElement("div");
        divProductInfoName.className = "product-info-name";
        divProductInfoName.innerText = prodottoFiltrato.nome;
        divProductCard.appendChild(divProductInfoName);

        const divProductInfoPrice = document.createElement("div");
        divProductInfoPrice.className = "product-info-price";
        divProductInfoPrice.innerText = prodottoFiltrato.prezzo;
        divProductCard.appendChild(divProductInfoPrice);

        const divProductInfoFlavour = document.createElement("div");
        divProductInfoFlavour.className = "product-info-flavour";
        divProductInfoFlavour.innerText = prodottoFiltrato.gusto;

        divProductCard.appendChild(divProductInfoFlavour);



        const button = document.createElement("button");
        button.innerHTML = "Aggiungi al carrello";
        button.className= "cartAdd";

        button.setAttribute("data-product", prodottoFiltrato.id);


        divProductCard.appendChild(button)

        group.appendChild(divProductCard);
    });

    // Rimuovi tutti i listener click precedenti e aggiungi nuovi listener
    document.querySelectorAll(".cartAdd").forEach(button => {
        button.addEventListener("click", function() {
            const product = this.getAttribute("data-product");
            addCart(product);
        });
    });

}
