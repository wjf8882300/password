$(function() {
    /**
     * 判断有没有登陆，没有登陆弹出登陆窗口
     */
    var judgeLogin = function(data) {
        /*if (data.code == 403) {
            $('#loginModal').modal('show');
        }*/
    };

    /**
     * 新增或者修改
     */
    $("#bntInsert").click(function () {
        var form = {};
        form.id = $("#scoreTypeId").val();
        form.scoreType = $("#scoreType").val();
        form.scoreValue = $("#scoreValue").val();
        $.ajax({
            type: "post",
            url: "/score/type/save",
            contentType: "application/json",
            dataType: "json",
            data: JSON.stringify(form),
            cache: false,
            headers: {
                "X-Token": localStorage.getItem('token')
            },
            success: function (data) {
                if (data.code == 403) {
                    judgeLogin(data);
                } else if (data.code != 200) {
                    fail_prompt(data.message);
                }
                else {
                    $('#myModal').modal('hide');
                    $("#bntSearch").click();
                }
            },
            error: function (e) {
                fail_prompt("获取服务器数据异常!" + e);
            }
        });
    });

    /**
     * 修改modal
     */
    modifyPassword = function (id) {
        var form = {};
        form.id = id;
        $.ajax({
            type: "get",
            url: "/score/type/query/one",
            dataType: "json",
            data: form,
            cache: false,
            headers: {
                "X-Token": localStorage.getItem('token')
            },
            success: function (data) {
                if (data.code == 403) {
                    judgeLogin(data);
                } else if (data.code != 200) {
                    fail_prompt(data.message);
                }
                else {
                    $("#scoreTypeId").val(data.data.id);
                    $("#scoreType").val(data.data.scoreType);
                    $("#scoreValue").val(data.data.scoreValue);
                    $('#myModal').modal('show');
                }
            },
            error: function (e) {
                fail_prompt("获取服务器数据异常!" + e);
            }
        });
    }

    /**
     * 删除
     */
    deletePassword = function (id) {
        var form = {};
        form.id = id;
        $.ajax({
            type: "post",
            url: "/score/type/delete",
            contentType: "application/json",
            dataType: "json",
            data: JSON.stringify(form),
            cache: false,
            headers: {
                "X-Token": localStorage.getItem('token')
            },
            success: function (data) {
                if (data.code == 403) {
                    judgeLogin(data);
                } else if (data.code != 200) {
                    fail_prompt(data.message);
                }
                else {
                    $("#bntSearch").click();
                }
            },
            error: function (e) {
                fail_prompt("获取服务器数据异常!" + e);
            }
        });
    }

    /**
     * 查询
     */
    $("#bntSearch").click(function () {
        $("table tbody").html('');
        var form = {};
        form.scoreType = $("#searchScoreType").val();
        $.ajax({
            type: "post",
            url: "/score/type/list",
            contentType: "application/json",
            dataType: "json",
            data: JSON.stringify(form),
            cache: false,
            headers: {
                "X-Token": localStorage.getItem('token')
            },
            success: function (data) {
                if (data.code == 403) {
                    judgeLogin(data);
                } else if (data.code != 200) {
                    fail_prompt(data.message);
                }
                else {
                    var html = '';
                    $.each(data.data, function (index, item) {
                        html += '<tr>'
                            //+ '<td>' + (index + 1) + '</td>'
                            + '<td>' + item.scoreType + '</td>'
                            + '<td>' + item.scoreValue + '</td>'
                            //+ '<td>' + item.remark + '</td>'
                            + '<td><a href="#" onclick="javascript:modifyPassword(\'' + item.id + '\');">修改</a>'
                            + '<a href="#" onclick="javascript:deletePassword(\'' + item.id + '\');">删除</a></td>'
                            + '</tr>';
                    });
                    $("table tbody").html(html);
                }
            },
            error: function (e) {
                fail_prompt("获取服务器数据异常!" + e);
            }
        });
    });

    $("#bntInsertModal").click(function () {
        $("#scoreTypeId").val('');
        $("#scoreType").val('');
        $("#scoreValue").val('');
        $('#myModal').modal('show');
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

    bindEnter('passwordForm', 'bntSearch');

    $("#bntSearch").click();
})