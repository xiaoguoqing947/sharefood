$(document).ready(function () {
    var token = $.zui.store.get("token");
    var initCustomerListInfo = function () {
        var url = '/api/sysadmin/customerInfo';
        $.ajax({
            url: url,
            type: 'post',
            dataType: 'JSON',
            beforeSend: function (xhr) {//设置请求头信息
                xhr.setRequestHeader("token", token);
            },
            headers: {'token': token},
            success: function (result) {
                console.log(result.customers);
                if (result.status == 'success') {
                    var html = '';
                    for (var i = 0; i < result.customers.length; i++) {
                        html += '<div class="col-lg-12"><a  onclick="delCustomer('+result.customers[i].id+')" href="javascript:void(0)"\n' +
                            '                                                  class="message card px-5 py-3 mb-4 bg-hover-gradient-primary no-anchor-style">\n' +
                            '                            <div class="row">\n' +
                            '                                <div class="col-lg-3 d-flex align-items-center flex-column flex-lg-row text-center text-md-left">\n' +
                            '                                    <strong class="h5 mb-0">' + ((result.customers[i].age != null && result.customers[i].age != "") ? result.customers[i].age : "**") + '<sup\n' +
                            '                                            class="smaller text-gray font-weight-normal">岁</sup></strong><img\n' +
                            '                                        src="' + result.customers[i].headpic + '" alt="..." style="max-width: 3rem"\n' +
                            '                                        class="rounded-circle mx-3 my-2 my-lg-0">\n' +
                            '                                    <h6 class="mb-0">' + result.customers[i].username + '</h6>\n' +
                            '                                </div>\n' +
                            '                                <div class="col-lg-9 d-flex align-items-center flex-column flex-lg-row text-center text-md-left">\n' +
                            '                                    <div class="bg-gray-100 roundy px-4 py-1 mr-0 mr-lg-3 mt-2 mt-lg-0 text-dark exclode">\n' +
                            '                                        ' + ((result.customers[i].name != null && result.customers[i].name != "") ? result.customers[i].name : "**") + '\n' +
                            '                                    </div>\n' +
                            '                                    <p class="mb-0 mt-3 mt-lg-0">' + ((result.customers[i].address != null && result.customers[i].address != "") ? result.customers[i].address : "****") + '</p>\n' +
                            '                                </div>\n' +
                            '                            </div>\n' +
                            '                        </a></div>';
                    }
                    $('#customerListCard').html(html);
                    $('#headText').html('<div class="text-center text-info">该系统共有用户('+result.customers.length+')人</div>');
                } else {
                    toastr.info('token验证失败，请重新登录！');
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
    initCustomerListInfo();
    $("#deleteCustomerBtn").click(function () {
        $.ajax({
            url: '/api/customer/delete',
            type: 'post',
            dataType: 'JSON',
            beforeSend: function (xhr) {//设置请求头信息
                xhr.setRequestHeader("token", token);
            },
            headers: {'token': token},
            data: {
                id:$('#customerId').val()
            },
            success: function (data) {
                $('#deleteModal').modal('hide', 'fit');
                if (data.success == '1') {
                    toastr.info('修改成功');
                    initCustomerListInfo();
                } else {
                    toastr.warning('修改失败');
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
});
function delCustomer(id){
    // console.log('这是用户 id='+id)
    $('#customerId').val(id);
    $('#deleteModal').modal('show', 'fit');
}