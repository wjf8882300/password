$(function() {

    /**
     * 登录
     */
    $("#bntLogin").click(function () {
        var form = {};
        form.userName = $("#loginUserName").val();
        form.userPassword = $("#loginUserPassword").val();
        $.ajax({
            type: "post",
            url: "/user/login",
            contentType: "application/json",
            dataType: "json",
            data: JSON.stringify(form),
            cache: false,
            success: function (data) {
                if (data.code != 200) {
                    fail_prompt(data.message);
                }
                else {
                    $("#id").val(data.data);
                    $('#loginModal').modal('hide');
                    $('#authModal').modal('show');
                }
            },
            error: function (e) {
                fail_prompt("获取服务器数据异常!" + e);
            }
        });
    });

    /**
     * 校验
     */
    $("#bntAuth").click(function () {
        var form = {};
        form.id = $("#id").val();
        form.code = $("#code").val();
        $.ajax({
            type: "post",
            url: "/user/check",
            contentType: "application/json",
            dataType: "json",
            data: JSON.stringify(form),
            cache: false,
            success: function (data) {
                if (data.code != 200) {
                    fail_prompt(data.message);
                }
                else {
                    localStorage.setItem('token', data.data);
                    $('#authModal').modal('hide');
                    $("#bntExit").show();
                    $("#bntSearch").click();
                    $("#loginUserName").val('');
                    $("#loginUserPassword").val('');
                }
            },
            error: function (e) {
                fail_prompt("获取服务器数据异常!" + e);
            }
        });
    });

    /**
     * 退出
     */
    $("#bntExit").click(function () {
        $("table tbody").html('');
        var token = localStorage.getItem('token');
        if (token == null) {
            return;
        }
        localStorage.removeItem('token');
        $("#bntExit").hide();
        $("#bntSearch").click();
    });

    var bindEnter = function (formId, clickId) {
        $("#" + formId).bind("keydown", function (e) {
            // 兼容FF和IE和Opera
            var theEvent = e || window.event;
            var code = theEvent.keyCode || theEvent.which || theEvent.charCode;
            if (code == 13) {
                $("#" + clickId).click();
            }
        });

        $("#" + formId).submit(function (e) {
            return false;
        });
    };

    bindEnter('authForm', 'bntLogin');
    bindEnter('googleForm', 'bntAuth');
})