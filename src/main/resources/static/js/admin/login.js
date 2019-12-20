$(function () {
    /*登录*/
    $("#button").click(function () {
        var username = $("#username").val();
        var userpwd = $("#userpwd").val();
        console.log(userpwd);
        if (username != '' && userpwd != '') {
            $.ajax({
                type: "POST",
                url: '/login.action',
                data: {username: username, password: md5(userpwd)},
                success: function (data) {
                    if (data.status == 'success') {
                        saveInfo();
                        location.href = '/?isAdmin=no';
                    } else {
                        $("#username").val('');
                        $("#userpwd").val('');
                        toastr.error('用户名或密码错误，请重新输入！');
                    }
                }
            });
        } else {
            toastr.info('用户名或密码不为空！');
            $("#username").val("");
            $("#userpwd").val("");
        }
    });
    /*管理员登录*/
    $('#login_admin_btn').click(function () {
        var name = $('#admin_name').val();
        var pwd = $('#admin_pwd').val();
        console.log(name + '---' + pwd);
        if (name.length > 0 && pwd.length > 0) {
            $.ajax(
                {
                    url: "/loginAdmin.action",
                    type: 'post',
                    dataType: 'JSON',
                    data: {username: name, password: md5(pwd)},
                    success: function (data) {
                        if (data.status == 'success') {
                            window.location.href = '/?isAdmin=yes';
                        } else {
                            $('#admin_name').val('');
                            $('#admin_pwd').val('');
                            toastr.error('账号或密码错误，请重新输入');
                        }
                    }
                }
            )
        } else {
            toastr.info('用户名或密码不为空！');
        }
    });
    //取回密码
    $("#reget_pwd").click(function () {
        var usrmail = $("#usrmail").val();
        if (!Test_email(usrmail)) {
            toastr.info("请输入正确的邮箱！");
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
    $("#regist-admin").bind("click", function () {
        window.location.href = "/register";
    });
    $("#registForm").validate({
        // debug: true,//表单调试时使用
        errorPlacement: function (error, element) {
            error.appendTo(element.parent()); //直接把错误信息加在验证元素后··
        },
        submitHandler: function (form) {
            var password = md5($('#pwd').val());
            var username = $('#uname').val();
            var email = $('#email').val();
            $.ajax({
                url: "/register.action",
                type: 'post',
                dataType: 'JSON',
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify({
                    password: password,
                    username: username,
                    email: email
                }),
                success: function (data) {
                    console.log(data);
                    if (data.status == 'success') {
                        alert("恭喜您，注册成功");
                        window.location.href = '/login';
                    } else if (data.status == 'fail') {
                        toastr.error("注册失败");
                    }
                }
            });
        },
        rules: {
            uname: {
                required: true,
                remote: {
                    url: "/validateName.action",
                    type: 'post',
                    dataType: "json",
                    data: {
                        uname: function () {
                            return $("#uname").val();
                        }
                    }
                }
            },
            email: {
                required: true,
                email: true
            },
            pwd: {
                required: true,
                minlength: 6,
            },
            repwd: {
                required: true,
                minlength: 6,
                equalTo: "#pwd",
            }
        },
        messages: {
            pwd: {
                required: " 密码不为空  ",
                minlength: " 密码长度不能小于 6 个字母  ",
            },
            repwd: {
                required: " 密码不为空  ",
                minlength: " 密码长度不能小于 6 个字母  ",
                equalTo: " 两次密码输入不一致  "
            },
            email: {
                required: " 邮箱不为空  ",
                email: " 邮箱格式错误！  "
            },
            uname: {
                required: " 用户名不为空  ",
                remote: " 用户名已存在  "
            }
        }
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
    });


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

    saveInfo = function () {
        try {
            var isSave = document.getElementById('save_me').checked;   //保存按键是否选中
            if (isSave) {
                var username = document.getElementById('username').value;
                var password = document.getElementById('userpwd').value;
                if (username != "" && password != "") {
                    SetCookie(username, password);
                }
            } else {
                SetCookie("", "");
            }
        } catch (e) {

        }
    };

    //密码存入cookie时没有进行加密，因为前端md5加密没有办法解密
    function SetCookie(username, password) {
        var Then = new Date();
        Then.setTime(Then.getTime() + 1866240000000);
        document.cookie = "username=" + username + "%%" + password + ";expires=" + Then.toGMTString();
    }

    //取出cookie
    function GetCookie() {
        var nmpsd;
        var nm;
        var psd;
        var cookieString = String(document.cookie);
        var cookieHeader = "username=";
        var beginPosition = cookieString.indexOf(cookieHeader);
        cookieString = cookieString.substring(beginPosition);
        var ends = cookieString.indexOf(";");
        if (ends != -1) {
            cookieString = cookieString.substring(0, ends);
        }
        if (beginPosition > -1) {
            nmpsd = cookieString.substring(cookieHeader.length);
            if (nmpsd != "") {
                beginPosition = nmpsd.indexOf("%%");
                nm = nmpsd.substring(0, beginPosition);
                psd = nmpsd.substring(beginPosition + 2);
                document.getElementById('username').value = nm;
                document.getElementById('userpwd').value = psd;
                if (nm != "" && psd != "") {
                    document.getElementById('save_me').checked = true;
                }
            }
        }
    }

    GetCookie();
});

//Email 规则以后重新整理所有网站关于js 验证
function Test_email(strEmail) {
    var myReg = /^[-a-z0-9\._]+@([-a-z0-9\-]+\.)+[a-z0-9]{2,3}$/i;
    if (myReg.test(strEmail)) return true;
    return false;
}
