window.onload = function () {
//要初始化的东西
    $("#context").empty();
    $("#context").load('/page/mainInfo.html');
};

function reloadMainRight(url) {
    $("#context").empty();
    $("#context").load(url);
}
