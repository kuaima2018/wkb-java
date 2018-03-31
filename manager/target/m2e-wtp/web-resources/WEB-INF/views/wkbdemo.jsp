<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <%@page language="java" contentType="text/html; charset=UTF-8"
            pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
    <%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
    <c:set var="ctx" value="${pageContext.request.contextPath}"/>

    <title>工作宝后台管理</title>
    <script type="text/javascript"
            src="${ctx}/resources/js/jquery-1.8.0.min.js"></script>
    <script type="text/javascript"
            src="${ctx}/resources/js/jquery.easyui.min.js"></script>
    <script type="text/javascript"
            src="${ctx}/resources/js/jquery-div.js"></script>
    <script type="text/javascript"
            src="${ctx}/resources/js/wkbdemo.js"></script>

    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/icon.css">
    <%--<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/style.css">--%>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/demo.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/wkb.css">
</head>
<body>
<script type="text/javascript">
    var companyId=eval(${companyId});
    var companyManager=eval(${companyManager});//是否此公司管理员
    var systemManager=eval(${systemManager});;//是否超级管理员
    var industyValList=eval(${industyValList});
    var contextPath= '${ctx}';
</script>
<div style="margin:1px 0;"></div>
<div id="wkbhome" class="easyui-layout" data-options="fit:true"<%-- style="width:1024px;height:768px;--%>">
    <div data-options="region:'north'" style="height:26px">
        <%--<div style="height:2px"></div>--%>
        <div style="text-align:right">com.hmwkb.manager&nbsp;&nbsp;&nbsp;<a class="logout" title="退出" href="<c:url value="/j_spring_security_logout" />" ></a> &nbsp;&nbsp;&nbsp;</div>
    </div>
    <div data-options="region:'south',split:true" style="height:50px;"></div>
    <div data-options="region:'west',split:true" title="菜单栏" style="width:300px;">
        <div id="wkbCompany" class="easyui-accordion" data-options="fit:true,border:false">
            <div title="公司信息" data-options="selected:true" style="padding:10px;">
                <div id="wkb_companyExt" data-options="fit:true">
                    <table id="wkb_company" class="easyui-propertygrid" style="width: auto"></table>
                    <table id="">
                        <tr>
                            <td id="td_company_refresh"><div class="submitBtns"><a id="company_refresh" href="javascript:void(0)" >修改</a></div></td>
                            <td><div class="submitBtns"><a id="company_add" href="javascript:void(0)" >新增</a></div></td>
                        </tr>
                    </table>
                </div>
            </div>
            <div title="组织架构" data-options="selected:true" style="padding:10px;">
                <ul id="wkb_org" class="easyui-tree" />
            </div>
            <sec:authorize ifAllGranted="wkbSuperAdmin">
            <div title="公司操作" style="padding:10px">
                <ul id="leftPage">
                    <%--<li style="color: -webkit-link;"><a href="#" id="btn_company_apply_query" class="easyui-linkbutton" data-options="plain:true"><div style="color: -webkit-link;">公司申请处理</div></a></li>--%>
                    <li style="color: -webkit-link;"><a href="#" id="btn_company_query" class="easyui-linkbutton" data-options="plain:true"><div style="color: -webkit-link;">公司处理</div></a></li>
                    <%--<li ><a class="customerQuery" href="javascript:queryCompanyapply()">公司申请处理</a></li>
                    <li><a class="delivery" href="javascript:queryCompany()">公司处理</a></li>--%>
                </ul>
            </div>
            </sec:authorize>
            <sec:authorize ifAnyGranted="wkbSuperAdmin,wkbAdmin">
            <div title="管理员设置"  style="padding:10px;">
                <ul id="leftPage2">
                    <li style="color: -webkit-link;"><a href="#" id="btn_manager_modify" class="easyui-linkbutton" data-options="plain:true"><div style="color: -webkit-link;">设置管理员</div></a></li>
                    <%--<li style="color: -webkit-link;"><a href="#" id="btn_company_switch" class="easyui-linkbutton" data-options="plain:true"><div style="color: -webkit-link;">切换公司</div></a></li>--%>
                </ul>
            </div>
            </sec:authorize>
        </div>
    </div>
    <div data-options="region:'center',title:'操作管理',iconCls:'icon-ok'">
        <div id="wkb_tabs" class="easyui-tabs" data-options="fit:true,border:false,plain:true,tools:'#tab-tools'">
            <div title="后台操作指导" data-options="href:'<%=request.getContextPath()%>/resources/content.html'" style="padding:10px"></div>
        </div>
        <div id="tab-tools">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-remove'" onclick="clearWkbTabs()">关闭所有窗口</a>
        </div>

    </div>
