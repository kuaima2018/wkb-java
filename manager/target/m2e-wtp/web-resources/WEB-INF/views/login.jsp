<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="head.jsp" %>
<script type="text/javascript">
	$(function() {
		$("#authIMG").bind("click", function() {
			var contextPath = $("#contextPath").val();
			$(this).attr("src", contextPath + "/authCode?time=" + (new Date()).getTime());
		});
	})
</script>
<%@ include file="loginForm.jsp" %>