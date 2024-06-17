
document.addEventListener("DOMContentLoaded", function () {
    document.querySelectorAll(".cartAdd").forEach(button => {
        button.addEventListener("click", function () {
            const product = JSON.parse(this.getAttribute("data-product"));
            addCart(product);
        });
    });
});


function rmvClick() {
    console.log("buttonRemove clicked");
    const id = this.getAttribute("data-product-id");
    removeItem(id);
}

function addCart(product) {
    const xhttp = new XMLHttpRequest();
    const params = new URLSearchParams();
    params.append("action", "add");
    params.append("id", product.id.toString());
    params.append("nome", product.nome.toString());
    params.append("categoria", product.categoria.toString());
    params.append("prezzo", product.prezzo.toString());
    params.append("gusto", product.gusto.toString());

    xhttp.onreadystatechange = function () {
        if (xhttp.readyState === 4) {
            if (xhttp.status === 200) {
                const response = xhttp.responseText;
                try {
                    const cartItems = JSON.parse(response);
                    const cartItemDiv = document.getElementById("listCart");

                    // Pulisce il contenuto precedente
                    cartItemDiv.innerHTML = "";

                    cartItems.forEach(item => {
                        const div = document.createElement("div");
                        div.innerText = `${item.id} ${item.quantity} ${item.prezzo}`;
                        const rmvButton = document.createElement("button");

                        rmvButton.className = "rmvButton";
                        rmvButton.innerText = "Rimuovi Elemento";
                        rmvButton.style.display = "block";
                        rmvButton.style.color = "black";
                        rmvButton.setAttribute("data-product-id", item.id);

                        // Associa l'evento clic al pulsante di rimozione
                        rmvButton.addEventListener("click", rmvClick);

                        div.appendChild(rmvButton);
                        cartItemDiv.appendChild(div);
                    });
                } catch (error) {
                    console.error("Error parsing JSON response:", error);
                    console.log("Response text:", response);
                }
            } else {
                console.error("Network error: " + xhttp.status + " - " + xhttp.statusText);
            }
        }
    };

    xhttp.open("GET", "cartServlet?" + params.toString(), true);
    xhttp.send();
}

function removeItem(id) {
    const xhttp = new XMLHttpRequest();
    const urlParam = new URLSearchParams();
    const action = "remove";

    urlParam.append("action", action);
    urlParam.append("id", id);

    xhttp.onreadystatechange = function () {
        if (xhttp.readyState === 4 && xhttp.status === 200) {
            const response = xhttp.responseText;
            try {
                const cartItems = JSON.parse(response);
                const cartItemDiv = document.getElementById("listCart");

                // Pulisce il contenuto precedente
                cartItemDiv.innerHTML = "";

                cartItems.forEach(item => {
                    const div = document.createElement("div");
                    div.innerText = `${item.id} ${item.quantity} ${item.prezzo}`;
                    const rmvButton = document.createElement("button");
                    rmvButton.className = "rmvButton";
                    rmvButton.innerText = "Rimuovi Elemento";
                    rmvButton.style.display = "block";
                    rmvButton.style.color = "black";
                    rmvButton.setAttribute("data-product-id", item.id);

                    // Associa l'evento clic al pulsante di rimozione
                    rmvButton.addEventListener("click", rmvClick);

                    div.appendChild(rmvButton);
                    cartItemDiv.appendChild(div);
                });
            } catch (error) {
                console.error("Error parsing JSON response:", error);
            }
        }
    }

    xhttp.open("GET", "cartServlet?" + urlParam.toString(), true);
    xhttp.send();
}
