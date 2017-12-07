<%--
  Created by IntelliJ IDEA.
  User: Leon.Chen1
  Date: 2017/11/27
  Time: 16:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>test</title>
    <script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="js/jquery.md5.js"></script>
    <script type="text/javascript" src="js/test.js"></script>
</head>
<body>
    <input type="button" id="btn" value="Submit" />

    <input type="password" name="pass" id="pass" />
    <span id="passstrength"></span>

    <img src="/utils/getCodeImage" alt="换一张" style="cursor: hand" class="getCodeImage"/>
</body>
</html>
