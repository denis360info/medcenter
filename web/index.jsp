<%-- 
    Document   : index
    Created on : 26.04.2018, 23:30:39
    Author     : ����
--%>

<%@page contentType="text/html" pageEncoding="windows-1251"%>
<!DOCTYPE html>
<html>
    <head>
        <title>TODO supply a title</title>
        <meta charset="windows-1251">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
         <link href="css/main_style.css" rel="stylesheet" type="text/css"/>
         <link href="css/style.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
               

<nav class="top-menu">
  <a class="navbar-logo" href=""></a>
  <ul class="menu-main">
    <li> <a href="/MedCenterMarchenko/login/login" />Login</a></li>
    <li><a href="/MedCenterMarchenko/about.jsp">About</a></li>
    <li><a href="">Story</a></li>
    <li><a href="">Shop</a></li>
    <li><a href="">Gallery</a></li>
    <li><a href="">Contact</a></li>
  </ul>
</nav>
        <div class="modal" id="signin">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="container">
                <div class="row">

                    <div class="col-md-6">
                        <form class="form-horizontal" method="post" action="/MedCenterMarchenko/site/sign_in">
                            <span class="heading">�����������</span>
                            <div class="form-group">
                                <input type="text" class="form-control" id="inputLogin"
                                       placeholder="Login" name="login_in">
                                <i class="fa fa-user"></i>
                            </div>
                            <div class="form-group help">
                                <input type="password" class="form-control" id="inputPassword"
                                       placeholder="Password" name="password_in">
                                <i class="fa fa-lock"></i>
                                <a href="#" class="fa fa-question-circle"></a>
                            </div>
                            <div class="form-group">
                                <div class="main-checkbox">
                                    <input type="checkbox" value="none" id="checkbox1" name="check"/>
                                    <label for="checkbox1"></label>
                                </div>
                                <span class="text">�������������</span>
                                <button type="submit" class="btn btn-default">����</button>
                            </div>
                        </form>
                    </div>
                </div><!-- /.row -->
            </div><!-- /.container -->
        </div>
    </div>
</div>
    </body>
</html>

