$(function () {
    //火狐需要清空一下
    $(".userphone").val("");
    $(".pwddiv .userpwd").val("");

    var numberCode = "";

    //点击标识跳到首页
    $(".signdiv").click(function () {
        window.location.href = "index.jsp";
    });

    //检测手机号是否注册过
    $(".userphone").keyup(function () {
       var userphone = $(".userphone").val();
       if(userphone.length == 11){
           $.ajax({
              type:"post",
              url:"/user/validUserphone",
              data:{"userphone":userphone},
              success:function (data) {
                  if(data == "phoneExist"){
                      console.log("该手机号已被注册");
                      $(".phoneerror").text("*该手机号已被注册,可直接进入首页登录");
                      $(".phoneerror").show();
                      $(".registerbtn").css({"cursor":"not-allowed"});
                      $(".getcodebtn").attr("disabled", true);
                  }else if(data == "phoneNone"){
                      $(".phoneerror").text("");
                      $(".phoneerror").hide();
                  }
              }
           });
       }else if(userphone.length > 11){
           $(".phoneerror").text("*手机号不符合规范");
           $(".phoneerror").show();
           $(".registerbtn").css({"cursor":"not-allowed"});
           $(".getcodebtn").attr("disabled", true);
       }else{
           $(".phoneerror").text("");
           $(".phoneerror").show();
           $(".registerbtn").css({"cursor":"pointer"});
           $(".getcodebtn").removeAttr("disabled");
       }
    });

    //获取短信验证码
    $(".getcodebtn").click(function () {
        console.log("getcodebtn");
        var userphone = $(".userphone").val();
        console.log("userphone-->"+userphone);

        if(userphone != null && userphone != ""){
            console.log("userphone is not null");
            $(".phoneerror").hide();
            var bool = validPhone(userphone);
            console.log("bool-->"+bool);

            if(!bool){
                $(".phoneerror").text("*手机号输入不正确");
                $(".phoneerror").show();
                return;
            }
            $.ajax({
                type:"post",
                url:"/user/getNumberCode",
                success:function(data){
                    numberCode = data;
                    console.log("6位随机数："+data);

                    var count = 10;
                    var countdown = setInterval(CountDown, 1000);
                    function CountDown() {
                        $(".getcodebtn").text(count+"s后可重发");
                        $(".getcodebtn").attr("disabled", true);
                        if (count == -1) {
                            $(".getcodebtn").text("获取短信验证码").removeAttr("disabled");
                            clearInterval(countdown);
                            numberCode = "";
                        }
                        count--;
                    }
                },
                error:function () {
                    alert("服务器请求错误");
                }
            });
        }else{
            console.log("userphone is null");
            $(".phoneerror").text("*手机号不能为空");
            $(".phoneerror").show();
        }

    });

    //检测密码
    var numberreg = /^\d{4,20}$/;
    $(".userpwd").keyup(function () {
        var userpwd = $(".userpwd").val();
        if(userpwd.length >= 4){
            if(numberreg.test(userpwd)){
                $(".pwdsuccessimg").show();
            }else{
                $(".pwdsuccessimg").hide();
            }
        }else{
            $(".pwdsuccessimg").hide();
        }

    });

    //注册
    $(".registerbtn").click(function(){
        console.log("registerbtn and numberCode-->"+numberCode);
        var userphone = $(".userphone").val();
        var msgcode = $(".msgcode").val();
        var userpwd = $(".userpwd").val();

        //判断为空
        if(userphone == null || msgcode == null || userpwd == null
            || userphone == "" || msgcode == "" || userpwd == ""){
            $(".errorspan").text("*对不起，填写不允许为空");
            $(".errorspan").show();
        }else if($(".pwdsuccessimg").is(":hidden")){
            console.log("密码检验错误");
            $(".userpwd").val("");
            $(".errorspan").text("*对不起，密码不符合规范");
            $(".errorspan").show();
        }else{
            $(".errorspan").text("");
            $(".errorspan").hide();
            //匹配短信验证码
            if(msgcode != numberCode){
                console.log("短信验证码匹配不正确");
                $(".codeerror").val("*短信验证码错误,请填写图形验证码");
                $(".codeerror").show();

                //将 获取验证码按钮设为不可操作
                $(".getcodebtn").css({"cursor":"not-allowed"});
                $(".getcodebtn").attr("disabled", true);

                //获取并显示图形验证码
                $(".imgcodeinput").val("");
                $(".getCodeImage").attr("src","/util/getCodeImage");
                $(".imgcodediv").show();

                //检测图形验证码
                $(".imgcodeinput").keyup(function () {
                    var imgcodeinput = $(".imgcodeinput").val();
                    if(imgcodeinput.length >= 4){
                        $.ajax({
                            type:"post",
                            url:"/util/validCodeImage" ,
                            data:{"imgcodeinput":imgcodeinput},
                            dataType:"text",
                            success:function (data) {
                                console.log("data-->"+data);
                                //若匹配成功，显示成功icon，并设置可获取短信验证
                                if(data == "validSuccess"){
                                    $(".imgcodeinput").css({"border":"1px solid white"});
                                    $(".codesuccessimg").removeClass("codesuccessimg");
                                    $(".codesuccessimg").addClass("changecodesuccessimg");
                                    $(".codesuccessimg").show();
                                    $(".getcodebtn").css({"cursor":"pointer"});
                                    $(".getcodebtn").removeAttr("disabled");
                                }else if(data == "validFail"){
                                    $(".imgcodeinput").css({"border":"1px solid red"});
                                    $(".codesuccessimg").hide();
                                    $(".getcodebtn").css({"cursor":"not-allowed"});
                                    $(".getcodebtn").attr("disabled", true);
                                }
                            }
                        });
                    }
                });
            }else{
                console.log("手机号验证码匹配正确");
                //同意协议后才可提交,否则不可提交
                if($(".agreecheck").is(":checked")){
                    /*var userpwdmd5 = $.md5(userpwd);
                    console.log("加密后的密码-->"+userpwdmd5);*/
                    $.ajax({
                        type:"post",
                        url:"/user/register",
                        data:{"userphone":userphone,"userpwd":userpwd},
                        success:function (data) {
                            console.log("data-->"+data);
                            if(data == "registerSuccess"){
                                $(".registermsgdiv").show();
                                $(".registermsgspan").show();
                                var count = 3;
                                var countdown = setInterval(CountDown, 1000);
                                function CountDown() {
                                    if (count == 0) {
                                        $(".registermsgdiv").fadeOut();
                                        $(".registermsgspan").fadeOut();
                                        window.location.href = "login.jsp";
                                    }
                                    count--;
                                }
                            }else if(data == "registerFail"){
                                alert("请稍微，服务器开小差了");
                            }
                        },
                        error:function () {
                            alert("服务器请求错误");
                        }
                    });
                }else{
                    $(".registerbtn").css({"cursor":"not-allowed"});
                    $(".getcodebtn").attr("disabled", true);
                }
            }
        }
    });

    //更换图形验证码
    $(".getCodeImage").click(function () {
        console.log("getCodeImage") ;
        var imgsrc = $(this).attr("src");
        $(this).attr("src",imgsrc+"?"+new Date().getTime());
    });

});

//js正则，检验手机号
function validPhone(userphone) {
    var myreg=/^[1][3,4,5,7,8][0-9]{9}$/;
    if (!myreg.test(userphone)) {
        return false;
    }else {
        return true;
    }
}
