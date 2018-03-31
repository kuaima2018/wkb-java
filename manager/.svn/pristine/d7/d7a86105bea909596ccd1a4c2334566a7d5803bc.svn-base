
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<script type="text/javascript"
        src="<%=request.getContextPath()%>/resources/js/jquery-1.8.0.min.js"></script>
<script type="text/javascript"
        src="<%=request.getContextPath()%>/resources/js/jquery.easyui.min.js"></script>
<script type="text/javascript"
        src="<%=request.getContextPath()%>/resources/js/wkborg.js"></script>

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/icon.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/style.css">
<%--<link rel="stylesheet" type="text/css" href="http://www.jeasyui.com/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="http://www.jeasyui.com/easyui/demo/demo.css">--%>
</head>
<body>
	<input type="hidden" value="<%=request.getContextPath()%>"
		id="contextPath" />

	<%--<%@ include file="headbar.jsp"%>--%>

    <div class="easyui-panel" style="padding:5px">
		<%--<%@ include file="leftMenu.jsp"%>--%>
				<div class="panel-heading">
					<h3 class="panel-title">公司组织结构管理</h3>
				</div>
				<%--<div class="panel-body">--%>
                    <ul id="wkb_org" class="easyui-tree" />
				<%--</div>--%>
			</div>

    <div id="orgMenu" class="easyui-menu" style="width:120px;display: none">
        <div onclick="addChildOrg()" data-options="iconCls:'icon-add'">增加下级组织</div>
        <div onclick="removeOrg()" data-options="iconCls:'icon-remove'">删除本组织</div>
       <%-- <div class="menu-sep"></div>--%>
    </div>

    <div id="dlgOrgAdd" class="easyui-window"
         closed="true" >
        <form id="fmOrgAdd" method="post" class="popWin_wrap" novalidate>
            <table id="tabOrgAdd" class="editTable">
                <tr>
                    <td ><label class="ml10" for="txt_parentOId">父组织：</label></td>
                    <td><input id="txt_parentOId" name="parentOId" readonly /></td>
                </tr>
                <tr>
                    <td ><label class="ml10" for="txt_oId">组织代码：</label></td>
                    <td><input id="txt_oId" name="oId"/></td>
                </tr>
                <tr>
                    <td ><label class="ml10" for="txt_oName">组织名称：</label></td>
                    <td><input id="txt_oName" name="oName"/></td>
                </tr>
            </table>
            <input type="hidden" id="h_parentOrgId"/>
            <p class="winBtnsBar textC"><a id="orgAdd_confirm" class="window_btn" href="javascript:void(0)" >确定</a><span ><a id="orgAdd_cancel" class="window_btn ml10" href="javascript:void(0)" >取消</a></span></p>
        </form>
    </div>

</body>
</html>
