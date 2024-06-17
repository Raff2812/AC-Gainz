//Script per le tab laterali

function opentab(cityName)
{
    var i, tabcontent, tablinks;
    tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++)
    {
        tabcontent[i].style.display = "none";
    }
    tablinks = document.getElementsByClassName("tablinks");
    for (i = 0; i < tablinks.length; i++)
    {
        tablinks[i].className = tablinks[i].className.replace("active", "");
    }
    document.getElementById(cityName).style.display = "block";
    this.currentTarget.className += " active";
}


function displayAll(idx){
    var x = document.getElementById(idx);

    if(x.style.display === "none") x.style.display = "block";
    else x.style.display = "none";
}




/*
function genericEdit(field, value){
    const xhttp = new XMLHttpRequest();

    const address = "edit";

    xhttp.onreadystatechange = function (){
        if(xhttp.readyState === 4 && xhttp.status === 200){
            //gestire errori o successi
        }
    }


    xhttp.open("POST", address,true);

    xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

    value.append('action', )
    xhttp.send();
}
*/


/*
document.addEventListener("DOMContentLoaded", function (){
    const forms = document.querySelectorAll("form");

    forms.forEach(form =>{
        form.addEventListener("submit", function (event){
            event.preventDefault();

            const formData = new FormData(form);

            const action = formData.get('action');

            genericEdit(action, formData)
        })
    })
})*/



/*

//script per mostrare la div del singolo campo di modifica
function displaydivpass() {
    var x = document.getElementById("passdiv");
    if (x.style.display === "none")
    {
        x.style.display = "block";
    }
    else
    {
        x.style.display = "none";
    }
}
function displaydivind() {
    var x = document.getElementById("inddiv");
    if (x.style.display === "none")
    {
        x.style.display = "block";
    }
    else
    {
        x.style.display = "none";
    }
}

function displaydivnumtel() {
    var x = document.getElementById("numteldiv");
    if (x.style.display === "none")
    {
        x.style.display = "block";
    }
    else
    {
        x.style.display = "none";
    }
}

function displaydivnome() {
    var x = document.getElementById("nomediv");
    if (x.style.display === "none")
    {
        x.style.display = "block";
    }
    else
    {
        x.style.display = "none";
    }
}

function displaydivcognome() {
    var x = document.getElementById("cognomediv");
    if (x.style.display === "none")
    {
        x.style.display = "block";
    }
    else
    {
        x.style.display = "none";
    }
}

function displaydivddn() {
    var x = document.getElementById("ddndiv");
    if (x.style.display === "none")
    {
        x.style.display = "block";
    }
    else
    {
        x.style.display = "none";
    }
}

function displaydivcodfis() {
    var x = document.getElementById("codfisdiv");
    if (x.style.display === "none")
    {
        x.style.display = "block";
    }
    else
    {
        x.style.display = "none";
    }
}*/