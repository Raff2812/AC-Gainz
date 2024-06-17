function togglePassword(idInput, idImg){
        var passwordField = document.getElementById(idInput);
        var imgPass = document.getElementById(idImg);
        if(passwordField.type === "password"){
            passwordField.type = "text";
            imgPass.src = "Immagini/visible.png"
        }else
        {
            passwordField.type = "password";
            imgPass.src = "Immagini/hide.png";
        }
}

