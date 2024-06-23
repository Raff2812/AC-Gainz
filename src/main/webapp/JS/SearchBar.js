document.addEventListener("DOMContentLoaded", function () {
    const input = document.getElementById("search-input");

    input.addEventListener("input", function () {
        const inputValue = this.value;
        if (window.location.pathname.includes("categories") || window.location.pathname.includes("FilterProducts"))
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
    console.log("searching with ajax by searchBar input");

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


            //const product = JSON.stringify(prodottoFiltrato);



            button.setAttribute("data-product", prodottoFiltrato.id);


            button.onclick = function() {
                addCart(button.getAttribute("data-product"));
            };


            const div = document.createElement("div");
            div.innerHTML = `${prodottoFiltrato.id} ${prodottoFiltrato.nome} ${prodottoFiltrato.prezzo} ${prodottoFiltrato.gusto} ${prodottoFiltrato.calorie}`;
            div.appendChild(button);
            group.appendChild(div);
        });


    } catch (error) {
        console.error("Error parsing JSON response:", error);
    }
}
