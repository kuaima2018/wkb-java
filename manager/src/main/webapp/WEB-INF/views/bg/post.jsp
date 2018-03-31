
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
		$("#postTable tbody tr").click(function(e) {
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

		oTable = $('#postTable').dataTable({
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
		var postId = $("#postId");
		var orgId = $("#orgId");
		var right = $("#right");
		var allFields = $([]).add(name).add(postId).add(orgId).add(right);
		var tips = $(".validateTips");
		
		var nameUpdate = $("#nameUpdate");
		var postIdUpdate = $("#postIdUpdate");
		var orgIdUpdate = $("#orgIdUpdate");
		var rightUpdate = $("#rightUpdate");
		var allFieldsUpdate = $([]).add(nameUpdate).add(postIdUpdate).add(orgIdUpdate).add(rightUpdate);
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

		function addPost(post) {
			var num = $("#postTable tbody tr").size();
			var trClass = "even";
			if (num % 2 == 0) {
				trClass = "odd";
			}
			$("#postTable tbody").append(
					"<tr class='"+ trClass + "'><td>" + post.id + "</td><td>" + post.postId + "</td><td>"
							+ post.name + "</td><td>" + post.orgId + "</td><td>" + post.right
							+ "</td></tr>");
		}
		$("#dialog-form-update").dialog({
			autoOpen : false,
			height : 360,
			width : 400,
			modal : true,
			buttons : {
				"更新" : function() {
					var bValid = true;

					allFieldsUpdate.removeClass("ui-state-error");
					bValid = bValid && checkLength(tipsUpdate, nameUpdate, "岗位名称", 2, 16);
					bValid = bValid && checkLength(tipsUpdate, postIdUpdate, "岗位代码", 1, 11);
					bValid = bValid && checkLength(tipsUpdate, orgIdUpdate, "组织代码", 1, 11);
					if (bValid) {
						var selectedNode = $(".row_selected td");
						var post = {
							"id" : selectedNode.first().text(),
							"postId": postIdUpdate.val(),
							"name" : nameUpdate.val(),
							"orgId" : orgIdUpdate.val(),
							"right" : rightUpdate.val(),
							"companyId" : $("#companyId").val(),
							"creator" : $("#username").val()
						}
						var posts = JSON.stringify(post);
						$.ajax({
							type : "post", // 请求方式  
							url : contextPath + "/bg/post/update", //url地址  
							data : posts, //数据  
							contentType : "application/json",
							dataType : "json",
							success : function(data) {
								if(data == true) {
									
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
			height : 360,
			width : 400,
			modal : true,
			buttons : {
				"创建" : function() {
					var bValid = true;

					allFields.removeClass("ui-state-error");

					bValid = bValid && checkLength(tips, name, "岗位名称", 2, 16);
					bValid = bValid && checkLength(tips, postId, "岗位代码", 1, 11);
					bValid = bValid && checkLength(tips, orgId, "组织代码", 1, 11);
					if (bValid) {

						var org = {
							"postId": postId.val(),
							"name" : name.val(),
							"orgId" : orgId.val(),
							"right" : right.val(),
							"companyId" : $("#companyId").val(),
							"creator" : $("#username").val()
						}
						var orgs = JSON.stringify([ org ]);
						$.ajax({
							type : "post", // 请求方式  
							url : contextPath + "/bg/post/add", //url地址  
							data : orgs, //数据  
							contentType : "application/json",
							dataType : "json",
							success : function(data) {
								$(data).each(function() {
									if (this.id == 0) {
										this.id = "插入失败！";
										addPost(this);
									} else {
										window.location.reload();
									}
									
								});
							},
							error : function() {
								var post = {
									id : "插入失败",
									postId : postId.val(),
									orgId : orgId.val(),
									name : name.val(),
									right : right.val()
								};
								addPost(post);
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
			var selectedPostId = $(".row_selected td").first().text();
			var selectedNode = $(".row_selected");
			if (selectedNode.length == 0) {
				$("#dialog-message").html("请点击选择行！");
				$("#dialog-message").dialog("open");
				return;
			}

			$.ajax({
				type : "get", // 请求方式  
				url : contextPath + "/bg/post/delete/" + selectedPostId, //url地址  
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
			
			$("#postIdUpdate").val(nodeValue[1]);
			$("#nameUpdate").val(nodeValue[2]);
			$("#orgIdUpdate").val(nodeValue[3]);
			$("#rightUpdate").val(nodeValue[4]);
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
		<form name="updatePost" class="form-horizontal" role="form">
			<div class="form-group form-valid">
				<label for="postIdUpdate" class="col-sm-4 control-label">岗位代码：</label>
				<div class="col-sm-4">
					<input type="text" name="postId" id="postIdUpdate"
						class="form-control" placeholder="岗位代码" />
				</div>
				<div class="col-sm-4">
					<code> </code>
				</div>
			</div>
			<div class="form-group form-valid">
				<label for="nameUpdate" class="col-sm-4 control-label">岗位名称：</label>
				<div class="col-sm-4">
					<input type="text" name="name" id="nameUpdate" class="form-control"
						placeholder="岗位名称" />
				</div>
				<div class="col-sm-4">
					<code class="error"> </code>
				</div>
			</div>
			<div class="form-group form-valid">
				<label for="orgIdUpdate" class="col-sm-4 control-label">组织代码：</label>
				<div class="col-sm-4">
					<input type="text" name="orgId" id="orgIdUpdate"
						class="form-control" placeholder="组织代码" />
				</div>
				<div class="col-sm-4">
					<code> </code>
				</div>
			</div>
			<div class="form-group form-valid">
				<label for="rightUpdate" class="col-sm-4 control-label">选择权限：</label>
				<div class="col-sm-4">
					<select name="right" id="rightUpdate">
  						<option value="1" selected="selected">全部</option>
  						<option value="2">下级</option>
  						<option value="3">本部门</option>
  						<option value="4">其他</option>
					</select>
				</div>
				<div class="col-sm-4">
					<code> </code>
				</div>
			</div>
		</form>
	</div>
	<div id="dialog-form" title="创建岗位">
		<p class="validateTips">All form fields are required.</p>
		<form name="addPostRight" class="form-horizontal" role="form">
			<div class="form-group form-valid">
				<label for="postId" class="col-sm-4 control-label">岗位代码：</label>
				<div class="col-sm-4">
					<input type="text" name="postId" id="postId"
						class="form-control" placeholder="岗位代码" />
				</div>
				<div class="col-sm-4">
					<code> </code>
				</div>
			</div>
			<div class="form-group form-valid">
				<label for="name" class="col-sm-4 control-label">岗位名称：</label>
				<div class="col-sm-4">
					<input type="text" name="name" id="name" class="form-control"
						placeholder="岗位名称" />
				</div>
				<div class="col-sm-4">
					<code class="error"> </code>
				</div>
			</div>
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
				<label for="right" class="col-sm-4 control-label">选择权限：</label>
				<div class="col-sm-4">
					<select name="right" id="right">
  						<option value="1" selected="selected">全部</option>
  						<option value="2">下级</option>
  						<option value="3">本部门</option>
  						<option value="4">其他</option>
					</select>
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
					<h3 class="panel-title">公司岗位管理</h3>
				</div>
				<div class="panel-body">
					<div class="col-md-4" style="padding-bottom: 10px;">
						<button id="add" class='btn btn-primary'>增加</button>
						<button id="edit" class='btn btn-primary col-md-offset-1 edit'>编辑</button>
						<button id="del" class='btn btn-primary col-md-offset-1 del'>删除</button>
					</div>
					<table id="postTable">
						<thead>
							<tr>
								<th>序号</th>
								<th>岗位代码</th>
								<th>岗位名称</th>
								<th>组织代码</th>
								<th>岗位权限代码</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="post" items="${postList}" varStatus="vs">
								<tr>
									<td>${post.id }</td>
									<td>${post.pId }</td>
									<td>${post.pName }</td>
									<td>xxx<%--${post.orgId }--%></td>
									<td>${post.pRight }</td>
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
