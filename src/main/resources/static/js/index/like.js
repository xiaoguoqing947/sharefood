function addViewCount(foodId) {
    $.ajax({
        url: '/liketable/addView',
        type: "POST",
        data: {"msId": foodId, "view": 1},
        success: function (data) {
            if (data.status == 'addSuccess') {
                console.log('add view success！');
            } else if (data.status == 'updateSuccess') {
                console.log('udpate view success！');
            } else {
                alert('fail！');
            }
        }
    });
}


function addLikeCount(foodId) {
    $.ajax({
        url: '/api/liketable/addLike',
        type: "POST",
        data: {"msId": foodId},
        success: function (data) {
            if (data.status == 'success') {
                var html='';
                html+='<div class="button _1" onclick="deleteLikeCount('+foodId+')"><span><div class="fa fa-thumbs-up">';
                html+='</div>取消点赞</span><div class="back"></div></div>';
                $('#zan').html(html);
            } else {
                toastr.info('请先登录后重试！');
                setTimeout('toLogin()', 1500);
            }
        }
    });
}

function deleteLikeCount(foodId) {
    $.ajax({
        url: '/api/liketable/deleteLike',
        type: "POST",
        data: {"msId": foodId},
        success: function (data) {
            if (data.status == 'success') {
                var html='';
                html+='<div class="button _1" onclick="addLikeCount('+foodId+')"><span><div class="fa fa-thumbs-o-up">';
                html+='</div>赞</span><div class="back"></div></div>';
                $('#zan').html(html);
            } else {
                toastr.info('请先登录后重试！');
                setTimeout('toLogin()', 1500);
            }
        }
    });
}

function addCollectCount(foodId) {
    $.ajax({
        url: '/api/liketable/addCollect',
        type: "POST",
        data: {"msId": foodId},
        success: function (data) {
            if (data.status == 'success') {
                var html='';
                html+='<div class="button _2" onclick="deleteCollectCount('+foodId+')"><span><div class="fa fa-star">';
                html+='</div>取消收藏</span><div class="back"></div></div>';
                $('#collect').html(html);
            } else {
                toastr.info('请先登录后重试！');
                setTimeout('toLogin()', 1500);
            }
        }
    });
}

function deleteCollectCount(foodId) {
    $.ajax({
        url: '/api/liketable/deleteCollect',
        type: "POST",
        data: {"msId": foodId},
        success: function (data) {
            if (data.status == 'success') {
                var html='';
                html+='<div class="button _2" onclick="addCollectCount('+foodId+')"><span><div class="fa fa-star-half-empty">';
                html+='</div>收藏</span><div class="back"></div></div>';
                $('#collect').html(html);
            } else {
                toastr.info('请先登录后重试！');
                setTimeout('toLogin()', 1500);
            }
        }
    });
}

/*跳转登录界面*/
function toLogin() {
    location.href = '/login';
}