document.addEventListener("DOMContentLoaded", function () {
    let selectTastes = document.getElementById("tastes");
    let selectWeights = document.getElementById("weights");
    let idProdotto = selectTastes.getAttribute("data-product-id");

    selectTastes.onchange = function (){
        updateOptionsProduct(idProdotto, this.value)
            .then(() =>{
                let weightValue = selectWeights.value;
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


function updatePriceProductView(response){
    let price = JSON.parse(response);

    /*
    const divCent = document.querySelector(".centered-div");

    const prezzoSpan = divCent.querySelector(".price-span");
    prezzoSpan.innerHTML = '';
    const prezzo = document.createElement("h3");
    prezzo.className = "current-price";
    prezzo.innerText = `${(price.prezzo * (1-price.sconto/100)).toFixed(2)}`;
    prezzoSpan.appendChild(prezzo);

    if(price.sconto > 0){
        const prezzoOriginale = document.createElement("span");
        prezzoOriginale.className = "original-price";
        prezzoOriginale.innerText = `${price.prezzo}`;
        prezzoSpan.appendChild(prezzoOriginale);
    }
*/


    let productInfo = document.querySelector(".product-info");
    const productRisparmio = productInfo.querySelector(".product-info-risparmio");
    productRisparmio.innerText = "";

    const costoAttuale = productInfo.querySelector(".product-info-costoattuale");
    costoAttuale.innerText = "";

    const costoOriginale = productInfo.querySelector(".product-info-costooriginale");
    costoOriginale.innerText = "";
    if (price.sconto > 0) {
        productRisparmio.innerText = `Risparmia ${(price.prezzo * price.sconto / 100).toFixed(2)} €`;

        costoAttuale.innerText = `${(price.prezzo * (1 - price.sconto / 100)).toFixed(2)} €`;

        costoOriginale.innerText = `Era ${price.prezzo} €`;
    }else{
        const costoAttualeNow = productInfo.querySelector(".product-info-costoattuale");
        costoAttualeNow.innerText = "";
        costoAttualeNow.innerText = `${price.prezzo} €`;
    }
}


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