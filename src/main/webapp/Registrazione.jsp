<%--
  Created by IntelliJ IDEA.
  User: luigiauriemma
  Date: 17/05/24
  Time: 10:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html><head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Centered Form</title>
    <style>
        body, html {
            height: 100%;
            margin: 0;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .container {
            display: flex;
            justify-content: center;
            align-items: center;
            width: 100%;
            height: 100%;
        }
        form {
            background-color: #f3f3f3;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
    </style>
<body>
<div class="container">
<form action="register" method="post">
    <input type="email" name="email" id="email" placeholder="Email" required><br>
    <input type="password" name="password" id="password" placeholder="Password" required><br>
    <input type="text" name="nome" id="nome" placeholder="Nome" required><br>
    <input type="text" name="cognome" id="cognome" placeholder="Cognome" required><br>
    <input type="text" name="codiceFiscale" id="codiceFiscale" placeholder="Codice Fiscale" required><br>
    <input type="date" name="dataDiNascita" id="dataDiNascita" placeholder="Data di nascita" required><br>
    <input type="text" name="indirizzo" id="indirizzo" placeholder="Indirizzo" required><br>
    <input type="text" name="numCellullare" id="numCellulare" placeholder="numCellulare" required><br>
    <input type="submit" >
</form>
</div>
</body>
</html>
