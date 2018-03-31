
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="../head.jsp"%>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/datatable/css/jquery.dataTables_themeroller.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/datatable/css/demo_page.css">

<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/datatable/css/demo_table.css">

<!-- 
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/datatable/css/jquery.jquery.dataTables.css">-->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/datatable/css/demo_table_jui.css">

<link
	href="<%=request.getContextPath()%>/resources/css/smoothness/jquery-ui-1.8.4.custom.css"
	rel="stylesheet">

<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/jquery.datatables.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/jquery.jeditable.js"></script>
<script
	src="<%=request.getContextPath()%>/resources/js/jquery-ui-1.10.4.custom.js"></script>

<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/jquery.treeview.css">
<script
	src="<%=request.getContextPath()%>/resources/js/jquery.cookie.js"
	type="text/javascript"></script>
<script
	src="<%=request.getContextPath()%>/resources/js/jquery.treeview.js"
	type="text/javascript"></script>

<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/left-menu.css">
<script type="text/javascript" charset="utf-8">
	$(function() {
		var oTable;
		$("#DataTables_Table_0_length").addClass("col-md-3");

		$("#DataTables_Table_0_length")
				.before(
						"<div class='col-md-5'><button class='btn btn-primary'>增加</button><button class='btn btn-primary col-md-offset-2'>删除</button></div>");
		$("#DataTables_Table_0_filter").addClass("col-md-4");
		$("#DataTables_Table_0_info").addClass("col-md-6");
		//$("#DataTables_Table_0_paginate").addClass("col-md-6");
		//$("#DataTables_Table_0_paginate").css("text-align", "right");
		$("#orgTable tbody tr").click(function(e) {
			if ($(this).hasClass('row_selected')) {
				$(this).removeClass('row_selected');
			} else {
				oTable.$('tr.row_selected').removeClass('row_selected');
				$(this).addClass('row_selected');
			}
		});
		function fnGetSelected(oTableLocal) {
			return oTableLocal.$('tr.row_selected');
		}

		oTable = $('#orgTable').dataTable({
			"bJQueryUI" : true,
			"sPaginationType" : "full_numbers",
			"iDisplayLength" : 10,
			"bPaginate" : true,
			"oLanguage" : {
				"sLengthMenu" : "每页显示 _MENU_ 条记录",
				"sZeroRecords" : "抱歉， 没有找到",
				"sInfo" : "从 _START_ 到 _END_ /共 _TOTAL_ 条数据",
				"sInfoEmpty" : "没有数据",
				"sInfoFiltered" : "(从 _MAX_ 条数据中检索)",
				"oPaginate" : {
					"sFirst" : "首页",
					"sPrevious" : "前一页",
					"sNext" : "后一页",
					"sLast" : "尾页"
				}
			}
		});

		var name = $("#name");
		var orgId = $("#orgId");
		var fatherId = $("#fatherId");
		var allFields = $([]).add(orgId).add(name).add(fatherId);
		var tips = $(".validateTips");
		
		var orgIdUpdate = $("#orgIdUpdate");
		var nameUpdate = $("#nameUpdate");
		var fatherIdUpdate = $("#fatherIdUpdate");
		var allFieldsUpdate = $([]).add(orgIdUpdate).add(nameUpdate).add(fatherIdUpdate);
		var tipsUpdate = $(".validateTipsUpdate");
		
		var contextPath = $("#contextPath").val();

		function updateTips(tp, t) {
			tp.text(t).addClass("ui-state-highlight");
			setTimeout(function() {
				tp.removeClass("ui-state-highlight", 100);
			}, 500);
		}

		function checkLength(tip, o, n, min, max) {
			if (o.val().length > max || o.val().length < min) {
				o.addClass("ui-state-error");
				updateTips(tip, "Length of " + n + " must be between " + min
						+ " and " + max + ".");
				return false;
			} else {
				return true;
			}
		}

		function addOrg(org) {
			var num = $("#orgTable tbody tr").size();
			var trClass = "even";
			if (num % 2 == 0) {
				trClass = "odd";
			}
			$("#orgTable tbody").append(
					"<tr class='"+ trClass + "'><td>" + org.id + "</td><td>" + org.orgId + "</td><td>"
							+ org.name + "</td><td>" + org.fatherId
							+ "</td></tr>");
		}
		$("#dialog-form-update").dialog({
			autoOpen : false,
			height : 300,
			width : 400,
			modal : true,
			buttons : {
				"更新" : function() {
					var bValid = true;

					allFieldsUpdate.removeClass("ui-state-error");
					bValid = bValid && checkLength(tipsUpdate, orgIdUpdate, "父组织ID", 1, 11);
					bValid = bValid && checkLength(tipsUpdate, nameUpdate, "组织名称", 2, 16);
					bValid = bValid && checkLength(tipsUpdate, fatherIdUpdate, "父组织ID", 1, 11);
					if (bValid) {
						var selectedNode = $(".row_selected td");
						var org = {
							"id" : selectedNode.first().text(),
							"orgId" : orgIdUpdate.val(),
							"name" : nameUpdate.val(),
							"fatherId" : fatherIdUpdate.val(),
							"companyId" : $("#companyId").val(),
							"creator" : $("#username").val()
						}
						var orgs = JSON.stringify(org);
						$.ajax({
							type : "post", // 请求方式  
							url : contextPath + "/bg/org/update", //url地址  
							data : orgs, //数据  
							contentType : "application/json",
							dataType : "json",
							success : function(data) {
								if(data == true) {
									var selectedNode = $(".row_selected td");
									selectedNode.each(function(index) {
										if(index == 1) {
											$(this).text(org.name);
										}
										if(index == 2) {
											$(this).text(org.fatherId);
										}
									})
									window.location.reload();
								} else {
									$("#dialog-message").html(this.id + "更新失败！");
									$("#dialog-message").dialog("open");
								}
							},
							error : function() {
								$("#dialog-message").html("更新失败！");
								$("#dialog-message").dialog("open");
							}
						});

						$(this).dialog("close");
					}
				},
				"取消" : function() {
					$(this).dialog("close");
				}
			},
			close : function() {
				allFieldsUpdate.val("").removeClass("ui-state-error");
			}
		});

		$("#dialog-form").dialog({
			autoOpen : false,
			height : 300,
			width : 400,
			modal : true,
			buttons : {
				"创建" : function() {
					var bValid = true;

					allFields.removeClass("ui-state-error");

					bValid = bValid && checkLength(tips, name, "组织名称", 2, 16);
					bValid = bValid && checkLength(tips, fatherId, "父组织ID", 1, 11);
					if (bValid) {

						var org = {
							"orgId": orgId.val(),
							"name" : name.val(),
							"fatherId" : fatherId.val(),
							"companyId" : $("#companyId").val(),
							"creator" : $("#username").val()
						}
						var orgs = JSON.stringify([ org ]);
						$.ajax({
							type : "post", // 请求方式  
							url : contextPath + "/bg/org/add", //url地址  
							data : orgs, //数据  
							contentType : "application/json",
							dataType : "json",
							success : function(data) {
								$(data).each(function() {
									if (this.id == 0) {
										this.id = "插入失败！";
										addOrg(this);
									} else {
										window.location.reload();
									}
									
								});
							},
							error : function() {
								var org = {
									id : "插入失败",
									orgId : orgId.val(),
									name : name.val(),
									fatherId : fatherId.val()
								};
								addOrg(org);
							}
						});

						$(this).dialog("close");
					}
				},
				"取消" : function() {
					$(this).dialog("close");
				}
			},
			close : function() {
				allFields.val("").removeClass("ui-state-error");
			}
		});
		$("#dialog-message").dialog({
			autoOpen : false,
			height : 300,
			width : 400,
			modal : true,
			close : function() {
				if($("#status").val() == 1) {
					window.location.reload();
				}
			}
		});
		$("#add").click(function() {

			$("#dialog-form").dialog("open");
		});
		$("#del").click(function() {
			var selectedOrgId = $(".row_selected td").first().text();
			var selectedNode = $(".row_selected");
			if (selectedNode.length == 0) {
				$("#dialog-message").html("请点击选择行！");
				$("#dialog-message").dialog("open");
				return;
			}

			$.ajax({
				type : "get", // 请求方式  
				url : contextPath + "/bg/org/delete/" + selectedOrgId, //url地址  
				contentType : "application/json",
				dataType : "json",
				success : function(data) {
					if (data == true) {
						window.location.reload();
					} else {
						$("#dialog-message").html("ROOT 节点不能删除！");
						$("#dialog-message").dialog("open");
					}
				},
				error : function() {
					$("#dialog-message").html("删除失败！");
					$("#dialog-message").dialog("open");
				}
			});
		});

		$("#edit").click(function() {
			var selectedNode = $(".row_selected");
			if (selectedNode.length == 0) {
				$("#dialog-message").html("请点击选择行！");
				$("#dialog-message").dialog("open");
				return;
			}
			var nodeValue = [];
			$(".row_selected td").each(function(index) {
				nodeValue[index] = $(this).text();
			});
			var selectedOrgId = nodeValue[1];
			var selectedOrgName = nodeValue[2];
			var selectedOrgFatherId = nodeValue[3];
			$("#orgIdUpdate").val(selectedOrgId);
			$("#nameUpdate").val(selectedOrgName);
			$("#fatherIdUpdate").val(selectedOrgFatherId);
			$("#dialog-form-update").dialog("open");
		});
	});
