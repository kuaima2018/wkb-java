<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="../head.jsp"%>

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
<script
	src="<%=request.getContextPath()%>/resources/js/jquery.validate.js"></script>



</head>
<body>
	<%@ include file="headbar.jsp"%>
	<div class="row">
		<%@ include file="leftMenu.jsp"%>
		<div class="col-md-9">
			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">密码修改</h3>
				</div>
				<div>
					${message }
				</div>
			</div>

		</div>
	</div>
</body>
</html>