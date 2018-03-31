<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <%@page language="java" contentType="text/html; charset=UTF-8"
            pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
    <%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>

    <title>设置用户</title>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/resources/js/jquery-1.8.0.min.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/resources/js/jquery.easyui.min.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/resources/js/wkbCompanyOp.js"></script>


    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/icon.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/wkb.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/demo.css">
</head>
<body>
<script type="text/javascript">
    var industyValList=eval(${industyValList});
    var orgCompanyId=eval(${companyId});//当前用户对应的公司
    var company=eval(${company});
</script>
<div id="dlgComapnyOp" class="" >
    <form id="fmComapnyOp" method="post">
        <table id="tabComapnyOp" class="editTable">
            <tr id="tr_company_id">
                <td ><label class="ml10" for="txt_company_id">公司编号：</label></td>
                <td ><input id="txt_company_id" name="txt_company_id" style="width:250px" value="${company.cId}" readonly="readonly"/></td>
            </tr>
            <tr>
                <td ><label class="ml10" for="txt_company_name">公司全称：</label></td>
                <td ><input id="txt_company_name" style="width:250px" name="txt_company_name" class="easyui-validatebox"
                           data-options="required:true" value="${company.cName}" missingMessage="不能为空"/></td>
            </tr>
            <tr>
                <td ><label class="ml10" for="txt_company_mobile">公司电话：</label></td>
                <td><input id="txt_company_mobile" name="txt_company_mobile" style="width:250px" class="easyui-validatebox"
                           data-options="validType:'telephoneNumber'" value="${company.cMobile}" invalidMessage="电话号码不正确"/></td>
            </tr>
            <tr>
                <td ><label class="ml10" for="txt_company_address">公司地址：</label></td>
                <td><input id="txt_company_address" name="txt_company_address" style="width:250px" class="easyui-validatebox"
                            value="${company.cAddr}" /></td>
            </tr>
            <tr>
                <td ><label class="ml10" for="txt_company_zipcode">公司邮编：</label></td>
                <td><input id="txt_company_zipcode" name="txt_company_zipcode" style="width:254px" class="easyui-numberbox"
                            value="${company.cZipcode}"/></td>
            </tr>
            <tr>
                <td ><label class="ml10" for="txt_company_email">电子邮箱：</label></td>
                <td><input id="txt_company_email" name="txt_company_email" style="width:250px" class="easyui-validatebox"
                           data-options="validType:'email'" value="${company.cEmail}" invalidMessage="邮箱地址不正确"/></td>
            </tr>
            <tr>
                <td ><label class="ml10" for="txt_company_industy">公司行业：</label></td>
                <td><input id="txt_company_industy" value="${company.cIndustry}" name="txt_company_industy" style="width:254px" class="easyui-validatebox" /></td>
            </tr>
            <tr>
                <td ><label class="ml10" for="txt_company_remark">公司简介：</label></td>
                <td><textarea id="txt_company_remark" name="txt_company_remark" style="width:250px;height: 150px" >${company.cRemark}</textarea></td>
            </tr>

        </table>
        <table>
            <tr>
                <td></td>
                <td colspan="2"><div class="submitBtns" >
                    <a href="javascript:void(0);" id="btn_company_confirm">确定</a>
                    <a href="javascript:void(0);" id="btn_company_cancel">取消</a>
                </div></td>
            </tr>
        </table>
    </form>

</div>
 <script type="text/javascript">
     var modifyCompanyId=$('#txt_company_id').val();
     if(modifyCompanyId==null||modifyCompanyId=='')
         $('#tr_company_id').css({display:'none'});

     $('#btn_company_cancel').click(function(){
         $('#companyOpDialog').window('close');
     });

     $('#btn_company_confirm').click(function(){
         if(!$("#fmComapnyOp").form("validate"))
         {
             return;
         }

         var parms={
             'cId':$('#txt_company_id').val(),
             'cName':$('#txt_company_name').val(),
             'cTel':$('#txt_company_mobile').val(),
             'cAddr':$('#txt_company_address').val(),
             'cZipcode':$('#txt_company_zipcode').val(),
             'cEmail':$('#txt_company_email').val(),
             'cIndustry':$('#txt_company_industy').val(),
             'cRemark':$('#txt_company_remark').val()
         };

         var companyId=$('#txt_company_id').val();
         var url='/company/add';
         var bNew=true;
         if (companyId != null && companyId != '') {
             bNew = false;
             url = "/company/update";
         }

         //如果是新增公司并且用户已在一个公司
         if (bNew == true && orgCompanyId != null && orgCompanyId != '') {
             $.messager.confirm('提示', '<p style="font-size:14px;height:60px;line-height: 60px;color: #666;text-align: center;">确定退出原公司并新建公司?<p>',
                     function (r) {
                         if (r) {
                             $.ajax({
                                 url: url,
                                 type: 'POST',
                                 async: false,
                                 data: JSON.stringify(parms),
                                 dataType: "json",
                                 contentType: "application/json; charset=utf-8",
                                 success: function (data) {
                                     if (data != null) {
                                         if (data.succ != 'succ') {
                                             $.messager.alert('错误提示', data.msg);
                                         }
                                         else {
                                             $('#companyOpDialog').window('close');
                                             //重新刷新公司
                                             if (data.company != null) {
                                                 //如果是新增公司，那么切换
                                                 companyManager = true;
                                                 $('#td_company_refresh').css({display: ''});
                                                 switchCompany(data.company.cId);
                                             }
                                             else
                                             {
                                                 refreshCompany();
                                             }
                                         }
                                     }
                                 }
                             });
                         }
                     });
         }
         else {
             //更新公司或者新增公司（用户原先不在一个公司）
             $.ajax({
                 url: url,
                 type: 'POST',
                 async: false,
                 data: JSON.stringify(parms),
                 dataType: "json",
                 contentType: "application/json; charset=utf-8",
                 success: function (data) {
                     if (data != null) {
                         if (data.succ != 'succ') {
                             $.messager.alert('错误提示', data.msg);
                         }
                         else {
                             $('#companyOpDialog').window('close');
                             //重新刷新公司
                             if (data.company != null) {
                                 //如果是新增公司，那么切换
                                 companyManager = true;
                                 $('#td_company_refresh').css({display: ''});
                                 switchCompany(data.company.cId);
                             }
                             else {
                                //更新左侧菜单里面的公司信息
                                 refreshCompany();
                             }
                         }
                     }
                 }
             });
         }
     });

     $('#txt_company_zipcode').numberbox({
         filter:function(e)
         {
             if(e.which==46|| e.which==45)
                 return false;
         }
     });

     /*$('#txt_company_industy').combobox({
         data:industyValList,
         valueField: 'id',
         textField: 'val'
     });*/
 </script>
</body>
</html>
