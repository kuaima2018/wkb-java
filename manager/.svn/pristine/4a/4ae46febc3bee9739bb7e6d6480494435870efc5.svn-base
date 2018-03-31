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
<style>
input.error {
	border: 1px solid red;
}

.error {
	color: red;
}
</style>
<script type="text/javascript">
	$(function() {
		
		$("form").validate({
			rules : {
				oldPassword : {
					required : true,
					minlength : 5
					},
				password : {
					required : true,
					minlength : 5
					},
				rePassword : {
					required : true,
					minlength : 5,
					equalTo : "#password"
					}
			},
			messages : {
								oldPassword : {
									required : "Please provide a password",
									minlength : "Your password must be at least 5 characters long"
								},
								password : {
									required : "Please provide a password",
									minlength : "Your password must be at least 5 characters long"
								},
								rePassword : {
									required : "Please provide a password",
									minlength : "Your password must be at least 5 characters long",
									equalTo : "Please enter the same password as above"
								}
			}
		});
		$(".form-valid").each(function() {
			var errorId = $(this).find("code>span").attr("id");
			if(errorId === undefined) {
				$(this).addClass("");
			} else {
				$(this).addClass("has-error has-feedback");
			}
		});
	});
</script>


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
				<form:form modelAttribute="pwd" commandName="pwd" name="pwd" class="form-horizontal" role="form" method="post"
					style="margin-top: 10px" action="${pageContext.request.contextPath}/bg/user/pwd">
					
					<div class="form-group form-valid">
						<label for="oldPassword" class="col-sm-3 control-label">旧密码：</label>
						<div class="col-sm-4">
							<form:password path="oldPassword" cssClass="form-control"
								id="oldPassword" placeholder="旧密码" />
						</div>
						<div class="col-sm-5">
							<code><form:errors path="oldPassword"></form:errors> </code>
						</div>
					</div>

					<div class="form-group form-valid">
						<label for="password" class="col-sm-3 control-label">新密码：</label>
						<div class="col-sm-4">
							<form:password path="password" cssClass="form-control"
								id="password" placeholder="新密码" />
						</div>
						<div class="col-sm-5">
							<code><form:errors path="password"></form:errors> </code>
						</div>
					</div>

					<div class="form-group form-valid">
						<label for="rePassword" class="col-sm-3 control-label">确认新密码：</label>
						<div class="col-sm-4">
							<form:password path="rePassword" class="form-control"
								id="rePassword" placeholder="确认新密码" />
						</div>
						<div class="col-sm-5">
							<code><form:errors path="rePassword"></form:errors>  </code>
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-3 col-sm-12">
							<button type="submit" class="btn btn-default edit">修改</button>

						</div>
					</div>

				</form:form>
			</div>

		</div>
	</div>
</body>
</html>