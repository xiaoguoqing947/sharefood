$("#discountstime").datetimepicker(
    {
        language: "zh-CN",
        weekStart: 1,
        autoclose: 1,
        startView: 1,
        minView: 1,
        forceParse: 0,
        format: "yyyy-mm-dd HH:mm"
    });
$("#discountetime").datetimepicker(
    {
        language: "zh-CN",
        weekStart: 1,
        autoclose: 1,
        startView: 1,
        minView: 1,
        forceParse: 0,
        format: "yyyy-mm-dd HH:mm"
    });
$(document).ready(function () {
    var token = $.zui.store.get("token");
    var initMainInfo = function () {
        var url = '/api/admin/mainInfo';
        $.ajax({
            url: url,
            type: 'post',
            dataType: 'JSON',
            beforeSend: function (xhr) {//设置请求头信息
                xhr.setRequestHeader("token", token);
            },
            headers: {'token': token},
            success: function (result) {
                if (result.status == 'success') {
                    var fristPageHtml = '';
                    var secondPageHtml = '';
                    for (var i = 0; i < result.meiShiList.length; i++) {
                        fristPageHtml += '<div class="flex sm:flex-col items-center sm:items-start w-full xs:w-1/2 sm:w-1/3 md:w-1/5 p-4">\n' +
                            '                            <img src="' + result.meiShiList[i].mspic + '" alt="book-02" class="w-1/3 sm:w-full shadow-md transition-normal hover:brighter hover:translate-y-1 hover:shadow-lg hover:border-indigo js-book">\n' +
                            '                            <div class="ml-3 sm:ml-0 w-2/3 sm:w-full">\n' +
                            '                                <p class="text-sm my-2 font-medium sm:font-normal">' + result.meiShiList[i].msname + '</p>\n' +
                            '                                <label  class="hidden sm:inline-block rounded-full libre-bg-yellow text-white px-2 py-1/2 text-xs">' + result.meiShiList[i].senduser + '</label>\n' +
                            '                            </div>\n' +
                            '                        </div>';
                        if (result.meiShiList[i].isfb == 0) {
                            secondPageHtml += '<div class="flex justify-start items-center p-5 px-6 w-full sm:w-1/2"\n' +
                                '                             style="background-color:#ECE7E9;">\n' +
                                '                            <img src="' + result.meiShiList[i].mspic + '" alt="pick" class="shadow-md w-1/3">\n' +
                                '                            <div class="ml-4 mt-1 w-2/3">\n' +
                                '                                <p class="font-medium">' + result.meiShiList[i].msname + '</p>\n' +
                                '                                <p class="mt-3 text-sm">' + result.meiShiList[i].content + '</p>\n' +
                                '                                <button class="shadow-md mt-3 bg-grey-lightest hover:bg-white text-indigo-darker text-xs py-2 px-4 rounded-full transition-normal hover:shadow hover:translate-y-1 active:translate-y-1 focus:outline-none">\n' +
                                '                                    草稿\n' +
                                '                                </button>\n' +
                                '                            </div>\n' +
                                '                        </div>';
                        } else {
                            secondPageHtml += '<div class="flex justify-start items-center p-5 px-6 w-full sm:w-1/2"\n' +
                                '                             style="background-color:#F1CECD;">\n' +
                                '                            <img src="' + result.meiShiList[i].mspic + '" alt="pick" class="shadow-md w-1/3">\n' +
                                '                            <div class="ml-4 mt-1 w-2/3">\n' +
                                '                                <p class="font-medium">' + result.meiShiList[i].msname + '</p>\n' +
                                '                                <p class="mt-3 text-sm">' + result.meiShiList[i].content + '</p>\n' +
                                '                                <button  class="shadow-md mt-3 bg-grey-lightest hover:bg-white text-indigo-darker text-xs py-2 px-4 rounded-full transition-normal hover:shadow hover:translate-y-1 active:translate-y-1 focus:outline-none">\n' +
                                '                                    已发布\n' +
                                '                                </button>\n' +
                                '                            </div>\n' +
                                '                        </div>';
                        }
                    }

                    $('#section-library').html(fristPageHtml);
                    $('#section-picks').html(secondPageHtml);
                    $('#meiShiCount').text('(' + result.meiShiList.length + ')');
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
    initMainInfo();
    //初始化添加
    $("#addBtn").click(function () {
        //获取菜单列表
        var url = "/api/meishi/initAdd";
        var token = $.zui.store.get("token");//Token值
        $.ajax({
            url: url,
            type: 'post',
            dataType: 'JSON',
            beforeSend: function (xhr) {
                xhr.setRequestHeader("token", token);
            },
            headers: {
                Accept: "application/json; charset=utf-8",
                "token": token
            },
            success: function (result) {
                console.log(result.dictionarys);
                var htmlType = '';
                var htmlDiscount = '';
                var htmlCrowl = '';
                for (var i = 0; i < result.dictionarys.length; i++) {
                    if (result.dictionarys[i].dcisCode == 'type') {
                        htmlType += '<option value="' + result.dictionarys[i].keyValue + '">' + result.dictionarys[i].keyName + '</option>';
                    }
                    if (result.dictionarys[i].dcisCode == 'discount') {
                        htmlDiscount += '<option value="' + result.dictionarys[i].keyValue + '">' + result.dictionarys[i].keyName + '</option>';
                    }
                    if (result.dictionarys[i].dcisCode == 'crowl') {
                        htmlCrowl += '<option value="' + result.dictionarys[i].keyValue + '">' + result.dictionarys[i].keyName + '</option>';
                    }
                }
                var htmlTag = '';
                for (var i = 0; i < result.tags.length; i++) {
                    htmlTag += '<option value="' + result.tags[i].id + '">' + result.tags[i].tag + '</option>';
                }
                $('#mstag').html(htmlTag);
                $('#types').html(htmlType);
                $('#isdiscount').html(htmlDiscount);
                $('#recommendcrowd').html(htmlCrowl);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert("jqXHR.responseText=" + jqXHR.responseText);
                alert("jqXHR.statusText=" + jqXHR.statusText);
                alert("jqXHR.status=" + jqXHR.status)
            }
        });
    });
    /*添加美食*/
    var addSaveMeiShi = function () {
        var validator = $("#addForm").validate({
            rules: {
                mspic: {
                    "required": true
                },
                types: {
                    "required": true
                },
                msname: {
                    "required": true
                },
                content: {
                    "required": true
                }
            },
            submitHandler: function (form) {
                var formdata = new FormData(form);
                $.ajax({
                    url: "/api/meishi/add",
                    type: 'post',
                    async: false,
                    cache: false,
                    contentType: false,
                    processData: false,
                    dataType: 'JSON',
                    beforeSend: function (xhr) {//设置请求头信息
                        xhr.setRequestHeader("token", token);
                    },
                    headers: {
                        Accept: "application/json; charset=utf-8",
                        "token": token
                    },
                    data: formdata,
                    success: function (data) {
                        if (data.code == 200) {
                            new $.zui.Messager('添加成功!', {
                                type: 'success',
                                placement: 'center'
                            }).show();
                            $('#addModal').modal('hide', 'fit');
                            initMainInfo();
                            validator.resetForm();
                        } else {
                            new $.zui.Messager("添加失败", {
                                type: 'warning',
                                placement: 'center'
                            }).show();
                        }
                    },
                    error: function (e) {
                        new $.zui.Messager('系统繁忙,请稍候再试!', {
                            type: 'warning',
                            placement: 'center'
                        }).show();
                    }
                });
            }
        });
    };
    //保存草稿按钮
    $("#addCgBtn").click(function () {
        $('#fb').val(0);
        addSaveMeiShi();
    });
    //保存发布按钮
    $("#addFbBtn").click(function () {
        $('#fb').val(1);
        addSaveMeiShi();
    });
});
