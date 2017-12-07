<%--
  Created by IntelliJ IDEA.
  User: Leon.Chen1
  Date: 2017/11/27
  Time: 10:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<html>
<head>
    <title>index</title>
    <link rel="stylesheet" href="css/index.css">
    <script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="js/index.js"></script>
</head>
<body>
<c:set var="userphone" scope="session" value="${sessionScope.userphone}"/>
<c:set var="status" scope="session" value="${sessionScope.status}"/>
<input type="text" class="userphone" value="<%=session.getAttribute("userphone")%>" hidden>

    <div class="navdiv">
        <div class="signdiv">
            <span class="title">arvato</span>
            <span class="smalltitle">SYSTEMS</span>
        </div>
      <span class="maintitle">消息列表</span>
      <div class="logAndregistdiv">
          <c:if test="${not empty userphone}">
              <span><%=session.getAttribute("userphone")%></span>
              <a class="logoutbtn">注销</a>
          </c:if>
          <c:if test="${empty userphone}">
              <a href="login.jsp">login</a>
              <a href="register.jsp">register</a>
          </c:if>
      </div>
    </div>

    <div class="containerdiv">
        <c:if test="${userphone == null}">
            <div class="nologindiv">
                <span class="nologinspan">对不起，您暂时还未登录！</span>
            </div>
        </c:if>
        <c:if test="${userphone != null}">
            <div class="newsdiv">
                <div class="markdiv" hidden></div>

                <!-- 显示新增页面 -->
                <div class="adddiv" style="display: none">
                    <div class="edithead">
                        <span>新增</span>
                        <button class="savebtn">提交</button>
                    </div>
                    <hr class="line" />
                    <div class="editcontent">
                        <div class="addiddiv">
                            <span>消息序号:</span>
                            <input type="text" class="addcid" />
                        </div>
                        <div class="editnamediv">
                            <span>消息标题:</span>
                            <input type="text" class="addcname" />
                        </div>
                        <div class="edittimediv">
                            <span>消息时间:</span>
                            <input type="date" class="addctime" value="" />
                        </div>
                        <div class="editcontentdiv">
                            <span>消息正文:</span>
                            <textarea class="addcontent"></textarea>
                        </div>
                    </div>
                </div>

                <!-- 显示编辑页面 -->
                <div class="editdiv" style="display: none">
                    <div class="edithead">
                        <span>修改</span>
                        <button class="updatebtn">确定</button>
                    </div>
                    <hr class="line" />
                    <div class="editcontent">
                        <div class="editnamediv">
                            <span>消息标题:</span>
                            <input type="text" class="editcname" />
                        </div>
                        <div class="edittimediv">
                            <span>消息时间:</span>
                            <input type="date" class="editctime" value="" />
                        </div>
                        <div class="editcontentdiv">
                            <span>消息正文:</span>
                            <textarea class="editcontent" id="editcontent"></textarea>
                        </div>

                    </div>
                </div>

                <!-- 显示消息页面 -->
                <div class="contentdiv" style="display: none;">
                    <h2 class="titleh2"></h2>
                    <span class="ctimespan"></span>
                    <div class="detaildiv"></div>
                    <button class="backbtn">关闭</button>
                </div>

                <button class="addbtn">新增消息</button>
                <table class="newstable">
                </table>
            </div>
        </c:if>
    </div>

</body>
</html>
