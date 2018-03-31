<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../head.jsp" %>
<script type="text/javascript">
	$(function() {
		$("#authIMG").click(function() {
			var contextPath = $("#contextPath").val();
			$(this).attr("src", contextPath + "/authCode?time=" + (new Date()).getTime());
		});
		$(".form-valid").each(function() {
			var errorId = $(this).find("code>span").attr("id");
			if(errorId === undefined) {
				$(this).addClass("has-success has-feedback");
			} else {
				$(this).addClass("has-error has-feedback");
			}
		});
	})
</script>
<%@ include file="../loginForm.jsp" %>