<%--
  Created by IntelliJ IDEA.
  User: Leon.Chen1
  Date: 2017/11/24
  Time: 16:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="css/login.css" />
    <script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="js/jquery.md5.js"></script>
    <script type="text/javascript" src="js/jquery.base64.js"></script>
    <script type="text/javascript" src="js/jquery.cookie.js"></script>
    <script type="text/javascript" src="js/jquery.md5.js"></script>
    <script type="text/javascript" src="js/login.js"></script>
</head>
<body>
    <div class="signdiv">
        <span class="title">arvato</span>
        <span class="smalltitle">SYSTEMS</span>
    </div>

    <div class="logindiv">
        <h1>Login</h1>
        <input type="text" class="userphone" placeholder="请输入手机号" />
        <span class="phoneerror"></span>
        <input type="password" class="userpwd" placeholder="请输入密码" />
        <span class="loginerror"></span>
        <div class="linediv">
            <div class="rememberpwddiv">
                <input type="checkbox" class="rememberInfo" />
                <span>记住用户名和密码</span>
            </div>
            <a href="register.jsp">快速注册</a>
            <a class="forgetpwd">忘记密码</a>
        </div>

        <button class="loginbtn">登录</button>
    </div>
</body>
</html>
