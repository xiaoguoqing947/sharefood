$(document).ready(function () {
    var token = $.zui.store.get("token");//Token值
    var queryListFun = function () {
        var queryListUrl = "/api/tag/queryListUrl";
        $.ajax({
            url: queryListUrl,
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
            success: function (result) {
                if (result.status == 'success') {
                    $('#listDataGrid').empty();
                    $('#listDataGrid').data('zui.datagrid', null);
                    $('#listDataGrid').datagrid({
                        checkable: true,
                        checkByClickRow: true,
                        dataSource: {
                            cols: [
                                {name: 'id', label: '标签ID', width: -1},
                                {name: 'tag', label: '标签名', width: -1},
                            ],
                            cache: false,
                            array: result.data
                        }
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
    queryListFun();
    /*TODO  标签的添加和删除*/
    $('#addSaveBtn').click(function () {
        var tagName = $("#tagName").val();
        if (tagName == ''){
            toastr.error('标签名不为空！');
        }else{
            $.ajax({
                type: "POST",
                url: '/api/tag/add',
                data: {tagName: tagName},
                success: function (data) {
                    if (data) {
                        $('#addModal').modal('hide', 'fit');
                        toastr.success('添加标签成功！');
                        queryListFun();
                    } else {
                        $('#addModal').modal('hide', 'fit');
                        toastr.error('添加标签失败！');
                    }
                }
            });
        }
    });
    /*删除按钮*/
    $("#deleteBtn").click(function(){
        // 获取数据表格实例
        var listDataGrid = $('#listDataGrid').data('zui.datagrid');
        // 获取当前已选中的行数据项
        var selectedItems = listDataGrid.getCheckItems();
        if (selectedItems.length == 0) {
            new $.zui.Messager('请选择要删除的记录', {
                type: 'warning',
                placement:'center'
            }).show();
        }
        else if (selectedItems.length > 1) {
            new $.zui.Messager('请只选择一条要删除的记录', {
                type: 'warning',
                placement:'center'
            }).show();
        }
        else {
            $('#deleteModal').modal('show', 'fit');
        }
    });
    //删除菜单
    $("#deleteTagBtn").click(function(){
        // 获取数据表格实例
        var listDataGrid = $('#listDataGrid').data('zui.datagrid');
        // 获取当前已选中的行数据项
        var selectedItems = listDataGrid.getCheckItems();
        var url = '/api/tag/delete';
        var param = 'id='+selectedItems[0].id;//请求到列表页面
        $.ajax({
            url:url,
            type:'post',
            dataType:'JSON',
            beforeSend:function(xhr){//设置请求头信息
                xhr.setRequestHeader("token",token);
            },
            headers:{'token':token},
            data:param,
            success:function(data){
                $('#deleteModal').modal('hide', 'fit');
                if (data.success=='1'){
                    new $.zui.Messager('删除成功!', {
                        type: 'success',
                        placement:'center'
                    }).show();
                    queryListFun();
                }
                else{
                    new $.zui.Messager("删除失败", {
                        type: 'warning',
                        placement:'center'
                    }).show();
                }
            },
            error:function(e){
                new $.zui.Messager('系统繁忙,请稍候再试!', {
                    type: 'warning',
                    placement:'center'
                }).show();
            }
        });
    });
});