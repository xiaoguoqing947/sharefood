$(function () {
    /*登录*/
    $("#button").click(function () {
        var username = $("#username").val();
        var userpwd = $("#userpwd").val();
        userpwd=md5(userpwd);
        console.log(userpwd)
        if (username.length > 0 && userpwd.length > 0) {
            logJudge(1);
        }else{
            toastr.info('用户名或密码不为空！');
        }
    });
    //取回密码
    $("#reget_pwd").click(function () {
        var usrmail = $("#usrmail").val();
        if (!Test_email(usrmail)) {
            toastr.info("请输入正确的邮箱！")
            return false;
        }
        $.ajax({
            type: "POST",
            url: '#',
            data: {typex: 5, usrmail: usrmail},
            success: function (data) {//
                alert(data);
                $("#login_model").show();
                $("#forget_model").hide();
                $("#regist_model").hide();
                $("#usrmail").val("");
                $("#username").val("");
                $("#userpwd").val("");
            }
        });
    });
    //注册用户
    $('#regist').click(function () {

    });
    /*管理员登录*/
    $('#login_admin_btn').click(function () {

    });

    /*进入管理员登录界面*/
    $('#admin_login').click(function () {
        $("#login_model").hide();
        $("#regist_model").hide();
        $("#admin_login_model").show();
    });
    //进入忘记密码界面
    $("#iforget").click(function () {
        $("#login_model").hide();
        $("#regist_model").hide();
        $("#forget_model").show();
    });
    //进入注册账号界面
    $("#iregist").click(function () {
        $("#login_model").hide();
        $("#admin_login_model").hide();
        $("#forget_model").hide();
        $("#regist_model").show();
    })


    //忘记密码界面返回
    $("#forget_back").click(function () {
        $("#usrmail").val("");
        $("#username").val("");
        $("#userpwd").val("");
        $("#login_model").show();
        $("#forget_model").hide();
    });
    //注册界面返回
    $('#regist_back').click(function () {
        $("#regist_model").hide();
        $("#admin_login_model").hide();
        $("#login_model").show();
    });
    //管理员登录界面返回
    $('#admin_back').click(function () {
        $("#regist_model").hide();
        $("#admin_login_model").hide();
        $("#login_model").show();
    });


    //typexx 自动 还是手动
    function logJudge(typex) {
        var username = $("#username").val();
        var userpwd = $("#userpwd").val();
        var issavecookies = "NO";
        if ($("#save_me").attr("checked") == true) {
            issavecookies = "Yes";
        } else {
            issavecookies = "NO";
        }
        if (typex == "2") {
            if (username == null && userpwd == null) {
                ////保存了cook
                username = $.cookie('codeusername');
                userpwd = $.cookie('codeppsd');
                $.ajax({
                    type: "POST",
                    url: '#',
                    data: {username: username, userpwd: userpwd, issavecookies: issavecookies, typex: 2},
                    success: function (data) {///去更新cookies
                    }
                });
            }
        } else if (typex == "1") {
            ///// 手動 登錄
            $.ajax({
                type: "POST",
                url: '#',
                data: {username: username, userpwd: userpwd, issavecookies: issavecookies, typex: 1},
                success: function (data) {///去更新cookies
                    if (data == "0" || data == "1") {
                        window.location.href = "#";
                    }
                }
            });
        }
    }
});

//Email 规则以后重新整理所有网站关于js 验证
function Test_email(strEmail) {
    var myReg = /^[-a-z0-9\._]+@([-a-z0-9\-]+\.)+[a-z0-9]{2,3}$/i;
    if (myReg.test(strEmail)) return true;
    return false;
}
