<%@ page session="false"%>
<%@ page pageEncoding="UTF-8" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><!DOCTYPE html>
<!DOCTYPE html>
<html>
    <head>
        <title>Регистрация в Медицинском центре</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" pageEncoding="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
         <link href="../css/main_style.css" rel="stylesheet" type="text/css"/>
         <link href="../css/style.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <h1>Registration</h1>
        <form method="post" action="/MedCenterMarchenko/site/registration">
            <span>Введите данные</span><br>
            <span>Логин</span><input type="text" id="inputLogin" placeholder="Login" name="login_in"><br>
            <span>Password</span><input type="password" id="inputPassword" placeholder="Password" name="password_in"><br>
            <span>Name</span><input type="text" id="inputLogin" placeholder="Name" name="name"><br>
            <span>Surname</span><input type="text" id="inputLogin" placeholder="Surname" name="surname"><br>
            <span>Patronymic</span><input type="text" id="inputLogin" placeholder="Patronymic" name="patronymic"><br>
            <span>Telephone</span><input type="text" id="inputLogin" placeholder="Telephone" name="telephone"><br>
            <span>E-mail</span><input type="text" id="inputLogin" placeholder="Email" name="email"><br>
            <button type="submit">Registration</button><br>
        </form>
    </body>
</html>
