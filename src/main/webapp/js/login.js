$(function () {
    //初始化界面
    var userphone = $.cookie("userphone");//获取cookie中的用户名
    var userpwd = $.cookie("userpwd");//获取cookie中的密码
    console.log("cookie-->"+userphone+","+userpwd);

    //如果用户名和密码存在的话
    if (userphone && userpwd)
    {
        $(".userphone").val(userphone);
        $(".userpwd").val($.base64.decode(userpwd));
        $(".remeberInfo").attr("checked", true);
    }else{
        $(".userphone").val("");
        $(".userpwd").val("");
        $(".remeberInfo").attr("checked", false);
    }

    //检测手机号
    $(".userphone").keyup(function () {
        var userphone = $(".userphone").val();
        if (userphone.length == 11) {
            var bool = validPhone(userphone);
            if(!bool){
                $(".phoneerror").text("*手机号不符合规范");
                $(".phoneerror").show();
            }else{
                $(".phoneerror").text("");
                $(".phoneerror").hide();
                $(".loginbtn").css({"cursor":"pointer"});
                $(".loginbtn").removeAttr("disabled");
            }
        }else if(userphone.length > 11) {
            $(".phoneerror").text("*手机号不符合规范");
            $(".phoneerror").show();
            $(".loginbtn").css({"cursor":"not-allowed"});
            $(".loginbtn").attr("disabled", true);
        }else{
            $(".phoneerror").text("");
            $(".phoneerror").hide();
        }
    });

    //跳转到首页
    $(".signdiv").click(function () {
        window.location.href = "index.jsp";
    });

    $(".loginbtn").click(function () {
        var userphone = $(".userphone").val();
        var userpwd = $(".userpwd").val();
        var ischeck = $(".remeberInfo:checked");

        //判断是否选中了“记住密码”复选框
        if (ischeck && ischeck.length > 0)
        {
            console.log("选中记住");
            //设置cookie7天后到期
            $.cookie("userphone", userphone, { expires: 7 });//调用jquery.cookie.js中的方法设置cookie中的用户名
            $.cookie("userpwd", $.base64.encode(userpwd), { expires: 7 });//调用jquery.cookie.js中的方法设置cookie中的登陆密码，并使用base64（jquery.base64.js）进行加密
        } else {
            $.cookie("userphone", "");
            $.cookie("userpwd", "");
        }

        if(userphone.length > 0 && userpwd.length > 0 && $(".phoneerror").is(":hidden")){
            console.log("填写不为空且手机号符合规范");
            /*var userpwdmd5 = $.md5(userpwd);
            console.log("login userpwdmd5-->"+userpwdmd5);*/
            $.ajax({
                type:"post",
                url:"/user/validUserphone",
                data:{"userphone":userphone},
                async:false,
                success:function (data) {
                    if(data == "phoneExist"){
                        console.log("该手机号已被注册,允许登录");
                         $.ajax({
                            type:"post",
                            url:"/user/login",
                            data:{"userphone":userphone,"userpwd":userpwd},
                             async:false,
                            success:function (data) {
                                console.log("data-->"+data);
                                if(data == "loginSuccess"){
                                    window.location.href = "index.jsp";
                                }else if(data == "loginFail"){
                                    $(".loginerror").text("*对不起，密码错误");
                                    $(".loginerror").show();

                                    $(".userpwd").keyup(function () {
                                        $(".loginerror").text("");
                                        $(".loginerror").hide();
                                    });
                                }else if(data == "phonelock"){
                                    $(".loginerror").text("*对不起，密码连续3次输入错误，该用户已被锁定，24小时后可重新登录");
                                    $(".loginerror").show();
                                }
                            },
                            error:function () {
                                alert("服务器请求错误");
                            }
                        });
                    }else if(data == "phoneNone"){
                        $(".phoneerror").text("*该手机号未注册");
                        $(".phoneerror").show();
                        $(".loginbtn").css({"cursor":"not-allowed"});
                        $(".loginbtn").attr("disabled", true);
                    }
                }
            });
        }else{
            $(".loginerror").text("手机号或密码不能为空");
        }
    });

    //重置密码
    $(".forgetpwd").click(function () {
        var userphone = $(".userphone").val();
        if(userphone.length > 0){
            var msg = "您确定对"+userphone+"账号重置密码吗？";
            if(confirm(msg)){
                $.ajax({
                    type:"get",
                    url:"/mail/sendMail",
                    data:{"userphone":userphone},
                    success:function (data) {
                        console.log(data);
                    },
                    error:function () {
                        alert("服务器请求错误");
                    }
                });
            }
        }else{
            alert("请填写需要重置密码的账号");
        }
    });
});

//检测手机号
function validPhone(userphone) {
    var myreg=/^[1][3,4,5,7,8][0-9]{9}$/;
    if (!myreg.test(userphone)) {
        return false;
    }else {
        return true;
    }
}