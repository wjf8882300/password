$(function() {
    /**
     * 判断有没有登陆，没有登陆弹出登陆窗口
     */
    var judgeLogin = function(data) {
        if (data.code == 403) {
            $('#loginModal').modal('show');
        }
    };

    /**
     * 新增或者修改
     */
    $("#bntInsert").click(function () {
        var form = {};
        form.id = $("#projectId").val();
        form.projectType = $("#projectType").val();
        form.projectName = $("#projectName").val();
        form.userPassword = $("#userPassword").val();
        form.remark = $("#remark").val();

        $.ajax({
            type: "post",
            url: "/password/save",
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
            url: "/password/query/one",
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
                    $("#projectId").val(data.data.id);
                    $("#projectType").val(data.data.projectType);
                    $("#projectName").val(data.data.projectName);
                    $("#userPassword").val(data.data.userPassword);
                    $("#remark").val(data.data.remark);
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
            url: "/password/delete",
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
        if (localStorage.getItem('token') == null) {
            $('#loginModal').modal('show');
            return;
        }
        $("table tbody").html('');
        var form = {};
        form.projectType = $("#searchProjectType").val();
        form.projectName = $("#searchProjectName").val();
        $.ajax({
            type: "post",
            url: "/password/list",
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
                            + '<td>' + item.projectType + '</td>'
                            + '<td>' + item.projectName + '</td>'
                            + '<td>' + item.userPassword + '</td>'
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

    $("#searchProjectType").autosuggest({
        url: '/password/query/projectType',
        minLength: 1,
        maxNum: 5,
        align: 'left',
        method: 'get',
        queryParamName: 'query',
        headers: {
            "X-Token": localStorage.getItem('token')
        },
        split: ' ',
        highlight: true
    });

    $("#searchProjectName").autosuggest({
        url: '/password/query/projectType',
        minLength: 1,
        maxNum: 5,
        align: 'left',
        method: 'get',
        queryParamName: 'query',
        headers: {
            "X-Token": localStorage.getItem('token')
        },
        split: ' ',
        highlight: true
    });

    $("#bntInsertModal").click(function () {
        if (localStorage.getItem('token') == null) {
            $('#loginModal').modal('show');
        } else {
            $("#projectId").val('');
            $("#projectType").val('');
            $("#projectName").val('');
            $("#userPassword").val('');
            $("#remark").val('');
            $('#myModal').modal('show');
        }
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
})