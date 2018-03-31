<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="../head.jsp"%>

<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/jquery.treeview.css">
<script
	src="<%=request.getContextPath()%>/resources/js/jquery-ui-1.10.4.custom.js"></script>
<script
	src="<%=request.getContextPath()%>/resources/js/jquery.cookie.js"
	type="text/javascript"></script>
<script
	src="<%=request.getContextPath()%>/resources/js/jquery.treeview.js"
	type="text/javascript"></script>
<link
	href="<%=request.getContextPath()%>/resources/css/smoothness/jquery-ui-1.8.4.custom.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/resources/css/left-menu.css">
<script type="text/javascript">
	$(function() {
		var contextPath = $("#contextPath").val();

		$("#dialog-org").dialog({
			autoOpen : false,
			height : 300,
			width : 400,
			modal : true,
			buttons : {
				"更新" : function() {
					var bValid = true;
					if (bValid) {
						var user = {
							"id":$("#userId").val(),
							"postId":$("input[type='radio'][name='postName']:checked").val()
						}
						user = JSON.stringify( user );
						$.ajax({
							type : "post", // 请求方式  
							url : contextPath + "/bg/user/update", //url地址  
							data : user, //数据  
							contentType : "application/json",
							dataType : "json",
							success : function(data) {
								if(data == true) {
									$("#status").val("1")
									$("#dialog-message").html("更新成功！");
									$("#dialog-message").dialog("open");
									
								} else {
									$("#dialog-message").html("更新失败！");
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
		$(".btn").click(function() {
			var postId = $(this).attr("id");
			var userId = $(this).attr("uid");
			$("#userId").val(userId);
			$("input[type='radio'][name='postName'][value='"+postId+"']").attr("checked", true);
			$("#dialog-org").dialog("open");
		});
	});
</script>

</head>
<body>
<input type="hidden" value="<%=request.getContextPath()%>"
		id="contextPath" />
<input type="hidden" name="status" id="status" value="0">
	<div id="dialog-message" title="消息">
	</div>
	<div id="dialog-org" title="更新组织">
		<form name="updateUserOrg" class="form-horizontal" role="form">
			<c:set var="orgIndex" value="0" scope="request" />
			<!-- 自增序号，注意scope-->
			<c:set var="orgLevel" value="0" scope="request" />
			<!-- 记录树的层次，注意scope-->
			<c:import url="__org.jsp" />
			<input type="hidden" name="userId" id="userId" value="">
			<input type="hidden" name="postName" id="postName" value="">
		</form>
	</div>
	<%@ include file="headbar.jsp"%>

	<div class="row">
		<%@ include file="leftMenu.jsp"%>
		<div class="col-md-8">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">员工部门岗位管理</h3>
				</div>
				<div class="panel-body">
					<table id="userList" class="table table-hover">
						<thead>
							<tr>
								<th>姓名</th>
								<th colspan="1">操作</th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="em" items="${emList}" varStatus="vs">
								<tr>
									<td>${em.uName }</td>
									<td><button class="btn btn-primary" id="${em.uId }" uid="${em.uId }" >选择岗位</button>
								</tr>
							</c:forEach>

						</tbody>
					</table>
				</div>
			</div>

		</div>
		<div class="col-md-4"></div>
	</div>




</body>
</html>