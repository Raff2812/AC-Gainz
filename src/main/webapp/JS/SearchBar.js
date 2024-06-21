document.addEventListener("DOMContentLoaded", function () {
    const input = document.getElementById("search-input");

    input.addEventListener("input", function () {
        const inputValue = this.value;
        searchBar(inputValue);
    });

    input.addEventListener("keypress", function (e){
        if (e.key === 'Enter'){
            const inputValue = this.value;

            window.location.href = "FilterProducts.jsp?name=" + encodeURIComponent(inputValue);
        }
    })
});

function searchBar(inputValue) {
    const urlParams = new URLSearchParams();
    urlParams.append("name", inputValue);

    fetch("searchBar?" + urlParams.toString())
        .then(response => {
            if (!response.ok)
                throw new Error();

            return response.text();
        })
        .then(responseText => {
            updateSearchResults(responseText);
        })
        .catch(error => {
            console.error(error);
        });
}

function updateSearchResults(response) {
    try {
        if (!response) {
            response = '[]';
            console.log("response not ok");
        }

        const productsResult = JSON.parse(response);

        const group = document.getElementById("gr");
        group.innerHTML = "";

        productsResult.forEach(prodottoFiltrato => {
            const button = document.createElement("button");
            button.innerHTML = "Aggiungi al carrello";
            button.className = "cartAdd";


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
    } catch (error) {
        console.error("Error parsing JSON response:", error);
        console.log("Response text:", response);
    }
}
