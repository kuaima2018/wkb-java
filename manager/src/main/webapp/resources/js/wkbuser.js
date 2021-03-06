var parmRoleUser=null;
var parmRoleAddUser={xxx:1};
$(function(){

    parmRoleUser=
    {
        roleId:roleId
    };
    $('#table_user').datagrid({
        url:'/user/role/query/',
        striped: true,
        border: true,
        collapsible:true,
        height : 380,
        scrollbarSize:-1,
        columns:[[
            {field:'ck', width:20, checkbox: true},
            {field:'uId',title:'用户编号',width:100,editor:'text'},
            {field:'uName',title:'姓名',width:100,editor:'text'},
            {field:'uSex',title:'性别',width:100,editor:'text',formatter:function (value){
                if(value==1)
                    return '男';
                else if(value==0)
                    return '女';
                else
                    return '';
            }
            },
            {field:'uBrithday',title:'生日',width:100,editor:'text'},
            {field:'uMobile',title:'手机',width:100,editor:'text'},
            {field:'uTel',title:'电话',width:100,editor:'text'},
            {field:'uEmail',title:'邮箱',width:100,editor:'text'},
            {field:'uFax',title:'传真',width:100,editor:'text'},
            {field:'uAddr',title:'地址',width:100,editor:'text'},
            {field:'applytime',title:'申请时间',width:140,editor:'text'},
            {field:'jointime',title:'加入',width:140,editor:'text'}
        ]],
        remoteSort:false,
        singleSelect:false,
        checkOnSelect: true,
        selectOnCheck: true,
        pagination:true,
        queryParams: parmRoleUser,
        onLoadSuccess: function(data){
            if(data.error!=null)
            {
                $.messager.alert('系统提示', data.err,'error');
            }
        },
        onLoadError: function(){
            $.messager.alert('系统提示', "加载失败!",'error');
        },
        onUncheck:function(index,data){
            $(this).datagrid("clearSelections");
        }
    });

    if(companyManager==true||systemManager==true)
    {
        //wkt-超级管理员或者公司管理员才显示操作按钮
        $('#table_user').datagrid({
            toolbar: [{
                iconCls: 'icon-add',
                text:'增加',
                handler: function(){addRoleUsers();}
            },{
                text:'移除',
                iconCls: 'icon-remove',
                handler: function(){removeRoleUsers();}
            }]
        });
    }

    var p = $('#table_user').datagrid('getPager');
    $(p).pagination({
        pageSize: 10,
        pageList: [5,10,15],
        beforePageText: '',
        afterPageText: '页    共 {pages}页',
        displayMsg: '当前 {from} - {to} 记录 共 {total} 记录'
    });

    $('#btn_userSearch').click(function(){
        if(!$("#fmUserQuery").form("validate"))
        {
            return;
        }

        parmRoleUser={
            roleId:roleId,
            uId:getNotEmptyString($("#txt_userId").val()),
            uName:getNotEmptyString($("#txt_userName").val()),
            uMobile:getNotEmptyString($("#txt_userMobile").val()),
            uTel:getNotEmptyString($("#txt_userTel").val()),
            jointBeginTime:getNotEmptyString($("#tbBeginJointdt").datebox('getValue')),
            jointEndTime:getNotEmptyString($("#tbEndJointdt").datebox('getValue'))
        };

        $('#table_user').datagrid('load',parmRoleUser);


    });

    $('#btn_add_userCancel').click(function(){
        $('#dlgUserAdd').dialog('close');
    });

    $('#table_add_user').datagrid({
        url:'/user/companyApply/query',
        striped: true,
        border: true,
        collapsible:true,
        fitColumns : true,
        height : 380,
        width:700,
        scrollbarSize:-1,
        columns:[[
            {field:'ck', width:20, checkbox: true},
            {field:'uId',title:'用户编号',width:100,editor:'text'},
            {field:'uName',title:'姓名',width:100,editor:'text'},
            {field:'uSex',title:'性别',width:100,editor:'text',formatter:function (value){
                if(value==1)
                    return '男';
                else if(value==0)
                    return '女';
                else
                    return '';
            }
            },
            {field:'uBrithday',title:'生日',width:100,editor:'text'},
            {field:'uMobile',title:'手机',width:100,editor:'text'},
            {field:'uTel',title:'电话',width:100,editor:'text'},
            {field:'uEmail',title:'邮箱',width:100,editor:'text'},
            {field:'uFax',title:'传真',width:100,editor:'text'},
            {field:'uAddr',title:'地址',width:100,editor:'text'},
            {field:'applytime',title:'申请时间',width:140,editor:'text'}
        ]],
        remoteSort:false,
        singleSelect:false,
        checkOnSelect: true,
        selectOnCheck: true,
        pagination:true,
        queryParams: parmRoleAddUser,
        onBeforeLoad:function(data)
        {
            if(data==null||data.xxx!=null)
                return false;
        },
        onLoadSuccess: function(data){
            if(data.error!=null)
            {
                $.messager.alert('系统提示', data.err,'error');
            }
        },
        onLoadError: function(){
            $.messager.alert('系统提示', "加载失败!",'error');
        },
        onUncheck:function(index,data){
            $(this).datagrid("clearSelections");
        }
    });
    var p_add = $('#table_add_user').datagrid('getPager');
    $(p_add).pagination({
        pageSize: 10,
        pageList: [5,10,15],
        beforePageText: '',
        afterPageText: '页    共 {pages}页',
        displayMsg: '当前 {from} - {to} 记录 共 {total} 记录'
    });

    $('#btn_add_userSearch').click(function(){
        if(!$("#fmUserAdd").form("validate"))
        {
            return;
        }

        parmRoleAddUser={
            roleId:roleId,
            uId:getNotEmptyString($("#txt_add_userId").val()),
            uName:getNotEmptyString($("#txt_add_userName").val()),
            uMobile:getNotEmptyString($("#txt_add_userMobile").val()),
            uTel:getNotEmptyString($("#txt_add_userTel").val()),
            applyBeginTime:getNotEmptyString($("#add_tbBeginApplydt").datebox('getValue')),
            applyEndTime:getNotEmptyString($("#add_tbEndApplydt").datebox('getValue'))
        };

        $('#table_add_user').datagrid('load',parmRoleAddUser);


    });

    $('#btn_add_userConfirm').click(function(){
        var checkedRows = $('#table_add_user').datagrid('getChecked');
        if(checkedRows.length > 0)
        {
            var _list = new Array();
            for(var i=0;i<checkedRows.length ;i++)
            {
                _list.push(checkedRows[i].uId);
            }
            var json={
                uIdList:_list,
                roleId:roleId
            }
            var parms=JSON.stringify(json);
            $.ajax({
                type : 'POST',
                url : '/user/role/addUser',
                data : parms,
                dataType : "json",
                contentType: "application/json; charset=utf-8",
                success : function (data) {
                    if(data.succ=='succ')
                    {
                        $('#dlgUserAdd').dialog('close');
                        $('#btn_userSearch').click();
                    }
                    else
                    {
                        if(data.msg!=null)
                        {
                            $.messager.alert('系统提示',data.msg,'error');
                        }
                    }
                }
            });

        }else
        {
            $.messager.alert('系统提示', '未选中用户');
        }
    });

    $('#btn_add_userReset').click(function(){
        $('#fmUserAdd').form('reset');
    });

    $('#btn_userReset').click(function(){
        $('#fmUserQuery').form('reset');
    });


});

