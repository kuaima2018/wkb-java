<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="col-md-3" style="padding-left: 20px;">
	<ul id="left-nav" class="nav nav-pills nav-stacked">
		<li class="active"><a>公司组织结构</a> <c:set var="index" value="0"
				scope="request" /> <!-- 自增序号，注意scope--> <c:set var="level"
				value="0" scope="request" /> <!-- 记录树的层次，注意scope--> <c:import
				url="__r.jsp" /></li>
		<li><a class="normal" href="<%=request.getContextPath()%>/bg/company/${company.id}">公司组织结构管理</a></li>
		<li><a class="normal" href="<%=request.getContextPath()%>/bg/company/${company.id}/post">公司岗位管理</a></li>
		<li><a class="normal" href="<%=request.getContextPath()%>/bg/company/${company.id}/jion">员工加入列表管理</a></li>
		<li><a class="normal" href="<%=request.getContextPath()%>/bg/company/${company.id}/org">员工部门岗位管理</a></li>
		<li><a class="normal" href="<%=request.getContextPath()%>/bg/company/${company.id}/pwd">修改密码</a></li>
	</ul>

</div>
<script type="text/javascript">
	$(function() {
		$("#tree").treeview({
			collapsed : true,
			animated : "medium",
			control : "#sidetreecontrol",
			persist : "location"
		});
		$("#orgTree").treeview({
			animated : "medium",
			control : "#sidetreecontrol",
			persist : "location"
		});
		var location = window.location.href;
		$(".nav-stacked").find("a").each(function() {
			var href = $(this).attr("href");
			if(location.indexOf(href) != -1) {
				$(this).removeClass("normal");
				$(this).parent().addClass("active");
			}
		});
		
	})
</script>
