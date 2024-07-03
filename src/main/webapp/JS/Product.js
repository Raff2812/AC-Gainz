document.addEventListener("DOMContentLoaded", function () {
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