$(function () {
    var userphone = $(".userphone").val();
    if(userphone != null){
        console.log("userphone exist");
        $(".newstable").html("");
        $.ajax({
            type:"post",
            url:"/news/queryAllCommonNews",
            success:function (data) {
                console.log("查询所有公共消息:"+data);
                var trObj = "";
                $.each(data,function (index, value) {
                    var ctimestr = value.ctime;
                    var ctime = new Date(ctimestr);
                    var newctime = "2017-"+(ctime.getMonth()+1)+"-"+ctime.getDate();
                    console.log(value.cid+","+value.cname+","+newctime);

                    trObj += "<tr>";
                    trObj += "<td class='titletd'><a class='onea' cid='"+value.cid+"'cname='"+value.cname+"' ctime='"+newctime+"'>"+value.cname+"</br>"+newctime+"</a></td>";
                    trObj += "<td class='btntd'><button class='editonebtn' cid='"+value.cid+"' cname='"+value.cname+"' ctime='"+newctime+"'>编辑</button></td>";
                    trObj += "<td class='btntd'><button class='deleteonebtn' cid='"+value.cid+"'>删除</button></td>";
                    trObj += "</tr>";
                    trObj += "<div class='line'>";
                });

                $(".newstable").append(trObj);

                //查看每个消息的内容
                $(".onea").each(function () {
                    $(this).click(function () {
                        var cid = $(this).attr("cid");
                        var cname = $(this).attr("cname");
                        var ctime = $(this).attr("ctime");
                        console.log("cid-->"+cid+",canme-->"+cname+",ctime-->"+ctime);

                        var content;
                        $.ajax({
                            type:"post",
                            url:"/news/queryContentByCid",
                            data:{"cid":cid},
                            success:function (data) {
                                content = data;
                                console.log("该消息的内容-->"+data);
                                $(".contentdiv .titleh2").html(cname);
                                $(".contentdiv .ctimespan").html(ctime);
                                $(".contentdiv .detaildiv").html(content);
                                $(".markdiv").slideDown();
                                $(".contentdiv").slideDown();
                            }
                        });
                    });
                });

                //编辑一条
                $(".editonebtn").each(function () {
                   $(this).click(function () {
                       var cid = $(this).attr("cid");
                       var cname = $(this).attr("cname");
                       var ctime = $(this).attr("ctime");
                       console.log("cid-->"+cid+",canme-->"+cname+",ctime-->"+ctime);

                       $.ajax({
                           type:"post",
                           url:"/news/queryContentByCid",
                           data:{"cid":cid},
                           success:function (data) {
                               console.log("该消息的内容-->"+data);
                               $(".editdiv .editcname").val(cname);
                               console.log("ctime-->"+ctime);
                               $(".editdiv .editctime").val(ctime);
                               $(".editdiv .editcontent").val(data);
                               $(".updatebtn").attr("cid",cid);

                               $(".markdiv").slideDown();
                               $(".editdiv").slideDown();
                           }
                       });
                   });
                });
                
                //删除一条
                $(".deleteonebtn").each(function () {
                    $(this).click(function () {
                        var cid = $(this).attr("cid");
                        var msg = "您确定要删除这条消息吗？";
                        if(confirm(msg)){
                            $.ajax({
                                type:"post",
                                url:"/news/deleteOneNews",
                                data:{"cid":cid},
                                success:function (data) {
                                    if(data == "deleteOneSuccess"){
                                        window.location.href = "index.jsp";
                                    }else if(data == "deleteOneFail"){
                                        alert("服务器请求错误");
                                    }
                                }
                            });
                        }
                    });
                });
            },
            error:function () {
                alert("服务器请求错误");
            }
        });
    }

    $(".markdiv").click(function () {
        $(".markdiv").slideUp();
        $(".contentdiv").slideUp();
        $(".editdiv").slideUp();
        $(".adddiv").slideUp();
    });

    //确定修改
    $(".updatebtn").click(function(){
        console.log("updatebtn");
        var editcname = $(".editcname").val();
        var editctime = $(".editctime").val();
        //var editcontent = $(".editcontent").text($(".editcontent").val()).val();
        //var editcontent = $(".editcontent").val();
        var editcontent = document.getElementById("editcontent").value;
        var cid = $(this).attr("cid");
        console.log("cid-->"+cid+",editcname-->"+editcname+",editctime-->"+editctime+",editcontent-->"+editcontent);
        //alert("更改后的："+editcname+","+editctime+","+editcontent);
        $.ajax({
            type:"post",
            url:"/news/updateNews",
            data:{"cid":cid,"editcname":editcname,"editctime":editctime,"editcontent":editcontent},
            success:function (data) {
                console.log("data-->"+data);
                if(data == "updateSuccess"){
                    $(".markdiv").slideUp();
                    $(".editdiv").slideUp();
                    //重新请求加载页面
                    window.location.href = "index.jsp";
                }else if(data == "updateFail"){
                    alert("服务器开小差了");
                }
            }
        });
    });

    //关闭按钮
    $(".backbtn").click(function () {
        $(".markdiv").slideUp();
        $(".contentdiv").slideUp();
        $(".editdiv").slideUp();
        $(".adddiv").slideUp();
    });

    //新增
    $(".addbtn").click(function () {
        $(".markdiv").slideDown();
        $(".adddiv").slideDown();
    });

    //保存新消息按钮
    $(".savebtn").click(function () {
        var caddid = $(".addcid").val();
        var addname = $(".addcname").val();
        var addctime = $(".addctime").val();
        var addcontent = $(".addcontent").val();

        if(caddid.length > 0 && addname.length > 0 && addctime.length > 0 && addcontent.length > 0){
            $.ajax({
                type:"post",
                url:"/news/insertOneNews",
                data:{"cid":caddid,"cname":addname,"ctime":addctime,"content":addcontent},
                success:function (data) {
                    if(data == "insertSuccess"){
                        window.location.href = "index.jsp";
                    }else if(data == "insertFail"){
                        alert("服务器请求错误");
                    }
                }
            });
        }else{
            alert("填写不允许为空");
        }
    });

    //注销
    $(".logoutbtn").click(function () {
        var msg = "您确定注销吗？";
        if(confirm(msg)){
            $.ajax({
                type:"post",
                url:"/user/logout",
                success:function (data) {
                    if(data == "logoutSuccess"){
                        window.location.href = "login.jsp";
                    }else {
                        alert("服务器请求错误")
                    }
                }
            });
        }
    });

});