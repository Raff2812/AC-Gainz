function showTastes(){
    const xhttp = new XMLHttpRequest();
    const select = document.querySelector("#tastes");
    const currentSelection = select.value;

    xhttp.onreadystatechange = function (){
        if(xhttp.readyState === 4 && xhttp.status === 200){
            const gusti = JSON.parse(xhttp.responseText);

            select.innerHTML = "<option value=''>Seleziona un gusto</option>"; // Default option

            gusti.forEach(gusto=>{
                const option = document.createElement("option");
                option.value = gusto;
                option.innerHTML = gusto;
                select.appendChild(option);
            });

            // Ripristina il valore selezionato, se esiste
            if (currentSelection) {
                select.value = currentSelection;
            }
        }
    };

    xhttp.open("GET", "showTastes", true);
    xhttp.send();
}

document.addEventListener("DOMContentLoaded", function() {
    console.log("DOM fully loaded and parsed");

    document.getElementById('tastes').addEventListener('click', showTastes)
});