</div>

<div id="companyOpDialog"  style="padding:10px" data-options="closed:true,modal:true,draggable:false,top:100" ></div>

<div id="orgMenu" class="easyui-menu" style="width:120px;display: none">
    <div onclick="addChildOrg()" data-options="iconCls:'icon-add'">增加下级部门</div>
    <div onclick="removeOrg()" data-options="iconCls:'icon-remove'">删除本部门</div>
    <div class="menu-sep"></div>
    <div onclick="addRole()" data-options="iconCls:'icon-add'">增加岗位</div>
    <%--<div onclick="removeRole()" data-options="iconCls:'icon-remove'">删除岗位</div>--%>
</div>

<div id="roleMenu" class="easyui-menu" style="width:120px;display: none">
    <%--<div onclick="addRole()" data-options="iconCls:'icon-add'">增加岗位</div>--%>
    <div onclick="removeRole()" data-options="iconCls:'icon-remove'">删除岗位</div>
    <div class="menu-sep"></div>

</div>

<div id="companyMenu" class="easyui-menu" style="width:120px;display: none">
    <div onclick="addChildOrg()" data-options="iconCls:'icon-add'">增加部门</div>
    <div class="menu-sep"></div>
</div>

<div id="dlgOrgAdd" class="easyui-window"
     closed="true" >
    <form id="fmOrgAdd" method="post" class="popWin_wrap">
        <table id="tabOrgAdd" class="editTable">
            <tr>
                <td ><label class="ml10" for="txt_parentOId">上级部门：</label></td>
                <td><input id="txt_parentOId" name="parentOId" readonly="readonly" /></td>
            </tr>
            <%--<tr>
                <td ><label class="ml10" for="txt_xoId">部门x代码：</label></td>
                <td><input id="txt_xoId" name="oId" class="easyui-validatebox textbox" data-options="required:true"/></td>
            </tr>--%>
            <tr>
                <td ><label class="ml10" for="txt_oName">部门名称：</label></td>
                <td><input id="txt_oName" name="oName" class="easyui-validatebox textbox" data-options="required:true"/></td>
            </tr>
        </table>
        <input type="hidden" id="h_parentOrgId"/>
        <input type="hidden" id="h_cId"/>
        <div class="submitBtns"><a id="orgAdd_confirm" href="javascript:void(0)" >确定</a>&nbsp;&nbsp;&nbsp;<a id="orgAdd_cancel"  href="javascript:void(0)" >取消</a></div>
    </form>
</div>
<div id="dlgOrgDel" class="easyui-window"
     closed="true" >
    <form id="fmOrgDel" method="post" class="popWin_wrap" novalidate>
        <table id="tabOrgDel" class="editTable">
            <tr>
                <td ><label class="ml10" for="txt_parentOIdDel">上级部门：</label></td>
                <td><input id="txt_parentOIdDel" name="parentOId" readonly="readonly" /></td>
            </tr>
            <%--<tr>
                <td ><label class="ml10" for="txt_xoIdDel">部门x代码：</label></td>
                <td><input id="txt_xoIdDel" name="oId" readonly="readonly"/></td>
            </tr>--%>
            <tr>
                <td ><label class="ml10" for="txt_oNameDel">部门名称：</label></td>
                <td><input id="txt_oNameDel" name="oName" readonly="readonly"/></td>
            </tr>
        </table>

        <input type="hidden" id="h_oId"/>
        <div class="submitBtns"><a id="orgDel_confirm" href="javascript:void(0)" >确定</a>&nbsp;&nbsp;&nbsp;<a id="orgDel_cancel" href="javascript:void(0)" >取消</a></div>
    </form>
