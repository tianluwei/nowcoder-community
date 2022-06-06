$(function () {
    $("#publishBtn").click(publish);
});

function publish() {
    $("#publishModal").modal("hide");

    //获取标题和内容
    var title=$("#recipient-name").val();
    var content=$("#message-text").val();
    //发送异步请求，POST
    //1、路径  2、发送的内容 3、回调函数，处理返回的结果。结果是字符串，需要改为对象。状态和提示消息。
    $.post(
        CONTEXT_PATH + "/discuss/add",
        {"title":title,"content":content},
        function(data){
            data=$.parseJSON(data);
        //    在提示框中显示返回的消息
            $("#hintBody").text(data.msg);

            //显示提示框
            $("#hintModal").modal("show");
            //2秒后，自动隐藏提示框
            setTimeout(function () {
                $("#hintModal").modal("hide");
            //    刷新页面
                if(data.code==0){
                    window.location.reload();
                }
            }, 2000);
        }
    );


}