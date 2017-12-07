$(function () {
    $("#btn").click(function () {
        var str1 = "1234";
        var md1 = $.md5(str1);

        var str2 = "1234";
        var md2 = $.md5(str2);

        console.log(md1+","+md2);
        if(md1 == md2){
            alert("==");
        }else{
            alert("!=");
        }

        /*var count = 3;
        var countdown = setInterval(CountDown, 1000);

        function CountDown() {
            $("#btn").attr("disabled", true);
            $("#btn").val("Please wait " + count + " seconds!");
            if (count == 0) {
                $("#btn").val("Submit").removeAttr("disabled");
                clearInterval(countdown);
            }
            count--;
        }*/
    });

    //更换图形验证码
    $(".getCodeImage").click(function () {
        console.log("getCodeImage") ;
        var imgsrc = $(this).attr("src");
        $(this).attr("src",imgsrc+"?"+new Date().getTime());
    });

});