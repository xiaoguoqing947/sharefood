$(document).ready(function () {
    var currentPage = 1;
    var pageSize = 10;
    var token = $.zui.store.get("token");//Token值var initTj = function () {
    var initTj = function () {
        var tjForm = {
            "picName": $('#picName').val(),
            "pageSize": pageSize,
            "currentPage": currentPage - 1
        };
        $.ajax({
            url: '/api/tujian/queryListUrl',
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
            data: JSON.stringify(tjForm),//转化为json字符串
            success: function (result) {
                if (result.result == 'success') {
                    $('#listDataGrid').empty();
                    $('#listDataGrid').data('zui.datagrid', null);
                    $('#listDataGrid').datagrid({
                        checkable: true,
                        checkByClickRow: true,
                        dataSource: {
                            cols: [
                                {name: 'id', label: '图片编号', width: -1},
                                {name: 'title', label: '图片名称', width: -1},
                                {name: 'pic', label: '图片', width: -1},
                                {name: 'tjtype', label: '图片类型', width: -1},
                                {name: 'tjdesc', label: '图片描述', width: -1},
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
    initTj();
    $("#searchBtn").click(function () {
        initTj();
    });
    //监听分页，修改当前页，条数
    $('#listPager').on('onPageChange', function (e, state, oldState) {
        currentPage = state.page;
        pageSize = state.recPerPage;
        initTj();
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
            var url = '/api/tujian/initUpdate';
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
                    if (data.result == 'success') {
                        $('#updateModal').modal('show', 'fit');
                        $('#tjPic').attr("src", data.tujian.pic);
                        $('#title').val(data.tujian.title);
                        $('#tjtype').val(data.tujian.tjtype);
                        $('#tjdesc').val(data.tujian.tjdesc);
                        $('#id').val(data.tujian.id);
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
    //修改保存
    $("#updateSaveBtn").click(function () {
        var updateValite = $("#updateForm").validate({
            rules: {
                pic: {
                    "required": true
                },
                title: {
                    "required": true
                },
                tjtype: {
                    "required": true
                },
                tjdesc: {
                    "required": true
                }
            },
            submitHandler: function (form) {
                var formdata = new FormData(form);//文件传输需要
                $.ajax({
                    url: "/api/tujian/update",
                    type: 'post',
                    dataType: 'JSON',
                    async: false,
                    cache: false,
                    contentType: false,
                    processData: false,
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
                            initTj();
                            updateValite.resetForm();
                        } else {
                            new $.zui.Messager(data.msg, {
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
//初始化详情
    $("#detailBtn").click(function () {
        // 获取数据表格实例
        var listDataGrid = $('#listDataGrid').data('zui.datagrid');
        // 获取当前已选中的行数据项
        var selectedItems = listDataGrid.getCheckItems();
        if (selectedItems.length == 0) {
            new $.zui.Messager('请选择要查看的记录', {
                type: 'warning',
                placement: 'center'
            }).show();
        } else if (selectedItems.length > 1) {
            new $.zui.Messager('请只选择一条要查看的记录', {
                type: 'warning',
                placement: 'center'
            }).show();
        } else {
            $("#detailForm")[0].reset();
            var url = '/api/tujian/detail';
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
                    if (result.success == '1') {
                        $('#detailModal').modal('show', 'fit');
                        $('#detailPic').attr('src', result.tujian.pic);
                        $('#detailTitle').html(result.tujian.title);
                        $('#detailTjdesc').html(result.tujian.tjdesc);
                        if (result.tujian.tjtype == 0) {
                            $('#detailTjtype').html('传统');
                        } else if (result.tujian.tjtype == 1) {
                            $('#detailTjtype').html('儿童');
                        } else if (result.tujian.tjtype == 2) {
                            $('#detailTjtype').html('海鲜');
                        }
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