</script>

</head>
<body>
	<input type="hidden" value="<%=request.getContextPath()%>"
		id="contextPath" />
	<input id="companyId" type="hidden" value="${user.companyId }" />
	<input id="username" type="hidden" value="${user.username }" />
	<div id="dialog-message" title="消息">请点击选择行！</div>
	<div id="dialog-form-update" title="更新组织">
		<p class="validateTipsUpdate">All form fields are required.</p>
		<form name="updateOrg" class="form-horizontal" role="form">
			<div class="form-group form-valid">
				<label for="orgId" class="col-sm-4 control-label">组织代码：</label>
				<div class="col-sm-4">
					<input type="text" name="orgId" id="orgIdUpdate"
						class="form-control" placeholder="组织代码" />
				</div>
				<div class="col-sm-4">
					<code> </code>
				</div>
			</div>
			<div class="form-group form-valid">
				<label for="name" class="col-sm-4 control-label">组织名称：</label>
				<div class="col-sm-4">
					<input type="text" name="name" id="nameUpdate" class="form-control"
						placeholder="用户编号" />
				</div>
				<div class="col-sm-4">
					<code class="error"> </code>
				</div>
			</div>
			<div class="form-group form-valid">
				<label for="fatherId" class="col-sm-4 control-label">父组织代码：</label>
				<div class="col-sm-4">
					<input type="text" name="fatherId" id="fatherIdUpdate"
						class="form-control" placeholder="父组织代码" />
				</div>
				<div class="col-sm-4">
					<code> </code>
				</div>
			</div>
		</form>
	</div>
	<div id="dialog-form" title="创建组织">
		<p class="validateTips">All form fields are required.</p>
		<form name="addOrg" class="form-horizontal" role="form">
			<div class="form-group form-valid">
				<label for="orgId" class="col-sm-4 control-label">组织代码：</label>
				<div class="col-sm-4">
					<input type="text" name="orgId" id="orgId"
						class="form-control" placeholder="组织代码" />
				</div>
				<div class="col-sm-4">
					<code> </code>
				</div>
			</div>
			<div class="form-group form-valid">
				<label for="name" class="col-sm-4 control-label">组织名称：</label>
				<div class="col-sm-4">
					<input type="text" name="name" id="name" class="form-control"
						placeholder="用户编号" />
				</div>
				<div class="col-sm-4">
					<code class="error"> </code>
				</div>
			</div>
			<div class="form-group form-valid">
				<label for="fatherId" class="col-sm-4 control-label">父组织代码：</label>
				<div class="col-sm-4">
					<input type="text" name="fatherId" id="fatherId"
						class="form-control" placeholder="父组织ID" />
				</div>
				<div class="col-sm-4">
					<code> </code>
				</div>
			</div>
		</form>
	</div>

	<%@ include file="headbar.jsp"%>

	<div class="row">
		<%@ include file="leftMenu.jsp"%>
		<div class="col-md-9">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">公司组织结构管理</h3>
				</div>
				<div class="panel-body">
					<div class="col-md-4" style="padding-bottom: 10px;">
						<button id="add" class='btn btn-primary'>增加</button>
						<button id="edit" class='btn btn-primary col-md-offset-1 edit'>编辑</button>
						<button id="del" class='btn btn-primary col-md-offset-1 del'>删除</button>
					</div>
					<table id="orgTable">
						<thead>
							<tr>
								<th>序号</th>
								<th>组织代码</th>
								<th>组织名称</th>
								<th>父组织代码</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="org" items="${orgList}" varStatus="vs">
								<tr>
									<td>${org.id }</td>
									<td>${org.oId }</td>
									<td>${org.oName }</td>
									<td>${org.oFatherId }</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>

				</div>
			</div>

		</div>
	</div>



</body>
</html>