</div>


<div id="dlgRoleOper" class="easyui-window"
     closed="true" >
    <form id="fmRoleOper" method="post" class="popWin_wrap">
        <table id="tabRoleOper" class="editTable">
            <%--<tr>
                <td ><label class="ml10" for="txt_xroleOId">部门代码：</label></td>
                <td><input id="txt_xroleOId" name="roleOId" readonly="readonly" /></td>
            </tr>--%>
            <tr>
                <td ><label class="ml10" for="txt_roleOName">部门名称：</label></td>
                <td><input id="txt_roleOName" name="roleOName" readonly="readonly" /></td>
            </tr>
            <%--<tr>
                <td ><label class="ml10" for="txt_xroleId">岗位代码：</label></td>
                <td><input id="txt_xroleId" name="roleId" class="easyui-validatebox textbox" data-options="required:true"/></td>
            </tr>--%>
            <tr>
                <td ><label class="ml10" for="txt_roleName">岗位名称：</label></td>
                <td><input id="txt_roleName" name="roleName" class="easyui-validatebox textbox" data-options="required:true"/></td>
            </tr>
            <tr>
                <td ><label class="ml10" for="txt_roleRight">管理权限：</label></td>
                <td><select class="easyui-combobox" id="txt_roleRight" options="editable:false" name="roleRight" style="width:200px;">
                    <option value="1">公司全体人员</option>
                    <%--<option value="2">上级部门</option>--%>
                    <option value="3">部门内所有员工</option>
                    <option value="4">所有下属员工</option>
                </select>
                </td>
            </tr>
        </table>
        <p><strong>公司全体人员：</strong>该权限是最高权限，一般为企业主、总经理、董事长；能查看所有员工的相关信息；<br>
            <strong>部门内所有员工：</strong>该权限管理本部门及下属部门，一般为部门最高领导。如某部门有正副部长、正副经理等，正部长有权限管理副部长，副部长无权管理正部长，则该权限分配给正部长；<br>
            <strong>所有下属员工：</strong>以上为例，该权限分配给副部长。该权限管理本部门所有下属员工及下属部门所有员工；</p>
        <input type="hidden" id="h_roleId"/>
        <div class="submitBtns"><a id="roleOper_confirm" class="submitBtns" href="javascript:void(0)" >确定</a>&nbsp;&nbsp;&nbsp;<a id="roleOper_cancel" class="submitBtns" href="javascript:void(0)" >取消</a></div>
    </form>
</div>

<div id="dlgUserModify" class="easyui-window" closed="true" >
    <form id="fmUserModify" method="post" class="popWin_wrap">
        <table id="tabUserModify" class="editTable">
            <tr>
                <td ><label class="ml10" for="txt_pass1">新密码：</label></td>
                <td><input type="password" id="txt_pass1" name="pass1" class="easyui-validatebox" data-options="required:true"/></td>
            </tr>
            <tr>
                <td ><label class="ml10" for="txt_pass2">确认密码：</label></td>
                <td><input type="password" id="txt_pass2" name="pass2" class="easyui-validatebox"
                           required="required" validType="equals['#txt_pass1']"/></td>
            </tr>

        </table>

        <div class="submitBtns"><a id="userModify_confirm" class="submitBtns" href="javascript:void(0)" >确定</a>&nbsp;&nbsp;&nbsp;
            <a id="userModify_cancel" class="submitBtns" href="javascript:void(0)" >取消</a></div>
        </form>
</div>

</body>
</html>