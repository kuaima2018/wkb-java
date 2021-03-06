<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <%@page language="java" contentType="text/html; charset=UTF-8"
            pageEncoding="UTF-8"%>
    <title>Complex Layout - jQuery EasyUI Demo</title>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/resources/js/jquery-1.8.0.min.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/resources/js/jquery.easyui.min.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/resources/js/wkbuser.js"></script>


    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/icon.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/wkb.css">
    <%--<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/style.css">--%>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/demo.css">
</head>
<body>
<script type="text/javascript">
    var roleId=eval(${roleId });
    var companyManager=eval(${companyManager});//是否此公司管理员
    var systemManager=eval(${systemManager});;//是否超级管理员
</script>
<div >
    <form id="fmUserQuery" method="post" class="popWin_wrap">
        <table id="tabUserQuery" class="editTable">
            <tr>
                <td ><label class="ml10" for="txt_userId">用户编号：</label></td>
                <td><input id="txt_userId" name="userId" class="easyui-validatebox" validType="number" invalidMessage="请输入整数"/></td>
                <td ><label class="ml10" for="txt_userName">用户名称：</label></td>
                <td><input id="txt_userName" name="userName"/></td>
                <td ><label class="ml10" for="txt_userMobile">用户手机：</label></td>
                <td><input id="txt_userMobile" name="userMobile" /></td>
            </tr>
            <tr>

                <td ><label class="ml10" for="txt_userTel">用户电话：</label></td>
                <td><input id="txt_userTel" name="userTel"/></td>
                <td>加入时间：</td>
                <td colspan="2"><input style="width:95px" class="easyui-datebox"  data-options="validType:'querydatevalidator'"
                                       type="text" id="tbBeginJointdt" name="beginJointdt">&nbsp;&nbsp;&nbsp;~&nbsp;&nbsp;&nbsp;<input
                        style="width:95px" class="easyui-datebox" type="text" id="tbEndJointdt"  data-options="validType:'querydatevalidator'"
                        name="endJointdt"></td>
            </tr>

        <tr>
            <td></td>
            <td></td>
            <td colspan="2"><div class="submitBtns" >
                <a href="javascript:void(0);" id="btn_userSearch">查询</a>
                <a href="javascript:void(0);" id="btn_userReset">清空</a>
            </div></td>
        </tr>
        </table>
    </form>
</div>
<div style="margin:20px 0;"></div>

<table id="table_user" class="easyui-datagrid" title="岗位用户列表" >
    <thead>
    </thead>
</table>


<div id="dlgUserAdd" class="easyui-window"
     closed="true" >
    <form id="fmUserAdd" method="post" class="popWin_wrap">
        <table id="tabUserAdd" class="editTable">
            <tr>
                <td ><label class="ml10" for="txt_add_userId">用户编号：</label></td>
                <td><input id="txt_add_userId" name="add_userId" class="easyui-validatebox" validType="number" invalidMessage="请输入整数"/></td>
                <td ><label class="ml10" for="txt_add_userName">用户名称：</label></td>
                <td><input id="txt_add_userName" name="add_userName"/></td>
                <td ><label class="ml10" for="txt_add_userMobile">用户手机：</label></td>
                <td><input id="txt_add_userMobile" name="add_userMobile" /></td>
            </tr>
            <tr>

                <td ><label class="ml10" for="txt_add_userTel">用户电话：</label></td>
                <td><input id="txt_add_userTel" name="add_userTel"/></td>
                <td>加入时间：</td>
                <td colspan="2"><input style="width:95px" class="easyui-datebox"  data-options="validType:'querydatevalidator'"
                                       type="text" id="add_tbBeginApplydt" name="add_beginApplydt">&nbsp;&nbsp;&nbsp;~&nbsp;&nbsp;&nbsp;<input
                        style="width:95px" class="easyui-datebox" type="text" id="add_tbEndApplydt"  data-options="validType:'querydatevalidator'"
                        name="add_endApplydt"></td>
            </tr>
        </table>
        <tr>
            <td></td>
            <td colspan="2"><div class="submitBtns" >
                <a href="javascript:void(0);" id="btn_add_userSearch">查询</a>
                <a href="javascript:void(0);" id="btn_add_userReset">清空</a>
            </div></td>
            <td colspan="2"><div class="submitBtns" >
                <a href="javascript:void(0);" id="btn_add_userConfirm">确定</a>
                <a href="javascript:void(0);" id="btn_add_userCancel">取消</a>
            </div></td>
        </tr>
    </form>

    <table id="table_add_user" class="easyui-datagrid" title="申请用户列表" >
        <thead>
        </thead>
    </table>
</div>

</body>
</html>