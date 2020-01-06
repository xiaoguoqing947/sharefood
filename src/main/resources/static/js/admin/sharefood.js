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
$("#upddiscountstime").datetimepicker(
    {
        language: "zh-CN",
        weekStart: 1,
        autoclose: 1,
        startView: 1,
        minView: 1,
        forceParse: 0,
        format: "yyyy-mm-dd HH:mm"
    });
$("#upddiscountetime").datetimepicker(
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
    var currentPage = 1;
    var pageSize = 10;
    var token = $.zui.store.get("token");//Token值var initMs = function () {
    var initMs = function () {
        var msForm = {
            "foodName": $('#foodName').val(),
            "pageSize": pageSize,
            "currentPage": currentPage - 1
        };
        $.ajax({
            url: '/api/meishi/queryListUrl',
            contentType: "application/json;charset=UTF-8",
            type: 'post',
            dataType: 'JSON',
            beforeSend: function (xhr) {
                xhr.setRequestHeader("token", token);
            },
            headers: {
                Accept: "application/json; charset=utf-8",
                "token": token
            },
            data: JSON.stringify(msForm),//转化为json字符串
            success: function (result) {
                console.log(result.data);
                if (result.result == 'success') {
                    $('#listDataGrid').empty();
                    $('#listDataGrid').data('zui.datagrid', null);
                    $('#listDataGrid').datagrid({
                        checkable: true,
                        checkByClickRow: true,
                        dataSource: {
                            cols: [
                                {name: 'isfb', label: '发布状态', width: -1},
                                {name: 'types', label: '美食类型', width: -1},
                                {name: 'msname', label: '美食名称', width: -1},
                                {name: 'msaddress', label: '美食地址', width: -1},
                                {name: 'mspic', label: '美食图片', width: -1},
                                {name: 'isdiscount', label: '优惠活动', width: -1},
                                {name: 'content', label: '美食描述', width: -1},
                            ],
                            cache: false,
                            array: result.data
                        }
                    });
                    $('#listPager').pager({
                        page: currentPage,
                        recPerPage: pageSize,
                        elements: ['prev_icon', 'pages', 'next_icon', 'total_text', 'size_menu'],
                        pageSizeOptions: [5, 10],
                        recTotal: result.pager.recTotal
                    });
                } else {
                    new $.zui.Messager("请重新登录", {
                        type: 'warning', // 定义颜色主题
                        placement: 'center' // 定义显示位置
                    }).show();
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert("jqXHR.responseText=" + jqXHR.responseText);
                alert("jqXHR.statusText=" + jqXHR.statusText);
                alert("jqXHR.status=" + jqXHR.status)
            }
        });
    };
    initMs();
    $("#searchBtn").click(function () {
        initMs();
    });
    //监听分页，修改当前页，条数
    $('#listPager').on('onPageChange', function (e, state, oldState) {
        currentPage = state.page;
        pageSize = state.recPerPage;
        initMs();
    });
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
                            initMs();
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
    //初始化修改
    $("#updateBtn").click(function () {
        // 获取数据表格实例
        var listDataGrid = $('#listDataGrid').data('zui.datagrid');
        // 获取当前已选中的行数据项
        var selectedItems = listDataGrid.getCheckItems();
        if (selectedItems.length == 0) {
            new $.zui.Messager('请选择要修改的记录', {
                type: 'warning',
                placement: 'center'
            }).show();
        } else if (selectedItems.length > 1) {
            new $.zui.Messager('请只选择一条要修改的记录', {
                type: 'warning',
                placement: 'center'
            }).show();
        } else {
            $("#updateForm")[0].reset();
            var url = '/api/meishi/initUpdate';
            var param = 'id=' + selectedItems[0].id;//请求到列表页面
            $.ajax({
                url: url,
                type: 'post',
                dataType: 'JSON',
                beforeSend: function (xhr) {//设置请求头信息
                    xhr.setRequestHeader("token", token);
                },
                headers: {'token': token},
                data: param,
                success: function (result) {
                    $('#updateModal').modal('show', 'fit');
                    var htmlType = '';
                    var htmlDiscount = '';
                    var htmlCrowl = '';
                    for (var i = 0; i < result.dictionarys.length; i++) {
                        if (result.dictionarys[i].dcisCode == 'type') {
                            if (result.meiShi.types == result.dictionarys[i].keyValue) {
                                htmlType += '<option value="' + result.dictionarys[i].keyValue + '" selected>' + result.dictionarys[i].keyName + '</option>';
                            } else {
                                htmlType += '<option value="' + result.dictionarys[i].keyValue + '">' + result.dictionarys[i].keyName + '</option>';
                            }
                        }
                        if (result.dictionarys[i].dcisCode == 'discount') {
                            if (result.meiShi.isdiscount == result.dictionarys[i].keyValue) {
                                htmlDiscount += '<option value="' + result.dictionarys[i].keyValue + '" selected>' + result.dictionarys[i].keyName + '</option>';
                            } else {
                                htmlDiscount += '<option value="' + result.dictionarys[i].keyValue + '">' + result.dictionarys[i].keyName + '</option>';
                            }
                        }
                        if (result.dictionarys[i].dcisCode == 'crowl') {
                            if (result.meiShi.recommendcrowd == result.dictionarys[i].keyValue) {
                                htmlCrowl += '<option value="' + result.dictionarys[i].keyValue + '" selected>' + result.dictionarys[i].keyName + '</option>';
                            } else {
                                htmlCrowl += '<option value="' + result.dictionarys[i].keyValue + '">' + result.dictionarys[i].keyName + '</option>';
                            }
                        }
                    }
                    var htmlTag = '';
                    for (var i = 0; i < result.tags.length; i++) {
                        if (result.meiShi.mstag == result.tags[i].id) {
                            htmlTag += '<option value="' + result.tags[i].id + '" selected>' + result.tags[i].tag + '</option>';
                        } else {
                            htmlTag += '<option value="' + result.tags[i].id + '" >' + result.tags[i].tag + '</option>';
                        }
                    }
                    $('#updmstag').html(htmlTag);
                    $('#updtypes').html(htmlType);
                    $('#updisdiscount').html(htmlDiscount);
                    $('#updrecommendcrowd').html(htmlCrowl);
                    /*普通字段*/
                    $('#updpic').attr('src', result.meiShi.mspic);
                    $('#updmsname').val(result.meiShi.msname);
                    $('#updmsaddress').val(result.meiShi.msaddress);
                    $('#updmsnumber').val(result.meiShi.msnumber);
                    $('#upddiscountstime').val(result.meiShi.discountstime);
                    $('#upddiscountetime').val(result.meiShi.discountetime);
                    $('#updcontent').val(result.meiShi.content);
                    $('#updId').val(result.meiShi.id);
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
    //修改保存
    $("#updateSaveBtn").click(function () {
        var updateValite = $("#updateForm").validate({
            rules: {
                updtypes: {
                    "required": true
                },
                updmsname: {
                    "required": true
                },
                updcontent: {
                    "required": true
                }
            },
            submitHandler: function (form) {
                var formdata = new FormData(form);
                $.ajax({
                    url: "/api/meishi/update",
                    type: 'post',
                    /*上传图片时使用到的，一定要加*/
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
                        if (data.code == 200) {
                            new $.zui.Messager('修改成功!', {
                                type: 'success',
                                placement: 'center'
                            }).show();
                            $('#updateModal').modal('hide', 'fit');
                            updateValite.resetForm();
                            initMs();
                        } else {
                            new $.zui.Messager("修改失败", {
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
    });
    /*删除按钮*/
    $("#deleteBtn").click(function () {
        // 获取数据表格实例
        var listDataGrid = $('#listDataGrid').data('zui.datagrid');
        // 获取当前已选中的行数据项
        var selectedItems = listDataGrid.getCheckItems();
        if (selectedItems.length == 0) {
            new $.zui.Messager('请选择要删除的记录', {
                type: 'warning',
                placement: 'center'
            }).show();
        } else if (selectedItems.length > 1) {
            new $.zui.Messager('请只选择一条要删除的记录', {
                type: 'warning',
                placement: 'center'
            }).show();
        } else {
            $('#deleteModal').modal('show', 'fit');
        }
    });
//删除菜单
    $("#deleteMsBtn").click(function () {
        // 获取数据表格实例
        var listDataGrid = $('#listDataGrid').data('zui.datagrid');
        // 获取当前已选中的行数据项
        var selectedItems = listDataGrid.getCheckItems();
        var url = '/api/meishi/delete';
        var param = 'id=' + selectedItems[0].id;//请求到列表页面
        $.ajax({
            url: url,
            type: 'post',
            dataType: 'JSON',
            beforeSend: function (xhr) {//设置请求头信息
                xhr.setRequestHeader("token", token);
            },
            headers: {'token': token},
            data: param,
            success: function (data) {
                $('#deleteModal').modal('hide', 'fit');
                if (data.success == '1') {
                    new $.zui.Messager('删除成功!', {
                        type: 'success',
                        placement: 'center'
                    }).show();
                    initMs();
                } else {
                    new $.zui.Messager("删除失败", {
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
    });
    //审核修改
    $("#checkBtn").click(function () {
        // 获取数据表格实例
        var listDataGrid = $('#listDataGrid').data('zui.datagrid');
        // 获取当前已选中的行数据项
        var selectedItems = listDataGrid.getCheckItems();
        if (selectedItems.length == 0) {
            new $.zui.Messager('请选择要修改的记录', {
                type: 'warning',
                placement: 'center'
            }).show();
        } else if (selectedItems.length > 1) {
            new $.zui.Messager('请只选择一条要修改的记录', {
                type: 'warning',
                placement: 'center'
            }).show();
        } else {
            $("#checkForm")[0].reset();
            var url = '/api/meishi/initCheck';
            var param = 'id=' + selectedItems[0].id;//请求到列表页面
            $.ajax({
                url: url,
                type: 'post',
                dataType: 'JSON',
                beforeSend: function (xhr) {//设置请求头信息
                    xhr.setRequestHeader("token", token);
                },
                headers: {'token': token},
                data: param,
                success: function (result) {
                    $('#checkModal').modal('show', 'fit');
                    var htmlFb = '';
                    for (var i = 0; i < result.dictionarys.length; i++) {
                        if (result.dictionarys[i].dcisCode == 'fb') {
                            if (result.meiShi.isfb == result.dictionarys[i].keyValue) {
                                htmlFb += '<option value="' + result.dictionarys[i].keyValue + '" selected>' + result.dictionarys[i].keyName + '</option>';
                            } else {
                                htmlFb += '<option value="' + result.dictionarys[i].keyValue + '">' + result.dictionarys[i].keyName + '</option>';
                            }
                        }
                        $('#checkfb').html(htmlFb);
                        $('#checkId').val(result.meiShi.id);
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
    /*发布按钮*/
    $("#checkMsBtn").click(function () {
        var checkValite = $("#checkForm").validate({
            submitHandler: function (form) {
                $.ajax({
                    url: "/api/meishi/check",
                    type: 'post',
                    dataType: 'JSON',
                    beforeSend: function (xhr) {//设置请求头信息
                        xhr.setRequestHeader("token", token);
                    },
                    headers: {'token': token},
                    data: $(form).serialize(),
                    success: function (data) {
                        if (data.success == 1) {
                            new $.zui.Messager('修改成功!', {
                                type: 'success',
                                placement: 'center'
                            }).show();
                            $('#checkModal').modal('hide', 'fit');
                            checkValite.resetForm();
                            initMs();
                        } else {
                            new $.zui.Messager("修改失败", {
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
    });
});