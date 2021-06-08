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
        form.id = $("#scoreId").val();
        form.scoreTypeId = $("#scoreTypeId").val();
        form.scoreDate = $("#scoreDate").val();

        $.ajax({
            type: "post",
            url: "/score/save",
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
            url: "/score/query/one",
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
                    $("#scoreId").val(data.data.id);
                    $("#scoreTypeId").val(data.data.scoreTypeId);
                    $("#scoreDate").val(data.data.scoreDate);
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
            url: "/score/delete",
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
            url: "/score/list",
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
                    var total = 0;
                    var current = 0;
                    var sub = 0;
                    $.each(data.data, function (index, item) {
                        html += '<tr>'
                            //+ '<td>' + (index + 1) + '</td>'
                            + '<td>' + item.scoreDate + '</td>'
                            + '<td>' + item.scoreType + '</td>'
                            + '<td>' + item.scoreValue + '</td>'
                            //+ '<td>' + item.remark + '</td>'
                            + '<td><a href="#" onclick="javascript:modifyPassword(\'' + item.id + '\');">修改</a>'
                            + '<a href="#" onclick="javascript:deletePassword(\'' + item.id + '\');">删除</a></td>'
                            + '</tr>';
                        current += item.scoreValue;
                        total += (item.scoreValue > 0 ? item.scoreValue : 0);
                        sub += (item.scoreValue < 0 ? 0 - item.scoreValue : 0);
                    });
                    $("table tbody").html(html);
                    $("#total").html("当前积分：" + current + "， 累计："+total+"，扣除：" + sub);
                }
            },
            error: function (e) {
                fail_prompt("获取服务器数据异常!" + e);
            }
        });
    });

    /**
     *对Date的扩展，将 Date 转化为指定格式的String
     *月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
     *年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
     *例子：
     *(new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
     *(new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
     */
    Date.prototype.format = function (fmt) {
        var o = {
            "M+": this.getMonth() + 1, //月份
            "d+": this.getDate(), //日
            "h+": this.getHours(), //小时
            "m+": this.getMinutes(), //分
            "s+": this.getSeconds(), //秒
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度
            "S": this.getMilliseconds() //毫秒
        };
        if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    }

    $("#bntInsertModal").click(function () {
        $("#scoreId").val('');
        $("#scoreDate").val(new Date().format("yyyy-MM-dd"));
        $("#scoreTypeId").val('');
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

    bindScoreType = function () {
        $.ajax({
            type: "post",
            url: "/score/type/list",
            contentType: "application/json",
            dataType: "json",
            data: JSON.stringify({}),
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
                    $("#scoreTypeId")[0].options.length=0;
                    $("#scoreTypeId").append("<option value=''>请选择</option>");
                    $.each(data.data, function (index, item) {
                        $("#scoreTypeId").append("<option value='" + item.id + "'>" + item.scoreType + "(" + item.scoreValue + ")"+ "</option>");
                    });
                }
            },
            error: function (e) {
                fail_prompt("获取服务器数据异常!" + e);
            }
        });
    }

    bindScoreType();

    $("#bntSearch").click();

})