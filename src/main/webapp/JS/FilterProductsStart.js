document.addEventListener("DOMContentLoaded", function (){
    startSession();
})

function startSession(){
    fetch("startSession")
        .then(response =>{
            if(!response.ok) throw new Error();

            return response.text();
        })
        .then(responseText =>{
            updateButtonsOnResponse(responseText);
        })
        .catch(error =>{
            console.error(error);
        })
}

function updateButtonsOnResponse(responseText) {
    const productsID = JSON.parse(responseText);
    console.log(productsID);

    const buttons = document.querySelectorAll(".cartAdd");

    for (let productIndex = 0; productIndex < productsID.length; productIndex++) {
        const button = buttons[productIndex]; // Ottieni il pulsante corrispondente all'indice
        const product = productsID[productIndex]; // Ottieni l'oggetto prodotto corrente

        button.setAttribute("data-product", product["id" + productIndex]); // Imposta l'attributo data-product con l'ID del prodotto
    }
}


