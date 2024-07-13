document.addEventListener("DOMContentLoaded", function () {
    let selectTastes = document.getElementById("tastes");
    let selectWeights = document.getElementById("weights");
    let idProdotto = selectTastes.getAttribute("data-product-id");

    selectTastes.onchange = function (){
        updateOptionsProduct(idProdotto, this.value)
            .then(() =>{
                updatePriceProduct(idProdotto, selectTastes.value, selectWeights.value);
            })
            .catch(error => {
                console.error(error);
            });
    }

    let cartAdd = document.querySelector(".cartAddProduct");
    cartAdd.onclick = function (){
        let quantityInput = document.getElementById("quantitynumber").value;
        console.log(quantityInput);
        addCartVariant(idProdotto, quantityInput, selectTastes.value, selectWeights.value);
    }

    selectWeights.onchange = function (){
        let flavourValue = selectTastes.value;
        updatePriceProduct(idProdotto, flavourValue, this.value);
    }

    // Codice per gestire l'accordion della descrizione prodotto
    var acc = document.getElementsByClassName("product-description-accordion");
    for (var i = 0; i < acc.length; i++) {
        acc[i].addEventListener("click", function () {
            this.classList.toggle("active");
            var panel = this.nextElementSibling;
            if (panel.style.display === "block") {
                panel.style.display = "none";
            } else {
                panel.style.display = "block";
            }
        });
    }
});



function updatePriceProduct(idProdotto, flavour, weight){
    console.log(idProdotto);
    console.log(flavour);
    console.log(weight);
    let urlSearch = new URLSearchParams();
    urlSearch.append("idProdotto", idProdotto);
    urlSearch.append("flavour", flavour);
    urlSearch.append("weight", weight);
    urlSearch.append("action", "updatePrice");


    fetch("showOptions?"+urlSearch.toString())
        .then(response =>{
            if(!response.ok) throw new Error(`Network Error: ${response.status} - ${response.statusText}`);

            return response.text();
        })
        .then(responseText =>{
            updatePriceProductView(responseText);
        })
        .catch(error =>{
            console.error(error);
        })
}

function updatePriceProductView(response) {
    let price = JSON.parse(response);

    let productInfo = document.querySelector(".product-info");
    if (!productInfo) return;

    if (price.sconto > 0) {
        let productRisparmio = productInfo.querySelector(".product-info-risparmio");
        if (!productRisparmio) {
            productRisparmio = document.createElement("div");
            productRisparmio.className = "product-info-risparmio";
        }

        productRisparmio.innerText = `Risparmia ${(price.prezzo * price.sconto / 100).toFixed(2)} €`

        let productCostoAttuale = productInfo.querySelector(".product-info-costoattuale");
        if (!productCostoAttuale) {
            productCostoAttuale = document.createElement("span");
            productCostoAttuale.className = "product-info-costoattuale";
        }

        productCostoAttuale.innerText = `${(price.prezzo * (1 - price.sconto / 100)).toFixed(2)}€`;

        let productCostoOriginale = productInfo.querySelector(".product-info-costooriginale");
        if (!productCostoOriginale) {
            productCostoOriginale = document.createElement("span");
            productCostoOriginale.className = "product-info-costooriginale";

            productInfo.prepend(productRisparmio, productCostoAttuale, productCostoOriginale)
        }

        productCostoOriginale.innerText = ` Era ${price.prezzo} €`;
    } else {
        let productCostoAttuale = productInfo.querySelector(".product-info-costoattuale");
        if (!productCostoAttuale) {
            productCostoAttuale = document.createElement("span");
            productCostoAttuale.className = "product-info-costoattuale";
            productInfo.prepend(productCostoAttuale);
        }

        productCostoAttuale.innerText = `${(price.prezzo * (1 - price.sconto / 100)).toFixed(2)} €`;

        let productRisparmio = productInfo.querySelector(".product-info-risparmio");
        let productCostoOriginale = productInfo.querySelector(".product-info-costooriginale");
        if (productRisparmio) productRisparmio.remove();
        if (productCostoOriginale) productCostoOriginale.remove();

    }

}



























    /*
    // Gestisci il risparmio
    let productRisparmio = productInfo.querySelector(".product-info-risparmio");
    if (!productRisparmio) {
        productRisparmio = document.createElement("div");
        productRisparmio.className = "product-info-risparmio";
        productInfo.prepend(productRisparmio);  // Aggiungi all'inizio di productInfo
    }
    productRisparmio.innerText = "";

    // Gestisci il costo attuale
    let costoAttuale = productInfo.querySelector(".product-info-costoattuale");
    if (!costoAttuale) {
        costoAttuale = document.createElement("span");
        costoAttuale.className = "product-info-costoattuale";
        productInfo.appendChild(costoAttuale);
    }
    costoAttuale.innerText = "";

    // Gestisci il costo originale
    let costoOriginale = productInfo.querySelector(".product-info-costooriginale");
    if (!costoOriginale) {
        costoOriginale = document.createElement("span");
        costoOriginale.className = "product-info-costooriginale";
    }
    costoOriginale.innerText = "";

    if (price.sconto > 0) {
        productRisparmio.innerText = `Risparmia ${(price.prezzo * price.sconto / 100).toFixed(2)} €`;

        costoAttuale.innerText = `${(price.prezzo * (1 - price.sconto / 100)).toFixed(2)} €`;

        costoOriginale.innerText = `Era ${(price.prezzo).toFixed(2)} €`;
    } else {
        costoAttuale.innerText = `${price.prezzo.toFixed(2)} €`;
        costoOriginale.innerText = "";  // Rimuovi il testo se non c'è sconto
        if (productRisparmio) {
            productRisparmio.remove();  // Rimuovi l'elemento risparmio se non c'è sconto
        }
    }*/

function updateOptionsProduct(idProdotto, value) {
    let urlSearch = new URLSearchParams();
    urlSearch.append("action", "updateOptions");
    urlSearch.append("flavour", value);
    urlSearch.append("idProdotto", idProdotto);
    return fetch("showOptions?" + urlSearch.toString()) // Ensure return of promise
        .then(response => {
            if (!response.ok) throw new Error(`Network error: ${response.status} - ${response.statusText}`);
            return response.text();
        })
        .then(responseText => {
            updateWeightOptionsProduct(responseText);
        });
}
function updateWeightOptionsProduct(response){
    let weights = JSON.parse(response);

    let productInfo = document.querySelector(".product-info-peso");
    let selectWeights = productInfo.querySelector("#weights");
    selectWeights.innerHTML = "";
    /*
    let divCent = document.querySelector(".centered-div");
    let weightSelect = divCent.querySelector(".weight-select");
    weightSelect.innerHTML = '';
    */
    weights.forEach(weight =>{
        const weightOption = document.createElement("option");
        weightOption.innerText = `${weight.peso}`;
        selectWeights.appendChild(weightOption);
    })
}

/*
 // Codice per gestire il pulsante "Aggiungi al Carrello"
    document.querySelectorAll(".cartAddProduct").forEach(button => {
        button.addEventListener("click", function () {
            const quantity = document.getElementById("quantitynumber").value;
            console.log(quantity);
            if (quantity > 0) {
                const id = this.getAttribute("data-product");
                addCart(id, quantity);
            }
        });
    });
 */