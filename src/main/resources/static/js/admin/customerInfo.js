$(document).ready(function () {
    var token = $.zui.store.get("token");
    var initCustomerInfo = function () {
        var url = '/api/customer/detail';
        $.ajax({
            url: url,
            type: 'post',
            dataType: 'JSON',
            beforeSend: function (xhr) {//设置请求头信息
                xhr.setRequestHeader("token", token);
            },
            headers: {'token': token},
            success: function (result) {
                if (result.success == '1') {
                    $('#leftCartUsername').text(result.customerInfo.username);
                    $('#leftCartHeadpic').attr("src", result.customerInfo.headpic);
                    var html = '       <small class="text-muted">邮箱地址</small>\n' +
                        '                <h6>' + ((result.customerInfo.email != null && result.customerInfo.email != "") ? result.customerInfo.email : "baobao@qq.com") + '</h6> <small class="text-muted p-t-30 db">联系电话</small>\n' +
                        '                <h6>' + ((result.customerInfo.phone != null && result.customerInfo.phone != "") ? result.customerInfo.phone : "完善个人信息") + '</h6> <small class="text-muted p-t-30 db">家庭地址</small>\n' +
                        '                <h6>' + ((result.customerInfo.address != null && result.customerInfo.address != "") ? result.customerInfo.address : "香港是中国的") + '</h6>\n' +
                        '                <div class="map-box">\n' +
                        '                    <iframe src="/images/bbig1.jpg" width="100%" height="150" frameborder="0" style="border:0"\n' +
                        '                            allowfullscreen></iframe>\n' +
                        '                </div>';
                    $('#leftBottomCard').html(html);
                    //TODO rightCart will do it!
                    $('#name').val(result.customerInfo.name);
                    $('#email').val(result.customerInfo.email);
                    if (result.customerInfo.sex == null || result.customerInfo.sex == '' || result.customerInfo.sex == 0) {
                        $('#woman').attr("checked", true);
                        $('#man').attr("checked", false);
                    } else if (result.customerInfo.sex == 1) {
                        $('#woman').attr("checked", false);
                        $('#man').attr("checked", true);
                    }
                    $('#phone').val(result.customerInfo.phone);
                    $('#phone').val(result.customerInfo.phone);
                    $('#age').val(result.customerInfo.age);
                    //身份证号取前四位和后四位
                    var idcard = result.customerInfo.idcard.slice(0, 4) + '****' + result.customerInfo.idcard.slice(-4);
                    $('#idcard').val(idcard);
                    $('#address').val(result.customerInfo.address);

                }
            },
            error: function (e) {
                new $.zui.Messager('系统繁忙,请稍候再试!', {
                    type: 'warning',
                    placement: 'center'
                }).show();
            }
        });
    };
    initCustomerInfo();
    $("#customerForm").validate({
        submitHandler: function (form) {
            $.ajax({
                url: "/api/customer/update",
                type: 'post',
                dataType: 'JSON',
                beforeSend: function (xhr) {//设置请求头信息
                    xhr.setRequestHeader("token", token);
                },
                headers: {'token': token},
                data: $(form).serialize(),
                success: function (data) {
                    console.log(data);
                    if (data.status == 'success') {
                        toastr.info('修改成功');
                        initCustomerInfo();
                    } else if (data.status == 'fail') {
                        toastr.info('修改失败');
                    }
                }
            });
        },
        rules: {
            name: {
                "required": true
            },
            email: {
                "required": true
            },
            age: {
                "required": true
            },
            number: {
                "required": true
            },
            idcard: {
                "required": true
            },
            address: {
                "required": true
            }
        }
    });
    $("#updateHeadPic").validate({
        rules: {
            file: {
                "required": true
            }
        },
        message: {
            file: {
                "required": "请选择你想要更换的头像"
            }
        },
        submitHandler: function (form) {
            var formdata = new FormData(form);
            $.ajax({
                url: "/api/file/upload/updateHeadPic",
                type: 'post',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                dataType: 'JSON',
                beforeSend: function (xhr) {//设置请求头信息
                    xhr.setRequestHeader("token", token);
                },
                headers: {'token': token},
                data: formdata,
                success: function (data) {
                    console.log('data=' + data);
                    if (data.code == 200) {
                        toastr.info('修改成功');
                        window.location.reload();
                    } else if (data.code == 500) {
                        toastr.warning(data.msg);
                    }
                },
                error: function (e) {
                    toastr.warning("系统繁忙，请联系管理员!");
                }
            });
        }
    })
});
