function showIntegratori(){
    document.getElementById("lista").innerHTML = `
        <ul>
            <li>
            <a href="index.jsp">goBACK</a>
            </li>
            <li><button onclick="filterCategory('proteine')">Proteine</button></li>
            <li><button onclick="filterCategory('creatina')">Creatina</button></li>
            <li><button onclick="filterCategory('pre-workout')">Pre-Workout</button></li>
        </ul>
    `;

}

document.addEventListener('headerScriptLoaded', () => {
    showIntegratori();
    console.log("showIntegratori script executed");
});