<!DOCTYPE html>
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
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/resources/js/wkbcompany.js?v2"></script>


    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/icon.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/wkb.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/demo.css">
</head>
<body>
<div >
    <form id="fmCompanyQuery" method="post" class="popWin_wrap">
        <table id="tabCompanyQuery" class="editTable">
            <tr>
                <td ><label class="ml10" for="txt_cId">公司编号：</label></td>
                <td><input id="txt_cId" name="cId"/></td>

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


                <td>创建时间：</td>
                <td colspan="2"><input style="width:95px" class="easyui-datebox"  data-options="validType:'querydatevalidator'"
                                       type="text" id="tbBeginCreatedt" name="beginCreatedt">&nbsp;&nbsp;&nbsp;~&nbsp;&nbsp;&nbsp;<input
                        style="width:95px" class="easyui-datebox" type="text" id="tbEndCreatedt"  data-options="validType:'querydatevalidator'"
                        name="endCreatedt"></td>
            </tr>

        <tr>
            <td></td>
            <td></td>
            <td colspan="2"><div class="submitBtns" >
                <a href="javascript:void(0);" id="btn_companySearch">查询</a>&nbsp;&nbsp;
                <a href="javascript:void(0);" id="btn_companyReset">清空</a>&nbsp;&nbsp;
                <a href="javascript:void(0);" id="btn_companySwitch">切换</a>&nbsp;&nbsp;
            </div></td>
            </td>
        </tr>
        </table>
    </form>
</div>
<div style="margin:20px 0;"></div>

<table id="table_company" class="easyui-datagrid" title="公司列表" >
    <thead>
    </thead>
</table>

<div id="companyManagerDialog"  style="padding:10px" data-options="closed:true,modal:true,draggable:false,top:100" ></div>

</body>
</html>