function showPassword(){
        var passwordField = document.getElementById("password");
        var imgPass = document.getElementById("imgPass");
        if(passwordField.type === "password"){
            passwordField.type = "text";
            imgPass.src = "./Immagini/visible.png";
        }else
        {
            passwordField.type = "password";
            imgPass.src = "./Immagini/hide.png";
        }
}

