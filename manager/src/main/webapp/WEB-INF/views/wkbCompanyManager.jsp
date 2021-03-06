<%--<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <%@page language="java" contentType="text/html; charset=UTF-8"
            pageEncoding="UTF-8"%>
    <title>公司处理</title>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/resources/js/jquery-1.8.0.min.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/resources/js/jquery.easyui.min.js"></script>
    &lt;%&ndash;<script type="text/javascript"
            src="<%=request.getContextPath()%>/resources/js/wkbCompanyManager.js"></script>&ndash;%&gt;


    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/icon.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/wkb.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/demo.css">
</head>
<body>--%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<script type="text/javascript">
    var companyId=eval(${companyId});
</script>

<div id="dlgUserConfig" class="" >
    <form id="fmUserConfig" method="post">
        <table id="tabUserConfig" class="editTable">
            <tr>
                <td ><label class="ml10" for="txt_config_userId">用户编号：</label></td>
                <td><input id="txt_config_userId" name="config_userId" class="easyui-validatebox" validType="number" invalidMessage="请输入整数"/></td>
                <td ><label class="ml10" for="txt_config_userName">用户名称：</label></td>
                <td><input id="txt_config_userName" name="config_userName"/></td>
                <td ><label class="ml10" for="txt_config_userMobile">用户手机：</label></td>
                <td><input id="txt_config_userMobile" name="config_userMobile" /></td>
            </tr>
            <tr>

                <td ><label class="ml10" for="txt_config_userTel">用户电话：</label></td>
                <td><input id="txt_config_userTel" name="config_userTel"/></td>
                <td>加入时间：</td>
                <td colspan="3"><input style="width:95px" class="easyui-datebox"  data-options="validType:'querydatevalidator'"
                                       type="text" id="add_tbBeginJointdt" name="add_beginJointdt">&nbsp;&nbsp;&nbsp;~&nbsp;&nbsp;&nbsp;<input
                        style="width:95px" class="easyui-datebox" type="text" id="add_tbEndJointdt"  data-options="validType:'querydatevalidator'"
                        name="add_endJointdt"></td>

                </td>
            </tr>
            <tr>
                <td ><label class="ml10" for="txt_config_manager">角色：</label></td>
                <td><select class="easyui-combobox" id="txt_config_manager" options="editable:false" name="config_manager" style="width:200px;">
                    <option value="0">员工</option>
                    <option value="1">管理员</option>
                </select>
            </tr>
        </table>
        <table>
            <tr>
            <td></td>
            <td colspan="2"><div class="submitBtns" >
                <a href="javascript:void(0);" id="btn_config_userSearch">查询</a>
                <a href="javascript:void(0);" id="btn_config_userReset">清空</a>
            </div></td>
            <td colspan="2"><div class="submitBtns" >
                <a href="javascript:void(0);" id="btn_config_userConfirm">置成管理员</a>
                <a href="javascript:void(0);" id="btn_config_userCancel">撤销管理员</a>
            </div></td>
            </tr>
            <tr>
                <td colspan="4">该功能可以新增和删除管理员；被新增的管理员，可以设置公司组织架构：如部门、岗位、分配人员权限等操作；</td>
            </tr>
        </table>
    </form>

    <table id="table_config_user" class="easyui-datagrid" title="用户列表" >
        <thead>
        </thead>
    </table>

</div>

<%--</body>--%>
<script>
    var parmCompanyUserQuery={xxx:1};
    //$(function(){
        $('#table_config_user').datagrid({
            url:'/company/user/query',
            striped: true,
            border: true,
            collapsible:true,
            fitColumns : true,
            height : 360,
            width:750,
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
                {field:'jointime',title:'加入时间',width:140,editor:'text'},
                {field:'uAdmin',title:'角色',width:140,editor:'text',formatter:function (value){
                    if(value==1)
                        return '管理员';
                    else if(value==2)
                        return '超级管理员';
                    else
                        return '';
                }
                }
            ]],
            remoteSort:false,
            singleSelect:false,
            checkOnSelect: true,
            selectOnCheck: true,
            pagination:true,
            queryParams: parmCompanyUserQuery,
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
        var p_add = $('#table_config_user').datagrid('getPager');
        $(p_add).pagination({
            pageSize: 10,
            pageList: [5,10,15],
            beforePageText: '',
            afterPageText: '页    共 {pages}页',
            displayMsg: '当前 {from} - {to} 记录 共 {total} 记录'
        });

        $('#btn_config_userSearch').click(function(){
            if(!$("#fmUserConfig").form("validate"))
            {
                return;
            }

            parmCompanyUserQuery={
                cId: getNotEmptyString(companyId),
                uId:getNotEmptyString($("#txt_config_userId").val()),
                uName:getNotEmptyString($("#txt_config_userName").val()),
                uMobile:getNotEmptyString($("#txt_config_userMobile").val()),
                uTel:getNotEmptyString($("#txt_config_userTel").val()),
                applyBeginTime:getNotEmptyString($("#add_tbBeginJointdt").datebox('getValue')),
                applyEndTime:getNotEmptyString($("#add_tbEndJointdt").datebox('getValue')),
                uAdmin:getNotEmptyString($('#txt_config_manager').combobox('getValue'))
            };

            $('#table_config_user').datagrid('load',parmCompanyUserQuery);


        });

        $('#btn_config_userConfirm').click(function(){
            setUserRoles(true,'是否确定设置选中的用户为管理员？');
        });

        $('#btn_config_userCancel').click(function(){
            setUserRoles(false,'是否撤销选中的管理员？');
        });

   // });

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

    function getNotEmptyString(str){
        if(str==null||str=="")
            return null;
        else
            return str;
    }

    function setUserRoles(bAdmin,msg)
    {
        var checkedRows = $('#table_config_user').datagrid('getChecked');
        if(checkedRows.length > 0)
        {
            var _list = new Array();
            for(var i=0;i<checkedRows.length ;i++)
            {
                _list.push(checkedRows[i].uId);
            }
            var json={
                uIdList:_list,
                cId:companyId
            }

            $.messager.confirm('警告',msg,function(r)
            {
                if(r==true)
                {
                    var parms=JSON.stringify(json);
                    updateUserRoles(bAdmin,parms);
                }
            });
        }else
        {
            $.messager.alert('系统提示', '未选中用户');
        }
    }

    function updateUserRoles(bAdmin,parms)
    {
        var url=null;
        if(bAdmin==true)
        {
            url='/company/user/adminAdd';
        }
        else
        {
            url='/company/user/adminDel';
        }

        $.ajax({
            type : 'POST',
            url : url,
            data : parms,
            dataType : "json",
            contentType: "application/json; charset=utf-8",
            success : function (data) {
                if(data.succ=='succ')
                {
                    $('#table_config_user').datagrid('reload',parmCompanyUserQuery);
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
    }
</script>
<%--
</html>--%>
