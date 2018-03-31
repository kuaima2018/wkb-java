<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <%@page language="java" contentType="text/html; charset=UTF-8"
            pageEncoding="UTF-8"%>
    <title>公司申请处理</title>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/resources/js/jquery-1.8.0.min.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/resources/js/jquery.easyui.min.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/resources/js/wkbcompanyapply.js"></script>


    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/icon.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/wkb.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/demo.css">
</head>
<body>
<div >
    <form id="fmCompanyapplyQuery" method="post" class="popWin_wrap">
        <table id="tabCompanyapplyQuery" class="editTable">
            <tr>
                <td ><label class="ml10" for="txt_name">公司名称：</label></td>
                <td><input id="txt_name" name="name"/></td>
                <td ><label class="ml10" for="txt_contact">联系人：</label></td>
                <td><input id="txt_contact" name="contact"/></td>
                <td ><label class="ml10" for="txt_mobile">用户手机：</label></td>
                <td><input id="txt_mobile" name="mobile" /></td>
            </tr>
            <tr>

                <td ><label class="ml10" for="txt_tel">用户电话：</label></td>
                <td><input id="txt_tel" name="tel"/></td>

                <td ><label class="ml10" for="txt_status">状态：</label></td>
                <td><select class="easyui-combobox" id="txt_status" options="editable:false" name="status" style="width:200px;">
                    <option value="0">已申请</option>
                    <option value="1">已加入</option>
                    <option value="" selected="selected"> </option>
                </select>
                </td>

                <td>申请时间：</td>
                <td colspan="2"><input style="width:95px" class="easyui-datebox"  data-options="validType:'querydatevalidator'"
                                       type="text" id="tbBeginApplydt" name="beginApplydt">&nbsp;&nbsp;&nbsp;~&nbsp;&nbsp;&nbsp;<input
                        style="width:95px" class="easyui-datebox" type="text" id="tbEndApplydt"  data-options="validType:'querydatevalidator'"
                        name="endApplydt"></td>
            </tr>

        <tr>
            <td></td>
            <td></td>
            <td colspan="2"><div class="submitBtns" >
                <a href="javascript:void(0);" id="btn_applySearch">查询</a>
                <a href="javascript:void(0);" id="btn_applyReset">清空</a>
            </div></td>
            </td>
            <td colspan="2"><div class="submitBtns" >
                <a href="javascript:void(0);" id="btn_applyAdd">增加</a>
                <a href="javascript:void(0);" id="btn_applyDelete">删除</a>
            </div></td>
        </tr>
        </table>
    </form>
</div>
<div style="margin:20px 0;"></div>

<table id="table_companyapply" class="easyui-datagrid" title="公司申请列表" >
    <thead>
    </thead>
</table>

</body>
</html>