$.fn.datebox.defaults.formatter = function(date){
    var y = date.getFullYear();
    var m = date.getMonth()+1;
    var d = date.getDate();
    return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
};

$.extend($.fn.validatebox.defaults.rules,{
    querydatevalidator:{
        validator: function(value)
        {
            var date = $.fn.datebox.defaults.parser(value);
            var s = $.fn.datebox.defaults.formatter(date);
            return s==value;
        },message: '日期无效'
    }
});
$.extend($.fn.validatebox.defaults.rules, {
    number: {
        validator: function (value, param) {
            return /^\d+$/.test(value);
        },
        message: '请输入数字'
    }
});

function getNotEmptyString(str){
    if(str==null||str=="")
        return null;
    else
        return str;
}

function removeRoleUsers()
{
    var checkedRows = $('#table_user').datagrid('getChecked');
    if(checkedRows.length > 0)
    {
        var _list = new Array();
        for(var i=0;i<checkedRows.length ;i++)
        {
            _list.push(checkedRows[i].uId);
        }
        var json={
            uIdList:_list,
            roleId:roleId
        }
        var parms=JSON.stringify(json);
        $.ajax({
            type : 'POST',
            url : '/user/role/removeUser',
            data : parms,
            dataType : "json",
            contentType: "application/json; charset=utf-8",
            success : function (data) {
                if(data.succ=='succ')
                {
                    $('#btn_userSearch').click();
                }
                else
                {
                    if(data.msg!=null)
                    {
                        $.messager.alert('系统提示',data.msg,'error');
                    }
                }
            }
        });

    }else
    {
        $.messager.alert('系统提示', '未选中用户');
    }
}

function addRoleUsers()
{
    $('#fmUserAdd').form('reset');
    $('#btn_add_userSearch').click();
    $('#dlgUserAdd').window({title:'选择用户',
        width:720,
        height:600,
        top:50,
        iconCls:'',
        shadow: true,
        modal:true,
        closed:true,
        minimizable:false,
        maximizable:false,
        collapsible:false,
        draggable:false
    })
        .window('open');
}
