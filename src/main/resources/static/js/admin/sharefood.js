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
                var htmlTag='';
                for(var i = 0; i < result.tags.length; i++){
                    htmlTag+='<option value="' + result.tags[i].id + '">' + result.tags[i].tag + '</option>';
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
});