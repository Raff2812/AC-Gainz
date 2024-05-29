function filterCategory(category){

    const xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function (){
        if(xhttp.readyState === 4 && xhttp.status === 200){
            const prodotti = JSON.parse(xhttp.responseText);

            const group = document.querySelector(".group");
            group.innerHTML = '';

            prodotti.forEach(prodotto => {
                const p = document.createElement("p");
                p.innerText = prodotto.nome;
                group.appendChild(p);
            })
        }
    }

    xhttp.open("GET", "filter?category="+category, true);
    xhttp.send();

}