document.addEventListener("DOMContentLoaded", function () {
    // Aggiunta dell'evento click per il carrello
    document.getElementById("cart").addEventListener("click", function () {
        toggleCartVisibility();
    });

    // Carica il contenuto del carrello al caricamento della pagina
    showCart();
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

function addCart(idProdotto, quantity) {
    const params = new URLSearchParams();
    params.append("action", "add");
    params.append("id", idProdotto);
    if (quantity)
        params.append("quantity", quantity);

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
            showCart();

            if (window.location.pathname.includes("cart")){
                console.log("Stong ndo cart")
                showCartCheckOut();
            }
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

            if (window.location.pathname.includes("Carrello"))
                updateCartJSP(responseText);
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

        console.log(cartItems);
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

            // Reset cart total to zero
            const cartElement = document.getElementById("cart");

            cartElement.innerHTML = "Carrello (0)";

            return; // Exit early if cart is empty
        }

        let totalQuantity = 0;
        let totalPrice = 0;

        // Loop through the cartItems except the last element which is totalPrice
        for (let i = 0; i < cartItems.length - 1; i++) {
            const item = cartItems[i];
            const div = document.createElement("div");
            div.innerText = `${item.nome} ${item.quantity} ${item.prezzo}`;
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

            totalQuantity += item.quantity;
        }

        // The last item is the totalPrice object
        const totalPriceItem = cartItems[cartItems.length - 1];
        totalPrice = totalPriceItem.totalPrice;

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


        const goToCheckOutForm = document.createElement("form");
        goToCheckOutForm.method = "POST";
        goToCheckOutForm.action = "cart";



        const goToCheckOut = document.createElement("button");
        goToCheckOut.type = "submit";
        goToCheckOut.className = "checkOut";
        goToCheckOut.innerText = "Vai al CheckOut";

        goToCheckOutForm.appendChild(goToCheckOut);

        cartItemDiv.appendChild(goToCheckOutForm);


    } catch (error) {
        console.error("Error parsing JSON response:", error);
        console.log("Response text:", response);
    }
}


