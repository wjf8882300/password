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
        form.id = $("#incomeId").val();
        form.incomeType = $("#incomeType").val();
        form.incomeDate = $("#incomeDate").val();
        form.amount = $("#amount").val();
        form.remark = $("#remark").val();

        $.ajax({
            type: "post",
            url: "/income/save",
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
            url: "/income/query/one",
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
                    $("#incomeId").val(data.data.id);
                    $("#incomeType").val(data.data.incomeType);
                    $("#incomeDate").val(data.data.incomeDate);
                    $("#amount").val(data.data.amount);
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
            url: "/income/delete",
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
        form.incomeType = $("#searchIncomeType").val();
        form.startDate = $("#searchStartDate").val();
        form.endDate = $("#searchEndDate").val();
        $.ajax({
            type: "post",
            url: "/income/list",
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
                    var type1 = 0;
                    var type2 = 0;
                    var type3 = 0;
                    $.each(data.data, function (index, item) {
                        html += '<tr>'
                            //+ '<td>' + (index + 1) + '</td>'
                            + '<td>' + (item.incomeType == 1 ? "工资" : (item.incomeType == 2 ? "奖金": "外快")) + '</td>'
                            + '<td>' + item.incomeDate + '</td>'
                            + '<td>' + item.amount + '</td>'
                            //+ '<td>' + item.remark + '</td>'
                            + '<td><a href="#" onclick="javascript:modifyPassword(\'' + item.id + '\');">修改</a>'
                            + '<a href="#" onclick="javascript:deletePassword(\'' + item.id + '\');">删除</a></td>'
                            + '</tr>';
                        if(item.incomeType == 1) {
                            type1 += item.amount;
                        }
                        if(item.incomeType == 2) {
                            type2 += item.amount;
                        }
                        if(item.incomeType == 3) {
                            type3 += item.amount;
                        }
                    });
                    $("table tbody").html(html);
                    $("#total").html("工资：" + type1.toFixed(2) + "元，奖金：" + type2.toFixed(2) + "元, 外快：" + type3.toFixed(2) + "元" );
                }
            },
            error: function (e) {
                fail_prompt("获取服务器数据异常!" + e);
            }
        });
    });

    $("#bntInsertModal").click(function () {
        if (localStorage.getItem('token') == null) {
            $('#loginModal').modal('show');
        } else {
            $("#incomeId").val('');
            //$("#incomeType").val('');
            $("#incomeDate").val('');
            $("#amount").val('');
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

    /**
     * 日期下拉框
     * @param id
     */
    var inputDate = function (id) {
        xvDate({
            targetId: id, //时间写入对象的id
            triggerId: id, //触发事件的对象id
            alignId: id, //日历对齐对象
            hms: "off", // 关闭时分秒
            format: "-", //时间格式 默认'YYYY-MM-DD HH:MM:SS'
            min: "2018-01-01", //最小时间
            max: "2050-12-31" //最大时间
        });
    };

    bindEnter('passwordForm', 'bntSearch');

/*    inputDate("incomeDate");
    inputDate("searchStartDate");
    inputDate("searchEndDate");*/
})