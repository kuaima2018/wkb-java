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

<script type="text/javascript">
$(function() {
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
	$("tbody tr").each(function() {
		$(this).find(".aggree-btn").click(function() {
			var id = $(this).attr("id");
			var contextPath = $("#contextPath").val();
			$.ajax({
				type : "get", // 请求方式  
				url : contextPath + "/bg/aggree/" + id, //url地址  
				contentType : "application/json",
				dataType : "json",
				success : function(data) {
					if (data == true) {
						$("#status").val("1");
						$("#dialog-message").html("加入成功！");
						$("#dialog-message").dialog("open");
						
					} else {
						$("#dialog-message").html("加入失败！");
						$("#dialog-message").dialog("open");
					}
				},
				error : function() {
					$("#dialog-message").html("加入失败！");
					$("#dialog-message").dialog("open");
				}
			});
		});
		
		$(this).find(".reject-btn").click(function() {
			var id = $(this).attr("id");
			var contextPath = $("#contextPath").val();
			$.ajax({
				type : "get", // 请求方式  
				url : contextPath + "/bg/reject/" + id, //url地址  
				contentType : "application/json",
				dataType : "json",
				success : function(data) {
					if (data == true) {
						$("#status").val("1");
						$("#dialog-message").html("拒绝成功！");
						$("#dialog-message").dialog("open");
						
					} else {
						$("#dialog-message").html("拒绝失败！");
						$("#dialog-message").dialog("open");
					}
				},
				error : function() {
					$("#dialog-message").html("拒绝失败！");
					$("#dialog-message").dialog("open");
				}
			});
		});
	})
})
</script>
</head>
<body>
<input type="hidden" value="<%=request.getContextPath()%>"
		id="contextPath" />
<input type="hidden" name="status" id="status" value="0">
<div id="dialog-message" title="消息">请点击选择行！</div>
	<%@ include file="headbar.jsp"%>

	<div class="row">
		<%@ include file="leftMenu.jsp"%>
		<div class="col-md-9">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">员工加入列表管理</h3>
				</div>
				<div class="panel-body">
					<table class="table table-hover">
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
									<td>${em.username }</td>
									<td><button class="btn btn-primary aggree-btn" id="${em.id}">同意</button>
										<button class="btn btn-danger col-md-offset-1 reject-btn" id="${em.id}">拒绝</button></td>
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