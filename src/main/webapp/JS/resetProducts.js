function resetProducts() {
    console.log("Resetting products...");

    const selects = document.querySelectorAll('select');

    selects.forEach(select => {
        select.value = '';
    });

    genericFilter();
}

document.getElementById("reset-button").addEventListener("click", resetProducts);
