function showCondimenti() {
    document.getElementById("lista").innerHTML = `
        <ul>
            <li>
            <a href="index.jsp">goBACK</a>
            </li>
            <li><button onclick="filterCategory('salse')">Salse</button></li>
            <li><button onclick="filterCategory('creme spalmabili')">Creme Spalmabili</button>
            </li>
        </ul>
    `;

}

document.addEventListener('headerScriptLoaded', () => {
        showCondimenti();
        console.log("showCondimenti script executed");
});