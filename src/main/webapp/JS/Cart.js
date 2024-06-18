document.addEventListener("DOMContentLoaded", function () {
    // Aggiunta di eventi click per i pulsanti "Aggiungi al carrello"
    document.querySelectorAll(".cartAdd").forEach(button => {
        button.addEventListener("click", function () {
            const product = JSON.parse(this.getAttribute("data-product"));
            addCart(product);
        });
    });

    // Aggiunta dell'evento click per il carrello
    document.getElementById("cart").addEventListener("click", function () {
        toggleCartVisibility();
    });
});

function toggleCartVisibility() {
    const listCart = document.getElementById("listCart");
    if (listCart) {
        listCart.classList.toggle("hidden");
        if (!listCart.classList.contains("hidden")) {
            showCart();
        }
    } else {
        console.error("Element with ID 'listCart' not found");
    }
}

function rmvClick() {
    console.log("buttonRemove clicked");
    const id = this.getAttribute("data-product-id");
    removeItem(id);
}

function addCart(product) {
    const params = new URLSearchParams();
    params.append("action", "add");
    params.append("id", product.id.toString());
    params.append("nome", product.nome.toString());
    console.log(params.get("nome"));
    params.append("categoria", product.categoria.toString());
    params.append("prezzo", product.prezzo.toString());
    params.append("gusto", product.gusto.toString());

    fetch("cartServlet?" + params.toString())
        .then(response => {
            if (!response.ok) {
                throw new Error(`Network error: ${response.status} - ${response.statusText}`);
            }
            return response.text();
        })
        .then(responseText => {
            updateCartView("add", responseText);
        })
        .catch(error => {
            console.error(error);
        });
}

function removeItem(id) {
    const urlParam = new URLSearchParams();
    urlParam.append("action", "remove");
    urlParam.append("id", id);

    fetch("cartServlet?" + urlParam.toString())
        .then(response => {
            if (!response.ok)
                throw new Error(`Network error: ${response.status} - ${response.statusText}`);

            return response.text();
        })
        .then(responseText => {
            updateCartView("remove", responseText);
        })
        .catch(error => {
            console.error(error);
        });
}

function showCart() {
    const urlParam = new URLSearchParams();
    urlParam.append("action", "show");

    fetch("cartServlet?" + urlParam.toString())
        .then(response => {
            if (!response.ok) {
                throw new Error(`Network error: ${response.status} - ${response.statusText}`);
            }

            return response.text();
        })
        .then(responseText => {
            updateCartView("show", responseText);
        })
        .catch(error => {
            console.error(error);
        });
}

function updateCartView(action, response) {
    try {
        if (!response) {
            response = '[]';
        }
        const cartItems = JSON.parse(response);
        const cartItemDiv = document.getElementById("listCart");

        if (!cartItemDiv) {
            console.error("Element with ID 'listCart' not found");
            return;
        }

        // Clear the previous content
        cartItemDiv.innerHTML = "";

        if (cartItems.length === 0) {
            const emptyMessage = document.createElement("div");
            emptyMessage.innerText = "Il carrello è vuoto.";
            cartItemDiv.appendChild(emptyMessage);
        } else {
            cartItems.forEach(item => {
                const div = document.createElement("div");
                div.innerText = `${item.id} ${item.quantity} ${item.prezzo}`;
                const rmvButton = document.createElement("button");

                rmvButton.className = "rmvButton";
                rmvButton.innerText = "Rimuovi Elemento";
                rmvButton.style.display = "block";
                rmvButton.style.color = "black";
                rmvButton.setAttribute("data-product-id", item.id);

                // Associate click event to the remove button
                rmvButton.addEventListener("click", rmvClick);

                div.appendChild(rmvButton);
                cartItemDiv.appendChild(div);
            });
        }

        // Calculate the total quantity
        let totalQuantity = 0;
        let totalPrice = 0;
        for (let i = 0; i < cartItems.length; i++) {
            totalQuantity += cartItems[i].quantity;
            totalPrice += cartItems[i].prezzo;
        }

        // Update cart counter
        const cartElement = document.getElementById("cart");
        if (!cartElement) {
            console.error("Element with ID 'cart' not found");
            return;
        }
        cartElement.innerHTML = `Carrello (${totalQuantity})`;

        const totalPriceDiv = document.createElement("h3");
        totalPriceDiv.innerText = `Totale carrello: ${totalPrice}`;
        cartItemDiv.appendChild(totalPriceDiv);

    } catch (error) {
        console.error("Error parsing JSON response:", error);
        console.log("Response text:", response);
    }
}
