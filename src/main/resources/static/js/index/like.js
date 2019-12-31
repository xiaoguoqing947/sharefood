function addViewCount(foodId) {
    $.ajax({
        url: '/liketable/addView',
        type: "POST",
        data: {"msId": foodId, "view": 1},
        success: function (data) {
            if (data.status == 'addSuccess') {
                alert('add view success！');
            } else if(data.status == 'updateSuccess'){
                alert('udpate view success！');
            }else{
                alert('fail！');
            }
        }
    });
}