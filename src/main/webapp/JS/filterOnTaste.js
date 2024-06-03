function filterOnTaste(gusto){
    const xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function (){
        if(xhttp.readyState === 4 && xhttp.status === 200){
            const prodottiFiltrati = JSON.parse(xhttp.responseText);


            const group = document.querySelector(".group");
            group.innerHTML = "";

            prodottiFiltrati.forEach(prodottoFiltrato =>{
                const p = document.createElement("p");
                p.innerHTML = prodottoFiltrato.id + " " + prodottoFiltrato.nome + " " + prodottoFiltrato.prezzo + " " + prodottoFiltrato.gusto + " " + prodottoFiltrato.calorie;
                group.appendChild(p);
            })

        }
    }
    xhttp.open("GET", "filterTaste?taste="+gusto, true);
    xhttp.send();
}

document.getElementById('tastes').addEventListener('change', (event) => {
    const selectedTaste = event.target.value;
    console.log('Selected taste:', selectedTaste);
    filterOnTaste(selectedTaste);
});