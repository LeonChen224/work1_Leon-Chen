<%--
  Created by IntelliJ IDEA.
  User: Leon.Chen1
  Date: 2017/11/27
  Time: 10:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Register</title>
    <link rel="stylesheet" href="css/register.css">
    <script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="js/jquery.md5.js"></script>
    <script type="text/javascript" src="js/register.js"></script>
</head>
<body>
    <div class="signdiv">
        <span class="title">arvato</span>
        <span class="smalltitle">SYSTEMS</span>
    </div>

    <div class="registermsgdiv" style="display: none">
        <span class="registermsgspan" hidden="hidden">注册成功,恭喜您成为arvato会员</span>
    </div>

    <div class="registerdiv">
        <h1>Register</h1>
        <input type="text" class="userphone" placeholder="手机号码" />
        <span class="phoneerror" hidden></span>
        <div class="codediv">
            <input type="text" class="msgcode" placeholder="短信验证码" />
            <button class="getcodebtn">获取短信验证码</button>
        </div>
        <span class="codeerror" hidden></span>

        <div class="imgcodediv" hidden>
            <input type="text" class="imgcodeinput" placeholder="图形验证码" />
            <img src="images/success.png" class="codesuccessimg" hidden>
            <img src="" alt="换一张" class="getCodeImage"/>
        </div>
        <div class="pwddiv">
            <input type="password" class="userpwd" placeholder="密码(至少4位数字)" />
            <img class="pwdsuccessimg" src="images/success.png" hidden />
        </div>
        <span class="errorspan" hidden></span>
        <div class="agreementdiv">
            <input type="checkbox" class="agreecheck">
            <span>我已阅读并同意<a href="#">《用户注册协议》</a></span>
        </div>
        <button class="registerbtn">提交</button>
    </div>
</body>
</html>
