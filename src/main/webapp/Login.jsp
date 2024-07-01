<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
    <title>Login Form</title>
    <link rel="icon" type="image/x-icon" href="Immagini/favicon.ico">


    <%
        if (session.getAttribute("Utente")!=null)
            response.sendRedirect("index.jsp");
    %>

    <style>
        @import url('https://fonts.googleapis.com/css2?family=Inter:ital,wght@0,100;0,300;0,400;0,500;0,700;0,900&display=swap');
        * {
            font-family: "Inter";
        }

        body {
            display: flex;
            background-image: url("./Immagini/Drawing.png");
            background-repeat: no-repeat;
            height: fit-content;
            background-size: cover;
        }

        .login-contenitore {
            width: 420px;
            border: 2px solid black;
            backdrop-filter: blur(40px);
            box-shadow: 0 0 5px black;
            color: white;
            border-radius: 18px;
            margin: 10px 0 10px 50px;
            padding: 30px 40px;
        }

        .login-contenitore h1 {
            font-size: 38px;
            text-align: center;
            color: black;
        }

        .login-contenitore .input-box {
            position: relative;
            width: 100%;
            height: 50px;
            background: white;
            margin: 30px 0;
            border-radius: 40px;
        }

        .input-box input {
            width: 100%;
            height: 100%;
            background: transparent;
            outline: none;
            border: 1px solid black;
            border-radius: 40px;
            padding: 20px 45px 20px 20px;
        }

        .input-box input::placeholder {
            color: black;
        }

        .input-box img {
            position: absolute;
            top: 50%;
            left: 85%;
            transform: translateY(-50%);
            cursor: pointer;
            width: 30px;
        }

        .login-contenitore .ricordami-passdim {
            display: flex;
            justify-content: space-between;
            color: white;
            font-size: 15px;
            margin: -15px 0 15px;
        }

        .ricordami-passdim a {
            color: white;
            text-decoration: none;
            transition: 0.2s;
        }

        .ricordami-passdim a:hover {
            color: orangered;
        }

        .login-contenitore .submit-button {
            width: 100%;
            height: 45px;
            background: orangered;
            outline: none;
            border-radius: 40px;
            border: 1px solid black;
            cursor: pointer;
            font-size: 20px;
            color: white;
            font-weight: 500;
            transition: 0.2s;
        }

        .login-contenitore .submit-button:hover {
            color: black;
        }

        .login-contenitore .registrazione {
            font-size: 14px;
            text-align: center;
            margin: 20px 0 15px;
            color: white;
        }

        .registrazione p a {
            color: white;
            text-decoration: none;
            font-weight: 600;
            transition: 0.2s;
        }

        .registrazione p a:hover {
            color: orangered;
        }

        .login-contenitore .errorMessage {
            color: black;
            font-size: 30px;
            margin-left: 40px;
        }
    </style>



</head>
<body>

<%
    String err = request.getParameter("err");
%>

<script defer src="JS/togglePassword.js"></script>
<script defer src="JS/validateForm.js"></script>
<div class="login-contenitore">
    <form action="login" method="post" autocomplete="on">
        <h1>Accedi</h1>
        <div class="input-box">
            <label for="email"></label><input type="email" name="email" id="email" placeholder="Email" required>
        </div>
        <div class="input-box">
            <label for="password"></label>
            <input type="password" name="password" id="password" placeholder="Password" required>
            <img src="Immagini/hide.png" alt="hide png" id="imgPass" onclick="togglePassword('password', 'imgPass')">
        </div>
        <div class="ricordami-passdim">
            <label><input type="checkbox">Ricordami</label>
            <a href="">Password dimenticata?</a>
        </div>

        <button type="submit" class="submit-button">Login</button>

        <div class="registrazione">
            <p>Non hai ancora un account?
                <a href="Registrazione.jsp">Registrami</a>
            </p>
        </div>
    </form>

    <div class="errorMessage" id="errorMessage"></div>
</div>

<script>
    document.addEventListener("DOMContentLoaded", function (){
        var errorMessage = "<%= err %>";
        console.log("Error Message: " + errorMessage);

        if (errorMessage && errorMessage.trim() !== "") {
            showError(errorMessage);
        }

        // Rimuovi il parametro `err` dalla query string senza ricaricare la pagina
        var newURL = window.location.protocol + "//" + window.location.host + window.location.pathname;
        window.history.replaceState({ path: newURL }, "", newURL);
    });

    function showError(errorMessage) {
        var errorMsgElement = document.getElementById("errorMessage");
        errorMsgElement.style.display = "block";

        switch (errorMessage) {
            case "patternPassword":
                errorMsgElement.innerHTML = "Password non valida. Deve contenere almeno 8 caratteri, un numero, una lettera maiuscola, una lettera minuscola e un carattere speciale.";
                break;
            case "patternMail":
                errorMsgElement.innerHTML = "Email non valida. Inserisci un'email valida.";
                break;
            case "UtenteNonRegistrato":
                errorMsgElement.innerHTML = "Utente non registrato.";
                break;
            case "PasswordSbagliata":
                errorMsgElement.innerHTML = "Password Sbagliata.";
                break;
            default:
               ;
        }
    }
</script>

</body>
</html>
